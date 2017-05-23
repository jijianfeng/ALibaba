package com.jjf.dwy;

//��������C/S�ṹ��ƣ�������Server��
//����jdk�Դ���������
import java.net.*; //��������ͨ�ŵ�
import java.io.*; //�����ļ�����ȡд���
import java.util.*; //j���ù������
public class OmokServer{
  private ServerSocket server; //����һ��ServerSocket��������ͨ�ŵ�
  private BManager bMan=new BManager();  //DManager�̳���Vector�࣬��һ��������,���ڴ洢�����ͻ��˵��̼߳��ϵĹ�ϵ���������ս��ϵ��
  private Random rnd= new Random(); //��������Random�������������������
  public OmokServer(){}  //����OmokServer��Ҳ��������౾���Ĺ��췽��
  void startServer(){    //����startServer���������þ���֤��������
    try{
      server=new ServerSocket(7777); //��ʼ��������������ServerSocket�������Ӷ˿�Ϊ7777��ʹ��������
      System.out.println("�������׽����ѱ�����.");
      while(true){      //����ѭ��������������Ϊ��֤�����һֱ���У����Խ��������ͻ��˵�����
         Socket socket=server.accept();  //��ȡ���󷽣��ͻ��ˣ���socket�������ڻ�������
         Omok_Thread ot=new Omok_Thread(socket);  //�øղŻ�ȡ����socket�ͻ��˶��󣬽����µ��߳�ot���߳̿������Ϊ�����ܹ�ͬʱ���е�����һ����·
         ot.start();  //�����߳�ot
         bMan.add(ot);  //��ot�߳���ӵ�bman������
         System.out.println("������: "+bMan.size());
      }
    }catch(Exception e){
      System.out.println(e);  //������汻try{}�����Ĵ����У��б����ͻ���ת��catch{}�У�������쳣��Ϣ
    }
  }
  public static void main(String[] args){ //java������
    OmokServer server=new OmokServer();
    server.startServer();   //����startServer��������������
  }
  class Omok_Thread extends Thread{ //Omok_Thread�̳�Thread�࣬ʵ���˶��̣߳�ʵ�ֶ��߳������ŷ�����1.�̳�Thread�� 2.ʵ��Runable�ӿ�
    private int roomNumber=-1; //�����
    private String userName=null;  //�û���
    private Socket socket; //�����˶���==�ᱣ��ͻ��˵�socket����

