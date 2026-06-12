(ns advent-of-code.dive
  (:require [clojure.string :as str]))

(defn data []
  (map #(str/split % #" ") (str/split-lines (slurp "resources/dive.txt"))))

(defn part1 []
  (let [points {"forward" [:x +]
                "up" [:y -]
                "down" [:y +]}
        graph (reduce (fn [m [pos val]]
                        (apply update m (conj (points pos) (Long/parseLong val))))
                      {:x 0
                       :y 0}
                      (data))]
    (* (:x graph) (:y graph))))

(defn part2 []
  (let [graph (reduce (fn [m [pos val]]
                        (let [val (Long/parseLong val)]
                          (case pos
                            "up" (update m :aim - val)
                            "down" (update m :aim + val)
                            "forward" (-> m
                                          (update :x + val)
                                          (update :y - (* (:aim m) val))))))
                      {:aim 0
                       :x 0
                       :y 0}
                      (data))]
    (* (:x graph) (- (:y graph)))))
