(ns tic-tac-toe.display.terminal
  (:require [tic-tac-toe.board :refer [board-size board-dimensions]]
            [tic-tac-toe.game :refer [winner]]
            [tic-tac-toe.protocol.display :refer [DisplayProtocol]]))

(def base-index 0)

(def empty-marker "_")
(def marker-a "x")
(def marker-b "o")

(def left-column "| ")
(def small-right-column " |")
(def large-right-column "  |")
(def large-row-index 9)

(defn clear-terminal[]
  (println "\033[2J"))

(defn print-message [message]
  (println message))

(defn empty-marker? [marker]
  (or (= marker empty-marker) (not (or (= marker-a marker)(= marker-b marker)))))

(defn add-column [index item]
  (let[position (inc index)
       marker item]
    (if (and (empty-marker? marker) (< large-row-index position))
       (str position small-right-column)
       (if (empty-marker? marker)
          (str position large-right-column)
          (str marker large-right-column)))))

(defn index-board [board]
  (partition (board-dimensions board) (map-indexed add-column (flatten board))))

(defn row-print [row]
  (println (str left-column (clojure.string/join " " row))))

(defn display-board [board]
  (if (false? (empty? board))
    (let[row (first board)]
      (row-print row)
    (display-board (rest board)))))

(defn print-winner [board]
  (println (str "Game Winner: " (winner board))))

(defn display-iteration [board]
  (clear-terminal)
  (display-board (index-board board)))

(defrecord TerminalDisplay []
  DisplayProtocol
  (display-state [display board] (display-iteration board))
  (display-winner [display board] (print-winner board)))
