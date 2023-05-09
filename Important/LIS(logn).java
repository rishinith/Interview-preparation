/**
 * 1Find the Longest Valid Obstacle Course at Each Position

You want to build some obstacle courses. You are given a 0-indexed integer array obstacles of length n, where obstacles[i] describes the height of the ith obstacle.

For every index i between 0 and n - 1 (inclusive), find the length of the longest obstacle course in obstacles such that:

You choose any number of obstacles between 0 and i inclusive.
You must include the ith obstacle in the course.
You must put the chosen obstacles in the same order as they appear in obstacles.
Every obstacle (except the first) is taller than or the same height as the obstacle immediately before it.
Return an array ans of length n, where ans[i] is the length of the longest obstacle course for index i as described above

Input: obstacles = [3,1,5,6,4,2]
Output: [1,1,2,3,2,2]
Explanation: The longest valid obstacle course at each position is:
- i = 0: [3], [3] has length 1.
- i = 1: [3,1], [1] has length 1.
- i = 2: [3,1,5], [3,5] has length 2. [1,5] is also valid.
- i = 3: [3,1,5,6], [3,5,6] has length 3. [1,5,6] is also valid.
- i = 4: [3,1,5,6,4], [3,4] has length 2. [1,4] is also valid.
- i = 5: [3,1,5,6,4,2], [1,2] has length 2.
 */

/**
 * LIS problem, it can be done in n^2 but it can be done in nlogn also
 * If you have forgot the implementation, check https://www.youtube.com/watch?v=on2hvxBXJH4&ab_channel=takeUforward
 */
class Solution {

    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {

        int n=obstacles.length; 
        List<Integer> lisSeq=new ArrayList<>();
        int answer[] =new int[n];

        answer[0]=1;
        lisSeq.add(obstacles[0]);
        for(int i=1;i<n;i++){
            int height=obstacles[i];
            if(height>=lisSeq.get(lisSeq.size()-1)){
                answer[i]=lisSeq.size()+1;
                lisSeq.add(height);
            }else{
                //find the element which is just greater than the num, listSeq is already sorted so binary search can be done
                int idx=binarySearch(lisSeq,height);
                //idx represents the index, to get the size till idx add 1
                answer[i]=idx+1;
                //update the new height in lis sequence at idx
                lisSeq.set(idx,height);
            }

        }   
        return answer;     
    }

int binarySearch(List<Integer> list, int num){
    int left=0;
    int right=list.size()-1;
    while(left<right){
        int mid=(left+right)/2;
        if(list.get(mid)<=num){
            left=mid+1;
        }else{
            right=mid;
        }
    }

    return left;
}

}