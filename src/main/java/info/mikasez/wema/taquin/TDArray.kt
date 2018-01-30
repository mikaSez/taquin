package info.mikasez.wema.taquin

import java.util.*

class TDArray {
    private val h: Int
    private val w: Int
    private val array: Array<Int?>
    private val zeroPosition: Pair<Int, Int>

    var previous: TDArray? = null


    constructor(height: Int, width: Int) {
        this.h = height
        this.w = width
        array = arrayOfNulls(h * w)
        zeroPosition = Pair(-1, -1)
    }

    constructor(height: Int, width: Int, values: Array<Int?>) {
        this.h = height
        this.w = width
        this.array = values.copyOf()
        zeroPosition = locateZero()
    }

    constructor(height: Int, width: Int, values: Array<Int?>, previous: TDArray) {
        this.h = height
        this.w = width
        this.array = values.copyOf()
        zeroPosition = locateZero()
        this.previous = previous
    }

    private fun locateZero(): Pair<Int, Int> {
        for (x in 0 until w) {
            for (y in 0 until h) {
                if (get(x, y) == 0) {
                    return Pair(x, y)
                }
            }
        }
        return Pair(-1, -1)
    }


    /**
     * Put the specified value on the specified position (X/Y)
     * @param px:  specified horizontal position
     * @param py: specified vertical position
     * @param value : integer value to put at this place
     * */
    fun put(px: Int, py: Int, value: Int) {
        this.array[(py * w) + px] = value
    }

    /**
     * Get a value from the specified position (X/Y)
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : the value at specified positions
     * */
    fun get(px: Int, py: Int) = this.array[(py * w) + px]

    /**
     * Get a value from position above the specified one
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : value situated one case up or null
     * */
    fun getUp(px: Int, py: Int): Int? {
        return if (py > 0)
            get(px, py - 1)
        else
            null
    }

    private fun getUp(): TDArray? {
        val result = getUp(zeroPosition.first, zeroPosition.second)
        return when (result) {
            null -> null
            else -> TDArray(h, w, array.swap(0, result), this)
        }
    }

    /**
     * Get a value from position below the specified one
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : value situated one case down or null
     * */
    fun getDown(px: Int, py: Int): Int? {
        return if (py < (h - 1))
            get(px, py + 1)
        else
            null
    }

    private fun getDown(): TDArray? {
        val result = getDown(zeroPosition.first, zeroPosition.second)
        return when (result) {
            null -> null
            else -> TDArray(h, w, array.swap(0, result), this)
        }
    }

    /**
     * Get a value from position on the left of the specified one
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : value situated one case left or null
     * */
    fun getLeft(px: Int, py: Int): Int? {
        return if (px > 0)
            get(px - 1, py)
        else
            null
    }

    private fun getLeft(): TDArray? {
        val result = getLeft(zeroPosition.first, zeroPosition.second)
        return when (result) {
            null -> null
            else -> TDArray(h, w, array.swap(0, result), this)
        }
    }

    /**
     * Get a value from position on the right of the specified one
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : value situated one case right or null
     * */
    fun getRight(px: Int, py: Int): Int?
            = if (px < (w - 1))
        get(px + 1, py)
    else
        null


    private fun getRight(): TDArray? {
        val result = getRight(zeroPosition.first, zeroPosition.second)
        return when (result) {
            null -> null
            else -> TDArray(h, w, array.swap(0, result), this)
        }
    }

    /**
     * Should return all swaps available of the number zero from the given position <br/>
     * A swap can be done from the adjacent element <br/>
     * Adjacent elements cannot be taken from diagonals
     * @return : List<TDArray> : Arrays formed by non null result
     * */
    fun getExistingSwaps(): List<TDArray>
            = listOf(getRight(), getLeft(), getUp(), getDown()).filterNotNull()

    private fun Array<Int?>.swap(value1: Int, value2: Int): Array<Int?> {
        val swapped = this.copyOf()
        val index1 = this.indexOf(value1)
        val index2 = this.indexOf(value2)
        val tmp = this[index1]
        swapped[index1] = this[index2]
        swapped[index2] = tmp
        return swapped
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TDArray
        if (h != other.h) return false
        if (w != other.w) return false
        if (!Arrays.equals(array, other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = h
        result = 31 * result + w
        result = 31 * result + Arrays.hashCode(array)
        return result
    }

    override fun toString(): String
            = Arrays.toString(array)

    /**
     * Check if the current TDArray has the same size and the same order as the passed one<br/>
     * @see Arrays.equals
     * @param completedState: the state to compare current array to, usually it should be the last step
     * @result : true if the current state is equals to the given state
     * */
    fun inSameOrder(completedState: TDArray): Boolean
            = Arrays.equals(array, completedState.array)


    /**
     * Create a final state for the given TDArray
     * @sample <pre>
     *          2 2     2 2
     *          1 3  -> 1 2
     *          0 2     3 0
     *         </pre>
     * @return TDArray: representing the state considered as complete
     * */
    fun deduceFinalState(): TDArray {
        val finalState = array.copyOf()
        for (i in 1 until array.size) {
            finalState[i - 1] = i
        }
        finalState[finalState.lastIndex] = 0
        return TDArray(h, w, finalState)
    }

    /**
     * Should return a 2D representation of the given TDArray
     *
     * */
    fun prettyFormat(): String {
        val sb = StringBuilder()
        sb.append("$h $w")
        sb.append("\n")
        for (y in 0 until h) {
            for (x in 0 until w) {
                sb.append(this.get(x, y))
                sb.append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }


}