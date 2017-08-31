package com.jjf.io;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jjf_lenovo on 2017/5/23.
 */
//1�����������ļ�����δ����ǴӴ��̶���InputStreamTest.java�ļ���Ȼ��ת�����ַ�����������ǽ�Դ�ļ�ԭ�������
public class InputStreamTest  {
    public static void main(String[] args) throws Exception {
        System.out.println(read("C:\\Users\\jjf_lenovo\\Desktop\\M.txt"));
    }

    public static String read(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String s;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        br.close();
        return sb.toString();
    }
}
