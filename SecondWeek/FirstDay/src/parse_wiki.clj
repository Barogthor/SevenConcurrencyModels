(ns parse-wiki
  (:require [word-count :as wd]) )








(defn run [opts]   
	(println (wd/word-frequencies 
      (mapcat wd/get-words ["first second word" "word give sentence" "second john wonderful"]))
  ))
