(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer [->AiPlayer ]]
           [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner]]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.input.console :refer :all]
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

; (defn set-players [human-marker input]
;   (if (= human-marker "x")
;     (let [player-1 (->player-protocol-one "x" input)
;           player-2 (->AiPlayer "o")]
;       [player-1 player-2])
;     (let [player-2 (->HumanPlayer "o" input)
;           player-1 (->AiPlayer "x")]
;       [player-1 player-2])))

(defmulti set-players (fn [game-type marker input] game-type))

(defmethod set-players :computer-vs-human [game-type-type marker input ]
  (if (= marker "x")
    (let [player-1 (->HumanPlayer "x" input)
          player-2 (->AiPlayer "o")]
      [player-1 player-2])
    (let [player-2 (->HumanPlayer "o" input)
          player-1 (->AiPlayer "x")]
      [player-1 player-2])))

(defn game-intializer [display input board game-type]
  (let [marker-selection (get-marker input)
        players (set-players game-type marker-selection input)
        game {:board board :ai-marker (opposite-marker marker-selection) :player-marker marker-selection}]
    (game-runner game display (players 0) (players 1))))

(defn -main []
  (let [terminal (->TerminalDisplay)
        input (->ConsoleInput)
        board (create-empty-board 3)]
    (game-intializer terminal input board)))