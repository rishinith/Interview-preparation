import java.util.*;


//Time complexity: Half of BFS (V+E)/2;
//Space complexity: Double of BFS 2V
class Solution {
    public int bidirectionalBFS(int[][] graph, int start, int end) {
        // Data structures for the origin side
        Queue<Integer> originQueue = new LinkedList<>();
        //Visited collection is not required, we can simply use distance collection. Just for readibility we are adding
        Set<Integer> originVisited = new HashSet<>();
        Map<Integer, Integer> originDistance = new HashMap<>();

        // Data structures for the target side
        Queue<Integer> targetQueue = new LinkedList<>();
        Set<Integer> targetVisited = new HashSet<>();
        Map<Integer, Integer> targetDistance = new HashMap<>();

        originQueue.add(start);
        originVisited.add(start);
        originDistance.put(start, 0);

        targetQueue.add(end);
        targetVisited.add(end);
        targetDistance.put(end, 0);

        while (!originQueue.isEmpty() && !targetQueue.isEmpty()) {
            // Expand the circle of origin
            int currentOrigin = originQueue.poll();
            int originDist = originDistance.get(currentOrigin);
            for (int neighbor : graph[currentOrigin]) {
                if (!originVisited.contains(neighbor)) {
                    originQueue.add(neighbor);
                    originVisited.add(neighbor);
                    originDistance.put(neighbor, originDist + 1);
                }
                if (targetVisited.contains(neighbor)) {
                    return originDist + targetDistance.get(neighbor);
                }
            }

            // Expand the circle of target
            int currentTarget = targetQueue.poll();
            int targetDist = targetDistance.get(currentTarget);
            for (int neighbor : graph[currentTarget]) {
                if (!targetVisited.contains(neighbor)) {
                    targetQueue.add(neighbor);
                    targetVisited.add(neighbor);
                    targetDistance.put(neighbor, targetDist + 1);
                }
                if (originVisited.contains(neighbor)) {
                    return targetDist + originDistance.get(neighbor);
                }
            }
        }

        // No bidirectional path found
        return -1;
    }
}
