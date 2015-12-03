(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]))

(deftest board-create
  (testing "Create new board based on user input"
		(is ( =  [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] (create-empty-board 3 )))))

(deftest board-size-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
	  (testing "returns the size of the board "
	    (is ( = 9 (board-size board))))))

(deftest board-empty-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
	  (testing "returns the size of the board "
	    (is ( = 9 (empty-spaces board))))))

(deftest board-move
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
	  (testing "creates a new vector with updated representation of the board"
	    (is ( = [["x" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] (move [0 0] "x" board))))))

(deftest valid-move-zero
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= false (validmove? 0))))

(deftest valid-move-one
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= true (validmove? 1))))

(deftest valid-move-two
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= true (validmove? 2))))

(deftest valid-move-three
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= true (validmove? 3))))

(deftest valid-move-four
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= true (validmove? 4))))

(deftest valid-move-five
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= true (validmove? 5))))

(deftest valid-move-six
  (testing "test to mke sure user input is within game move option[0-8]")
 	  (is (= true (validmove? 6))))

(deftest valid-move-seven
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= true (validmove? 7))))

(deftest valid-move-eight
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= true (validmove? 8))))

(deftest valid-move-eight
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= true (validmove? 9))))

(deftest invalid-move-less-than-zero
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= false (validmove? -1))))

(deftest invalid-move-greater-than-eight
  (testing "test to mke sure user input is within game move option[0-8]")
    (is (= false (validmove? 20))))

(deftest open-move
  (testing "checks to see if move is taken")
    (is (= false (moveopen? [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] 1))))

(deftest matrix-convrt-test
  (testing "takes the users move and converts to nested vector location ")
    (is (= [0 0] (matrix-convrt 1 3))))

(deftest determine-equality
  (testing "checks for matches"
    (is (= true (check-equality ["x" "x" "x"] )))))

(deftest get-nested-elements
  (testing "gets nested elements in board"
    (is (= '("y" "y" "y")(get-location [["y" "x" "x"] ["y" "y" "y"] ["y" "x" "y"]] [0 1 2 ] )))))

(deftest get-diagnoals-test
  (let [board [["x" "_" "_" ]["_" "x" "_" ]["_" "_" "x" ]]
    		rowsize 3]
    (testing "gets diagonal positions"
  	  (is (= (get-diagnoals board 3))))))