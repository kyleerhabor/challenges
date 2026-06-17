// The Swift Programming Language
// https://docs.swift.org/swift-book
// 
// Swift Argument Parser
// https://swiftpackageindex.com/apple/swift-argument-parser/documentation

import ArgumentParser
import Foundation

struct Database {
    let ranges: [ClosedRange<Int>]
    let ids: [Int]
}

@main
struct swift: ParsableCommand {
    @Argument(transform: URL.init(fileURLWithPath:)) private var inputFile: URL

    func parse(input: String) -> Database {
        let database = input
            .split(omittingEmptySubsequences: false, whereSeparator: \.isNewline)
            .dropLast()
            .split(separator: "")

        let ranges = database[0]
            .map { range in
                let split = range.split(separator: "-")
                let lower = Int(split[0])!
                let upper = Int(split[1])!
                let range = lower...upper

                return range
            }

        let ids = database[1].map { Int($0)! }
        let result = Database(ranges: ranges, ids: ids)

        return result
    }

    func part1(input: String) -> String {
        let database = self.parse(input: input)
        let count = database.ids.count { id in
            database.ranges.contains { range in
                range.contains(id)
            }
        }

        let result = String(count)

        return result
    }

    func part2(input: String) -> String {
        let database = self.parse(input: input)
        let ranges = RangeSet(database.ranges.map { Range($0) })
        let count = String(ranges.ranges.map(\.count).reduce(0, +))

        return count
    }

    mutating func run() throws {
        let data = try Data(contentsOf: inputFile)
        let string = String(decoding: data, as: UTF8.self)
        print("Part 1: \(self.part1(input: string))")
        print("Part 2: \(self.part2(input: string))")
    }
}
