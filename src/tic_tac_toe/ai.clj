(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move board-dimensions]]
            [tic-tac-toe.game :refer [game-depth winner]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))

(def moves-ahead 6)
(def move-depth 0)
(def draw-score 0)

(defn scoring-base [game]
  (+ 1 (board-size (:board game))))

(defn score-game [game depth]
  (let [current-winner (winner (game :board))]
    (cond
      (= (:ai-marker game) current-winner) (- (scoring-base game) depth)
      (= (:player-marker game) current-winner) (- depth (scoring-base game))
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

(defn best-score-index [scores maximizing]
  (let [scores (vec scores)]
    (if maximizing
     [(.indexOf scores (apply max scores)) (apply max scores)]
     [(.indexOf scores (apply min scores)) (apply min scores)])))

(defn possible-board [location marker current-board]
  (move (matrix-convrt location (board-dimensions current-board)) marker current-board))

(defn game-states [open-positions game marker]
<<<<<<< HEAD
  (let[ai (:ai-marker game)
       player (:player-marker game)
       board (:board game)]
    (map (fn [move] {:board (possible-board move marker board) :ai-marker ai :player-marker player}) open-positions)))

(declare minimax)

(defn alpha-max [game-results child]
  (let[beta (:beta game-results)
       alpha (:alpha game-results)
       current-value (:current-value game-results)
       depth (:depth game-results)
       scores (:scores game-results)]
    (if (<= beta alpha)
      (assoc game-results :scores (conj scores alpha))
      (let [score (last (minimax child false (inc depth) alpha beta))
            new-value (max current-value score)
            new-alpha (max alpha new-value)]
        (assoc game-results :current-value new-value :alpha new-alpha :scores (conj scores new-value))))))

(defn beta-min [game-results child]
  (let[beta (:beta game-results)
       alpha (:alpha game-results)
       current-value (:current-value game-results)
       depth (:depth game-results)
       scores (:scores game-results)]
    (if (<= beta alpha)
      (assoc game-results :scores (conj scores beta))
      (let [score (last (minimax child true (inc depth) alpha beta))
            new-value (min current-value score)
            new-beta (min beta new-value)]
        (assoc game-results :current-value new-value :beta new-beta :scores (conj scores new-value))))))

(defn get-scores [alpha-or-beta value alpha beta depth children]
  (:scores (reduce alpha-or-beta {:current-value value :alpha alpha :beta beta :depth depth :scores []} children)))
=======
  (map #(assoc game :board (possible-board % marker (:board game))) open-positions))

(declare minimax)

(defn find-alpha-beta [game-results optimal min-or-max maximizing child]
  (let [scores (:scores game-results)
        alpha (:alpha game-results)
        beta (:beta game-results)]
    (if (<= beta alpha)
      (assoc game-results :scores (conj scores optimal))
      (let [score (last (minimax child maximizing (inc (:depth game-results)) alpha beta))
            optimal-node-value (min-or-max (:current-value game-results) score)
            root-optimal (min-or-max optimal optimal-node-value)]
        (if (not maximizing)
          (assoc game-results :current-value optimal-node-value :alpha root-optimal :scores (conj scores optimal-node-value))
          (assoc game-results :current-value optimal-node-value :beta root-optimal :scores (conj scores optimal-node-value)))))))

(defn alpha-max [game-results child]
  (find-alpha-beta game-results (:alpha game-results) max false child))

(defn beta-min [game-results child]
  (find-alpha-beta game-results (:beta game-results) min true child))

(defn get-scores [alpha-or-beta optimal-node-value alpha beta depth children]
  (:scores (reduce alpha-or-beta {:current-value optimal-node-value :alpha alpha :beta beta :depth depth :scores []} children)))
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732

(defn score [game maximizing player open-positions depth alpha beta]
  (let [children (game-states open-positions game player)]
    (if maximizing
      (let [optimal-node-value -100]
        (get-scores alpha-max optimal-node-value alpha beta depth children))
      (let [optimal-node-value  100]
        (get-scores beta-min optimal-node-value alpha beta depth children)))))

(defn get-best-score-for [game maximizing depth alpha beta]
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing (:ai-marker game) open-positions depth alpha beta) maximizing)
      (best-score-index (score game maximizing (:player-marker game) open-positions depth alpha beta) maximizing))))

(def minimax
  (memoize (fn [game maximizing depth alpha beta]
             (if (or (winner (:board game)) (= moves-ahead depth))
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
  (if-not (winner (:board game))
    (assoc game :board (move (ai-move game) marker (:board game)))))

(defrecord AiPlayer [marker]
  PlayerProtocol
  (next-move [player game] (game-move game marker)))
