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

(defn possible-moves [board iteration moves]
  (if (< iteration (board-size board))
      (do (if (= "_" (nth (flatten board) iteration))
             (possible-moves board  (+ 1 iteration) (conj moves (+ 1 iteration))) 
             (possible-moves board  (+ 1 iteration) moves)))
       moves))


(defn best-move [scores moves maximizing]
	(if ( = true maximizing)
	 (do (let [ max-score-index  (first(last (sort-by second (map-indexed vector scores))))]	
	       [(moves max-score-index) (scores max-score-index)]))
	 (do (let [min-score-index (first(first (sort-by second (map-indexed vector scores))))]	
	       [(moves min-score-index) (scores min-score-index)]))))
      
 
(defn minimax [board maximizing] 
  (if (or (winner? board) (= 8 (game-depth board)))
      (score-game board)
      (let [ scores  [ ]  
      			     moves	[ ]		
      		    available-moves    (possible-moves board 0 [])]
        (doseq [location available-moves]  
        	(if (= true maximizing)
        		(conj scores ((minimax (move (matrix-convrt location 3) "o" board) false) 0))
        		(conj scores ((minimax (move (matrix-convrt location 3) "x" board) true) 0)))
          (conj moves location)) 
        (best-move scores moves maximizing))))










