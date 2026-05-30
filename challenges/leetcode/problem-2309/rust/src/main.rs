struct Solution;

impl Solution {
    pub fn greatest_letter(s: String) -> String {
        const LOWERCASE_MIN: char = 'a';
        const LOWERCASE_MAX: char = 'z';
        const UPPERCASE_MIN: char = 'A';
        const UPPERCASE_MAX: char = 'Z';
        let mut greatest = None::<char>;

        for (i, letter) in s.char_indices() {
            match letter {
                LOWERCASE_MIN ..= LOWERCASE_MAX => {
                    let upper = letter.to_ascii_uppercase();

                    if let Some(greatest) = greatest && greatest >= upper {
                        continue;
                    }

                    if let Some(_) = s[..i].find(upper) {
                        greatest = Some(upper);
                    }
                },
                UPPERCASE_MIN ..= UPPERCASE_MAX => {
                    if let Some(greatest) = greatest && greatest >= letter {
                        continue;
                    }

                    let lower = letter.to_ascii_lowercase();

                    if let Some(_) = s[..i].find(lower) {
                        greatest = Some(letter);
                    }
                },
                _ => unreachable!(),
            }
        }

        return greatest.map_or("".to_string(), |x| x.to_string());
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        assert_eq!(Solution::greatest_letter("lEeTcOdE".to_string()), "E");
        assert_eq!(Solution::greatest_letter("arRAzFif".to_string()), "R");
        assert_eq!(Solution::greatest_letter("AbCdEfGhIjK".to_string()), "");
    }
}

fn main() {
    println!("Hello, world!");
}
