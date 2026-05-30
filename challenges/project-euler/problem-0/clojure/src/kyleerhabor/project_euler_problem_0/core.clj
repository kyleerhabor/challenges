(ns kyleerhabor.project-euler-problem-0.core)

(defn square [x]
  (* x x))

(defn sum [xs]
  (reduce + xs))

(defn theorem [n]
  (sum (filter odd? (map square (range 1 (inc n))))))

(defn -main []
  ;; A number is a perfect square, or a square number, if it is the square of a positive integer.
  ;; For example, 25 is a square number because 5^2 = 5 * 5 = 25; it is also an odd square.
  ;; 
  ;; The first 5 square numbers are: 1, 4, 9, 16, 15, and the sum of the odd squares is 1 + 9 + 25.
  ;; 
  ;; Among the first 536 thousand square numbers, what is the sum of all the odd squares?
  (theorem 536000))