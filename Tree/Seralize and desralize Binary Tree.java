import java.util.*;

public class Codec {

    // Encodes a tree to a single string using BFS.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        //Level order traversal, store null as N
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node == null) {
                sb.append("N,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree using BFS.
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }

        String[] nodeValues = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(nodeValues[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        for (int i = 1; i < nodeValues.length; i++) {
            TreeNode parent = queue.poll();

            if (!nodeValues[i].equals("N")) {
                TreeNode left = new TreeNode(Integer.parseInt(nodeValues[i]));
                parent.left = left;
                queue.offer(left);
            }

            i++;

            if (!nodeValues[i].equals("N")) {
                TreeNode right = new TreeNode(Integer.parseInt(nodeValues[i]));
                parent.right = right;
                queue.offer(right);
            }
        }

        return root;
    }
}
