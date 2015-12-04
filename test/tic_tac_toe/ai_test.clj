(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.ai :refer :all]
  	        [tic-tac-toe.protocol.player :refer [PlayerProtocol next-move]]))


(deftest game-state-score-draw-depth-zero
  (let [game   { :board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of zero"
   (is (= 0 (score-game game))))))

(deftest game-state-score-draw-depth-nine
  (let [ game { :board [["x" "o" "x"] ["x" "o" "o"] ["o" "x" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of nine"
   (is (= 0 (score-game game))))))

(deftest game-state-score-depth-one
  (let [ game { :board [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of one"
   (is (= 0 (score-game game))))))

(deftest game-state-score-depth-two
  (let [game { :board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of two"
   (is (= 0 (score-game game))))))

(deftest game-state-score-depth-three
  (let [game { :board [["x" "_" "x"] ["_" "o" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"} ]
    (testing "scores the current game state of the board at a game depth of three"
   (is (= 0 (score-game game))))))

(deftest game-state-score-depth-four
  (let [game { :board [["x" "x" "_"] ["o" "_" "o"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of four"
   (is (= 0 (score-game game))))))

(deftest game-state-score-max-depth-five
  (let [game { :board [["o" "o" "o"] ["x" "_" "x"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of five"
   (is (= 5 (score-game game))))))

(deftest game-state-score-min-depth-six
  (let [game { :board [["x" "_" "x"] ["o" "_" "o"] ["o" "_" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of six"
   (is (= 0 (score-game game))))))

(deftest game-state-score-max-depth-seven
  (let [game { :board [["o" "x" "o"] ["x" "o" "x"] ["x" "_" "o"]]:ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of seven"
   (is (= 2 (score-game game))))))

(deftest game-state-score-depth-eight
  (let [game { :board[["x" "o" "x"] ["o" "_" "o"] ["x" "o" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of eight"
   (is (= 0 (score-game game))))))

(deftest game-possible-moves
  (let [game  { :board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "y"]] :ai-marker "o" :player-marker "x"}]
  (testing "returns a vector of possible moves"
    (is (= [ 2 3 4  6 7 8] (possible-moves game  0 []))))))

(deftest best-move-possible-max
  (testing "returns the  index and score of the best maximizing move"
    (is (=[8 10] (best-score-index [2 3 4 5 6 7 8 9 10] true )))))

(deftest best-move-possible-mini
  (testing "returns the score and index of the best minimizing move"
    (is (= [0 2] (best-score-index  [2 3 4 5 6 7 8 9 10] false )))))

(deftest possible-board-state
  (testing "return a posible board state based on input"
   (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]
          marker "x"]
    (is (= [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "x"]] (possible-board 9 marker board))))))

(deftest possible-game-state
  (testing "returns possible board states based on available moves"
   (let [current-player "o"
         available-moves [3 5]
         game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
         possible-game-states '({:board [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"}
                                {:board [["o" "o" "_"] ["x" "o" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"})]
      (is (= possible-game-states (game-states available-moves game current-player))))))

(deftest minimax-test
 (let [ game { :board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        depth 0]
    (testing "return best score and its index based on the board state"
   (is (= [0 4] (minimax game true  depth))))))

(deftest ai-move-deafult-check
  (let [game { :board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] :ai-marker "o" :player-marker "x"}]
    (is (= true (move-state-default? game)))))

(deftest ai-best-move-win-one
  (let [game { :board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [0 2] (ai-move game))))))

 (deftest ai-best-move-block-horizontial
  (let [game { :board  [["o" "_" "_"] ["x" "_" "x"] ["_" "" "_"]] :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [1 1] (ai-move game))))))

 (deftest ai-best-move-block-horizontial
  (let [game {:board  [["x" "_" "x"] ["_" "_" "o"] ["_" "" "_"]]  :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [0 1] (ai-move game))))))

 (deftest ai-best-move-block-horizontial
  (let [game { :board [["_" "_" "_"] ["_" "_" "o"] ["x" "_" "x"]]  :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [2 1] (ai-move game))))))

 (deftest ai-best-move-block-diagonal
  (let [game { :board [["x" "_" "o"] ["_" "x" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [2 2] (ai-move game))))))

 (deftest ai-best-move-block-diagonal
  (let [game { :board  [["o" "_" "x"] ["_" "x" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [2 0] (ai-move game))))))

 (deftest ai-best-move-block-verticle
  (let [game { :board [["o" "_" "x"] ["_" "_" "x"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
   (testing "returns the best move location"
      (is (= [2 2] (ai-move game))))))

(deftest ai-deafult-move
  (let [player  (->AiPlayer "o")
        game { :board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] :ai-marker "o" :player-marker "x"}]
    (testing "returns true for game depth <=1"
      (is (=(game-move game player))))))

(deftest ai-gamemove
  (let [player  (->AiPlayer "o")
        game { :board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] :ai-marker "o" :player-marker "x"}]
    (testing "returns a game set with updated board with ai's move"
      (is (=(game-move game player))))))

(deftest ai-record
  (let [player  (->AiPlayer "o")
        game { :board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] :ai-marker "o" :player-marker "x"}]
    (testing "creates defrecord of player protocol"
      (is (= (assoc game :board [["o" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]) (next-move player game))))))
