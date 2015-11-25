(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-runner set-markers ]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

(deftest game-iterattion-test 
   (let [ board-winner [["_" "o" "o" ]["x" "_" "_" ]["_" "_" "_" ]]
          terminal (->TerminalDisplay)
          human (->HumanPlayer "x")  
          ai (->AiPlayer "o")]
   (test "this test confirms the game folloes the correct game interation sequences")
      (is (= (str (with-out-str(clear-terminal)) 
                  (with-out-str(display-index board-winner)) 
                  "_oo\nx__\n___\nwhat is your next move\n"
                  (with-out-str(clear-terminal)) 
                  (with-out-str(display-index board-winner)) 
                  "_oo\nx_x\n___\n" 
                  (with-out-str(clear-terminal))
                   (with-out-str(display-index board-winner)) 
                  "ooo\nx_x\n___\n"
                  "Game Winner: o\n")  
            (with-out-str (with-in-str "6" (game-runner board-winner terminal human ai)))))))

(deftest set-markers-test
  (let [ board-empty [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
          terminal (->TerminalDisplay)
          human (->HumanPlayer "x")  
          ai (->AiPlayer "o")
          marker-selection "x"]  
      (test "game allows you to swtich player order")
      (is (= [#tic_tac_toe.human.HumanPlayer{:marker "x"} #tic_tac_toe.ai.AiPlayer{:marker "o"}] (set-markers marker-selection)))))

(deftest game-flow
  (let [human (->HumanPlayer "x")
        ai (->AiPlayer "o")
        terminal (->TerminalDisplay)
        board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (test "this checks the game flow for a win state for x")
      (is (= (str 
              (with-out-str(clear-terminal)) 
              (with-out-str(display-index board)) 
              "___\n___\n___\n"
              "what is your next move\n"
             (with-out-str(clear-terminal)) 
             (with-out-str(display-index board))
              "x__\n___\n___\n"
             (with-out-str(clear-terminal)) 
             (with-out-str(display-index board))
              "xo_\n___\n___\n"
              "what is your next move\n"
             (with-out-str(clear-terminal)) 
             (with-out-str(display-index board))
              "xox\n___\n___\n"
              (with-out-str(clear-terminal)) 
              (with-out-str(display-index board))
              "xox\n_o_\n___\n"
              "what is your next move\n"
              (with-out-str(clear-terminal)) 
              (with-out-str(display-index board))
              "xox\nxo_\n___\n"
              (with-out-str(clear-terminal)) 
              (with-out-str(display-index board))
              "xox\nxo_\n_o_\n"
              "Game Winner: o\n")
             (with-out-str (with-in-str "1\n3\n4\n" (game-runner board terminal human ai)))))))






