(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer [->AiPlayer ]]
           [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner]]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.input :refer :all]
           [tic-tac-toe.protocol.player :refer :all]
           [tic-tac-toe.protocol.input :refer :all]
           [tic-tac-toe.protocol.display :refer [display-state display-winner]]))

(def marker-one "x")
(def marker-two "o")


(defn game-runner [game display player1 player2]
  (display-state display (:board game))
  (if-not (winner? (:board game))
    (let [current-state (next-move player1 game)]
      (display-state display (:board current-state))
      (if-not (winner? (:board current-state))
        (game-runner (next-move player2 current-state) display player1 player2)
        (print-winner (:board current-state))))
    (print-winner(:board game))))

(defn opposite-marker [marker]
  (if (= marker-one marker)
    marker-two
    marker-one))

(defn set-markers [human-marker input]
  (if (= human-marker "x")
    (let [player-1 (->HumanPlayer "x" input)
          player-2 (->AiPlayer "o")]
      [player-1 player-2])
    (let [player-2 (->HumanPlayer "o" input)
          player-1 (->AiPlayer "x")]
      [player-1 player-2])))

(defn game-intializer [display input board]
  (println "Select your marker x or o ")
  (let [selection (read-line)]
    (let[players (set-markers selection input)
         game {:board board :ai-marker (opposite-marker selection) :player-marker selection}]
      (game-runner game display (players 0) (players 1)))))

(defn -main []
  (let [terminal (->TerminalDisplay)
        input (->ConsoleInput)
        board (create-empty-board 3)]
    (game-intializer terminal input board)))

