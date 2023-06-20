/**
 * 44. Wildcard Matching

Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Example 1:
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".


Example 2:
Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.


Example 3:
Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 */

import java.util.*;

class TUF {
  static boolean isAllStars(String S1, int i) {
    for (int j = 0; j <= i; j++) {
      if (S1.charAt(j) != '*')
        return false;
    }
    return true;
  }

  static int wildcardMatchingUtil(String S1, String S2, int i, int j, int[][] dp) {

    //Base Conditions
    //both pattern and string exhausts
    if (i < 0 && j < 0)
      return 1;
    //pattern exhausts but string still has some characters
    if (i < 0 && j >= 0)
      return 0;

    //string exhausts but pattern has some characters left.. Check if those characters are only '*'  
    if (j < 0 && i >= 0)
      return isAllStars(S1, i) ? 1 : 0;

    if (dp[i][j] != -1) return dp[i][j];

    if (S1.charAt(i) == S2.charAt(j) || S1.charAt(i) == '?')
      return dp[i][j] = wildcardMatchingUtil(S1, S2, i - 1, j - 1, dp);

    else {
      if (S1.charAt(i) == '*')
        return (wildcardMatchingUtil(S1, S2, i - 1, j, dp) == 1 || wildcardMatchingUtil(S1, S2, i, j - 1, dp) == 1) ? 1 : 0;
      else return 0;
    }
  }

  static int wildcardMatching(String S1, String S2) {

    int n = S1.length();
    int m = S2.length();

    int dp[][] = new int[n][m];
    for (int row[]: dp)
      Arrays.fill(row, -1);
    return wildcardMatchingUtil(S1, S2, n - 1, m - 1, dp);

  }

  public static void main(String args[]) {

    String S1 = "ab*cd";
    String S2 = "abdefcd";

    if (wildcardMatching(S1, S2) == 1)
      System.out.println("String S1 and S2 do match");
    else System.out.println("String S1 and S2 do not match");
  }
}



//Tabulation
//i , j are reversed as per above recursion but it wont matter, check the logic only
class Solution {
    public boolean isMatch(String s, String p) {

        int m=s.length();
        int n=p.length();
        boolean[] prev=new boolean[n+1];

        prev[0]=true;

        for(int j=1;j<=n;j++){
            if(p.charAt(j-1)!='*'){
               prev[j]=false;
                break;
            }
            else{
                prev[j]=true;
            }
        }
            


        for(int i=1;i<=m;i++){
            boolean[] dp=new boolean[n+1];
            for(int j=1;j<=n;j++){

                if(s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='?'){
                   dp[j]=prev[j-1];
                }
                else if(p.charAt(j-1)=='*'){
                   dp[j]=dp[j-1] || prev[j];
                }
                else{
                    dp[j]=false;
                }
            }
            prev=dp;
        }

        return prev[n];

    }
}