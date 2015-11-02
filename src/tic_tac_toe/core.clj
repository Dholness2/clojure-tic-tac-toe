(ns tic-tac-toe.core
  (:gen-class))
(require 'clojure.core.matrix)

  (defn create-empty-board []
	[["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])
   
  (defn move [location player board]
    (assoc-in board location player ))

  
  (defn validmove? [move]
    (if (and (<= move 9)(>=  move 1))
      true 
      false))

  (defn moveopen? [board move]
     (if(= "_" ((vec (flatten board)) (- move 1)))
     	true 
     	false))

  (defn matrix-convrt [move rowsize]
  	[ (quot ( - move 1) rowsize) (mod (- move 1) rowsize ) ])
  

  (defn user-input-move [board rowsize]
    (println "what is your next move")
    (def input (read-string(read-line)))
    (if(and (validmove? input) (moveopen? board input))
      (matrix-convrt input rowsize)
      (do (println "invalid selection") 
      	   (user-input-move board rowsize))))
  
  (defn computer-move [board rowsize]
  	(matrix-convrt (+ 1(.indexOf (flatten board) "_")) rowsize))

  (defn display-board [board]
  	(if(= false (empty? board))
  	  (let[ row ((vec(take 1 board))0)]
        (println (clojure.string/join row))
		(display-board (drop 1 board)))
  	    ))

  (defn check-equality [items] 
    (if (or (= "_" (some #{"_"} items) (>= (count(distinct items)) 2)))
      false
      true ))

  (defn row-check 
    ([board]
  	(let[ row (first (take 1 board)) ]
      (if(or (= "_" (some #{"_"} row)) (>= (count(distinct row)) 2)) 
        (row-check (drop 1 board))
        (row-check board (get row 0)))))
    ([board winner]
       winner))

 (defn column-check [board]
   (row-check (clojure.core.matrix/transpose board)))

 (defn get-location [board locations]
   (map (fn [location] (get-in board [location location])) locations))

 (defn get-diagnoals [board rowsize]
 	(let[diagonal-indexs-top  (vec(take rowsize (iterate inc 0))) 
 		 diagonal-indexs-bottom	(vec(take rowsize (iterate  dec (- rowsize 1))))]
      [(get-location board diagonal-indexs-top)  (get-location (vec (reverse board)) diagonal-indexs-bottom)]))

 (defn diagonal-check [board rowsize]
    (let [diagonal-top     ((get-diagnoals board rowsize) 0)
    	   diagonal-bottom ((get-diagnoals board rowsize) 1)]
    (if (= true (check-equality diagonal-top))  
    	(first diagonal-top) 
    	(if (= true (check-equality diagonal-bottom)) 
    	    (first diagonal-bottom)))))

 (defn  draw? [board]
  	(if-not(= "_" (some #{"_"} (flatten board)))
      true
      false))

(defn winner? [board]
 	(cond 
   	 (row-check board) (row-check board)
   	 (column-check board) (column-check board)
   	 
     (draw? board ) "its a draw"))

(defn clear-terminal[]
    (println "\033[2J"))

(defn print-winner [board]
  (if (= true (winner? board))
  	  println (winner? board)
  	  ))

(defn game-runner 
 ([welcome]
   (println welcome)
   (def current-board (create-empty-board))
   (display-board current-board )
   	(def current-board (move (user-input-move current-board 3) "X" current-board)) 
    (display-board current-board )
   (game-runner current-board "X"))
 ([board player]
	(def current-board (move (computer-move board 3) "O" board))
 	(clear-terminal)
 	(display-board current-board)
 	(println (winner? current-board))
    (def current-board (move (user-input-move current-board  3) player current-board))
    (clear-terminal)
 	(display-board current-board)
    (game-runner current-board "X")))

(defn -main []
   ; (def move-chart ["0" "1" "2" "3" "4" "5" "6" "7" "8"])
  (def welcome "welcome to Dons tic tac toe")
  (game-runner  welcome ))



 