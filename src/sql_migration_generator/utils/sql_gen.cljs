(ns sql-migration-generator.utils.sql-gen)

;; Need to think about the structure for defining models and relationships.
;; Something like:
;; [:parent {:id "Long" :name "String"}
;;     [:child1 {:id "Long" :name "String"}]
;;     [:child2 {:id "Long" :description "Text"}]]
;; To define a table called `parent` that contains many of `child1` and `child2`,
;; but how would we define one-to-one?

;; What if we did this:
;; [:root "parent" {:id "Long" :name "String"}
;;     [:one "child1" {:id "Long" :name "String"}]
;;     [:many "child2" {:id "Long" :description "Text"}]]

;; That wouldn't work, or we'd have to redefine tables for multiple parents...
;; What about this:
;; {:tables [{:name "parent1"
;;            :fields [:id "Long" :name "String"]}
;;           {:name "child1"
;;            :fields [:id "Long" :name "String"]}
;;           {:name "child2"
;;            :fields [:id "Long" :description "Text"]}
;;           {:name "parent2"
;;            :fields [:id "Long" :body "Text"]}
;;           ]
;;  :relationships [{"parent1"
;;                   :has-many ["child1"]
;;                   :has-one ["child2"]}
;;                  {"parent2"
;;                   :has-many ["child2"]
;;                   :has-one ["child1"]}
;;                  ]}
;; Would this work?
;; I am trying to avoid having the user define join-tables, but it may
;; make sense for them to atleast have the ability to define it if they want,
;; and provide syntactic sugar to automatically define it, if possible.
