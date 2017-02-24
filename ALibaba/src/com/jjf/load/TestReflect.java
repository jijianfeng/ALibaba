//// ��ȡ��������ķ���
//TestReflect testReflect = new TestReflect();
//        System.out.println("�������  " + testReflect.getClass().getClassLoader().getClass().getName());
package com.jjf.load;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
//������Ŀ�ӿ�
interface Subject {
    public String say(String name, int age);
}
// ������ʵ��Ŀ
class RealSubject implements Subject {
    public String say(String name, int age) {
        return name + "  " + age;
    }
}
class MyInvocationHandler implements InvocationHandler {
    private Object obj = null;
    public Object bind(Object obj) {
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object temp = method.invoke(this.obj, args);
        return temp;
    }
}
/**
 * ��java�������������������
 * 
 * 1��Bootstrap ClassLoader �˼���������c++��д��һ�㿪���к��ټ���
 * 
 * 2��Extension ClassLoader ����������չ��ļ��أ�һ���Ӧ����jrelibextĿ¼�е���
 * 
 * 3��AppClassLoader ����classpathָ�����࣬����õļ�������ͬʱҲ��java��Ĭ�ϵļ�������
 * 
 * �����Ҫ��ɶ�̬����������Ҫ����һ��InvocationHandler�ӿڵ����࣬����ɴ���ľ��������
 * 
 * @author xsoftlab.net
 * 
 */
public class TestReflect {
    public static void main(String[] args) throws Exception {
        MyInvocationHandler demo = new MyInvocationHandler();
        Subject sub = (Subject) demo.bind(new RealSubject());
        String info = sub.say("Rollen", 20);
        System.out.println(info);
    }
}