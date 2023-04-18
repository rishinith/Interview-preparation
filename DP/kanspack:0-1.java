/**
 * 

  You're given an array of arrays where each subarray holds two integer values
  and represents an item; the first integer is the item's value, and the second
  integer is the item's weight. You're also given an integer representing the
  maximum capacity of a knapsack that you have.


  Your goal is to fit items in your knapsack without having the sum of their
  weights exceed the knapsack's capacity, all the while maximizing their
  combined value. Note that you only have one of each item at your disposal.


  Write a function that returns the maximized combined value of the items that
  you should pick as well as an array of the indices of each item picked.


  If there are multiple combinations of items that maximize the total value in
  the knapsack, your function can return any of them.

Sample Input
items = [[1, 2], [4, 3], [5, 6], [6, 7]]
capacity = 10

Sample Output
[10, [1, 3]] // items [4, 3] and [6, 7]


 */

// This is a Java program to solve the knapsack problem using dynamic programming approach.
// The knapsack problem involves selecting items with maximum total value, while keeping the total weight below a certain limit.

import java.util.*;

class Program {
public static List<List<Integer>> knapsackProblem(int[][] items, int capacity) {

// Initialize a 2D array to store the maximum value that can be obtained for different sub-problems.
int[][] knapsack=new int[items.length+1][capacity+1];

// Iterate through all items and their weights, and calculate the maximum value that can be obtained for each weight limit.
for(int i=1;i<=items.length;i++){
  int currentWeight=items[i-1][1];
  int currentValue=items[i-1][0];
  for(int c=1;c<=capacity;c++){
  
    // If the current item's weight exceeds the weight limit, we cannot add it to the knapsack.
    if(currentWeight>c){
      knapsack[i][c]= knapsack[i-1][c]; // Inherit the previous maximum value.
      continue;
    }
    
    // Calculate the maximum value that can be obtained by either adding or not adding the current item.
    knapsack[i][c]=Math.max(knapsack[i-1][c],knapsack[i-1][c-currentWeight]+currentValue);
  }
}

// The maximum value that can be obtained is stored in the last cell of the knapsack array.
int maxValue=knapsack[items.length][capacity];

// Retrieve the indices of items that were selected to obtain the maximum value.
List<Integer> seq=getKnapsackItems(knapsack, items);

// Prepare the result in a List of Lists format.
List<List<Integer>>  result=new ArrayList<>();
result.add(Arrays.asList(maxValue));
result.add(seq);

return result;
}

// Helper function to retrieve the indices of items that were selected.
static List<Integer> getKnapsackItems(int[][] knapsack,int[][] items){
    List<Integer> seq=new ArrayList<>();
    int i=knapsack.length-1;
    int c=knapsack[0].length-1;
    while(i>0){
        if(knapsack[i][c]==knapsack[i-1][c]){ // If the current item was not selected, move to the previous item.
        i--;
        continue;
        }
        seq.add(i-1); // If the current item was selected, add its index to the sequence.
        c-=items[i-1][1]; // Reduce the capacity by the weight of the current item.
        i--;

        if(c==0){ // If the capacity becomes zero, we have selected all items that can fit in the knapsack.
        break;
        }
    }
    Collections.reverse(seq); // Reverse the sequence to get the order in which items were added to the knapsack.
    return seq; 
}
} 

