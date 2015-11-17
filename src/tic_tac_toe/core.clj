(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.ai :refer :all]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.display :refer :all]
           [tic-tac-toe.game :refer :all]))

(def human-place-holder "x")
(def computer-place-holder "o")
(def board-dimensions 3 )

(defn game-runner [board]
  (display-iteration board)
  (if-not (winner? board)
    (let [user-move (user-input-move board board-dimensions)
          current-board (move user-move human-place-holder board)]
      (display-iteration current-board)
      (if (ai-move current-board) (game-runner (move (ai-move current-board) computer-place-holder current-board))))
    (print-winner board)))

(defn -main []
  (let [emptyboard (create-empty-board board-dimensions)]
    (game-runner emptyboard)))