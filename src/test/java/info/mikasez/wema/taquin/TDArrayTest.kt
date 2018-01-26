package info.mikasez.wema.taquin

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
}