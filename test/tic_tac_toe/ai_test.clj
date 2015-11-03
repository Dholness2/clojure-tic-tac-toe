(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.board :refer :all]
  	        [tic-tac-toe.ai :refer :all]))

(def board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]) 
(def board-depth-four [["_" "_" "o"] ["_" "_" "_"] ["o" "x" "x"]]) 
(def board-winner-x   [["x" "x" "x"] ["o" "_" "o"] ["_" "o" "_"]])
(def board-winner-y   [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]])


(deftest board-size-count
 (testing "board size"
   (is (= 9 (board-size board)))))


(deftest board-size-count
 (testing "board size"
   (is (= 9 (board-size board)))))

(deftest empty-moves-count
	(testing "returns the amount of unoccupied spaces in the board"
	(is (= 9 (empty-spaces board)))))

(deftest depth-counter  
  (testing "gets game depth" 
  	(is (= 0  (game-depth board)))))

(deftest depth-counter-four-steps
	(testing "gets game depth"
	 (is (= 4 (game-depth board-depth-four)))))


(deftest game-state-score-draw
  (testing "scores the current game state of the board"
   (is (= 0 (score-game board)))))

(deftest game-state-score-mini-x
  (testing "scores the current game state of the board with a minimizing win"
   (is (= -4 (score-game board-winner-x)))))

(deftest game-state-score-mini-y
  (testing "scores the current game state of the board with a minimizing win"
   (is (= 4 (score-game board-winner-y)))))


; (deftest game-possible-moves
;   (testing "possble moves"
;     (is (= [1 2 3 4 5 6 7 8 9 10 ] (possble-moves board)))))
