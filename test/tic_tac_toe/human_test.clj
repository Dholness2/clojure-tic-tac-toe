(ns tic-tac-toe.human-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :refer :all]
            [tic-tac-toe.input :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.protocol.player :refer :all]
            [tic-tac-toe.protocol.input :refer :all]))

(defmacro with-out-str-value
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (let [v# ~@body]
         v#))))

(deftest human-move-test
  (let [board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]]]
    (testing "updates board with a new move"
      (is (= [["_" "_" "_"] ["x" "_" "_"] ["_" "_" "_"]] (with-out-str-value (with-in-str "4"(human-move board "x" (->ConsoleInput)))))))))

(deftest human-record
  (let [player  (->HumanPlayer "x")]
    (testing "creates defrecord of player protocol"
      (is (= "x"  (.marker player))))))

(deftest human-record
  (let [player  (->HumanPlayer "x" (->ConsoleInput))
        game {:board [["_" "_" "_" ]["_" "_" "_" ]["_" "_" "_" ]] :ai-marker "o"  :player-marker "x"}]
    (testing "creates defrecord of player protocol"
      (is (= ( assoc game :board [["_" "_" "_"] ["x" "_" "_"] ["_" "_" "_"]]) (with-out-str-value (with-in-str "4" (next-move player game))))))))

