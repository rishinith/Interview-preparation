/**
 * 

  You're given two positive integers representing the width and height of a
  grid-shaped, rectangular graph. Write a function that returns the number of
  ways to reach the bottom right corner of the graph when starting at the top
  left corner. Each move you take must either go down or right. In other words,
  you can never move up or left in the graph.


  For example, given the graph illustrated below, with
  width = 2 and height = 3, there are three ways to
  reach the bottom right corner when starting at the top left corner:

 _ _
|_|_|
|_|_|
|_|_|


  Down, Down, Right
  Right, Down, Down
  Down, Right, Down


  Note: you may assume that width * height = 2. In other words,
  the graph will never be a 1x1 grid.

Sample Input
width = 4
height = 3

Sample Output
10


 */

import java.util.*;

class Program {

//Space: O(width*height)

  public int numberOfWaysToTraverseGraph(int width, int height) {

    int[][] matrix=new int[height][width];


    for(int i=0;i<matrix.length;i++)
      for(int j=0;j<matrix[0].length;j++){
          if(i==0 || j==0){
            matrix[i][j]=1;
            continue;
          }

        matrix[i][j]=matrix[i-1][j]+matrix[i][j-1];
        
      }

      return matrix[height-1][width-1];
  
  }
}



import java.util.*;

//Space: O(width)
class Program {

  public int numberOfWaysToTraverseGraph(int width, int height) {

  
    int[] auxillaryArray=new int[width];
    Arrays.fill(auxillaryArray,1);

    for(int i=1;i<height;i++){
      int[] newArray=new int[width];
      newArray[0]=1;
      for(int j=1;j<width;j++){
           newArray[j]=auxillaryArray[j]+ newArray[j-1];
      }
      auxillaryArray=newArray;
    }

      return auxillaryArray[width-1];
  
  }
}


/**
 * 
 * Maths formula:
 * 
 * r=No: of Right we can move=width-1
 * d=No. of down we can move=height-1
 * 
 * total ways=(r+d)!/(r!*d!)
 * 
 */