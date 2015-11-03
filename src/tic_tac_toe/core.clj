(ns tic-tac-toe.core
  (:gen-class)
(require [tic-tac-toe.board :refer :all]))

  
  (defn computer-move [board rowsize]
  	(matrix-convrt (+ 1(.indexOf (flatten board) "_")) rowsize))

  (defn clear-terminal[]
    (print "\033[2J"))

  (defn display-index  [moves]
     println (apply str (moves 0))
     (display-index (drop 1 moves)))

  (defn display-board [board]
  	(if(= false (empty? board))
  	  (let[ row ((vec(take 1 board))0)]
        (println (clojure.string/join row))
		(display-board (drop 1 board)))
  	    ))

  
(defn game-runner 
 ([welcome]
   (println welcome)
   (def current-board (create-empty-board 3 3))
   (display-board current-board )
   	(def current-board (move (user-input-move current-board 3) "X" current-board)) 
   	(clear-terminal)
    (display-board current-board )
   (game-runner current-board "X"))
 ([board player]
   (if-not (winner? board)
    (do 
       (def current-board (move (computer-move board 3) "O" board))
	   (clear-terminal)
 	   (display-board current-board)
       (def current-board (move (user-input-move current-board  3) player current-board))
       (clear-terminal)
       (display-board current-board)
       (game-runner current-board "X"))
    (print-winner current-board))))

(defn -main []
  (def welcome "welcome to Dons tic tac toe")
  (game-runner  welcome ))



        