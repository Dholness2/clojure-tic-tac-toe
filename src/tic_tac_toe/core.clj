(ns tic-tac-toe.core
  (:gen-class)
 (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer :all]
           [tic-tac-toe.display.terminal :refer :all]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.protocol.player :refer :all]
           [tic-tac-toe.protocol.display :refer :all]))

(defn game-runner [board display player1 player2]
  (display-state  display board)
  (if-not (winner? board)
    (let [current-board (next-move player1 board)]
      (display-state display current-board)
      (if-not (winner? board)(game-runner (next-move player2 current-board) display player1 player2)))
    (print-winner board)))

(defn -main []

(def terminal (->TerminalDisplay))
(def human (->HumanPlayer "x"))
(def ai (->AiPlayer "o"))

(let [emptyboard (create-empty-board board-dimensions)]
   (game-runner emptyboard terminal human  ai  )))

