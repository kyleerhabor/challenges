import { readInput } from "../core.js";

const NUMBER_SEPARATOR = "\n";
const NUMBER_DIVIDER = " ".repeat(3);

export async function part1() {
  const input = await readInput("inputs/day1.txt");
  const numbers = input
    .split(NUMBER_SEPARATOR)
    .map((s) => s.split(NUMBER_DIVIDER))
    .flat();

  const count = numbers.length / 2;
  let leftList = [];
  let rightList = [];
  const iterator = numbers[Symbol.iterator]();

  while (true) {
    const left = iterator.next();

    if (left.done) {
      break;
    }

    leftList.push(parseInt(left.value, 10));

    const right = iterator.next();

    if (right.done) {
      break;
    }

    rightList.push(parseInt(right.value, 10));
  }

  leftList.sort();
  rightList.sort();

  let sum = 0;

  for (let i = 0; i < count; i++) {
    const left = leftList[i];
    const right = rightList[i];
    const diff = Math.abs(left - right);

    sum += diff;
  }

  console.log(sum);
}

export async function part2() {
  const input = await readInput("inputs/day1.txt");
  const numbers = input
    .split(NUMBER_SEPARATOR)
    .map((s) => s.split(NUMBER_DIVIDER));
  
  let nums = [];
  let similarity = {};

  for (const [left, right] of numbers) {
    nums.push(parseInt(left, 10));

    const number = parseInt(right, 10);

    similarity[number] = (similarity[number] || 0) + 1;
  }

  const score = nums
    .map(number => number * (similarity[number] || 0))
    .reduce((sum, number) => sum + number);

  console.log(score);
}