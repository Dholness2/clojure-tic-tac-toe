(ns tic-tac-toe.display.terminal
  (:require [tic-tac-toe.board :refer [board-size]]
            [tic-tac-toe.game :refer [winner?]]
            [tic-tac-toe.protocol.display :refer [DisplayProtocol]]))

(def base-index 0)

(def marker-a "x")
(def marker-b "o")

(def left-column "| ")
(def small-right-column " |")
(def large-right-column "  |")

(defn clear-terminal[]
  (println "\033[2J"))

(defn print-message [message]
  (println message))

(defn empty-position [element]
  (or (= element "_") (not (or (= marker-a element)(= marker-b element)))))

(defn add-column [indx item]
  (let[position (inc indx)
       element item]
    (if (and (empty-position element) (< 9 position))
       (str position small-right-column)
       (if (empty-position element)
          (str position large-right-column)
          (str element large-right-column)))))

(defn index-board [board]
  (partition (int (Math/sqrt (board-size board))) (map-indexed add-column (flatten board))))

(defn get-first-row [board]
  ((vec (take 1 board)) 0))

(defn display-board [board indx]
  (if (= false (empty? board))
    (let[row (get-first-row board)]
      (println (str left-column (clojure.string/join " " row)))
    (display-board (drop 1 board) (inc indx)))))

(defn print-winner [board]
  (println (str "Game Winner: " (winner? board))))

(defn display-iteration [board]
  (clear-terminal)
  (display-board (index-board board) base-index))

(defrecord TerminalDisplay []
  DisplayProtocol
  (display-state [display board] (display-iteration board))
  (display-winner [display board] (print-winner board)))