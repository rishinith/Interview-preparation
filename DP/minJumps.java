/**
 * 

  You're given a non-empty array of positive integers where each integer represents the
  maximum number of steps you can take forward in the array. For example, if the
  element at index 1 is 3, you can go from index
  1 to index 2, 3, or 4.


  Write a function that returns the minimum number of jumps needed to reach the
  final index.


  Note that jumping from index i to index i + x always
  constitutes one jump, no matter how large x is.

Sample Input
array = [3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3]

Sample Output
4 // 3 -- (4 or 2) -- (2 or 3) -- 7 -- 3


 */

import java.util.*;

class Program {
  public static int minNumberOfJumps(int[] array) {
    if(array.length<=1){
      return 0;
    }

    int [] minJumps=new int[array.length];
    Arrays.fill(minJumps,Integer.MAX_VALUE);
    minJumps[0]=0;
    for(int i=0;i<array.length-1;i++){
        for(int j=i+1;j<array.length && j<=i+array[i];j++){
            minJumps[j]=Math.min(minJumps[j],minJumps[i]+1);
        }
    }
    return minJumps[array.length-1];
  }
}


import java.util.*;

class Program {
  public static int minNumberOfJumps(int[] nums) {

        int l=0;
        int r=0;
        int ans=0;

        while(r<nums.length-1){
            int farthest=Integer.MIN_VALUE;
            for(int i=l;i<=r;i++){
                farthest=Math.max(farthest,i+nums[i]);
            }
            ans++;
            l=r+1;
            r=farthest;
        }

        if(r>=nums.length-1){
            return ans;
        }

        return -1; 
  }
}
