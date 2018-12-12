package com.demo1.client.view;

import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.comman.TimeThread;
import com.demo1.client.comman.User;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapUserModel;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;

/**
 * 人机对战页面（棋盘为人机和人人共享）
 * 面板：电脑信息，棋盘，玩家信息，棋子图片，计时器
 *
 * @author admin
 */
public class PCMainBoard extends MainBoard {
    private PCChessBoard cb;//棋盘
    private JButton start;//开始游戏按钮
    private JButton restart;    //重新开始按钮
    private JButton back;//悔棋按钮
    private JButton exit;//返回按钮
    private JButton gradeHistory;   //玩家历史战绩按钮
    private JLabel people;//玩家标签
    private JLabel computer;//电脑标签
    private JLabel plv;//玩家等级标签
    private JLabel clv;//电脑等级标签
    private String clv_text;    //电脑等级显示
    private JLabel timecount;//计时器标签

    //双方状态
    private JLabel situation1;//玩家状态标签
    private JLabel situation2;//电脑状态标签
    private JLabel jLabel1;
    private Image image1;//白棋图片
    private ImageIcon imageIcon1;//
    private JLabel jLabel2;//
    private Image image2;//黑棋图片
    private ImageIcon imageIcon2;//
    private int level;
    private Logger logger = Logger.getLogger("游戏");

    public int getLevel() {
        return level;
    }//返回你要挑战的等级

    public JButton getstart() {
        return start;
    }//返回start

    public JButton getback() {
        return back;
    }//返回back

    public JLabel getSituation1() {
        return situation1;
    }//返回玩家状态

    public JLabel getSituation2() {
        return situation2;
    }//返回电脑状态

    public JButton getRestart() {       //返回restart
        return restart;
    }

    public JLabel getPlv() {
        return plv;
    }

    //显示对应等级的人机对战界面
    public PCMainBoard(int level, String userName) {
        this.u = MapUserModel.getUser(userName);
        this.level = level;
        //根据玩家选择的电脑等级，在游戏界面将其显示出来
        if(level == SelectLevel.PRIMARY){
            clv_text = "初级";
        } else if(level == SelectLevel.MEDIUM){
            clv_text = "中级";
        } else if(level == SelectLevel.SENIOR){
            clv_text = "高级";
        }
        init();
        String title = "欢乐五子棋--当前用户："+u.getName()+"("+u.getSex()+")"+"  等级："+u.getDan()+"-"+u.getGrade();
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    public void init() {
    /*    this.u = MapUserModel.getU();//获取用户的数据模型
        if(u == null){
            u = new User();
        }*/
        cb = new PCChessBoard(this);
        cb.setClickable(CAN_NOT_CLICK_INFO);//设置棋盘是否能被点击
        cb.setBounds(210, 40, 570, 585);
        cb.setVisible(true);
        start = new JButton("开始游戏");//设置名称，下同
        start.setBounds(780, 240, 200, 50);//设置起始位置，宽度和高度，下同
        start.setBackground(new Color(50, 205, 50));//设置颜色，下同
        start.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        start.addActionListener(this);
        restart = new JButton("重新开始");    //设置名称，下同
        restart.setEnabled(false);              //游戏未开始，该按钮不可用
        restart.setBounds(780, 295, 200, 50);//设置起始位置，宽度和高度，下同
        restart.setBackground(new Color(50, 205, 50));//设置颜色，下同
        restart.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        restart.addActionListener(this);
        back = new JButton("悔  棋");
        back.setBounds(780, 350, 200, 50);
        back.setBackground(new Color(85, 107, 47));
        back.setFont(new Font("宋体", Font.BOLD, 20));
        back.addActionListener(this);
        exit = new JButton("改变电脑等级");
        exit.setBackground(new Color(218, 165, 32));
        exit.setBounds(780, 405, 200, 50);
        exit.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        exit.addActionListener(this);
        gradeHistory = new JButton("历史战绩");
        gradeHistory.setBounds(780, 185, 200, 50);
        gradeHistory.setBackground(new Color(85, 107, 47));
        gradeHistory.setFont(new Font("宋体", Font.BOLD, 20));
        gradeHistory.addActionListener(this);
        people = new JLabel("    玩 家:");
        people.setOpaque(true);
        people.setBackground(new Color(82, 109, 165));
        people.setBounds(10, 410, 200, 50);
        people.setFont(new Font("宋体", Font.BOLD, 20));
        computer = new JLabel("    电 脑:");
        computer.setOpaque(true);
        computer.setBackground(new Color(82, 109, 165));
        computer.setBounds(10, 75, 200, 50);
        computer.setFont(new Font("宋体", Font.BOLD, 20));
        timecount = new JLabel("    计时器:");
        timecount.setBounds(320, 1, 200, 50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));
        plv = new JLabel("    等 级: "+u.getDan()+"-"+u.getGrade());
        plv.setOpaque(true);
        plv.setBackground(new Color(82, 109, 165));
        plv.setBounds(10, 465, 200, 50);
        plv.setFont(new Font("宋体", Font.BOLD, 20));
        clv = new JLabel("    等 级: "+clv_text);
        clv.setOpaque(true);
        clv.setBackground(new Color(82, 109, 165));
        clv.setBounds(10, 130, 200, 50);
        clv.setFont(new Font("宋体", Font.BOLD, 20));
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
        image1 = Toolkit.getDefaultToolkit().getImage("images/black.png");//添加黑棋图片
        jLabel1 = new JLabel();
        add(jLabel1);
        imageIcon1 = new ImageIcon(image1);
        jLabel1.setIcon(imageIcon1);

        setVisible(true);
        jLabel1.setBounds(130, 75, 200, 50);
        image2 = Toolkit.getDefaultToolkit().getImage("images/white.png");//添加白棋图片
        jLabel2 = new JLabel();
        imageIcon2 = new ImageIcon(image2);
        jLabel2.setIcon(imageIcon2);
        add(jLabel2);
        setVisible(true);
        jLabel2.setBounds(130, 410, 200, 50);
        add(cb);
        add(back);
        add(start);
        add(restart);
        add(exit);
        add(gradeHistory);
        add(people);
        add(computer);
        add(timecount);
        add(clv);
        add(plv);
        add(situation1);
        add(situation2);
    }

