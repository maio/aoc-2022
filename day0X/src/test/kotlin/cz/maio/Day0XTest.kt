package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day0XTest {
    @Test
    fun `sample input`() {
        val input = """
        """.trimIndent()

        assertThat(input).isEqualTo(input)
    }

    @Test
    fun `part 1 & 2`() {
        val input = readResource("/input.txt")

        assertThat(input).isEqualTo(input)
    }
}
