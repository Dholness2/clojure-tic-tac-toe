(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move board-diemensions]]
            [tic-tac-toe.game :refer [game-depth winner?]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol]]))

(def moves-ahead 9)
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
  (move (matrix-convrt location (board-diemensions current-board)) marker current-board))

(defn game-states [open-positions game marker]
  (map (fn [move] {:board (possible-board move marker (:board game)) :ai-marker (:ai-marker game) :player-marker (:player-marker game)}) open-positions))

(declare minimax)

<<<<<<< 49f2aea8e3c70b50941b5d2a591d6c046481beca
(defn get-score-for-gamestate [game maximizing depth]
  (last (minimax game (not maximizing) (+ depth 1))))

(defn score [game maximizing open-positions player depth]
    (map #(get-score-for-gamestate % maximizing depth) (game-states open-positions game player)))

(defn get-best-score-for [game maximizing depth]
=======
(defn alpha_max [game-state alpha beta depth] 
  (if (>= @alpha @beta )
    @alpha
    (let [value (last (minimax game-state false (inc depth) @alpha @beta))]
      (reset! alpha (max value @alpha))
       value)))

(defn beta_min [game-state alpha beta depth]
  (if (>= @alpha @beta)
    @beta
    (let [value (last (minimax game-state true (inc depth) @alpha @beta))]
        (reset! beta (min value @beta))
         value)))

(defn score [game maximizing player open-positions depth alpha beta]
  (let [children (game-states open-positions game player)
        a (atom alpha)
        b (atom beta)]
   (if maximizing 
      (map #(alpha_max % a  b depth) children))
      (map #(beta_min %  a  b depth) children)))

(defn get-best-score-for [game maximizing depth alpha beta]
>>>>>>> wip
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing (:ai-marker game) open-positions depth alpha beta) maximizing)
      (best-score-index (score game maximizing (:player-marker game) open-positions depth alpha beta) maximizing))))
(def minimax
  (memoize (fn [game maximizing depth alpha beta]
    (if (or (winner? (:board game)) (= 9 depth))
      (let [score (score-game game depth)
            score-index 0]
       [score-index score])
    (get-best-score-for game maximizing depth alpha beta)))))

(defn ai-move [game]
 (let [open-positions (possible-moves game)
        move-score (minimax game true 0 -100 100)
        board-diemension (board-diemensions (:board game))]
    (matrix-convrt (open-positions (first move-score)) board-diemension)))

(defn game-move [game marker]
  (if-not (winner? (:board game))
    (assoc game :board (move (ai-move game) marker (:board game)))))

(defrecord AiPlayer[marker]
  PlayerProtocol
  (next-move [player game] (game-move game marker)))
