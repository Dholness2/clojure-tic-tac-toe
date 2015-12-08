(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer [create-empty-board]]
           [tic-tac-toe.game :refer [winner?]]
           [tic-tac-toe.ai :refer [->AiPlayer ]]
           [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner]]
           [tic-tac-toe.human :refer [->HumanPlayer]]
           [tic-tac-toe.input.console :refer [->ConsoleInput]]
           [tic-tac-toe.protocol.player :refer [next-move]]
           [tic-tac-toe.protocol.input :refer [get-move get-marker get-board-size]]
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
        (display-winner display  (:board current-state))))
    (display-winner display (:board game))))

(defn opposite-marker [marker]
  (if (= marker-one marker)
    marker-two
    marker-one))

(defmulti create-game (fn [game-type input display board] game-type))

(defmethod create-game :computer-vs-human [game-type-type input display board ]
  (let [marker-selection (get-marker input)]
    (if (= marker-selection marker-one)
      (let [player-1 (->HumanPlayer marker-one input)
            player-2 (->AiPlayer marker-two)
            game {:board board :ai-marker (opposite-marker marker-selection) :player-marker marker-selection}]
        (game-runner game display player-1 player-2))
      (let [player-2 (->HumanPlayer marker-two input)
            player-1 (->AiPlayer marker-one)
            game {:board board :ai-marker (opposite-marker marker-selection) :player-marker marker-selection}]
        (game-runner game display player-1 player-2)))))

(defn game-intializer [display input game-type]
  (let [game game-type
        board-diemnson (get-board-size input)
        board (create-empty-board board-diemnson)]
    (create-game game input display board)))

(defn -main []
  (let [terminal (->TerminalDisplay)
        input (->ConsoleInput)
        game-type :computer-vs-human]
    (game-intializer terminal input game-type)))

