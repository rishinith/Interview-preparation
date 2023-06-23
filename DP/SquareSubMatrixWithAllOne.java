/**
 * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.

Input: matrix =
[
  [0,1,1,1],
  [1,1,1,1],
  [0,1,1,1]
]
Output: 15
Explanation: 
There are 10 squares of side 1.
There are 4 squares of side 2.
There is  1 square of side 3.
Total number of squares = 10 + 4 + 1 = 15.
 */


class Solution {
    public int countSquares(int[][] matrix) {
        int m=matrix.length;
        int n=matrix[0].length;
        int[][] dp=new int[m][n];

        int total=0;
        for(int i=0;i<m;i++){
            dp[i][0]=matrix[i][0];
            total+=matrix[i][0];
        }
        for(int i=1;i<n;i++){
            dp[0][i]=matrix[0][i];
            total+=matrix[0][i];
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){

                if(matrix[i][j]==1){
                    //bottom right corner of the square, store no of squares possible at this point
                    dp[i][j]=Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j-1]))+1;
                    total+=dp[i][j];
                }else{
                    dp[i][j]=0;
                }
            }
        }

        return total;
    }


    int withoutAnyAucillarySpace(int[][] matrix){
        int m=matrix.length;
        int n=matrix[0].length;

        int total=0;
    

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){

                if(matrix[i][j]>=1 && i>0 && j>0){
                    matrix[i][j]=Math.min(matrix[i-1][j], Math.min(matrix[i-1][j-1], matrix[i][j-1]))+1;
                }
                total+=matrix[i][j];
            }
        }

        return total;
    }
}