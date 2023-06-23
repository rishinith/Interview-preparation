/**
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
 * Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
 */

class Solution {
    public int largestRectangleArea(int[] heights) {

    /**
    1) We weep pushing in the stack is stack is empty or peek<= the array value
    2) The moment we get the array value less than the peek, will pop the stack and calculate the maxArea 
    and store it and keep popping.
    3) If stack is not empty, keep popping the stack and calculate the area from the stack index to the last index.

     */

    Stack<Integer> stack=new Stack<>();
    stack.push(-1);
    int maxArea=Integer.MIN_VALUE;
     for(int i=0;i<heights.length;i++){


        if (stack.peek() == -1 || heights[stack.peek()]<heights[i]){
             stack.push(i);
         }
         else{
             while(stack.peek() != -1 && heights[stack.peek()]>=heights[i]){
                 int index1=stack.pop();
                 int h=heights[index1];
                 int w=i-stack.peek()-1;
                 maxArea=Math.max(h*w, maxArea);
             }
             stack.push(i);
         }

     }


     while(stack.peek() != -1){
         int index1=stack.pop();
         int h=heights[index1];
         int w=heights.length-stack.peek()-1;
         maxArea=Math.max(maxArea,h*w);
     }

     return maxArea;
 
    }
}