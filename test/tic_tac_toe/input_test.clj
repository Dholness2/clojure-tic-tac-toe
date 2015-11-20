(ns tic-tac-toe.input-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.input :refer :all]
            [tic-tac-toe.protocol.input :refer :all]))

(deftest console-get-input
    (testing "gets user input from console"
   	  (is (= 1   (with-in-str "1" (get-move))))))

(deftest input-record
  (let [load (->ConsoleInput)]
    (testing "creats defreacord of input protocol" 
      (is (= 1 (with-in-str "1" (get-input load)))))))
