(ns tic-tac-toe.display)


(defn clear-terminal[]
  (print "\033[2J"))

  (defn display-index  [moves]
    println (apply str (moves 0))
    (display-index (drop 1 moves)))

  (defn display-board [board]
    (if (= false (empty? board))
      (let[ row ((vec(take 1 board))0)]
        (println (clojure.string/join row))
        (display-board (drop 1 board)))))
