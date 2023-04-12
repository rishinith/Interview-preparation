/**
1) Build the adjacency list: First, build an adjacency list to represent the graph. 
Each node in the graph will be a key in the map, and its value will be a list of its neighbors.
2) Initialize the indegrees: Initialize an array of indegrees, which will store the number of incoming edges for each node. 
For each node in the graph, iterate over its neighbors and increment their indegrees.
3) Create the queue: Create an empty queue to store nodes with indegree 0.
4) Enqueue the nodes: Iterate over the indegrees array and enqueue all nodes with an indegree of 0.
5) Topological sort: While the queue is not empty, dequeue a node and add it to the sorted list. 
For each of its neighbors, decrement their indegrees. If a neighbor's indegree becomes 0, enqueue it.
6) Check for cycle: If the size of the sorted list is less than the number of nodes in the graph, there is a cycle in the graph. 
Return an empty list or an error message.
7) Return the sorted list: Otherwise, return the sorted list.
*/

public List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, int[] indegrees) {
    List<Integer> sorted = new ArrayList<>();
    Queue<Integer> queue = new LinkedList<>();

    // Enqueue nodes with indegree 0
    for (int i = 0; i < indegrees.length; i++) {
        if (indegrees[i] == 0) {
            queue.add(i);
        }
    }

    while (!queue.isEmpty()) {
        int node = queue.remove();
        sorted.add(node);

        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            indegrees[neighbor]--;

            if (indegrees[neighbor] == 0) {
                queue.add(neighbor);
            }
        }
    }

    // Check for cycle
    if (sorted.size() < graph.size()) {
        return new ArrayList<>();
    }

    return sorted;
}
