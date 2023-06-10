/**
 * You are given nums, an array of positive integers of size 2 * n. You must perform n operations on this array.

In the ith operation (1-indexed), you will:

Choose two elements, x and y.
Receive a score of i * gcd(x, y).
Remove x and y from nums.
Return the maximum score you can receive after performing n operations.

The function gcd(x, y) is the greatest common divisor of x and y.

 

Example 1:
Input: nums = [1,2]
Output: 1
Explanation: The optimal choice of operations is:
(1 * gcd(1, 2)) = 1


Example 2:
Input: nums = [3,4,6,8]
Output: 11
Explanation: The optimal choice of operations is:
(1 * gcd(3, 6)) + (2 * gcd(4, 8)) = 3 + 8 = 11


Example 3:
Input: nums = [1,2,3,4,5,6]
Output: 14
Explanation: The optimal choice of operations is:
(1 * gcd(1, 5)) + (2 * gcd(2, 4)) + (3 * gcd(3, 6)) = 1 + 4 + 9 = 14


 */

/**
 * The problem can be solved by using a backtracking approach, 
 * we can try forming all cases of all possible pairs of elements, generating the total score in each case, and selecting the one with the maximum score.
 * 
 * We break the current bigger problem into smaller similar sub-problems.
 * 
 * We have an array of eight elements [a, b, c, d, e, f, g, h]. 
 * In Case 1, we select pairs (c, d) and (b, e), and the remaining array to find the answer is [a, f, g, h]. 
 * In Case 2, we select pairs (b, c) and (d, e), and again the remaining array is [a, f, g, h]. 
 * It is evident that the sub-problem of finding the answer for the array [a, f, g, h] is common to both cases. 
 * To optimize computation time and avoid redundant calculations, 
 * we can use memoization to store and reuse the results of this sub-problem whenever it occurs again
 * 
 * For memorization, we can use int[] to record the state of array index and then Map<String,Integer> cache to store the result; Key of the cache cane be Array.toString(int[])
 * 
 * Now, we know that the state of the current sub-problem depends on the remaining elements of the array. 
 * So we need to memorize the result based on this state. An easy way to implement this is using bitmasking.
 * 
 * As integers have 32 bits, each bit can be 0 or 1. We can use these bits to represent if an element of our array is picked or not.
In an integer number (say mask) if the bit at position i is 0, it means the array element at the i th index is not picked otherwise 
if it's 1 it means the element was picked earlier.

Note: If number of elements in the nums array will exceed 32 then we will not be able to use this method with a 32 bit integer.

So we can map the mask (current state) with the result, using a hashmap or an array, memo.

Here, m=2∗n is the number of elements, and A is the maximum value in the nums array. n is no of operations
Time Complexity:

The overall time complexity is O(4^n * n^2 * log A).
The exponential number of unique states of the mask (2^(2n)) contributes to the complexity.
Each function call performs nested iterations through all pairs (O(m^2)).
For each pair, a gcd operation is performed, taking at most O(log A) time.
Due to memoization, only 2^m unique calls of the function are evaluated.

Space Complexity:

The space complexity is O(4^n).
The recursive stack uses O(n) space at most.
The memo array stores results for all possible states (2^(2n) states).
Please note that while there may exist a better upper bound for the time complexity, this analysis is sufficient for interview settings with limited time.
 * 
 */

     public int maxScore(int[] nums) {

        //no of possible states is 2^(nums size)
        int memoSize=1<<nums.length;
        int[] memo=new int[memoSize];
        Arrays.fill(memo,-1);
        return recursiveUsingBitmask(nums,memo, 1, 0);
    }

    int recursiveUsingBitmask(int [] nums, int[] memo,  int operation, int mask){
        if(operation>nums.length/2 ){
            return 0;
        }

        if(memo[mask]!=-1){
            return memo[mask];
        }

        int result=0;
        for(int i=0;i<nums.length;i++){

            //if i th bit is already set i.e i th item is already included, ignore
            if((mask & (1 << i))!=0){
                continue;
            }

                                  
            for(int j=i+1;j<nums.length;j++){

                //if jth bit is already set i.e j th item is already included, ignore
               if((mask & (1 << j))!=0){
                   continue;
               }

                

                int num1=nums[i];
                int num2=nums[j];
                
                //set ith and jth bit, include i th and j th item
                mask|=1<<i;
                mask|=1<<j;
                
                int temp=operation*gcd(num1, num2) + recursiveUsingBitmask(nums, memo, operation+1, mask);
                result=Math.max(result, temp);

                //Backtracking: unset ith and jth bit, remove ith and jth item
                mask^=1<<j;
                mask^=1<<i;
                


            }
        }

        memo[mask]=result;

        return result;
    }


    public int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }