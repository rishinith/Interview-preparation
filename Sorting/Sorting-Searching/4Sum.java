class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);

        List<List<Integer>> result=new ArrayList<>();

        for(int i=0;i<nums.length-3;i++){

            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }

            for(int j=i+1;j<nums.length-2;j++){
                if(j>i+1 && nums[j]==nums[j-1]){
                    continue;
                }
                searchPair(nums, target,i,j,result);
            }

        }

        return result;
        
    }

    void searchPair(int[] nums, long target, int i, int j, List<List<Integer>> result){
        int l=j+1;
        int h=nums.length-1;

        target=target-nums[i]-nums[j];

        while(l<h){
            long currSum=nums[l]+nums[h];
            if(currSum==target){
                result.add(Arrays.asList(nums[i],nums[j],nums[l],nums[h]));
                l++; h--;
                while(l<h && nums[l]==nums[l-1]){
                    l++;
                    continue;
                }

                while(l<h && nums[h]==nums[h+1]){
                    h--;
                    continue;
                }
            }
            else if(currSum>target){
                h--;
            }else{
                l++;
            }
        }
    }
}