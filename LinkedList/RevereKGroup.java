/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

/**
 * Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
k is a positive integer and is less than or equal to the length of the linked list. 
If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
You may not alter the values in the list's nodes, only nodes themselves may be changed.
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
 if (k <= 1 || head == null)
      return head;

    ListNode current = head, previous = null;
    while (true) {
      ListNode lastNodeOfPreviousPart = previous;
      // after reversing the LList 'current' will become the last node of the sub-list
      ListNode lastNodeOfSubList = current;
      ListNode next = null; // will be used to temporarily store the next node
      // reverse 'k' nodes

    if(!haveKNodes(current,k)){
        break;
    }

      for (int i = 0; current != null && i < k; i++) {
        next = current.next;
        current.next = previous;
        previous = current;
        current = next;
      }

      // connect with the previous part
      if (lastNodeOfPreviousPart != null)
        // 'previous' is now the first node of the sub-list
        lastNodeOfPreviousPart.next = previous;
      else // this means we are changing the first node (head) of the LinkedList
        head = previous;

      // connect with the next part
      lastNodeOfSubList.next = current;

      if (current == null) // break, if we've reached the end of the LinkedList
        break;
      // prepare for the next sub-list
      previous = lastNodeOfSubList;
    }

    return head;

    }

    boolean haveKNodes(ListNode current, int k){
        int i=1;
        while(i<=k && current!=null){
            current=current.next;
            i++;
        }

        return i==k+1;
    }

}