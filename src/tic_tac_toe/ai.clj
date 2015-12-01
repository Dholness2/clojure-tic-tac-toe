(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move]]
            [tic-tac-toe.game :refer  [game-depth winner?]]
             [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))

(def marker-1 "x")
(def marker-2 "o")
(def place-holder (atom {:player-marker marker-1 :ai-marker marker-2}))

(def max-score 1)
(def min-score -1)
(def draw-score 0)

(defn switch-markers [place-holder marker-1 marker-2]
 (swap! place-holder assoc (first(keys @place-holder)) marker-2 (last(keys @place-holder)) marker-1))

(defn score-game [board]
  (let [winner (winner? board)]
	  (cond
	   (= (@place-holder :ai-marker) winner ) (- 10 (game-depth board))
	   (= (@place-holder :player-marker) winner) (- (game-depth board) 10)
     :else 0)))

(defn space-available? [board position]
  (= empty-space (nth (flatten board) position)))

(defn possible-moves
  ([game]
    (possible-moves game 0 []))
  ([game iteration moves]
    (if (< iteration (board-size (game :board)))
      (if (space-available? (game :board) iteration)
        (possible-moves game  (+ 1 iteration) (conj moves (+ 1 iteration)))
        (possible-moves game  (+ 1 iteration) moves))
       moves)))

(defn best-score-index[scores  maximizing]
  (let [scores (vec scores)]
    (if maximizing
	    [(.indexOf scores (apply max scores)) (apply max scores)]
      [(.indexOf scores (apply min scores)) (apply min scores)])))

(defn possible-board [location marker current-board ]
  (move (matrix-convrt location 3) marker current-board))

(defn board-states [open-positions board marker]
  (map (fn [move] (possible-board move marker board)) open-positions))

(declare minimax)

(defn score [board maximizing open-positions player]
  (if maximizing
    (map (fn [board] (last (minimax board false))) (board-states open-positions board player))
    (map (fn [board] (last (minimax board true))) (board-states open-positions board player))))
(defn get-best-score-for [board maximizing]
  (let [open-positions (possible-moves board)]
    (if maximizing
      (best-score-index (score board maximizing open-positions (@place-holder :ai-marker)) maximizing)
      (best-score-index (score board maximizing open-positions (@place-holder :player-marker)) maximizing))))

(defn minimax [board maximizing]
  (if (winner? board)
     (let [score (score-game board)
           score-index 0 ]
       [score-index score])
    (get-best-score-for board maximizing)))

(defn ai-move [board]
  (if (= 0 (game-depth board))
    [0 0]
    (let [open-positions (possible-moves board)
          move-score (minimax board true)]
     (matrix-convrt (open-positions (first move-score)) 3))))

(defrecord AiPlayer[marker]
  PlayerProtocol
  (next-move [player board] (if-not (winner? board) (move (ai-move board) marker board))))














