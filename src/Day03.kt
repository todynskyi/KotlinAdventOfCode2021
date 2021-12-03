fun main() {

    fun String.toDecimal() = Integer.parseInt(this, 2)

    fun part1(data: List<String>, size: Int): Int {
        fun calculate(predicate: (List<Char>) -> Boolean) = (0 until size).joinToString("") { index ->
            val digits: List<Char> = data.map {
                it[index]
            }
            if (predicate(digits)) "1" else "0"
        }.toDecimal()

        val gammaRate = calculate { digits ->
            digits.count { it == '1' } > digits.count { it == '0' }
        }

        val epsilonRate = calculate { digits ->
            digits.count { it == '1' } < digits.count { it == '0' }
        }

        return gammaRate * epsilonRate
    }

    fun part2(data: List<String>, size: Int): Int {

        fun calculate(one: Boolean, predicate: (Int, Int) -> Boolean): Int {
            var input = data

            (0 until size).forEach { index ->
                if (input.size == 1) {
                    return@forEach
                }
                val ones: List<String> = input.filter { it[index] == '1' }
                val zeros: List<String> = input.filter { it[index] == '0' }

                input = if (ones.size == zeros.size) {
                    if (one) ones else zeros
                } else if (predicate(ones.size, zeros.size)) ones else zeros
            }
            return input.first().toDecimal();
        }

        val oxygenGeneratorRating = calculate(true) { a, b -> a > b }
        val co2ScrubberRating = calculate(false) { a, b -> a < b }

        return oxygenGeneratorRating * co2ScrubberRating
    }

    val input: List<String> = readInput("Day03").map { it.trim() }
    val size = input.first().length

    println(part1(input, size))
    println(part2(input, size))
}