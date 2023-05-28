/**
 * 947. Most Stones Removed with Same Row or Column
On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone.
A stone can be removed if it shares either the same row or the same column as another stone that has not been removed.
Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, return the largest possible number of stones that can be removed.
 
 */

/**
 * Time: N*N*alpha(N)
 * Space: N
 */
class Solution {
    // Return true if stone a and b shares row or column
    boolean shareSameRowOrColumn(int[] a, int[] b) {
        return a[0] == b[0] || a[1] == b[1];
    }
    
    // Returns the representative of vertex x
    int find(int[] rep, int x) {
        if (x == rep[x]) {
            return x;
        }
        // Uses Path compression
        return rep[x] = find(rep, rep[x]);
    }
    
    // Combine the stone x and y, and returns 1 if they were not connected
    int performUnion(int [] rep, int [] size, int x, int y) {
        x = find(rep, x);
        y = find(rep, y);
        
        if (x == y) {
            return 0;
        }
        
        if (size[x] > size[y]) {
            rep[y] = x;
            size[x] += size[y];
        } else {
            rep[x] = y;
            size[y] += size[x];
        }
        
        return 1;
    }
    
    public int removeStones(int[][] stones) {
        int[] rep = new int[stones.length];
        int[] size = new int[stones.length];
        // Initialize rep to itself and size as 1
        for (int i = 0; i < stones.length; i++) {
            rep[i] = i;
            size[i] = 1;
        }
        
        int componentCount = stones.length;
        for (int i = 0; i < stones.length; i++) {
            for (int j = i + 1; j < stones.length; j++) {
                if (shareSameRowOrColumn(stones[i], stones[j])) {
                    // Decrement the componenets if union invloved merging
                    componentCount -= performUnion(rep, size, i, j);
                }
            }
        }
        
        // Return the maximum stone that can be removed
        return stones.length - componentCount;
    }
};


//Optimized
// T: O(N)*alpha(K) + O(K), N is length of stones, K is sum of maxValue of row and maxValue of column
// S: O(K)+O(N)
/**
 * We know the stones with the same row are connected, so instead of checking for all the pairs, we can use the row to represent the stones with this row, 
 * similarly for the same column. For example, if we have a stone at (x, y), then we connect all the stones with the same x coordinate, 
 * and similarly we connect all the stones with the same y coordinate. Now we can connect all the stones under the x-coordinate with those under the y-coordinate. 
 * This is equivalent to connecting the x and y itself.
 * But we need a way to distinguish between the same value for x-coordinate and y-coordinate.
 * we can distinguish by x=x and y=max(x)+y+1
 */
class Solution {
    public int removeStones(int[][] stones) {

        int maxRow=0;
        int maxColumn=0;
        for(var stone:stones){
            maxRow=Math.max(maxRow,stone[0]);
            maxColumn=Math.max(maxColumn, stone[1]);
        }

        UnionFind uf=new UnionFind(maxRow+maxColumn+2);


        Set<Integer> stonesRowColum=new HashSet<>();
        for(var stone:stones){
            int x=stone[0];
            int y=maxRow+stone[1]+1;
            uf.union(x, y);
            stonesRowColum.add(x);
            stonesRowColum.add(y);
        }

        int component=0;
        for(var stone:stonesRowColum){
            if(uf.parent[stone]==stone){
                component++;
            }
        }

        return stones.length-component;
        
    }
}


class UnionFind{

    int[] parent;
    int [] rank;

    UnionFind(int m){
        parent=new int[m];
        rank=new int[m];

        for(int i=0;i<m;i++){
            parent[i]=i;
            rank[i]=1;
        }
    }


    int findParent(int x){
        if (parent[x]!=x){
            parent[x]=findParent(parent[x]);
        }
        return parent[x];
    }

    boolean union(int x, int y){
        int p1=findParent(x);
        int p2=findParent(y);

        if(p1==p2){
            return false;
        }

        if(rank[p1]<rank[p2]){
            parent[p1]=p2;
        }
        else if(rank[p1]>rank[p2]){
            parent[p2]=p1;
        }
        else{
            parent[p1]=p2;
            rank[p2]++;
        }
        return true;
    }
}