    /**
     * 点击事件
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == start) {
            start.setEnabled(false);
            start.setText("正在游戏");
            logger.info("开始游戏");
            start.setFont(new Font("宋体", Font.BOLD, 20));
            //设置玩家状态
            situation1.setText("    状态:等待...");
            situation2.setText("    状态:下棋...");
            cb.setClickable(MainBoard.CAN_CLICK_INFO);
            //加载计时线程
            timer = new TimeThread(label_timeCount);
            timer.setCb(cb);
            timer.start();
            //设置结果为1，游戏继续
            cb.setResult(1);
            //设置重新开始按钮可用
            restart.setEnabled(true);
        }
        //点击重新开始操作
        else if(source == restart){
            //重新开始前让用户确认
            int rs = JOptionPane.showConfirmDialog(this, "您将丢失现在的游戏进度，确定要重新开始吗?", "提示", JOptionPane.YES_NO_OPTION);
            //用户点击了"是"
            if (rs == JOptionPane.YES_OPTION){
                //重置游戏
                cb.gameRestart();
                //自动开始新一局游戏
                /*start.setText("正在游戏");*/
                logger.info("开始游戏");
                start.setFont(new Font("宋体", Font.BOLD, 20));
                //设置玩家状态
                situation1.setText("    状态:等待...");
                situation2.setText("    状态:下棋...");
                cb.setClickable(MainBoard.CAN_CLICK_INFO);
                //加载计时线程
                timer = new TimeThread(label_timeCount);
                timer.start();
                //设置结果为1，游戏继续
                cb.setResult(1);
            }
        }
        //点击悔棋后的操作
        else if (source == back) {
            cb.backstep();
            logger.info("玩家选择悔棋");
        }
        //点击返回后的操作，返回模式选择界面
        else if (source == exit) {
                dispose();
                new SelectLevel(u.getName());
                logger.info("玩家选择返回主菜单");
        }
        //玩家点击了历史战绩
        else if(source == gradeHistory){
            GradeRecordDialog grd = new GradeRecordDialog(this, "历史战绩", u);
        }
    }

}