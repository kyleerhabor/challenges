use std::{cmp::Ordering, collections::HashMap};

struct Creator {
    creator: String,
    ids: Vec<String>,
    views: Vec<i32>,
}

impl Creator {
    fn view_count(&self) -> i64 {
        // Given:
        //   n == creators.length == ids.length == views.length
        //   1 <= n <= 10^5
        //   0 <= views[i] <= 10^5
        //
        // 2^31 - 1 < 10^5 * 10^5 = 10^10 < 2^63 - 1
        self.views.iter().map(|&x| i64::from(x)).sum()
    }

    fn most_viewed_video(&self) -> String {
        struct Video {
            id: String,
            views: i32,
        }

        return self.ids.iter().zip(self.views.iter())
            .map(|(id, &views)| Video { id: id.clone(), views: views })
            .reduce(|a, b| match a.views.cmp(&b.views) {
                Ordering::Less => b,
                Ordering::Equal if a.id <= b.id => a,
                Ordering::Equal => b,
                Ordering::Greater => a,
            })
            .unwrap()
            .id;
    }
}

struct Solution;

impl Solution {
    pub fn most_popular_creator(creators: Vec<String>, ids: Vec<String>, views: Vec<i32>) -> Vec<Vec<String>> {
        let mut firstpass = HashMap::<String, Creator>::with_capacity(creators.len());

        for (creator, (id, views)) in creators.iter().zip(ids.iter().zip(views.iter())) {
            firstpass
                .entry(creator.clone())
                .and_modify(|creator| {
                    creator.ids.push(id.clone());
                    creator.views.push(*views);
                })
                .or_insert_with(|| Creator { creator: creator.clone(), ids: vec![id.clone()], views: vec![*views] });
        }

        let max_views = firstpass.iter()
            .map(|(_, creator)| creator.view_count())
            .max()
            .unwrap();
        
        let most_popular_creators = firstpass.iter()
            .filter(|(_, creator)| creator.view_count() == max_views)
            .map(|(_, creator)| vec![creator.creator.clone(), creator.most_viewed_video()])
            .collect::<Vec<_>>();

        return most_popular_creators;
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        assert_eq!(
            Solution::most_popular_creator(
                vec!["alice".to_owned(), "bob".to_owned(), "alice".to_owned(), "chris".to_owned()],
                vec!["one".to_owned(), "two".to_owned(), "three".to_owned(), "four".to_owned()],
                vec![5, 10, 5, 4],
            ),
            vec![
                vec!["alice".to_owned(), "one".to_owned()],
                vec!["bob".to_owned(), "two".to_owned()],
            ],
        );

        assert_eq!(
            Solution::most_popular_creator(
                vec!["alice".to_owned(), "alice".to_owned(), "alice".to_owned()],
                vec!["a".to_owned(), "b".to_owned(), "c".to_owned()],
                vec![1, 2, 2],
            ),
            vec![vec!["alice".to_owned(), "b".to_owned()]],
        );

        assert_eq!(
            Solution::most_popular_creator(
                vec!["a".to_owned(), "a".to_owned()],
                vec!["aa".to_owned(), "a".to_owned()],
                vec![5, 5],
            ),
            vec![vec!["a".to_owned(), "a".to_owned()]],
        );
    }
}

fn main() {
    println!("Hello, world!");
}
