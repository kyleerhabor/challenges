(ns kyleerhabor.project-euler-multiples-of-3-or-5.core)

(defn sum [xs]
  (reduce + xs))

(defn divides? [a b]
  (int? (/ b a)))

(defn multiple? [n]
  (or (divides? 3 n) (divides? 5 n)))

(defn theorem [n]
  (sum (filter multiple? (range 1 n))))

(defn -main []
  ;; If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these
  ;; multiples is 23.
  ;; 
  ;; Find the sum of all the multiples of 3 or 5 below .
  (theorem 1000))