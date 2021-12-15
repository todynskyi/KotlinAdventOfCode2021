fun main() {

    fun neighbors(row: Int, cell: Int): List<Pair<Int, Int>> = listOf(
        row + 1 to cell,
        row - 1 to cell,
        row to cell - 1,
        row to cell + 1
    )

    fun calculate(row: Int, cell: Int, risks: Array<IntArray>): Int {
        if (row < risks.size && cell < risks[row].size) return risks[row][cell]
        var risk = 0
        if (cell >= risks[0].size) {
            risk = calculate(row, cell - risks[0].size, risks) + 1
        }
        if (row >= risks.size) {
            risk = calculate(row - risks.size, cell, risks) + 1
        }
        return ((risk - 1) % 9) + 1
    }

    fun lowestTotalRisk(dimensions: Int, grid: Array<IntArray>): Int {
        val minRisks =
            Array(grid.size * dimensions) { IntArray(grid[0].size * dimensions) { Int.MAX_VALUE } }
                .apply { get(0)[0] = 0 }

        var completed = false

        while (!completed) {
            completed = true
            for (row in minRisks.indices) {
                for (cell in minRisks[row].indices) {
                    val currentRisk: Int = minRisks[row][cell]
                    neighbors(row, cell).forEach { (r, c) ->
                        if (r >= 0 && r < minRisks.size && c >= 0 && c < minRisks[row].size) {
                            val risk = currentRisk + calculate(r, c, grid)
                            if (minRisks[r][c] > risk) {
                                minRisks[r][c] = risk
                                completed = false
                            }
                        }
                    }
                }
            }
        }

        return minRisks[minRisks.size - 1][minRisks[0].size - 1];
    }

    fun part1(grid: Array<IntArray>): Int = lowestTotalRisk(1, grid)

    fun part2(grid: Array<IntArray>): Int = lowestTotalRisk(5, grid)

    val input =
        readInput("Day15").map { line -> line.toCharArray().map { it.toString().toInt() }.toIntArray() }.toTypedArray()

    println(part1(input))
    println(part2(input))
}
