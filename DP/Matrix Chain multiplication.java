/**
 * Given a chain of matrices A1, A2, A3,.....An. Your task is to find out the minimum cost to multiply these matrices. 
 * The cost of matrix multiplication is defined as the number of scalar multiplications. 
 * A Chain of matrices A1, A2, A3,.....An is represented by a sequence of numbers in an array ‘arr’ 
 * where the dimension of 1st matrix is equal to arr[0] * arr[1] , 2nd matrix is arr[1] * arr[2], and so on.

For example:

For arr[ ] = { 10, 20, 30, 40}, matrix A1 = [10 * 20], A2 = [20 * 30], A3 = [30 * 40]

Scalar multiplication of matrix with dimension 10 * 20 is equal to 200.

Cost of multiplication of A1 and A2=10*20*30 (i.e no of operations)

The problem is not actually to perform the multiplications, but merely to decide in which order to perform the multiplications.
Note:no matter how we parenthesize the product, the result will be the same. For example, if we had four matrices A, B, C, and D, we would have:

(ABC)D = (AB)(CD) = A(BCD) = ....
However, the order in which we parenthesize the product affects the number of simple arithmetic operations needed to compute the product, or the efficiency.
 For example, suppose A is a 10 × 30 matrix, B is a 30 × 5 matrix, and C is a 5 × 60 matrix. Then,

(AB)C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 operations
A(BC) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 operations.
Clearly the first parenthesization requires less number of operations.
Note ; We'll be given an array arr[ ] which represents the chain of matrices such that the ith matrix arr[i] is of dimension arr[i-1] x arr[i].
That's why we start out 'k' i.e partition from 'i' =1 so that arr[ 1] is of dimentions arr[1-1] * arr[1] 
else we'll get index out of bound error Eg arr[0-1] * arr[0] is not possible
So first half of the array is from i to k & other half is from k+1 to j
Also we need to find the cost of multiplication of these 2 resultant matrixes (first half & second half) which is nothing but arr[i-1] * arr[k] * arr[j]

 */

//T: N^3
public class Solution {
	public static int matrixMultiplication(int[] arr , int N) {
		Integer[][] dp=new Integer[N][N];
		return matrixMultiplication(arr, 1, N-1, dp);
	}

	static int matrixMultiplication(int[] arr, int i, int j, Integer[][] dp){

		if(i==j){
			return 0;
		}

		if(dp[i][j]!=null){
			return dp[i][j];
		}
		int minSteps=1_000_000_000;

		for(int k=i;k<=j-1;k++){
			int steps=arr[i-1]*arr[k]*arr[j]
			+ matrixMultiplication(arr, i, k, dp)+ matrixMultiplication(arr, k+1, j, dp);
			minSteps=Math.min(minSteps, steps);
		}

		return dp[i][j]=minSteps;

	}
	
}
