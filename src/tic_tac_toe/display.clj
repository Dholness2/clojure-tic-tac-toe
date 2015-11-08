(ns tic-tac-toe.display
(:require [tic-tac-toe.board :refer :all]))

(defn clear-terminal[]
  (print "\033[2J"))

(defn display-index [board]
    (let [game-index (partition (count board) (range 1 (+ 1 (board-size board))))]
      (println "Game Index")
      (doseq [row game-index] (println (apply str row)))

      ))


  (defn display-board [board]
   (if (= false (empty? board))
      (let[ row ((vec(take 1 board))0)]
        (println (clojure.string/join row))
        (display-board (drop 1 board)))))




