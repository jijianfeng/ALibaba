package com.jjf.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */

/**
 * ������ʽ��ȡ�ַ�
 */
public class RegexMatches {
    public static void main( String args[] ){

        // ��ָ��ģʽ���ַ�������
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // ���� Pattern ����
        Pattern r = Pattern.compile(pattern);

        // ���ڴ��� matcher ����
        Matcher m = r.matcher(line);
        if (m.find( )) {
            for(int i=0;i<=m.groupCount();i++){
                System.out.println("Found value: " + m.group(i) );
            }
        } else {
            System.out.println("NO MATCH");
        }
    }
}
