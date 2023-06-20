/**
 * Given a rod of length ‘N’ units. The rod can be cut into different sizes and each size has a cost associated with it. 
 * Determine the maximum cost obtained by cutting the rod and selling its pieces.

Note:

1. The sizes will range from 1 to ‘N’ and will be integers.
2. The sum of the pieces cut should be equal to ‘N’.
3. Consider 1-based indexing.

Example:
length: 5
prices: [2 5 7 8 10]

Test case 1:

All possible partitions are:
1,1,1,1,1           max_cost=(2+2+2+2+2)=10
1,1,1,2             max_cost=(2+2+2+5)=11
1,1,3               max_cost=(2+2+7)=11
1,4                 max_cost=(2+8)=10
5                   max_cost=(10)=10
2,3                 max_cost=(5+7)=12
1,2,2               max _cost=(1+5+5)=12    

Clearly, if we cut the rod into lengths 1,2,2, or 2,3, we get the maximum cost which is 12.

 */

/**
 * Solution:
 * We can say that this problem can be solved by the same technique we used in solving the Unbounded Knapsack
The rod pieces are equivalent to the items and the rod length is equivalent to the knapsack capacity. 
From where we will discuss the unbounded knapsack problem with the required modifications.
 */

import java.util.*;

class TUF{
static int cutRodUtil(int[] price, int ind, int N,int[][] dp){

    if(ind == 0){
        return N*price[0];
    }
    
    if(dp[ind][N]!=-1)
        return dp[ind][N];
        
    int notTaken = 0 + cutRodUtil(price,ind-1,N,dp);
    
    int taken = Integer.MIN_VALUE;
    int rodLength = ind+1;
    if(rodLength <= N)
        taken = price[ind] + cutRodUtil(price,ind,N-rodLength,dp);
        
    return dp[ind][N] = Math.max(notTaken,taken);
}


static int cutRod(int[] price,int N) {

    
    int dp[][]=new int[N][N+1];
    for(int row[]:dp)
    Arrays.fill(row,-1);
    return cutRodUtil(price,N-1,N,dp);
}

}


//Tabulation
static int cutRod(int[] price,int N) {

    int dp[][]=new int[N][N+1];
    
    for(int row[]:dp)
    Arrays.fill(row,-1);
    
    for(int i=0; i<=N; i++){
        dp[0][i] = i*price[0];
    }
    
    for(int ind=1; ind<N; ind++){
        for(int length =0; length<=N; length++){
        
             int notTaken = 0 + dp[ind-1][length];
    
             int taken = Integer.MIN_VALUE;
             int rodLength = ind+1;
             if(rodLength <= length)
                taken = price[ind] + dp[ind][length-rodLength];
        
             dp[ind][length] = Math.max(notTaken,taken);   
        }
    }
    
    return dp[N-1][N];
}


//Space optimized Tabulation:

static int cutRod(int[] price,int N) {

    int cur[]=new int[N+1];
    
    for(int i=0; i<=N; i++){
        cur[i] = i*price[0];
    }
    
    for(int ind=1; ind<N; ind++){
        for(int length =0; length<=N; length++){
        
             int notTaken = 0 + cur[length];
    
             int taken = Integer.MIN_VALUE;
             int rodLength = ind+1;
             if(rodLength <= length)
                taken = price[ind] + cur[length-rodLength];
        
             cur[length] = Math.max(notTaken,taken);   
        }
    }
    
    return cur[N];
}