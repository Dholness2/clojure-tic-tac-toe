(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.board :refer :all]))

(def board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]])
(def rowsize 3)

(deftest board-create
	(testing "Create new board based on user input"
		(is ( =  [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] (create-empty-board 3 3)))))

(deftest board-move
	(testing "creates a new vector with updated representation of the board"
	  (is ( = [["x" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] (move [0 0] "x" board)))))

(deftest valid-move-zer0
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= false (validmove? 0))))

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

(deftest valid-move-eight
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= true (validmove? 9))))


(deftest invalid-move-less-than-zero
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= false (validmove? -1))))

(deftest invalid-move-greater-than-eight
 (testing "test to mke sure user input is within game move option[0-8]")
 	(is (= false (validmove? 20))))

(deftest get-move
  (testing "gets players move"
	(is (= (with-out-str (println "what is your next move"))  (with-out-str (with-in-str "4"(user-input-move board rowsize)))))))

(deftest view-clear
	(testing "clears terminal"
	(is (= (with-out-str (print "\033[2J")) (with-out-str(clear-terminal))))))

(deftest view-show
	(testing "dispays the board"
	(is (= "___\n___\n___\n" (with-out-str (display-board board ))))))

(deftest computer-next-move 
	(testing "Computer next move"
	(is (=  [0 0] (computer-move board 3)))))

(deftest board-draw 
	(testing "game is a draw"
	(is (=  true (draw? [["x" "y" "y"] ["x" "y" "x"] ["Y" "x" "y"] ])))))

(deftest board-row-check 
	(testing "check for winner from row"
	(is (=  "x" (row-check [["x" "x" "x"] ["y" "x" "Y"] ["Y" "x" "y"] ])))))


(deftest board-colunm-check 
	(testing "check for winner from column"
	(is (=  "y" (column-check [["y" "x" "x"] ["y" "Y" "Y"] ["y" "x" "y"]])))))


(deftest determine-equality 
  (testing "checks for matches"
  (is (= true (check-equality ["x" "x" "x"] )))))

(deftest get-nested-elements 
  (testing "gets nested elements in board"
  (is (= '("y" "y" "y")(get-location [["y" "x" "x"] ["y" "y" "Y"] ["Y" "x" "y"]] [0 1 2 ] )))))

(deftest diagonal-checker 
	(testing "checks for any diagonal wins"
	(is (= "y" (diagonal-check [["y" "x" "_"]["x" "y" "x"]["x" "x" "y"]] 3)))))

(deftest winner-test 
   (testing "check for win state or draw") 
   (is (=  "x" (winner? [["x" "x" "x"] ["y" "x" "Y"] ["x" "x" "y"] ])))
   )

(deftest open-move
 (testing "checks to see if move is taken")
  (is (= false (moveopen? [["x" "_" "_"] ["_" "_" "_"] ["_" "_" "_"]] 1))))

(deftest game-flow
  (test "this checks the game flow for a win state for x")
  (is (= "welcome to don tic tac toe\n___\n___\n___\nwhat is your next move\n\033[2JX__\n___\n___\n\033[2JXO_\n___\n___\nwhat is your next move\n\033[2JXO_\nX__\n___\n\033[2JXOO\nX__\n___\nwhat is your next move\n\033[2JXOO\nX__\nX__\nGame Winner: X\n"
  (with-out-str (with-in-str "1\n4\n7\n8\n" (game-runner "welcome to don tic tac toe"))))))









 

		

