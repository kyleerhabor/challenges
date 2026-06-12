import * as fs from "node:fs/promises";

export function increment(x) {
  return x + 1;
}

export function decrement(x) {
  return x - 1;
}

export function add(a, b) {
  return a + b;
}

export async function readInput(path) {
  const filePath = `${process.cwd()}/${path}`;
  const contents = fs.readFile(filePath, { encoding: "utf8" });

  return contents;
}
