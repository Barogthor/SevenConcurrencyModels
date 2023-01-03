(ns parse-wiki 
  (:require [word-count :as wd] 
            [pages :refer :all] 
            [clojure.xml :as xml]
            [clojure.java.io :as io]
            [clojure.core.reducers :as r]))

(def pages ["one potato two potato three potato four" "five potato six potato seven potato more"])

(defn count-words-parallel [pages]
  (reduce (partial merge-with + )
    (pmap #(frequencies (wd/get-words %)) pages)))


(defn count-words [pages]
  (reduce (partial merge-with +)
    (pmap #(wd/count-words-sequential %) (partition-all 100 pages))))

(defn open [filename]
  (println (io/file filename)  )
)

(defn run [opts]   
  (println "Count parallel :")
	(time (count-words-parallel (get-pages "../dump-wiki.xml")) )
  (println "Count partition :")
	(time (count-words (get-pages "../dump-wiki.xml")) )
  ;;(open "../dump-wiki.xml")
  (shutdown-agents)
  ;;(open "../dump-wiki.xml")
)
