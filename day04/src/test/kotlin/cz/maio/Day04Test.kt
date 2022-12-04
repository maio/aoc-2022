package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day04Test {
    @Test
    fun `sample input`() {
        val input = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()

        run {
            val line = input.lines().first()
            val ranges = line.toRanges()

            assertThat(ranges).isEqualTo(listOf(2..4, 6..8))
            assertThat(ranges.containsFullOverlap()).isFalse
        }

        run {
            val line = input.lines().drop(3).first()
            val ranges = line.toRanges()

            assertThat(ranges).isEqualTo(listOf(2..8, 3..7))
            assertThat(ranges.containsFullOverlap()).isTrue
            assertThat(ranges.reversed().containsFullOverlap()).isTrue
        }

        assertThat(compute(input)).isEqualTo(2)
    }

    @Test
    fun `part 1 & 2`() {
        val input = readResource("/input.txt")

        assertThat(compute(input)).isEqualTo(560)
        assertThat(compute2(input)).isEqualTo(839)
    }

    @Test
    fun `doesOneContainsTheOther works`() {
        assertThat(listOf(1..3, 1..1).containsFullOverlap()).isTrue
        assertThat(listOf(1..1, 1..3).containsFullOverlap()).isTrue
    }

    private fun compute(input: String) = parseLines(input).filter { it.containsFullOverlap() }.size
    private fun compute2(input: String) = parseLines(input).filter { it.containsPartialOverlap() }.size

    private fun parseLines(input: String) = input.lines().map { it.toRanges() }

    private fun String.toRanges() = split(",").map {
        val (a, b) = it.split("-").map { it.toInt() }
        a..b
    }

    private fun List<IntRange>.containsFullOverlap(): Boolean {
        val (r1, r2) = this

        return r1.contains(r2) || r2.contains(r1)
    }

    private fun IntRange.contains(other: IntRange) = when {
        other.first in this && other.last in this -> true
        this.first in other && this.last in other -> true
        else -> false
    }

    private fun List<IntRange>.containsPartialOverlap(): Boolean {
        val (r1, r2) = this

        return r1.intersects(r2)
    }

    private fun IntRange.intersects(other: IntRange) = when {
        other.first in this || other.last in this -> true
        this.first in other || this.last in other -> true
        else -> false
    }
}
