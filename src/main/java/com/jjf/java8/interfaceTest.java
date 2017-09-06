package com.jjf.java8;

import java.util.function.Supplier;

/**
 * http://www.importnew.com/19345.html  java8����
 * Created by jijianfeng on 2017/9/6.
 */
public class interfaceTest {
  private interface Defaulable {
    // Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.
    /**
     Java 8�����������µĸ����ڽӿ�������ʱ��Ĭ�Ϻ;�̬������Ĭ�Ϸ�����Trait��Щ���ƣ�����Ŀ�겻һ����
     Ĭ�Ϸ������������ڽӿ�������µķ������������ƻ�ʵ������ӿڵ�������ļ����ԣ�Ҳ����˵����ǿ��ʵ�ֽӿڵ���ʵ��Ĭ�Ϸ�����
     */
    default String notRequired() {
      return "Default implementation";
    }
  }

  private static class DefaultableImpl implements Defaulable {}

  private static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
      return "Overridden implementation";
    }
  }

  /**
   * Java 8 ������һ������˼���������ǽӿ������������̬���������ҿ���ʵ�֡�
   */
  private interface DefaulableFactory {
    // Interfaces now allow static methods
    static Defaulable create( Supplier< Defaulable > supplier ) {
      return supplier.get();
    }
  }

  public static void main( String[] args ) {
    Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );
    System.out.println( defaulable.notRequired() );

    defaulable = DefaulableFactory.create( OverridableImpl::new );
    System.out.println( defaulable.notRequired() );
  }
}


