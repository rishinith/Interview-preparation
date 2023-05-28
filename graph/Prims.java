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
