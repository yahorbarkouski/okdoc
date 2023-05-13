class BinarySearch {

    /**
     * Searches a sorted array for a specified key using binary search algorithm.
     * @param array the sorted array to search
     * @param key the key to search for
     * @param start the starting index of the search range
     * @param end the ending index of the search range
     * @return the index of the key in the array if found, else -1
     * @throws IllegalArgumentException if the array is null or empty
     */
    public <T extends Comparable<T>> int find(T[] array, T key) {
        return search(array, key, 0, array.length - 1);
    }

    /**
     * Performs a binary search on an array to find the index of a given key.
     * @param array The array to search through. Must be sorted in ascending order.
     * @param key The key to search for.
     * @param left The left index of the search range.
     * @param right The right index of the search range.
     * @param <T> The type of elements in the array, which must implement Comparable.
     * @return The index of the key in the array, or -1 if the key is not found.
     */
    private <T extends Comparable<T>> int search(
            T[] array,
            T key,
            int left,
            int right
    ) {
        if (right < left) {
            return -1; // this means that the key not found
        }
        // find median
        int median = (left + right) >>> 1;
        int comp = key.compareTo(array[median]);

        if (comp == 0) {
            return median;
        } else if (comp < 0) {
            return search(array, key, left, median - 1);
        } else {
            return search(array, key, median + 1, right);
        }
    }
}