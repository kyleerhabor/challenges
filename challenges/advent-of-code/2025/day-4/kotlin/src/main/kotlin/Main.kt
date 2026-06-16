import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

private const val GRID_POINT_PAPER_ROLL = '@'
// Could probably conjure up a better name.
private const val FORKLIFT_PAPER_ROLL_ACCESS_COUNT = 4

fun countPaperRoll(point: Char?): Int = if (point == GRID_POINT_PAPER_ROLL) {
    1
} else {
    0
}

fun countPaperRoll(point: Boolean?): Int = if (point ?: false) {
    1
} else {
    0
}

fun part1(input: String): String {
    val rows = input
        .trimEnd()
        .lines()

    val count = rows
        .withIndex()
        .sumOf { (i, row) ->
            val above = rows.getOrNull(i - 1)
            val below = rows.getOrNull(i + 1)
            val count = row
                .withIndex()
                .filter { it.value == GRID_POINT_PAPER_ROLL }
                .sumOf { (j, _) ->
                    val before = j - 1
                    val after = j + 1
                    val topLeft = countPaperRoll(above?.getOrNull(before))
                    val topCenter = countPaperRoll(above?.getOrNull(j))
                    val topRight = countPaperRoll(above?.getOrNull(after))
                    val centerLeft = countPaperRoll(row.getOrNull(before))
                    val centerRight = countPaperRoll(row.getOrNull(after))
                    val bottomLeft = countPaperRoll(below?.getOrNull(before))
                    val bottomCenter = countPaperRoll(below?.getOrNull(j))
                    val bottomRight = countPaperRoll(below?.getOrNull(after))
                    val sum = topLeft + topCenter + topRight + centerLeft + centerRight + bottomLeft + bottomCenter + bottomRight
                    val count = if (sum < FORKLIFT_PAPER_ROLL_ACCESS_COUNT) {
                        1
                    } else {
                        0
                    }

                    count
                }

            count
        }
        .toString()

    return count
}

data class Grid(val rows: List<List<Boolean>>, val removalCount: Int)
data class GridRow(val row: List<Boolean>, val removalCount: Int)
data class GridPoint(val point: Boolean, val isRemoved: Boolean)

fun part2(input: String): String {
    var grid = Grid(
        rows = input
            .trimEnd()
            .lines()
            .map { row ->
                row
                    .map { it == GRID_POINT_PAPER_ROLL }
                    .toMutableList()
            },
        removalCount = 0,
    )

    while (true) {
        val rows = grid.rows
            .withIndex()
            .map { (i, row) ->
                val above = grid.rows.getOrNull(i - 1)
                val below = grid.rows.getOrNull(i + 1)
                val points = row
                    .withIndex()
                    .map { (j, point) ->
                        if (!point) {
                            return@map GridPoint(point = point, isRemoved = false)
                        }

                        val before = j - 1
                        val after = j + 1
                        val topLeft = countPaperRoll(above?.getOrNull(before))
                        val topCenter = countPaperRoll(above?.getOrNull(j))
                        val topRight = countPaperRoll(above?.getOrNull(after))
                        val centerLeft = countPaperRoll(row.getOrNull(before))
                        val centerRight = countPaperRoll(row.getOrNull(after))
                        val bottomLeft = countPaperRoll(below?.getOrNull(before))
                        val bottomCenter = countPaperRoll(below?.getOrNull(j))
                        val bottomRight = countPaperRoll(below?.getOrNull(after))
                        val sum = topLeft + topCenter + topRight + centerLeft + centerRight + bottomLeft + bottomCenter + bottomRight
                        val isRemoved = sum < FORKLIFT_PAPER_ROLL_ACCESS_COUNT
                        val point = GridPoint(
                            point = if (isRemoved) {
                                false
                            } else {
                                point
                            },
                            isRemoved = isRemoved
                        )

                        point
                    }

                val row = GridRow(
                    row = points.map { it.point },
                    removalCount = points.count { it.isRemoved },
                )

                row
            }

        val grid2 = Grid(
            rows = rows.map { it.row },
            removalCount = grid.removalCount + rows.sumOf { it.removalCount },
        )

        if (grid2.removalCount == grid.removalCount) {
            break
        }

        grid = grid2
    }

    val count = grid.removalCount.toString()

    return count
}

class Cli : CliktCommand() {
    val input: File by option().file().required()

    override fun run() {
        val input = this.input.readText()
        println("Part 1: ${part1(input = input)}")
        println("Part 2: ${part2(input = input)}")
    }
}

fun main(args: Array<String>) = Cli().main(args)