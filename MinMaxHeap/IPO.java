/**
Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.

Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

 

Example 1:
Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
Output: 4
Explanation: Since your initial capital is 0, you can only start the project indexed 0.
After finishing it you will obtain profit 1 and your capital becomes 1.
With capital 1, you can either start the project indexed 1 or the project indexed 2.
Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.


Example 2:
Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
Output: 6
**/

class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {

        PriorityQueue<Integer> minHeap=new PriorityQueue<>((a,b)->Integer.compare(capital[a],capital[b]));
        PriorityQueue<Integer> maxHeap=new PriorityQueue<>((a,b)->Integer.compare(profits[b],profits[a]));

        int index=0;

        //minHeap will contain min capital on top
        while(index<capital.length){
            minHeap.add(index);
            index++;
        }

        int t=0;

        while(t<k){

            //pull all the projects that I can do and add in maxHeap to get max Profit in that project
            while(!minHeap.isEmpty() && capital[minHeap.peek()]<=w){
                maxHeap.add(minHeap.poll());
            }

            //No projects to choose now, capital is exhausted
            if(maxHeap.isEmpty()){
                break;
            }

            //add the profit
            w+=profits[maxHeap.poll()];
            t++;
        }


        return w;
        
    }
}