package com.jjf.test;

import org.junit.Assert;

import java.util.Scanner;

/**
 * ģ��Ca���߳��µĽ��ܷ�ʽ
 * 
 * @author jjf_lenovo
 * 
 */
public class Test{

  @org.junit.Test
  public void testParse(){
    System.out.println(Long.parseLong(null));
  }

  @org.junit.Test
  public void testEquals(){
//    Assert.assertTrue("123".equals(123));
    Assert.assertTrue(Long.valueOf(123L).equals("123"));
  }

  static int cut(int[] parts) {
    int cost=0;
    int sum=0;
    for(int part:parts){
      sum=sum+part;
    }
    int temp=parts[0];
    if(parts.length==1){
      return parts[0];
    }
    if(parts.length==2){
      return parts[0]+parts[1];
    }
    for(int i=1;i<parts.length-1;i++){
      temp=temp+parts[i]+parts[parts.length-1];
      if((sum-temp>temp)&&(sum-temp-parts[i+1]-parts[parts.length-i-1]<temp+parts[i+1]+parts[parts.length-i-1])){
        cost=cost+sum;
        cost=cost+cut(cutParts(parts,0,i));
        cost=cost+cut(cutParts(parts,i,parts.length-1));
        return cost;
      }
    }
    return 0;
  }

  private static int[] cutParts(int[] parts,int start,int end){
    int[] result=new int[end-start];
    int j=0;
    for(int i=start;i<end;i++){
      result[j]=parts[i];
      j++;
    }
    return result;
  }

  // һ�˿�������begin��highָ�����е��½���Ͻ磬�ݹ��㷨
  private static void quickSort(int[] table, int begin, int end) {
    if (begin < end) // ������Ч
    {
      int i = begin, j = end;
//			System.out.println(i+":"+j);
      int vot = table[i]; // ��һ��ֵ��Ϊ��׼ֵ
      while (i != j) // һ������
      {
        while (i < j && vot <= table[j]){
          // �Ӻ���ǰѰ�ҽ�Сֵ
          j--;
        }
        if (i < j){
          table[i++] = table[j];// ��СԪ����ǰ�ƶ�
        }
        while (i < j && table[i] <= vot){
          // ��ǰ���Ѱ�ҽϴ�ֵ
          i++;
        }
        if (i < j){
          table[j--] = table[i]; // �ϴ�Ԫ������ƶ�
        }
//				print(table);
      }
      table[i] = vot; // ��׼ֵ��������λ��
//			print(table);
      quickSort(table, begin, j - 1); // ǰ�������������򣬵ݹ����
      quickSort(table, i + 1, end); // ��������������򣬵ݹ����
    }
  }
  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    int pieces = Integer.parseInt(in.nextLine().trim());
    int[] parts = new int[pieces];
    for (int i = 0; i < pieces; i++) {
      parts[i] = Integer.parseInt(in.nextLine().trim());
    }
    quickSort(parts,0,parts.length-1);
    System.out.println(cut(parts));
  }
}
