
/**
 * Given an array of distinct positive integers representing coin denominations and a
single non-negative integer n representing a target amount of
money, write a function that returns the number of ways to make change for
that target amount using the given coin denominations.

Note that an unlimited amount of coins is at your disposal.
Sample Input
n = 6
denoms = [1, 5]

Sample Output
2 // 1x1 + 1x5 and 6x1
 */

import java.util.*;

class Program {
  public static int numberOfWaysToMakeChange(int n, int[] denoms) {

    int[] result=new int[n+1];

    result[0]=1;

    Arrays.sort(denoms);

    // This is not the correct way to implement because we want to include each coin in only way, 
    // For eg: For amount 6: 1,5 and 5,1 are same. But in below loop, it will calculates as two ways
    // for(int amount=1;amount<=n;amount++){
    //   for(int d=0;d<denoms.length;d++){
    //     if(denoms[d]<=amount)
    //       result[amount]+=result[amount-denoms[d]];
    //   }
    // }


    for(int denom:denoms){
      for(int amount=1;amount<=n;amount++){
          if(denom>amount){
            continue;
          }
        result[amount]+=result[amount-denom];
      }
    }

    
    return result[n];
  }
}
