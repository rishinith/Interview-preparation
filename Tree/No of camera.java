/**
 * You are given the root of a binary tree. We install cameras on the tree nodes where each camera at a node can monitor its parent, itself, and its immediate children.

Return the minimum number of cameras needed to monitor all nodes of the tree.
 */

class Solution {

    int ans=0;
    public int minCameraCover(TreeNode root) {

        ans=0;
        int status=dfs(root);
        //if status is -1, it means root child needs camera so add +1 in answer
        return status==-1?ans+1:ans;
    }

    //statuses
    //1: I am covered
    //-1: need camera 
    //0: I have camera
    //post traversal
    int dfs(TreeNode node){
        //covering the null node so no body pay attention to add camera for this node
        if(node==null){
            return 1;
        }

        int left=dfs(node.left);
        int right=dfs(node.right);

        //my left or right child need camera so I have to add camera on me
        if(left==-1 || right==-1){
            ans++;
            return 0;
        }

        // my left or right child is having camera so I am covered
        if(left==0 || right==0){
            return 1;
        }

        //I dont have camera, and I am not putting the camera because my child dont need, let my parent cover me
       return -1;

    }
}