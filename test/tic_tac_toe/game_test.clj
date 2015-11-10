(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.display :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]))
(deftest board-draw 
	(testing "game is a draw"
	(is (=  true (draw? [["x" "y" "y"] ["x" "y" "x"] ["Y" "x" "y"]])))))

(deftest winner-test 
   (testing "check for win state or draw") 
   (is (=  "x" (winner? [["x" "x" "x"] ["y" "x" "Y"] ["x" "x" "y"]]))))

(deftest game-depth-test 
  (testing "returns the depth of the board"
  (is (= 0 (game-depth [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]])))))