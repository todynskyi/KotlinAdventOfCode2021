fun main() {

    fun getAdjacentOctopuses(row: Int, cell: Int): List<Pair<Int, Int>> = listOf(
        row - 1 to cell - 1,
        row - 1 to cell,
        row - 1 to cell + 1,
        row to cell - 1,
        row to cell + 1,
        row + 1 to cell - 1,
        row + 1 to cell,
        row + 1 to cell + 1
    )

    fun increment(octopuses: Array<IntArray>, row: Int, cell: Int): Int {
        var flashes = 0
        if (octopuses[row][cell] == -1) {
            return 0
        } else if (octopuses[row][cell] == 9) {
            flashes++
            octopuses[row][cell] = -1
            getAdjacentOctopuses(row, cell).forEach { (aRow, aCell) ->
                octopuses.getOrNull(aRow)?.getOrNull(aCell)?.apply {
                    flashes += increment(octopuses, aRow, aCell)
                }
            }
        } else {
            octopuses[row][cell]++
        }
        return flashes
    }

    fun step(octopuses: Array<IntArray>): Int {
        var flashes = 0
        octopuses.forEachIndexed { row, line ->
            line.forEachIndexed { cell, _ ->
                flashes += increment(octopuses, row, cell)
            }
        }
        octopuses.forEachIndexed { row, line ->
            line.forEachIndexed { cell, _ ->
                if (octopuses[row][cell] == -1) {
                    octopuses[row][cell]++
                }
            }
        }
        return flashes
    }

    fun part1(octopuses: Array<IntArray>): Int = (1..100).sumOf { step(octopuses) }


    fun part2(octopuses: Array<IntArray>): Int {
        var steps = 0
        while (true) {
            steps++
            step(octopuses)

            if (octopuses.all { line -> line.all { it == 0 } }) {
                return steps
            }
        }
    }

    val input =
        {
            readInput("Day11")
                .map { line -> line.toCharArray().map { it.toString().toInt() }.toIntArray() }
                .toTypedArray()
        }

    println(part1(input()))
    println(part2(input()))
}
