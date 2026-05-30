(ns kyleerhabor.project-euler-multiples-of-3-or-5.core)

(defn sum [xs]
  (reduce + xs))

(defn divides? [a b]
  (int? (/ b a)))

(defn sums [n d]
  (sum (filter #(divides? d %) (range 0 n d))))

(defn theorem [n]
  (- (+ (sums n 3) (sums n 5)) (sums n 15)))

(defn -main []
  ;; If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these
  ;; multiples is 23.
  ;; 
  ;; Find the sum of all the multiples of 3 or 5 below 1000.
  (theorem 1000)
  (theorem 1000000)
  ;; On my 2019 MacBook Pro, this takes 10-15 seconds to evaluate.
  #_(theorem 1000000000))