(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer [create-empty-board]]
            [clojure.core.matrix :refer [rotate]]
           [tic-tac-toe.game :refer [winner?]]
           [tic-tac-toe.ai :refer [->AiPlayer ]]
           [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner]]
           [tic-tac-toe.human :refer [->HumanPlayer]]
           [tic-tac-toe.input.console :refer [->ConsoleInput]]
           [tic-tac-toe.protocol.player :refer [next-move]]
           [tic-tac-toe.protocol.input :refer [get-move get-board-size get-game-type]]
           [tic-tac-toe.protocol.display :refer [display-state display-winner]]))

(def marker-one "x")
(def marker-two "o")
(def games [:human-vs-computer :computer-vs-human])

(defn game-runner [game display]
  (let [game-state (first game)
        players    (last game )]
    (display-state display (:board game-state))
    (if-not (winner? (:board game-state))
      (let [current-state (next-move (first players) game-state)]
        (display-state display (:board current-state))
        (if-not (winner? (:board current-state))
          (game-runner [current-state (rotate players 0 1)] display)
          (display-winner display  (:board current-state)))))))3

(defmulti create-game (fn [game-type input board ] game-type))

(defmethod create-game :human-vs-computer [game-type input board]
  (let [player-1 (->HumanPlayer marker-one input)
        player-2 (->AiPlayer marker-two)
        game-state {:board board :ai-marker marker-two :player-marker marker-one}]
   [game-state [player-1 player-2]]))

(defmethod create-game :computer-vs-human [game-type input board]
  (let [player-1 (->AiPlayer marker-two)
        player-2 (->HumanPlayer marker-one input)
        game-state {:board board :ai-marker marker-one :player-marker marker-two}]
    [game-state [player-1 player-2]]))

(defn game-intializer [display input]
  (let [play-type (games (get-game-type input games))
        board-diemnson (get-board-size input)
        board (create-empty-board board-diemnson)
        game (create-game play-type input  board)]
        (game-runner game display)))

(defn -main []
  (let [terminal (->TerminalDisplay)
        input (->ConsoleInput)]
    (game-intializer terminal input)))

