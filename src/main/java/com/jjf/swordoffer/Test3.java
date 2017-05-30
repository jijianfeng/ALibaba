package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ����һ��������β��ͷ��ӡ����ÿ���ڵ��ֵ��
 * 
 * @author jjf_lenovo ʱ�����ƣ�1�� �ռ����ƣ�32768K 
 * 2017��2��24��19:18:04 ����ʱ�䣺36ms ռ���ڴ棺636k
 * ����ʱ�䣺36ms ռ���ڴ棺629k
 */
public class Test3 {

	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		Stack<Integer> stack = new Stack<>();
		while(listNode!=null){
			stack.push(listNode.val);
			listNode = listNode.next;
		}
//		Collections.reverse(list);  //util������ת
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(!stack.isEmpty()){
			result.add(stack.pop());
		}
		return result ;
	}
}

class ListNode {
	int val;
	ListNode next = null;
	ListNode(int val) {
		this.val = val;
	}
}
