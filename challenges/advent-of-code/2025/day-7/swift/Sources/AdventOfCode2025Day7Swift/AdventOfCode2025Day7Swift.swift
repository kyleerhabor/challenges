// The Swift Programming Language
// https://docs.swift.org/swift-book
// 
// Swift Argument Parser
// https://swiftpackageindex.com/apple/swift-argument-parser/documentation

import ArgumentParser
import Foundation

@main
struct AdventOfCode2025Day7Swift: ParsableCommand {
    @Argument(transform: URL.init(fileURLWithPath:)) private var inputFile: URL

    func part1(input: String) -> String {
        fatalError("Not implemented")
    }

    func part2(input: String) -> String {
        fatalError("Not implemented")
    }

    mutating func run() throws {
        let data = try Data(contentsOf: inputFile)
        let string = String(decoding: data, as: UTF8.self)
        print("Part 1: \(self.part1(input: string))")
        print("Part 2: \(self.part2(input: string))")
    }
}
