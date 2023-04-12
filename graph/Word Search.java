/**
Given an m x n grid of characters board and a string word, return true if word exists in the grid.
The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. 
The same letter cell may not be used more than once.

 */

 class Solution {
    public boolean exist(char[][] board, String word) {

        boolean result=false;

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
               if(findWordRecursively(board,i,j,0,word)){
                   return true;
               }
            }
        }



        return false;
        
    }


    boolean findWordRecursively(char[][] board, int x, int y, int pos, String word){
        if(x<0 || x>= board.length || y<0 || y>=board[0].length || pos>=word.length() ||
         board[x][y]!=word.charAt(pos)){
            return false;
        }

        if(pos==word.length()-1 && board[x][y]==word.charAt(pos)){
            return true;
        }

        board[x][y]='#';

        
        var result=findWordRecursively(board,x+1,y,pos+1, word) ||
        findWordRecursively(board,x-1,y,pos+1, word) ||
        findWordRecursively(board,x,y+1,pos+1, word) ||
        findWordRecursively(board,x,y-1,pos+1, word);
        

         board[x][y]=word.charAt(pos);

        return result;



    }
}