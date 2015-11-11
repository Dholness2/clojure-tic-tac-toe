(ns tic-tac-toe.human-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.display :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.human :refer :all]))

(deftest check-validation-move
	(let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
  		  input 3]
    (testing "user input is valid choice"
      (is (= true (valid-selection input board))))))

(deftest get-move
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
  		  rowsize 3]
    (testing "gets players move"
	    (is (= (with-out-str (println "what is your next move"))  (with-out-str (with-in-str "4"(user-input-move board rowsize))))))))