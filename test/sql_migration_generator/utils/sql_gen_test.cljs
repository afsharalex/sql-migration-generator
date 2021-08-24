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
  (t/testing "table without relationships"
    (t/is (= "CREATE TABLE TestTable ( ID int , Name varchar(255) );\n"
             (sut/gen-migration {:tables [{:name "TestTable"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Name" :type "varchar(255)"}]}]
                                 :relationships []})))

    (t/is (= "CREATE TABLE TestTable ( ID int , Description TEXT );\n"
             (sut/gen-migration {:tables [{:name "TestTable"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Description" :type "TEXT"}]}]
                                 :relationships []})))
    )

  (t/testing "tables with many fields"
    (t/is (= "CREATE TABLE TestTable (\n\tID int,\n\tName varchar(255),\n\tDescription TEXT,\n\tThings int\n);"
             (sut/gen-migration {:tables [{:name "TestTable"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Name" :type "varchar(255)"}
                                                    {:name "Description" :type "TEXT"}
                                                    {:name "Things" :type "int"}]}]
                                 :relationships []})))
    )

  (t/testing "multiple tables without relationships"
    (t/is (= "CREATE TABLE Table1 ( ID int , Name varchar(255) );\nCREATE TABLE Table2 ( ID int , Description TEXT );\n"
             (sut/gen-migration {:tables [{:name "Table1"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Name" :type "varchar(255)"}]}
                                          {:name "Table2"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Description" :type "TEXT"}]}]
                                 :relationships []})))
    )
  )
