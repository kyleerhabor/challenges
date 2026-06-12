(ns advent-of-code.binary-diagnostic
  (:require [clojure.string :as str]))

(defn puzzle []
  (str/split-lines (slurp "resources/binary-diagnostic.txt")))

(defn part1 []
  (let [diag (reduce #(let [freq (frequencies %2)]
                        (if (> (freq \1) (freq \0))
                          (-> %1
                              (update :gamma str \1)
                              (update :epsilon str \0))
                          (-> %1
                              (update :gamma str \0)
                              (update :epsilon str \1))))
                     {:gamma ""
                      :epsilon ""}
                     (apply map vector (puzzle)))]
    (* (Long/parseLong (:gamma diag) 2) (Long/parseLong (:epsilon diag) 2))))

(defn part2-part [puzzle idx common?]
  (let [freq (frequencies (map #(get % idx) puzzle))
        bit (if ((if common?
                   >=
                   <) (freq \1) (freq \0))
              \1
              \0)
        matches (filter #(= bit (get % idx)) puzzle)]
    (if (= 1 (count matches))
      (first matches)
      (recur matches (+ 1 idx) common?))))

(defn part2 []
  (let [puzzle (puzzle)]
    (* (Long/parseLong (part2-part puzzle 0 true) 2)
       (Long/parseLong (part2-part puzzle 0 false) 2))))
