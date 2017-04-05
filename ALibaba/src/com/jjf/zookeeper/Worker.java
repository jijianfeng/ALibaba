package com.jjf.zookeeper;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

/**
 * Created by jjf_lenovo on 2017/4/4.
 */
public class Worker implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);

    ZooKeeper zk;
    String hostPort;
    String serverId = Integer.toHexString(new Random().nextInt());

    Worker(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);//����رճ�����15��
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info(event.toString()+","+hostPort);
    }

    void register(){
        zk.create("/workers/worker-"+serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallback,
                null);
    }

    AsyncCallback.StringCallback createWorkerCallback = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    register();//��Ϊ��Ψһ�ģ����Կ���ֱ������
                    return;
                case OK:
                    LOG.info("ע��ɹ�"+serverId);
                    return;
                case NODEEXISTS:
                    LOG.warn("�ڵ��Ѿ�����");
                    return;
                default:
                    LOG.error("���쳣"+KeeperException.create(KeeperException.Code.get(rc),path));
            }
        }
    };

    public static void main(String[] args) throws Exception {
        Worker worker = new Worker("118.190.92.176:2182");
        worker.startZK();
        worker.register();
        Thread.sleep(30000);
    }
}
