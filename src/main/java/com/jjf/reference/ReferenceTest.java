package com.jjf.reference;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by jijianfeng on 2017/7/30.
 */
public class ReferenceTest {
  @Test
  public void strongReference() {
    Object referent = new Object();

    /**
     * ͨ����ֵ���� StrongReference
     */
    Object strongReference = referent;

    assertSame(referent, strongReference);

    referent = null;
    System.gc();

    /**
     * StrongReference �� GC �󲻻ᱻ����
     */
    assertNotNull(strongReference);
  }

  @Test
  public void weakReference() {
    Object referent = new Object();
    WeakReference<Object> weakRerference = new WeakReference<Object>(referent);

    assertSame(referent, weakRerference.get());

    referent = null;
    System.gc();

    /**
     * һ��û��ָ�� referent ��ǿ����, weak reference �� GC ��ᱻ�Զ�����
     */
    assertNull(weakRerference.get());
  }

  @Test
  public void weakHashMap() throws InterruptedException {
    Map<Object, Object> weakHashMap = new WeakHashMap<Object, Object>();
    Object key = new Object();
    Object value = new Object();
    weakHashMap.put(key, value);

    assertTrue(weakHashMap.containsValue(value));

    key = null;
    System.gc();

    /**
     * �ȴ���Ч entries ���� ReferenceQueue �Ա���һ�ε��� getTable ʱ������
     */
    Thread.sleep(1000);

    /**
     * һ��û��ָ�� key ��ǿ����, WeakHashMap �� GC ���Զ�ɾ����ص� entry
     */
    assertFalse(weakHashMap.containsValue(value));
  }

  @Test
  public void softReference() {
    Object referent = new Object();
    SoftReference<Object> softRerference = new SoftReference<Object>(referent);

    assertNotNull(softRerference.get());

    referent = null;
    System.gc();

    /**
     *  soft references ֻ���� jvm OutOfMemory ֮ǰ�Żᱻ����, �������ǳ��ʺϻ���Ӧ��
     */
    assertNotNull(softRerference.get());
  }
}
