(ns sql-migration-generator.utils.sql-gen
  (:require [clojure.string :as str]))

(defn gen-table-migration
  "Generate a single table migration statement."
  [table]
  (let [{:keys [name fields]} table]
    (if (<= (count fields) 2)
      (str "CREATE TABLE " name
           " ( "
           (:name (first fields))
           " "
           (:type (first fields))
           " , "
           (:name (last fields))
           " "
           (:type (last fields))
           " );\n")

      (str "CREATE TABLE " name " (\n"
           (str/join ",\n"
                     (reduce (fn [acc field]
                               (conj acc
                                     (str "\t"
                                          (:name field)
                                          " "
                                          (:type field))))
                             [] fields))
           "\n);\n")
      )))


(defn gen-migration
  "Generate migrations for supplied data definitions structure."
  [definitions]
  (reduce (fn [acc table]
            (str acc (gen-table-migration table)))
          "" (:tables definitions))
  )
