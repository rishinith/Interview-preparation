/**
 * You are given an m x n grid grid where:

'.' is an empty cell.
'#' is a wall.
'@' is the starting point.
Lowercase letters represent keys.
Uppercase letters represent locks.
You start at the starting point and one move consists of walking one space in one of the four cardinal directions. You cannot walk outside the grid, or walk into a wall.

If you walk over a key, you can pick it up and you cannot walk over a lock unless you have its corresponding key.

For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English alphabet in the grid. 
This means that there is exactly one key for each lock, and one lock for each key; and also that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.

Return the lowest number of moves to acquire all keys. If it is impossible, return -1.
 */

class Solution {
    int[][] dirs = { {1,0} , {0,1} , {0,-1} , {-1,0} };

    // Node class to represent a position on the grid with its row, column, and current key combination
    class Node {
        int row, col;
        String key;

        public Node(int row, int col, String key) {
            this.row = row;
            this.col = col;
            this.key = key;
        }

        public String toString(int r, int c, String k) {
            return r + "-" + c + "-" + k;
        }
    }

    public int shortestPathAllKeys(String[] grid) {
        Queue<Node> q = new LinkedList<>();
        int rows = grid.length;
        int col = grid[0].length();
        int keyCount = 0;
        HashSet<String> visited = new HashSet<>();

        // Iterate over the grid to find the starting position and count the number of keys
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                char ch = grid[i].charAt(j);
                if (ch == '@') {
                    q.offer(new Node(i, j, ""));
                }
                if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'f') {
                    keyCount++;
                }
            }
        }


        //Modified bfs[ Visited part is modified by adding key state also]
        int steps = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int x = 0; x < size; x++) {
                Node temp = q.poll();
                int i = temp.row;
                int j = temp.col;
                String key = temp.key;

                // Check if the current position with the key combination has been visited before
                if (!visited.contains(temp.toString(i, j, key))) {
                    visited.add(temp.toString(i, j, key));
                } else {
                    continue; // Skip this position if it has already been visited
                }

                // If all keys have been collected, return the number of steps taken
                if (key.length() == keyCount) {
                    return steps;
                }

                // Explore the four cardinal directions from the current position
                for (int[] d : dirs) {
                    int nr = i + d[0];
                    int nc = j + d[1];

                    Node newNode = new Node(nr, nc, key);

                    // Skip invalid positions (outside grid or walls) and already visited positions
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= col || grid[nr].charAt(nc) == '#' ||
                        visited.contains(newNode.toString(nr, nc, key))) {
                        continue;
                    }

                    char ch = grid[nr].charAt(nc);
                    // If the next position contains a new key, add it to the queue with the updated key combination
                    if (key.indexOf(ch) == -1 && ch >= 'a' && ch <= 'f') {
                        q.add(new Node(nr, nc, key + ch));
                    }

                    // If the next position contains a lock, check if the corresponding key has been collected
                    // If not, skip this position
                    if (ch >= 'A' && ch <= 'F' && key.indexOf(Character.toLowerCase(ch)) == -1) {
                        continue;
                    } else {
                        q.add(new Node(nr, nc, key)); // Add the next position to the queue
                    }
                }
            }
            steps++; // Increment the number of steps taken
        }
        return -1; // Return -1 if it is impossible to collect all keys
    }
}
