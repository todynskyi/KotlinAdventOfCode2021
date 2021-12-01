fun main() {

    fun part1(data: List<Long>): Int =
        List(data.size) { index -> if (index == 0 || data[index - 1] >= data[index]) 0 else 1 }.sum()

    fun part2(data: List<Long>): Int {
        val sumDepths = data.mapIndexed { index, value ->
            value + data.getOrElse(index + 1) { 0 } + data.getOrElse(index + 2) { 0 }
        }
        return part1(sumDepths)
    }

    val input = readInput("Day01").map { it.toLong() }
    println(part1(input))
    println(part2(input))
}
