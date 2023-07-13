/**
 * A component is called a Strongly Connected Component(SCC) only if for every possible pair of vertices (u, v) inside that component,
 *  u is reachable from v and v is reachable from u.
 * 
 * 
 * Kosa Raju Algo
 * 
 * Approach:
 * 1) Sort all the nodes according to their finishing time[ DFS Topo Sort]
 * 2) Reverse all the edges of the entire graph:
 * 3) Perform the DFS and count the no. of different DFS calls to get the no. of SCC:
 */

import java.util.*;
public class Solution {

    public static List<List<Integer>> stronglyConnectedComponents(int n, int[][] edges) {

        List<List<Integer>> adjList=new ArrayList<>();
        for(int i=0;i<n;i++){
            adjList.add(new ArrayList<>());
        }
        for(int[] edge:edges){
            adjList.get(edge[0]).add(edge[1]);
        }
       
       Stack<Integer> topoStack=new Stack<>();
       boolean[] visited=new boolean[n];

        //topoSort
       for(int i=0;i<n;i++){
           if(!visited[i]){
               dfs(i, topoStack, visited, adjList);
           }
       }

        List<List<Integer>> reversedAdjList=new ArrayList<>();
        for(int i=0;i<n;i++){
            reversedAdjList.add(new ArrayList<>());
        }

        //graph transpose
        for(int i=0;i<n;i++){
            for(int nei:adjList.get(i)){
                reversedAdjList.get(nei).add(i);
            }
        }

        //DFS again
        List<List<Integer>> result=new ArrayList<>();
        Arrays.fill(visited, false);
        while(!topoStack.isEmpty()){
            int i=topoStack.pop();
            if(!visited[i]){
                List<Integer> connectedComponents=new ArrayList<>();
                Stack<Integer> stack=new Stack<>();
                dfs(i, stack, visited, reversedAdjList);
                while(!stack.isEmpty()) connectedComponents.add(stack.pop());
                result.add(connectedComponents);
            }
        }
       
        return result;

    }

    static void dfs(int node,  Stack<Integer> topoStack, boolean[] visited, List<List<Integer>> adjList){
        visited[node]=true;

        for(int nei:adjList.get(node)){
            if(!visited[nei]){
                dfs(nei, topoStack, visited, adjList);
            }
        }

        topoStack.add(node);
    }
}