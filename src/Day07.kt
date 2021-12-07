import kotlin.math.abs

fun main() {

    fun calculate(crabs: List<Int>, cost: (Int, Int) -> List<Int>): Int {
        val max = crabs.maxOf { it }
        val total = IntArray(max + 1)
        crabs.forEach { crab ->
            cost(crab, max).forEachIndexed { index, value ->
                total[index] += value
            }
        }

        return total.minOf { it }
    }

    fun part1(crabs: List<Int>): Int = calculate(crabs) { crab, max ->
        (0..max).map { abs(crab - it) }
    }

    fun part2(crabs: List<Int>): Int = calculate(crabs) { crab, max ->
        (0..max).map { index ->
            abs(crab - index).let { it * (it + 1) / 2 }
        }
    }

    val input = readInput("Day07").first().split(",").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
