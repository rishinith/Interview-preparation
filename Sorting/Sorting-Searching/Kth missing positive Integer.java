/**
 * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
Return the kth positive integer that is missing from this array.

Example 1:
Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.


Example 2:
Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
 */

class Solution {
    public int findKthPositive(int[] arr, int k) {
       /**
        * Approach 1: Start iterating over the array elements, and for every element check if the next element is consecutive or not, if not, 
        * then take the difference between these two, and check if the difference is greater than or equal to given k, 
        * then calculate ans = a[i] + count, else iterate for next element.
        */
        // if the kth missing is less than arr[0]
        if (k <= arr[0] - 1) {
            return k;
        }
        k -= arr[0] - 1;

        // search kth missing between the array numbers
        int n = arr.length;
        for (int i = 0; i < n - 1; ++i) {
            // missing between arr[i] and arr[i + 1]
            int currMissing = arr[i + 1] - arr[i] - 1;
            // if the kth missing is between
            // arr[i] and arr[i + 1] -> return it
            if (k <= currMissing) {
                return arr[i] + k;
            }
            // otherwise, proceed further
            k -= currMissing;
        }

        // if the missing number if greater than arr[n - 1]
        return arr[n - 1] + k;
        //return linearSearch(arr,k);
       //return binarySearchHelper(arr,k);
    }

    //Approach2
    int linearSearch(int[] arr, int k){

        for(int i=0;i<arr.length;i++){

            if(arr[i]<=k){
                k++;
            }else{
                break;
            }
        }

        return k;
    }

    //Approach3
    int binarySearchHelper(int[] arr, int k){
         int low=0;
        int high=arr.length-1;

        while(low<=high){
            int mid=(low+high)/2;

            int missingNoUptoThisIndex=arr[mid]-(mid+1);
            if(missingNoUptoThisIndex<k){
                low=mid+1;
            }
            else{
                high=mid-1;
            }
        }



        //no lie in range of arr[high]---arr[low]  now
        
        return high+1+k;
    }



}