/**
 * Before going for Binary Indexed tree to perform operations over range, one must confirm that the operation or the function is:
F(a,F(b,c))=F(F(a,b),c))
Associative. i.e 
 this is true even for seg-tree

Has an inverse. eg:

Addition has inverse subtraction (this example we have discussed)
Multiplication has inverse division
gcd() has no inverse, so we can’t use BIT to calculate range gcd’s
Sum of matrices has inverse
Product of matrices would have inverse if it is given that matrices are degenerate i.e. determinant of any matrix is not equal to 0
 */

/**
 * 1D array Mutable
 * Given an integer array nums, handle multiple queries of the following types:
Update the value of an element in nums.
Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
Implement the NumArray class:

NumArray(int[] nums) Initializes the object with the integer array nums.
void update(int index, int val) Updates the value of nums[index] to be val.
int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 
 */
/**
 * Update (1D) - O(log⁡N)
Query (1D) - O(log⁡N)
Build (1D) - O(NlogN)
 */
class NumArrayMutable {

    int bit[];
    int size;
    int nums[];

    public NumArray(int[] nums) {
        this.nums=nums;
        size=nums.length;
        bit=new int[size+1];
        //bit construction: nlogn
        for(int i=0;i<size;i++){
            init(i, nums[i]);
        } 
    }

    void init(int i, int num){
        i++;
        while(i<=size){
            bit[i]+=num;
            //adding the last lsb to the same no to get new index to update
            i=i+(i&-i);
        }
    }
    
    public void update(int index, int val) {
        //get the new value to be added in bit index
        int diff=val-nums[index];
        nums[index]=val;
        init(index,diff);
    }
    
    public int sumRange(int left, int right) {
       return getSum(right)-getSum(left-1);
        
    }

    public int getSum(int i){
        i++;
        int sum=0;
        while(i>0){
            sum+=bit[i];
            i=i-(i&-i);
        }
        return sum;
    }
}

/**
 * 308. Range Sum Query 2D - Mutable
Given a 2D matrix matrix, handle multiple queries of the following types:
Update the value of a cell in matrix.
Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
Implement the NumMatrix class:

NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
void update(int row, int col, int val) Updates the value of matrix[row][col] to be val.
int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by 
its upper left corner (row1, col1) and lower right corner (row2, col2).
 */


/**
Update (2D) - O(log⁡N⋅log⁡M)
Query (2D) - O(log⁡N⋅log⁡M)
Build (2D) - O(N⋅M⋅log⁡N⋅log⁡M)
Here N denotes the number of rows and M denotes the number of columns.
 */
class NumMatrix {
    private int rows;
    private int cols;
    private int[][] bit; // The BIT matrix

    private int lsb(int n) {
        // the line below allows us to directly capture the right most non-zero bit of a number
        return n & (-n);
    }

    private void updateBIT(int r, int c, int val) {
        // keep adding lsb(i) to i, lsb(j) to j and add val to bit[i][j]
        // Using two nested for loops, one for the rows and one for the columns
        for (int i = r; i <= rows; i += lsb(i)) {
            for (int j = c; j <= cols; j += lsb(j)) {
                this.bit[i][j] += val;
            }
        }
    }

    private int queryBIT(int r, int c) {
        int sum = 0;
        // keep subtracting lsb(i) to i, lsb(j) to j and obtain the final sum as the sum of non-overlapping sub-rectangles
        // Using two nested for loops, one for the rows and one for the columns
        for (int i = r; i > 0; i -= lsb(i)) {
            for (int j = c; j > 0; j -= lsb(j)) {
                sum += this.bit[i][j];
            }
        }
        return sum;
    }

    private void buildBIT(int[][] matrix) {
        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <= cols; ++j) {
                // call update function on each of the entries present in the matrix
                int val = matrix[i - 1][j - 1];
                updateBIT(i, j, val);
            }
        }
    }

    public NumMatrix(int[][] matrix) {
        rows = matrix.length;
        if (rows == 0) return;
        cols = matrix[0].length;
        bit=new int[rows+1][cols+1];
        buildBIT(matrix);
    }

    public void update(int row, int col, int val) {
        int old_val = sumRegion(row, col, row, col);
        // handling 1-based indexing
        row++; col++;
        int diff = val - old_val;
        updateBIT(row, col, diff);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // handling 1-based indexing
        row1++; col1++; row2++; col2++;
        int a = queryBIT(row2, col2);
        int b = queryBIT(row1 - 1, col1 - 1);
        int c = queryBIT(row2, col1 - 1);
        int d = queryBIT(row1 - 1, col2);
        //similar to immutable matrix prefix sum array implmentation
        return (a + b) - (c + d);
    }
};