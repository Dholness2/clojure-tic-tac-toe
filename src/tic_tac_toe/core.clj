(ns tic-tac-toe.core
  (:gen-class)
(require [tic-tac-toe.board :refer :all]
         [tic-tac-toe.ai :refer :all]
         [tic-tac-toe.display :refer :all]
         [tic-tac-toe.game :refer :all]))

(defn game-runner
 ([welcome]
   (println welcome)
   (def current-board (create-empty-board 3 3))
   (display-index current-board)
   (display-board current-board )
   	(def current-board (move (user-input-move current-board 3) "x" current-board))
   	(clear-terminal)
    (display-index current-board)
    (display-board current-board )
   (game-runner current-board "x"))
 ([board player]
   (if-not (winner? board)
    (do
       (def current-board (move (ai-move board) "o" board))
	   (clear-terminal)
     (display-index current-board)
 	   (display-board current-board)
       (def current-board (move (user-input-move current-board  3) player current-board))
       (clear-terminal)
       (display-index current-board)
       (display-board current-board)
       (game-runner current-board "x"))
    (print-winner current-board))))

(defn -main []
  (def welcome "welcome to Dons tic tac toe")
  (game-runner  welcome ))