    private boolean ready=false; //�Ƿ�׼��
    //read��write����java.io�ļ����ĺ��Ĳ��������Ƕ���д
    private BufferedReader reader; //�����ȡ�����̳�read����ȡ�ࣩ�����Լӿ��ȡ�ٶȣ�����һ�����ڴ�����
    private PrintWriter writer; // ��������Ҳ�д�����,�̳�write��д�ࣩ��������ĳ���ط�����������������Ϊ���ݣ�������ͨ�ŵĽ��ʣ�
    Omok_Thread(Socket socket){ //Omok_Thread�̵߳ĳ�ʼ��������������������socket�����ʼ��Ϊ�ͻ���socket
      this.socket=socket;
    }
    Socket getSocket(){
      return socket;
    } //��ȡ���̵߳Ŀͻ���socket����
    int getRoomNumber(){
      return roomNumber;
    } //��ȡ�����
    String getUserName(){
      return userName;
    } //��ȡ�û���
    boolean isReady(){
      return ready;
    } //��ȡ�Ƿ�׼��
    public void run(){
      try{
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream())); //��ȡsocket����������Ϣ
        writer=new PrintWriter(socket.getOutputStream(), true);
        String msg;
        while((msg=reader.readLine())!=null){
          if(msg.startsWith("[NAME]")){ //�����Ϣ��[NAME]��ͷ����ȡ�û���
            userName=msg.substring(6);
          }
          else if(msg.startsWith("[ROOM]")){ //�����Ϣ��[ROOM]��ͷ����ȡ�����
            int roomNum=Integer.parseInt(msg.substring(6));
            if( !bMan.isFull(roomNum)){  //�жϷ�����û�������ǲ�����������
              if(roomNumber!=-1) {  //�������Ų�����-1,Ҳ����Ҫ�����µķ��䣬�Ǿ��Ǹ��ߵ�ǰ���ڵķ���������ˣ���exit�˳���ǰ�ķ���
                bMan.sendToOthers(this, "[EXIT]" + userName);
            }
            roomNumber=roomNum;
            writer.println(msg);
            writer.println(bMan.getNamesInRoom(roomNumber));//���߶Է�������
            bMan.sendToOthers(this, "[ENTER]"+userName); //��Է������ҽ�����
            }
            else writer.println("[FULL]");
          }
          else if(roomNumber>=1 && msg.startsWith("[STONE]")) //���ӵ���Ϣ
            bMan.sendToOthers(this, msg);
          else if(msg.startsWith("[MSG]")) //�����Ϣ��[MSG]��ͷ���������ض��������˵��
            bMan.sendToRoom(roomNumber, "["+userName+"]: "+msg.substring(5));
          else if(msg.startsWith("[START]")){
            ready=true;
            if(bMan.isReady(roomNumber)){ //����������˶�׼���ˣ��Ϳ�ʼ
              int a=rnd.nextInt(2); //���������
              if(a==0){ //����������ж���ɫ
                writer.println("[COLOR]BLACK");
                bMan.sendToOthers(this, "[COLOR]WHITE");
              }
              else{
                writer.println("[COLOR]WHITE");
                bMan.sendToOthers(this, "[COLOR]BLACK");
              }
            }
          }
          else if(msg.startsWith("[STOPGAME]")) //ȡ��׼��
            ready=false;
          else if(msg.startsWith("[DROPGAME]")){ //�ر���Ϸ
            ready=false;
            bMan.sendToOthers(this, "[DROPGAME]");
          }
          else if(msg.startsWith("[WIN]")){ //����ж�
            ready=false;
            writer.println("[WIN]");
            bMan.sendToOthers(this, "[LOSE]");
          }
        }
      }catch(Exception e){
      }finally{
        try{
          bMan.remove(this);
          if(reader!=null) reader.close();
          if(writer!=null) writer.close();
          if(socket!=null) socket.close();
          reader=null; writer=null; socket=null;
          System.out.println(userName+"�Ѷ���.");
          System.out.println("��������: "+bMan.size());
          bMan.sendToRoom(roomNumber,"[DISCONNECT]"+userName);
        }catch(Exception e){}
      }
    }
  }

  class BManager extends Vector{//DManager�̳���Vector�࣬��һ��������,���ڴ洢�����ͻ��˵��̼߳��ϵĹ�ϵ���������ս��ϵ��
                                // ��Vector������ArrayList,����ArrayList�̲߳���ȫ��Vector�̰߳�ȫ����������õ����̣߳�������Vector
    BManager(){} //���췽��
    void add(Omok_Thread ot){
      super.add(ot);
    } //����������ӿͻ��������߳�
    void remove(Omok_Thread ot){
       super.remove(ot);
    }//�Ƴ��ͻ��������߳�
    Omok_Thread getOT(int i){//��ȡ �ͻ��������߳�
      return (Omok_Thread)elementAt(i);
    }
    Socket getSocket(int i){
      return getOT(i).getSocket();
    } //��ÿͻ��������߳��еĿͻ���Socket��ͨ�Ŷ���
    void sendTo(int i, String msg){//�򼯺���ĵ�N���˷���Ϣ
      try{
        PrintWriter pw= new PrintWriter(getSocket(i).getOutputStream(), true);
        pw.println(msg);
      }catch(Exception e){}
    }
    int getRoomNumber(int i){
      return getOT(i).getRoomNumber();
    } //��ȡ�����
    synchronized boolean isFull(int roomNum){ //�жϷ����Ƿ�������synchronized���Σ���֤�̰߳�ȫ��������������˽��뷿������
      if(roomNum==0)return false;
      int count=0;
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))count++;
      if(count>=2)return true;
      return false;
    }
    void sendToRoom(int roomNum, String msg){ //�򷿼䡾roomnum��˵msg
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))
          sendTo(i, msg);
    }
    void sendToOthers(Omok_Thread ot, String msg){ //�򷿼���������˵msg
      for(int i=0;i<size();i++)
        if(getRoomNumber(i)==ot.getRoomNumber() && getOT(i)!=ot) { //ȷ�Ϸ���ľ����Լ�socket����
          sendTo(i, msg);
        }
    }
    synchronized boolean isReady(int roomNum){ //�Ƿ�׼������synchronized���Σ���֤��������Լ�ȡ��׼����ȴ��Ȼ������Ϸ�����
      int count=0;
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i) && getOT(i).isReady())
          count++;
      if(count==2)return true;
      return false;
    }
    String getNamesInRoom(int roomNum){ //��÷������û�������
      StringBuffer sb=new StringBuffer("[PLAYERS]");
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))
          sb.append(getOT(i).getUserName()+"\t");
      return sb.toString();
    }
  }
}