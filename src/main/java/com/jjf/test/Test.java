package com.jjf.test;

import org.junit.Assert;

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
}
