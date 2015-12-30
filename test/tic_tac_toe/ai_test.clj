(ns tic-tac-toe.ai-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.game :refer [winner draw]]
            [tic-tac-toe.display.terminal :refer [->TerminalDisplay print-winner display-board index-board]]
            [tic-tac-toe.protocol.player :refer [PlayerProtocol next-move]]))

(defn win-or-draw? [player-marker winner-state]
  (or (= player-marker winner-state) (= player-marker draw)))

(defn correct-ai-choice? [game]
  (let [winner (winner (:board game))]
    (condp = winner
      (:ai-marker game) true
      draw true
      nil true
      false)))

(declare check-every-possible-gamestate)

(defn check-gamestate [gamestate]
  (let [winner-state (winner (:board gamestate))]
    (if (nil? winner-state)
      (check-every-possible-gamestate gamestate)
      (correct-ai-choice? gamestate))))

(def check-every-possible-gamestate
  (memoize (fn [gamestate]
             (let [ai-choice (game-move gamestate (:ai-marker gamestate))]
               (if (= nil (winner (:board ai-choice)))
                 (let [possible-games (game-states (possible-moves ai-choice) ai-choice (:player-marker ai-choice))]
                   (map check-gamestate possible-games))
                 (correct-ai-choice? ai-choice))))))

(deftest game-value-test-3-by-3
  (let [game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns scoring base value of board- size + 1 "
      (is (= 10 (scoring-base game))))))

(deftest game-value-test-4-by-4
  (let [game {:board [["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"] ["_" "_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns scoring base value of board- size + 1 "
      (is (= 17 (scoring-base game))))))

(deftest game-state-score-draw-3-by-3
  (let [game   {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of zero"
      (is (= 0 (score-game game 0))))))

(deftest game-state-score-draw-4-by-4
  (let [game {:board [["x" "o" "x" "o"] ["x" "o" "o" "x"] ["o" "x" "x" "o"] ["_" "_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of nine"
      (is (= 0 (score-game game 0))))))

(deftest game-state-score-win-3-by-3
  (let [game   {:board [["o" "o" "o"] ["x" "_" "x"] ["x" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of zero"
      (is (= 9 (score-game game 1))))))

(deftest game-state-score-win-4-by-4
  (let [game {:board [["x" "o" "x" "x"] ["o" "o" "o" "o"] ["x" "x" "x" "o"] ["_" "_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of nine"
      (is (= 16 (score-game game 1))))))

(deftest game-state-score-loss-3-by-3
  (let [game   {:board [["x" "x" "x"] ["o" "_" "x"] ["o" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of zero"
      (is (= -9 (score-game game 1))))))

(deftest game-state-score-loss-4-by-4
  (let [game {:board [["x" "x" "x" "x"] ["x" "o" "o" "o"] ["o" "o" "x" "o"] ["_" "_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "scores the current game state of the board at a game depth of nine"
      (is (= -16 (score-game game 1))))))

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
  (let [game  {:board [["x" "_" "_"] ["_" "o" "_"] ["_" "_" "y"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns a vector of possible moves"
      (is (= [2 3 4  6 7 8] (possible-moves game  0 []))))))

(deftest best-move-possible-max
  (testing "returns the  index and score of the best maximizing move"
    (is (= [8 10] (best-score-index [2 3 4 5 6 7 8 9 10] true)))))

(deftest best-move-possible-mini
  (testing "returns the score and index of the best minimizing move"
    (is (= [0 2] (best-score-index  [2 3 4 5 6 7 8 9 10] false)))))

(deftest possible-board-state
  (let [board  [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]]
        marker "x"]
    (testing "return a possible board state based on input"
      (is (= [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "x"]] (possible-board 9 marker board))))))

(deftest possible-game-state
  (let [current-player "o"
        available-moves [3 5]
        game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        possible-game-states '({:board [["o" "o" "o"] ["x" "_" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"}
                               {:board [["o" "o" "_"] ["x" "o" "x"] ["_" "x" "_"]], :ai-marker "o", :player-marker "x"})]
    (testing "returns possible board states based on available moves"
      (is (= possible-game-states (game-states available-moves game current-player))))))

(deftest find-alpha-beta-test-alpha
  (let [optimal -100
        maximizing false
        child {:board [["o" "o" "o"] ["x" "_" "x"] ["_" "_ " " _"]] :ai-marker "o" :player-marker "x"}
        game-results {:current-value optimal :alpha optimal :beta 100 :depth 0 :scores []}
        expected-output {:current-value 9 :alpha 9 :beta 100 :depth 0 :scores [9]}]
    (testing "returns game results with updated max alpha and max current-value"
      (is (= expected-output (find-alpha-beta game-results optimal max  maximizing child))))))

(deftest find-alpha-beta-test-beta
  (let [optimal 100
        maximizing true
        child {:board [["o" "o" "o"] ["x" "_" "x"] ["_" "_ " " _"]] :ai-marker "x" :player-marker "o"}
        game-results {:current-value optimal :alpha -100 :beta optimal :depth 0 :scores []}
        expected-output {:current-value 100 :alpha -100 :beta 100 :depth 0 :scores [100]}]
    (testing "returns game-results with updated min beta and current-value"
      (is (= expected-output (find-alpha-beta game-results optimal max  maximizing child))))))

(deftest alpha-max-test
  (let [child {:board [["o" "o" "o"] ["x" "_" "x"] ["_" "_ " " _"]] :ai-marker "o" :player-marker "x"}
        game-results {:current-value -100 :alpha -100 :beta 100 :depth 0 :scores []}
        expected-output {:current-value 9 :alpha 9 :beta 100 :depth 0 :scores [9]}]
    (testing "returns updated game-results :beta and :current-value data based on score of child"
      (is (= expected-output (alpha-max game-results child))))))

(deftest beta-min-test
  (let [game {:board [["o" "o" "o"] ["x" "_" "x"] ["_" "_ " " _"]] :ai-marker "x" :player-marker "o"}
        game-results {:current-value 100 :alpha -100 :beta 100 :depth 0 :scores []}
        expected-output {:current-value -9, :alpha -100, :beta -9, :depth 0, :scores [-9]}]
    (testing "returns updated game-results :beta and :current-value based on score of child"
      (is (= expected-output (beta-min game-results game))))))

(deftest get-scores-test-max
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        children (game-states [3 5  7 9] game "o")
        depth 0
        value -100
        alpha -100
        beta 100]
    (testing "return the scores for the provided children unless alpha is greater than beta"
      (is (= [9 9 9 9] (get-scores alpha-max value alpha beta depth children))))))

(deftest get-scores-test-min
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        children (game-states [3 5  7 9] game "o")
        depth 0
        value -100
        alpha -100
        beta 100]
    (testing "return the scores for the provided children unless alpha is greater than beta"
      (is (= [-100 -100 -100 -100] (get-scores beta-min value alpha beta depth children))))))

(deftest score-test-max
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        maximizing true
        open-positions [3 5  7 9]
        player "o"
        depth 0]
    (testing "return the scores for the provided positions; if the beta is <= alpha score is set to alpha"
      (is (= [9 9 9 9] (score game maximizing player open-positions depth -100  100))))))

(deftest score-test-min
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "x" :player-marker "o"}
        maximizing false
        open-positions [3 5 6 8]
        player "o"
        depth 0]
    (testing "returns the scores for the current player's moves; if the beta is <= alpha score is set to beta"
      (is (= [-9 -9 -9 -9] (score game maximizing player  open-positions depth -100  100))))))

(deftest best-score-test-max
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        maximizing true
        depth 0]
    (testing "returns the best score and its index"
      (is (= [0 9] (get-best-score-for game maximizing depth -100 100))))))

(deftest best-score-test-min
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}
        maximizing false
        depth 0]
    (testing "returns the best score and its index"
      (is (= [1 -9] (get-best-score-for game maximizing depth -100 100))))))

(deftest minimax-test
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}
        depth 0]
    (testing "return best score and its index based on the board state"
      (is (= [0 9] (minimax game true depth -100 100))))))

(deftest ai-best-move-win-one
  (let [game {:board [["o" "o" "_"] ["x" "_" "x"] ["_" "x" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [0 2] (ai-move game))))))

(deftest ai-best-move-block-horizontial
  (let [game {:board  [["x" "_" "x"] ["_" "_" "o"] ["_" "" "_"]]  :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [0 1] (ai-move game))))))

(deftest ai-best-move-block-horizontial
  (let [game {:board [["_" "_" "_"] ["_" "_" "o"] ["x" "_" "x"]]  :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [2 1] (ai-move game))))))

