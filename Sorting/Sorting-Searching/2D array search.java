/**
 * You are given an m x n integer matrix matrix with the following two properties:

Each row is sorted in non-decreasing order.
The first integer of each row is greater than the last integer of the previous row.
Given an integer target, return true if target is in matrix or false otherwise.

You must write a solution in O(log(m * n)) time complexity.
 */

//Time: log m +log n=log(m*n)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {

        //find the row in which element can exist
        int row=findRow(matrix,target);
        if(row==-1){
            return false;
        }
        return findInRow(matrix[row], target); 
    }

    //simple binary search to find target element in row
    boolean findInRow(int[] arr, int target){
        int start=0;
        int end=arr.length-1;

        while(start<=end){
            int mid=start+(end-start)/2;
            if(arr[mid]==target){
                return true;
            }else if(arr[mid]> target){
                end=mid-1;
            }else{
                start=mid+1;
            }
        }

        return false;
    }

    //find the row in which given target can exist
    //target can lie in a row if   startElement<=target<=lastElement
    int findRow(int[][] matrix, int target){
        int top=0;
        int bottom=matrix.length-1;

        int result=-1;

        while(top<=bottom){

            int mid=top+(bottom-top)/2;

            //startElement>target
            if(matrix[mid][0]> target){
                bottom=mid-1;
            }
            //lastElement<target
            else if(matrix[mid][matrix[0].length-1]<target){
                top=mid+1;
            }else{
                //startElement<=target<=lastElement
                return mid;
            }

        }

        return result;
    }
}


/**
 * 
 * 2nd Approach
 * 
 * Since its given in the question that each row is sorted in non-decreasing order.
The first integer of each row is greater than the last integer of the previous row.

The algorithm is a standard binary search :

Initialise left and right indexes left = 0 and right = m x n - 1.

While left <= right :

Pick up the index in the middle of the virtual array as a pivot index : pivot_idx = (left + right) / 2.

The index corresponds to row = pivot_idx // n and col = pivot_idx % n in the initial matrix, and hence one could get the pivot_element. 
This element splits the virtual array in two parts.

Compare pivot_element and target to identify in which part one has to look for target.
 */

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0)
            return false;
        int n = matrix[0].length;

        // binary search
        int left = 0, right = m * n - 1;
        int pivotIdx, pivotElement;
        while (left <= right) {
            pivotIdx = (left + right) / 2;
            pivotElement = matrix[pivotIdx / n][pivotIdx % n];
            if (target == pivotElement)
                return true;
            else {
                if (target < pivotElement)
                    right = pivotIdx - 1;
                else
                    left = pivotIdx + 1;
            }
        }
        return false;
    }
}