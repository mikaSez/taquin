package info.mikasez.wema.taquin

import org.assertj.core.api.Assertions
import org.junit.Test
import kotlin.test.assertEquals

class TDArrayTest {

    @Test
    fun `should put a value on the specified position`() {
        val tested = TDArray(2, 2)
        tested.put(1, 0, 5)
        val expected = TDArray(2, 2, arrayOf(null, 5, null, null))

        assertEquals(tested, expected)
    }

    @Test
    fun `should retrieve the value at the specified position`() {
        val tested = TDArray(2, 2, arrayOf(null, null, null, 5))
        val expected = 5

        val result = tested.get(1, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve the value above the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = 2

        val result = tested.getUp(1, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve null if nothing above the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = null

        val result = tested.getUp(1, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve the value below the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = 4

        val result = tested.getDown(1, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve null if nothing below the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = null

        val result = tested.getDown(1, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve the value on the right of the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = 4

        val result = tested.getRight(0, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve null if nothing on the right of the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = null

        val result = tested.getRight(1, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve the value on the left of the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = 1

        val result = tested.getLeft(1, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `should retrieve null if nothing on the left of the specified position`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 4))
        val expected = null

        val result = tested.getLeft(0, 1)
        assertEquals(expected, result)
    }

    @Test
    fun `should return available swaps from the current position`() {
        val tested = TDArray(3, 3, arrayOf(1, 2, 3,
                4, 0, 5,
                6, 7, 8))

        val expected1 = TDArray(3, 3, arrayOf(1, 0, 3,
                4, 2, 5,
                6, 7, 8))

        val expected2 = TDArray(3, 3, arrayOf(1, 2, 3,
                0, 4, 5,
                6, 7, 8))

        val expected3 = TDArray(3, 3, arrayOf(1, 2, 3,
                4, 5, 0,
                6, 7, 8))

        val expected4 = TDArray(3, 3, arrayOf(1, 2, 3,
                4, 7, 5,
                6, 0, 8))

        val result = tested.getExistingSwaps()

        Assertions.assertThat(result).containsExactlyInAnyOrder(expected1, expected2, expected3, expected4)
    }

    @Test
    fun `next TDArray should know previous one if exist`() {
        val tested = TDArray(2, 2, arrayOf(1, 2,
                0, 3))
        val result = tested.getExistingSwaps()
        val expected1 = TDArray(2, 2, arrayOf(0, 2,
                1, 3), tested)
        val expected2 = TDArray(2, 2, arrayOf(1, 2,
                3, 0), tested)
        Assertions.assertThat(result).containsExactlyInAnyOrder(expected1, expected2)
    }

    @Test
    fun `should deduce final state of the array`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 0))
        val tested2 = TDArray(2, 2, arrayOf(1, 2, 0, 3))

        val result1 = tested.deduceFinalState()
        val result2 = tested2.deduceFinalState()

        Assertions.assertThat(tested).isEqualToComparingFieldByField(result1)
        Assertions.assertThat(tested.inSameOrder(result1)).isTrue()
        Assertions.assertThat(tested.inSameOrder(tested2)).isFalse()
        Assertions.assertThat(result1).isEqualToComparingFieldByField(result2)
        Assertions.assertThat(result1.inSameOrder(result2)).isTrue()

    }

    @Test
    fun `should return the pretty format of a given tdArray`() {
        val tested = TDArray(2, 2, arrayOf(1, 2, 3, 0))
        val expected = "2 2\n1 2 \n3 0 \n"

        val result = tested.prettyFormat()

        Assertions.assertThat(result)
                .isEqualTo(expected)
    }

}