package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/25.
 */
//����һ��������ת�����������������Ԫ�ء�
public class Test12 {
    public static void main(String[] args){
//        System.out.println("dada");
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        a.next=b;
        b.next=c;
        c.next=d;
        d.next=e;
        ReverseList(a);
    }

    /**
     * �����汾�ģ���ʵ��򵥾������½�һ������
     * @param head
     * @return
     */
    public static ListNode ReverseList(ListNode head) {
        if(head==null){
            return null;
        }
        if(head.next==null){
            return head;
        }
        ListNode next = head.next;
        head.next=null;
        while(next.next!=null){
            ListNode temp =next.next;
            next.next=head;
            head = next ;
            next = temp;
        };
        next.next=head;
        return next;
    }
}
