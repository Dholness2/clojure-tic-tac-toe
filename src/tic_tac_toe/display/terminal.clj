(ns tic-tac-toe.display.terminal
(:require [tic-tac-toe.board :refer :all]
          [tic-tac-toe.game :refer :all]
          [tic-tac-toe.protocol.display :refer [DisplayProtocol]]))

(defn clear-terminal[]
  (println "\033[2J"))

(defn display-index [board]
  (let [game-index (partition (count board) (range 1 (+ 1 (board-size board))))]
    (println "Game Index")
    (doseq [row game-index] (println (apply str row)))))

(defn display-board [board]
  (if (= false (empty? board))
  (let[ row ((vec(take 1 board))0)]
    (println (clojure.string/join row))
  (display-board (drop 1 board)))))

(defn print-winner [board]
  (println (str "Game Winner: " (winner? board))))

(defn display-iteration [board]
  (clear-terminal)
  (display-index board)
  (display-board board))

(defrecord TerminalDisplay []
  DisplayProtocol
  (display-state [display board] (display-iteration board))
  (display-winner [display board] (print-winner board)))




