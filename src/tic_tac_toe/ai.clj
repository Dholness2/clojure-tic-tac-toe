(ns tic-tac-toe.ai
  (:require [tic-tac-toe.board :refer [board-size move matrix-convrt empty-space move]]
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
  ([game iteration moves]
    (if (< iteration (board-size (:board game)))
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

(defn game-states [open-positions game marker]
  (map (fn [move]  {:board (possible-board move marker (:board game)) :ai-marker (:ai-marker game) :player-marker (:player-marker game)}) open-positions))

(declare minimax)

(defn score [game maximizing open-positions player]
  (if maximizing
    (map (fn [game] (last (minimax game false))) (game-states open-positions game  player))
    (map (fn [game] (last (minimax game true))) (game-states open-positions game player))))

(defn get-best-score-for [game maximizing]
  (let [open-positions (possible-moves game)]
    (if maximizing
      (best-score-index (score game maximizing open-positions (:ai-marker game)) maximizing)
      (best-score-index (score game maximizing open-positions (:player-marker game)) maximizing))))

(defn minimax [game maximizing]
  (if (winner? (:board game))
    (let [score (score-game game)
          score-index 0]
      [score-index score])
    (get-best-score-for game maximizing)))

(defn ai-move [game]
  (if (= 0 (game-depth (:board game)))
    [0 0]
    (let [open-positions (possible-moves game)
          move-score (minimax game true)]
      (matrix-convrt (open-positions (first move-score)) 3))))

(defrecord AiPlayer[marker]
  PlayerProtocol
  (next-move [player game] (if-not (winner? (:board game)) (assoc game :board (move (ai-move game) marker (:board game))))))














