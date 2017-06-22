package com.jjf;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import org.joda.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class DateTest {
    @Test
    public void testDateTime(){
        //��ʼ��ʱ��
        DateTime dateTime=new DateTime(2012, 12, 13, 18, 23,55);

        // ��,��,��,ʱ,��,��,����
        DateTime dt3 = new DateTime(2011, 2, 13, 10, 30, 50, 333);// 2010��2��13��10��30��50��333����

        //������ǰ���һ��ĸ�ʽ���ʱ��
        String str2 = dateTime.toString("MM/dd/yyyy hh:mm:ss.SSSa");
        String str3 = dateTime.toString("dd-MM-yyyy HH:mm:ss");
        String str4 = dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa");
        String str5 = dateTime.toString("MM/dd/yyyy HH:mm ZZZZ");
        String str6 = dateTime.toString("MM/dd/yyyy HH:mm Z");

        DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
        //ʱ�����
        DateTime dateTime2 = DateTime.parse("2012-12-21 23:22:45", format);

        //ʱ���ʽ�������==> 2012/12/21 23:22:45 Fri
        String string_u = dateTime2.toString("yyyy/MM/dd HH:mm:ss EE");
        System.out.println(string_u);

        //��ʽ����Locale�����==> 2012��12��21�� 23:22:45 ������
        String string_c = dateTime2.toString("yyyy��MM��dd�� HH:mm:ss EE",Locale.CHINESE);
        System.out.println(string_c);

        DateTime dt1 = new DateTime();// ȡ�õ�ǰʱ��

        // ����ָ����ʽ,��ʱ���ַ���ת����DateTime����,����ĸ�ʽ������������ʽ��һ����
        DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2012-12-26 03:27:39");

        //�����������ڼ��������
        LocalDate start=new LocalDate(2012, 12,14);
        LocalDate end=new LocalDate(2013, 01, 15);
        int days = Days.daysBetween(start, end).getDays();

        //�����������ڼ����Сʱ��,������,����

        //��������
        DateTime dateTime1 = DateTime.parse("2012-12-03");
        dateTime1 = dateTime1.plusDays(30);
        dateTime1 = dateTime1.plusHours(3);
        dateTime1 = dateTime1.plusMinutes(3);
        dateTime1 = dateTime1.plusMonths(2);
        dateTime1 = dateTime1.plusSeconds(4);
        dateTime1 = dateTime1.plusWeeks(5);
        dateTime1 = dateTime1.plusYears(3);

        // Joda-time ���ֲ���.....
        dateTime = dateTime.plusDays(1) // ������
                .plusYears(1)// ������
                .plusMonths(1)// ������
                .plusWeeks(1)// ��������
                .minusMillis(1)// ������
                .minusHours(1)// ��Сʱ
                .minusSeconds(1);// ������

        //�ж��Ƿ�����
        DateTime dt4 = new DateTime();
        org.joda.time.DateTime.Property month = dt4.monthOfYear();
        System.out.println("�Ƿ�����:" + month.isLeap());

        //ȡ�� 3��ǰ��ʱ��
        DateTime dt5 = dateTime1.secondOfMinute().addToCopy(-3);
        dateTime1.getSecondOfMinute();// �õ������Ӻ󣬹���������
        dateTime1.getSecondOfDay();// �õ�����󣬹���������
        dateTime1.secondOfMinute();// �õ����Ӷ���,�����������жϵ�ʹ��

        // DateTime��java.util.Date����,��ǰϵͳTimeMillisת��
        DateTime dt6 = new DateTime(new Date());
        Date date = dateTime1.toDate();
        DateTime dt7 = new DateTime(System.currentTimeMillis());
        dateTime1.getMillis();

        Calendar calendar = Calendar.getInstance();
        dateTime = new DateTime(calendar);
    }

    @Test
    public void test1() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusHours(1);
        System.out.print(dateTime.toString());
    }
}
