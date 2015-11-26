(ns tic-tac-toe.input-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.input :refer :all]
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

(deftest input-record
  (let [load (->ConsoleInput)
  		board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "creates defrecord of input protocol"
      (is (= 1 (with-out-str-value (with-in-str "1" (get-move load board ))))))))

(deftest input-record
  (let [load (->ConsoleInput)
  	     board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "creates defrecord of input protocol"
      (is (= [0 0] (with-out-str-value (with-in-str "1" (get-move load board))))))))

(deftest input-marker
  (let [load (->ConsoleInput)]
    (testing "gets the users marker selection"
      (is (= "x" (with-out-str-value (with-in-str "x" (get-marker load))))))))
