(ns word-count)

(defn word-frequencies [words]
  (reduce 
    (fn [counts word] (assoc counts word (inc (get counts word 0))))
    {} words))

(defn get-words [text] (re-seq #"\w+" text))

(defn count-words-sequential [pages]
  (word-frequencies (mapcat get-words pages)))