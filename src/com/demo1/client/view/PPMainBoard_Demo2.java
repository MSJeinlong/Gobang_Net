package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapUserModel;
import com.demo1.client.tools.NetTool;
import org.apache.log4j.Logger;

import javax.management.ObjectName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;

/**
 * @program: Gobang
 * @Date: 2018-12-12 15:42
 * @Author: long
 * @Description:
 * 人人对战页面
 * 接受信息线程
 */
public class PPMainBoard_Demo2 extends MainBoard {
    private PPChessBoard_Demo2 cb;
    private JButton findRival;
    private JButton exitGame;
    private JButton back;//悔棋按钮
    private JButton send; //聊天发送按钮
    private JButton gradeRecord;    //历史成绩查询
    private JLabel timecount;//计时器标签
    //双方状态
    private JLabel people1;//自己标签
    private JLabel people2;//对手标签
    private JLabel myLevel;//自己等级标签
    private JLabel rivalLevel;//对手等级标签
    private JLabel situation1;//自己状态标签
    private JLabel situation2;//对手状态标签
    private JLabel jLabel1;
    private JLabel jLabel2;//

    private JTextArea talkArea;
/*    private JTextField tf_ip; //输入IP框*/
    private JTextField talkField; //聊天文本框
    private String ip;
    private DatagramSocket socket;
    private String gameState;
    private String enemyGameState;//敌人状态
    private Logger logger = Logger.getLogger("游戏");
    private User rival;     //对手

    public JButton getstart() {
        return findRival;
    }

    public String getIp() {
        return ip;
    }

/*    public JTextField getTf() {
        return tf_ip;
    }*/

    public DatagramSocket getSocket() {
        return socket;
    }

    public JLabel getLabel1() {
        return jLabel1;
    }

    public JLabel getLabel2() {
        return jLabel2;
    }

    public JLabel getSituation1() {
        return situation1;
    }

    public JLabel getSituation2() {
        return situation2;
    }

