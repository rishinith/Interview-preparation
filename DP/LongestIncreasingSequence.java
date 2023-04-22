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

