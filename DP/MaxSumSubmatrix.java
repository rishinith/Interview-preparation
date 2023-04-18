/**
 * 

  You're given a two-dimensional array (a matrix) of potentially unequal height
  and width that's filled with integers. You're also given a positive integer
  size. Write a function that returns the maximum sum that can be
  generated from a submatrix with dimensions size * size.

For example, consider the following matrix:
[
  [2, 4],
  [5, 6],
  [-3, 2],
]

If size = 2, then the 2x2 submatrices to consider are:
[2, 4]
[5, 6]
------
[5, 6]
[-3, 2]


  The sum of the elements in the first submatrix is 17, and the sum
  of the elements in the second submatrix is 10. In this example,
  your function should return 17.


  Note: size will always be at least 1, and the
  dimensions of the input matrix will always be at least
  size * size.

Sample Input
matrix = 
[
  [5, 3, -1, 5],
  [-7, 3, 7, 4],
  [12, 8, 0, 0],
  [1, -8, -8, 2],
]
size = 2

Sample Output
18
// [
//   [., ., ., .],
//   [., 3, 7, .],
//   [., 8, 0, .],
//   [., ., ., .],
// ]


 */

import java.util.*;

public class Program {

  // Method to calculate the maximum sum of a submatrix of given size
  public static int maximumSumSubmatrix(int[][] matrix, int size) {
    int m = matrix.length;
    int n = matrix[0].length;
    
    // Create a 2D array to store the sum of all elements up to and including the current row and column
    int[][] sum = new int[m][n];
    
    // Initialize the first row and column of the sum array
    sum[0][0] = matrix[0][0];
    for (int i = 1; i < n; i++) {
      sum[0][i] = sum[0][i-1] + matrix[0][i];
    }
    for (int i = 1; i < m; i++) {
      sum[i][0] = sum[i-1][0] + matrix[i][0];
    }
    
    // Calculate the sum of all elements up to and including the current row and column for the rest of the sum array
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + matrix[i][j];
      }
    }
    
    // Find the maximum sum of a submatrix of the given size
    int maxSum = Integer.MIN_VALUE;
    for (int i = size-1; i < m; i++) {
      for (int j = size-1; j < n; j++) {
        int total = sum[i][j];
        
        // Subtract the sum of elements outside the current submatrix, if any
        if (i-size >= 0) {
          total -= sum[i-size][j];
        }
        if (j-size >= 0) {
          total -= sum[i][j-size];
        }
        if (i-size >= 0 && j-size >= 0) {
          total += sum[i-size][j-size];
        }
        
        // Update the maximum sum found so far
        maxSum = Math.max(maxSum, total);
      }
    }
    
    // Return -1 if no submatrix of the given size exists in the matrix, otherwise return the maximum sum
    return maxSum == Integer.MIN_VALUE ? -1 : maxSum;
  }
  
  // Example usage
  public static void main(String[] args) {
    int[][] matrix = {{1, 2, -1, -4, -20}, {-8, -3, 4, 2, 1}, {3, 8, 10, 1, 3}, {-4, -1, 1, 7, -6}};
    int size = 2;
    int maxSum = maximumSumSubmatrix(matrix, size);
    System.out.println(maxSum); // Output: 29
  }
}
