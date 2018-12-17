package com.demo1.client.view;

import com.demo1.client.comman.*;
import com.demo1.client.model.*;
import com.demo1.client.tools.Judge;
import com.demo1.client.tools.MapClientConServerThread;
import com.demo1.client.tools.MapUserModel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 加载人机棋盘
 * 人机对战
 *
 * @author：admin
 */

public class PCChessBoard extends ChessBoard {
    private int role;   //人类角色
    private PCMainBoard mb;//定义主界面
    private Computer com;//定义电脑角色
    private int step[][] = new int[30 * 30][2];//定义储存步数数组
    private int stepCount = 0;//初始化数组
    private Coord coord = new Coord(); //坐标
    private User u;
    //打印日志
    private Logger logger1 = Logger.getLogger("棋盘");
    private Logger logger2 = Logger.getLogger("接收通道");

    public PCChessBoard(PCMainBoard mb) {
        this.mb = mb;
        this.u = mb.u;
        role = Chess.WHITE; //定义玩家执白
        com = new Computer();//加载电脑算法
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
     * 悔棋，去掉刚下的黑白两棋
     * chessCount - 2
     */
    public void backstep() {
        if (stepCount >= 2) {
            chess[step[stepCount][0]][step[stepCount][1]] = 0;              //撤销电脑下的棋子
            chess[step[stepCount - 1][0]][step[stepCount - 1][1]] = 0;      //撤销玩家下的棋子
            stepCount = stepCount - 2;
            savePreChess(step[stepCount][0], step[stepCount][1]);           //重新标识最新的一步棋
            rounds--;
        }
    }

    /**
     * 胜利事件
     *
     * @param winner 胜利方
     **/
    public void WinEvent(int winner) {

        //白棋或黑棋赢时
        if(winner == Chess.WHITE || winner == Chess.BLACK){
            reStartInit(winner);        //游戏结束，重新初始化
        }
        //出现游戏和棋的局面
        else if(isGameDraw()){
            reStartInit(winner);         //游戏结束，重新初始化
        }
    }


    //游戏正常结束，保存记录，重新初始化
    public void reStartInit(int winner){
        gameOver = true;
        try {
            mb.getTimer().interrupt();  //中断线程
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        /*恢复初始页面状态*/
        mb.getstart().setText("开始游戏");
        mb.getstart().setEnabled(true);
        mb.getRestart().setEnabled(false);
        mb.getSituation1().setText("    状态:");
        mb.getSituation2().setText("    状态:");

        //保存对战记录
        GradeRecord gr = new GradeRecord();
        //获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date()).toString();
        this.model = MapUserModel.getModel(u.getName());
        gr.setUserName(u.getName());
        gr.setRivalName("电脑");
        gr.setRounds(rounds);
        gr.setTime(time);
        gr.setUserLevel(u.getDan()+"-"+u.getGrade());
        //设置对战记录中的模式
        if(model == MapUserModel.VERSUS){
            gr.setModel("对弈");
        } else {
            gr.setModel("训练");
        }
        int level = mb.getLevel();
        //设置记录里的电脑水平
        if(level == SelectLevel.PRIMARY){
            gr.setRivalLevel("初级");
        }
        else if(level == SelectLevel.MEDIUM){
            gr.setRivalLevel("中级");
        } else if(level == SelectLevel.SENIOR){
            gr.setRivalLevel("高级");
        }

        //黑棋赢
        if(winner == Chess.BLACK){
            //电脑获胜
            gr.setWin("负");
            if(model == MapUserModel.VERSUS){
                //对弈模式下玩家段位可以上升，训练模式下无论输赢段位都不改变
                //电脑为初级时
                if(level == SelectLevel.PRIMARY){
                    //输给初级电脑连降2级
                    u.degrade(2);
                }
                else if(level == SelectLevel.MEDIUM){
                    //输给中级电脑降1级
                    u.degrade(1);
                } else if(level == SelectLevel.SENIOR){
                    //输给高级电脑降1级
                    u.upgrade(2);
                }
            }
            if(mb.getTimer().isTimeOver()){
                JOptionPane.showMessageDialog(mb, "超过时间限制，电脑(黑棋)获胜！\n共 " + rounds + " 个回合，很遗憾，你输了~");
            } else {
                JOptionPane.showMessageDialog(mb, "电脑(黑棋)获胜！\n共 " + rounds + " 个回合，很遗憾，你输了~");
            }
            logger1.info("黑棋获胜！初始化棋盘页面");
        }
        //白棋赢（玩家获胜）
        else if(winner == Chess.WHITE){
            gr.setWin("胜");
            //对弈模式下玩家段位可以上升，训练模式下无论输赢段位都不改变
            if(model == MapUserModel.VERSUS) {
                //电脑为初级时
                if (level == SelectLevel.PRIMARY) {
                    //初级太简单了，不升级
                } else if (level == SelectLevel.MEDIUM) {
                    //赢了中级电脑升1级
                    u.upgrade(1);
                } else if (level == SelectLevel.SENIOR) {
                    //赢了高级电脑升2级
                    u.upgrade(2);
                }
            }
            JOptionPane.showMessageDialog(mb, "恭喜！白棋获胜\n共 "+rounds+" 个回合, 你赢了~");
            logger1.info("白棋获胜！初始化棋盘页面");
        }
        //和棋
        else {
            gr.setWin("和");
            JOptionPane.showMessageDialog(mb, "平分秋色，和棋~\n共 "+rounds+" 个回合");
        }

        //更新数据
        mb.setU(u);
        //更新用户等级标签
        mb.getPlv().setText("    等 级: "+u.getDan()+"-"+u.getGrade());

        //通过通信线程向服务器发送消息包，请求保存对战记录和更新用户等级
        Message m = new Message();
        //设置消息包类型
        m.setMesType(MessageType.UPDATE_GRADE);
        m.setU(u);
        m.setGr(gr);

        try {
            //获取客户端到服务器的通信线程
            ObjectOutputStream oos = new ObjectOutputStream
                    (MapClientConServerThread.getClientConnServerThread(u.getName()).getS().getOutputStream());
            //通过对象流发送消息包
            oos.writeObject(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //更新MapUserModel里User的数据
        MapUserModel.addUser(u.getName(), u);
        setClickable(MainBoard.CAN_NOT_CLICK_INFO);
        initArray();    //初始化页面
        rounds = 0;     //重置chessCount
        mb.getLabel().setText(null);    //清空计时
    }

    /**
     * 提前结束游戏（没有胜负），重置游戏
     * */
    public void gameRestart(){
        gameOver = true;
        try{
            mb.getTimer().interrupt();
        } catch (Exception e){
            e.printStackTrace();
        }
        /*恢复初始页面状态*/
        mb.getstart().setText("开始游戏");
        mb.getSituation1().setText("    状态:");
        mb.getSituation2().setText("    状态:");
        initArray();        //初始化数据
        rounds = 0;     //初始化棋子个数
    }

    /**
     * 落子算法
     * 判断落子是否合法
     * @param e
     **/
    @Override
    public void mouseClicked(MouseEvent e) {
        int winner;
        if (clickable == MainBoard.CAN_CLICK_INFO) {
            //获取落子的坐标
            chessX = e.getX();
            chessY = e.getY();
            //将点击限制在棋盘内
            if (chessX < 524 && chessX > 50 && chessY < 523 && chessY > 50) {
                //计算出要贴图的坐标
                float x = (chessX - 49) / 25;
                float y = (chessY - 50) / 25;
                int x1 = (int) x;
                int y1 = (int) y;

                //如果这个地方没有棋子
                if (chess[x1][y1] == Chess.BLANK) {
                    chess[x1][y1] = role;
                    try {
                        mb.getTimer().interrupt();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    mb.getSituation1().setText("    状态:下棋...");
                    mb.getSituation2().setText("    状态:等待...");
                    logger2.info("等待对方消息");
                    savePreChess(x1, y1);   //保存最新下的棋子的坐标，用于画红色标识方框
                    saveStep(x1, y1);  //保存坐标，用于悔棋
                    rounds++;       //棋子总数+1
                    logger1.info("白棋落子:" + x1 + "," + y1);
                    setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                    winner = Judge.whowin(x1, y1, chess, role);//判断胜负
                    WinEvent(winner);
                    if (!gameOver) {
                        coord = com.computePos(Chess.BLACK, chess, mb.getLevel());//加载电脑棋类算法
                        chess[coord.getX()][coord.getY()] = Chess.BLACK;//输出黑棋图片
                        //重新启动计时线程
                        mb.timer = new TimeThread(mb.getLabel());
                        mb.timer.setPCChessBoard(this);
                        mb.timer.start();
                        logger1.info("黑棋落子:" + coord.getX() + "," + coord.getY());
                        mb.getSituation1().setText("    状态:等待...");
                        logger2.info("等待对方消息");
                        mb.getSituation2().setText("    状态:下棋...");
                        saveStep(coord.getX(), coord.getY());  //保存电脑坐标，用于悔棋
                        savePreChess(coord.getX(), coord.getY());   //保存电脑坐标，用于红框标识
                        winner = Judge.whowin(coord.getX(), coord.getY(), chess, Chess.BLACK);
                        WinEvent(winner);
                        if (!gameOver) {
                            setClickable(MainBoard.CAN_CLICK_INFO);
                        }
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
            //获得鼠标点击的坐标
            mousex = e.getX();
            mousey = e.getY();
            //重新绘图
            repaint();
        }
    }

}
