import kotlin.math.max

fun main() {

    fun Pair<Int, Int>.move(dice: Int): Pair<Int, Int> {
        val position = (this.second + dice - 1) % 10 + 1
        val points = this.first + position
        return points to position
    }

    fun part1(player1: Int, player2: Int): Int {
        var rolls = 0
        var game = 0

        fun roll(): Int {
            rolls++
            game = if (game == 100) 1 else game + 1
            return game
        }

        var player1Score = Pair(0, player1)
        var player2Score = Pair(0, player2)

        val movePlayer1: () -> Int = {
            player1Score = player1Score.move(roll() + roll() + roll())
            player1Score.first
        }

        val movePlayer2: () -> Int = {
            player2Score = player2Score.move(roll() + roll() + roll())
            player2Score.first
        }

        while (movePlayer1() < 1000 && movePlayer2() < 1000) {
        }

        return rolls * if (player1Score.first >= 1000) player2Score.first else player2Score.first
    }

    fun part2(player1: Int, player2: Int): Long {
        val cache = mutableMapOf<Int, Int>()
        for (die1 in 1..3) {
            for (die2 in 1..3) {
                for (die3 in 1..3) {
                    cache.merge(
                        die1 + die2 + die3, 1
                    ) { a: Int, b: Int -> Integer.sum(a, b) }
                }
            }
        }

        fun count(pawns: IntArray, scores: IntArray, p: Int, occurs: Long, wins: LongArray): LongArray {
            for (i in 0..1) {
                if (scores[i] >= 21) {
                    val nw = wins.copyOf(2)
                    nw[i] += occurs
                    return nw
                }
            }
            val newWins = LongArray(2)
            cache.forEach { (die, occ) ->
                val ss: IntArray = scores.copyOf(2)
                val ps: IntArray = pawns.copyOf(2)
                ps[p] += die
                ps[p] %= 10
                ss[p] += ps[p] + 1
                val w: LongArray = count(ps, ss, if (p == 0) 1 else 0, occurs * occ, wins)
                newWins[0] += w[0]
                newWins[1] += w[1]
            }
            return newWins
        }

        val wins = count(intArrayOf(player1, player2), intArrayOf(0, 0), 0, 1, LongArray(2))

        return max(wins[0], wins[1])
    }

    val (player1, player2) = readInput("Day21").let {
        it.first().replace("Player 1 starting position: ", "").toInt() to it.last()
            .replace("Player 2 starting position: ", "").toInt()
    }
    println(part1(player1, player2))
    println(part2(player1 - 1, player2 - 1))
}
