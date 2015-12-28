(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]))

(deftest board-create
  (let [board-size 3]
    (testing "Create new board based on user input"
<<<<<<< HEAD
      (is (= [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] (create-empty-board board-size))))))
=======
      (is (= [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] (create-empty-board board-size))))))
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732

(deftest board-dimension
  (let [board [["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"]]]
    (testing "returns the diemension of the board")
<<<<<<< HEAD
      (is (= 4 (board-dimensions board)))))

(deftest board-size-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "returns the size of the board "
      (is ( = 9 (board-size board))))))

(deftest board-empty-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
   (testing "returns the count of empty spaces in the board "
      (is ( = 9 (empty-space-count board))))))
=======
    (is (= 4 (board-dimensions board)))))

(deftest board-size-test
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "returns the size of the board "
      (is (= 9 (board-size board))))))

(deftest board-empty-test
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "returns the count of empty spaces in the board "
      (is (= 9 (empty-space-count board))))))
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732

(deftest board-move
  (let [board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "creates a new vector with updated representation of the board"
      (is (= [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] (move [0 0] "x" board))))))

(deftest valid-move-zero
  (let [board-size 9
        move 0]
    (testing "test to mke sure user input is within game move option[1-9]"
<<<<<<< HEAD
 	    (is (= false (validmove? move board-size))))))

(deftest valid-move-ten
  (let [board-size 9
         move  10]
=======
      (is (= false (validmove? move board-size))))))

(deftest valid-move-ten
  (let [board-size 9
        move  10]
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
    (testing "test to mke sure user input is within game move option[1-9]"
      (is (= false (validmove? move board-size))))))

(deftest valid-move-five
  (let [board-size 9
<<<<<<< HEAD
         move  5]
=======
        move  5]
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
    (testing "test to mke sure user input is within game move option[1-9]"
      (is (= true (validmove? move board-size))))))

(deftest valid-move-zero-larger-board
  (let [board-size 16
        move 0]
    (testing "test to mke sure user input is within game move option[1-16]"
      (is (= false (validmove? move board-size))))))

(deftest valid-move-twenty-larger-board
  (let [board-size 16
<<<<<<< HEAD
         move  20]
=======
        move  20]
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
    (testing "test to mke sure user input is within game move option[1-16]"
      (is (= false (validmove? move board-size))))))

(deftest valid-move-fithteen-larger-board
  (let [board-size 16
<<<<<<< HEAD
         move  15]
=======
        move  15]
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
    (testing "test to mke sure user input is within game move option[1-16]"
      (is (= true (validmove? move board-size))))))

(deftest open-move
  (testing "checks to see if move is taken"
    (is (= false (moveopen? [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] 1)))))

(deftest matrix-convrt-test
  (testing "takes the users move and converts to nested vector location "
    (is (= [0 0] (matrix-convrt 1 3)))))

(deftest determine-equality
  (testing "checks for matches"
    (is (= true (check-equality ["x" "x" "x"])))))

(deftest get-nested-elements
  (testing "gets nested elements in board"
    (is (= '("y" "y" "y") (get-location [["y" "x" "x"] ["y" "y" "y"] ["y" "x" "y"]] [0 1 2])))))

(deftest get-diagnoals-test
<<<<<<< HEAD
  (let [board [["x" "_" "_" ]["_" "x" "_" ]["_" "_" "x" ]]
=======
  (let [board [["x" "_" "_"] ["_" "x" "_"] ["_" "_" "x"]]
>>>>>>> 1f4f3041fe75bbb2ecadeb32d11cd7285b7aa732
        rowsize 3]
    (testing "gets diagonal positions"
      (is (= (get-diagnoals board 3))))))
