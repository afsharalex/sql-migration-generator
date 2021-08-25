(ns sql-migration-generator.utils.sql-gen
  (:require [clojure.string :as str]))


;; CREATE TABLE customers (
;;     customer_id INT AUTO_INCREMENT PRIMARY KEY,
;;     customer_name VARCHAR(100)
;; );

;; Want to specify the type as foreign key, in some optional data.
;; Want to avoid specifying the field as well as the foreign key.
;; CREATE TABLE orders (
;;     order_id INT AUTO_INCREMENT PRIMARY KEY,
;;     customer_id INT,
;;     amount DOUBLE,
;;     FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
;; );
;; {:foreign_key "customer_id" :references {:table "customers" :column "customer_id"}}
;; A bit verbose.

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
