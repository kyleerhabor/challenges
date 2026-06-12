import { decrement, increment, readInput } from "../core.js";

const OBSTACLE = "#";
const GUARD = "^";
const UP = 0;
const RIGHT = 1;
const DOWN = 2;
const LEFT = 3;
const DIRECTION_COUNT = 4;

export async function part1() {
  const input = await readInput("inputs/day6.txt");
  const stridei = input.indexOf("\n");
  let state = {
    index: input.indexOf(GUARD),
    direction: UP
  };

  let used = new Set();

  loop:
  while (true) {
    // let x = Math.floor(state.index / (stridei + 1));
    // let y = state.index % (stridei + 1);

    // console.log(`At (${x}, ${y})`);
    used.add(state.index);

    let i;

    switch (state.direction) {
      case LEFT:
        if (state.index % (stridei + 1) === 0) {
          break loop;
        }

        i = decrement(state.index);

        break;
      case RIGHT:
        if (state.index % (stridei + 1) === stridei) {
          break loop;
        }

        i = increment(state.index);

        break;
      case UP:
        if (state.index < stridei + 1) {
          break loop;
        }

        i = state.index - stridei - 1;

        break;
      case DOWN:
        if (input.length - stridei + 1 < state.index) {
          break loop;
        }

        i = state.index + stridei + 1;

        break;
    }

    if (input[i] !== OBSTACLE) {
      state.index = i;

      continue;
    }

    const next = increment(state.direction) % DIRECTION_COUNT;
    state.direction = next
  }

  console.log(used.size);
}
