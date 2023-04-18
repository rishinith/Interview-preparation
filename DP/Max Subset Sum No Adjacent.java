

/**
 * Write a function that takes in an array of positive integers and returns the
maximum sum of non-adjacent elements in the array.

If the input array is empty, the function should return 0.
Sample Input
array = [75, 105, 120, 75, 90, 135]

Sample Output
330 // 75 + 120 + 135
 */

import java.util.*;

class Program {
  public static int maxSubsetSumNoAdjacent(int[] array) {

    if(array.length==0){
      return 0;
    }else if(array.length==1){
      return array[0];
    }

   int first=array[0];
    int second=Math.max(first,array[1]);
    int current=second;
    for(int i=2;i<array.length;i++){
      current=Math.max(first+array[i], second);
      first=second;
      second=current;
    }
    
    return current;
  }
}
