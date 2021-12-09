fun main() {

    fun isLowPoint(rowIndex: Int, colIndex: Int, matrix: Array<IntArray>): Boolean {
        val digit = matrix[rowIndex][colIndex]
        val upper = matrix.getOrNull(rowIndex - 1)?.getOrNull(colIndex)?.toString()?.toInt() ?: Int.MAX_VALUE
        val down = matrix.getOrNull(rowIndex + 1)?.getOrNull(colIndex)?.toString()?.toInt() ?: Int.MAX_VALUE
        val left = matrix.getOrNull(rowIndex)?.getOrNull(colIndex - 1)?.toString()?.toInt() ?: Int.MAX_VALUE
        val right = matrix.getOrNull(rowIndex)?.getOrNull(colIndex + 1)?.toString()?.toInt() ?: Int.MAX_VALUE

        return digit < upper && digit < down && digit < left && digit < right
    }

    fun part1(matrix: Array<IntArray>): Int {

        val lowPoints = mutableListOf<Int>()
        matrix.forEachIndexed { rowIndex, digits ->
            digits.forEachIndexed { colIndex, digit ->
                if (isLowPoint(rowIndex, colIndex, matrix)) {
                    lowPoints.add(digit)
                }
            }
        }

        return lowPoints.sumOf { it + 1 }
    }


    fun part2(matrix: Array<IntArray>): Int {

        fun borders(x: Int, y: Int): Int = if (x < 0 || x >= matrix[0].size ||
            y < 0 || y >= matrix.size
        ) 9 else matrix[y][x]

        fun size(x: Int, y: Int): Int {
            if (borders(x, y) == 9) return 0
            matrix[y][x] = 9
            return 1 + size(x - 1, y) +
                    size(x + 1, y) +
                    size(x, y - 1) +
                    size(x, y + 1)
        }

        val basins = mutableListOf<Int>()
        matrix.forEachIndexed { rowIndex, digits ->
            digits.forEachIndexed { colIndex, _ ->
                if (isLowPoint(rowIndex, colIndex, matrix)) {
                    basins.add(size(colIndex, rowIndex))
                }
            }
        }

        return basins.sortedDescending().take(3).reduce { a, b -> a * b }
    }

    val input = readInput("Day09")
        .map { line -> line.toCharArray().map { it.toString().toInt() }.toIntArray() }
        .toTypedArray()

    println(part1(input))
    println(part2(input))
}
