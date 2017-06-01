package com.jjf.io;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */
public class PipedInputStreamTest {
    public static void main(String[] args)  throws Exception {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream();
        pin.connect(pout);  //�����������������
        ReadThread readTh   = new ReadThread(pin);
        WriteThread writeTh = new WriteThread(pout);
        new Thread(readTh).start();
        new Thread(writeTh).start();
    }
}
class ReadThread implements Runnable
{
    private PipedInputStream pin;
    ReadThread(PipedInputStream pin)   //
    {
        this.pin=pin;
    }

    public void run() //���ڱ���Ҫ����run����,�������ﲻ����,ֻ��try
    {
        try
        {
            sop("R:��ȡǰû������,������...�ȴ����ݴ����������������̨...");
            byte[] buf = new byte[1024];
            int len = pin.read(buf);  //read����
            sop("R:��ȡ���ݳɹ�,�������...");

            String s= new String(buf,0,len);
            sop(s);    //����ȡ�����������ַ������ַ�����ӡ����
            pin.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("R:�ܵ���ȡ��ʧ��!");
        }
    }

    public static void sop(Object obj) //��ӡ
    {
        System.out.println(obj);
    }
}

class WriteThread implements Runnable
{
    private PipedOutputStream pout;
    WriteThread(PipedOutputStream pout)
    {
        this.pout=  pout;
    }

    public void run()
    {
        try
        {
            sop("W:��ʼ������д��:���ȸ�5�������ǹ۲�...");
            Thread.sleep(5000);  //�ͷ�cpuִ��Ȩ5��
            pout.write("W: writePiped ����...".getBytes());  //�ܵ������
            pout.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("W:WriteThreadд��ʧ��...");
        }
    }

    public static void sop(Object obj) //��ӡ
    {
        System.out.println(obj);
    }
}