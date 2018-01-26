package info.mikasez.wema.taquin

import java.util.*

class TDArray {
    private val h: Int
    private val w: Int
    private val array: Array<Int?>

    constructor(height: Int, width: Int) {
        this.h = height
        this.w = width
        array = arrayOfNulls(h * w)
    }

    constructor(height: Int, width: Int, values: Array<Int?>) {
        this.h = height
        this.w = width
        this.array = values.copyOf()
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

    /**
     * Get a value from position on the right of the specified one
     * @param px : specified horizontal position
     * @param py : specified vertical position
     * @return value : value situated one case right or null
     * */
    fun getRight(px: Int, py: Int): Int? {
        return if (px < (w - 1))
            get(px + 1, py)
        else
            null
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

    override fun toString(): String {
        return Arrays.toString(array)
    }

}