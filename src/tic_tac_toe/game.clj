(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer [get-diagnoals check-equality empty-space empty-spaces board-size]]
     [clojure.core.matrix :refer [transpose]]))

(def player1-marker)
(def player2-marker "o")
(def board-dimensions 3)

(defn row-check
  ([board]
    (let[ row (first (take 1 board))]
      (if (or (= empty-space (some #{empty-space} row)) (>= (count(distinct row)) 2))
        (row-check (drop 1 board))
        (row-check board (get row 0)))))
  ([board winner]
    winner))

(defn column-check [board]
  (row-check (transpose board)))

(defn diagonal-check [board rowsize]
  (let [diagonal-top     ((get-diagnoals board rowsize) 0)
        diagonal-bottom ((get-diagnoals board rowsize) 1)]
    (if (check-equality diagonal-top)
      (first diagonal-top)
      (if (check-equality diagonal-bottom)
        (first diagonal-bottom)))))

(defn  draw? [board]
  (if-not(= empty-space (some #{empty-space} (flatten board)))
    true
    false))

(defn winner? [board]
  (cond
    (row-check board) (row-check board)
    (column-check board) (column-check board)
    (diagonal-check board board-dimensions) (diagonal-check board board-dimensions)
    (draw? board ) "its a draw"))

(defn game-depth [board]
  (- (board-size board) (empty-spaces board)))
