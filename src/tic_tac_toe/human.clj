 (ns tic-tac-toe.human
(:require [tic-tac-toe.board :refer [move]]
         [tic-tac-toe.input :refer :all]
         [tic-tac-toe.protocol.input :refer :all]
         [tic-tac-toe.protocol.player :refer :all]))


(defn human-move [board marker input-protocol]
   (move (get-move input-protocol board ) marker board))

(defrecord HumanPlayer[marker input-protocol]  
  PlayerProtocol 
    (next-move [player board] (human-move board marker input-protocol)))