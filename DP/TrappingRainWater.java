/**
 *

  You're given an array of non-negative integers where each non-zero integer
  represents the height of a pillar of width 1. Imagine water being
  poured over all of the pillars; write a function that returns the surface area
  of the water trapped between the pillars viewed from the front. Note that
  spilled water should be ignored.


  You can refer to the first three minutes of this question's video explanation
  for a visual example.

Sample Input
heights = [0, 8, 0, 0, 5, 0, 0, 10, 0, 0, 1, 1, 0, 3]

Sample Output
48

// Below is a visual representation of the sample input.
// The dots and vertical lines represent trapped water and pillars, respectively.
// Note that there are 48 dots.
//        |
//        |
//  |.....|
//  |.....|
//  |.....|
//  |..|..|
//  |..|..|
//  |..|..|.....|
//  |..|..|.....|
// _|..|..|..||.|


 */

import java.util.*;

class Program {
  public static int waterArea(int[] heights) {

    if(heights.length==0){
      return 0;
    }

    int leftMax[]=new int[heights.length];
    int rightMax[]=new int[heights.length];

    int maxFound=leftMax[0];
    for(int i=0;i<heights.length-1;i++){
        maxFound=Math.max(heights[i],maxFound);
        leftMax[i+1]=maxFound;
    }

    maxFound=rightMax[heights.length-1];
    for(int i=heights.length-1;i>0;i--){
        maxFound=Math.max(heights[i],maxFound);
        rightMax[i-1]=maxFound;
    }

    int totalArea=0;
    for(int i=0;i<heights.length;i++){
      int area=Math.min(leftMax[i],rightMax[i])-heights[i];
      if (area>0){
        totalArea+=area;
      }
      
    }

    
    return totalArea;
  }
}




//Space optimimized solution O(1)
//Just keep the two pointers on left and right and we know that we leftmax or rightmax whichever is smaller will limit our volume for the respective left/right index
import java.util.*;

class Program {
  public static int waterArea(int[] heights) {

    if(heights.length==0){
      return 0;
    }

    int left=0;
    int right=heights.length-1;
    int leftMax=heights[left];
    int rightMax=heights[right];

    int area=0;
    while(left<right){
      if(leftMax<rightMax){
        left++;
        int s=leftMax-heights[left];
        if(s>0){
        area=area+s;
        }
        leftMax=Math.max(leftMax, heights[left]);
      }else{
         right--;
         int s=rightMax-heights[right];
        if(s>0){
        area=area+s;
        }
        rightMax=Math.max(rightMax, heights[right]);
       
        
      }
    }
    return area;
  }
}
