package info.mikasez.wema.taquin

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals

class TaquinTest {

    /**
     * Rules should be public because they didn't want to mess with the security manager
     * */
    @get:Rule
    public val unexpectedException: ExpectedException = ExpectedException.none()

    @Test
    fun `should read file and return contained lines`() {
        val fileContent = "/testFile1".readFile()
        val expected = listOf("first Line", "second line")

        assertEquals(expected, fileContent)
    }

    @Test
    fun `should throw illegal argument exception on file not found`() {
        unexpectedException.expect(IllegalArgumentException::class.java)
        "/doesNotExist".readFile()
    }

    @Test
    fun `should read file and return TDArray`() {
        val tdArray = Taquin().readFromFile("/testFile2")
        val expected = TDArray(4, 4, arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0))

        assertEquals(expected, tdArray)
    }

    @Test
    fun `should throw illegal argument exception with decent message when file not found`() {
        val path = "/doesNotExist"
        unexpectedException.expect(IllegalArgumentException::class.java)
        unexpectedException.expectMessage("File not found $path")
        Taquin().readFromFile(path)
    }
}