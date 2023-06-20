/**
 * 

  Given a non-empty array of integers, write a function that returns the longest
  strictly-increasing subsequence in the array.


  A subsequence of an array is a set of numbers that aren't necessarily adjacent
  in the array but that are in the same order as they appear in the array. For
  instance, the numbers [1, 3, 4] form a subsequence of the array
  [1, 2, 3, 4], and so do the numbers [2, 4]. Note
  that a single number in an array and the array itself are both valid
  subsequences of the array.


  You can assume that there will only be one longest increasing subsequence.

Sample Input
array = [5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35]

Sample Output
[-24, 2, 3, 5, 6, 35]


 */


import java.util.*;

//n*n time,  n space
class Program {
  public static List<Integer> longestIncreasingSubsequence(int[] array) {

    int dp[]=new int[array.length];
    int sequence[]=new int[array.length];
    Arrays.fill(sequence,-1);

    int maxIndex=0;
    for(int i=0;i<array.length;i++){
      dp[i]=1;
      for(int j=0;j<i;j++){
        if(array[j]<array[i] && dp[i]<dp[j]+1){
          dp[i]=dp[j]+1;
          sequence[i]=j;    
        }
      }
      if(dp[i]>dp[maxIndex]){
        maxIndex=i;
      }
    }

    var result=new ArrayList<Integer>();

    while(maxIndex!=-1){
      result.add(array[maxIndex]);
      maxIndex=sequence[maxIndex];
    }

    Collections.reverse(result);
    return result;
  }
}


//Optimized using binary search
//T: nlogn
import java.util.*;

class Program {
  public static List<Integer> longestIncreasingSubsequence(int[] array) {

   int[] sequences=new int[array.length];
    int[] indices=new int[array.length+1];
    Arrays.fill(indices,-1);

    int length=0;

    for(int i=0;i<array.length;i++){
      int num=array[i];
      int newLength=binarySearch(indices,1, length, array, num);
      sequences[i]=indices[newLength-1];
      indices[newLength]=i;
      length=Math.max(length,newLength);
    }

    return buildSequence(array,sequences, indices[length]);
    
  }

  static List<Integer> buildSequence(int[] array, int[] sequences, int index){
    List<Integer> seq=new ArrayList<>();
    while(index!=-1){
      seq.add(0, array[index]);
      index=sequences[index];
    }
    return seq;
  }

  static int binarySearch(int[] indices, int start, int end, int[] array, int num){
    if(start>end){
      return start;
    }
    int middle=start+(end-start)/2;
    if(array[indices[middle]]<num){
      start=middle+1;
    }
    else{
      end=middle-1; 
    }
    return binarySearch(indices,start,end,array,num);
    
  }
}



/**
 * Given an integer array nums, return the number of longest increasing subsequences.

Notice that the sequence has to be strictly increasing.

 

Example 1:
Input: nums = [1,3,5,4,7]
Output: 2
Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].


Example 2:
Input: nums = [2,2,2,2,2]
Output: 5
Explanation: The length of the longest increasing subsequence is 1, and there are 5 increasing subsequences of length 1, so output 5.
 */

class Solution {
    public int findNumberOfLIS(int[] nums) {

        int length[]=new int[nums.length];
        int count[]=new int[nums.length];

        int maxLength=-1;
        for(int i=0;i<nums.length;i++){
            length[i]=1;
            count[i]=1;
            for(int j=0;j<i;j++){

                if(nums[i]>nums[j] && 1+length[j]>length[i]){
                    length[i]=1+length[j];
                    count[i]=count[j];
                }
                else if (nums[i]>nums[j] && 1+length[j]==length[i]){
                    count[i]+=count[j];
                }

            }

            maxLength=Math.max(maxLength, length[i]);
        }


        int total=0;

        for(int i=0;i<nums.length;i++){

            if(length[i]==maxLength){
                total+=count[i];
            }
        }

        return total;
        
    }
}