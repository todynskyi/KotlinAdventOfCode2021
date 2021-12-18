fun main() {

    fun toNode(line: String): Node {
        if (line.length == 1) {
            return NodeNumber(line.toInt())
        }

        var level = 0
        var splitAt = -1
        var i = 0
        while (i < line.length && splitAt == -1) {
            when (line[i]) {
                '[' -> level++
                ']' -> level--
                ',' -> {
                    if (level == 1) {
                        splitAt = i
                    }
                }
            }
            i++
        }

        return NodePair(
            left = toNode(line.substring(1, splitAt)),
            right = toNode(line.substring(splitAt + 1, line.length - 1))
        )
    }

    fun toLine(node: Node): String {
        return when (node) {
            is NodeNumber -> node.value.toString()
            is NodePair -> "[${toLine(node.left)},${toLine(node.right)}]"
        }
    }

    fun reduce(node: Node) {
        do {
            val exploded = node.explode() != null
            val split = if (!exploded) node.split() else false
        } while (exploded || split)
    }

    fun part1(lines: List<String>): Int = toNode(lines.reduce { acc, line ->
        val node = toNode("[$acc,$line]")
        reduce(node)
        toLine(node)
    }).magnitude()

    fun part2(lines: List<String>): Int = lines.maxOf { first ->
        lines.filterNot { it == first }.maxOf { second ->
            toNode("[$first,$second]").apply { reduce(this) }.magnitude()
        }
    }

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}

sealed class Node {
    abstract fun magnitude(): Int
    abstract fun addToLeft(value: Int)
    abstract fun addToRight(value: Int)
    abstract fun split(): Boolean
    abstract fun explode(level: Int = 0): Pair<Int, Int>?
}

class NodeNumber(var value: Int) : Node() {
    override fun magnitude(): Int = value

    override fun addToLeft(value: Int) {
        this.value += value
    }

    override fun addToRight(value: Int) {
        this.value += value
    }

    override fun split(): Boolean = false

    override fun explode(level: Int): Pair<Int, Int>? = null
}

class NodePair(var left: Node, var right: Node) : Node() {
    override fun magnitude(): Int = 3 * left.magnitude() + 2 * right.magnitude()

    override fun addToLeft(value: Int) {
        left.addToLeft(value)
    }

    override fun addToRight(value: Int) {
        right.addToRight(value)
    }

    override fun split(): Boolean {
        if (split(left) { left = it } || left.split()) {
            return true
        }
        return split(right) { right = it } || right.split()
    }

    private fun split(node: Node, split: (Node) -> Unit): Boolean {
        if (node is NodeNumber) {
            val value = node.value
            if (value >= 10) {
                split(NodePair(NodeNumber(value / 2), NodeNumber((value + 1) / 2)))
                return true
            }
        }
        return false
    }

    override fun explode(level: Int): Pair<Int, Int>? {
        if (level == 4) {
            return (left as NodeNumber).value to (right as NodeNumber).value
        }
        left.explode(level + 1)?.let { (first, second) ->
            if (first != -1 && second != -1) {
                this.left = NodeNumber(0)
                this.right.addToLeft(second)
                return first to -1
            }
            if (second != -1) {
                this.right.addToLeft(second)
                return -1 to -1
            }
            return first to -1
        }
        right.explode(level + 1)?.let { (first, second) ->
            if (first != -1 && second != -1) {
                this.right = NodeNumber(0)
                this.left.addToRight(first)
                return -1 to second
            }
            if (first != -1) {
                this.left.addToRight(first)
                return -1 to -1
            }
            return -1 to second
        }
        return null
    }
}