(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer :all]))

(def player1-marker "x")
(def player2-marker "o")
(def board-dimensions 3 )


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
