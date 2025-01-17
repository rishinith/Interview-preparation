/**
 * Given an array nums that represents a permutation of integers from 1 to n. 
 * We are going to construct a binary search tree (BST) by inserting the elements of nums in order into an initially empty BST.
 *  Find the number of different ways to reorder nums so that the constructed BST is identical to that formed from the original array nums.

For example, given nums = [2,1,3], we will have 2 as the root, 1 as a left child, and 3 as a right child. 
The array [2,3,1] also yields the same BST but [3,2,1] yields a different BST.
Return the number of ways to reorder nums such that the BST formed is identical to the original BST formed from nums.

Since the answer may be very large, return it modulo 109 + 7.

 

Example 1:
Input: nums = [2,1,3]
Output: 1
Explanation: We can reorder nums to be [2,3,1] which will yield the same BST. There are no other ways to reorder nums which will yield the same BST.


Example 2:
Input: nums = [3,4,5,1,2]
Output: 5
Explanation: The following 5 arrays will yield the same BST: 
[3,1,2,4,5]
[3,1,4,2,5]
[3,1,4,5,2]
[3,4,1,2,5]
[3,4,1,5,2]


Example 3:
Input: nums = [1,2,3]
Output: 0
Explanation: There are no other orderings of nums that will yield the same BST.
 */

class Solution {
    private long mod = (long)1e9 + 7;
    private long[][] table;

    public int numOfWays(int[] nums) {
        int m = nums.length;
        
        // Table of Pascal's triangle
        table = new long[m][m];
        for (int i = 0; i < m; ++i) {
            table[i][0] = table[i][i] = 1;
        }
        for (int i = 2; i < m; i++) {
            for (int j = 1; j < i; j++) {
                table[i][j] = (table[i - 1][j - 1] + table[i - 1][j]) % mod;
            }
        }
        List<Integer> arrList = Arrays.stream(nums).boxed().collect(Collectors.toList());
        return (int)((dfs(arrList) - 1) % mod);
    }

    private long dfs(List<Integer> nums) {
        int m = nums.size();
        if (m < 3) {
            return 1;
        }

        List<Integer> leftNodes = new ArrayList<>();
        List<Integer> rightNodes = new ArrayList<>();
        for (int i = 1; i < m; ++i) {
            if (nums.get(i) < nums.get(0)) {
                leftNodes.add(nums.get(i));
            } else {
                rightNodes.add(nums.get(i));
            }
        }
        long leftWays = dfs(leftNodes) % mod;
        long rightWays = dfs(rightNodes) % mod;

        return (((leftWays * rightWays) % mod) * table[m - 1][leftNodes.size()]) % mod;
    }
}

/**
 * Time complexity: O(m^2)

In Java or C++, a table of Pascal's triangle of size m×m is built, which takes O(m^2) time.

The dfs(nums) function recursively calls itself to process the left and right subtrees of the current node nums[0]. 
Since the total size of the subtrees decreases by 1 at each level of the recursion, the maximum height of the recursion tree is m.
 Thus, the total time complexity of the recursive solution is O(m^2) because in each call we are doing O(m) work, creating the subsequences.

Space complexity: O(m^2) + O(m)

In Java or C++, a table of Pascal's triangle of size m×m is built.
The recursive solution uses the call stack to keep track of the current subtree being processed. 
The maximum depth of the call stack is equal to the height of the BST constructed from the input array. 
In the worst case, nums may form a degenerate BST (e.g., a sorted array), which has a height of m−1, and the stack can hold up to m−1 calls,
 resulting in a space complexity of O(m).
 */