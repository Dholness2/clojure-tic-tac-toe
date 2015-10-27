(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))
(def board ["_" "_" "_" "_" "_" "_" "_" "_" "_"])

(deftest board-move
	(testing "Create new board based on user input"
		(is ( =  ["_" "_" "_" "_" "_" "_" "_" "_" "_"] ( create-empty-board )))))
(deftest valid-move
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 0))))

(deftest get-move
  (testing "gets players move"
	(is (= 4 (with-in-str "4"(user-input-move))))))

(deftest view-clear
	(testing "clears terminal"
	(is (= (println "\033[2J" ) (clear-terminal)))))

(deftest view-show
	(testing "dispays the board"
	( is(= (println board)(display-board board)))))

(deftest computer-next-move 
	(testing "Computer next move"
	(is (=  0 (computer-move board)))))



; (deftest game-runner
; 	(testing "gets user input and updates the board"
;   (is (= (println ""))

		

