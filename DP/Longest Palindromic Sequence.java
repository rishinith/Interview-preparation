/**
 * Given a string s, find the longest palindromic subsequence's length in s.
A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.
Example 1:

Input: s = "bbbab"
Output: 4
Explanation: One possible longest palindromic subsequence is "bbbb".
 */
class Solution {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] memo = new int[n][n];
        return lps(s, 0, n - 1, memo);
    }

    private int lps(String s, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (i > j) {
            return 0;
        }
        if (i == j) {
            return 1;
        }

        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = lps(s, i + 1, j - 1, memo) + 2;
        } else {
            memo[i][j] = Math.max(lps(s, i + 1, j, memo), lps(s, i, j - 1, memo));
        }
        return memo[i][j];
    }
}



//Iterative  
//Space:N
class Solution {
    public int longestPalindromeSubseq(String s) {

        int n=s.length();
        int[] prev=new int[n];


        for(int i=n-1;i>=0;i--){
            //dp[i][i]=1;
            int[] dp=new int[n];
            for(int j=0;j<n;j++){
                if(j<i){
                    continue;
                }

                if(i==j){
                    dp[j]=1;
                    continue;
                }

                // if(i+1>=n || j-1<0){
                //     continue;
                // }
                if(s.charAt(i)==s.charAt(j)){

                    dp[j]=2+prev[j-1];

                }
                else{
                    dp[j]=Math.max(prev[j], dp[j-1]);
                }
            }
            prev=dp;
        }

        return prev[n-1];
        
    }
}