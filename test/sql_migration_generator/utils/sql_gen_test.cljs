(ns sql-migration-generator.utils.sql-gen-test
  (:require [sql-migration-generator.utils.sql-gen :as sut]
            [cljs.test :as t :include-macros true]))


;; Define test for this structure:
;; {:tables [{:name "parent1"
;;            :fields [:id "Long" :name "String"]}
;;           {:name "child1"
;;            :fields [:id "Long" :name "String"]}
;;           {:name "child2"
;;            :fields [:id "Long" :description "Text"]}
;;           {:name "parent2"
;;            :fields [:id "Long" :body "Text"]}
;;           ]
;;  :join-tables [{"parent2_child2"
;;                 :fields [:parent2_id "Long"
;;                          :child2_id "Long"]}]
;;  :relationships [{"parent1"
;;                   :has-many ["child1"]
;;                   :has-one ["child2"]}
;;                  {"parent2"
;;                   :has-one ["child1"]}
;;                  ]}
;; to generate migrations.

(t/deftest sql-structure
  (t/testing "tables without relationships"
    (t/is (= 1 1))
    )
  )
