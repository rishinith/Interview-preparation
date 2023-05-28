/**
 * 378. Kth Smallest Element in a Sorted Matrix
Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

You must find a solution with a memory complexity better than O(n2).
 */

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        
        //Time: let X=min(K,N);X+Klog⁡(X)
        //Space: O(X)

        // PriorityQueue<int[]> pq=new PriorityQueue<>(
        //     (a,b)->matrix[a[0]][a[1]]-matrix[b[0]][b[1]]
        //     );

        // int m=matrix.length;
        // int n=matrix[0].length;

        // for(int i=0;i<Math.min(m,k);i++){
        //     pq.add(new int[]{i,0});
        // }

        // int[] element=null;
        // while(k>0){
        //     element=pq.poll();

        //     if(element[1]<n-1){
        //         pq.add(new int[]{element[0], element[1]+1});
        //     }
        //     k--;
        // }

        // return matrix[element[0]][element[1]];

        return kthSmallestBinarySearch(matrix, k);     
    }


    // Time Complexity: O(N×log(Max−Min))
    int kthSmallestBinarySearch(int[][] matrix, int k){
        int start=matrix[0][0];
        int n = matrix.length;
        int end=matrix[n-1][n-1];

        while(start<end){
            int mid=start+(end-start)/2;

            int[] smallLargePair=new int[]{matrix[0][0], matrix[n-1][n-1]};

            int count=countLessEqual(matrix,mid,smallLargePair);

            if(count==k){
                return smallLargePair[0];
            }

            if(count>k){
                end=smallLargePair[0];
            }else{
                start=smallLargePair[1];
            }
        }

        return start;
    }

private int countLessEqual(int[][] matrix, int mid, int[] smallLargePair) {

    int count = 0;
    int n = matrix.length, row = n - 1, col = 0;

    while (row >= 0 && col < n) {

      if (matrix[row][col] > mid) {

        // as matrix[row][col] is bigger than the mid, let's keep track of the
        // smallest number greater than the mid
        smallLargePair[1] = Math.min(smallLargePair[1], matrix[row][col]);
        row--;

      } else {

        // as matrix[row][col] is less than or equal to the mid, let's keep track of the
        // biggest number less than or equal to the mid
        smallLargePair[0] = Math.max(smallLargePair[0], matrix[row][col]);
        count += row + 1;
        col++;
      }
    }

    return count;
  }
}