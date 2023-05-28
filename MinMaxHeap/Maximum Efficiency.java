/**
 * 1383. Maximum Performance of a Team
You are given two integers n and k and two integer arrays speed and efficiency both of length n. There are n engineers numbered from 1 to n. 
speed[i] and efficiency[i] represent the speed and efficiency of the ith engineer respectively.
Choose at most k different engineers out of the n engineers to form a team with the maximum performance.
The performance of a team is the sum of their engineers' speeds multiplied by the minimum efficiency among their engineers.
Return the maximum performance of this team. Since the answer can be a huge number, return it modulo 109 + 7.

 

Example 1:
Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
Output: 60
Explanation: 
We have the maximum performance of the team by selecting engineer 2 (with speed=10 and efficiency=4) 
and engineer 5 (with speed=5 and efficiency=7). That is, performance = (10 + 5) * min(4, 7) = 60.

Example 2:
Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
Output: 68
Explanation:
This is the same example as the first but k = 3. We can select engineer 1, engineer 2 and engineer 5
 to get the maximum performance of the team. That is, performance = (2 + 10 + 5) * min(5, 4, 7) = 68.
 */

//T: N (logN + logK)  S: N+K
class Solution {
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int modulo = (int) Math.pow(10, 9) + 7;
        List<int[]> candidates = new ArrayList<>();

        // Prepare a combined list of efficiency and speed for each engineer
        for (int i = 0; i < speed.length; i++) {
            candidates.add(new int[]{efficiency[i], speed[i]});
        }

        // Sort the candidates based on their efficiency in decreasing order
        Collections.sort(candidates, (a, b) -> b[0] - a[0]);

        // Priority queue will store the valid k candidates with efficiency greater than the current candidate
        // The priority queue will ensure the current candidate is always the minimum efficient
        PriorityQueue<Integer> speedHeap = new PriorityQueue<>((a, b) -> a - b);

        long speedSum = 0; // To keep track of the sum of speeds of selected engineers
        long maxPerformance = 0; // To store the maximum performance achieved

        // Iterate over each candidate (engineer) starting from the most efficient
        for (var candidate : candidates) {
            int currEfficiency = candidate[0];
            int currSpeed = candidate[1];

            if (speedHeap.size() > k - 1) {
                // If the number of selected engineers exceeds k, remove the one with the lowest speed
                speedSum -= speedHeap.poll();
            }

            // Add the current engineer's speed to the heap and update the speed sum
            speedHeap.add(currSpeed);
            speedSum += currSpeed;

            // Calculate the performance with the current candidate as the minimum efficient
            maxPerformance = Math.max(maxPerformance, speedSum * currEfficiency);
        }

        // Return the maximum performance modulo 10^9 + 7
        return (int) (maxPerformance % modulo);
    }
}
