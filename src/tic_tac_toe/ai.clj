(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move board-diemensions]]
            [tic-tac-toe.game :refer  [game-depth winner?]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))


(defn score-game [game]
  (let [winner (winner? (game :board))]
	  (cond
	    (= (:ai-marker game) winner) (- 10 (game-depth (:board game)))
	    (= (:player-marker game) winner) (- (game-depth (:board game)) 10)
      :else 0)))

(defn space-available? [board position]
  (= empty-space (nth (flatten board) position)))

(defn possible-moves
  ([game]
    (possible-moves game 0 []))
  ([game move-index moves]
    (if (< move-index (board-size (:board game)))
      (if (space-available? (game :board) move-index)
        (possible-moves game  (+ 1 move-index) (conj moves (+ 1 move-index)))
        (possible-moves game  (+ 1 move-index) moves))
    moves)))

(defn best-score-index[scores maximizing]
  (let [scores (vec scores)]
    (if maximizing
	    [(.indexOf scores (apply max scores)) (apply max scores)]
      [(.indexOf scores (apply min scores)) (apply min scores)])))

(defn possible-board [location marker current-board ]
  (move (matrix-convrt location (board-diemensions current-board)) marker current-board))

(defn game-states [open-positions game marker]
  (map (fn [move]  {:board (possible-board move marker (:board game)) :ai-marker (:ai-marker game) :player-marker (:player-marker game)}) open-positions))

(declare minimax)

(defn score [game maximizing open-positions player depth]
  (if maximizing
    (map (fn [game] (last (minimax game false (+ depth 1)))) (game-states open-positions game player))
    (map (fn [game] (last (minimax game true (+ depth 1)))) (game-states open-positions game player))))

(defn get-best-score-for [game maximizing depth]
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing open-positions (:ai-marker game) depth) maximizing)
      (best-score-index (score game maximizing open-positions (:player-marker game) depth) maximizing))))

(def minimax
  (memoize (fn [game maximizing depth]
  (if (or (winner? (:board game)) (= 8 depth))
    (let [score (score-game game)
          score-index 0]
      [score-index score])
    (get-best-score-for game maximizing depth)))))


(defn move-state-default? [game]
  (or (= 0 (game-depth (:board game))) (= 1 (game-depth (:board game)))))

(defn ai-move [game]
  (if (move-state-default? game)
    (matrix-convrt (first (possible-moves game)) (board-diemensions (:board game)))
  (let [open-positions (possible-moves game)
        move-score (minimax game true 0)
        board-diemension (board-diemensions (:board game))]
    (matrix-convrt (open-positions (first move-score)) board-diemension))))

(defn game-move [game marker]
  (if-not (winner? (:board game))
    (assoc game :board (move (ai-move game) marker (:board game)))))

(defrecord AiPlayer[marker]
  PlayerProtocol
  (next-move [player game] (game-move game marker)))