(ns tic-tac-toe.inputoutput-test
  (:require [clojure.test :refer :all]
  	        [tic-tac-toe.inputoutput :refer :all]))

(deftest input-test 
 (testing "gets  to user's console"
 (is (= "4"  (with-in-str "4" (get-input))))))


(deftest output-test
 (testing "send user output"
 (is (= "what is your next move ? ") (with-out-str (send-output "what is your next move ?")))))

; (defn fake-send-output [string-to-print]
; 	(add-to-send-messages string-to-print))

; (defn prompt [message]
; 	(send-output message)
; 	(receive-input))

; (with-redefs [send-output fake-send-output]
; 	(prompt "some message")
; 	(assert "some message" (last sent-messages)))