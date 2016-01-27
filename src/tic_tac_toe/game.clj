(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [get-diagnoals check-equality empty-space empty-space-count board-size board-dimensions]]
            [clojure.core.matrix :refer [transpose]]))

(def draw "it's a draw")
(def one-vector 1)

(defn row-check
  ([board]
    (let [row (first board)]
      (if (not (check-equality row))
         (row-check (drop one-vector board))
         (row-check board (first row)))))
  ([board winner]
    winner))

(defn column-check [board]
  (row-check (transpose board)))

(defn diagonal-check [board rowsize]
  (let [diagonal-top (first (get-diagnoals board rowsize))
        diagonal-bottom (last (get-diagnoals board rowsize))]
    (if (check-equality diagonal-top)
      (first diagonal-top)
      (if (check-equality diagonal-bottom)
        (first diagonal-bottom)))))

(defn draw? [board]
  (not (= empty-space (some #{empty-space} (flatten board)))))

(defn winner [board]
  (cond
    (row-check board) (row-check board)
    (column-check board) (column-check board)
    (diagonal-check board (board-dimensions board)) (diagonal-check board (board-dimensions board))
    (draw? board) draw))

(defn game-depth [board]
  (- (board-size board) (empty-space-count board)))
