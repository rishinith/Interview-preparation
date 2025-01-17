/**
 * Alice and Bob continue their games with piles of stones.  
 * There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i]. 
 *  The objective of the game is to end with the most stones. 

Alice and Bob take turns, with Alice starting first.  Initially, M = 1.

On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.  Then, we set M = max(M, X).

The game continues until all the stones have been taken.

Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.

Input: piles = [2,7,9,4,4]
Output: 10
Explanation:  If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. Alice can get 2 + 4 + 4 = 10 piles in total.
 If Alice takes two piles at the beginning, then Bob can take all three piles left. In this case, Alice get 2 + 7 = 9 piles in total. So we return 10 since it's larger. 
 */

//Time complexity: O(n^3)
//Space: O(n^2)
class Solution {
    public int stoneGameII(int[] piles) {

        int[][][] dp=new int[2][piles.length+1][piles.length+1];
        for(int[][] row:dp){
            for(int[] row2:row){
                Arrays.fill(row2,-1);
            }
        }
        return noOfStones(piles, 0, 0, 1, dp);
        
    }


    int noOfStones(int[] piles, int p, int i, int m,  int[][][] dp){
        if(i>=piles.length){
            return 0;
        }

        if(dp[p][i][m]!=-1){
            return dp[p][i][m];
        }

        int x=2*m;

        //p==0 alice, p==1 bob
        int res=(p==1)?Integer.MAX_VALUE:0;
        int s=0;
        for(int j=i;j<Math.min(i+x, piles.length);j++){
            s+=piles[j];
            //alice chance
            if(p==0){
                //adding the no. of stones and storing the maxium result for each value of x
                int stones=s+noOfStones(piles, 1,j+1, Math.max(m,j-i+1), dp);
                res=Math.max(res, stones);
            }
            //bob chance
            else{

                //no need to add the no of stones but we bob will want to minimize the no. of stones for alice(in next turn)
                int stones=noOfStones(piles, 0,j+1, Math.max(m,j-i+1), dp);
                res=Math.min(res, stones);
            }
        }

        return dp[p][i][m]=res;


    }
}