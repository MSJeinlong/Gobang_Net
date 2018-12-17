package com.demo1.client.view;

import com.demo1.client.comman.*;
import com.demo1.client.tools.Judge;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.NetTool;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Gobang
 * @Date: 2018-12-12 15:42
 * @Author: long
 * @Description:
 */
public class PPChessBoard_Demo2 extends ChessBoard {
    private int role; //角色
    private PPMainBoard_Demo2 mb;
    private int step[][] = new int[30 * 30][2];//定义储存步数数组
    private int stepCount = 0;//初始化数组
    //加载黑白棋子，用于判定玩家所执棋
    private ImageIcon imageIcon1 = new ImageIcon(blackChess);
    private ImageIcon imageIcon2 = new ImageIcon(whiteChess);
    private Logger logger = Logger.getLogger("棋盘");
    private User u;         //用户
    private int rounds;     //回合数
    private boolean turnToMe;   //我的回合

    /**
     * 构造函数，初始化棋盘的图片，初始化数组
     *
     * @param mb 人人对战页面
     */
    public PPChessBoard_Demo2(PPMainBoard_Demo2 mb) {
        super();
        this.mb = mb;
        this.u = mb.getU();
        /*//设置先开始游戏的玩家执白
        setRole(Chess.WHITE);*/
    }

    public boolean isTurnToMe() {
        return turnToMe;
    }

    public void setTurnToMe(boolean turnToMe) {
        this.turnToMe = turnToMe;
    }

    /**
     * 保存黑白棋子的坐标于二维数组中
     *
     * @param posX
     * @param posY
     */
    private void saveStep(int posX, int posY) {
        stepCount++;
        step[stepCount][0] = posX;
        step[stepCount][1] = posY;
    }

    /**
     * 悔棋，去掉黑白棋子各一个
     */
    public void backstep() {
        // TODO Auto-generated method stub
        if (stepCount >= 2) {
            chess[step[stepCount][0]][step[stepCount][1]] = 0;
            chess[step[stepCount - 1][0]][step[stepCount - 1][1]] = 0;
            stepCount = stepCount - 2;
        }
    }

    /**
     * 设置棋子横坐标
     *
     * @param x,y,r 横坐标,纵坐标,对方的角色黑/白
     */
    public void setCoord(int x, int y, int r) {
        //对方执白，自己执黑
        if (r == Chess.WHITE) {
            role = Chess.BLACK;
            mb.getLabel1().setIcon(imageIcon2);
            mb.getLabel2().setIcon(imageIcon1);
        }
        //对方执黑，自己执白
        else {
            role = Chess.WHITE;
            mb.getLabel1().setIcon(imageIcon1);
            mb.getLabel2().setIcon(imageIcon2);
        }
        chess[x][y] = r;
        saveStep(x, y); //保存坐标
        int winner = Judge.whowin(x, y, chess, r);
        WinEvent(winner);
        setClickable(MainBoard.CAN_CLICK_INFO);
        repaint();
    }

    /**
     * 设置角色
     *
     * @param role 角色
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * 获得角色
     *
     * @return 角色
     */
    public int getRole() {
        return role;
    }

    /**
     * 从父类继承的方法，自动调用，绘画图形
     *
     * @param g 该参数是绘制图形的句柄
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * 获得结果
     *
     * @return 结果
     */
    public int getResult() {
        return result;
    }

