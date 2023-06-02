/**
 * You are given a 0-indexed m x n integer matrix grid consisting of distinct integers from 0 to m * n - 1. 
 * You can move in this matrix from a cell to any other cell in the next row. 
 * That is, if you are in cell (x, y) such that x < m - 1, you can move to any of the cells (x + 1, 0), (x + 1, 1), ..., (x + 1, n - 1). 
 * Note that it is not possible to move from cells in the last row.

Each possible move has a cost given by a 0-indexed 2D array moveCost of size (m * n) x n, 
where moveCost[i][j] is the cost of moving from a cell with value i to a cell in column j of the next row. The cost of moving from cells in the last row of grid can be ignored.

The cost of a path in grid is the sum of all values of cells visited plus the sum of costs of all the moves made.
 Return the minimum cost of a path that starts from any cell in the first row and ends at any cell in the last row.
 */

class Solution {
    public int minPathCost(int[][] grid, int[][] moveCost) {

        int m=grid.length;
        int n=grid[0].length;

        int prevDP[]=new int[n];

        int result=Integer.MAX_VALUE;

        for(int i=0;i<m;i++){

            int currentDP[]=new int[n];
            Arrays.fill(currentDP, Integer.MAX_VALUE);
            for(int j=0;j<n;j++){

                if(i==0){
                    currentDP[j]=grid[i][j];
                }
                else{

                    for(int k=0;k<n;k++){

                        int fromCellValue=grid[i-1][k];
                        int movingCost=moveCost[fromCellValue][j];
                        currentDP[j]=Math.min(currentDP[j], movingCost+ grid[i][j]+ prevDP[k]);
                    }
                }

                if(i==m-1){
                    result=Math.min(result,currentDP[j]);
                }

            }
            prevDP=currentDP;
        }

        return result;
        
    }
}