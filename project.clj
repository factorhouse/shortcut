(defproject io.factorhouse/shortcut "0.1.0"

  :description "Shortcut by Factor House"

  :min-lein-version "2.9.0"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.clojure/core.async "1.3.622"]
                 [org.clojure/tools.reader "1.3.6"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [ch.qos.logback/logback-classic "1.2.6"]
                 [org.slf4j/slf4j-api "1.7.32"]
                 [cheshire "5.10.1"]]

  :clean-targets ^{:protect false} ["resources/public/js" "dev-resources/public/js" :target-path ".shadow-cljs"]

  :profiles {:cljs    {:dependencies [[thheller/shadow-cljs "2.15.10" :exclusions [commons-codec]]
                                      [com.cognitect/transit-cljs "0.8.269"]]}
             :dev     {:resource-paths ["dev-resources"]
                       :plugins        [[lein-cljfmt "0.8.0" :exclusions [org.clojure/clojure]]
                                        [lein-shell "0.5.0"]]
                       :dependencies   [[clj-kondo "2021.09.25" :exclusions [com.fasterxml.jackson.core/jackson-core]]]}
             :uberjar {:prep-tasks  ["clean" ["shell" "lein" "release-cljs"] "javac" "compile"]
                       :aot         :all
                       :omit-source true}
             :smoke   {:pedantic? :abort}}

  :aliases {"check"        ["with-profile" "+smoke" "check"]
            "fmt"          ["with-profile" "+smoke" "cljfmt" "check"]
            "fmtfix"       ["with-profile" "+smoke" "cljfmt" "fix"]
            "kondo"        ["with-profile" "+smoke" "run" "-m" "clj-kondo.main" "--lint" "src"]
            "smoke"        ["with-profile" "+smoke,+cljs" ["do" ["clean"] ["check"] ["cljfmt" "check"] ["run" "-m" "clj-kondo.main" "--lint" "src"] ["test"]]]
            "release-cljs" ["with-profile" "+cljs" "run" "-m" "shadow.cljs.devtools.cli" "release" "app"]
            "live"         ["with-profile" "+cljs" "run" "-m" "shadow.cljs.devtools.cli" "watch" "live"]
            "demo"         ["with-profile" "+cljs" "run" "-m" "shadow.cljs.devtools.cli" "release" "live"]}

  :source-paths ["src"]
  :test-paths ["test"]

  :uberjar-name "shortcut-standalone.jar"
  :main shortcut
  :aot [shortcut]

  :java-source-paths ["src-java"]
  :javac-options ["-target" "11" "-source" "11" "-Xlint:-options"]

  :pedantic? :warn)
