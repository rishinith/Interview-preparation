/**
 * 
  You're given an array of positive integers representing the prices of a single stock on
  various days (each index in the array represents a different day). You're also
  given an integer k, which represents the number of transactions
  you're allowed to make. One transaction consists of buying the stock on a
  given day and selling it on another, later day.


  Write a function that returns the maximum profit that you can make by buying
  and selling the stock, given k transactions.


  Note that you can only hold one share of the stock at a time; in other words,
  you can't buy more than one share of the stock on any given day, and you can't
  buy a share of the stock if you're still holding another share. Also, you
  don't need to use all k transactions that you're allowed.

Sample Input
prices = [5, 11, 3, 50, 60, 90]
k = 2

Sample Output
93 // Buy: 5, Sell: 11; Buy: 3, Sell: 90

 */

import java.util.*;

//T: n*n*k  S=n*k
class Program {
  public static int maxProfitWithKTransactions(int[] prices, int k) {

    if(prices.length==0){
      return 0;
    }

    //2D array to save max profit for 1..k transactions
    int[][] profitMatrix=new int[k+1][prices.length];

    //for 0 transaction, profit will be always zero
    for(int i=0;i<prices.length;i++){
      profitMatrix[0][i]=0;
    }

    for(int t=1;t<=k;t++){
      for(int d=1;d<prices.length;d++){
        /**
          profitMatrix[t][d]=
          Max(
              maxProfit[t][d-1] --max profit upto last day,
              prices[d] + max(-prices[x]+maxProfit[t-1][x]) where 0<=x<d, i.e in t-1 transaction when the stock was sold  
              )
          **/

          int maxProfitIfStockIsSoldToday=Integer.MIN_VALUE;
          for(int x=0;x<d;x++){
              maxProfitIfStockIsSoldToday=Math.max(maxProfitIfStockIsSoldToday,+prices[d]-prices[x]+profitMatrix[t-1][x]);
          }
        profitMatrix[t][d]=Math.max(profitMatrix[t][d-1],maxProfitIfStockIsSoldToday);
      }
    }

    
    return profitMatrix[k][prices.length-1];
  }
}



//Time optimized: T: n*k  S=n*k

import java.util.*;

class Program {
  public static int maxProfitWithKTransactions(int[] prices, int k) {

    if(prices.length==0){
      return 0;
    }

    //2D array to save max profit for 1..k transactions
    int[][] profitMatrix=new int[k+1][prices.length];

    //for 0 transaction, profit will be always zero
    for(int i=0;i<prices.length;i++){
      profitMatrix[0][i]=0;
    }

    for(int t=1;t<=k;t++){

      int maxSoFar=Integer.MIN_VALUE;
      for(int d=1;d<prices.length;d++){
        /**
          profitMatrix[t][d]=
          Max(
              maxProfit[t][d-1] --max profit upto last day,
              prices[d] + max(-prices[x]+maxProfit[t-1][x]) where 0<=x<d, i.e in t-1 transaction when the stock was sold  
              )
          **/

        maxSoFar=Math.max(maxSoFar, -prices[d-1]+profitMatrix[t-1][d-1]);
        
        profitMatrix[t][d]=Math.max(profitMatrix[t][d-1],maxSoFar+prices[d]);
      }
    }

    
    return profitMatrix[k][prices.length-1];
  }
}



//Space optimized: T: n*k  S=n

import java.util.*;

class Program {
  public static int maxProfitWithKTransactions(int[] prices, int k) {

    if(prices.length==0){
      return 0;
    }

    //2D array to save max profit for 1..k transactions
    int[] lastProfit=new int[prices.length];

    //for 0 transaction, profit will be always zero
    for(int i=0;i<prices.length;i++){
      lastProfit[i]=0;
    }
    
    for(int t=1;t<=k;t++){

      int[] currentProfit=new int[prices.length];
      
      int maxSoFar=Integer.MIN_VALUE;
      for(int d=1;d<prices.length;d++){
        /**
          profitMatrix[t][d]=
          Max(
              maxProfit[t][d-1] --max profit upto last day,
              prices[d] + max(-prices[x]+maxProfit[t-1][x]) where 0<=x<d, i.e in t-1 transaction when the stock was sold  
              )
          **/

        maxSoFar=Math.max(maxSoFar, -prices[d-1]+lastProfit[d-1]);
        
        currentProfit[d]=Math.max(currentProfit[d-1],maxSoFar+prices[d]);
      }
      lastProfit=currentProfit;
    }

    
    return lastProfit[prices.length-1];
  }
}
