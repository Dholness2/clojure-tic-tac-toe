(ns tic-tac-toe.core
  (:gen-class)
 (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer [->AiPlayer place-holder switch-markers marker-1 marker-2 ]]
           [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner]]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.input :refer :all]
           [tic-tac-toe.protocol.player :refer :all]
           [tic-tac-toe.protocol.input :refer :all]
           [tic-tac-toe.protocol.display :refer [display-state display-winner]]))

(defn game-runner [board display player1 player2]
  (display-state  display board)
  (if-not (winner? board)
    (let [current-board (next-move player1 board)]
      (display-state display current-board)
      (if-not (winner? current-board)
        (game-runner (next-move player2 current-board) display player1 player2)
        (print-winner current-board)))
    (print-winner board)))

(defn set-markers [human-marker input]
  (if (= human-marker "x")
    (let [player-1 (->HumanPlayer "x" input)
           player-2 (->AiPlayer "o")]
      [player-1 player-2])
    (let [player-2 (->HumanPlayer "o" input)
           player-1 (->AiPlayer "x")]
      (switch-markers place-holder marker-1 marker-2)
      [player-1 player-2])))

(defn game-intializer [display input board]
  (println "Select your marker x or o ")
  (let [human-marker (read-line)]
    (let[players (set-markers human-marker input )]
        (game-runner board display (players 0) (players 1)))))

(defn -main []
  (let [terminal (->TerminalDisplay)
        input (->ConsoleInput)
        board (create-empty-board 3)]
    (game-intializer terminal  input board)))