    public PPMainBoard_Demo2(String userName) {
        init(userName);
        String title = "欢乐五子棋--当前用户："+u.getName()+"("+u.getSex()+")"+"  等级："+u.getDan()+"-"+u.getGrade();
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化页面
     */
    public void init(String userName) {
        //暂时初始化一下rival
        this.rival = new User();
        this.u = MapUserModel.getUser(userName);
        gameState = "NOT_START";
        enemyGameState = "NOT_START";
        cb = new PPChessBoard_Demo2(this);
        cb.setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
        cb.setBounds(210, 40, 570, 585);
        cb.setVisible(true);
        cb.setInfoBoard(talkArea);
/*        tf_ip = new JTextField("请输入对手IP地址");
        tf_ip.setBounds(780, 75, 200, 30);
        tf_ip.addMouseListener(this);*/
        //设置历史战绩按钮
        gradeRecord = new JButton("历史成绩");
        gradeRecord.setBounds(780, 75, 200, 50);//设置起始位置，宽度和高度，下同
        gradeRecord.setBackground(new Color(50, 205, 50));//设置颜色，下同
        gradeRecord.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        gradeRecord.addActionListener(this);

        findRival = new JButton("寻找对手");//设置名称，下同
        findRival.setBounds(780, 130, 200, 50);//设置起始位置，宽度和高度，下同
        findRival.setBackground(new Color(50, 205, 50));//设置颜色，下同
        findRival.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        findRival.addActionListener(this);
        back = new JButton("悔  棋");
        back.setBounds(780, 185, 200, 50);
        back.setBackground(new Color(85, 107, 47));
        back.setFont(new Font("宋体", Font.BOLD, 20));
        back.addActionListener(this);
        send = new JButton("发送");
        send.setBounds(840, 550, 60, 30);
        send.setBackground(new Color(50, 205, 50));
        send.addActionListener(this);
        talkField = new JTextField("聊天");
        talkField.setBounds(780, 510, 200, 30);
        talkField.addMouseListener(this);
        exitGame = new JButton("返  回");
        exitGame.setBackground(new Color(218, 165, 32));
        exitGame.setBounds(780, 240, 200, 50);
        exitGame.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        exitGame.addActionListener(this);
        people1 = new JLabel("    我:");
        people1.setOpaque(true);
        people1.setBackground(new Color(82, 109, 165));
        people1.setBounds(10, 410, 200, 50);
        people1.setFont(new Font("宋体", Font.BOLD, 20));
        people2 = new JLabel("    对手:");
        people2.setOpaque(true);
        people2.setBackground(new Color(82, 109, 165));
        people2.setBounds(10, 75, 200, 50);
        people2.setFont(new Font("宋体", Font.BOLD, 20));
        timecount = new JLabel("    计时器:");
        timecount.setBounds(320, 1, 200, 50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));
        myLevel = new JLabel("    等 级: "+u.getDan()+"-"+u.getGrade());
        myLevel.setOpaque(true);
        myLevel.setBackground(new Color(82, 109, 165));
        myLevel.setBounds(10, 465, 200, 50);
        myLevel.setFont(new Font("宋体", Font.BOLD, 20));
        rivalLevel = new JLabel("    等 级: "+rival.getDan()+"-"+rival.getGrade());
        rivalLevel.setOpaque(true);
        rivalLevel.setBackground(new Color(82, 109, 165));
        rivalLevel.setBounds(10, 130, 200, 50);
        rivalLevel.setFont(new Font("宋体", Font.BOLD, 20));
        situation1 = new JLabel("    状态:");
        situation1.setOpaque(true);
        situation1.setBackground(new Color(82, 109, 165));
        situation1.setBounds(10, 185, 200, 50);
        situation1.setFont(new Font("宋体", Font.BOLD, 20));
        situation2 = new JLabel("    状态:");
        situation2.setOpaque(true);
        situation2.setBackground(new Color(82, 109, 165));
        situation2.setBounds(10, 520, 200, 50);
        situation2.setFont(new Font("宋体", Font.BOLD, 20));
        jLabel1 = new JLabel();
        add(jLabel1);
        jLabel1.setBounds(130, 75, 200, 50);
        jLabel2 = new JLabel();
        add(jLabel2);
        jLabel2.setBounds(130, 410, 200, 50);
        timecount = new JLabel("    计时器:");
        timecount.setBounds(320, 1, 200, 50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));
        talkArea = new JTextArea();  //对弈信息
        talkArea.setEnabled(false);
        talkArea.setBackground(new Color(199,238,206));
        //滑动条
        JScrollPane p = new JScrollPane(talkArea);
        p.setBounds(780, 295, 200, 200);

     /*   add(tf_ip);*/
        add(cb);
        add(gradeRecord);
        add(findRival);
        add(back);
        add(exitGame);
        add(people1);
        add(people2);
        add(myLevel);
        add(rivalLevel);
        add(situation1);
        add(situation2);
        add(timecount);
        add(p);
        add(send);
        add(talkField);
        //加载线程
        /*ReicThread();*/
        repaint();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("触发windowClosing事件");
                //向服务器发出请求，要求更新数据库里的user.status
                try {
                    //获取客户端到服务器的通信线程
                    ObjectOutputStream oos = new ObjectOutputStream
                            (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                    Message m = new Message();
                    m.setMesType(MessageType.UPDATE_USER);
                    m.setU(u);
                    u.setStatus(User.OUT_LINE);
                    //通过对象流向服务器发送消息包
                    oos.writeObject(m);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }

            public void windowClosed(WindowEvent e)
            {
                //向服务器发出请求，要求更新数据库里的user.status
                try {
                    //获取客户端到服务器的通信线程
                    ObjectOutputStream oos = new ObjectOutputStream
                            (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                    Message m = new Message();
                    m.setMesType(MessageType.UPDATE_USER);
                    m.setU(u);
                    u.setStatus(User.OUT_LINE);
                    //通过对象流向服务器发送消息包
                    oos.writeObject(m);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
                System.out.println("触发windowClosed事件");
            }
        });
    }

