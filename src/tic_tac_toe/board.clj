(ns tic-tac-toe.board)
(require 'clojure.core.matrix)

(def empty-space "_")

(defn create-empty-board [diemension]
  (vec (take diemension (repeat (vec (take diemension (repeat empty-space)))))))

(defn board-size [board]
  (* (count board) (count (first board))))

(defn empty-spaces [board]
  (if ((frequencies (flatten board)) empty-space)
     ((frequencies (flatten board)) empty-space)
      0))

(defn move [location player board]
  (assoc-in board location player))

(defn validmove? [move]
  (and (<= move 9) (>=  move 1)))

(defn moveopen? [board move]
  (= "_" ((vec (flatten board)) (- move 1))))

(defn matrix-convrt [move rowsize]
  [(quot (- move 1) rowsize) (mod (- move 1) rowsize)])

(defn check-equality [items]
  (if (or (= empty-space (some #{empty-space} items)) (>= (count(distinct items)) 2))
    false
    true))

(defn row-check
  ([board]
    (let[ row (first (take 1 board))]
      (if (or (= empty-space (some #{empty-space} row)) (>= (count(distinct row)) 2))
        (row-check (drop 1 board))
        (row-check board (get row 0)))))
  ([board winner]
    winner))

(defn column-check [board]
  (row-check (clojure.core.matrix/transpose board)))

(defn get-location [board locations]
  (map (fn [location] (get-in board [location location])) locations))

(defn get-diagnoals [board rowsize]
  (let[diagonal-indexs-top  (vec(take rowsize (iterate inc 0)))
 	     diagonal-indexs-bottom	(vec(take rowsize (iterate  dec (- rowsize 1))))]
      [(get-location board diagonal-indexs-top)  (get-location (vec (reverse board)) diagonal-indexs-bottom)]))

(defn diagonal-check [board rowsize]
  (let [diagonal-top     ((get-diagnoals board rowsize) 0)
    	  diagonal-bottom ((get-diagnoals board rowsize) 1)]
    (if (check-equality diagonal-top)
      (first diagonal-top)
      (if (check-equality diagonal-bottom)
    	  (first diagonal-bottom)))))

