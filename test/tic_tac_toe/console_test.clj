(ns tic-tac-toe.console-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.input.console :refer :all]
            [tic-tac-toe.protocol.input :refer :all]))

(defmacro with-out-str-value
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (let [v# ~@body]
         v#))))

(deftest check-validation-move
	(let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
  		  input 3]
    (testing "user input is valid choice"
      (is (= true (valid-selection input board))))))

(deftest user-marker-test
  (testing "gets the users marker and confirms is an o or x"
	(is (= "x" (with-out-str-value (with-in-str "x" (user-marker)))))))

(deftest console-get-input
    (testing "gets user input from console"
   	  (is (= 1  (with-out-str-value (with-in-str "1" (prompt-terminal "next move")))))))

(deftest console-get-input
	(let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "gets user input from console"
   	  (is (= [0 0]  (with-out-str-value (with-in-str "1" (user-input-move board))))))))

(deftest test-diemension
  (testing "return conslole supplied selection of board diemension")
    (is ( = 4 (with-out-str-value (with-in-str "4" (get-board-diemension))))))

(deftest ConsoleInput-get-move
  (let [input (->ConsoleInput)
  	     board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "test if #get-move returns user move selection based on provided input"
      (is (= [0 0] (with-out-str-value (with-in-str "1" (get-move input board))))))))

(deftest ConsoleInput-get-marker
  (let [input (->ConsoleInput)]
    (testing "test #get-marker function returns sleceted marker x or o"
      (is (= "x" (with-out-str-value (with-in-str "x" (get-marker input))))))))

(deftest ConsoleInput-get-board-size
  (let [input (->ConsoleInput)]
    (testing "test #get-board-size function returns size"
      (is (= 7 (with-out-str-value (with-in-str "7" (get-board-size input))))))))


