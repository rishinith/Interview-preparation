/**
 * You are given an integer array nums and an integer goal.
You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal.
 That is, if the sum of the subsequence's elements is sum, then you want to minimize the absolute difference abs(sum - goal).
Return the minimum possible value of abs(sum - goal).
Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the original array.
1 <= nums.length <= 40
-107 <= nums[i] <= 107
-109 <= goal <= 109
 */

//

/**
 * Simple brute force approach will be to generate all subsequence sum i.e 2^N but it will be time exceed because N can be 40.
 * So Idea is to divide array in two part and genrate subsets sum array in isolated fashion and that will take 2^N/2 time and space
 * 
 * No sort one array or use Treeset to get the required sum between two arrays
 * 
 * T: (2^N/2)log(2^N/2);
 * 
 * Space: 2^N/2
 * 
 */
class Solution {
    public int minAbsDifference(int[] nums, int goal)
    {
         //splitting the array , nums, into 2 parts
    	int part1[] = new int[nums.length/2];
    	int part2[] = new int[nums.length-part1.length];
    	int k = 0;
    	for(int i=0;i<part1.length;i++)
    	{
    		part1[i] = nums[i];
    		k++;
    	}
    	for(int i=0 ; i<part2.length ; i++)
    	{
    		part2[i] = nums[k];
    		k++;
    	}
    	ArrayList<Integer> sum1=new ArrayList<>(); // stores the sum of all subseq of part1
    	ArrayList<Integer> sum2=new ArrayList<>(); //stores the sum of all subseq of part2
    	//fills sum1 & sum2 ,  i.e add the sum of elements of all possible subseq of part1 into sum1 &
    	//add the sum of elements of all possible subseq of part2 into sum2
        addSum(sum1,part1,0,0);
        addSum(sum2,part2,0,0);
        
           
        int res=Integer.MAX_VALUE;

        TreeSet<Integer> set=new TreeSet<>();
        for(int sum:sum1){
            set.add(sum);
        }

        for(int sum:sum2){

            //sum1+sum2<=goal
            //sum1<=goal-sum2
            //lower bound i.e floor value


            Integer lb=set.floor(goal-sum);

            if(lb!=null){
                res=Math.min(res, Math.abs(lb+sum-goal));
            }


            //sum1+sum2>=goal
            //sum1>=goal-sum2
            //upper bound i.e ceil value
            Integer ub=set.ceiling(goal-sum);

            if(ub!=null){
                res=Math.min(res, Math.abs(ub+sum-goal));
            }
        }
    
        return res;
    }
    private void addSum(ArrayList<Integer> sumlist, int[] part,int sum,int si) 
	{
		if(si>=part.length)
		{
			sumlist.add(sum);
			return;
		}
		//include
		addSum(sumlist,part,sum+part[si],si+1);
		//exclude
		addSum(sumlist,part,sum,si+1);
	}
}




/**
 * 
 * You are given an integer array nums of 2 * n integers. You need to partition nums into two arrays of length n to minimize the absolute difference of the sums of the arrays. 
 * To partition nums, put each element of nums into one of the two arrays.

Return the minimum possible absolute difference.
 */

class Solution {
    public int minimumDifference(int[] nums) {
        int total= 0;
        for(int i : nums){
            total+=i;
        }
        int target = total/2, min = Integer.MAX_VALUE, val = min;

        //This hashmap stores the count of number taken and the list of sum possible with that count
        HashMap<Integer, ArrayList<Integer>> hm1 = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> hm2 = new HashMap<>();
        int half = nums.length/2;
        for(int i = 0 ; i<=half ; i++){
            hm1.put(i,new ArrayList<Integer>());
            hm2.put(i,new ArrayList<Integer>());
        }
        solve(nums,0,0,0,half,hm1);
        solve(nums,half,0,0,nums.length,hm2);



        for(int i = 0 ; i<=half ; i++){

            //get the possible sum list for ith num count
            ArrayList<Integer> list1 = hm1.get(i);

            //get the possible sum list for rest num count
            ArrayList<Integer> list2 = hm2.get(half-i);
            Collections.sort(list1);
            Collections.sort(list2);
            int j = 0 , k = list2.size()-1;

            //No search for the target
            while(j<list1.size() && k>=0){
                int sum = list1.get(j) + list2.get(k);
                if(sum==target){
                    return Math.abs(total-2*sum);
                }else if(sum>target){
                    k--;
                }else{
                    j++;
                }
                int diff = Math.abs(sum-target);
                if(diff<min){
                    min = diff;
                    val = sum;
                }
            }
            
        }
        return Math.abs(total - 2*val);
    }
    
    public void solve(int []nums,int i, int sum, int count, int high, HashMap<Integer, ArrayList<Integer>> hm){
        if(i==high){
            hm.get(count).add(sum);
            return;
        } 
        solve(nums,i+1,sum+nums[i],count+1,high,hm);
        solve(nums,i+1,sum,count,high,hm);
    }
}