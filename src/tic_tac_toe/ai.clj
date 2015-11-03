(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer :all]))

(defn board-size [board]
 (* (count board ) (count(first board))))

(defn empty-spaces [board]
  ((frequencies (flatten board)) "_"))
         

(defn game-depth [board] 
  (- (board-size board) (empty-spaces board)))

(defn score-game [board]
	(cond 
	 (= "o" (winner? board)) (- 10 (game-depth board))
	 (= "x"  (winner? board)) (- (game-depth board) 10)
	  :else 0))

; (defn possible-moves (board iteration moves)
;   (if (and (= iteration (- (board-size board) 1)) (= "_" (nth board iteration)))
;    (possible-moves board (+ 1 iteration) (conj moves (nth board iteration))) 

 





