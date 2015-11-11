(ns tic-tac-toe.display-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.display :refer :all]))
(deftest view-clear
  (testing "clears terminal"
    (is (= (with-out-str (println "\033[2J")) (with-out-str(clear-terminal))))))

(deftest view-show
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "dispays the board"
      (is (= "___\n___\n___\n" (with-out-str (display-board board )))))))

(deftest displays-game-interation
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "dispays iteraton"
	  (is (= ( str "\033[2J\n" "Game Index\n123\n456\n789\n" "___\n___\n___\n") (with-out-str (display-iteration board )))))))
(deftest print-winner-test
  (let [board [["x" "x" "x" ]["o" "_" "o" ]["_" "_" "_" ]]]
    (testing "print game winnner"
      (is (=  "Game Winner: x\n"  (with-out-str(print-winner board)))))))