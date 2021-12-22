import kotlin.math.max
import kotlin.math.min


fun main() {

    fun part1(steps: List<Step>): Int {

        val inRange: (LongRange) -> Boolean = { it.first >= -50 && it.last <= 50 }

        val cubes = mutableSetOf<Cube>()

        steps.filter { inRange(it.x) && inRange(it.y) && inRange(it.z) }
            .forEach { step ->
                for (x in step.x) {
                    for (y in step.y) {
                        for (z in step.z) {
                            val cube = Cube(x, y, z)
                            if (step.on) {
                                cubes.add(cube)
                            } else {
                                cubes.remove(cube)
                            }
                        }
                    }
                }
            }

        return cubes.size
    }

    fun part2(steps: List<Step>): Long {
        val placed = mutableListOf<Step>()
        steps.forEach { c ->
            val needToVisit = mutableListOf<Step>()
            if (c.on) needToVisit.add(c)
            placed.forEach { p ->
                p.intersect(c, !p.on)?.let { needToVisit.add(it) }
            }
            placed.addAll(needToVisit)
        }
        return placed.sumOf { it.volume() }
    }

    val roRange: (String) -> LongRange = { raw ->
        val (start, end) = raw.substringAfter("=").split("..", limit = 2)
        start.toLong()..end.toLong()
    }

    val steps = readInput("Day22")
        .map { line ->
            val mode = line.substringBefore(" ")
            val (rawX, rawY, rawZ) = line.replace(Regex("on |off "), "").split(",", limit = 3)
            Step(mode == "on", roRange(rawX), roRange(rawY), roRange(rawZ))
        }

    println(part1(steps))
    println(part2(steps))
}

data class Step(val on: Boolean, val x: LongRange, val y: LongRange, val z: LongRange) {
    fun volume(): Long =
        (x.last - x.first + 1L) * (y.last - y.first + 1L) * (z.last - z.first + 1L) * (if (on) 1 else -1)

    fun intersect(c: Step, on: Boolean): Step? = if (
        x.first > c.x.last || x.last < c.x.first || y.first > c.y.last || y.last < c.y.first || z.first > c.z.last || z.last < c.z.first
    ) null else Step(
        on,
        max(x.first, c.x.first)..min(x.last, c.x.last),
        max(y.first, c.y.first)..min(y.last, c.y.last),
        max(z.first, c.z.first)..min(z.last, c.z.last)
    )
}

data class Cube(val x: Long, val y: Long, val z: Long)