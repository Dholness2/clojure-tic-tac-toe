(ns tic-tac-toe.human-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.display :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]))

(deftest check-validation-move
	(let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
  		  input 3]
    (testing "user input is valid choice"
      (is (= true (valid-selection input board))))))

(deftest get-move
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "gets players move"
	    (is (= (with-out-str (println "what is your next move"))  (with-out-str (with-in-str "4"(user-input-move board))))))))

(deftest human-move-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "updates board with a new move"
      (is (= [["_" "_" "_"] ["x" "_" "_"] ["_" "_" "_"]]   (with-in-str "4"(human-move board "x")))))))

(deftest human-record
  (let [player  (->HumanPlayer "x")]
    (testing "creates defrecord of player protocol"
      (is (= "x"  (.marker player))))))

(deftest human-record
  (let [player  (->HumanPlayer "x")
        board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "creates defrecord of player protocol"
      (is (= [["_" "_" "_"] ["x" "_" "_"] ["_" "_" "_"]] (next-move player board))))))

