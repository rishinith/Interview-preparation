/**
 * Last Day Where You Can Still Cross

There is a 1-based binary matrix where 0 represents land and 1 represents water. 
You are given integers row and col representing the number of rows and columns in the matrix, respectively.

Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. 
You are given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith column (1-based coordinates) 
will be covered with water (i.e., changed to 1).

You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells. 
You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four cardinal directions (left, right, up, and down).

Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.
 */

/**
 * Typical problem of Binary search and BFS/DFS or use UnionFind
 * 
 * With Binary search and DFS/BFS: Time Complexity= row*col*log(row*col)  [cells size is row*col to binary search time is log(row*col)]
 * 
 * IF we use Union find this can be reduced to row*col
 * We need to reverse the days cells, which equals replacing water cells with land cells. This is necessary because we are searching for the last day when there is a path, 
 * which is the same as the first day in reversed order. During the union-find process, will we traverse through cells in reverse and replace the corresponding water cell 
 * cells[i] by land cell. For each newly added land cell, we connect it with all of its neighboring land cells. 
 * We repeat this process until either the first row and the last row are connected or the traversal is complete.
 * 
 * Considering that the top row may contain multiple disconnected cells (As shown on the left picture below, the group 1, 2 and 3 contain cells in the top row, 
 * but they are not connected), how can we efficiently check whether a cell in the top row is connected to a cell in the bottom row? Do we need to check them one by one?

The answer is no, we just need two additional nodes in dsu (say top and bottom) that represent all land cells in the top row and all land cells in the bottom row, respectively.
 * During the iteration, for each water cell cells[i] that will be replaced by land, in addition to connecting it with its land neighbors, we will also:
connect it with top if it is in the first row.
connect it with bottom if it is in the last row.
Therefore, we can simply check if the first row is connected with the bottom row after this day, by checking if top and bottom are connected.
 * 
 */

class Solution {
    public int latestDayToCross(int row, int col, int[][] cells) {

        int firstDay=0;
        int lastDay=cells.length-1;

        int ans=0;

        //binary search
        while(firstDay<=lastDay){
            int mid=(firstDay+lastDay)/2;

            //use bfs/dfs to check if with the gen water cells , path can be crossed or not
            if(possibleToWalk(row, col, cells, mid)){
                ans=mid;
                firstDay=mid+1;
            }else{
                lastDay=mid-1;
            }  
        }
        return ans; 
    }



    boolean possibleToWalk(int row, int col, int[][] cells, int day){

        int[][] matrix=new int[row][col];

        for(int i=0;i<day;i++){
            int[] cell=cells[i];
            matrix[cell[0]-1][cell[1]-1]=1;
        }
 
        // for(int i=0;i<col;i++){
        //     if(matrix[0][i]>0){
        //         continue;
        //     }
        //     if(dfs(matrix, 0, i)){
        //         return true;
        //     }
        // }

        return bfs(matrix);

    }

    boolean bfs(int[][] matrix){

        Queue<int[]> queue=new LinkedList<>();

        for(int i=0;i<matrix[0].length;i++){
            if(matrix[0][i]==0){
                queue.add(new int[]{0,i});
            }
        }

        while(!queue.isEmpty()){

            int[] current=queue.poll();

            if(current[0]==matrix.length-1){
                return true;
            }

            for(var dir:directions){

                int newRow=current[0]+dir[0];
                int newCol=current[1]+dir[1];

                if(newRow<0 || newCol<0 || newRow>=matrix.length || newCol>=matrix[0].length || matrix[newRow][newCol]>0){
                    continue;
                }

                matrix[newRow][newCol]=2;
                queue.add(new int[]{newRow, newCol});

            }

        }

        return false;

    }

    int directions[][]={{-1,0},{1,0},{0,-1},{0,1}};
    boolean dfs(int[][] matrix, int r, int c){

        if(r>=matrix.length || r<0 || c>=matrix[0].length || c<0 || matrix[r][c]>0){
            return false;
        }

        if(r==matrix.length-1){
            return true;
        }

        matrix[r][c]=2;
        for(var dir:directions){
            if(dfs(matrix, r+dir[0], c+dir[1])){
                return true;
            }
        }

        return false;

    }
}


/////////////////////////////////UNION FIND [ Time: row*col no binary search]/////////////////////////////////////////////////////////////////////////////////////

class Solution {

    int[][] directions=new int[][]{{0,1},{1,0},{-1,0},{0,-1}};
    //length of cells array is row*col
    public int latestDayToCross(int row, int col, int[][] cells) {

        int[][] grid=new int[row][col];

        //Union find for 2D..2 additional virtual nodes for connecting top and connecting bottom rows
        UnionFind uf=new UnionFind(row*col+2);
        
        //reverse iteration of water cells
        for(int i=cells.length-1;i>=0;i--){
            int[] cell=cells[i];
            int r=cell[0]-1; int c=cell[1]-1;

            //marking this as land cell now
            grid[r][c]=1;

            //intentionally adding 1 becaue 0th node is virtual node representing top
            int index=r*col+c+1;

            for(var dir:directions){
                int newR=r+dir[0]; int newC=c+dir[1];
                if(newR<0 || newR>=row || newC<0 || newC>=col || grid[newR][newC]==0){
                    continue;
                }
                //intentionally adding 1 becaue 0th node is virtual node representing top
                int neiIndex=newR*col+newC+1;
                //Connecting all neigbour land cell
                uf.union(index, neiIndex);
            }

            //if this is first row, lets connect it with virtual 0th node(top)
            if(r==0){
                uf.union(0, index);
            }

            //if this is last row, lets connect it with virtual last node(bottom)
            if(r==row-1){
                uf.union(row*col+1, index);
            }

            //check if top and bottom are connected. If yes this is the answer
            if(uf.find(0)==uf.find(row*col+1)){
                return i;
            }

        }

        return -1;
        
    }
}

class UnionFind{
    int n;

    int parent[];
    int rank[];

    UnionFind(int n){
        this.n=n;
        parent=new int[n];
        rank=new int [n];

        for(int i=0;i<n;i++){
            parent[i]=i;
            rank[i]=0;
        }
    }


    int find(int x){
        if(parent[x]!=x){
            parent[x]=find(parent[x]);
        }
        return parent[x];
    }

    boolean union(int x, int y){
        int p1=find(x);
        int p2=find(y);
        if(p1==p2){
            return false;
        }

        if(rank[p1]<rank[p2]){
            parent[p1]=p2;
        }else if( rank[p2]< rank[p1]){
            parent[p2]=p1;
        }
        else{
            parent[p1]=p2;
            rank[p2]++;
        }

        return true;

    }
}