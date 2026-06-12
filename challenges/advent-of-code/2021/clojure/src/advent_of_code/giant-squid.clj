(ns advent-of-code.giant-squid
  (:require [clojure.string :as str]))

(defn puzzle []
  (let [[numbers & boards] (str/split (slurp "resources/giant-squid.txt") #"\n\n")]
    {:numbers (str/split numbers #",")
     :boards (map (partial re-seq #"\d+") boards)}))

(defn bingo? [vs]
  (some (fn [v] (every? #(str/ends-with? % "+") v))
        vs))

(defn part1 []
  (loop [{:keys [boards]
          [n & nums] :numbers} (puzzle)]
    (let [boards (map (partial map #(if (= n %)
                                      (str % "+")
                                      %)) boards)]
      (if-let [board (first (filter #(or (bingo? (partition 5 %))
                                         (bingo? (apply map vector (partition 5 %)))) boards))]
        (* (Long/parseLong n) (->> board
                                   (filter #(not (str/ends-with? % "+")))
                                   (map #(Long/parseLong %))
                                   (reduce +)))
        (recur {:numbers nums
                :boards boards})))))

(defn part2 []
  ;; This implementation sucks, but I want to get this over with.
  (loop [{:keys [boards]
          [n & nums] :numbers} (puzzle)]
    (let [bs (->> boards
                      (map (partial map #(if (= n %)
                                           (str % "+")
                                           %)))
                      (remove #(or (bingo? (partition 5 %))
                                   (bingo? (apply map vector (partition 5 %))))))]
      (if (and (= 1 (count boards))
               (empty? bs))
        (* (Long/parseLong n) (->> (first boards)
                                   (filter #(not (str/ends-with? % "+")))
                                   (map #(Long/parseLong %))
                                   (reduce +)
                                   (+ (- (Long/parseLong n)))))
        (recur {:numbers nums
                :boards bs})))))
