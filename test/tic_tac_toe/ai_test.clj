(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.game :refer [winner?]]
            [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner display-board index-board]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol next-move]]))


(defn win-or-draw? [player-marker winner-state]
  (or (= player-marker winner-state) (= player-marker "its a draw")))

(defn correct-ai-choice? [game]
  (let[winner (winner? (:board game))]
     (cond
      (= winner (:ai-marker game)) true
      (= winner "its a draw") true
      (= winner nil) true
      :else false )))

(declare check-every-possible-gamestate)

(defn check-gamestate [gamestate]
  (let [winner-state (winner? (:board gamestate))]
    (if (nil? winner-state)
      (check-every-possible-gamestate gamestate)
      (if (correct-ai-choice? gamestate)
          true
          false))))

(def check-every-possible-gamestate
   (memoize ( fn [gamestate]
   (let [ai-choice (game-move gamestate (:ai-marker gamestate))]
     (if (= nil (winner? (:board ai-choice)))
       (let [possible-games (game-states (possible-moves ai-choice) ai-choice (:player-marker ai-choice))]
           (map check-gamestate possible-games))
       (correct-ai-choice? ai-choice))))))

(deftest game-value-test-three-by-three
  (let [game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns scoring base value of board- size + 1 "
      (is (=  10 (scoring-base game))))))

(deftest game-value-test-four-by-four
  (let [game {:board [["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns scoring base value of board- size + 1 "
      (is (=  17 (scoring-base game))))))

(deftest game-state-score-draw-depth-zero
  (let [game   { :board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of zero"
      (is (= 0 (score-game game 0))))))

(deftest game-state-score-draw-depth-nine
  (let [ game { :board [["x" "o" "x"] ["x" "o" "o"] ["o" "x" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of nine"
      (is (= 0 (score-game game 9))))))

(deftest game-state-score-depth-one
  (let [ game { :board [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of one"
      (is (= 0 (score-game game 1))))))

(deftest game-state-score-depth-two
  (let [game { :board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of two"
      (is (= 0 (score-game game 2))))))

(deftest game-state-score-depth-three
  (let [game { :board [["x" "_" "x"] ["_" "o" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"} ]
    (testing "scores the current game state of the board at a game depth of three"
      (is (= 0 (score-game game 3))))))

(deftest game-state-score-depth-four
  (let [game { :board [["x" "x" "_"] ["o" "_" "o"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of four"
      (is (= 0 (score-game game 4))))))

(deftest game-state-score-max-depth-five
  (let [game { :board [["o" "o" "o"] ["x" "_" "x"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of five"
      (is (= 5 (score-game game 5))))))

(deftest game-state-score-min-depth-six
  (let [game { :board [["x" "_" "x"] ["o" "_" "o"] ["o" "_" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of six"
      (is (= 0 (score-game game 6))))))

(deftest game-state-score-max-depth-seven
  (let [game { :board [["o" "x" "o"] ["x" "o" "x"] ["x" "_" "o"]]:ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of seven"
      (is (= 3 (score-game game 7))))))

(deftest game-state-score-depth-eight
  (let [game { :board[["x" "o" "x"] ["o" "_" "o"] ["x" "o" "x"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of eight"
      (is (= 0 (score-game game 8))))))

(deftest space-available-test
  (let [board  [["_" "x" "o"] ["x" "x" "o"] ["o" "x" "o"]]
        position 0]
    (testing "returns true if there are any empty spaces"
      (is (= true (space-available? board position))))))

(deftest space-available-test-false
  (let [board  [["0" "x" "o"] ["x" "x" "o"] ["o" "x" "o"]]
        position 8]
    (testing "returns false if there are no empty spaces"
      (is (= false (space-available? board position))))))

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
    (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]
          marker "x"]
      (testing "return a posible board state based on input"
        (is (= [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "x"]] (possible-board 9 marker board))))))

(deftest possible-game-state
   (let [current-player "o"
         available-moves [3 5]
         game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
         possible-game-states '({:board [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"}
                                {:board [["o" "o" "_"] ["x" "o" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"})]
    (testing "returns possible board states based on available moves"
      (is (= possible-game-states (game-states available-moves game current-player))))))

(deftest game-state-score-test
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
          maximizing true
          depth 0]
    (testing "return the score for a specfic game state"
      (is(= -8 (get-score-for-gamestate game maximizing depth))))))

(deftest score-test
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
          maximizing true
          open-positions [3 5 6 8]
          player "o"
          depth 0]
   (testing "return the scores for the prodvided positions"
     (is(= '(4 2 0 -4) (score game maximizing open-positions player depth))))))

(deftest score-test
    (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
          maximizing true
          open-positions [3 5 6 8]
          player "o"
          depth 0]
      (testing "returns the scores for the the currnet player's moves"
        (is(= '(9 7 0 -8) (score game maximizing open-positions player depth))))))

(deftest best-score-test
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
          maximizing true
          depth 0]
    (testing "return the best score  and its index for a specfic game state"
      (is(= [0 9] (get-best-score-for game maximizing depth))))))

(deftest minimax-test
 (let [ game { :board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        depth 0]
    (testing "return best score and its index based on the board state"
      (is (= [0 9] (minimax game true  depth))))))

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

(deftest ai-move-win-state-test-3-by-3
  (let [ai-marker "x"
        new-game { :board [["_" "_" "_"]["_" "_" "_"]["_" "_" "_"]]  :ai-marker "x" :player-marker "o"}
        gamestates (flatten (check-every-possible-gamestate new-game))]
   (testing "ai never loses"
     (is (= true (every? true? gamestates))))))

(deftest ai-move-win-state-test-4-by-4
  (let [ai-marker "x"
        new-game { :board [["_" "_" "_" "_"]["_" "_" "_" "_"]["_" "_" "_" "_"]["_" "_" "_" "_"]]  :ai-marker "x" :player-marker "o"}
        gamestates (flatten (check-every-possible-gamestate new-game))]
   (testing "ai never loses"
     (is (= true (every? true? gamestates))))))


