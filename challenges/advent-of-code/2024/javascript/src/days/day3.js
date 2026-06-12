import { add, readInput } from "../core.js";

const INSTRUCTION_DATA_ARGUMENT_SEPARATOR = ",";
const MULTIPLY_INSTRUCTION = "mul";
const MULTIPLY_ENABLE_INSTRUCTION = "do";
const MULTIPLY_DISABLE_INSTRUCTION = "don't";

export async function part1() {
  const input = await readInput("inputs/day3.txt");
  const matches = input.matchAll(new RegExp(`${MULTIPLY_INSTRUCTION}\\((\\d{1,3})${INSTRUCTION_DATA_ARGUMENT_SEPARATOR}(\\d{1,3})\\)`, "g"))
  const result = matches
    .map((match) => parseInt(match[1], 10) * parseInt(match[2], 10))
    .reduce(add, 0);
  
  console.log(result);
}

export async function part2() {
  const input = await readInput("inputs/day3.txt");
  const matches = input.matchAll(new RegExp(`(${MULTIPLY_INSTRUCTION}|${MULTIPLY_ENABLE_INSTRUCTION}|${MULTIPLY_DISABLE_INSTRUCTION})\\((\\d{1,3}${INSTRUCTION_DATA_ARGUMENT_SEPARATOR}\\d{1,3})?\\)`, "g"))

  let isMulEnabled = true;
  let value = 0;

  matches.forEach(match => {
    const instruction = match[1];

    switch (instruction) {
      case MULTIPLY_INSTRUCTION:
        if (!isMulEnabled) {
          return;
        }

        const data = match[2];
        const [lhs, rhs] = data.split(INSTRUCTION_DATA_ARGUMENT_SEPARATOR);
        const expr = parseInt(lhs) * parseInt(rhs);
        value += expr

        break;
      case MULTIPLY_ENABLE_INSTRUCTION:
        isMulEnabled = true;

        break;
      case MULTIPLY_DISABLE_INSTRUCTION:
        isMulEnabled = false;

        break;
    }
  });

  console.log(value);
}