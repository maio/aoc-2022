package cz.maio

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun `sample input`() {
        assertThat(compute1("mjqjpqmgbljsphdztnvjfqwrcgsmlb")).isEqualTo(7)
        assertThat(compute1("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(5)
        assertThat(compute1("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(6)
        assertThat(compute1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(10)
        assertThat(compute1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(11)
    }

    @Test
    fun `part 1 & 2`() {
        val input = readResource("/input.txt")

        assertThat(compute1(input)).isEqualTo(1175)
        assertThat(compute2(input)).isEqualTo(3217)
    }

    private fun compute1(input: String) = input.findPacket(4)
    private fun compute2(input: String) = input.findPacket(14)

    private fun String.findPacket(length: Int) = windowedSequence(length)
        .indexOfFirst { it.toSet().size == length } + length
}
