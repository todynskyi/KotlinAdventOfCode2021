import kotlin.math.abs

fun main() {

    fun fillHorizontal(matrix: Array<IntArray>, y1: Int, x1: Int, x2: Int): Int {
        var counter = 0
        (Math.min(x1, x2)..Math.max(x1, x2)).forEach { i ->
            matrix[y1][i] += 1
            if (matrix[y1][i] == 2) {
                counter++;
            }
        }
        return counter
    }

    fun fillVertical(matrix: Array<IntArray>, x1: Int, y1: Int, y2: Int): Int {
        var counter = 0
        (Math.min(y1, y2)..Math.max(y1, y2)).forEach { i ->
            matrix[i][x1] += 1
            if (matrix[i][x1] == 2) {
                counter++;
            }
        }
        return counter
    }

    fun part1(data: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val matrix: Array<IntArray> = Array(999) { IntArray(999) }

        var counter = 0
        data.forEach { (point1, point2) ->
            if (point1.first == point2.first) {
                counter += fillVertical(matrix, point1.first, point1.second, point2.second)
            }
            if (point1.second == point2.second) {
                counter += fillHorizontal(matrix, point1.second, point1.first, point2.first)
            }
        }
        return counter
    }

    fun part2(data: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val matrix: Array<IntArray> = Array(999) { IntArray(999) }

        fun fillDiagonal(matrix: Array<IntArray>, x1: Int, y1: Int, x2: Int, y2: Int): Int {
            var counter = 0

            var x = x1
            var y = y1

            repeat(abs(x2 - x1) + 1) {
                matrix[y][x] += 1
                if (matrix[y][x] == 2) {
                    counter++
                }
                x += (if (x2 > x) 1 else -1)
                y += (if (y2 > y) 1 else -1)
            }
            return counter
        }

        var counter = 0
        data.forEach { (point1, point2) ->
            counter += if (point1.first == point2.first) {
                fillVertical(matrix, point1.first, point1.second, point2.second)
            } else if (point1.second == point2.second) {
                fillHorizontal(matrix, point1.second, point1.first, point2.first)
            } else {
                fillDiagonal(matrix, point1.first, point1.second, point2.first, point2.second)
            }
        }
        return counter
    }

    val input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = readInput("Day05").map {
        val points = it.split(" -> ")
        val coordinates: List<String> = points[0].split(",")
        val point1 = Pair(coordinates[0].toInt(), coordinates[1].toInt())

        val coordinates2: List<String> = points[1].split(",")
        val point2 = Pair(coordinates2[0].toInt(), coordinates2[1].toInt())
        point1 to point2
    }

    println(part1(input))
    println(part2(input))
}
