 (ns tic-tac-toe.human
  (:gen-class)
(require [tic-tac-toe.board :refer :all]
         [tic-tac-toe.ai :refer :all]
         [tic-tac-toe.display :refer :all]
         [tic-tac-toe.game :refer :all]
         [tic-tac-toe.inputoutput :refer :all]
         [tic-tac-toe.protocol.player :refer :all]))

(defn valid-selection [input board]
  (and (number? input) (validmove? input) (moveopen? board input)))

(defn user-input-move [board ]
  (send-output "what is your next move")
  (let [input (read-string(get-input))]
    (if (= true (valid-selection input board))
      (matrix-convrt input board-dimensions)
      (do (println "invalid selection")
      	   (user-input-move board board-dimensions)))))

(defn human-move [board marker]
   (move (user-input-move board) marker board))

(defrecord HumanPlayer[marker]  
  PlayerProtocol 
    (next-move [player board] (human-move board marker)))