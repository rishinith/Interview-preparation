/**
 * Minimum Cost to Cut a Stick

Given a wooden stick of length n units. The stick is labelled from 0 to n. For example, a stick of length 6 is labelled as follows:
Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.
You should perform the cuts in order, you can change the order of the cuts as you wish.
The cost of one cut is the length of the stick to be cut, the total cost is the sum of costs of all cuts. 
When you cut a stick, it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick before the cut). 
Please refer to the first example for a better explanation.

Return the minimum total cost of the cuts.

Input: n = 7, cuts = [1,3,4,5]
Output: 16
Explanation: Using cuts order = [1, 3, 4, 5] as in the input leads to the following scenario:

The first cut is done to a rod of length 7 so the cost is 7. 
The second cut is done to a rod of length 6 (i.e. the second part of the first cut), the third is done to a rod of length 4 and the last cut is to a rod of length 3.
The total cost is 7 + 6 + 4 + 3 = 20.
Rearranging the cuts to be [3, 5, 1, 4] for example will lead to a scenario with total cost = 16 (as shown in the example photo 7 + 4 + 3 + 2 = 16).
 */

class Solution {
    public int minCost(int n, int[] cuts) {

        Arrays.sort(cuts);

        List<Integer> cutList=new ArrayList<>();
        for(var cut:cuts){
            cutList.add(cut);
        }
        
        cutList.add(0);
        cutList.add(n);

        Collections.sort(cutList);

        int[][] dp=new int[cutList.size()][cutList.size()];
        for(var row: dp){
            Arrays.fill(row, -1);
        }


        for(int i=cutList.size()-1;i>=0;i--){
            for(int j=0;j<cutList.size();j++){
                if(i>=j){
                    continue;
                }
                int minCost=Integer.MAX_VALUE;
                for(int mid=i+1;mid<j;mid++){
                    int cost=cutList.get(j)-cutList.get(i)+dp[i][mid]+dp[mid][j];
                    minCost=Math.min(cost,minCost);
                }
                dp[i][j]=minCost==Integer.MAX_VALUE?0:minCost;
            }
        }

        return dp[0][cutList.size()-1];

        //return minCost(0,cutList.size()-1,cutList, dp);
        
    }

    int minCost(int left, int right, List<Integer> cutList, int[][] dp){
        if(left>=right){
            return 0;
        }

        if(dp[left][right]!=-1){
            return dp[left][right];
        }

        int minCost=Integer.MAX_VALUE;
        for(int i=left+1;i<right;i++){
                int cost=cutList.get(right)-cutList.get(left)
                         +minCost(left, i, cutList, dp) + minCost(i, right, cutList, dp);

                minCost=Math.min(minCost, cost);
            }

        if(minCost==Integer.MAX_VALUE){
            minCost=0;
        }


        return dp[left][right]=minCost;
    }
}