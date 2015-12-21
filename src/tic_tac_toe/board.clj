(ns tic-tac-toe.board
  (:require [clojure.core.matrix :refer [transpose]]))

(def empty-space "_")

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
  (and (<= move board-size) (>=  move 1)))

(defn moveopen? [board move]
  (= "_" ((vec (flatten board)) (- move 1))))

(defn matrix-convrt [move rowsize]
  [(quot (- move 1) rowsize) (mod (- move 1) rowsize)])

(defn check-equality [items]
 (not (or (= empty-space (some #{empty-space} items)) (>= (count(distinct items)) 2))))

(defn get-location [board locations]
  (map (fn [location] (get-in board [location location])) locations))

(defn get-diagnoals [board rowsize]
  (let [diagonal-indexs-top (vec (take rowsize (iterate inc 0)))
       diagonal-indexs-bottom (vec (take rowsize (iterate dec (- rowsize 1))))]
    [(get-location board diagonal-indexs-top) (get-location (vec (reverse board)) diagonal-indexs-bottom)]))
