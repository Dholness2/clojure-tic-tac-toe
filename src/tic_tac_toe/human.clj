(ns tic-tac-toe.human
  (:gen-class)
(require [tic-tac-toe.board :refer :all]
         [tic-tac-toe.ai :refer :all]
         [tic-tac-toe.display :refer :all]
         [tic-tac-toe.game :refer :all]))


(defn valid-selection [input board]
  (and (number? input) (validmove? input) (moveopen? board input)))

(defn user-input-move [board rowsize]
  (println "what is your next move")
  (def input (read-string(read-line)))
  (if (= true (valid-selection input board))
      (matrix-convrt input rowsize)
      (do (println "invalid selection")
      	   (user-input-move board rowsize))))
