(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-runner set-markers ]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.input :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.input :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

(deftest game-iterattion-test
   (let [ iteration-one [["_" "o" "o" ]["x" "_" "_" ]["_" "_" "_" ]]
          iteration-two [["_" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
          iteration-three [["o" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
          terminal (->TerminalDisplay)
          human (->HumanPlayer "x" (->ConsoleInput))
          ai (->AiPlayer "o")]
   (test "this test confirms the game follows the correct game interation sequences")
      (is (= (str (with-out-str(display-state terminal iteration-one))
                  "what is your next move ?\n"
                  (with-out-str(display-state terminal iteration-two))
                  (with-out-str(display-state terminal iteration-three))
                  (with-out-str(display-winner terminal iteration-three)))) (with-out-str (with-in-str "6" (game-runner iteration-one terminal human ai))))))

(deftest set-markers-test
  (let [ board-empty [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
          terminal (->TerminalDisplay)
          human (->HumanPlayer "x" (->ConsoleInput))
          ai (->AiPlayer "o")
          marker-selection "x"
         input (->ConsoleInput)]
   (test "game allows you to swtich player markers")
     (is (=[#tic_tac_toe.human.HumanPlayer{:marker "x", :input-protocol #tic_tac_toe.input.ConsoleInput{}} #tic_tac_toe.ai.AiPlayer{:marker "o"}] (set-markers marker-selection input)))))

(deftest game-flow
  (let [human (->HumanPlayer "x" (->ConsoleInput))
        ai (->AiPlayer "o")
        terminal (->TerminalDisplay)
        iteration-one [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
        iteration-two [["x" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
        iteration-three [["x" "_" "_" ]["_" "o" "_" ]["_" "_" "_" ]]
        iteration-four [["x" "_" "x" ]["_" "o" "_" ]["_" "_" "_" ]]
        iteration-five [["x" "o" "x" ]["_" "o" "_" ]["_" "_" "_" ]]
        iteration-six [["x" "o" "x" ]["x" "o" "_" ]["_" "_" "_" ]]
        iteration-seven [["x" "o" "x" ]["x" "o" "_" ]["_" "o" "_" ]]]
    (test "this checks the game flow for a win state for o")
      (is (= (str (with-out-str(display-state terminal iteration-one))
                (with-out-str (with-in-str "1" (next-move human iteration-one)))
                (with-out-str(display-state terminal iteration-two))
                (with-out-str(display-state terminal iteration-three))
                (with-out-str (with-in-str "3" (next-move human iteration-three)))
                (with-out-str(display-state terminal iteration-four))
                (with-out-str(display-state terminal iteration-five))
                (with-out-str (with-in-str "4" (next-move human iteration-five)))
                (with-out-str(display-state terminal iteration-six))
                (with-out-str(display-state terminal iteration-seven))
                (with-out-str(display-winner terminal iteration-seven))) (with-out-str (with-in-str "1\n3\n4\n" (game-runner iteration-one terminal human ai)))))))












