(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move]]
            [tic-tac-toe.game :refer  [game-depth  player1-marker player2-marker winner?]]
             [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))

(defn score-game [board]
	(cond
	 (= player2-marker (winner? board)) (- 10 (game-depth board))
	 (= player1-marker (winner? board)) (- (game-depth board) 10)
	  :else 0))

(defn possible-moves [board iteration moves]
  (if (< iteration (board-size board))
      (do (if (= empty-space (nth (flatten board) iteration))
             (possible-moves board  (+ 1 iteration) (conj moves (+ 1 iteration)))
             (possible-moves board  (+ 1 iteration) moves)))
       moves))

(defn best-score-index[scores  maximizing]
  (if maximizing
	  [(.indexOf scores (apply max scores)) (apply max scores)]
    [(.indexOf scores (apply min scores)) (apply min scores)]))

(defn possible-board [location marker current-board ]
  (move (matrix-convrt location 3) marker current-board))

(defn board-states [open-positions board marker]
  (map (fn [move] (possible-board move marker board )) open-positions))

(defn minimax [board maximizing]
  (if (or (winner? board) (= 1 (game-depth board)))
     (let [score (score-game board)
           score-index 0 ]
       [score-index score])
    (let [open-positions (possible-moves board 0 [] )]
      (if  maximizing
        (best-score-index(vec (map (fn [board] (last (minimax board false))) (board-states open-positions board player2-marker))) maximizing)
        (best-score-index(vec (map (fn [board] (last (minimax board true))) (board-states open-positions board player1-marker))) maximizing)))))

(defn ai-move [board]
  (let [open-positions (possible-moves board 0 [])
        move-score (minimax board true)]
   (if-not (empty? open-positions)
     (matrix-convrt (open-positions (first move-score)) 3))))

(defrecord AiPlayer[marker]
  PlayerProtocol
    (next-move [player board] (move (ai-move board) marker board)))














