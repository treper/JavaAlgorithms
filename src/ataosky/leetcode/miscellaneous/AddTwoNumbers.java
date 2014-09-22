package ataosky.leetcode.miscellaneous;


import ataosky.leetcode.utils.ListNode;

/**
 * You are given two linked lists representing two non-negative numbers.
 * <p/>
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * <p/>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * <p/>
 * Output: 7 -> 0 -> 8
 *
 * not checked

 */

public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode a, ListNode b) {
        if (a == null)
            return b;
        if (b == null)
            return a;

        ListNode head = new ListNode(0);
        ListNode cur = head;


        int plus = 0;
        while (a != null && b != null) {
            int sum = a.val + b.val + plus;
            sum = sum / 10;
            plus = sum % 10;

            cur.next  = new ListNode(sum);
            cur = cur.next;
            a = a.next;
            b = b.next;
        }

        if (null != a) {
            if (plus == 0) {
                cur.next = a;
            } else {
                cur.next = addTwoNumbers(a, new ListNode(plus));
            }
        } else if (null != b) {
            if (plus == 0) {
                cur.next = b;
            } else {
                cur.next = addTwoNumbers(b, new ListNode(plus));
            }
        }


        return head.next;
    }

}