(deftest ai-best-move-block-diagonal-b
  (let [game {:board [["x" "_" "o"] ["_" "x" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [2 2] (ai-move game))))))

(deftest ai-best-move-block-diagonal
  (let [game {:board  [["o" "_" "x"] ["_" "x" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [2 0] (ai-move game))))))

(deftest ai-best-move-block-verticle
  (let [game {:board [["o" "_" "x"] ["_" "_" "x"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns the best move location"
      (is (= [2 2] (ai-move game))))))

(deftest ai-deafult-move
  (let [player (->AiPlayer "o")
        game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}
        expected-results {:board [[#tic_tac_toe.ai.AiPlayer {:marker "o"} "_" "_"] ["_" "_" "_"] ["_" "_" "_"]], :ai-marker "o", :player-marker "x"}]
    (testing "returns true for game depth <=1"
      (is (= expected-results (game-move game player))))))

(deftest ai-gamemove
  (let [player (->AiPlayer "o")
        game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}
        expected-result {:board [[#tic_tac_toe.ai.AiPlayer {:marker "o"} "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}]
    (testing "returns a game set with updated board with AI's move"
      (is (= expected-result (game-move game player))))))

(deftest ai-record
  (let [player  (->AiPlayer "o")
        game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] :ai-marker "o" :player-marker "x"}
        expected-result {:board [["o" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]], :ai-marker "o", :player-marker "x"}]
    (testing "creates defrecord of player protocol"
      (is (= expected-result (next-move player game))))))

(deftest ai-move-win-state-test-3-by-3-x
  (let [ai-marker "x"
        new-game {:board [["_" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]  :ai-marker "x" :player-marker "o"}
        gamestates (flatten (check-every-possible-gamestate new-game))]
    (testing "ai never loses"
      (is (= true (every? true? gamestates))))))

(deftest ai-move-win-state-test-3-by-3-o
  (let [ai-marker "o"
        new-game {:board [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]]  :ai-marker "o" :player-marker "x"}
        gamestates (flatten (check-every-possible-gamestate new-game))]
    (testing "AI  never loses"
      (is (= true (every? true? gamestates))))))
