/**
* A class representing a calculator that can perform basic arithmetic operations.
*/
class Calculator {


    /**
     * Returns the sum of two integers.
     *
     * @param a the first integer to be added
     * @param b the second integer to be added
     * @return the sum of integers a and b
     */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    /**
     * Subtracts the second integer parameter from the first integer parameter and returns the result
     * @param a The integer to be subtracted from
     * @param b The integer to subtract
     * @return The difference between a and b
     */
    fun minus(a: Int, b: Int): Int {
        return a - b
    }
}