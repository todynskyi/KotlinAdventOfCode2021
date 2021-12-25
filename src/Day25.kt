fun main() {

    fun part1(grid: Map<Pair<Int, Int>, Char>, max: Pair<Int, Int>): Int = generateSequence(grid) { next ->
        val nextMap = next.toMutableMap()
        next.filterValues { it == '>' }
            .keys
            .map { it to (if (it.first == max.first) 0 to it.second else it.first + 1 to it.second) }
            .filter { it.second !in next }
            .forEach { (prev, east) ->
                nextMap.remove(prev)
                nextMap[east] = '>'
            }
        nextMap.filterValues { it == 'v' }
            .keys
            .map { it to if (it.second == max.second) it.first to 0 else it.first to it.second + 1 }
            .filter { it.second !in nextMap }
            .forEach { (prev, south) ->
                nextMap.remove(prev)
                nextMap[south] = 'v'
            }
        nextMap
    }
        .zipWithNext()
        .indexOfFirst { it.first == it.second } + 1

    fun part2(): String = "Marry Christmas!"

    val input = readInput("Day25")

    val grid = mutableMapOf<Pair<Int, Int>, Char>()
    input.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (cell != '.') {
                grid[x to y] = cell
            }
        }
    }
    val max = input.first().lastIndex to input.lastIndex

    println(part1(grid, max))
    println(part2())
}
