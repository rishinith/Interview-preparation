// You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
// The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
// Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.

class Solution {
    public int minCostConnectPoints(int[][] points) {       

        /**
        Prim's algo
        1. Pick up the first vertex
        2. Add all the negibours in the priority queue to sort by weight like (node, weight)
        3. Loop in the queue till its empty or all the nodes are visted
        4. Poll the first edge, mark it visited
        5. Add the new negbours like (node, weight)
        6. Repeat from 3

        Time complexity: ElogV
        Space Complexity: E
         */

        //visted array
         boolean[] visited=new boolean[points.length];
          //to track no of nodes visited
         int noOfNodes=0;
      
         //priority queue to sort the edges by weight
         PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->a[1]-b[1]);
         //adding first vertex
         pq.add(new int[]{0,0});

         int cost=0;

         while(!pq.isEmpty() && noOfNodes<points.length){
             var edge=pq.poll();
             int node=edge[0];
             int weight=edge[1];
             if(visited[node]){
                 continue;
             }else{
                visited[node]=true;
                noOfNodes++;
             }
             cost+=weight;

             for(int i=0;i<points.length;i++){
                  //adding neighbours if not visited
                 if(!visited[i]){
                    //calculating edge weight i.e manhatton distance in this question
                    int dist=Math.abs(points[edge[0]][0]-points[i][0])+Math.abs(points[edge[0]][1]-points[i][1]);
                    pq.add(new int[]{i,dist});
                 }
             }
         }

         return cost;  
    }
}



import java.util.*;

public class Solution {
    public static ArrayList<ArrayList<Integer>> calculatePrimsMST(int n, int m, ArrayList<ArrayList<Integer>> g) {
        List<List<int[]>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (ArrayList<Integer> edge : g) {
            int n1 = edge.get(0) - 1;
            int n2 = edge.get(1) - 1;
            int wt = edge.get(2);
            adjList.get(n1).add(new int[] { n2, wt });
            adjList.get(n2).add(new int[] { n1, wt });
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> e1[2] - e2[2]);

        boolean[] visited = new boolean[n];

        //src, dest, weight
        pq.add(new int[] { 0, 0, 0 });

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        //Time: ElogE
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int src = edge[0];
            int dest = edge[1];
            int weight = edge[2];

            if (visited[dest]) {
                continue;
            }

            visited[dest] = true;

            if (src != dest) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(src + 1);
                list.add(dest + 1);
                list.add(weight);
                result.add(list);
            }

            for (int[] nei : adjList.get(dest)) {
                int nextDest = nei[0];
                int nextWeight = nei[1];
                if (!visited[nextDest]) {
                    pq.add(new int[] { dest, nextDest, nextWeight });
                }
            }
        }

        return result;
    }
}
