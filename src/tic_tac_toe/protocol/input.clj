(ns tic-tac-toe.protocol.input)

(defprotocol InputProtocol
  (get-move [input board]))