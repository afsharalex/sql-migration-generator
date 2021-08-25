(ns sql-migration-generator.utils.sql-gen-test
  (:require [sql-migration-generator.utils.sql-gen :as sut]
            [cljs.test :as t :include-macros true]))

(t/deftest validation
  (t/testing "empty map returns empty string"
    (t/is (= ""
             (sut/gen-migration {}))))

  (t/testing "empty tables returns empty string"
    (t/is (= ""
             (sut/gen-migration {:tables []}))))

  (t/testing "empty tables & empty relationships returns empty string"
    (t/is (= ""
             (sut/gen-migration {:tables []
                                 :relationships []}))))
  )

(t/deftest create-tables

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

  (t/testing "table with many fields"
    (t/is (= "CREATE TABLE TestTable (\n\tID int,\n\tName varchar(255),\n\tDescription TEXT,\n\tThings int\n);\n"
             (sut/gen-migration {:tables [{:name "TestTable"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Name" :type "varchar(255)"}
                                                    {:name "Description" :type "TEXT"}
                                                    {:name "Things" :type "int"}]}]
                                 :relationships []})))

    )

  (t/testing "table with many fields and table with two fields"
    (t/is (= "CREATE TABLE TestTable (\n\tID int,\n\tName varchar(255),\n\tDescription TEXT,\n\tThings int\n);\nCREATE TABLE TestTable2 ( ID int , Description TEXT );\n"
             (sut/gen-migration {:tables [{:name "TestTable"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Name" :type "varchar(255)"}
                                                    {:name "Description" :type "TEXT"}
                                                    {:name "Things" :type "int"}]}
                                          {:name "TestTable2"
                                           :fields [{:name "ID" :type "int"}
                                                    {:name "Description" :type "TEXT"}]}]
                                 :relationships []}))))

  (t/testing "multiple tables with many fields"
    (t/is (= "CREATE TABLE TestTable (\n\tID int,\n\tName varchar(255),\n\tDescription TEXT,\n\tThings int\n);\nCREATE TABLE TestTable2 (\n\tID int,\n\tDescription TEXT,\n\tName varchar(255)\n);\n"))
    )

  )
