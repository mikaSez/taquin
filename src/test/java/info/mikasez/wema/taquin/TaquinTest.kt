package info.mikasez.wema.taquin

import info.mikasez.wema.taquin.exceptions.IllegalContentException
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
    fun `should throw illegal content exception when file has not allowed characters`() {
        val path = "/incorrectFile1"
        unexpectedException.expect(IllegalContentException::class.java)
        unexpectedException.expectMessage("File content illegal characters, only numbers allowed")
        Taquin().readFromFile(path)
    }

    @Test
    fun `should throw illegal content exception when file has not enough content`() {
        val path = "/incorrectFile2"
        unexpectedException.expect(IllegalContentException::class.java)
        unexpectedException.expectMessage("14puzzle content should fill the entire array")
        Taquin().readFromFile(path)
    }

    @Test
    fun `should throw illegal content exception when file has too much content`() {
        val path = "/incorrectFile3"
        unexpectedException.expect(IllegalContentException::class.java)
        unexpectedException.expectMessage("There are too many content for given size parameters")
        Taquin().readFromFile(path)
    }

    @Test
    fun `should throw illegal argument exception with decent message when file not found`() {
        val path = "/doesNotExist"
        unexpectedException.expect(IllegalArgumentException::class.java)
        unexpectedException.expectMessage("File not found $path")
        Taquin().readFromFile(path)
    }
}