package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day02Test {
    enum class RoundResult {
        Won, Draw, Lost;

        fun score() = when (this) {
            Won -> 6
            Draw -> 3
            Lost -> 0
        }

        fun invert() = when (this) {
            Won -> Lost
            Draw -> Draw
            Lost -> Won
        }

        companion object {
            fun of(s: String) = when (s) {
                "X" -> Lost
                "Y" -> Draw
                "Z" -> Won
                else -> error("Unknown: $s")
            }
        }
    }

    enum class Choice {
        Rock, Paper, Scissors;

        fun score(): Int = when (this) {
            Rock -> 1
            Paper -> 2
            Scissors -> 3
        }

        fun compare(other: Choice) = when (this) {
            Rock -> when (other) {
                Rock -> RoundResult.Draw
                Paper -> RoundResult.Lost
                Scissors -> RoundResult.Won
            }

            Paper -> when (other) {
                Rock -> RoundResult.Won
                Paper -> RoundResult.Draw
                Scissors -> RoundResult.Lost
            }

            Scissors -> when (other) {
                Rock -> RoundResult.Lost
                Paper -> RoundResult.Won
                Scissors -> RoundResult.Draw
            }
        }

        fun findMyChoiceForResult(result: RoundResult): Choice {
            return listOf(Rock, Paper, Scissors).first { this.compare(it) == result }
        }

        companion object {
            fun of(s: String): Choice {
                return when (s) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    "C", "Z" -> Scissors
                    else -> error("Unknown: $s")
                }
            }
        }
    }

    /**
     * A|X - Rock
     * B|Y - Paper
     * C|Z - Scissors
     *
     * Scoring:
     *
     * Shape Selected:
     * 1 for Rock
     * 2 for Paper
     * 3 for Scissors
     *
     * Outcome:
     * 0 if you lost
     * 3 if the round was a draw
     * and 6 if you won
     */

    private fun computePart1(input: String): Int {
        return input.lines().map {
            computePart1Single(it)
        }.sum()
    }

    private fun computePart1Single(line: String): Int {
        val (theirs, my) = line.split(" ").map { Choice.of(it) }

        return my.compare(theirs).score() + my.score()
    }

    private fun computePart2(input: String) = input.lines().sumOf {
        computePart2Single(it)
    }

    private fun computePart2Single(line: String): Int {
        val (a, b) = line.split(" ")
        val theirs = Choice.of(a)
        val result = RoundResult.of(b)
        val my = theirs.findMyChoiceForResult(result.invert())

        return my.compare(theirs).score() + my.score()
    }

    @Test
    fun `sample input`() {
        val input = """
            A Y
            B X
            C Z
        """.trimIndent()

        assertThat(computePart1(input)).isEqualTo(8 + 1 + 6)
        assertThat(computePart2(input)).isEqualTo(4 + 1 + 7)
    }

    @Test
    fun `part 1 & 2`() {
        val input = readResource("/input.txt")

        assertThat(computePart1(input)).isEqualTo(14297)
        assertThat(computePart2(input)).isEqualTo(10498)
    }
}
