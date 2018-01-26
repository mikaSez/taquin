package info.mikasez.wema.taquin

class Taquin {

    /**
     * Takes a path to a file containing lines representing the initial state of a 15 puzzle <br/>
     * Converts it to our array representation
     * @param path : path to the file containing the puzzle representation
     * @return TDArray : representing our initial state
     * */
    fun readFromFile(path: String): TDArray {
        var fileContent: List<String>
        try {
            fileContent = path.readFile()
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("File not found $path", e)
        }
        val sizes = fileContent.first().split(" ")
        val destination = mutableListOf<Int>()
        fileContent.drop(1).flatMapTo(destination)
        { line ->
            line.split(" ")
                    .map { number -> number.toInt() }
        }
        return TDArray(sizes[0].toInt(), sizes[1].toInt(), destination.toTypedArray())
    }
}


/**
 * threats the given string as filePath and opens it basing on classpath
 * @exception: IllegalArgumentException : Use JDK8 as maximum, will not work in JDK9 out of the box
 * @return file content split by lines
 * */
fun String.readFile() = String::class.java.getResource(this).readText().split("\n")
