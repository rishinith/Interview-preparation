/**
 * 312. Burst Balloons
You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.

If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, 
then treat it as if there is a balloon with a 1 painted on it.

Return the maximum coins you can collect by bursting the balloons wisely.

Input: nums = [3,1,5,8]
Output: 167
Explanation:
nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 */

class Solution {
    public int maxCoins(int[] nums) {
        List<Integer> list=new ArrayList<>();

        list.add(1);
        for(int num:nums){
            list.add(num);
        }
        list.add(1);

        // Integer[][] dp=new Integer[list.size()][list.size()];

        // //the first and last element are sentinal nodes so starting from 1 to list.size()-2
        // return maxCoins(list, 1, list.size()-2,dp );

        return maxCoinsTabulation(list);
    }


    int maxCoins(List<Integer> nums, int i, int j, Integer[][] dp){

        if(i>j){
            return 0;
        }

        if(dp[i][j]!=null){
            return dp[i][j];
        }

        int maxCoins=0;
        for(int k=i;k<=j;k++){

            //lets says this is the last ballon bursed in range (i,j)
            //After that fix ballon nums[k] and find solution for left and right side
            int coins=nums.get(i-1)*nums.get(k)*nums.get(j+1) + maxCoins(nums,i, k-1, dp) + maxCoins(nums, k+1, j, dp);
            maxCoins=Math.max(maxCoins, coins);

        }

        return dp[i][j]=maxCoins;

    }


    int maxCoinsTabulation(List<Integer> nums){

        int[][] dp=new int[nums.size()][nums.size()];

        for(int i=nums.size()-2;i>=1;i--){
            for(int j=0;j<=nums.size()-2;j++){
                if(i>j){
                    continue;
                }
                int maxCoins=0;
                for(int k=i;k<=j;k++){
                    //lets says this is the last ballon bursed in range (i,j)
                    int coins=nums.get(i-1)*nums.get(k)*nums.get(j+1) + dp[i][k-1] + dp[k+1][j];
                    maxCoins=Math.max(maxCoins, coins);
                }
                dp[i][j]=maxCoins;

            }
        }

        return dp[1][nums.size()-2];
    }
}