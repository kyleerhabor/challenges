import { decrement, increment, readInput } from "../core.js";

const WORD = "XMAS";

function reverseIndex(length, i) {
  return length - i - 1;
}

const wordState = {
  index: -1,
  string: ""
};

function next(state) {
  const reset = { ...state, ...wordState };

  if (state.string[state.index] !== WORD[state.index]) {
    return reset;
  }

  if (state.string.length !== WORD.length) {
    return state;
  }

  return {
    ...reset,
    count: increment(state.count)
  };
}

const word = {
  ...wordState,
  count: 0
};

export async function part1() {
  const input = await readInput("inputs/day4.txt");
  const stridei = input.indexOf("\n");
  const stride = increment(stridei);
  const lineCount = input.length / stridei;
  let forward = Object.assign({}, word);
  let back = Object.assign({}, word);

  // Horizontal, vertical.
  for (let y = 0; y < lineCount - 1; y++) {
    for (let x = 0; x < stridei; x++) {
      const f = input[x + y * stride];
      const b = input[reverseIndex(stridei, x) + y * stride];

      forward = next({
        ...forward,
        index: increment(forward.index),
        string: forward.string + f
      });

      back = next({
        ...back,
        index: increment(back.index),
        string: back.string + b
      });
    }
  }

  Object.assign(forward, wordState);
  Object.assign(back, wordState);

  // Vertical, horizontal.
  for (let x = 0; x < stridei; x++) {
    for (let y = 0; y < lineCount - 1; y++) {
      const f = input[x + y * stride];
      const b = input[x + reverseIndex(stridei, y) * stride];

      forward = next({
        ...forward,
        index: increment(forward.index),
        string: forward.string + f
      });

      back = next({
        ...back,
        index: increment(back.index),
        string: back.string + b
      });
    }
  }

  Object.assign(forward, wordState);
  Object.assign(back, wordState);

  for (let y = 0; y < decrement(lineCount); y++) {
    let s = "";
    let is = [];

    for (let x = 0; x <= y; x++) {
      const fi = y - x + x * stride;
      const bi = y - x + reverseIndex(stridei, x) * stride;
      // Top left
      const f = input[fi];
      // Bottom left
      const b = input[bi];
      s += b;
      is.push(bi);

      forward = next({
        ...forward,
        index: increment(forward.index),
        string: forward.string + f
      });

      back = next({
        ...back,
        index: increment(back.index),
        string: back.string + b
      });
    }

    // console.log("s:", s, is);
  }

  Object.assign(forward, wordState);
  Object.assign(back, wordState);

  for (let x = 0; x < stridei; x++) {
    let s = "";
    let is = [];

    for (let y = 0; y <= x; y++) {
      const fi = reverseIndex(stridei, y) - x + x * (stridei + 3);
      const bi = 0;
      // const bi = 0; // I can't for the life for me figure out the index for the bottom left.
      // Top left
      const f = input[fi];
      // Bottom left
      const b = input[bi];
      s += b;
      is.push(bi);

      forward = next({
        ...forward,
        index: increment(forward.index),
        string: forward.string + f
      });

      back = next({
        ...back,
        index: increment(back.index),
        string: back.string + b
      });
    }

    // console.log("s:", s, is);
  }

  console.log(forward.count + back.count);
}
