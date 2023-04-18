/**
 * 

  Write a function that takes in two strings and returns their longest common
  subsequence.


  A subsequence of a string is a set of characters that aren't necessarily
  adjacent in the string but that are in the same order as they appear in the
  string. For instance, the characters ["a", "c", "d"] form a
  subsequence of the string "abcd", and so do the characters
  ["b", "d"]. Note that a single character in a string and the
  string itself are both valid subsequences of the string.


  You can assume that there will only be one longest common subsequence.

Sample Input
str1 = "ZXVVYZW"
str2 = "XKYKZPW"

Sample Output
["X", "Y", "Z", "W"]


 */

import java.util.*;

public class Program {

    public static List<Character> longestCommonSubsequence(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        // Fill the DP matrix
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        // Retrieve the LCS
        List<Character> result = new ArrayList<>();
        int i = str1.length(), j = str2.length();
        while (i > 0 && j > 0) {
            if (str1.charAt(i-1) == str2.charAt(j-1)) {
                result.add(str1.charAt(i-1));
                i--;
                j--;
            } else if (dp[i-1][j] >= dp[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }

        // Reverse and return the result
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        String str1 = "AGGTAB";
        String str2 = "GXTXAYB";
        List<Character> lcs = longestCommonSubsequence(str1, str2);
        System.out.println(lcs);
    }
}
