package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ����һ��������β��ͷ��ӡ����ÿ���ڵ��ֵ��
 * 
 * @author jjf_lenovo ʱ�����ƣ�1�� �ռ����ƣ�32768K 
 * 2017��2��24��19:18:04 ����ʱ�䣺36ms ռ���ڴ棺636k
 * ����ʱ�䣺36ms ռ���ڴ棺629k
 */
public class Test3 {

	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(listNode!=null){
			list.add(listNode.val);
			listNode = listNode.next;
		}
		Collections.reverse(list);  
		return list ;
	}
}

class ListNode {
	int val;
	ListNode next = null;
	ListNode(int val) {
		this.val = val;
	}
}
