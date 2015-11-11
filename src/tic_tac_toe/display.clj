(ns tic-tac-toe.display
(:require [tic-tac-toe.board :refer :all]
          [tic-tac-toe.game :refer :all]
          [tic-tac-toe.inputoutput :refer :all]))

(defn clear-terminal[]
  (send-output "\033[2J"))

(defn display-index [board]
  (let [game-index (partition (count board) (range 1 (+ 1 (board-size board))))]
    (send-output"Game Index")
    (doseq [row game-index] (send-output (apply str row)))))

(defn display-board [board]
  (if (= false (empty? board))
  (let[ row ((vec(take 1 board))0)]
    (send-output (clojure.string/join row))
  (display-board (drop 1 board)))))

(defn print-winner [board]
  (if (winner? board)
    (send-output (str "Game Winner: " (winner? board)))))

(defn display-iteration [board]
  (clear-terminal)
  (display-index board)
  (display-board board))




