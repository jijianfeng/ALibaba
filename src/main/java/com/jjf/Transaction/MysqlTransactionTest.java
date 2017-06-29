package com.jjf.Transaction;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * Created by jijianfeng on 2017/6/29.
 */
public class MysqlTransactionTest {

    private Connection con =null;

    private String TABLE_NAME = "test";

    private String ID = "1";

    private String COLUM = "age";

    private int INCR = 10;

    //����������
    String driver = "com.mysql.jdbc.Driver";
    //URLָ��Ҫ���ʵ����ݿ���mydata
    String url = "jdbc:mysql://118.190.92.176:3306/test";
    //MySQL����ʱ���û���
    String user = "root";
    //MySQL����ʱ������
    String password = "";

    @Test
    public void test() throws Exception {
        Class.forName(driver);
        for(int i=0 ; i<5 ; i++ ){
//            String sql ="select * from "+TABLE_NAME+" where id="+ID;
//            new Thread(new ThreadManager(con,sql,TABLE_NAME,ID,COLUM,INCR)).start();

            //����+for update
            //String sql ="select * from "+TABLE_NAME+" where id="+ID+" for update";
            String sql ="select * from "+TABLE_NAME+" where id="+ID;
            new Thread(new ThreadTransactionManager(DriverManager.getConnection(url,user,password),sql,TABLE_NAME,ID,COLUM,INCR)).start();
        }
        Thread.sleep(100000);
    }

}

/**
 * ĳ������ĳ������
 */
class ThreadManager implements Runnable{

    private Connection con = null;

    private String sql ;

    private String tableName;

    private String id ;

    private String colum;

    private int incr;

    public ThreadManager(Connection con,String sql,String tableName,String id ,String colum,int incr){
        this.con = con;
        this.sql = sql;
        this.tableName = tableName;
        this.id = id;
        this.colum = colum;
        this.incr = incr;
    }
    @Override
    public void run() {
        try {
            Statement statement = this.con.createStatement();
            ResultSet rs = statement.executeQuery(this.sql);
            int sum = 0;
            while(rs.next()){
                sum = rs.getInt(colum);
            }
            sum += incr;
            String sql = "update "+tableName+" set "+colum+" = "+sum+" where id = "+id;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/**
 * ����������ִ��ĳ������
 */
class ThreadTransactionManager implements Runnable{

    private Connection con = null;

    private String sql ;

    private String tableName;

    private String id ;

    private String colum;

    private int incr;

    public ThreadTransactionManager(Connection con,String sql,String tableName,String id ,String colum,int incr){
        this.con = con;
        this.sql = sql;
        this.tableName = tableName;
        this.id = id;
        this.colum = colum;
        this.incr = incr;
    }
    @Override
    public void run() {
        try {
            Statement statement = this.con.createStatement();
            //��������
            if(con.getAutoCommit()==true){
                con.setAutoCommit(false);
            }
            ResultSet rs = statement.executeQuery(this.sql);
            int sum = 0;
            while(rs.next()){
                sum = rs.getInt(colum);
                System.out.println(sum);
            }
            sum += incr;
            Thread.sleep(2000);
            String sql = "update "+tableName+" set "+colum+" = "+sum+" where id = "+id;
            statement.execute(sql);
            //��������
            if(con.getAutoCommit()==false){
                con.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}