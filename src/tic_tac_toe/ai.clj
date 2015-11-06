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

(defn find-max-index [collection] 
  (first (last (sort-by second (map-indexed vector collection)))))

(defn find-min-index [collection] 
  (first (first (sort-by second (map-indexed vector collection)))))

(defn best-move [scores moves maximizing]
  (if ( = true maximizing)
	  (let [ max-score-index  (find-max-index scores)]	
	      [(moves max-score-index) (scores max-score-index)])
	  (let [ min-score-index  (find-min-index scores)]	
	      [(moves min-score-index) (scores min-score-index)])))

(defn possible-board [location marker current-board ]
  (move (matrix-convrt location 3) marker current-board))

(defn game-over? [board]
  (or (winner? board) (= 8 (game-depth board))))
      
 (defn minimax [board maximizing] 
   (if (game-over? board)
     (score-game board)
     (let [ available-moves (possible-moves board 0 [])]
          (if (= true maximizing)      
             (best-move (vec (map (fn [location] (last (minimax (possible-board location "o" board) false))) available-moves)) available-moves maximizing)
             (best-move (vec (map (fn [location] (last (minimax (possible-board location "x" board) true)))  available-moves)) available-moves maximizing)))))











      








