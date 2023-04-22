/**
 * Range Sum Query 1D - Immutable
Given an integer array nums, handle multiple queries of the following type:

Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
Implement the NumArray class:

NumArray(int[] nums) Initializes the object with the integer array nums.
int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 */
class NumArray {


    static int[] prefixSum=null;
    public NumArray(int[] nums) {
        if(nums==null || nums.length==0){
            return;
        }
        prefixSum=new int[nums.length];
        prefixSum[0]=nums[0];
        for(int i=1;i<nums.length;i++){
            prefixSum[i]=prefixSum[i-1]+nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        if(prefixSum==null){
            return -1;
        }

        int n1=prefixSum[right];
        int n2=left>0?prefixSum[left-1]:0;

        return n1-n2;
    }
}


/**
 * 304. Range Sum Query 2D - Immutable
Given a 2D matrix matrix, handle multiple queries of the following type:

Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
Implement the NumMatrix class:

NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
You must design an algorithm where sumRegion works on O(1) time complexity.
 */

//Similar like multiple prefix sum array  of 1D rows
class NumMatrix {
    private int[][] dp;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        dp = new int[matrix.length][matrix[0].length + 1];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                dp[r][c + 1] = dp[r][c] + matrix[r][c];
            }
        }
    }

    //T: O(m)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for (int row = row1; row <= row2; row++) {
            sum += dp[row][col2 + 1] - dp[row][col1];
        }
        return sum;
    }
}



//More optimized
class NumMatrix {

    int[][] prefixSumArray;
    public NumMatrix(int[][] matrix) {
        prefixSumArray=new int[matrix.length+1][matrix[0].length+1];

        for(int i=1;i<=matrix.length;i++){
            for(int j=1;j<=matrix[0].length;j++){
                prefixSumArray[i][j]=prefixSumArray[i-1][j]+prefixSumArray[i][j-1]+matrix[i-1][j-1]-prefixSumArray[i-1][j-1];
            }
        }
        
        
    }
    
   // T: O(1)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        row1=row1+1; col1=col1+1; row2=row2+1; col2=col2+1;
        int left=prefixSumArray[row2][col1-1];
        int above=prefixSumArray[row1-1][col2];
        int topLeft=prefixSumArray[row1-1][col1-1];
        int bottomRight=prefixSumArray[row2][col2];

        return bottomRight-left-above+topLeft;

    }
}