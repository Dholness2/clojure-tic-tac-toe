(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move board-diemensions]]
            [tic-tac-toe.game :refer  [game-depth winner?]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))


(defn scoring-base [game]
  (+ 1 (board-size (:board game))))

(defn score-game [game]
  (let [winner (winner? (game :board))]
    (cond
      (= (:ai-marker game) winner) (- (scoring-base game) (game-depth (:board game)))
      (= (:player-marker game) winner) (- (game-depth (:board game)) (scoring-base game))
      :else 0)))

(defn space-available? [board position]
  (= empty-space (nth (flatten board) position)))

(defn possible-moves
  ([game]
    (possible-moves game 0 []))
  ([game move-index moves]
    (if (< move-index (board-size (:board game)))
      (let [next-move-index (+ 1 move-index)]
        (if (space-available? (:board game) move-index)
          (possible-moves game next-move-index (conj moves next-move-index))
          (possible-moves game next-move-index moves)))
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

(defn get-score-for-gamestate [game maximizing depth]
  (last (minimax game (not maximizing) (+ depth 1))))

(defn score [game maximizing open-positions player depth]
  (map
    #(get-score-for-gamestate % maximizing depth)
    (game-states open-positions game player)))

(defn get-best-score-for [game maximizing depth]
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing open-positions (:ai-marker game) depth) maximizing)
      (best-score-index (score game maximizing open-positions (:player-marker game) depth) maximizing))))

(def minimax
  (memoize (fn [game maximizing depth]
    (if (or (winner? (:board game)) (= 9 depth))
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
