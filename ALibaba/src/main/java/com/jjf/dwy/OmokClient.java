package com.jjf.dwy;

import java.awt.*; //ͼ�ΰ�
import java.awt.event.*; //�¼���������¼�ʲô��
//io �����ļ�����
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
class OmokBoard extends Canvas{ //OmokBoard��Canvas�������������࣬���沼����ʾ
  public static final int BLACK=1, WHITE=-1;
  private int[][] map; ///���̸��������꼯
  private int size, cell; //��С�����̣����ڵ㣨���ӣ�
  private String info="��Ϸ��ֹ";
  private int color=BLACK;

  private boolean enable=false; //��ͣ����˼��Ӧ��
  private boolean running=false;//�Ƿ���������

  private PrintWriter writer;
  private Graphics gboard, gbuff;
  private Image buff;

  OmokBoard(int s, int c){ //��ʼ������
    this.size=s;this.cell=c;
    map=new int[size+2][];
    for(int i=0;i<map.length;i++)
      map[i]=new int[size+2];
    setBackground(new Color(200,200,100));//���ñ�����ɫ
    setSize(size*(cell+1)+size, size*(cell+1)+size); //�������Ӵ�С

    addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent me){ //��ӵ���¼�
        if(!enable)return; //�����ͣ�Ͳ���
        int x=(int)Math.round(me.getX()/(double)cell);
        int y=(int)Math.round(me.getY()/(double)cell);
        if(x==0 || y==0 || x==size+1 || y==size+1)return;//�������̱߽粻��
        if(map[x][y]==BLACK || map[x][y]==WHITE)return;//�Ѿ������Ӳ���
        writer.println("[STONE]"+x+" "+y); //����������Ϣ
        map[x][y]=color;
        if(check(new Point(x, y), color)){
          info="��ʤ.";
          writer.println("[WIN]");
        }
        else info="�ȴ��Է�����.";
        repaint();
        enable=false;
      }
    });
  }
  public boolean isRunning(){
    return running;
  }//�ж��Ƿ���������
  public void startGame(String col){ //��ʼ��Ϸ
    running=true;
    if(col.equals("BLACK")){
      enable=true; color=BLACK;
      info="��ʼ��Ϸ...������.";
    }
    else{
      enable=false; color=WHITE;
      info="��ʼ��Ϸ... ��ȴ�.";
    }
  }
  public void stopGame(){ //������Ϸ
    reset();
    writer.println("[STOPGAME]");
    enable=false;
    running=false;
  }
  public void putOpponent(int x, int y){ //����
    map[x][y]=-color;
    info="���������ӣ�������.";
    repaint();
  }
  public void setEnable(boolean enable){
    this.enable=enable;
  } //��ͣ
  public void setWriter(PrintWriter writer){
    this.writer=writer;
  }//���÷��ͷ�
  public void update(Graphics g){
    paint(g);
  } //��������
  public void paint(Graphics g){ //��Ⱦ����
    if(gbuff==null){
      buff=createImage(getWidth(),getHeight());
      gbuff=buff.getGraphics();
    }
    drawBoard(g);
  }
  public void reset(){ //���¿�ʼ
    for(int i=0;i<map.length;i++)
      for(int j=0;j<map[i].length;j++)
        map[i][j]=0;
    info="��Ϸ��ֹ";
    repaint();
  }
  private void drawLine(){ //��Ⱦ��
    gbuff.setColor(Color.black);
    for(int i=1; i<=size;i++){
      gbuff.drawLine(cell, i*cell, cell*size, i*cell);
      gbuff.drawLine(i*cell, cell, i*cell , cell*size);
    }
  }
  private void drawBlack(int x, int y){//��Ⱦ��ɫ
    Graphics2D gbuff=(Graphics2D)this.gbuff;
    gbuff.setColor(Color.black);
    gbuff.fillOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
    gbuff.setColor(Color.white);
    gbuff.drawOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
  }
  private void drawWhite(int x, int y){ //��Ⱦ��ɫ
    gbuff.setColor(Color.white);
    gbuff.fillOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
    gbuff.setColor(Color.black);
    gbuff.drawOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
  }
  private void drawStones(){ //������ͣ������
    for(int x=1; x<=size;x++)
     for(int y=1; y<=size;y++){
       if(map[x][y]==BLACK)
         drawBlack(x, y);
       else if(map[x][y]==WHITE)
         drawWhite(x, y);
     }
  }
  synchronized private void drawBoard(Graphics g){
    gbuff.clearRect(0, 0, getWidth(), getHeight());
    drawLine();
    drawStones();
    gbuff.setColor(Color.red);
    gbuff.drawString(info, 20, 15);
    g.drawImage(buff, 0, 0, this);
  }
  private boolean check(Point p, int col){ //���ʤ������
    if(count(p, 1, 0, col)+count(p, -1, 0, col)==4)
      return true;
    if(count(p, 0, 1, col)+count(p, 0, -1, col)==4)
      return true;
    if(count(p, -1, -1, col)+count(p, 1, 1, col)==4)
      return true;
    if(count(p, 1, -1, col)+count(p, -1, 1, col)==4)
      return true;
    return false;
  }
  private int count(Point p, int dx, int dy, int col){ //�ж�����
    int i=0;
    for(; map[p.x+(i+1)*dx][p.y+(i+1)*dy]==col ;i++);
    return i;
  }
}
public class OmokClient extends Frame implements Runnable, ActionListener{//client�ͻ��ˣ��̳�Frame���������棬ʵ��Runable�ӿڿ��Զ��̣߳�ActionListener���Դ����¼�
  private TextArea msgView=new TextArea("", 1,1,1); //��Ϣ����
  private TextField sendBox=new TextField(""); //���ʹ�
  private TextField nameBox=new TextField(); //�û���
  private TextField roomBox=new TextField("0"); //�����
  private Label pInfo=new Label("������:  ��"); //��������Ϣ
  private java.awt.List pList=new java.awt.List(); //��Ҽ��ϣ���
  private Button startButton=new Button("��ʼ�Ծ�"); //Button���ǰ�ť
  private Button stopButton=new Button("��Ȩ");
  private Button enterButton=new Button("�볡");
  private Button exitButton=new Button("ȥ������");
  private Label infoView=new Label("< ������ >", 1);
  private OmokBoard board=new OmokBoard(15,30); //��ʼ������
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket socket; //�ͻ���socket
  private int roomNumber=-1; //�����
  private String userName=null; //�û���
  public OmokClient(String title){ //��ʼ������
    super(title);
    setLayout(null);
    msgView.setEditable(false);
    infoView.setBounds(10,30,480,30);
    infoView.setBackground(new Color(200,200,255));
    board.setLocation(10,70);
    add(infoView);
    add(board);
    Panel p=new Panel();
    p.setBackground(new Color(200,255,255));
    p.setLayout(new GridLayout(3,3));
    p.add(new Label("��  ��:", 2));p.add(nameBox);
    p.add(new Label("�����:", 2)); p.add(roomBox);
    p.add(enterButton); p.add(exitButton);
    enterButton.setEnabled(false);
    p.setBounds(500,30, 250,70);

    Panel p2=new Panel();
    p2.setBackground(new Color(255,255,100));
    p2.setLayout(new BorderLayout());
    Panel p2_1=new Panel();
    p2_1.add(startButton); p2_1.add(stopButton);
    p2.add(pInfo,"North"); p2.add(pList,"Center"); p2.add(p2_1,"South");
    startButton.setEnabled(false); stopButton.setEnabled(false);
    p2.setBounds(500,110,250,180);

    Panel p3=new Panel();
    p3.setLayout(new BorderLayout());
    p3.add(msgView,"Center");
    p3.add(sendBox, "South");
    p3.setBounds(500, 300, 250,250);

    add(p); add(p2); add(p3);
    sendBox.addActionListener(this);
    enterButton.addActionListener(this);
    exitButton.addActionListener(this);
    startButton.addActionListener(this);
    stopButton.addActionListener(this);
    addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent we){
         System.exit(0);
       }
    });
  }
  public void actionPerformed(ActionEvent ae){ //��Ӱ�ť����¼�
    if(ae.getSource()==sendBox){ //���Ͱ�ť
      String msg=sendBox.getText();
      if(msg.length()==0)return;
      if(msg.length()>=30)msg=msg.substring(0,30);
      try{
        writer.println("[MSG]"+msg);
        sendBox.setText("");
      }catch(Exception ie){}
    }
    else if(ae.getSource()==enterButton){ //���뷿�䰴ť
      try{

        if(Integer.parseInt(roomBox.getText())<1){
          infoView.setText("����Ŵ���.���� 1");
          return;
        }
          writer.println("[ROOM]"+Integer.parseInt(roomBox.getText()));
          msgView.setText("");
        }catch(Exception ie){
          infoView.setText("��������������.");
        }
    }
    else if(ae.getSource()==exitButton){//�˳���ť
      try{
        goToWaitRoom();
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
      }catch(Exception e){}
    }
    else if(ae.getSource()==startButton){ //��ʼ��ť
      try{
        writer.println("[START]");
        infoView.setText("�ȴ��Է�����.");
        startButton.setEnabled(false);
      }catch(Exception e){}
    }
    else if(ae.getSource()==stopButton){//��Ȩ��ť
      try{
        writer.println("[DROPGAME]");
        endGame("����Ȩ.");
      }catch(Exception e){}
    }
  }
  void goToWaitRoom(){ //ֱ��ȥֻ��һ���˵ķ���
    if(userName==null){
      String name=nameBox.getText().trim();
      if(name.length()<=2 || name.length()>10){
        infoView.setText("���ִ���. 3~10����");
        nameBox.requestFocus();
        return;
      }
      userName=name;
      writer.println("[NAME]"+userName);
      nameBox.setText(userName);
      nameBox.setEditable(false);
    }
    msgView.setText("");
    writer.println("[ROOM]0");
    infoView.setText("�ѽ��������.");
    roomBox.setText("0");
    enterButton.setEnabled(true);
    exitButton.setEnabled(false);
  }
  public void run(){ //����
    String msg;
    try{
    while((msg=reader.readLine())!=null){ //����Ĳ�����ǰ���serverһ����ֻ����������Ҫת��view����ͼ����
        if(msg.startsWith("[STONE]")){
          String temp=msg.substring(7);
          int x=Integer.parseInt(temp.substring(0,temp.indexOf(" ")));
          int y=Integer.parseInt(temp.substring(temp.indexOf(" ")+1));
          board.putOpponent(x, y);
          board.setEnable(true);
        }
        else if(msg.startsWith("[ROOM]")){
          if(!msg.equals("[ROOM]0")){
            enterButton.setEnabled(false);
            exitButton.setEnabled(true);
            infoView.setText(msg.substring(6)+"�ŷ����ѱ�����.");
          }
          else infoView.setText("�ѽ��������.");
          roomNumber=Integer.parseInt(msg.substring(6));
          if(board.isRunning()){
            board.stopGame();
          }
        }
        else if(msg.startsWith("[FULL]")){
          infoView.setText("������Ա����ֹ����.");
        }
        else if(msg.startsWith("[PLAYERS]")){
          nameList(msg.substring(9));
        }
        else if(msg.startsWith("[ENTER]")){
          pList.add(msg.substring(7));
          playersInfo();
          msgView.append("["+ msg.substring(7)+"]�볡.\n");
        }
        else if(msg.startsWith("[EXIT]")){
          pList.remove(msg.substring(6));
          playersInfo();
          msgView.append("["+msg.substring(6)+"]������������.\n");
          if(roomNumber!=0)
            endGame("�Է����뿪.");
        }
        else if(msg.startsWith("[DISCONNECT]")){
          pList.remove(msg.substring(12));
          playersInfo();
          msgView.append("["+msg.substring(12)+"]�ж�����.\n");
          if(roomNumber!=0)
            endGame("�Է��뿪.");
        }
        else if(msg.startsWith("[COLOR]")){
          String color=msg.substring(7);
          board.startGame(color);
          if(color.equals("BLACK"))
            infoView.setText("�õ�����.");
          else
            infoView.setText("�õ�����.");
          stopButton.setEnabled(true);
        }
        else if(msg.startsWith("[DROPGAME]"))
          endGame("�Է���Ȩ.");
        else if(msg.startsWith("[WIN]"))
          endGame("��ʤ.");
        else if(msg.startsWith("[LOSE]"))
          endGame("ʧ��.");
        else msgView.append(msg+"\n");
    }
    }catch(IOException ie){
     msgView.append(ie+"\n");
    }
    msgView.append("�����ж�.");
  }
  private void endGame(String msg){ //������Ϸ
    infoView.setText(msg);
    startButton.setEnabled(false);
    stopButton.setEnabled(false);
    try{ Thread.sleep(2000); }catch(Exception e){}
    if(board.isRunning())board.stopGame();
    if(pList.getItemCount()==2)startButton.setEnabled(true);
  }
  private void playersInfo(){ //���s����Ϣ
    int count=pList.getItemCount();
    if(roomNumber==0)
      pInfo.setText("������: "+count+"��");
    else pInfo.setText(roomNumber+" �ŷ�: "+count+"��");
    if(count==2 && roomNumber!=0)
      startButton.setEnabled(true);
    else startButton.setEnabled(false);
  }
  private void nameList(String msg){ //�����s����Ϣ�� �����б�plist
    pList.removeAll();
    StringTokenizer st=new StringTokenizer(msg, "\t");
    while(st.hasMoreElements())
      pList.add(st.nextToken());
    playersInfo();
  }
  private void connect(){ //�ͻ�����Server�˵�����
    try{
      msgView.append("�������ӷ�����.\n");
      socket=new Socket("127.0.0.1", 7777);
      //�ϻ�ʱ�������ʵ�������дIP��ַ������һ�������127.0.0.1
      msgView.append("---���ӳɹ�--.\n");
      msgView.append("���������Ȼ����������.\n");
      reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer=new PrintWriter(socket.getOutputStream(), true);
      new Thread(this).start();
      board.setWriter(writer);
    }catch(Exception e){
      msgView.append(e+"\n\n����ʧ��..\n");
    }
  }
  public static void main(String[] args){
    OmokClient client=new OmokClient("����������");
    client.setSize(760,560);
    client.setVisible(true);
    client.connect();
  }
}