    /**
     * 接收信息放在线程中
     */
   /* public void ReicThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    byte buf[] = new byte[1024];
                    socket = new DatagramSocket(10086);
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    while (true) {
                        socket.receive(dp);
                        //0.接收到的发送端的主机名
                        InetAddress ia = dp.getAddress();
                        //enemyMsg.add(new String(ia.getHostName()));  //对方端口
                        logger.info("对手IP：" + ia.getHostName());
                        //1.接收到的内容
                        String data = new String(dp.getData(), 0, dp.getLength());
                        if (data.isEmpty()) {
                            cb.setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                        } else {
                            String[] msg = data.split(",");
                            System.out.println(msg[0] + " " + msg[1]);
                            //接收到对面准备信息并且自己点击了准备
                            if (msg[0].equals("ready")) {
                                enemyGameState = "ready";
                                System.out.println("对方已准备");
                                if (gameState.equals("ready")) {
                                    gameState = "FIGHTING";
                                    cb.setClickable(MainBoard.CAN_CLICK_INFO);
                                    startGame.setText("正在游戏");
                                    situation1.setText("    状态:等待...");
                                    situation2.setText("    状态:下棋...");
                                    logger.info("等待对方消息");
                                    timer = new TimeThread(label_timeCount);
                                    timer.start();
                                }
                            } else if (msg[0].equals("POS")) {
                                System.out.println("发送坐标");
                                //接受坐标以及角色
                                situation1.setText("    状态:等待...");
                                situation2.setText("    状态:下棋...");
                                //重新启动计时线程
                                timer = new TimeThread(label_timeCount);
                                timer.start();

                                cb.setCoord(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]));

                            } else if (msg[0].equals("enemy")) {
                                talkArea.append("对手：" + msg[1] + "\n");
                                logger.info("对手发送的消息" + msg[1]);
                            } else if (msg[0].equals("back")) {
                                int n = JOptionPane.showConfirmDialog(cb, "是否同意对方悔棋", "选择", JOptionPane.YES_NO_OPTION);
                                //点击确定按钮则可以悔棋
                                if (n == JOptionPane.YES_OPTION) {
                                    cb.backstep();
                                    NetTool.sendUDPBroadCast(ia.getHostName(), "canBack" + ", ");
                                } else {
                                    NetTool.sendUDPBroadCast(ia.getHostName(), "noBack" + ", ");
                                }

                            }
                            //允许悔棋
                            else if (msg[0].equals("canBack")) {
                                JOptionPane.showMessageDialog(cb, "对方允许您悔棋");
                                cb.backstep();
                            }
                            //不允许悔棋
                            else if (msg[0].equals("noBack")) {
                                JOptionPane.showMessageDialog(cb, "对方不允许您悔棋");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findRival) {
            FindRival fr = new FindRival(u);
        }
        //点击悔棋后的操作
        else if (e.getSource() == back) {
            //发送悔棋信息
            NetTool.sendUDPBroadCast(ip, "back" + ", ");
            logger.info("玩家选择悔棋");
        }
        // 聊天发送按钮
        else if (e.getSource() == send) {
            if (!talkField.getText().isEmpty() && !talkField.getText().equals("不能为空")) {
                //获得输入的内容
                String msg = talkField.getText();
                talkArea.append("我：" + msg + "\n");
                talkField.setText("");
                /*ip = tf_ip.getText();*/
                NetTool.sendUDPBroadCast(ip, "enemy" + "," + msg);
            } else {
                talkField.setText("不能为空");
            }

        }
        //退出游戏，加载主菜单
        else if (e.getSource() == exitGame) {
            dispose();
            new SelectModel(u.getName());
        }
        else if(e.getSource() == gradeRecord){
            GradeRecordDialog grd = new GradeRecordDialog(this, "历史战绩", u);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
