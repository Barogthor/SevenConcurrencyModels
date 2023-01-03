(ns script
  (:require [reducers :refer :all]))


(defn run [opts]
  (println (my-reduce + [0 1 2 3 4 5]) )
  (println (into [] (my-map (partial + 1) [1 2 3 4])))
  (println (into [] (my-map (partial * 2) (my-map (partial + 1) [1 2 3 4]))))
)