package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class Day05Test {
    @Test
    fun `sample input`() {
        val input = readResource("/sample.txt")

        val board = parseBoard(input)
        val instructionLines = parseInstructions(input)

        instructionLines.forEach { instruction ->
            board.applyInstruction1(instruction)
        }

        val result = board.result()

        assertThat(result).isEqualTo("CMZ")
    }

    @Test
    fun `sample input - part 2`() {
        val input = readResource("/sample.txt")

        val board = parseBoard(input)
        val instructionLines = parseInstructions(input)

        instructionLines.forEach { instruction ->
            board.applyInstruction2(instruction)
        }

        val result = board.result()

        assertThat(result).isEqualTo("MCD")
    }

    @Test
    fun `part 1`() {
        val input = readResource("/input.txt")

        val board = parseBoard(input)
        val instructionLines = parseInstructions(input)

        instructionLines.forEach { instruction ->
            board.applyInstruction1(instruction)
        }

        val result = board.result()

        assertThat(result).isEqualTo("ZRLJGSCTR")
    }

    @Test
    fun `part 2`() {
        val input = readResource("/input.txt")

        val board = parseBoard(input)
        val instructionLines = parseInstructions(input)

        instructionLines.forEach { instruction ->
            board.applyInstruction2(instruction)
        }

        val result = board.result()

        assertThat(result).isEqualTo("PRTTGRFPB")
    }

    private fun MutableMap<Int, Stack<String>>.result() =
        toSortedMap().values.joinToString("") { it.peek() }

    private fun parseInstructions(input: String) = input.split("\n\n")[1].lines().map {
        parseInstruction(it)
    }

    private fun MutableMap<Int, Stack<String>>.applyInstruction1(instruction: List<Int>) {
        val (amount, fromCol, toCol) = instruction
        repeat(amount) {
            push(toCol, pop(fromCol))
        }
    }

    private fun MutableMap<Int, Stack<String>>.applyInstruction2(instruction: List<Int>) {
        val (amount, fromCol, toCol) = instruction
        val lift = Stack<String>()
        repeat(amount) {
            lift.push(pop(fromCol))
        }
        getValue(toCol).addAll(lift.reversed())
    }

    private val instructionRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    private fun parseInstruction(line: String): List<Int> {
        val groups = instructionRegex.find(line)?.groups?.map { it?.value }!!
        return groups.drop(1).map { it?.toInt()!! }
    }

    private fun parseBoard(input: String): MutableMap<Int, Stack<String>> {
        val lines = input.lines()
        val columns = lines.find { it.startsWith(" 1") }!!.replace(" ", "").toList().map { it.digitToInt() }
        val boardLinesX = lines.takeWhile { !it.startsWith(" 1") }.map {
            it.chunked(4).map { it[1].toString() }
        }
        val boardLines = boardLinesX
            .map { it.joinToString("") }
            .reversed()

        val board = mutableMapOf<Int, Stack<String>>().withDefault {
            Stack()
        }

        columns.forEach { col ->
            boardLines.forEach { line ->
                line.getOrNull(col - 1)?.let {
                    if (it.toString().isNotBlank()) {
                        board.push(col, it.toString())
                    }
                }
            }
        }

        return board
    }

    private fun MutableMap<Int, Stack<String>>.pop(col: Int): String {
        return getValue(col).pop()
    }

    private fun MutableMap<Int, Stack<String>>.push(col: Int, item: String) {
        compute(col) { key: Int, stack: Stack<String>? ->
            (stack ?: Stack()).apply {
                push(item)
            }
        }
    }
}
