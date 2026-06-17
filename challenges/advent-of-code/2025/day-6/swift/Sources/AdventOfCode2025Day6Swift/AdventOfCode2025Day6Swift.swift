// The Swift Programming Language
// https://docs.swift.org/swift-book
// 
// Swift Argument Parser
// https://swiftpackageindex.com/apple/swift-argument-parser/documentation

import ArgumentParser
import Foundation

enum Operator {
    static let plusSign = "+"
    static let multiplicationSign = "*"

    case plus, multiplication
}

extension Collection where Element: Collection {
    func transpose() -> [[Element.Element]] {
        guard let first = self.first else {
            return []
        }

        let transposed = first.indices.map { i in
            self.map { $0[i] }
        }

        return transposed
    }
}

@main
struct AdventOfCode2025Day6Swift: ParsableCommand {
    @Argument(transform: URL.init(fileURLWithPath:)) private var inputFile: URL

    func part1(input: String) -> String {
        let symbols = input
            .split(omittingEmptySubsequences: false, whereSeparator: \.isNewline)
            .dropLast()
            .map { $0.split(whereSeparator: \.isWhitespace) }

        let operands = symbols
            .dropLast()
            .map { content in
                content.map { Int($0)! }
            }
        
        let operators = symbols
            .last!
            .map { content in
                let s = content.map { String($0) }.joined()

                switch s {
                    case Operator.plusSign:
                        return Operator.plus
                    case Operator.multiplicationSign:
                        return Operator.multiplication
                    default:
                        fatalError("Unknown operator: \(content)")
                }
            }
        
        let total = zip(operands.transpose(), operators)
            .map { (operands, op) in
                switch op {
                    case .plus:
                        return operands.reduce(0, +)
                    case .multiplication:
                        return operands.reduce(1, *)
                }
            }
            .reduce(0, +)
        
        let result = String(total)

        return result
    }

    func part2(input: String) -> String {
        let symbols = input
            .split(omittingEmptySubsequences: false, whereSeparator: \.isNewline)
            .dropLast()

        let blocks = symbols
            .dropLast()
            .map { String($0) }
            .transpose()
            .split(separator: [" ", " ", " ", " "])
            // .map { column in
                // column
                //     .filter { !$0.isWhitespace }
                //     .map { $0.wholeNumberValue! }
                //     .reduce(0) { $0 * 10 + $1 }
            // }
            // .filter { $0 != 0 }
        
        let operators = symbols
            .last!
            .split(whereSeparator: \.isWhitespace)
            .map { content in
                let s = content.map { String($0) }.joined()

                switch s {
                    case Operator.plusSign:
                        return Operator.plus
                    case Operator.multiplicationSign:
                        return Operator.multiplication
                    default:
                        fatalError("Unknown operator: \(content)")
                }
            }
        
        let total = zip(blocks, operators)
            .map { (block, op) in
                let operands = block
                    .map { operand in
                        operand
                            .filter { !$0.isWhitespace }
                            .map { $0.wholeNumberValue! }
                            .reduce(0) { $0 * 10 + $1 }
                    }
                
                switch op {
                    case .plus:
                        return operands.reduce(0, +)
                    case .multiplication:
                        return operands.reduce(1, *)
                }
            }
            .reduce(0, +)
        
        let result = String(total)

        return result
    }

    mutating func run() throws {
        let data = try Data(contentsOf: inputFile)
        let string = String(decoding: data, as: UTF8.self)
        print("Part 1: \(self.part1(input: string))")
        print("Part 2: \(self.part2(input: string))")
    }
}
