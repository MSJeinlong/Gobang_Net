package com.demo1.client.view;

import com.demo1.client.comman.*;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapUserModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @program: Gobang
 * @Date: 2018-12-12 15:42
 * @Author: long
 * @Description:
 * 人人对战页面
 * 接受信息线程
 */
public class PPMainBoard extends MainBoard {
    private PPChessBoard ppcb;
    private JButton findRival;
    private JButton exitGame;
    private JButton back;           //悔棋按钮
    private JButton send;           //聊天发送按钮
    private JButton gradeRecord;    //历史成绩查询
    private JLabel timecount;    //计时器标签

    //双方状态
    private JLabel people1;//自己标签
    private JLabel people2;//对手标签
    private JLabel myLevel;//自己等级标签
    private JLabel rivalLevel;//对手等级标签
    private JLabel situation1;//自己状态标签
    private JLabel situation2;//对手状态标签
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel turnTip;     //提示这是谁的回合标签

    private JTextArea talkArea;
    private JTextField talkField;   //聊天文本框
    private Logger logger = Logger.getLogger("游戏");
    private User rival;     //对手

    public JButton getFindRival() {
        return findRival;
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

    public JLabel getMyLevel() {
        return myLevel;
    }

    public JLabel getRivalLevel() {
        return rivalLevel;
    }

    public JButton getExitGame() {
        return exitGame;
    }

    public JButton getBack() {
        return back;
    }

    public PPChessBoard getPpcb() {
        return ppcb;
    }

    public JTextArea getTalkArea() {
        return talkArea;
    }

    public User getRival() {
        return rival;
    }

    public JLabel getTurnTip() {
        return turnTip;
    }

    public JLabel getPeople2() {
        return people2;
    }

    public JLabel getTimecount() {
        return timecount;
    }

    public void setRival(User rival) {
        this.rival = rival;
        rivalLevel.setText("    等 级: "+rival.getDan()+"-"+rival.getGrade());
        people2.setText("    对手: "+rival.getName());
    }

    public PPMainBoard(String userName) {
        init(userName);
        String title = "欢乐五子棋--当前用户："+u.getName()+"("+u.getSex()+")"+"  等级："+u.getDan()+"-"+u.getGrade();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化页面
     */
    public void init(String userName) {
        //暂时初始化一下rival
        this.u = MapUserModel.getUser(userName);
        ppcb = new PPChessBoard(this);
        ppcb.setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
        ppcb.setBounds(210, 40, 570, 585);
        ppcb.setVisible(true);
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
        talkField.addActionListener(this);
        exitGame = new JButton("返  回");
        exitGame.setBackground(new Color(218, 165, 32));
        exitGame.setBounds(780, 240, 200, 50);
        exitGame.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        exitGame.addActionListener(this);
        people1 = new JLabel("    我: "+u.getName());
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
        rivalLevel = new JLabel("    等 级:");
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
        turnTip = new JLabel("");
        turnTip.setOpaque(true);
        turnTip.setBackground(new Color(82, 109, 165));
        turnTip.setForeground(Color.red);
        turnTip.setBounds(10, 285, 200, 50);
        turnTip.setFont(new Font("宋体", Font.BOLD, 20));

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
        add(ppcb);
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
        add(turnTip);
        //刷新
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
                /*System.exit(0);*/
                dispose();
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
               /* System.exit(0);*/
                System.out.println("触发windowClosed事件");
            }
        });
    }

    //我方先开始游戏
    public void myFirstStart(){
        //设置开始游戏
        //是否是我的回合
        ppcb.setTurnToMe(true);
        turnTip.setText("   我的回合");
        ppcb.setClickable(MainBoard.CAN_CLICK_INFO);
        //先开始游戏的玩家执白
        ppcb.setRole(Chess.WHITE);
        people1.setIcon(new ImageIcon("images/white.png"));
        people2.setIcon(new ImageIcon("images/black.png"));
        situation1.setText("    状态:等待...");
        situation2.setText("    状态:下棋...");
        logger.info("等待对方消息");
        timer = new TimeThread(label_timeCount);
        timer.start();
        //禁止退出游戏
        exitGame.setEnabled(false);
        //禁止悔棋
        back.setEnabled(false);

        //更新status为对战中
        u.setStatus(User.VERSUSING);
        //请求服务器更新status
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
            Message m = new Message();
            m.setMesType(MessageType.UPDATE_USER);
            m.setU(u);
            oos.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //对方先开始游戏
    public void rivalFirstStart(){
        //设置开始游戏
        //是否是我的回合
        ppcb.setTurnToMe(false);
        turnTip.setText("   对手的回合");
        //后开始的玩家执白
        ppcb.setRole(Chess.BLACK);
        people1.setIcon(new ImageIcon("images/black.png"));
        people2.setIcon(new ImageIcon("images/white.png"));
        situation1.setText("    状态:下棋...");
        situation2.setText("    状态:等待...");
        logger.info("等待对方消息");
        timer = new TimeThread(label_timeCount);
        timer.start();
        //禁止退出游戏
        exitGame.setEnabled(false);
        //禁止悔棋
        back.setEnabled(false);

        //更新status为对战中
        u.setStatus(User.VERSUSING);
        //请求服务器更新status
        try {
            ObjectOutputStream oos = new ObjectOutputStream
                    (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
            Message m = new Message();
            m.setMesType(MessageType.UPDATE_USER);
            m.setU(u);
            oos.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //寻找对手
        if (source == findRival) {
            FindRival fr = new FindRival(u, this.getX() + 800, this.getY() + 220);
        }
        //退出游戏，加载主菜单
        else if (source == exitGame) {
            dispose();
            new SelectModel(u.getName());
        }
        //查询历史战绩
        else if(source == gradeRecord){
            GradeRecordDialog grd = new GradeRecordDialog(this, "历史战绩", u);
        }
        //玩家按下了"发送"按钮或者在输入框输完信息后按下回车，就能发送聊天信息
        else if(source == send || source == talkField){
            Message m = new Message();
            m.setMesType(MessageType.SEND_CHAT_CONTENT);
            String chatCon = talkField.getText();
            //在聊天区域中显示聊天信息
            talkArea.append("我："+chatCon+"\n");
            //清空输入框
            talkField.setText("");

            m.setChatContent(chatCon);
            m.setSender(u.getName());
            m.setGetter(rival.getName());
            //请求服务器转发信息
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                oos.writeObject(m);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        //玩家按下了悔棋按钮
        else if(source == back){
            //棋盘上没有棋子，不能悔棋
            if(ppcb.getStepCount() <= 0){
                JOptionPane.showMessageDialog(this, "棋盘上没有棋子，不能悔棋！", "警告" , JOptionPane.WARNING_MESSAGE);
                return;
            }
            Message m1 = new Message();
            m1.setMesType(MessageType.REQUEST_UNDO_CHESS);
            m1.setSender(u.getName());
            m1.setGetter(rival.getName());
            back.setText("等待对手同意");
            back.setEnabled(false);
            try {
                ObjectOutputStream oos1 = new ObjectOutputStream
                        (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                oos1.writeObject(m1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if(ppcb.clickable == MainBoard.CAN_CLICK_INFO){
            this.setCursor(Cursor.HAND_CURSOR);
        }else {
            this.setCursor(Cursor.DEFAULT_CURSOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }
}
