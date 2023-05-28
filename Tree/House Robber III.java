/**
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root.

Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. 
It will automatically contact the police if two directly-linked houses were broken into on the same night.

Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.

Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 */

class Solution {
    public int rob(TreeNode root) {
        var result=dfs(root);

        return Math.max(result[0], result[1]);
    }


    //Indexes: 0: loot if robbing the current node, 1: loot if not robbing the current node
    int[] dfs(TreeNode root){
        if(root==null){
            return new int[]{0,0};
        }

        int[] left=dfs(root.left);
        int[] right=dfs(root.right);

        // if we rob this node, we cannot rob its children
        int rob=root.val+left[1]+right[1];

        // else, we free to choose rob its children or not
        int noRob=Math.max(left[0], left[1])+Math.max(right[0], right[1]);

        return new int[]{rob, noRob};
    }
}