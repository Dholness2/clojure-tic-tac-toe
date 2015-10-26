(ns tic-tac-toe.core
  (:gen-class))



(defn create-board 
  [columns rows]
   )

(defn move 
  [location player board]
  (assoc board (keyword location) player ))

(defn user-input-move []
	(println "what is your next move")
	(read-line))

(defn display-board [board]
 (println (str (vals board)))

)