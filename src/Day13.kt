fun main() {

    fun myFold(points: Set<Pair<Int, Int>>, fold: Pair<String, Int>): Set<Pair<Int, Int>> = points.map { (x, y) ->
        when {
            fold.first == "y" && y > fold.second -> Pair(x, fold.second - (y - fold.second))
            fold.first == "x" && x > fold.second -> Pair(fold.second - (x - fold.second), y)
            else -> Pair(x, y)
        }
    }.toSet()

    fun part1(dots: Set<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Int =
        folds.take(1).fold(dots, ::myFold).size

    fun part2(dots: Set<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Set<Pair<Int, Int>> =
        folds.fold(dots, ::myFold)

    val input = readInput("Day13")
    val dots = input.filter { it.contains(",") }
        .map { line ->
            val parts = line.split(",")
            parts[0].toInt() to parts[1].toInt()
        }.toSet()
    val folds = input.filter { it.startsWith("fold") }
        .map { it.substringAfter("fold along ") }
        .map { it.split("=") }.map { it[0] to it[1].toInt() }

    fun print(data: Set<Pair<Int, Int>>) {
        (0..data.maxOf { it.second }).forEach { y ->
            (0..data.maxOf { it.first }).forEach { x ->
                print(if (Pair(x, y) in data) "#" else " ")
            }
            println()
        }
    }

    println(part1(dots, folds))
    print(part2(dots, folds))
}
