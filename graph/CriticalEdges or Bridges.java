/**
 * There are n servers numbered from 0 to n - 1 connected by undirected server-to-server connections forming a network where connections[i] = [ai, bi] 
 * represents a connection between servers ai and bi. Any server can reach other servers directly or indirectly through the network.

A critical connection is a connection that, if removed, will make some servers unable to reach some other server.

Return all critical connections in the network in any order.
 */

//T: V+E (single dfs) [because all the nodes are connected is mentioned in the question]
//S: V+E
//Tarzan's algo
class Solution {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // Initialize the result list to store critical edges
        List<List<Integer>> result=new ArrayList<>();
        // If connections is empty or null, return the empty result
        if(connections==null || connections.size()==0){
            return result;
        }
        // Get the adjacency list representation of the graph
        Map<Integer,List<Integer>> adjMap=getGraph(n,connections);
        // Initialize the arrival times array for DFS
        var arrivalTimes=new int[n];
        Arrays.fill(arrivalTimes,-1);

        // Perform DFS on every unvisited node
        for(int i=0;i<n;i++){
            if(arrivalTimes[i]==-1){
                dfs(i,-1,0,adjMap,arrivalTimes,result);
            }
        }

        // Return the result list of critical edges
        return result;
    }


void dfs(int currentNode, int parent, int currentTime, Map<Integer, List<Integer>> adjMap, int[] arrivalTimes, List<List<Integer>> result) {
    // Set the arrival time of the current node
    arrivalTimes[currentNode] = currentTime;
    // Initialize the minimum arrival time of the current node
    int minArrivalTime = currentTime;

    // Traverse the neighbors of the current node
    for (var destination : adjMap.get(currentNode)) {
        // Skip the parent node
        if (destination == parent) {
            continue;
        }
        // If the neighbor is unvisited, perform DFS on it
        if (arrivalTimes[destination] == -1) {
            dfs(destination, currentNode, currentTime + 1, adjMap, arrivalTimes, result);
            // Update the minimum arrival time of the current node
            minArrivalTime = Math.min(minArrivalTime, arrivalTimes[destination]);
            // Check if the edge is critical
            if (arrivalTimes[currentNode] < arrivalTimes[destination]) {
                // (currentNode, destination) is a critical edge
                result.add(Arrays.asList(currentNode, destination));
            }
        } else {
            // If the neighbor is already visited, update the minimum arrival time of the current node
            minArrivalTime = Math.min(minArrivalTime, arrivalTimes[destination]);
        }
    }

    // Update the arrival time of the current node to be the minimum arrival time of its neighbors
    arrivalTimes[currentNode]=minArrivalTime;
}


Map<Integer,List<Integer>> getGraph(int n, List<List<Integer>> connections){
        Map<Integer,List<Integer>> adjMap=new HashMap<>();
        for(int i=0;i<n;i++){
            adjMap.put(i,new ArrayList<>());
        }

        for(var connection:connections){
            adjMap.get(connection.get(0)).add(connection.get(1));
            adjMap.get(connection.get(1)).add(connection.get(0));
        }

        return adjMap;
}



//Articulation Point
/**
 * Given an undirected connected graph with V vertices and adjacency list adj. You are required to find all the vertices removing which (and edges through it) 
 * disconnects the graph into 2 or more components.
Note: Indexing is zero-based i.e nodes numbering from (0 to V-1). There might be loops present in the graph.
 */

class Solution
{
    public ArrayList<Integer> articulationPoints(int V,ArrayList<ArrayList<Integer>> adj)
    {
        
        //using set to store the result because answers can repeat themselves
        Set<Integer> result=new TreeSet<>();
        int[] arrival=new int[V];
        Arrays.fill(arrival, -1);
        
        for(int i=0;i<V;i++){
            dfs(i, adj, -1, result, arrival, 0);
        }
       
        if(result.size()==0){
            result.add(-1);
        }
        return new ArrayList<>(result);
    }
    
    void dfs(int v, ArrayList<ArrayList<Integer>> adj, int parent, Set<Integer> result, int[] arrival, int currentTime){
        if(arrival[v]!=-1){
            return;
        }

        arrival[v]=currentTime;
        
        
        int minArrivalTime=currentTime;
        
        int child=0;
        for(int nei:adj.get(v)){
            
            if(parent==nei){
                continue;
            }
            if(arrival[nei]!=-1){
                minArrivalTime=Math.min(minArrivalTime, arrival[nei]);
            }
            else{
                dfs(nei, adj, v, result, arrival, currentTime+1);
                minArrivalTime=Math.min(minArrivalTime, arrival[nei]);

                //This differs from above finding the bridges implementation here we are using >=, also avoiding starting node here with parent!=-1
                if(arrival[nei]>=arrival[v] && parent!=-1){
                    result.add(v);
                }
                child++;
            }
        }
        
        arrival[v]=minArrivalTime;
        
        //special handling for starting nodes
        if(parent==-1 && child>1){
            result.add(v);
        }
        
    }
}