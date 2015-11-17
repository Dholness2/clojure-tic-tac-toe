(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.board :refer :all]
  	        [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.game :refer :all]))

(deftest game-state-score-draw-depth-zero
  (let [board-empty  [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board"
   (is (= 0 (score-game board-empty))))))

(deftest game-state-score-draw-depth-nine
  (let [board-empty  [["x" "o" "x"] ["o" "x" "o"] ["x" "x" "o"]]]
    (testing "scores the current game state of the board"
   (is (= -1 (score-game board-empty))))))

(deftest game-state-score-depth-one
  (let [board  [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board))))))

(deftest game-state-score-depth-two
  (let [board  [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board))))))

(deftest game-state-score-depth-three
  (let [board  [["x" "_" "x"] ["_" "o" "_"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board))))))

(deftest game-state-score-depth-four
  (let [board  [["x" "x" "_"] ["o" "_" "o"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board))))))

(deftest game-state-score-max-depth-five
  (let [board-player-two-win  [["o" "o" "o"] ["x" "_" "x"] ["_" "_" "_"]]]
    (testing "scores the current game state of the board  "
   (is (= 5 (score-game board-player-two-win))))))

(deftest game-state-score-min-depth-six
  (let [board [["x" "_" "x"] ["o" "_" "o"] ["o" "_" "x"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board))))))

(deftest game-state-score-max-depth-seven
  (let [board-player-two-win  [["o" "x" "o"] ["x" "_" "x"] ["x" "_" "o"]]]
    (testing "scores the current game state of the board "
   (is (= 0 (score-game board-player-two-win))))))

(deftest game-possible-moves
  (let [board  [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "y"]]]
  (testing "returns a vector of possible moves"
    (is (= [ 2 3 4  6 7 8] (possible-moves board  0 []))))))

(deftest best-move-possible-max
  (testing "returns the  index and score of the best maximizing move"
    (is (=[8 10] (best-score-index [2 3 4 5 6 7 8 9 10] true )))))

(deftest best-move-possible-mini
  (testing "returns the score and index of the best minimizing move"
    (is (= [0 2] (best-score-index  [2 3 4 5 6 7 8 9 10] false )))))

(deftest possible-board-state
  (testing "return a posible board state based on input"
   (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]]
    (is (= [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "x"]] (possible-board 9 player1-marker board))))))

(deftest minimax-test-one
 (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]]
  (testing "return best score and its index based on the board state"
    (is (= [0 4] (minimax board true ))))))

 (deftest ai-best-move
  (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]]
   (testing "returns the best move location"
      (is (= [0 2] (ai-move board))))))






