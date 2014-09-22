package ataosky.leetcode.sort;

import ataosky.leetcode.utils.ListNode;

/**
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 */

//not submitted
public class MergeTwoSortedLists {

    public ListNode mergeTwoSortedLists(ListNode l1,ListNode l2)
    {
        ListNode head= new ListNode(0);
        ListNode cur = head;
        while(l1!=null && l2!=null)
        {
            if(l1.val<=l2.val)
            {
                cur.next = l1;
                l1=l1.next;
            }
            else
            {
                cur.next = l2;
                l2=l2.next;
            }
            cur = cur.next;

        }
        if(l1!=null)
        {
            cur.next = l1;
        }
        else if(l2!=null)
        {
            cur.next = l2;
        }
        return head.next;

    }
}
