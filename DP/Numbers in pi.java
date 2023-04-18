/**
 * 

  Given a string representation of the first n digits of Pi and a list of
  positive integers (all in string format), write a function that returns the
  smallest number of spaces that can be added to the n digits of Pi such that
  all resulting numbers are found in the list of integers.


  Note that a single number can appear multiple times in the resulting numbers.
  For example, if Pi is "3141" and the numbers are
  ["1", "3", "4"], the number "1" is allowed to appear
  twice in the list of resulting numbers after three spaces are added:
  "3 | 1 | 4 | 1".


  If no number of spaces to be added exists such that all resulting numbers are
  found in the list of integers, the function should return
  -1.

Sample Input
pi = "3141592653589793238462643383279",
numbers = ["314159265358979323846", "26433", "8", "3279", "314159265", "35897932384626433832", "79"]

Sample Output
2 // "314159265 | 35897932384626433832 | 79"


 */

import java.util.*;

class Program {

    public static int numbersInPi(String pi, String[] numbers) {
        // Creating a set of numbers from the array of numbers
        Set < String > numberSet = new HashSet < > ();
        for (String num: numbers) {
            numberSet.add(num);
        }

        // Calling the helper method to find the minimum number of spaces to be added to pi to make it composed of the given numbers
        var result = getMinSpaces(pi, numberSet, new HashMap < Integer, Integer > (), 0);

        // If result is equal to the maximum value of int, that means no such combination of numbers exist in pi, hence returning -1
        if (result == Integer.MAX_VALUE) {
            return -1;
        }

        // Otherwise returning the minimum number of spaces required to make pi composed of given numbers
        return result;
    }

    // A helper method that takes pi, a set of numbers, a map to store the minimum spaces required for each index of pi, and the current index of pi being considered
    static int getMinSpaces(String pi, Set < String > numberSet, Map < Integer, Integer > cache, int index) {
        // If the current index is equal to the length of pi, that means we have found a valid combination of numbers, hence returning -1
        if (index == pi.length()) {
            return -1;
        }

        // If we have already processed the current index and stored the minimum spaces required in cache, then return the value from cache
        if (cache.containsKey(index)) {
            return cache.get(index);
        }

        // Setting an initial value for the minimum spaces required to be the maximum value of int
        int minSpaces = Integer.MAX_VALUE;

        // Looping through the characters of pi starting from the current index
        for (int i = index; i < pi.length(); i++) {
            // Extracting the prefix of pi from the current index to the current character (i)
            String prefix = pi.substring(index, i + 1);

            // If the prefix is not present in the set of numbers, skip to the next iteration
            if (!numberSet.contains(prefix)) {
                continue;
            }

            // Recursively calling the method with the next index to get the minimum spaces required in the suffix
            int minSpacesInSuffix = getMinSpaces(pi, numberSet, cache, i + 1);

            // If the minimum spaces required in the suffix is not the maximum value of int, then update the minimum spaces required to be 
            // the minimum of the current minimum spaces required and the minimum spaces required in the suffix plus 1 (as we are adding a space after the current prefix)
            //This is important to check for integer overflow because of +1
            if (minSpacesInSuffix != Integer.MAX_VALUE) {
                minSpaces = Math.min(minSpaces, 1 + minSpacesInSuffix);
            }

        }

        // Storing the minimum spaces required for the current index in the cache
        cache.put(index, minSpaces);

        // Returning the minimum spaces required for the current index
        return minSpaces;
    }
}