/**
 * 

  Write a function that takes in two strings and returns the minimum number of
  edit operations that need to be performed on the first string to obtain the
  second string.


  There are three edit operations: insertion of a character, deletion of a
  character, and substitution of a character for another.

Sample Input
str1 = "abc"
str2 = "yabd"

Sample Output
2 // insert "y"; substitute "c" for "d"


 */

import java.util.*;
//space O(m*n) time:m*n
class Program {
  public static int levenshteinDistance(String str1, String str2) {

    int [][] matrix=new int[str1.length()+1][str2.length()+1];
    int fill=0;
    for(int i=0;i<=str1.length();i++){
      matrix[i][0]=fill++;
    }

    fill=0;
    for(int i=0;i<=str2.length();i++){
      matrix[0][i]=fill++;
    }


    for(int i=1;i<=str1.length();i++)
      for(int j=1;j<=str2.length();j++){

        if (str1.charAt(i-1)==str2.charAt(j-1)){
          matrix[i][j]=matrix[i-1][j-1];
        }
        else{
          matrix[i][j]=1+ Math.min(matrix[i-1][j], Math.min(matrix[i][j-1], matrix[i-1][j-1]));
        }
      }
    
    return matrix[str1.length()][str2.length()];
  }
}



import java.util.*;
//Space: O(min(m,n)) time:m*n
//Instead of 2 D array use 1D array
class Program {
  public static int levenshteinDistance(String str1, String str2) {

    String small=str1.length()>=str2.length()?str2:str1;
    String big=str1.length()>=str2.length()?str1:str2;

    int [] auxillaryArray=new int[small.length()+1];
    int fill=0;
     for(int i=0;i<auxillaryArray.length;i++){
      auxillaryArray[i]=fill++;
    }

    int count=1;
    System.out.println("Small:"+small);
    System.out.println("Big:"+big);

    for(int i=1;i<=big.length();i++){

      int newArray[]=new int[auxillaryArray.length];
      newArray[0]=count++;
      for(int j=1;j<auxillaryArray.length;j++){
          if(big.charAt(i-1)==small.charAt(j-1)){
            newArray[j]=auxillaryArray[j-1];
          }else{
            newArray[j]=1+Math.min(auxillaryArray[j-1],Math.min(newArray[j-1],auxillaryArray[j]));
          }        
      }
      auxillaryArray=newArray;
    }

    return auxillaryArray[small.length()];

  }
}

