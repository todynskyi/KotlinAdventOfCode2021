fun main() {


    fun part1(data: List<String>): Int {
        val n = listOf(2, 4, 3, 7)
        return data.flatMap {
            it.split(" | ")
                .last()
                .split(" ")
        }
            .count { it.length in n }
    }

    fun part2(data: List<String>): Int {
        val n = listOf(42, 17, 34, 39, 30, 37, 41, 25, 49, 45)
        var total = 0
        data.forEach { line ->
            val parts = line.split(" | ")
            var output = parts[1]

            for (c in "abcdefg".toCharArray()) {
                output = output.replace(
                    c.toString() + "", parts[0].chars()
                        .filter { cc: Int -> cc == c.code }.count().toString()
                )
            }

            total += output.split(" ")
                .map { d ->
                    d.chars().map(Character::getNumericValue)
                        .reduce(0, Integer::sum)
                }
                .map { n.indexOf(it) }
                .joinToString("") { it.toString() }
                .toInt()

        }
        return total
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
