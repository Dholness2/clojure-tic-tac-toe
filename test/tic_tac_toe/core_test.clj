(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer [game-runner]]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]
            [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))

(deftest game-flow
  (let [human (->HumanPlayer "x")
        ai (->AiPlayer "o")
        terminal (->TerminalDisplay)
        board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (test "this checks the game flow for a win state for x")
      (is (= (str "\033[2J\n""Game Index\n123\n456\n789\n"
              "___\n___\n___\n"
              "what is your next move\n"
             "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "x__\n___\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xo_\n___\n___\n"
              "what is your next move\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xox\n___\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xox\n_o_\n___\n"
              "what is your next move\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xox\nxo_\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xox\nxo_\n_o_\n"
              "Game Winner: o\n")
             (with-out-str (with-in-str "1\n3\n4\n" (game-runner board terminal human ai)))))))



