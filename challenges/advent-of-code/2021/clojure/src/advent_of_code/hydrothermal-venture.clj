(ns advent-of-code.hydrothermal-venture
  (:require [clojure.string :as str]))

(defn puzzle []
  (vec (partition 4 (re-seq #"\d+" (slurp "resources/hydrothermal-venture.txt")))))

(defn part1 []
  (let [puzzle (puzzle)]
    (count (keep-indexed (fn [idx [x1 y1 x2 y2]]
                           ;; TODO: Check if the points intersect.
                           (some (fn [[c-x1 c-y1 c-x2 c-y2]])
                                 (into (subvec puzzle 0 idx) (subvec puzzle (inc idx))))) puzzle))))
