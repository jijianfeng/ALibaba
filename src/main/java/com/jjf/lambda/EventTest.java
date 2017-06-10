package com.jjf.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class EventTest {
    /**
     * ��2��ʹ��Java 8 lambda���ʽ�����¼�����
     ������ù�Swing API��̣���ͻ�ǵ�����д�¼��������롣
     ������һ���ɰ汾��������ľ��������������ڿ��Բ������ˡ�
     �������lambda���ʽд�����õ��¼��������룬������ʾ��
     * @param args
     */
    public static void main(String[] args) {

    }

    public void swing(){
        // Java 8֮ǰ��
        JButton show = new JButton("Show");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event handling without lambda expression is boring");
            }
        });

        // Java 8��ʽ��
        show.addActionListener((e) -> {
            System.out.println("Light, Camera, Action !! Lambda expressions Rocks");
        });
    }

    /**
     * Java�����߾���ʹ�����������һ���ط���Ϊ Collections.sort() ���� Comparator��
     * ��Java 8�У�������ø��ɶ���lambda���ʽ������ª�������ࡣ
     * �Ұ����������ϰ��Ӧ�ò��ѣ����԰�������ʹ��lambda���ʽʵ�� Runnable �� ActionListener �Ĺ����е���·����
     */
    public void collenctionSort(List<String> list){
        //jdk8 ǰ
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1+"123");
            }
        });

        //jdk8 ��
        Collections.sort(list,
                (o1,o2)-> {
                     return o2.compareTo(o1+"123");
                }
        );
    }
}
