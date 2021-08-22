(ns sql-migration-generator.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [sql-migration-generator.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
