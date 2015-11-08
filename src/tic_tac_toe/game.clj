(ns tic-tac-toe.game
  (:require [tic-tac-toe.board :refer :all]))

(defn  draw? [board]
    (if-not(= "_" (some #{"_"} (flatten board)))
      true
      false))

(defn winner? [board]
  (cond
     (row-check board) (row-check board)
     (column-check board) (column-check board)
     (diagonal-check board 3) (diagonal-check board 3)
     (draw? board ) "its a draw"))

(defn print-winner [board]
  (if (winner? board)
    (println (str "Game Winner: " (winner? board)))))

(defn game-depth [board]
  (- (board-size board) (empty-spaces board)))
