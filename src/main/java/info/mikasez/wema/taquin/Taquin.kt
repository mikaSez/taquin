package info.mikasez.wema.taquin

import info.mikasez.wema.taquin.exceptions.IllegalContentException

class Taquin {

    private val numberDelimiter: String = " "

    /**
     * Takes a path to a file containing lines representing the initial state of a 15 puzzle <br/>
     * Converts it to our array representation
     * @param path : path to the file containing the puzzle representation
     * @return TDArray : representing our initial state
     * */
    fun readFromFile(path: String): TDArray {
        val fileContent = extractFileContent(path)

        val sizes = fileContent.first().split(numberDelimiter)
        val destination = mutableListOf<Int>()

        return fileContent.parseForTdArray(destination, sizes)
    }

    fun List<String>.parseForTdArray(destination: MutableList<Int>, sizes: List<String>): TDArray {
        val tdArray1: TDArray
        try {
            this.drop(1).flatMapTo(destination)
            { line ->
                line.split(numberDelimiter)
                        .map { number -> number.toInt() }
            }
        } catch (e: IllegalArgumentException) {
            throw IllegalContentException("File content illegal characters, only numbers allowed", e)
        }

        val (height, width, product) = sizes.toIntWithProduct()
        when {
            destination.size > product -> throw IllegalContentException("There are too many content for given size parameters")
            destination.size < product -> throw IllegalContentException("14puzzle content should fill the entire array")
            else -> tdArray1 = TDArray(height, width, destination.toTypedArray())
        }
        return tdArray1
    }

    private fun List<String>.toIntWithProduct(): List<Int> {
        val toInt = this.toInt()
        return toInt.plus(toInt.reduce { total, next -> total * next })
    }

    private fun List<String>.toInt() = this.map { it.toInt() }


    private fun extractFileContent(path: String): List<String> {
        val fileContent1: List<String>
        try {
            fileContent1 = path.readFile()
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("File not found $path", e)
        }
        return fileContent1
    }
}


/**
 * threats the given string as filePath and opens it basing on classpath
 * @exception: IllegalArgumentException : Use JDK8 as maximum, will not work in JDK9 out of the box
 * @return file content split by lines
 * */
fun String.readFile() = String::class.java.getResource(this).readText().split("\n")
