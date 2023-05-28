/**Given an integer array nums and an integer k, return the kth largest element in the array.

Note that it is the kth largest element in the sorted order, not the kth distinct element.

You must solve it in O(n) time complexity. */

class Solution {
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums,0,nums.length-1,nums.length-k);
    }

    int quickSelect(int[] nums, int left, int right, int index){
        if (left==right){
            return nums[left];
        }
        //Random random=new Random();
        //int pivot = left + random.nextInt(right - left); 

        int pivot=partition(nums,left+1,right,left);
        if(pivot==index){
            return nums[pivot];
        }else if(pivot<index){
            return quickSelect(nums,pivot+1,right, index);
        }
        else{
            return quickSelect(nums,left,pivot-1,index);
        }

    }

    int partition(int[] nums, int left, int right, int pivot){
        while(left<=right){
            if(nums[left]>nums[pivot] && nums[right]<nums[pivot]){
                swap(nums, left,right);
            }
            else if(nums[left]<=nums[pivot]){
                left++;
            }
            else if(nums[right]>=nums[pivot]){
                right--;
            }
        }
        swap(nums,pivot,right);
        return right;
    }

    void swap(int[] nums, int i, int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
}