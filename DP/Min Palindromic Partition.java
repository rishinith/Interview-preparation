/**
 * 

  Given a non-empty string, write a function that returns the minimum number of
  cuts needed to perform on the string such that each remaining substring is a
  palindrome.


  A palindrome is defined as a string that's written the same forward as
  backward. Note that single-character strings are palindromes.

Sample Input
string = "noonabbad"

Sample Output
2 // noon | abba | d"


 */

import java.util.*;

//T: n*n*n S:n*n
class Program {
  public static int palindromePartitioningMinCuts(String str) {
    boolean[][] palindromes=palindrome2DArray(str);

    int cuts[]=new int[str.length()];
    cuts[0]=0;
    for(int i=1;i<str.length();i++){
      //if substring is palindrome, no cut needed
      if(palindromes[0][i]){
        cuts[i]=0;
      }else{
        //whatver cut till last substring + 1 for new character
        cuts[i]=cuts[i-1]+1;
        for(int j=1;j<i;j++){
            //see if there is substring till i which is also palindrome
            if(palindromes[j][i] && cuts[j-1]+1<cuts[i]){
              cuts[i]=cuts[j-1]+1;
            }
        }
      }
    }
    return cuts[str.length()-1];
  }

  //return 2D array which store the palindrome of all substrings (i,j)
  //T: n*n*n S:n*n
  static boolean[][] palindrome2DArray(String str){
    int n=str.length();

    boolean[][] palindromes=new boolean[n][n];

    for(int i=0;i<n;i++){
      for(int j=i;j<n;j++){
        palindromes[i][j]=isPalindrome(str, i, j);
      }
    }

    return palindromes;
  }

  static boolean isPalindrome(String str, int i, int j){
    while(i<j){
      if(str.charAt(i)!=str.charAt(j)){
        return false;
      }
      i++;
      j--;
    }
    return true;
  }
}



//More optimized code. in T:n*n

  //return 2D array which store the palindrome of all substrings (i,j)
  //T: n*n S:n*n
   //return 2D array which store the palindrome of all substrings (i,j)
  static boolean[][] palindrome2DArray(String str){
    int n=str.length();

    boolean[][] palindromes=new boolean[n][n];

    for(int i=0;i<n;i++){
       palindromes[i][i]=true;
    }

    for(int length=2;length<str.length()+1;length++ ){
      for(int i=0;i<str.length()-length+1;i++){
          int j=i+length-1;
          if(length==2){
            palindromes[i][j]=str.charAt(i)==str.charAt(j);
          }
          else{
            palindromes[i][j]=str.charAt(i)==str.charAt(j) &&   palindromes[i+1][j-1];
          }
      }
    }

    return palindromes;
  }
