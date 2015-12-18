(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-intializer game-runner create-game marker-one marker-two games]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.input.console :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.input :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

(def last-displayed-state (atom {:states [] :winner nil}))
(def move-vec-a (atom  [3 9 8]))
(def move-vec-b (atom  [3 9 8]))

(defn input-move [moves board ]
  (let [move (first @moves)]
      (swap! moves (fn [current] (drop 1 current)))
     (matrix-convrt move (board-diemensions board))))

(defrecord DummyDisplay []
  DisplayProtocol
  (display-state [display board] (swap! last-displayed-state assoc :states board))
  (display-winner [display board] (swap! last-displayed-state assoc :winner (winner? board))))

(defrecord DummyInput [moves]
  InputProtocol
  (get-move [input board] (input-move moves board))
  (get-board-size [input]  3)
  (get-game-type [input games] 1))

(defmethod create-game :dummy-game [game-type input board ]
    [game-type input  board ])

(deftest gamme-runner-test
  (let [display (->DummyDisplay)
        input (->DummyInput move-vec-b)
        board (create-empty-board 3)
        game (create-game :computer-vs-human input board)]
   (with-out-str (game-runner game display)))
   (test "test game runner iterations"
     (is (= (@last-displayed-state :winner) "o"))))

(deftest human-vs-computer-test
  (let [input (->ConsoleInput)
        player-1 (->HumanPlayer marker-one input)
        player-2 (->AiPlayer marker-two)
        board (create-empty-board 3)
        test-game (create-game :human-vs-computer input board)]
    (test " returns a starting game state and players within a map"
      (is (= [player-1 player-2] (last test-game))))))

(deftest computer-vs-human-test
  (let [input (->ConsoleInput)
        player-1 (->AiPlayer marker-two)
        player-2 (->HumanPlayer marker-one input)
        board (create-empty-board 3)
        test-game  (create-game :computer-vs-human input board)]
    (test " returns a starting game state and players within a map"
      (is (= [player-1 player-2] (last test-game))))))

(deftest game-intializer-test
   (let [display (->DummyDisplay)
         input (->DummyInput move-vec-b)]
    (test "game initialize  with correct arguments"
      (game-intializer display input)
      (is(= (@last-displayed-state :winner) "o")))))
