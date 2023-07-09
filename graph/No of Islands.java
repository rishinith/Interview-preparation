
/**
 * You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. 
 * Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci)
 at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
You may assume all four edges of the grid are all surrounded by water.
 */

class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        
        int[][] visited=new int[m][n];
        int components=0;
        List<Integer> result=new ArrayList<>();

        int[][] directions=new int[][]{{-1,0},{1,0},{0,-1},{0,1}};

        UnionFind uf=new UnionFind(m,n);
        for(var pos:positions){
            int x=pos[0];
            int y=pos[1];

            //already counted
            if(visited[x][y]==1){
                result.add(components);
                continue;
            }
            else{
                components++;
                visited[x][y]=1;
                int point=x*n+y;
                for(var dir:directions){
                    int newX=x+dir[0];
                    int newY=y+dir[1];
                    if(newX<0 || newX>=m || newY<0 || newY>=n || visited[newX][newY]==0){
                        continue;
                    }
                    int newPoint=newX*n+newY;
                    System.out.println(newX+"-"+newY);
                    if(uf.union(point,newPoint)){
                        components--;
                    }

                }
                result.add(components);
            }
        }

        return result;

    }
}

class UnionFind{

    int[] parent;
    int [] rank;

    UnionFind(int m, int n){
        parent=new int[m*n];
        rank=new int[m*n];

        for(int i=0;i<m*n;i++){
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