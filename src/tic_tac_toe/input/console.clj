(ns tic-tac-toe.input.console
  (:require [tic-tac-toe.protocol.input :refer :all]
            [tic-tac-toe.board :refer [validmove? moveopen? matrix-convrt move board-dimensions board-size]]
            [tic-tac-toe.display.terminal :refer [print-message]]))

(def dimensions-limits #{3 4})

(defn invalid-input? [input]
  (or (clojure.string/blank? input) (= nil (re-matches #"\w+" input))))

(defn prompt-terminal [question]
  (print-message question)
  (let [response  (read-line)]
    (if (invalid-input? response)
      (prompt-terminal question)
      response)))

(defn valid-selection [input board]
  (and (number? input) (validmove? input (board-size board)) (moveopen? board input)))

(defn get-board-dimension []
  (let [selection (read-string (prompt-terminal "What size board do you want? x by x (provide one number for x)"))]
    (if (and (number? selection) (contains? dimensions-limits selection))
      selection
      (get-board-dimension))))

(defn user-input-move [board]
  (let [input (read-string (prompt-terminal "What is your next move ?"))]
    (if (valid-selection input board)
      (matrix-convrt input (board-dimensions board))
      (do (print-message "invalid selection")
          (user-input-move board)))))

(defn game-key-to-strings [games]
  (apply str (map #(str (inc (.indexOf games %)) "." (name %) "\n") games)))

(defn get-game-selection [games]
  (let [game-options (game-key-to-strings games)
        selection (read-string (prompt-terminal (str "Select game type\n" game-options)))]
    (if (and (number? selection) (<= selection (count games)) (>= selection 0))
      (dec selection)
      (get-game-selection games))))

(defrecord ConsoleInput []
  InputProtocol
  (get-move [input board] (user-input-move board))
  (get-board-size [input] (get-board-dimension))
  (get-game-type [input games] (get-game-selection games)))
