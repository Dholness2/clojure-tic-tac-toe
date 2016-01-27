(ns tic-tac-toe.board
  (:require [clojure.core.matrix :refer [transpose]]))

(def empty-space "_")

(def markers-count 2)
(def lowest-move 1)
(def starting-index 0)

(defn create-empty-board [dimension]
  (vec (take dimension (repeat (vec (take dimension (repeat empty-space)))))))

(defn board-dimensions [board]
  (count board))

(defn board-size [board]
  (* (count board) (count (first board))))

(defn empty-space-count [board]
  (count (filter #(= empty-space %) (flatten board))))

(defn move [location player board]
  (assoc-in board location player))

(defn validmove? [move board-size]
  (and (<= move board-size) (>= move lowest-move)))

(defn moveopen? [board move]
  (= empty-space ((vec (flatten board)) (dec move))))

(defn matrix-convrt [move rowsize]
  [(quot (dec move) rowsize) (mod (dec move) rowsize)])

(defn check-equality [items]
  (not (or (= empty-space (some #{empty-space} items)) (>= (count (distinct items)) markers-count))))

(defn get-location [board locations]
  (map (fn [location] (get-in board [location location])) locations))

(defn get-diagnoals [board rowsize]
  (let [diagonal-indexs-top (vec (take rowsize (iterate inc starting-index)))
        diagonal-indexs-bottom (vec (take rowsize (iterate dec (dec rowsize))))]
    [(get-location board diagonal-indexs-top) (get-location (vec (reverse board)) diagonal-indexs-bottom)]))
