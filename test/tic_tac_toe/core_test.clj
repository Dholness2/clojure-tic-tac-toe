(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.display :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.ai :refer :all]))

  (deftest game-flow
    (test "this checks the game flow for a win state for x")
      (is (= (str "\033[2J\n""Game Index\n123\n456\n789\n"
              "___\n___\n___\n"
              "what is your next move\n"
             "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "x__\n___\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "x__\n_o_\n___\n"
              "what is your next move\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xx_\n_o_\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xxo\n_o_\n___\n"
              "what is your next move\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xxo\nxo_\n___\n"
              "\033[2J\n"
              "Game Index\n123\n456\n789\n"
              "xxo\nxo_\no__\n"
              "Game Winner: o\n")
             (with-out-str (with-in-str "1\n2\n4\n" (game-runner  [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]))))))



