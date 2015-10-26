(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))
(def board { :1 "_"
                   :2 "_"
                   :3 "_" 
                   :4 "_" 
                   :5 "_" 
                   :6 "_" 
                   :7 "_" 
                   :8 "_" 
                   :9 "_"})
(def player-one "X")
(def Player-two "Y")




(deftest board-move
	(testing "Create new board based on user input"
		(is ( =  {:4 "_", :7 "_", :1 "_", :8 "_", :9 "_", :2 "_", :5 "X", :3 "_", :6 "_"} ( move "5" player-one  board )))))


(deftest get-move
  (testing "gets players move"
	(is (= "5" (with-in-str "5" (user-input-move))))))


(deftest print-board
	(testing "prints boards current state"
	(is (= (println "____\n____\n____\n" ) (display-board board)))))


