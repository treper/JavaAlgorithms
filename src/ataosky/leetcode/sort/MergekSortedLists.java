package ataosky.leetcode.sort;

import ataosky.leetcode.utils.ListNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 * <p/>
 * <p/>
 * 维护一个大小为k的堆，每次取堆顶的最小元素放到结果中，然后读取该元素的下一个元素放入堆中，重新维护好。
 * 因为每个链表是有序的，每次又是去当前k个元素中最小的，所以当所有链表都读完时结束，这个时候所有元素按
 * 从小到大放在结果链表中。这个算法每个元素要读取一次，即是k*n次，然后每次读取元素要把新元素插入堆中要
 * logk的复杂度，所以总时间复杂度是O(nklogk)。空间复杂度是堆的大小，即为O(k)。
 */
//not submitted
public class MergekSortedLists {
    public ListNode mergekSortedLists(ArrayList<ListNode> lists) {

        PriorityQueue<ListNode> heap = new PriorityQueue<ListNode>(lists.size(), new Comparator<ListNode>() {

            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1.val < o2.val)
                    return -1;
                else if (o1.val > o2.val)
                    return 1;
                return 0;

            }
        });

        for (ListNode node : lists) {
            if (node != null)
                heap.add(node);
        }


        ListNode head=null,cur=null;

        //heap [--,--,--,--]
        //head
        //cur
        while(!heap.isEmpty())
        {

            if(head==null)//第一个链表头 最小
            {
                head = heap.poll();
                cur = head;
            }
            else
            {
                cur.next = heap.poll();
                cur= cur.next;
            }
            if(cur.next!=null)//加入到堆
            {
                heap.add(cur.next);
            }
        }
        return head;

    }

}
