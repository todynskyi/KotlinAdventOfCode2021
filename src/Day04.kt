fun main() {

    fun playGame(value: Int, score: List<List<Pair<Int, Int>>>): List<List<Pair<Int, Int>>> {
        return score.map { row ->
            row.map { cell ->
                if (cell.first == value) cell.copy(second = 1) else cell
            }
        }
    }

    fun check(score: List<List<Pair<Int, Int>>>): List<Int> {
        (0..4).forEach { index ->
            val row: List<Pair<Int, Int>> = score[index]
            if (row.sumOf { it.second } == 5) {
                return row.map { it.first }
            }

            val col: List<Pair<Int, Int>> = score.map { it[index] }
            if (col.sumOf { it.second } == 5) {
                return col.map { it.first }
            }
        }
        return emptyList()
    }

    fun sum(score: List<List<Pair<Int, Int>>>): Int = (0..4).sumOf { index ->
        score[index].filter { it.second == 0 }
            .sumOf { it.first }
    }

    fun part1(scores: List<Int>, players: List<List<List<Pair<Int, Int>>>>): Int {
        var currentRoundPlayers = players
        scores.forEach { score ->
            currentRoundPlayers = currentRoundPlayers.map { player ->
                playGame(score, player)
            }
            currentRoundPlayers.forEach { player ->
                val result = check(player)
                if (result.isNotEmpty()) {
                    return score * sum(player)
                }
            }
        }

        throw IllegalArgumentException("There is no winner!")
    }

    fun part2(scores: List<Int>, players: List<List<List<Pair<Int, Int>>>>): Int {
        var currentRoundPlayers = players
        scores.forEach { score ->
            val nextRoundPlayers = mutableListOf<List<List<Pair<Int, Int>>>>()

            currentRoundPlayers.forEach { player ->
                val modified = playGame(score, player)
                val result = check(modified)
                if (result.isNotEmpty() && currentRoundPlayers.size == 1) {
                    return score * sum(modified)
                }
                if (result.isEmpty()) {
                    nextRoundPlayers.add(modified)
                }
            }

            currentRoundPlayers = nextRoundPlayers
        }
        throw IllegalArgumentException("There is no winner!")
    }

    val toMatrix: (List<String>) -> List<List<Pair<Int, Int>>> = { data ->
        data.map { it.split(" ") }
            .map { row ->
                row.filter { it.isNotBlank() }
                    .map { it.trim().toInt() to 0 }
            }
    }

    val input = readInput("Day04")
    val scores = input[0].split(",").map { it.toInt() }
    val players: List<List<List<Pair<Int, Int>>>> = input.subList(1, input.size)
        .chunked(6)
        .map { it.subList(1, it.size) }
        .map { toMatrix(it) }

    println(part1(scores, players))
    println(part2(scores, players))
}