    /**
     * 胜利事件
     *
     * @param winner 胜方
     */
    public void WinEvent(int winner) {
       /* //白棋赢
        if (winner == Chess.WHITE) {
            //中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            *//*mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);*//*
            result = Chess.WHITE;
            if(role == Chess.WHITE) {
                //我方赢了
                JOptionPane.showMessageDialog(mb, "恭喜！白棋获胜\n你赢了~", "提示", JOptionPane.INFORMATION_MESSAGE);

            } else {
                //我方输了
                JOptionPane.showMessageDialog(mb, "很遗憾！白棋获胜\n你输了~","提示", JOptionPane.INFORMATION_MESSAGE);
            }
            logger.info("白棋获胜！初始化页面");
            setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
            //初始化页面
            initArray();
            mb.getLabel().setText(null);
        }
        //黑棋赢
        else if (winner == Chess.BLACK) {
            //中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
           *//* mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);*//*
            result = Chess.BLACK;
            if(role == Chess.BLACK) {
                //我方赢了
                JOptionPane.showMessageDialog(mb, "恭喜！黑棋获胜\n你赢了~", "提示", JOptionPane.INFORMATION_MESSAGE);

            } else {
                //我方输了
                JOptionPane.showMessageDialog(mb, "很遗憾！黑棋获胜\n你输了~","提示", JOptionPane.INFORMATION_MESSAGE);
            }
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            logger.info("黑棋获胜！初始化页面");
            //初始化页面
            initArray();
            mb.getLabel().setText(null);
        }*/

        //胜负已分
        if (winner == Chess.WHITE || winner == Chess.BLACK) {
            System.out.println("胜负已分，游戏结束");
            //中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            //是白棋获胜
            if (winner == Chess.WHITE) {
                result = Chess.WHITE;
            }
            //否则是黑棋获胜
            else {
                result = Chess.BLACK;
            }
            //计算回合数
            if (stepCount % 2 == 0) {
                rounds = stepCount / 2;
            } else {
                rounds = stepCount / 2 + 1;
            }
            //要发送给服务器的信息包
            Message m = new Message();
            GradeRecord gr = new GradeRecord();
            m.setMesType(MessageType.UPDATE_GRADE);
            User rival = mb.getRival();
            gr.setRivalName(rival.getName());
            gr.setRivalLevel(rival.getDan() + "-" + rival.getGrade());
            gr.setModel("对弈");
            gr.setRounds(rounds);
            //获取当前系统时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String time = df.format(new Date()).toString();
            gr.setTime(time);
            gr.setUserName(u.getName());
            gr.setUserLevel(u.getDan() + "-" + u.getGrade());
            //获胜的是自己
            if (role == result) {
                gr.setWin("胜");
                //赢了就升一级
                u.upgrade(1);
                if(mb.getTimer().isTimeOver()){
                    //对方超时，我方胜利
                    JOptionPane.showMessageDialog(mb, "对方超出时间限制\n恭喜！你赢了~", "信息", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(mb, "恭喜！你赢了~", "信息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //获胜的是对手，自己输了
            else {
                gr.setWin("负");
                //输了就降一级
                u.degrade(1);
                if(mb.getTimer().isTimeOver()){
                    //我方超时，我方输了
                    JOptionPane.showMessageDialog(mb, "您超出了时间限制\n很遗憾！你输了~", "信息", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mb, "很遗憾！你输了~", "信息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //更新User的status为等待对战
            u.setStatus(User.WAIT_VERSUS);
            //设置Message的u
            m.setU(u);
            //设置Message的gr
            m.setGr(gr);
            //请求服务器保存记录
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                oos.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //初始化页面
            JButton jb_findRival =  mb.getFindRival();
            jb_findRival.setText("寻找对手");
            jb_findRival.setEnabled(true);
            mb.getSituation1().setText("    状态:");
            mb.getSituation2().setText("    状态:");
            mb.getTalkArea().setText(null);
            mb.getBack().setEnabled(true);
            mb.getExitGame().setEnabled(true);
            mb.setRival(new User());
            String title = "欢乐五子棋--当前用户："+u.getName()+"("+u.getSex()+")"+"  等级："+u.getDan()+"-"+u.getGrade();
            mb.setTitle(title);
            mb.getMyLevel().setText("");
            mb.getRivalLevel().setText("");
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            logger.info("黑棋获胜！初始化页面");
            initArray();
            mb.getLabel().setText(null);
        }
    }

    //对方刚刚走了一步棋，现在到我了
    public void myTurn(Coord coord) {
        turnToMe = true;
        mb.getBack().setEnabled(false);     //设置不能请求悔棋
        //先判断对手是否赢了
        //计时线程中断
        try {
            mb.getTimer().interrupt();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //显示对方刚下的棋子
        int x = coord.getX();
        int y = coord.getY();
        int rivalRole = coord.getRole();
        chess[x][y] = rivalRole;
        saveStep(x, y);
        savePreChess(x, y);
        //判断对手是否赢了
        int winner = Judge.whowin(x, y, chess, rivalRole);
        WinEvent(winner);

        //胜负未分，游戏继续
        if (result != GAME_OVER) {
            //设置棋盘可以点击
            setClickable(MainBoard.CAN_CLICK_INFO);
            //重新启动计时线程
            mb.timer = new TimeThread(mb.getLabel());
            mb.timer.setPPChessBoard(this);
            mb.timer.start();
            mb.getSituation1().setText("    状态:等待...");
            mb.getSituation2().setText("    状态:下棋...");
        }
    }

    /**
     * 按下鼠标时，记录鼠标的位置，并改写数组的数值，重新绘制图形
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (clickable == PPMainBoard.CAN_CLICK_INFO) {
            chessX = e.getX();
            chessY = e.getY();
            //限定点击区域为棋盘区域
            if (chessX < 524 && chessX > 50 && chessY < 523 && chessY > 50) {
                float x = (chessX - 49) / 25;
                float y = (chessY - 50) / 25;
                int x1 = (int) x;
                int y1 = (int) y;
                //如果这个地方没有棋子
                if (chess[x1][y1] == Chess.BLANK) {
                    //请求服务器转发这一步棋的信息
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream
                                (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
                        //设置消息包
                        Message m = new Message();
                        m.setMesType(MessageType.CHESS_COORD);
                        //设置棋子坐标
                        m.setCoord(new Coord(x1, y1, role));
                        m.setSender(u.getName());
                        m.setGetter(mb.getRival().getName());

                        oos.writeObject(m);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    turnToMe = false;
                    mb.getBack().setEnabled(true);      //设置可以请求悔棋
                    mb.getSituation1().setText("    状态:下棋...");
                    mb.getSituation2().setText("    状态:等待...");
                  /*  if (role == Chess.WHITE) {
                        logger.info("白棋落子:" + x1 + "," + y1);
                        mb.getSituation1().setText("    状态:下棋...");
                        mb.getSituation2().setText("    状态:等待...");
                    } else if (role == Chess.BLANK) {
                        logger.info("黑棋落子:" + x1 + "," + y1);
                        mb.getSituation1().setText("    状态:下棋...");
                        mb.getSituation2().setText("    状态:等待...");
                    }*/
                    //计时线程中断
                    try {
                        mb.getTimer().interrupt();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    chess[x1][y1] = role;
                    saveStep(x1, y1);     //保存这一步的棋子坐标，用于悔棋
                    savePreChess(x1, y1); //保存最新棋子的坐标，用于红框标识
                    //判断输赢
                    int winner = Judge.whowin(x1, y1, chess, role);
                    WinEvent(winner);
                    setClickable(MainBoard.CAN_NOT_CLICK_INFO);

                    //如果游戏未定胜负
                    //重新启动计时线程
                    if (result != GAME_OVER) {
                        //重新启动计时线程
                        mb.timer = new TimeThread(mb.getLabel());
                        mb.timer.setPPChessBoard(this);
                        mb.timer.start();
                    }
                }
            }
        }
    }


    /**
     * 鼠标点击事件
     *
     * @param e
     **/
    @Override
    public void mouseMoved(MouseEvent e) {
        if (clickable == MainBoard.CAN_CLICK_INFO) {
            mousex = e.getX();
            mousey = e.getY();
            repaint();
        }
    }
}
