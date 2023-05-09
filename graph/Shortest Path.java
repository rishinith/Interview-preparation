/**
 * Given a Directed Acyclic Graph of N vertices from 0 to N-1 and a 2D Integer array(or vector) edges[ ][ ] of length M, 
 * where there is a directed edge from edge[i][0] to edge[i][1] with a distance of edge[i][2] for all i, 0<=i

Find the shortest path from src(0) vertex to all the vertices and if it is impossible to reach any vertex, then return -1 for that vertex.
 */

/**
 * In case of Directed Acyclic Graph(DAG) shortest path for all the nodes from given source can be found in V+E fashion(like BFS or DFS) 
 * i.e faster than Dikshtra's and Bellman ford
 */
class Solution {

	public int[] shortestPath(int N,int M, int[][] edges, int source) {
	
	    List<List<int[]>> adjList=new ArrayList<>();
	    for(int i=0;i<N;i++){
	        adjList.add(new ArrayList<>());
	    }
	    
	    for(var edge:edges){
	        adjList.get(edge[0]).add(new int[]{edge[1],edge[2]});
	    }
	    
	    int dist[]=new int[N];
	    Arrays.fill(dist, Integer.MAX_VALUE);
	    
	    dist[0]=0;
	    
	    var topoStack=new Stack<Integer>();

        //all the nodes thats reachable from source will be added in stack in topological fashion
	    dfs(source,topoStack,new boolean[N], adjList);	    
	    
        //Simply iterate the stack in toplological fashion and update the minimum distances
	    while(!topoStack.isEmpty()){
	        var node=topoStack.pop();
	        if(dist[node]==Integer.MAX_VALUE){
	            continue;
	        }
	        for(var edge:adjList.get(node)){
	            dist[edge[0]]=Math.min(dist[node]+edge[1],dist[edge[0]]);
	        }
	        
	    }
	 
	    for(int i=0;i<N;i++){
	        if(dist[i]==Integer.MAX_VALUE){
	            dist[i]=-1;
	        }
	    }
	    return dist;
	}
	
	
	void dfs(int node, Stack<Integer> stack, boolean[] visited, List<List<int[]>> adjList){
	    visited[node]=true;
	    
	    for(var edge:adjList.get(node)){
	        if(!visited[edge[0]]){
	            dfs(edge[0],stack,visited,adjList);
	        }
	    }
	    
	    
	    stack.push(node);
	}
	
	


//Dikshtra Time: N(for creating distance array)+ElogV           Space: O(E)+O(N)


    void dijkstra(int[] signalReceivedAt,int k,int n){
        PriorityQueue<int[]> queue=new PriorityQueue<>((a,b)-> a[1]-b[1]);

        signalReceivedAt[k] = 0;
        queue.add(new int[]{k,0});

        int[] visited=new int[n+1];
        

        while(!queue.isEmpty()){

            var currNode=queue.poll();

            if(visited[currNode[0]]==1){
                continue;
            }

            visited[currNode[0]]=1;

            var children=adj.getOrDefault(currNode[0], new ArrayList<>());



            for(var child:children){
                int time=child[1];
                int node=child[0];

                if(visited[node]==0 && signalReceivedAt[node]>signalReceivedAt[currNode[0]]+time){
                    signalReceivedAt[node]=signalReceivedAt[currNode[0]]+time;
                    queue.add(new int[]{node,signalReceivedAt[node]});
                }
                
                 
            }
        }
    }


    
    void dijkstra2(int[] signalReceivedAt,int k,int n){

        signalReceivedAt[k] = 0;

        PriorityQueue<Integer> queue=new PriorityQueue<>((a,b)-> signalReceivedAt[a]-signalReceivedAt[b]);

        queue.add(k);

        //int[] visited=new int[n+1];
        

        while(!queue.isEmpty()){

            var currNode=queue.poll();

            var children=adj.getOrDefault(currNode, new ArrayList<>());

            for(var child:children){
                int time=child[1];
                int node=child[0];

                if(signalReceivedAt[node]>signalReceivedAt[currNode]+time){
                    signalReceivedAt[node]=signalReceivedAt[currNode]+time;
                    queue.add(node);
                }
                
                 
            }
        }
    }



//Dikshtra 's with shortest path
	
/**
 * Finding the shortest path along with greedy dikhtra also.
 * Simple parent array to track the parent and the ultimately backtrack to get the original path
 */
class Solution {
    public static List<Integer> shortestPath(int n, int m, int edges[][]) {
        List<List<int[]>> adjList=new ArrayList<>();
        
        for(int i=0;i<=n;i++){
            adjList.add(new ArrayList<>());
        }
        
        for(var edge:edges){
            adjList.get(edge[0]).add(new int[]{edge[1],edge[2]});
            adjList.get(edge[1]).add(new int[]{edge[0],edge[2]});
        }
        
        int[] parent=new int[n+1];
        
        dikshtra(adjList,parent, 1);
        //System.out.println("hey"+Arrays.toString(parent));

        if(parent[n]==-1){
            return Arrays.asList(-1);
        }
        
        var result=new ArrayList<Integer>();
        
        int currentIndex=n;
        while(currentIndex!=-1){
            result.add(0,currentIndex);
            currentIndex=parent[currentIndex];
        }
        
        return result;
        
        
    }
    
