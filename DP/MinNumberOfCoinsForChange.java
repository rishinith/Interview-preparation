/**
 * 

  Given an array of positive integers representing coin denominations and a
  single non-negative integer n representing a target amount of
  money, write a function that returns the smallest number of coins needed to
  make change for (to sum up to) that target amount using the given coin
  denominations.


  Note that you have access to an unlimited amount of coins. In other words, if
  the denominations are [1, 5, 10], you have access to an unlimited
  amount of 1s, 5s, and 10s.


  If it's impossible to make change for the target amount, return
  -1.

Sample Input
n = 7
denoms = [1, 5, 10]

Sample Output
3 // 2x1 + 1x5


 */


import java.util.*;

class Program {
  public static int minNumberOfCoinsForChange(int n, int[] denoms) {

    int[] result=new int[n+1];
    for(int i=0;i<result.length;i++)
      result[i]=Integer.MAX_VALUE;
    result[0]=0;
    for(int denom:denoms){
      for(int amount=1;amount<=n;amount++){
        if(denom<=amount){
            if(result[amount-denom]!=Integer.MAX_VALUE){
            result[amount]=Math.min(result[amount],result[amount-denom]+1);
            }

        }
        }
        
      }
    

    return result[n]==Integer.MAX_VALUE?-1:result[n];
  }
}
