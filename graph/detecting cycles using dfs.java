/**

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

Time complexity: E +V
Space complexity: E + V
**/

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Create adjacency list to represent the graph
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int prerequisiteCourse = prerequisite[1];
            adjList.get(course).add(prerequisiteCourse);
        }

        // Initialize visited and recursion stack arrays
        boolean[] visited = new boolean[numCourses];
        boolean[] recStack = new boolean[numCourses];

        // Perform DFS on each unvisited vertex
        for (int i = 0; i < numCourses; i++) {
            if (!visited[i] && dfs(i, adjList, visited, recStack)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfs(int v, List<List<Integer>> adjList, boolean[] visited, boolean[] recStack) {
        visited[v] = true;
        recStack[v] = true;

        for (int neighbor : adjList.get(v)) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, adjList, visited, recStack)) {
                    return true;
                }
            } else if (recStack[neighbor]) {
                return true;
            }
        }

        recStack[v] = false;
        return false;
    }
}
