import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt

fun main() {

    fun part1(yMin: Int): Int = (abs(yMin) - 1) * (abs(yMin)) / 2

    fun height(x: Int, y: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
        var xV = x
        var yV = y
        var xP = 0
        var yP = 0
        while (xP <= xMax && yP >= yMin) {
            xP += xV
            yP += yV
            if (xV > 0) xV--
            yV--
            if (xP in xMin..xMax && yP >= yMin && yP <= yMax) {
                return 1
            }
        }
        return 0
    }

    fun part2(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {

        val minXVelocity = ceil((-1 + sqrt((1 + 8 * xMin).toDouble())) / 2).toInt()
        val maxYVelocity = abs(yMin) - 1

        return (minXVelocity..xMax).flatMap { x ->
            (yMin..maxYVelocity).map { y ->
                height(x, y, xMin, xMax, yMin, yMax)
            }
        }.count { it == 1 }
    }

    val (x, y) = readInput("Day17").first()
        .split(", ")
        .let { points ->
            val toPoint: (String) -> Pair<Int, Int> = { data ->
                data.substringAfter("=").split("..").let { it[0].toInt() to it[1].toInt() }
            }
            toPoint(points[0]) to toPoint(points[1])
        }

    println(part1(y.first))
    println(part2(x.first, x.second, y.first, y.second))

}


