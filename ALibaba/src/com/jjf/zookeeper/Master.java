package com.jjf.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by jjf_lenovo on 2017/4/4.
 */
public class Master implements Watcher{
    ZooKeeper zk;
    String hostPort;
    String serverId = String.valueOf(new Random().nextLong());
    static boolean isLeader = false; //�Ƿ���Ⱥ�׽ڵ�

    Master(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);//����رճ�����15��
    }

    void stopZK() throws IOException, InterruptedException {
        zk.close();
    }

    /**
     * ���ڵ�
     * @return �Ƿ��Ϊ���ڵ�
     */
    boolean checkMaster(){
        while (true){
            Stat stat = new Stat();
            try {
                byte data[] = zk.getData("/master",false,stat);
                isLeader = new String(data).equals(serverId); //����Ѿ�����"/master"�ڵ㣬��ʹ��"/master"������ȷ��˭��Ⱥ��
                return true;
            } catch (KeeperException.ConnectionLossException e) {
                System.out.println("����");
//                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException.NoNodeException e){
                return false;
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ����һ������Ȩ�޵���ʱ�ڵ���ʱ�ڵ�
     */
    void runForMaster(){
       while(true){
           try {
               //ZooDefs.Ids.OPEN_ACL_UNSAFE Ϊ�������ṩ������Ȩ��
               //CreateMode.EPHEMERAL ��ʱ�ڵ�
               zk.create("/master",serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
               //�����ɹ��Ǿ������ڵ�
               isLeader = true;
               break;
           } catch (KeeperException.NodeExistsException e) {
               //�Ѿ��������ڵ���
               isLeader = false;
               System.out.println("�ýڵ��Ѿ�����");
               break;
           } catch (KeeperException e) {
               //�������һ�㶼��ConnectionLossException��KeeperException�����ࣩ�����﷢������һ��Ҫ���ж������Ƿ���ɣ��������Ƿ��ٴη�������
               e.printStackTrace();
           } catch (InterruptedException e) {
               //Դ�ڿͻ����̵߳�����Thread.interrupt,ͨ��������ΪӦ�ó��򲿷ֹر�
               e.printStackTrace();
           }
           if(checkMaster()){
               break;
           }
       }
    }

    //---------------------�첽�汾start----------------

    /**
     * �첽create�ڵ㣬����ֻ��һ�������Ļص�������������������ص�����������������������ȫ��
     */
    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        /**
         *
         * @param rc ���ص��õĽṹ
         * @param path ���Ǵ���create��path����ֵ
         * @param ctx ���Ǵ���create�������Ĳ���
         * @param name �ڵ���
         */
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch(KeeperException.Code.get(rc)) { //����rc���ж�create��������
                case CONNECTIONLOSS:
                    checkMasterWithCallback();//������Ҫ�ж���û��ִ����ɣ���������
                    return;
                case NODEEXISTS:
                    System.out.println("�ýڵ��Լ�����");
                    return;
                case OK:
                    isLeader = true;
                    System.out.println("ok");
                    break;
                default:
                    System.out.println("default");
                    isLeader = false;
            }
            System.out.println("I'm "+(isLeader?"":"not") +"the leader");
        }
    };

    void checkMasterWithCallback(){
        zk.getData("/master",false,masterCheckCallback,null);
    }

    AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    checkMasterWithCallback();
                    return;
                case NONODE:
                    runForMasterWithCallBack(); //������������
                    return;
            }
        }
    };

    void runForMasterWithCallBack(){
        zk.create("/master",
                serverId.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                masterCreateCallback,"ctx");
    }

    //---------------------end----------------

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Master m = new Master("118.190.92.176:2182");
        m.startZK();
        //ͬ��
//        m.runForMaster();
//        if(isLeader){
//            System.out.println("I'm the leader");
//            //���Ӻ󣬻ᴴ��һ���ػ��߳�ά���˴λỰ����Ϊ���ػ��̣߳���Ӱ������˳�������Ҫ��֤��������һ���
//        }
//        else{
//            System.out.println("I'm not the leader");
//        }
//        Thread.sleep(60000);
        //�첽
        m.runForMasterWithCallBack();
        Thread.sleep(60000);

        //����
        m.stopZK();
    }
}
