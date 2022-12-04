package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day03Test {
    @Test
    fun `sample input`() {
        val input = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()

        run {
            val line = input.lines().first()
            val (c1, c2) = parseCompartments(line)
            assertThat(c1).isEqualTo("vJrwpWtwJgWr")
            assertThat(c2).isEqualTo("hcsFMMfFFhFp")
        }

        run {
            val line = input.lines().first()
            val commonItem = findCommonItem(line)
            assertThat(commonItem).isEqualTo('p')
        }

        assertThat('A'.value()).isEqualTo(27)
        assertThat('a'.value()).isEqualTo(1)

        assertThat(compute1(input)).isEqualTo(157)

        assertThat(compute2(input)).isEqualTo(70)
    }

    @Test
    fun `part 1 & 2`() {
        assertThat(compute1(readResource("/input.txt"))).isEqualTo(7742)

        assertThat(compute2(readResource("/input.txt"))).isEqualTo(2276)
    }

    private fun compute1(input: String) = input.lines().map {
        findCommonItem(it).value()
    }.sum()

    private fun findCommonItem(line: String): Char {
        val (c1, c2) = parseCompartments(line)
        return c1.toSet().intersect(c2.toSet()).first()
    }

    private fun parseCompartments(line: String) = listOf(line.take(line.length / 2), line.drop(line.length / 2))

    private fun compute2(input: String) = input.lines().chunked(3) { group ->
        findCommonItemInGroup(group).value()
    }.sum()


    private fun findCommonItemInGroup(group: List<String>) =
        group.map { it.toSet() }.reduce { acc, chars -> acc.intersect(chars) }.first()
}

private val lowercase = 'a'..'z'
private val uppercase = 'A'..'Z'

private fun Char.value() = if (this in lowercase) {
    code - 96
} else {
    code - 38
}
