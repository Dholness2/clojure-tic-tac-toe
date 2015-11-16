(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer [winner? draw?]]))

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

(defn best-score-index[scores  maximizing]
  (if ( = true maximizing)
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

(defn game-over? [board]
  (or (winner? board) (= 8 (game-depth board))))

(defn minimax [board maximizing]
  (if (game-over? board)
    [-1 (score-game board)]
    (let [open-positions (possible-moves board 0 [] )]
      (if (= true maximizing)
        (best-score-index(vec (map (fn [board] (last (minimax board false))) (board-states open-positions board "o"))) maximizing)
        (best-score-index(vec (map (fn [board] (last (minimax board true))) (board-states open-positions board "x"))) maximizing)))))

(defn ai-move [board]
  (let [open-positions (possible-moves board 0 [] )
        move-score    (minimax board true)]
    (matrix-convrt  (open-positions (first move-score)) 3)))

















 

