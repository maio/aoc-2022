package cz.maio

import cz.maio.Day10Test.Instruction.Companion.ticks
import org.approvaltests.Approvals
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.Math.abs


class Day10Test {
    sealed class Instruction {
        abstract fun ticks(): List<Int>

        object Noop : Instruction() {
            override fun ticks(): List<Int> {
                return listOf(0)
            }
        }

        data class AddX(val x: Int) : Instruction() {
            override fun ticks(): List<Int> {
                return listOf(0, x)
            }
        }

        companion object {
            fun parse(s: String): Instruction {
                if (s == "noop") return Noop
                val (instruction, value) = s.split(" ")
                return when (instruction) {
                    "addx" -> AddX(value.toInt())
                    else -> error("Invalid instruction")
                }
            }

            fun List<Instruction>.ticks() = listOf(1) + this.flatMap { it.ticks() }
        }
    }

    private fun compute1(input: String): Int {
        val lines = input.lines()
        val instructions = lines.map { Instruction.parse(it) }

        return listOf(20, 60, 100, 140, 180, 220).map {
            instructions.ticks().take(it).sum() * it
        }.sum()
    }

    private fun compute2(input: String): String {
        val lines = input.lines()
        val instructions = lines.map { Instruction.parse(it) }

        val registerValues = instructions.ticks().take(240).runningReduce { acc, i ->
            acc + i
        }

        val pixels = registerValues.mapIndexed { index, value ->
            if (abs((index % 40) - value) < 2) "#" else "."
        }

        return pixels.chunked(40).joinToString("\n") { it.joinToString("") }
    }

    @Test
    fun `sample input - part 1`() {
        val input = readResource("/sample.txt")

        assertThat(compute1(input)).isEqualTo(13140)
    }

    @Test
    fun `part 1`() {
        val input = readResource("/input.txt")

        assertThat(compute1(input)).isEqualTo(16880)
    }

    @Test
    fun `sample input - part 2`() {
        val input = readResource("/sample.txt")

        Approvals.verify(compute2(input))
    }

    @Test
    fun `part 2`() {
        val input = readResource("/input.txt")

        Approvals.verify(compute2(input))
    }
}
