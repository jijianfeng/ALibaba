package com.jjf.copy;


class Professor implements Cloneable {
    String name;
    int age;
 
    Professor(String name, int age) {
        this.name = name;
        this.age = age;
    }
 
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }
}
 
class Student implements Cloneable {
    String name;
    int age;
    Professor p;
 
    Student(String name, int age, Professor p) {
        this.name = name;
        this.age = age;
        this.p = p;
    }
 
    public Object clone() {
        Student o = null;
        try {
            o = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        o.p = (Professor) p.clone();
        return o;
    }
}
 
public class DeepCopy {
    public static void main(String args[]) {
        Professor p = new Professor("wangwu", 50);
        Student s1 = new Student("zhangsan", 18, p);
        Student s2 = (Student) s1.clone();
        s2.p.name = "lisi";
        s2.p.age = 30;
//        System.out.println("name=" + s1.p.name + "," + "age=" + s1.p.age);// ѧ��1�Ľ��ڲ��ı䡣
        System.out.println("ѧ��s1��������" + s1.name + "\nѧ��s1���ڵ�������" + s1.p.name + "," + "\nѧ��s1���ڵ����" + s1.p.age);// ѧ��1�Ľ���
    }
}