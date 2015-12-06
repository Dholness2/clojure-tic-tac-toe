(ns tic-tac-toe.protocol.display)

(defprotocol DisplayProtocol
	(display-state [display board])
	(display-winner [display board]))