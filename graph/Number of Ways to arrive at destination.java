/**
 * 1976. Number of Ways to Arrive at Destination
You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.

You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.

Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 109 + 7.
 */

// Dijkstra 
class Solution {
    int mod = 1_000_000_007;
    public int countPaths(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n];
        
        for(int i = 0; i < n; i++)
            graph[i] = new ArrayList<int[]>();
        
        for(int[] edge: roads)
        {
            int src = edge[0], dest = edge[1], time = edge[2];
            
            graph[src].add(new int[]{dest, time});
            graph[dest].add(new int[]{src, time});
        }
        
        return shortestPath(graph, 0, n);
    }
    
    private int shortestPath(List<int[]>[] graph, int src, int target)
    {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        
        int[] minCost = new int[target];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        
        long[] ways = new long[target];
        ways[0] = 1;
        minCost[0] = 0;
        
        pq.offer(new int[]{0, 0});
        
        while(!pq.isEmpty())
        {
            int[] current = pq.poll();
            int city = current[0];
            int curCost = current[1];
            
            if(curCost > minCost[city]) 
                continue;
            
            for(int[] neighbourData: graph[city])
            {
                int neighbour = neighbourData[0], time = neighbourData[1];
                
                if(curCost + time < minCost[neighbour])
                {
                    minCost[neighbour] = curCost + time;
                    pq.offer(new int[]{neighbour, minCost[neighbour]});
                    ways[neighbour] = ways[city];
                }
                else if(curCost + time == minCost[neighbour])
                    ways[neighbour] = (ways[neighbour] + ways[city]) % mod;
            }
        }
        return (int)ways[target - 1];
    }
}