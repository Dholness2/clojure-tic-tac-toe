(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer [draw row-check column-check diagonal-check draw? winner game-depth]]))

(deftest board-row-check
  (testing "check for winner from row"
<<<<<<< HEAD
    (is (= "x" (row-check [["x" "x" "x"] ["y" "x" "Y"] ["Y" "x" "y"] ])))))
=======
    (is (= "x" (row-check [["x" "x" "x"] ["y" "x" "Y"] ["Y" "x" "y"]])))))
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732

(deftest board-colunm-check
  (testing "check for winner from column"
    (is (= "y" (column-check [["y" "x" "x"] ["y" "Y" "Y"] ["y" "x" "y"]])))))

(deftest diagonal-checker
  (testing "checks for any diagonal wins"
    (is (= "y" (diagonal-check [["y" "x" "_"] ["x" "y" "x"] ["x" "x" "y"]] 3)))))

(deftest board-draw
  (testing "game is a draw"
    (is (= true (draw? [["x" "y" "y"] ["x" "y" "x"] ["Y" "x" "y"]])))))

(deftest winner-test
  (testing "check for win state or draw"
    (is (= "x" (winner [["x" "x" "x"] ["y" "x" "Y"] ["x" "x" "y"]])))))

(deftest game-depth-test
  (testing "returns the depth of the board"
<<<<<<< HEAD
    (is (= 0 (game-depth [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]])))))
=======
    (is (= 0 (game-depth [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])))))
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
