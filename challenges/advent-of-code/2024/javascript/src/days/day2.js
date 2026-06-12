import { readInput } from "../core.js";

function isSafeDistance(diff) {
  return diff > 0 && diff < 4;
}

export async function part1() {
  const input = await readInput("inputs/day2.txt");
  const reports = input
    .split("\n")
    .reduce((count, report) => {
      const levels = report
        .split(" ")
        .map((level) => parseInt(level, 10));
      
      const windows = levels
        .slice(1)
        .map((level, i) => [levels[i], level]);
      
      const iterator = windows[Symbol.iterator]();
      const [prior, level] = iterator.next().value;
      const difference = level - prior;

      if (!isSafeDistance(Math.abs(difference))) {
        return count;
      }

      const sign = Math.sign(difference);

      for (const [prior, level] of iterator) {
        const difference = level - prior;

        if (Math.sign(difference) !== sign || !isSafeDistance(Math.abs(difference))) {
          return count
        }
      }

      return count + 1;
    }, 0)
  
  console.log(reports);
}

// export async function part2() {
//   const input = await readInput("inputs/day2.txt");
//   const reports = input
//     .split("\n")
//     .reduce((count, report) => {
//       const levels = report
//         .split(" ")
//         .map((level) => parseInt(level, 10));
      
//       const windows = levels
//         .slice(1)
//         .map((level, i) => {
//           const prior = levels[i];
//           const difference = level - prior;

//           return {
//             priorLevel: prior,
//             level: level,
//             sign: Math.sign(difference),
//             diff: Math.abs(difference)
//           };
//         });
      
//       for (let i = 0; i < windows.length; i++) {
//         const element = windows[i];

//         if (element.sign !== prior.sign || !isSafeDistance()) {
          
//         }
//       }
      
//       for (let i = 1; i < levels.length; i++) {
//         const prior = levels[i - 1];
//         const level = levels[i];
//         const difference = level - prior;

//       }
      
//       const iterator = windows[Symbol.iterator]();
//       const [prior, level] = iterator.next().value;
//       const difference = level - prior;

//       if (!isSafeDistance(Math.abs(difference))) {
//         return count;
//       }

//       const sign = Math.sign(difference);

//       for (const [prior, level] of iterator) {
//         const difference = level - prior;

//         if (Math.sign(difference) !== sign || !isSafeDistance(Math.abs(difference))) {
//           return count;
//         }
//       }

//       return count + 1;
//     }, { count: 0, elided: false });
  
//   console.log(reports);
// }