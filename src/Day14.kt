fun main() {

    fun part1(template: String, pairs: Map<String, String>): Int {
        var polymer = template

        repeat(10) {
            polymer = polymer
                .mapIndexedNotNull { index, c ->
                    if (index != polymer.lastIndex) c.toString() + polymer[index + 1].toString() else null
                }
                .let { converted ->
                    converted
                        .mapIndexed { index, pair ->
                            val start = pair.first().toString() + pairs[pair]
                            if (index != converted.lastIndex) start else start + pair.last().toString()
                        }
                        .joinToString("")
                }

        }

        return polymer.groupingBy { it }.eachCount()
            .let { count -> count.maxOf { it.value } - count.minOf { it.value } }
    }

    fun part2(template: String, pairs: Map<String, String>): Long {
        var polymers = template
            .mapIndexedNotNull { index, c ->
                if (index != template.lastIndex) c.toString() + template[index + 1].toString() else null
            }
            .map { it to 1 }
            .groupBy { it }
            .map { (key, value) -> key.first to value.sumOf { it.second }.toLong() }
            .toMap()

        repeat(40) {
            val newPolymers = mutableMapOf<String, Long>()
            for (polymer in polymers.keys) {
                val pair = pairs[polymer]
                val (first, second) = polymer.mapIndexed { index, c ->
                    if (index == 0) "$c$pair" else "$pair$c"
                }
                newPolymers[first] = newPolymers.getOrDefault(first, 0) + polymers[polymer]!!
                newPolymers[second] = newPolymers.getOrDefault(second, 0) + polymers[polymer]!!
            }

            polymers = newPolymers.toMap()
        }

        val counts = mutableMapOf<Char, Long>()
        polymers.forEach { (key, value) ->
            counts[key[1]] = counts.getOrDefault(key[1], 0) + value
        }

        return counts.maxOf { it.value } - counts.minOf { it.value }
    }

    val input = readInput("Day14")
    val template = input.first()
    val pairs = input.subList(2, input.size).associate { line -> line.split(" -> ").let { it.first() to it.last() } }

    println(part1(template, pairs))
    println(part2(template, pairs))
}
