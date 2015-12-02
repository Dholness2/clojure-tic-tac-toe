(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-runner set-markers opposite-marker game-intializer]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.input :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.input :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

; (defn latest-diplayed-state (atom []))

; (defrecord DummyDisplay []
;   DisplayProtocol
;   (display-state [display board]
;     (assoc @latest-diplayed-state board))
;   (display-winner [display board]))

(deftest game-iterattion-test
   (let [ iteration-one { :board [["_" "o" "o" ]["x" "_" "_" ]["_" "_" "_" ]] :ai-marker "o"  :player-marker "x"}
          iteration-two [["_" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
          iteration-three [["o" "o" "o" ]["x" "_" "x" ]["_" "_" "_" ]]
          terminal (->TerminalDisplay)
          human (->HumanPlayer "x" (->ConsoleInput))
          ai (->AiPlayer "o")]
   (test "this test confirms the game follows the correct game interation sequences")
      (is (= (str (with-out-str (display-state terminal iteration-one))
                  "what is your next move ?\n"
                  (with-out-str (display-state terminal iteration-two))
                  (with-out-str (display-state terminal iteration-three))
                  (with-out-str (display-winner terminal iteration-three))))
      (with-out-str
        (with-in-str "6" (game-runner iteration-one terminal human ai))))))

(deftest set-game-markers
  (test "returns the avilable marker based on input")
   (is (=  "x" (opposite-marker "o"))))


; (let [latest-diplayed-board (get @latest-diplayed-state 0)])

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
        input (->ConsoleInput)
        board (create-empty-board 3)
        iteration-one { :board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-two { :board[["x" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-three { :board [["x" "_" "_" ]["_" "o" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-four { :board [["x" "_" "x" ]["_" "o" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-five { :board[["x" "o" "x" ]["_" "o" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-six { :board[["x" "o" "x" ]["x" "o" "_" ]["_" "_" "_" ]]  :ai-marker "o"  :player-marker "x"}
        iteration-seven { :board[["x" "o" "x" ]["x" "o" "_" ]["_" "o" "_" ]]  :ai-marker "o"  :player-marker "x"}]
    (test "this checks the game flow for a win state for o")
      (is (= (str "Select your marker x or o \n"
                  (with-out-str (display-state terminal (iteration-one :board)))
                  (with-out-str (with-in-str "1" (next-move human iteration-one)))
                  (with-out-str (display-state terminal (iteration-two :board)))
                  (with-out-str (display-state terminal (iteration-three :board)))
                  (with-out-str (with-in-str "3" (next-move human iteration-three)))
                  (with-out-str (display-state terminal (iteration-four :board)))
                  (with-out-str (display-state terminal (iteration-five :board)))
                  (with-out-str (with-in-str "4" (next-move human iteration-five)))
                  (with-out-str (display-state terminal (iteration-six :board)))
                  (with-out-str (display-state terminal (iteration-seven :board)))
                  (with-out-str (display-winner terminal (iteration-seven :board)))) (with-out-str (with-in-str "x\n1\n3\n4\n" (game-intializer  terminal input board)))))))












