use std::path::PathBuf;
use clap::Parser;

enum RotationDirection {
    Left,
    Right,
}

struct Rotation {
    direction: RotationDirection,
    distance: i16,
}

const DIAL_POINT_MIN: i16 = 0;
const DIAL_POINT_SIZE: i16 = 100;
const DIAL_POINT_START: i16 = 50;

struct Dial {
    point: i16,
    zero_count: i16,
}

impl Dial {
    fn update(&self, total: i16, wrap_count: i16) -> Self {
        let point = total.rem_euclid(DIAL_POINT_SIZE);
        let dial = Dial {
            point,
            zero_count: self.zero_count + wrap_count,
        };

        dial

    }
}

fn parse_rotations(content: &str) -> impl Iterator<Item = Rotation> {
    content
        .lines()
        .map(|content| {
            let (direction, distance) = content.split_at(1);
            let direction = match direction {
                "L" => RotationDirection::Left,
                "R" => RotationDirection::Right,
                _ => panic!("Invalid direction: {}", direction),
            };

            let distance = distance.parse::<i16>().expect("Invalid distance");
            let rotation = Rotation {
                direction,
                distance,
            };

            rotation
        })
}

fn day1(content: &str) -> String {
    let rotations = parse_rotations(&content);
    let dial = rotations.fold(Dial { point: DIAL_POINT_START, zero_count: 0 }, |dial, rotation| {
        let point = match rotation.direction {
            RotationDirection::Left => (dial.point - rotation.distance).rem_euclid(DIAL_POINT_SIZE),
            RotationDirection::Right => (dial.point + rotation.distance).rem_euclid(DIAL_POINT_SIZE),
        };

        let dial = Dial {
            point,
            zero_count: if point == DIAL_POINT_MIN {
                dial.zero_count + 1
            } else {
                dial.zero_count
            }
        };

        dial
    });

    let password = dial.zero_count.to_string();

    password
}

fn day2(content: &str) -> String {
    let rotations = parse_rotations(&content);
    let dial = rotations.fold(Dial { point: DIAL_POINT_START, zero_count: 0 }, |dial, rotation| {
        match rotation.direction {
            RotationDirection::Left => {
                let total = dial.point - rotation.distance;
                let wrap_count = if dial.point == DIAL_POINT_MIN {
                    (-total).div_euclid(DIAL_POINT_SIZE)
                } else {
                    1 + (-total).div_euclid(DIAL_POINT_SIZE)
                };

                let dial = dial.update(total, wrap_count);

                dial
            },
            RotationDirection::Right => {
                let total = dial.point + rotation.distance;
                let wrap_count = total.div_euclid(DIAL_POINT_SIZE);
                let dial = dial.update(total, wrap_count);

                dial
            }
        }
    });

    let password = dial.zero_count.to_string();

    password
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
