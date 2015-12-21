(ns tic-tac-toe.protocol.input)

(defprotocol InputProtocol
  (get-move [input board])
  (get-board-size [input])
  (get-game-type [input games]))
