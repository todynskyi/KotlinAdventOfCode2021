import kotlin.math.abs

fun main() {

    fun calculate(left: Set<Point>, right: Set<Point>): Pair<Point, Set<Point>>? =
        (0 until 6).firstNotNullOfOrNull { face ->
            (0 until 4).firstNotNullOfOrNull { rotation ->
                val reoriented = right.map { it.face(face).rotate(rotation) }.toSet()
                left.firstNotNullOfOrNull { s1 ->
                    reoriented.firstNotNullOfOrNull { s2 ->
                        val point = s1 - s2
                        val moved = reoriented.map { it + point }.toSet()
                        if (moved.intersect(left).size >= 12) {
                            Pair(point, moved)
                        } else null
                    }
                }
            }
        }

    fun part1(scanners: List<Set<Point>>): Int {
        val beacons = scanners.first().toMutableSet()
        val sectors = ArrayDeque<Set<Point>>().apply { addAll(scanners.drop(1)) }
        while (sectors.isNotEmpty()) {
            val thisSector = sectors.removeFirst()
            val calculation = calculate(beacons, thisSector)
            if (calculation == null) {
                sectors.add(thisSector)
            } else {
                beacons.addAll(calculation.second)
            }
        }

        return beacons.size
    }


    fun part2(scanners: List<Set<Point>>): Int {
        val beacons = scanners.first().toMutableSet()
        val foundScanners = mutableSetOf(Point(0, 0, 0))
        val sectors = ArrayDeque<Set<Point>>().apply { addAll(scanners.drop(1)) }
        while (sectors.isNotEmpty()) {
            val thisSector = sectors.removeFirst()
            val calculation = calculate(beacons, thisSector)
            if (calculation == null) {
                sectors.add(thisSector)
            } else {
                beacons.addAll(calculation.second)
                foundScanners.add(calculation.first)
            }
        }

        return foundScanners.flatMapIndexed { index, a ->
            foundScanners.drop(index).map { b -> a to b }
        }.maxOf { it.first.distance(it.second) }
    }

    val input: List<Set<Point>> = readText("Day19").split("\n\n").map { scanner ->
        scanner.split("\n").drop(1).map { line ->
            val (x, y, z) = line.split(Regex(","), 3)
            Point(x.toInt(), y.toInt(), z.toInt())
        }.toSet()
    }

    println(part1(input))
    println(part2(input))
}

data class Point(val x: Int, val y: Int, val z: Int) {
    operator fun plus(point: Point): Point = Point(x + point.x, y + point.y, z + point.z)
    operator fun minus(point: Point): Point = Point(x - point.x, y - point.y, z - point.z)

    fun rotate(rotating: Int): Point = when (rotating) {
        0 -> this
        1 -> Point(-y, x, z)
        2 -> Point(-x, -y, z)
        3 -> Point(y, -x, z)
        else -> throw IllegalArgumentException("Invalid rotation: $rotating")
    }

    fun face(facing: Int): Point = when (facing) {
        0 -> this
        1 -> Point(x, -y, -z)
        2 -> Point(x, -z, y)
        3 -> Point(-y, -z, x)
        4 -> Point(y, -z, -x)
        5 -> Point(-x, -z, -y)
        else -> throw IllegalArgumentException("Invalid facing: $facing")
    }

    fun distance(point: Point): Int = abs(x - point.x) + abs(y - point.y) + abs(z - point.z)
}
