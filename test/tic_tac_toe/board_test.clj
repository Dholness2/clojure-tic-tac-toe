(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]))

(deftest board-create
  (let [board-size 3]
    (testing "Create new board based on user input"
      (is (= [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] (create-empty-board board-size))))))

(deftest board-dimension
  (let [board [["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"]]]
    (testing "returns the dimension of the board")
    (is (= 4 (board-dimensions board)))))

(deftest board-size-test
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "returns the size of the board "
      (is (= 9 (board-size board))))))

(deftest board-empty-test
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "returns the count of empty spaces in the board "
      (is (= 9 (empty-space-count board))))))

(deftest board-move
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "creates a new vector with updated representation of the board"
      (is (= [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] (move [0 0] "x" board))))))

(deftest valid-move-zero
  (let [board-size 9
        move 0]
    (testing "test to make sure user input is within game move option[1-9]"
      (is (false? (validmove? move board-size))))))

(deftest valid-move-ten
  (let [board-size 9
        move  10]
    (testing "test to make sure user input is within game move option[1-9]"
      (is (= false (validmove? move board-size))))))

(deftest valid-move-five
  (let [board-size 9
        move  5]
    (testing "test to make sure user input is within game move option[1-9]"
      (is (= true (validmove? move board-size))))))

(deftest valid-move-zero-larger-board
  (let [board-size 16
        move 0]
    (testing "test to make sure user input is within game move option[1-16]"
      (is (= false (validmove? move board-size))))))

(deftest valid-move-twenty-larger-board
  (let [board-size 16
        move  20]
    (testing "test to make sure user input is within game move option[1-16]"
      (is (false? (validmove? move board-size))))))

(deftest valid-move-fithteen-larger-board
  (let [board-size 16
        move  15]
    (testing "test to make sure user input is within game move option[1-16]"
      (is (true? (validmove? move board-size))))))

(deftest open-move
  (testing "checks to see if move is taken"
    (is (false? (moveopen? [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] 1)))))

(deftest matrix-convrt-test
  (testing "takes the users move and converts to nested vector location "
    (is (= [0 0] (matrix-convrt 1 3)))))

(deftest determine-equality
  (testing "checks for matches"
    (is (true? (check-equality ["x" "x" "x"])))))

(deftest get-nested-elements
  (testing "gets nested elements in board"
    (is (= '("y" "y" "y") (get-location [["y" "x" "x"] ["y" "y" "y"] ["y" "x" "y"]] [0 1 2])))))

(deftest get-diagnoals-test
  (let [board [["x" "_" "_"] ["_" "x" "_"] ["_" "_" "x"]]
        rowsize 3]
    (testing "gets diagonal positions"
      (is (= (get-diagnoals board 3))))))
