fun main() {

    fun part1(data: List<Pair<String, Long>>): Long {
        val transformed = data.groupBy({ it.first }, { it.second }).mapValues { it.value.sum() }

        val horizontal = transformed.getOrDefault("forward", 0)
        val depth = transformed.getOrDefault("down", 0) - transformed.getOrDefault("up", 0)
        return horizontal * depth
    }

    fun part2(data: List<Pair<String, Long>>): Long {
        var horizontal: Long = 0
        var depth: Long = 0
        var aim: Long = 0

        data.forEach { (action, value) ->
            when (action) {
                "down" -> {
                    aim += value
                }
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                else -> {
                    aim -= value
                }
            }
        }

        return horizontal * depth
    }

    val input: List<Pair<String, Long>> =
        readInput("Day02").map { it.split(" ") }.map { it[0].trim() to it[1].trim().toLong() }

    println(part1(input))
    println(part2(input))
}