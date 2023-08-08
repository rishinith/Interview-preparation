
class BSTIterator {

    Stack<TreeNode> stack=new Stack<>();
    public BSTIterator(TreeNode root) {
        stack=new Stack<>();
        pushLeft(root);
    }
    
    public int next() {
        TreeNode node=stack.pop();
        pushLeft(node.right);
        return node.val;
    }


    void pushLeft(TreeNode node){
        while(node!=null){
            stack.push(node);
            node=node.left;
        }
    }
    
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */