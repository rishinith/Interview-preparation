/**
 *
 * A* is an informed search algorithm or best-first search.
It prioritizes the steps that are the most promising, making the best and informed decision on the next steps.
A* uses a function f(n) = g(n) + h(n) to evaluate each potential step.
g(n) is the cost to reach a specific step n.
h(n) is a heuristic estimation of the cost to reach the destination from the step n. In grid it can be mahnatton distnace i.e abs(r1-r2) + abs(c1-c2)
f(n) is the estimated total cost to reach the destination if one takes the step n.
The heuristic function h(n) should be admissible, meaning it never overestimates the cost.
A* algorithm guarantees to find the shortest path if the heuristic function is admissible.
A* is commonly used in pathfinding and navigation applications, such as GPS navigation, game development, and robotics.
 */

/**
 * You're given a two-dimensional array containing 0s and
1s, where each 0 represents a free space and each
1 represents an obstacle (a space that cannot be passed through).
You can think of this array as a grid-shaped graph. You're also given four
integers startRow, startCol, endRow,
and endCol, representing the positions of a start node and an end
node in the graph.


Write a function that finds the shortest path between the start node and the
end node using the A* search algorithm and returns it.


The shortest path should be returned as an array of node positions, where each
node position is an array of two elements: the [row, col] of the
respective node in the graph. The output array should contain the start node's
position, the end node's position, and all of the positions of the remaining
nodes in the shortest path, and these node positions should be ordered from
start node to end node.


If there is no path from the start node to the end node, your function should
return an empty array.
 */

import java.util.*;

class Node {
    int row;
    int col;
    int distanceFromStart;
    int estimatedDistanceToEnd;
    Node cameFrom;
    String id;

    Node(int row, int col) {
        this.row = row;
        this.col = col;
        distanceFromStart = Integer.MAX_VALUE;
        estimatedDistanceToEnd = Integer.MAX_VALUE;
        id = row + "-" + col;
    }
}

class Program {
    private int[] xSteps = {-1, 1, 0, 0};
    private int[] ySteps = {0, 0, -1, 1};

    public int[][] aStarAlgorithm(int startRow, int startCol, int endRow, int endCol, int[][] graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.estimatedDistanceToEnd));
        Node startNode = new Node(startRow, startCol);
        startNode.distanceFromStart = 0;
        startNode.estimatedDistanceToEnd = getManhattanDistance(startRow, endRow, startCol, endCol);
        pq.add(startNode);
        Set<String> visited = new HashSet<>();
        Node currentNode;
        while (!pq.isEmpty()) {
            currentNode = pq.poll();
            if (currentNode.row == endRow && currentNode.col == endCol) {
                return reconstructPath(currentNode);
            }
            visited.add(currentNode.id);
            for (int i = 0; i < xSteps.length; i++) {
                int row = currentNode.row + xSteps[i];
                int col = currentNode.col + ySteps[i];
                //invalid neigbours
                if (row >= graph.length || row < 0 || col >= graph[0].length || col < 0) {
                    continue;
                }
                //obstacles or neigbours already visted
                if (graph[row][col] == 1 || visited.contains(row + "-" + col)) {
                    continue;
                }
                Node childNode = new Node(row, col);
                childNode.distanceFromStart = currentNode.distanceFromStart + 1;
                childNode.estimatedDistanceToEnd = childNode.distanceFromStart + getManhattanDistance(row, endRow, col, endCol);
                childNode.cameFrom = currentNode;
                pq.add(childNode);
            }
        }
        return new int[0][];
    }

    private int[][] reconstructPath(Node endNode) {
        List<int[]> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(new int[]{currentNode.row, currentNode.col});
            currentNode = currentNode.cameFrom;
        }
        Collections.reverse(path);
        return path.toArray(new int[path.size()][]);
    }

    private int getManhattanDistance(int r1, int r2, int c1, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
}
