/**
 * 

  Write a function that takes in an array of integers and returns the largest
  possible value for the expression
  array[a] - array[b] + array[c] - array[d], where a,
  b, c, and d are indices of the array
  and a  b  c  d.


  If the input array has fewer than 4 elements, your function
  should return 0.

Sample Input
array = [3, 6, 1, -3, 2, 7]

Sample Output
4
// Choose a = 1, b = 3, c = 4, and d = 5
// - 6 - (-3) + 2 - 7 = 4


 */

import java.util.*;

class Program {

  public int maximizeExpression(int[] arr) {
    int n = arr.length;
    if (n < 4) {
        return 0;
    }

    //find max 'a'
    int[] maxOfA=new int[arr.length];
    maxOfA[0]=arr[0];
    for(int i=1;i<n;i++){
      maxOfA[i]=Math.max(maxOfA[i-1], arr[i]);
    }

    //find max a-b where a<b;
    int[] maxOfAMinusB=new int[arr.length];
    maxOfAMinusB[0]=Integer.MIN_VALUE;
    for(int i=1;i<n;i++){
      maxOfAMinusB[i]=Math.max(maxOfAMinusB[i-1],maxOfA[i-1]-arr[i]);
    }

    //find max a-b +c where a<b<c;
    int[] maxOfAMinusBPlusC=new int[arr.length];
    maxOfAMinusBPlusC[0]=Integer.MIN_VALUE;
    maxOfAMinusBPlusC[1]=Integer.MIN_VALUE;
    for(int i=2;i<n;i++){
      maxOfAMinusBPlusC[i]=Math.max(maxOfAMinusBPlusC[i-1],maxOfAMinusB[i-1]+arr[i]);
    }

    //find max a-b +c-d where a<b<c<d;
    int[] maxOfAMinusBPlusCMinusD=new int[arr.length];
    maxOfAMinusBPlusCMinusD[0]=Integer.MIN_VALUE;
    maxOfAMinusBPlusCMinusD[1]=Integer.MIN_VALUE;
    maxOfAMinusBPlusCMinusD[2]=Integer.MIN_VALUE;
    for(int i=3;i<n;i++){
      maxOfAMinusBPlusCMinusD[i]=Math.max(maxOfAMinusBPlusCMinusD[i-1],maxOfAMinusBPlusC[i-1]-arr[i]);
    }

    return maxOfAMinusBPlusCMinusD[arr.length-1];
   
  }
}
