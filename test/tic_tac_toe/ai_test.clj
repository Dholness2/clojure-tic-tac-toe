(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.board :refer :all]
  	        [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.game :refer :all]))

(def board [["x" "x" "_"] ["o" "_" "o"] ["_" "_" "_"]])
(def board-empty [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]])
(def board-depth-four [["_" "_" "o"] ["_" "_" "_"] ["o" "x" "x"]])
(def board-winner-x   [["x" "x" "x"] ["o" "_" "o"] ["_" "o" "_"]])
(def board-winner-y   [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]])
(def board-minimax    [["o" "_" "o"] ["_" "_" "x"] ["_" "x" "x"]])
(def board-complete   [["o" "x" "o"] ["x" "o" "x"] ["x" "o" "x"]])
(def board-x-win   [["x" "x" "x"] ["o" "_" "o"] ["_" "_" "_"]])
(def board-x-block   [["x" "_" "x"] ["o" "_" "_"] ["_" "_" "_"]])


(deftest game-state-score-draw
  (let [board-empty  [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board"
   (is (= 0 (score-game board-empty))))))

(deftest game-state-score-mini-x
  (testing "scores the current game state of the board with a minimizing win"
   (is (= -4 (score-game board-winner-x)))))

(deftest game-state-score-mini-y
  (testing "scores the current game state of the board with a minimizing win"
   (is (= 4 (score-game board-winner-y)))))

(deftest game-possible-moves
  (testing "returns a vector of possible moves"
    (is (= [1 2 3 4 5 6 7 8 9 ] (possible-moves board-empty 0 [])))))

(deftest best-move-possible-max
  (testing "returns the  index and score of the best maximizing move"
    (is (=[8 10] (best-score-index [2 3 4 5 6 7 8 9 10] true )))))

(deftest best-move-possible-mini
  (testing "returns the score and index of the best minimizing move"
    (is (= [0 2] (best-score-index  [2 3 4 5 6 7 8 9 10] false )))))

(deftest possible-board-state
  (testing "return a psovble bard state based on input"
    (is (=      (possible-board 1 "x" board)))))

(deftest minimax-test
  (testing "return best sore and its index its score"
    (is (= [1] (minimax board-minimax true )))))

 (deftest minimax-test
  (testing "return the score of a complete game"
    (is (=  0 (minimax board-complete true )))))

(deftest minimax-test
  (testing "return the score of a x win game"
    (is (=[0 -5] (minimax board-x-win true )))))

 (deftest ai-best-move
   (testing "returns the winning move"
     (is (=  2 (ai-move board-minimax)))))

(deftest ai-best-move
   (testing "returns the best move to block win"
     (is (=  [0 1] (ai-move board-x-block)))))

(deftest board-states-test
  (testing "return possible-board boards states based on available moves"
    (is = (board-states [1 2 3 5 6] board "x"))))






