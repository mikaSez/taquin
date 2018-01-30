package info.mikasez.wema.taquin

import info.mikasez.wema.taquin.exceptions.IllegalContentException
import java.util.*

fun main(args: Array<String>) {
    Taquin().run("/twoPermutations")
}

class Taquin {

    private val numberDelimiter: String = " "

    private lateinit var completedState: TDArray
    private val exploredStates: Set<TDArray> = mutableSetOf()
    private val statesToExplore: Stack<TDArray> = Stack()

    /**
     * Takes a file name and executes a given resolution program <br/>
     * In the end prints into a console the result in a pretty format
     * @param fileName : the name of the file containing initial state
     * */
    fun run(fileName: String) {
        val initialState = readFromFile(fileName)
        completedState = initialState.deduceFinalState()
        exploredStates + completedState

        val endState = naiveSearchAlgorithm(initialState)
        printResultingChainOfStates(endState)
    }

    /**
     * DFS
     * */
    private fun naiveSearchAlgorithm(initialState: TDArray): TDArray {
        var nextState = initialState
        mainLoop@ while (!isComplete(nextState)) {
            val existingSwaps = nextState.getExistingSwaps()
            for (state in existingSwaps) {
                if (isComplete(state)) {
                    nextState = state
                    break@mainLoop
                }
            }
            statesToExplore.addAll(existingSwaps.filterNot { tdArray -> exploredStates.contains(tdArray) })
            nextState = statesToExplore.pop()
        }
        return nextState
    }

    private fun printResultingChainOfStates(nextState: TDArray) {
        var nextState1 = nextState
        var result: List<TDArray> = mutableListOf()
        result += nextState1
        while (nextState1.previous != null) {
            nextState1 = nextState1.previous as TDArray
            result += nextState1
        }

        val string = result.asReversed()
                .joinToString("\n") { tdArray -> tdArray.prettyFormat() }

        println(string)
    }

    /**
     * Takes an array and check against the final one <br/>
     * @pre completedState : should be initialized
     * @param state : the TDArray to be checked
     * */
    fun isComplete(state: TDArray): Boolean {
        return state.inSameOrder(completedState)
    }

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

    fun List<String>.parseForTdArray(destination: MutableList<Int>,
                                     sizes: List<String>): TDArray {
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
