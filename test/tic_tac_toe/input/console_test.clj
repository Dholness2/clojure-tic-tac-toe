(ns tic-tac-toe.input.console-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.input.console :refer :all]
            [tic-tac-toe.protocol.input :refer :all]))

(defmacro with-out-str-value
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (let [v# ~@body]
         v#))))

(deftest check-input-test
  (testing "for valid input"
    (is (= false (invalid-input? "4")))))

(deftest check-input-test-invalid
  (testing "for invalid input"
    (is (= true (invalid-input? "")))))

(deftest check-input-test-invalid
  (testing "for invalid input"
    (is (= true(invalid-input? "#$#$")))))

(deftest check-validation-move
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]
        input 3]
    (testing "user input is valid choice"
      (is (= true (valid-selection input board))))))

(deftest console-get-input
    (testing "gets user input from console"
      (is (= 1  (with-out-str-value (with-in-str "1" (prompt-terminal "next move")))))))

(deftest console-get-input
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "gets user input from console"
      (is (= [0 0]  (with-out-str-value (with-in-str "1" (user-input-move board))))))))

(deftest test-diemension
  (testing "return conslole supplied selection of board diemension"
    (is ( = 4 (with-out-str-value (with-in-str "4"(get-board-dimension)))))))

(deftest get-game-type-test
  (let [games [":computer-vs-human"  ":human-vs-computer"]]
    (testing "prompts the user to selecta specfic game type and returns its number index"
      ( is ( = 0  (with-out-str-value (with-in-str "1" (get-game-selection games))))))))

(deftest ConsoleInput-get-move
  (let [input (->ConsoleInput)
        board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "test if #get-move returns user move selection based on provided input"
      (is (= [0 0] (with-out-str-value (with-in-str "1" (get-move input board))))))))

(deftest ConsoleInput-get-game-type-test
  (let [input (->ConsoleInput)
        games [":computer-vs-human"  ":human-vs-computer"]]
    (testing "test if the protocol gets selected game type and returns the selected games index"
      (is (= 0 (with-out-str-value (with-in-str "1" (get-game-type input games))))))))

(deftest ConsoleInput-get-board-size
  (let [input (->ConsoleInput)]
    (testing "test #get-board-size function returns size"
      (is (= 4 (with-out-str-value (with-in-str "4" (get-board-size input))))))))

(deftest ConsoleInput-assign-board-size
  (let [input (->ConsoleInput)]
    (testing "returns only valid board sizes"
      (is (= 3 (with-out-str-value (with-in-str "x\n3" (get-board-size input))))))))
