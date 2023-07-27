/**
There are n cities connected by some number of flights. 
You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.

You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
 */

/**
 * This solution cant be directly solved by Dijkstra.
The reason is that Dijkstra's algorithm works by selecting the node with the smallest tentative distance from the source, 
and relaxing its adjacent edges to update the distances to its neighboring nodes. However, in this problem,
 we are not only interested in the shortest path from the source to the destination, but we also need to consider the number of stops made along the way.
Dijkstra's algorithm doesn't keep track of the number of stops made during the search, 
so it cannot guarantee that the solution it returns has the desired number of stops or is the cheapest path with at most k stops.
 * 
 */

class Solution {

    int minCost=Integer.MAX_VALUE;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        minCost=Integer.MAX_VALUE;

        Map<Integer,List<int[]>> adj=new HashMap<>();

        for(var flight:flights){
            int s=flight[0];
            int d=flight[1];
            int p=flight[2];
            adj.computeIfAbsent(s,j->new ArrayList<>()).add(new int[]{d,p});
        }

        //dfs(adj,0,k+1,src,dst,new boolean[n+1]);
        bfs(adj,0,k,src,dst,new boolean[n+1]);

        //bellmanFord(flights,k,n,src,dst);

        //return dijkstra(adj,k,n,src,dst);


       return minCost==Integer.MAX_VALUE?-1:minCost;

        
    }


    //Time limit may exceed
    void dfs(Map<Integer,List<int[]>> adj,int cost, int k, int src, int dst, boolean[] visited){
        if(cost>=minCost){
            return;
        }

        if(src==dst){
            minCost=Math.min(cost,minCost);
        }

        if(k==0){
            return;
        }

        if(visited[src]){
            return;
        }


        var edges=adj.get(src);
        if(edges==null){
            return;
        }

        visited[src]=true;
        for(var edge:edges){
            int node=edge[0];
            int wt=edge[1];
            dfs(adj,cost+wt,k-1,node,dst,visited);
        }
        visited[src]=false;

    }

/**
 * Let E be the number of flights and N be the number of cities.

Time complexity: O(N+E⋅K)
Depending on improvements in the shortest distance for each node, we may process each edge multiple times. 
However, the maximum number of times an edge can be processed is limited by K because that is the number of levels we will investigate in this algorithm. 
In the worst case, this takes O(E⋅K) time. We also need O(E) to initialize the adjacency list and O(N) to initialize the dist array.

Space complexity: O(N+E⋅K)
We are processing at most E⋅K edges, so the queue takes up O(E⋅K) space in the worst case. 
We also need O(E) space for the adjacency list and O(N) space for the dist array.
 */
    void bfs(Map<Integer,List<int[]>> adj,int cost, int k, int src, int dst, boolean[] visited){
        Queue<int[]> queue=new LinkedList<>();
        int[] dist=new int[visited.length];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[src]=0;

        int stop=0;
        queue.add(new int[]{src,0});

        while(stop<=k && !queue.isEmpty()){
            int size=queue.size();


            while(size-->0){
                int[] temp=queue.poll();
                int node=temp[0];
                int parentDist=temp[1];
                var edges=adj.get(node);
                if(edges==null){
                    continue;
                }
                for(var edge:edges){
                    if(dist[edge[0]]>parentDist+edge[1]){
                        dist[edge[0]]=parentDist+edge[1];
                        queue.add(new int[]{edge[0], dist[edge[0]]});
                    }
                }
            }

           stop++;
        }

        minCost=dist[dst];
    }

/**
 * Let E be the number of flights and NNN be number of cities.

Time complexity: O((N+E)⋅K)
We are iterating over all the edges K+1  times which takes O(E⋅K) At the start and end of each iteration, we also swap distance arrays, 
which take O(N⋅K time for all the iterations. This gives us a time complexity of O(E⋅K+N⋅K)
Space complexity: O(N)

We are using dist and temp arrays, which each require O(N) space.
 */
    void bellmanFord(int[][] flights, int k, int n, int src, int dst){
        int dist[]=new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE);

        dist[src]=0;

        for(int i=0;i<=k;i++){
            int[] temp=Arrays.copyOf(dist,n);
            for(var flight:flights){
                int s=flight[0];
                int d=flight[1];
                int price=flight[2];
                if(dist[s]!=Integer.MAX_VALUE && temp[d]>dist[s]+price){
                    temp[d]=dist[s]+price;
                }
            }
            dist=temp;
        }

        minCost=dist[dst];
    }


    //This wont work.. Just for representation purpose
    int dijkstra(Map<Integer,List<int[]>> adj,int k, int n, int src, int dst){
                // Initialize min cost and visited arrays
        int[] cost = new int[n];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[src] = 0;
        boolean[] visited = new boolean[n];

        // Initialize priority queue with source node and cost 0
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0}); // node, cost, stops

        // Run Dijkstra's algorithm
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], currCost = curr[1], stops = curr[2];

            // If we reach the destination or have exceeded the maximum stops, we can stop
           // if (node == dst ) break;

            if(stops>k){
                continue;
            }


            // Process neighbors of current node
            if (adj.containsKey(node)) {
                for (int[] neighbor : adj.get(node)) {
                    int nextNode = neighbor[0], nextCost = neighbor[1];
                    if (currCost + nextCost < cost[nextNode] && stops<=k) {
                        cost[nextNode]=currCost + nextCost;
                        pq.offer(new int[]{nextNode, currCost + nextCost, stops + 1});
                    }
                }
            }
        }

        return cost[dst] == Integer.MAX_VALUE ? -1 : cost[dst];
    }


    
}