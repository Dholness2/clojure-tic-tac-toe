(ns tic-tac-toe.input
  (:require [tic-tac-toe.protocol.input :refer :all]
	          [tic-tac-toe.board :refer [validmove? moveopen? matrix-convrt move]]
            [tic-tac-toe.game :refer [board-dimensions]]))

(defn prompt-terminal [question]
   (println question)
   (read-line))

(defn valid-selection [input board]
  (and (number? input) (validmove? input) (moveopen? board input)))

(defn user-marker []
  (let [selection  (prompt-terminal "Select your marker x or o ?")]
    (if  (or (= selection "o") (= selection "x"))
      selection
      (do (println "invalid selection")
      	   user-marker))))

(defn user-input-move [board]
 (let [input (read-string (prompt-terminal "what is your next move ?"))]
    (if (= true (valid-selection input board))
      (matrix-convrt input board-dimensions)
      (do (println "invalid selection")
      	   (user-input-move board board-dimensions)))))

(defrecord ConsoleInput []
  InputProtocol
  (get-move [input board] (user-input-move board))
  (get-marker [input] (user-marker)))
