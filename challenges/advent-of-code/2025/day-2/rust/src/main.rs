use std::{ops::RangeInclusive, path::PathBuf};
use clap::Parser;

const PRODUCT_ID_SEPARATOR: &str = ",";
const PRODUCT_ID_RANGE_SEPARATOR: &str = "-";

fn is_odd(n: u32) -> bool {
    n % 2 == 1
}

fn digit_count(n: u64) -> u32 {
    if n == 0 {
        return 1;
    }

    n.ilog10() + 1
}

fn divides(a: u32, b: u32) -> bool {
    b % a == 0
}

fn shift(n: u64, count: u32, width: u32) -> u64 {
    n / 10u64.pow(count) % 10u64.pow(width)
}

trait AllEqual: Iterator {
    fn all_equal(mut self) -> bool where Self: Sized, Self::Item: PartialEq {
        let Some(first) = self.next() else {
            return true;
        };

        self.all(|item| item == first)
    }
}

impl<I: Iterator> AllEqual for I {}

fn parse_product_id_ranges(content: &str) -> impl Iterator<Item = RangeInclusive<u64>> {
    content
        .trim_end()
        .split(PRODUCT_ID_SEPARATOR)
        .map(|range| {
            let (start, end) = range.split_once(PRODUCT_ID_RANGE_SEPARATOR).expect("Invalid product ID range format");
            let range = RangeInclusive::new(
                start.parse().expect("Invalid product ID range start format"),
                end.parse().expect("Invalid product ID range end format"),
            );

            return range;
        })
}

fn day1(content: &str) -> String {
    let product_id_ranges = parse_product_id_ranges(content);
    let invalid_id_sum = product_id_ranges
        .map(|range| range
            .filter_map(|n| {
                let digit_count = digit_count(n);

                if is_odd(digit_count) {
                    return None;
                }

                let modulo = 10u64.pow(digit_count / 2);
                let upper = n / modulo;
                let lower = n % modulo;
                
                if lower != upper {
                    return None;
                }

                Some(n)
            })
            .sum::<u64>())
        .sum::<u64>();

    invalid_id_sum.to_string()
}

fn day2(content: &str) -> String {
    let product_id_ranges = parse_product_id_ranges(content);
    // I'm shocked that this worked on the first try. It's not fast, but it's nicer than performing string processing.
    let invalid_id_sum = product_id_ranges
        .map(|range| {
            range
                .filter_map(|n| {
                    if n < 10 {
                        return None;
                    }

                    // 824824824
                    let digit_count = digit_count(n); // 9
                    let end = digit_count / 2; // 4
                    let satisfies = (1..=end) // 1, 2, 3, 4
                        .filter(|&count| divides(count, digit_count)) // 1, 3
                        .any(|count| {
                            let width = digit_count / count; // 9, 3
                            let result = (0..width) // [0, 1, 2, 3, 4, 5, 6, 7, 8], [0, 1, 2]
                                .map(|i| shift(n, i * count, count)) // [8, 2, 4, 8, 2, 4, 8, 2, 4], [824, 824, 824]
                                .all_equal(); // false, true

                            result
                        }); // true

                    if !satisfies {
                        return None;
                    }

                    Some(n)
                })
                .sum::<u64>()
        })
        .sum::<u64>();

    invalid_id_sum.to_string()
}

#[derive(Parser)]
#[command()]
struct Cli {
    /// Sets the input file to use
    #[arg(short = 'i', long = "input", value_name = "FILE")]
    puzzle_input: PathBuf,
}

fn main() {
    let cli = Cli::parse();
    let content = match std::fs::read_to_string(cli.puzzle_input) {
        Ok(content) => content,
        Err(error) => {
            println!("Error reading file: {}", error);

            return;
        }
    };

    println!("Day 1: {}", day1(&content));
    println!("Day 2: {}", day2(&content));
}
