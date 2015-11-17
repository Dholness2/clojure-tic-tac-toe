(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer  :all]
            ))

(defn score-game [board]
	(cond
	 (= player2-marker (winner? board)) (- 10 (game-depth board))
	 (= player1-marker (winner? board)) (- (game-depth board) 10)
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

(defn best-score-index[scores  maximizing]
  (if maximizing
	  (let [ max-score-index  (find-max-index scores)]
	      [ max-score-index (scores max-score-index)])
    (let [ min-score-index  (find-min-index scores)]
	      [min-score-index  (scores min-score-index)])))

(defn possible-board [location marker current-board ]
  (move (matrix-convrt location 3) marker current-board))

(defn board-states [open-positions board marker]
  (map (fn [move] (possible-board move marker board )) open-positions))

(defn score-board-states [board-states]
    (map score-game board-states ))

(defn minimax [board maximizing]
  (if (winner? board) 
    [0 (score-game board)]
  (let [open-positions (possible-moves board 0 [] )]
    (if  maximizing
      (best-score-index(vec (map (fn [board] (last (minimax board false))) (board-states open-positions board player2-marker))) maximizing)
      (best-score-index(vec (map (fn [board] (last (minimax board true))) (board-states open-positions board player1-marker))) maximizing)))))

(defn ai-move [board]
  (let [open-positions (possible-moves board 0 [] )
        move-score    (minimax board true)]
   (if-not (empty? open-positions)    
     (matrix-convrt  (open-positions (first move-score)) 3 ))))

















 

