(ns tic-tac-toe.terminal-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.display.terminal :refer :all]
            [tic-tac-toe.protocol.display :refer :all]))
(deftest view-clear
  (testing "clears terminal"
    (is (= (with-out-str (println "\033[2J")) (with-out-str(clear-terminal))))))

(deftest print-test
  (testing "prints message out to terminal")
  (is (= (with-out-str (println "hello")) (with-out-str (print-message "hello")))))

(deftest empty-position-test
  (testing "returns true or false for empty positons")
  (is (=  true (empty-marker? "_"))))

(deftest empty-position-test-x
  (testing "returns true or false for empty positons")
  (is (=  false (empty-marker? "x"))))

(deftest empty-position-test-o
  (testing "returns true or false for empty positons")
  (is (=  false (empty-marker? "o"))))

(deftest add-column-test-small-right-test
  (let[small-right-column "  |"
     element "_"
     position 2]
  (testing " appends small right column element if element positon  is less than 9 ")
  (is (= (str 3 small-right-column) (add-column position element)))))

(deftest add-column-test-large-right-test
  (let[large-right-column " |"
     element "_"
     position 9]
  (testing " appends small right column element if element positon  is less than 9, if not then large ")
  (is (= (str 10 large-right-column) (add-column position element)))))

(deftest index-board-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
         indx 0]
    (testing "replaces board empty spaces with move index"
      (is (= '(("1  |" "2  |" "3  |") ("4  |" "5  |" "6  |") ("7  |" "8  |" "9  |")) (index-board board))))))

(deftest display-board-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "dispays the board"
      (is (= "| _ _ _\n| _ _ _\n| _ _ _\n" (with-out-str (display-board board)))))))

(deftest displays-game-interation
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "dispays iteraton"
      (is (= ( str "\033[2J" "\n| 1  | 2  | 3  |\n| 4  | 5  | 6  |\n| 7  | 8  | 9  |\n") (with-out-str (display-iteration board )))))))

(deftest print-winner-test
  (let [board [["x" "x" "x" ]["o" "_" "o" ]["_" "_" "_" ]]]
    (testing "print game winnner"
      (is (=  "Game Winner: x\n"  (with-out-str(print-winner board)))))))

(deftest display-record
  (let [display (->TerminalDisplay)
        board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
   (testing "creats defreacord of display protocol"
     (is (= "\033[2J" "\n| 1  | 2  | 3  |\n| 4  | 5  | 6  |\n| 7  | 8  | 9  |\n" (with-out-str (display-state display board)))))))

(deftest display-record
  (let [display (->TerminalDisplay)
        board [["x" "x" "x" ]["_" "_" "_" ]["_" "_" "_" ]]]
   (testing "creats defreacord of display protocol"
     (is (= "Game Winner: x\n" (with-out-str (display-winner display board)))))))
