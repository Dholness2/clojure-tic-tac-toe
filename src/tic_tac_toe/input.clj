(ns tic-tac-toe.input
(:require [tic-tac-toe.protocol.input :refer :all]))

(defn get-move []
   (read-string(read-line)))

(defrecord ConsoleInput []
	InputProtocol
      (get-input [input] get-move))
