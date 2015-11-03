(ns tic-tac-toe.board)
 (require 'clojure.core.matrix)
(defn create-empty-board [rows cols]
    (vec (take rows (repeat (vec (take cols (repeat "_")))))))
  
  (defn create-move-index [rows cols]
  	 (take rows (partition cols (iterate inc 1))))
    
   (defn print-move-index [moves]
   	 println (apply str (nth moves 0))
   	 (print-move-index (drop 1 moves)))

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
  (defn check-equality [items] 
    (if (or (= "_" (some #{"_"} items)) (>= (count(distinct items)) 2))
      false
      true ))

  (defn row-check 
    ([board]
  	(let[ row (first (take 1 board))]
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
    (if (check-equality diagonal-top)  
    	(first diagonal-top) 
    	(if (check-equality diagonal-bottom) 
    	    (first diagonal-bottom)))))

 (defn  draw? [board]
  	(if-not(= "_" (some #{"_"} (flatten board)))
      true
      false))

(defn winner? [board]
 	(cond 
   	 (row-check board) (row-check board)
   	 (column-check board) (column-check board)
   	 (diagonal-check board 3) (diagonal-check board 3)
     (draw? board ) "its a draw"))

(defn print-winner [board]
  (if (winner? board)
    (println (str "Game Winner: " (winner? board)))))
