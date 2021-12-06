fun main() {

    fun part1(data: List<Int>): Int {
        var fishes = data.toMutableList()

        repeat(80) {
            var newFishes = 0;
            fishes = fishes.map { days ->
                if (days == 0) {
                    newFishes++
                    6
                } else {
                    days - 1
                }
            }.toMutableList()

            repeat(newFishes) {
                fishes.add(8)
            }
        }

        return fishes.count()
    }

    fun part2(data: List<Int>): Long {
        var score = mutableMapOf<Int, Long>()

        data.forEach { fish ->
            score[fish] = score.getOrDefault(fish, 0) + 1
        }

        repeat(256) { _ ->
            val temp = mutableMapOf<Int, Long>()
            score.forEach {
                if(it.key == 0) {
                    temp[6] = temp.getOrDefault(6, 0) + it.value
                    temp[8] = it.value
                } else if (it.key > 0) {
                    temp[it.key - 1] = (temp.getOrDefault(it.key - 1, 0) + it.value)
                }
            }
            score = temp
        }

        return score.map { it.value }.sum()
    }

    val input = readInput("Day06").first()
        .substringAfter("Initial state: ")
        .split(",")
        .map { it.toInt() }

    println(part1(input))
    println(part2(input))
}
