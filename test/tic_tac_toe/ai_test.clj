(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.board :refer :all]
  	        [tic-tac-toe.ai :refer :all]))

(def board [["x" "x" "_"] ["o" "_" "o"] ["_" "_" "_"]])
(def board-empty [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])
(def board-depth-four [["_" "_" "o"] ["_" "_" "_"] ["o" "x" "x"]])
(def board-winner-x   [["x" "x" "x"] ["o" "_" "o"] ["_" "o" "_"]])
(def board-winner-y   [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]])
(def board-minimax    [["o" "_" "o"] ["_" "_" "x"] ["_" "x" "x"]])
(def board-complete   [["o" "x" "o"] ["x" "o" "x"] ["x" "o" "x"]])
(def board-x-win   [["x" "x" "x"] ["o" "_" "o"] ["_" "_" "_"]])
(def board-x-block   [["x" "_" "x"] ["o" "_" "_"] ["_" "_" "_"]])

(deftest board-size-count
 (testing "board size"
   (is (= 9 (board-size board-empty)))))

(deftest board-size-count
 (testing "board size"
   (is (= 9 (board-size board-empty)))))

(deftest empty-moves-count
	(testing "returns the amount of unoccupied spaces in the board"
	(is (= 9 (empty-spaces board-empty)))))

(deftest depth-counter
  (testing "gets game depth"
  	(is (= 0  (game-depth board-empty)))))

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

(deftest game-possible-moves
  (testing "returns a vector of possible moves"
    (is (= [1 2 3 4 5 6 7 8 9 ] (possible-moves board-empty 0 [])))))

(deftest max-index
  (testing "finds the index of the max move"
    (is (= 7 (find-max-index [10 20 30 40 50 60 70 80])))))

(deftest min-index
  (testing "finds the min index"
    (is (= 0 (find-min-index [0 1 2 3 4 5 6 7 8 9])))))

(deftest best-move-possible-max
  (testing "returns the  index and score of the best maximizing move"
    (is (=[8 10] (best-score-index [2 3 4 5 6 7 8 9 10] true )))))

(deftest best-move-possible-mini
  (testing "returns the score and index of the best minimizing move"
    (is (= [0 2] (best-score-index  [2 3 4 5 6 7 8 9 10] false )))))

(deftest possible-board-state
  (testing "return a psovble bard state based on input"
    (is (=      (possible-board 1 "x" board)))))

(deftest game-end-state
  (testing "return true if the game is over"
    (is (= "x" (game-over? [["x" "x" "x"] ["o" "_" "o"] ["_" "o" "_"]])))))

(deftest minimax-test
  (testing "return best sore and its index its score"
    (is (= [1] (minimax board-minimax true )))))

 (deftest minimax-test
  (testing "return the score of a complete game"
    (is (=  0 (minimax board-complete true )))))

(deftest minimax-test
  (testing "return the score of a x win game"
    (is (=[nil -5] (minimax board-x-win true )))))

 (deftest ai-best-move
   (testing "returns the winning move"
     (is (=  2 (ai-move board-minimax)))))

(deftest ai-best-move
   (testing "returns the best move to block win"
     (is (=  2 (ai-move board-x-block)))))