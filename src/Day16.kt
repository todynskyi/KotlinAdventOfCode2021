fun main() {

    fun String.toVersion(): Int = this.take(3).toInt(2)
    fun String.toType(): Int = this.drop(3).take(3).toInt(2)

    fun parse(packet: String): Packet {
        val version = packet.toVersion()
        val type = packet.toType()
        val data = packet.drop(6)

        return if (type == 4) {
            data.chunked(5)
                .let { it.takeWhile { g -> g.first() == '1' } + it.first { g -> g.first() == '0' } }
                .let {
                    val value = it.joinToString("")
                        .chunked(5)
                        .joinToString("") { it.drop(1) }
                        .toLong(2)
                    Literal(version, value, "${packet.take(6)}${it.joinToString("")}")
                }
        } else {
            if (data.first() == '0') {
                val totalLength = data.drop(1).take(15).toInt(2)
                val subPackets = buildList<Packet> {
                    while (totalLength - sumOf { it.bits.length } > 0) {
                        parse(data.drop(16 + sumOf { it.bits.length })).also { add(it) }
                    }
                }
                Operator(
                    version,
                    type,
                    "${packet.take(22)}${subPackets.joinToString("") { it.bits }}",
                    subPackets
                )
            } else {
                val subPacketsNumber = data.drop(1).take(11).toInt(2)
                val subPackets = buildList<Packet> {
                    repeat(subPacketsNumber) {
                        parse(data.drop(12 + sumOf { it.bits.length })).also { add(it) }
                    }
                }
                Operator(
                    version,
                    type,
                    "${packet.take(18)}${subPackets.joinToString("") { it.bits }}",
                    subPackets
                )
            }
        }
    }

    fun part1(packet: String): Int {

        fun versionSum(p: Packet): Int {
            return when (p) {
                is Literal -> p.version
                is Operator -> p.version + p.packets.sumOf { versionSum(it) }
            }
        }

        return versionSum(parse(packet))
    }

    fun part2(packet: String): Long = parse(packet).value()

    val input = readInput("Day16").first().map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")

    println(part1(input))
    println(part2(input))
}

sealed class Packet(val bits: String) {
    abstract fun value(): Long
}

class Literal(val version: Int, private val value: Long, bits: String) : Packet(bits) {
    override fun value() = value
}

class Operator(val version: Int, private val type: Int, bits: String, val packets: List<Packet>) : Packet(bits) {

    override fun value(): Long = when (type) {
        0 -> packets.sumOf { it.value() }
        1 -> packets.fold(1) { acc, packet -> acc * packet.value() }
        2 -> packets.minOf { it.value() }
        3 -> packets.maxOf { it.value() }
        5 -> (packets[0].value() > packets[1].value()).toLong()
        6 -> (packets[0].value() < packets[1].value()).toLong()
        7 -> (packets[0].value() == packets[1].value()).toLong()
        else -> throw IllegalAccessException("Unsupported type $type")
    }

    private fun Boolean.toLong() = if (this) 1L else 0L
}