/**
 * 802. Find Eventual Safe States
There is a directed graph of n nodes with each node labeled from 0 to n - 1. 
The graph is represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, 
meaning there is an edge from node i to each node in graph[i].

A node is a terminal node if there are no outgoing edges. 
A node is a safe node if every possible path starting from that node leads to a terminal node (or another safe node).

Return an array containing all the safe nodes of the graph. The answer should be sorted in ascending order.

Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Explanation: The given graph is shown above.
Nodes 5 and 6 are terminal nodes as there are no outgoing edges from either of them.
Every path starting at nodes 2, 4, 5, and 6 all lead to either node 5 or 6.

Input: graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]
Output: [4]
Explanation:
Only node 4 is a terminal node, and every path starting at node 4 leads to node 4.
 */

/**
 * 
 * The crux of the problem is whether you reach a cycle or not when you start a node.
 * If yes, it cant be safe node.
 * This can be done either using DFS to cheeck cycles or Toplogical sort by reversing the edges
 */


class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        

        Set<Integer> visited=new HashSet<>();
        Set<Integer> recursiveStack=new HashSet<>();

        Set<Integer> result=new TreeSet<>();

        for(int i=0;i<graph.length;i++){
            if(!visited.contains(i)){
                checkForCycles(i,visited, recursiveStack, result, graph);
            }
        }

        return new ArrayList<>(result);

    }


    boolean checkForCycles(int node, Set<Integer> visited, Set<Integer> recursiveStack, Set<Integer> result, int[][] graph){

        visited.add(node);
        recursiveStack.add(node);


        for(var child:graph[node]){
     
            if(!visited.contains(child)){
                if(checkForCycles(child,visited, recursiveStack, result, graph)){
                    return true;
                }
            }
            else if(recursiveStack.contains(child)){
               return true;
            }
        }
      
        result.add(node);
        recursiveStack.remove(node);
        return false;
    }

}

///////////////////////////////////////TOPOLOGICAL SORT///////////////////


   List<Integer> reverseToplologicalSort(int[][] graph){

       //Adj List to save reverse graph
       List<Set<Integer>> reverseGraph=new ArrayList<>();
       List<Integer> safeList=new ArrayList<>();
       int[] indegrees=new int[graph.length];
       Queue<Integer> queue=new LinkedList<>();

        for(int i=0;i<graph.length;i++){
            reverseGraph.add(new HashSet<>());
        }

        for(int i=0;i<graph.length;i++){
            //No outgoing edges so on reverse no incoming edges
            if(graph[i].length==0){
                queue.add(i);
            }
           for(var child:graph[i]){
               reverseGraph.get(child).add(i);
               //saving the indegree.. we are reversing the edges so indegree here will be of actual parent
               indegrees[i]++;
           }
        }


        while(!queue.isEmpty()){
            int node=queue.poll();
            safeList.add(node);

            for(var child:reverseGraph.get(node)){
                indegrees[child]--;
                if(indegrees[child]==0){
                    queue.offer(child);
                }

            }
        }

       Collections.sort(safeList);
       return safeList;

    }





////////////////////////////////////////////////////////////////////////////



//Dont implement that.. This was the first version during practice
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        

        Set<Integer> visited=new HashSet<>();
        Set<Integer> recursiveStack=new HashSet<>();

        Set<Integer> result=new TreeSet<>();

        for(int i=0;i<graph.length;i++){
            if(!visited.contains(i)){
                checkForSafeNodes(i,visited, recursiveStack, result, graph);
            }
        }

        return new ArrayList<>(result);

    }


    boolean checkForSafeNodes(int node, Set<Integer> visited, Set<Integer> recursiveStack, Set<Integer> result, int[][] graph){

        visited.add(node);
        recursiveStack.add(node);


        boolean safe=true;
        for(var child:graph[node]){

            //self edges
            if(child==node){
                safe=false;
                break;
            }
            //not visited
            else if(!visited.contains(child)){
                safe&=checkForSafeNodes(child,visited, recursiveStack, result, graph);
                if(!safe){
                    return false;
                }
            }
            else if (visited.contains(child) && !result.contains(child)){
                safe=false;
                break;
            }
            else if(recursiveStack.contains(child)){
                safe=false;
                break;
            }
        }
        if(safe){
            result.add(node);
        }
        recursiveStack.remove(node);
        return safe;
    }

}