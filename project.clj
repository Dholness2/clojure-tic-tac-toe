(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [net.mikera/core.matrix "0.43.0"]]
  :main ^:skip-aot tic-tac-toe.core
  :target-path "target/%s"
  :aliases {"autotest" ["with-profile" "test" "auto" "test"]}
  :profiles {:test {:plugins [[lein-auto "0.1.2"]]}
             :uberjar {:aot :all}})