    static void dikshtra(List<List<int[]>> adjList,int[] parent, int src){
        
        Arrays.fill(parent,-1);
        
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->a[1]-b[1]);
        int dist[]=new int[parent.length];
        Arrays.fill(dist,Integer.MAX_VALUE);
        
        dist[src]=0;
        
        pq.add(new int[]{src,0});
        
        while(!pq.isEmpty()){
            var edge=pq.poll();
            var node=edge[0];
            var d=edge[1];
            
            for(var nei:adjList.get(node)){
                //System.out.println("hey"+node+"-"+Arrays.toString(nei));
                if(d+nei[1]<dist[nei[0]]){
                    dist[nei[0]]=d+nei[1];
                    parent[nei[0]]=node;
                    pq.add(new int[]{nei[0], dist[nei[0]]});
                }
            }
            
        }
         //System.out.println(Arrays.toString(dist));
    }
}'

/**
 * 1631. Path With Minimum Effort

You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, 
where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), 
and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, 
and you wish to find a route that requires the minimum effort.

A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.

Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

Modified Dijkstra
**/

class Solution {
    int directions[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length;
        int col = heights[0].length;
        int[][] differenceMatrix = new int[row][col];
        for (int[] eachRow : differenceMatrix)
            Arrays.fill(eachRow, Integer.MAX_VALUE);
        differenceMatrix[0][0] = 0;
        PriorityQueue<Cell> queue = new PriorityQueue<Cell>((a, b) -> (a.difference.compareTo(b.difference)));
        boolean[][] visited = new boolean[row][col];
        queue.add(new Cell(0, 0, differenceMatrix[0][0]));

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            visited[curr.x][curr.y] = true;
            if (curr.x == row - 1 && curr.y == col - 1)
                return curr.difference;
            for (int[] direction : directions) {
                int adjacentX = curr.x + direction[0];
                int adjacentY = curr.y + direction[1];
                if (isValidCell(adjacentX, adjacentY, row, col) && !visited[adjacentX][adjacentY]) {
                    int currentDifference = Math.abs(heights[adjacentX][adjacentY] - heights[curr.x][curr.y]);
                    int maxDifference = Math.max(currentDifference, differenceMatrix[curr.x][curr.y]);
                    if (differenceMatrix[adjacentX][adjacentY] > maxDifference) {
                        differenceMatrix[adjacentX][adjacentY] = maxDifference;
                        queue.add(new Cell(adjacentX, adjacentY, maxDifference));
                    }
                }
            }
        }
        return differenceMatrix[row - 1][col - 1];
    }

    boolean isValidCell(int x, int y, int row, int col) {
        return x >= 0 && x <= row - 1 && y >= 0 && y <= col - 1;
    }
}

class Cell {
    int x;
    int y;
    Integer difference;

    Cell(int x, int y, Integer difference) {
        this.x = x;
        this.y = y;
        this.difference = difference;
    }
}