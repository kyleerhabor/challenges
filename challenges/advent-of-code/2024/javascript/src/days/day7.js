import { decrement, readInput } from "../core.js";

const OP_ADD = 0;
const OP_MULTIPLY = 1;
const OP_CONCATENATE = 2;

function digitCount(x) {
  // https://stackoverflow.com/a/28203456
  return (Math.log10((x ^ (x >> 31)) - (x >> 31)) | 0) + 1;
}

function shift(a, b) {
  return a * Math.pow(10, digitCount(b)) + b;
}

function apply(op, a, b) {
  switch (op) {
    case OP_ADD:
      return a + b;
    case OP_MULTIPLY:
      return a * b;
    case OP_CONCATENATE:
      return shift(a, b);
  }
}

function permutation(n, base) {
  const count = Math.pow(base, n);
  let result = new Array(count);

  for (let i = 0; i < count; i++) {
    let row = new Array(n);

    for (let j = 0; j < n; j++) {
      row[j] = Math.floor((i / Math.pow(base, j)) % base);
    }

    result[i] = row;
  }

  return result;
}

export async function part1() {
  const input = await readInput("inputs/day7.txt");
  const sum = input
    .slice(0, -1) // EOL
    .split("\n")
    .reduce((sum, line) => {
      const [value, numbers] = line.split(": ");
      const val = parseInt(value, 10);
      const nums = numbers.split(" ");
      // We could optimize this by evaluating from most to least and short-circuiting. For example, if
      // a * b * c * d * e < x, then there's no need to test the smaller values.
      const ops = permutation(nums.length - 1, 2);
      const flag = ops.some((op) => {
        // This accounts for the initial value so we don't need to explicitly check.
        const op2 = [OP_ADD, ...op];

        return val === nums.reduce(
          (x, num, i) => apply(
            op2[i],
            x,
            parseInt(num, 10)
          ),
          0
        );
      });

      return sum + flag * val;
    }, 0);

  console.log(sum);
}

export async function part2() {
  const input = await readInput("inputs/day7.txt");
  const sum = input
    .slice(0, -1) // EOL
    .split("\n")
    .reduce((sum, line) => {
      const [value, numbers] = line.split(": ");
      const val = parseInt(value, 10);
      const nums = numbers.split(" ");
      // We could optimize this by evaluating from most to least and short-circuiting. For example, if
      // a * b * c * d * e < x, then there's no need to test the smaller values.
      const ops = permutation(nums.length - 1, 3);
      const flag = ops.some((op) => {
        // This accounts for the initial value so we don't need to explicitly check.
        const op2 = [OP_ADD, ...op];

        return val === nums.reduce(
          (x, num, i) => apply(
            op2[i],
            x,
            parseInt(num, 10)
          ),
          0
        );
      });

      return sum + flag * val;
    }, 0);

  console.log(sum);
}
