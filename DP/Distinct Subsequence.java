/**
 * 
 * Given two strings s and t, return the number of distinct 
subsequences
 of s which equals t.

The test cases are generated so that the answer fits on a 32-bit signed integer.

 

Example 1:

Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit
Example 2:

Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag
 */


class Solution {
    public int numDistinct(String s, String t) {

        //Recursive and Memorization
        // Integer dp[][]=new Integer[s.length()][t.length()];
        // return noOfDistinctSunsequences(s,t,s.length()-1, t.length()-1, dp);

        //Tabulation
        return noOfDistinctSunsequencesTabulation(s,t);
    }


    public int noOfDistinctSunsequencesTabulation(String s, String t){
        int m=s.length();
        int n=t.length();


        int[] prev=new int[n+1];

        prev[0]=1;

        for(int i=1;i<=m;i++){

            int[] currentDP=new int[n+1];
            currentDP[0]=1;
            for(int j=1;j<=n;j++){

                if(s.charAt(i-1)==t.charAt(j-1)){
                    currentDP[j]=prev[j]+prev[j-1];
                }else{
                    currentDP[j]=prev[j];
                }
            }
            prev=currentDP;
        }

        return prev[n];

    }


    int noOfDistinctSunsequences(String s, String t, int i, int j,  Integer dp[][]){

        // Not required
        // if(i==0 && j==0){
        //     if(s.charAt(0)==t.charAt(0))
        //         return 1;
        //     else{
        //         return 0;
        //     }
        // }
        
        if(j<0){
            return 1;
        }

        if(i<0){
            return 0;
        }

       
        if(dp[i][j]!=null){
            return dp[i][j];
        }

        if(s.charAt(i)==t.charAt(j)){
            return dp[i][j]=noOfDistinctSunsequences(s, t, i-1, j, dp) 
            + noOfDistinctSunsequences(s, t, i-1, j-1, dp);
        }else{
            return dp[i][j]=noOfDistinctSunsequences(s, t, i-1, j, dp);
        }


    }

}