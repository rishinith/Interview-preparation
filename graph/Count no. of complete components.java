/**
 * 2685. Count the Number of Complete Components

You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1. 
You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting vertices ai and bi.
Return the number of complete connected components of the graph.
A connected component is a subgraph of a graph in which there exists a path between any two vertices, 
and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
A connected component is said to be complete if there exists an edge between every pair of its vertices.

Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
Output: 1
Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, 
the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.

Intuition
Using union find we can find all components. If the size of the component that x is a part of is not equal to (number of edges connected to x) + 1, 
the component is not complete.

Approach
Use union find to find all the components first
In the DSU class we add a method called size_of_group_that_x_is_a_part_of. This will return the size of the groupd that x is part of. 
The number of edges connected to x must be one less than this.
Use a counter to count edges connected to each node
Use a set groups to find the number of unique groups/components in the graph.
Then just find the size_of_group_that_x_is_a_part_of and make sure that the number of edges connected to x is one less than this. 
If it is not the group/component x is a part of is not complete.
Complexity
Time complexity: O(max(n, len(edges)))
Space complexity: O(n)
 */

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        DSU uf = new DSU(n);
        Set<Integer> groups = new HashSet<>();
        int[] counter = new int[n];
        for (int[] e : edges)
            uf.unionSet(e[0], e[1]);

        for (int i = 0; i < n; i++)
            groups.add(uf.find(i));

        for (int[] e : edges) {
            counter[e[0]]++; counter[e[1]]++;
        }

        for (int i = 0; i < n; i++) {
            int f = uf.find(i);
            if (!groups.contains(f)) continue;
            if (uf.sizeOfGroupThatXIsAPartOf(i) != counter[i] + 1)
                groups.remove(f);
        }
        return groups.size();
    }
}

class DSU {
    private int[] p, rank, count;

    public DSU(int n) {
        p = new int[n];
        rank = new int[n];
        count = new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = i;
            count[i] = 1;
        }
    }

    public int find(int x) {
        if (x != p[x])
            p[x] = find(p[x]);
        return p[x];
    }

    public void unionSet(int x, int y) {
        int xx = find(x), yy = find(y);
        if (xx == yy) return;
        count[xx] = count[yy] = count[xx] + count[yy];
        if (rank[xx] < rank[yy]) p[xx] = yy;
        else p[yy] = xx;
        if (rank[xx] == rank[yy]) rank[xx]++;
    }

    public int sizeOfGroupThatXIsAPartOf(int x) {
        return count[find(x)];
    }
}