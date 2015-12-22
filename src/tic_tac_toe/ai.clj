(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move board-dimensions]]
            [tic-tac-toe.game :refer [game-depth winner?]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))

(def moves-ahead 6)
(def move-depth 0)
(def draw-score 0)

(defn scoring-base [game]
  (+ 1 (board-size (:board game))))

(defn score-game [game depth]
  (let [winner (winner? (game :board))]
    (cond
      (= (:ai-marker game) winner) (- (scoring-base game) depth)
      (= (:player-marker game) winner) (- depth (scoring-base game))
      :else draw-score)))

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

(defn possible-board [location marker current-board]
  (move (matrix-convrt location (board-dimensions current-board)) marker current-board))

(defn game-states [open-positions game marker]
  (map (fn [move] {:board (possible-board move marker (:board game)) :ai-marker (:ai-marker game) :player-marker (:player-marker game)}) open-positions))

(declare minimax)

(defn alpha_max [game-results child]
  (if (<= (:beta game-results) (:alpha game-results))
    (assoc game-results :scores (conj (:scores game-results) (:alpha game-results)))
    (let [new-value (max (:current-value game-results) (last (minimax child false (inc (:depth game-results)) (:alpha game-results) (:beta game-results))))
         new-alpha (max (:alpha game-results) new-value)]
      (assoc game-results :current-value new-value :alpha new-alpha :scores (conj (:scores game-results) new-value)))))

(defn beta_min [game-results child]
  (if (<= (:beta game-results) (:alpha game-results))
    (assoc game-results :scores (conj (:scores game-results) (:beta game-results)))
    (let [new-value (min (:current-value game-results) (last (minimax child true (inc (:depth game-results)) (:alpha game-results) (:beta game-results))))
         new-beta (min (:beta game-results) new-value)]
      (assoc game-results :current-value new-value :beta new-beta :scores (conj (:scores game-results) new-value)))))

(defn score [game maximizing player open-positions depth alpha beta]
  (let [children (game-states open-positions game player)]
    (if maximizing
      (let [value -100]
        (:scores (reduce alpha_max {:current-value value :alpha alpha :beta beta :depth depth :scores []} children)))
      (let [value 100]
        (:scores (reduce beta_min {:current-value value :alpha alpha :beta beta  :depth depth :scores []} children))))))

(defn get-best-score-for [game maximizing depth alpha beta]
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing (:ai-marker game) open-positions depth alpha beta) maximizing)
      (best-score-index (score game maximizing (:player-marker game) open-positions depth alpha beta) maximizing))))

(def minimax
  (memoize (fn [game maximizing depth alpha beta]
    (if (or (winner? (:board game)) (= moves-ahead depth))
      (let [score (score-game game depth)
            score-index 0]
       [score-index score])
    (get-best-score-for game maximizing depth alpha beta)))))

(defn ai-move [game]
 (let [open-positions (possible-moves game)
       move-score (minimax game true 0 -100 100)
       board-dimension (board-dimensions (:board game))]
    (matrix-convrt (open-positions (first move-score)) board-dimension)))

(defn game-move [game marker]
  (if-not (winner? (:board game))
    (assoc game :board (move (ai-move game) marker (:board game)))))

(defrecord AiPlayer[marker]
  PlayerProtocol
  (next-move [player game] (game-move game marker)))
