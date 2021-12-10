import java.util.*

fun main() {

    val open: (Char) -> Char = {
        when (it) {
            ')' -> {
                '('
            }
            '}' -> {
                '{'
            }
            ']' -> {
                '['
            }
            else -> {
                '<'
            }
        }
    }

    fun part1(data: List<String>): Int {
        fun getCorruptedCharacter(line: CharArray): Int {

            val cost: (Char) -> Int = {
                when (it) {
                    ')' -> {
                        3
                    }
                    '}' -> {
                        1197
                    }
                    ']' -> {
                        57
                    }
                    else -> {
                        25137
                    }
                }
            }

            val characters = Stack<Char>()
            line.forEach { character ->
                if (character == '[' || character == '(' || character == '{' || character == '<') {
                    characters.push(character)
                } else {
                    val expected = characters.pop()
                    if (!expected.equals(open(character))) {
                        return cost(character)
                    }
                }
            }
            return 0
        }

        return data.sumOf { getCorruptedCharacter(it.toCharArray()) }
    }

    fun part2(data: List<String>): Long {
        fun getCorruptedCharacters(line: CharArray): Long {

            val close: (Char) -> Char = {
                when (it) {
                    '(' -> {
                        ')'
                    }
                    '{' -> {
                        '}'
                    }
                    '[' -> {
                        ']'
                    }
                    else -> {
                        '>'
                    }
                }
            }

            val cost: (Char) -> Int = {
                when (it) {
                    ')' -> {
                        1
                    }
                    '}' -> {
                        3
                    }
                    ']' -> {
                        2
                    }
                    else -> {
                        4
                    }
                }
            }

            val characters = Stack<Char>()
            line.forEach { character ->
                if (character == '[' || character == '(' || character == '{' || character == '<') {
                    characters.push(character)
                } else {
                    val expected = characters.pop()
                    if (!expected.equals(open(character))) {
                        return 0
                    }
                }
            }

            if (characters.isNotEmpty()) {
                var total: Long = 0
                while (characters.size > 0) {
                    total = 5 * total + cost(close(characters.pop()))
                }
                return total
            }
            return 0
        }

        val scores = data.map { getCorruptedCharacters(it.toCharArray()) }.filter { it > 0 }.sorted()

        return scores[scores.size / 2]
    }

    val input = readInput("Day10")

    println(part1(input))
    println(part2(input))
}
