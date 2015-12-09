(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-intializer game-runner create-game marker-one marker-two ]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.input.console :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.input :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

(def latest-displayed-state (atom {:states [] :winner nil}))

(defrecord DummyDisplay []
  DisplayProtocol
  (display-state [display board] (swap! latest-displayed-state assoc :states board))
  (display-winner [display board] (swap! latest-displayed-state assoc :winner (winner? board))))

(defrecord DummyInput []
  InputProtocol
  (get-move [input board] )
  (get-marker [input] )
  (get-board-size [input]  3 ))

(defmethod create-game :dummy-game [game-type input board ]
    [game-type input  board ])

(deftest human-vs-computer-test
  (let [input (->ConsoleInput)
        player-1 (->HumanPlayer marker-one input)
        player-2 (->AiPlayer marker-two)
        board (create-empty-board 3)
        test-game (create-game :human-vs-computer input board)]
    (test " returns a starting game state and players within a map")
      (is (= [player-1 player-2] (last test-game)))))

(deftest computer-vs-human-test
  (let [input (->ConsoleInput)
        player-1 (->AiPlayer marker-two)
        player-2 (->HumanPlayer marker-one input)
        board (create-empty-board 3)
        test-game  (create-game :computer-vs-human input board)]
    (test " returns a starting game state and players within a map")
      (is (= [player-1 player-2] (last test-game)))))

(deftest game-iterattion-test
   (let [starting-state { :board [["_" "o" "o" ]["x" "_" "_" ]["_" "_" "_" ]] :ai-marker "o"  :player-marker "x"}
         closing-state [["o" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
         terminal (->DummyDisplay)
          human (->HumanPlayer "x" (->ConsoleInput))
          ai (->AiPlayer "o")]
     (test "this test confirms the game follows the correct game interation sequences")
     (with-in-str "6" (game-runner starting-state terminal human ai))
     (is (= (:states @latest-displayed-state)  closing-state))))

(deftest game-iterattion-test
   (let [starting-state { :board [["_" "o" "o" ]["x" "_" "_" ]["_" "_" "_" ]] :ai-marker "o"  :player-marker "x"}
          closing-state [["o" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
          terminal (->DummyDisplay)
          human (->HumanPlayer "x" (->ConsoleInput))
          ai (->AiPlayer "o")]
     (test "this test confirms the game follows the correct game interation sequences")
     (with-in-str "6" (game-runner starting-state terminal human ai))
     (is (= (:winner @latest-displayed-state) (winner? closing-state)))))

(deftest game-intializer-test
   (let [display (->DummyDisplay)
         input (->DummyInput)
         game-type :dummy-game]
     (test "game intializes with correct arguments")
     (is (=  (with-out-str(with-in-str "3\n3\n9\n8\n"(game-intializer display input game-type)))))))



