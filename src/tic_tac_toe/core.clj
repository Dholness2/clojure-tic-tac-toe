(ns tic-tac-toe.core
  (:gen-class)
  (:import [tic_tac_toe.human HumanPlayer])
  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer :all]
           [tic-tac-toe.display :refer :all]
           [tic-tac-toe.protocol.player :refer :all]))


(defn game-runner [board]
  (display-iteration board)
  (if-not (winner? board)
    (let [current-board (next-move player1-marker board)]
      (display-iteration current-board)
      (if (ai-move current-board) (game-runner (move (ai-move current-board) player2-marker current-board))))
    (print-winner board)))

(defn -main []

(println (.HumanPlayer "x"))
 
; (let [emptyboard (create-empty-board board-dimensions)]
;     (game-runner emptyboard)))

