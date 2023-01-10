(ns script
  (:require [reducers :refer :all]))

(def meaning-of-life (promise))
(future (println "The meaning of life is:" @meaning-of-life))
(deliver meaning-of-life 42)

(defn run [opts]
  (println "")
)