package com.demo1.client.view;

import com.demo1.client.comman.Chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * 棋盘页面父类
 * 初始化棋盘页面
 *
 * @author: admin
 */
public class ChessBoard extends JPanel implements MouseMotionListener, MouseListener {
    public static final int GAME_OVER = 0; //结束游戏
    public static final int COLS = 19;
    public static final int RAWS = 19;//棋盘的行数和列数
    public int result = 1; //结果，为0则结束游戏
    public Image whiteChess;
    //黑白棋子
    public Image chessBoardImage;
    public Image blackChess;
    //鼠标经过显示图片
    public Image position;
    //棋子的横坐标
    public int chessX;
    //棋子的纵坐标
    public int chessY;
    //鼠标坐标
    public int mousex;
    public int mousey;
    //自动校正后的最新下的棋子的坐标
    public int preChessX;
    public int preChessY;
    //游戏进行的回合数
    public int rounds;

    public JLabel timecount;
    //棋盘上隐形的坐标，每一个小区域代表一个数组元素
    public int chess[][] = new int[COLS][RAWS];
    public int clickable;       //棋盘是否可以点击
    public int model;           //游戏模式，对弈或训练


    /**
     * 构造函数，初始化棋盘的图片，初始化数组
     *
     * @param
     */

    ChessBoard() {
        //加载棋盘、黑棋、白棋、位置图片
        chessBoardImage = Toolkit.getDefaultToolkit().getImage("images/chessBoard.jpg");
        whiteChess = Toolkit.getDefaultToolkit().getImage("images/white.png");
        blackChess = Toolkit.getDefaultToolkit().getImage("images/black.png");
        position = Toolkit.getDefaultToolkit().getImage("images/position.gif");
        initArray();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * 设置面板是否可以点击
     *
     * @param clickable
     */
    public void setClickable(int clickable) {
        this.clickable = clickable;
    }

    /**
     * 初始化数组为BLANK
     */
    public void initArray() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                chess[i][j] = Chess.BLANK;
            }
        }
    }

    /**
     * 从父类继承的方法，自动调用，绘画图形
     *
     * @param g 该参数是绘制图形的句柄
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(chessBoardImage, 35, 35, null);
        //页面可点击，则显示预下棋框
        if (clickable == MainBoard.CAN_CLICK_INFO) {
            this.showPosition(g);       //显示蓝色方框
        }
        //判断棋盘上是否存在棋子
        if(isExistChess()){
            this.drawRedRect(g);    //红色方框标识最新的棋子
            /*System.out.println("画红色方框");*/
        } else {
            /*System.out.println("棋盘上没有棋子");*/
        }
        //对应位置显示黑棋、白棋
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (chess[i][j] == Chess.BLACK) {
                    g.drawImage(blackChess, 49 + i * 25, 50 + j * 25, null);
                } else if (chess[i][j] == Chess.WHITE) {
                    g.drawImage(whiteChess, 49 + i * 25, 50 + j * 25, null);
                }
            }
        }

    }

    public void savePreChess(int x, int y){
        //校正落子的坐标
        this.preChessX = 49 + x * 25;
        this.preChessY = 49 + y * 25;
    }

    /**
     * 坐标图片跟随鼠标显示
     *
     * @param g
     */
    private void showPosition(Graphics g) {
        int FrameWidth = getWidth(); //面板宽度
        int FrameHeight = getHeight(); //面板高度
        int x = (FrameWidth - 475) / 2;
        int y = (FrameHeight - 489) / 2;

        int px = mousex - (mousex - x) % 25;//25为棋盘格子大小
        int py = mousey - (mousey - y) % 25;
        if (px < x) px = x;
        if (py < y) py = y;
        if (px > (x + (475 - 25)))
            px = x + (475 - 25);
        if (py > (y + (475 - 25)))
            py = y + (475 - 25);

        //显示鼠标所在的棋盘格子，画出贴图
        g.drawImage(position, px, py, 25, 25, null);
    }

    /**
     * 红色矩形标识最新下的一个棋子
     *
     **/
    private void drawRedRect(Graphics g){
        g.setColor(Color.red);
        g.drawRect(preChessX, preChessY, 20, 21);
        g.drawRect(preChessX - 1, preChessY - 1 , 20, 21);
        g.setColor(Color.black);
    }

    /**
     * 检查棋盘是否有棋子了
     * */
    private boolean isExistChess(){
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if(chess[i][j] != Chess.BLANK){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * 按下鼠标时，记录鼠标的位置，并改写数组的数值，重新绘制图形
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
