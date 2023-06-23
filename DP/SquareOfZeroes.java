/**
 * 
  Write a function that takes in a square-shaped n x n two-dimensional array of
  only 1s and 0s and returns a boolean representing whether the input matrix
  contains a square whose borders are made up of only 0s.


  Note that a 1 x 1 square doesn't count as a valid square for the purpose of
  this question. In other words, a singular 0 in the input matrix
  doesn't constitute a square whose borders are made up of only 0s; a square of
  zeroes has to be at least 2 x 2.

Sample Input
matrix = [
  [1, 1, 1, 0, 1, 0],
  [0, 0, 0, 0, 0, 1],
  [0, 1, 1, 1, 0, 1],
  [0, 0, 0, 1, 0, 1],
  [0, 1, 1, 1, 0, 1],
  [0, 0, 0, 0, 0, 1],
]

Sample Output
true
[
  [ ,  ,  ,  ,  ,  ],
  [0, 0, 0, 0, 0,  ],
  [0,  ,  ,  , 0,  ],
  [0,  ,  ,  , 0,  ],
  [0,  ,  ,  , 0,  ],
  [0, 0, 0, 0, 0,  ],
]
 */

import java.util.*;

class Program {
  public static boolean squareOfZeroes(List<List<Integer>> matrix) {

    ZeroInfo[][] countZeroMatrix=preComputeNoOfZeroes(matrix);
    int n=matrix.size();
    for(int topRow=0;topRow<n;topRow++){
      for(int leftColumn=0;leftColumn<n;leftColumn++){
        int squareSize=2;

        while(squareSize<=n){
          int bottomRow=topRow+squareSize-1;
          int rightColumn=leftColumn+squareSize-1;
          if(bottomRow>=n || rightColumn>=n){
            break;
          }
          if(isSquare(topRow,bottomRow,leftColumn,rightColumn,countZeroMatrix,squareSize)){
            return true;
          }
          
          squareSize++;
        }
        
      }
    }
    
    return false;
  }

  static ZeroInfo[][] preComputeNoOfZeroes(List<List<Integer>> matrix){
    ZeroInfo[][] result=new ZeroInfo[matrix.size()][matrix.size()];
    for(int i=matrix.size()-1;i>=0;i--){
      for(int j=matrix.size()-1;j>=0;j--){
        int val=matrix.get(i).get(j);
        if(val==1){
          result[i][j]=new ZeroInfo(0,0);
        }else{
          result[i][j]=new ZeroInfo(1,1);
          if(i+1<matrix.size()){
             result[i][j].bottomZeroes+=result[i+1][j].bottomZeroes;
          }

          if(j+1<matrix.size()){
             result[i][j].rightZeroes+=result[i][j+1].rightZeroes;
          }
        }
      }
    }
    return result;
  }

  static boolean isSquare(int r1, int r2, int c1, int c2, ZeroInfo[][] countZeroMatrix, int size){
    boolean topRow=countZeroMatrix[r1][c1].rightZeroes>=size;
    boolean bottomRow=countZeroMatrix[r2][c1].rightZeroes>=size;
    boolean leftColumn=countZeroMatrix[r1][c1].bottomZeroes>=size;
    boolean rightColumn=countZeroMatrix[r1][c2].bottomZeroes>=size;
    return topRow && bottomRow && leftColumn && rightColumn;
  }

  static class ZeroInfo{
    int rightZeroes;
    int bottomZeroes;
    ZeroInfo( int rightZeroes,int bottomZeroes){
      this.rightZeroes=rightZeroes;
      this.bottomZeroes=bottomZeroes;
    }
  }

}
