(ns script
  (:require [reducers :refer :all]))


(defn run [opts]
  (println (my-reduce + [0 1 2 3 4 5]) )
)