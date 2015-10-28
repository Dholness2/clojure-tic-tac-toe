(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))
(def board ["_" "_" "_" "_" "_" "_" "_" "_" "_"])

(deftest board-move
	(testing "Create new board based on user input"
		(is ( =  ["_" "_" "_" "_" "_" "_" "_" "_" "_"] ( create-empty-board )))))
(deftest valid-move-zer0
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 0))))

(deftest valid-move-one
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 1))))

(deftest valid-move-two
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 2))))

(deftest valid-move-three
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 3))))

(deftest valid-move-four
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 4))))

(deftest valid-move-five
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 5))))

(deftest valid-move-six
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 6))))

(deftest valid-move-seven
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 7))))

(deftest valid-move-eight
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 8))))


(deftest invalid-move-less-than-zero
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= false (validmove? -1))))

(deftest invalid-move-greater-than-eight
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= false (validmove? 20))))

(deftest get-move
  (testing "gets players move"
	(is (= 4 (with-in-str "4"(user-input-move))))))

(deftest view-clear
	(testing "clears terminal"
	(is (= (println "\033[2J" ) (clear-terminal)))))

(deftest view-show
	(testing "dispays the board"
	( is(= (println board)(display-board board 3 )))))

(deftest computer-next-move 
	(testing "Computer next move"
	(is (=  0 (computer-move board)))))

(deftest board-draw 
	(testing "game is a draw"
	(is (=  true (draw? ["x" "y" "y" "x" "x" "Y" "Y" "x" "y" ])))))

(deftest board-row-check 
	(testing "game is a draw"
	(is (=  "x" (row-check ["x" "x" "x" "y" "x" "Y" "Y" "x" "y" ] 3 )))))


(deftest board-colunm-check 
	(testing "game is a draw"
	(is (=  "x" (column-check ["y" "x" "x" "y" "x" "Y" "Y" "x" "y" ] 3 3 )))))







; (deftest game-runner
; 	(testing "gets user input and updates the board"
;   (is (= (println ""))

		

