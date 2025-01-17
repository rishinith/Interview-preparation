/**
 * You are given an integer array nums. Two players are playing a game with this array: player 1 and player 2.

Player 1 and player 2 take turns, with player 1 starting first. Both players start the game with a score of 0. 
At each turn, the player takes one of the numbers from either end of the array (i.e., nums[0] or nums[nums.length - 1]) which reduces the size of the array by 1. The player adds the chosen number to their score. The game ends when there are no more elements in the array.

Return true if Player 1 can win the game. If the scores of both players are equal, then player 1 is still the winner, 
and you should also return true. You may assume that both players are playing optimally.

 

Example 1:
Input: nums = [1,5,2]
Output: false
Explanation: Initially, player 1 can choose between 1 and 2. 
If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be left with 1 (or 2). 
So, final score of player 1 is 1 + 2 = 3, and player 2 is 5. 
Hence, player 1 will never be the winner and you need to return false.


Example 2:
Input: nums = [1,5,233,7]
Output: true
Explanation: Player 1 first chooses 1. Then player 2 has to choose between 5 and 7. No matter which number player 2 choose, player 1 can choose 233.
Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
 */


/**
 * We can define a recursive function maxDiff(left, right) that takes the starting and ending indices left and right of the list as input to maximize the current score difference between the two players.

At each step, we simulate the current player selecting a number from the list and then call the function recursively to determine the optimal move for the next player. 
We update the score difference based on the player's move, and then return the maximum score difference that the first player can achieve.
 */

class Solution {
    int[][] memo;
    
    private int maxDiff(int[] nums, int left, int right, int n) {
        if (memo[left][right] != -1) {
            return memo[left][right];
        }
        if (left == right) {
            return nums[left];
        }
        
        int scoreByLeft = nums[left] - maxDiff(nums, left + 1, right, n);
        int scoreByRight = nums[right] - maxDiff(nums, left, right - 1, n);
        memo[left][right] = Math.max(scoreByLeft, scoreByRight);
        
        return memo[left][right];
    }
    
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        memo = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(memo[i], -1);
        }
        
        return maxDiff(nums, 0, n - 1, n) >= 0;
    }
}


//2nd way
class Solution {
    public boolean PredictTheWinner(int[] nums) {

        int totalScore=Arrays.stream(nums).sum();
        int player1MaxScore=player1MaxSCore(nums, 0, nums.length-1, 0, new Integer[nums.length][nums.length][2]);
        int player2MaxScore=totalScore-player1MaxScore;
        return player1MaxScore>=player2MaxScore;
        
    }



    int player1MaxSCore(int[] nums, int left, int right, int  chance, Integer[][][] dp){
        if(left>right){
            return 0;
        }

        if(dp[left][right][chance]!=null){
            return dp[left][right][chance];
        }
        if(chance==0){
            return dp[left][right][chance]= 
            Math.max(nums[left]+player1MaxSCore(nums, left+1, right, 1, dp), nums[right]+player1MaxSCore(nums, left, right-1, 1, dp));
        }
        else{
            return dp[left][right][chance]=
            Math.min(player1MaxSCore(nums, left+1, right, 0, dp), player1MaxSCore(nums, left, right-1, 0, dp));
        }
    }
}