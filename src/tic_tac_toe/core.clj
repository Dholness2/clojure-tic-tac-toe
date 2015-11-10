(ns tic-tac-toe.core
  (:gen-class)
  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.ai :refer :all]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.display :refer :all]
           [tic-tac-toe.game :refer :all]))

(defn game-runner [board]
  (display-iteration board)
  (if-not (winner? board)
    (let [user-move (user-input-move board 3)
          current-board (move user-move "x" board)]
      (display-iteration current-board)
      (game-runner (move (ai-move current-board) "o" current-board)))
    (print-winner board)))

(defn -main []
  (let [emptyboard (create-empty-board 3 3)]
    (game-runner emptyboard)))