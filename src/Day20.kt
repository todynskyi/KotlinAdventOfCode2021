fun main() {

    fun support(x: Int, y: Int, n: Int, m: Int) = x in 0 until n && y >= 0 && y < m

    fun convert(algorithm: String, image: List<String>, infinite: Boolean): List<String> {

        val n = image[0].length
        val m = image.size

        val temp = mutableListOf<String>()

        for (i in -1..image.size) {
            var converted = ""
            for (j in -1..image.size) {
                var bin = ""
                for (x in i - 1..i + 1) {
                    for (y in j - 1..j + 1) {
                        bin += if (support(x, y, n, m) && image[x][y] == '#') 1 else {
                            if (!support(x, y, n, m) && infinite) 1 else 0
                        }
                    }
                }

                converted += algorithm[bin.toInt(2)]
            }
            temp.add(converted)
        }

        return temp
    }

    fun calculate(algorithm: String, image: List<String>, repeat: Int) : Int {
        var infinite = false
        var result = image
        repeat(repeat) {
            result = convert(algorithm, result, infinite)

            infinite = when {
                algorithm[0] == '.' -> false
                algorithm[0] == '#' && algorithm[algorithm.length - 1] == '.' -> !infinite
                algorithm[0] == '#' && algorithm[algorithm.length - 1] == '#' -> true
                else -> throw  IllegalAccessError("Please check configuration")
            }
        }

        return result.sumOf { line -> line.count { pixel -> pixel == '#' } }
    }

    fun part1(algorithm: String, image: List<String>): Int  = calculate(algorithm, image, 2)

    fun part2(algorithm: String, image: List<String>): Int  = calculate(algorithm, image, 50)

    val input = readText("Day20")
    val (algorithm, imageString) = input.split("\n\n", limit = 2)
    val image = imageString.split("\n").filter { it.isNotBlank() }


    println(part1(algorithm, image))
    println(part2(algorithm, image))
}
