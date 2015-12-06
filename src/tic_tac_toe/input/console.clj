(ns tic-tac-toe.input.console
  (:require [tic-tac-toe.protocol.input :refer :all]
	          [tic-tac-toe.board :refer [validmove? moveopen? matrix-convrt move board-diemensions board-size]]
           [tic-tac-toe.display.terminal :refer [print-message]]))

(defn prompt-terminal [question]
   (print-message question)
  (let [response  (read-line)]
    (if (clojure.string/blank? response)
      (prompt-terminal question)
      response)))

(defn valid-selection [input board]
  (and (number? input) (validmove? input (board-size board)) (moveopen? board input)))

(defn user-marker []
  (let [selection  (prompt-terminal "Select your marker x or o ?")]
    (if  (or (= 0 (compare selection "o")) (= 0 (compare selection "x")))
      selection
      (do (print-message "invalid selection")
      	   (user-marker)))))

(defn get-board-diemension []
  (let [selection (read-string(prompt-terminal "What size board do you want? x by x (provide one number for x)"))]
    (if (number? selection)
      selection
      (get-board-diemension))))

(defn user-input-move [board]
 (let [input (read-string (prompt-terminal "what is your next move ?"))]
    (if (valid-selection input board)
      (matrix-convrt input (board-diemensions board))
      (do (print-message "invalid selection")
      	   (user-input-move board)))))


(defrecord ConsoleInput []
  InputProtocol
  (get-move [input board] (user-input-move board))
  (get-marker [input] (user-marker))
  (get-board-size [input] (get-board-diemension)))

