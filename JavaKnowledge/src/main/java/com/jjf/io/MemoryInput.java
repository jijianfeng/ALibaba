package com.jjf.io;

import java.io.StringReader;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
//2�����ڴ��ж�ȡ�� read���ص���int���͵����ݣ�����������������char����ǿ����ת�����ó���һ��һ��������ַ���
public class MemoryInput {
    public static void main(String[] args) throws Exception {
        StringReader in = new StringReader(
                InputStreamTest.read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt"));
        int c;
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
        in.close();
    }
}
