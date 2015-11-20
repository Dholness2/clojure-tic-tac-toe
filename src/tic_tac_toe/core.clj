(ns tic-tac-toe.core
  (:gen-class)

  (require [tic-tac-toe.board :refer :all]
           [tic-tac-toe.game :refer :all]
           [tic-tac-toe.ai :refer :all]
           [tic-tac-toe.display :refer :all]
           [tic-tac-toe.human :refer :all]
           [tic-tac-toe.protocol.player :refer :all]))

(defn game-runner [board player1 player2]
  (display-iteration board)
  (if-not (winner? board)
    (let [current-board (next-move player1 board)]
      (display-iteration current-board)
      (if-not (winner? board)(game-runner (next-move player2 current-board)player1 player2)))
    (print-winner board)))

(defn -main []

(def human (->HumanPlayer "x"))
(def ai (->AiPlayer "o"))

 (let [emptyboard (create-empty-board board-dimensions)]
     (game-runner emptyboard human  ai )))

