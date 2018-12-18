package com.demo1.client.comman;

import com.demo1.client.view.PCChessBoard;
import com.demo1.client.view.PPChessBoard;

import javax.swing.*;

/**
 * 计时线程
 *
 * @author admin
 */
public class TimeThread extends Thread {
    JLabel label; //计时标签
    private boolean timeOver;   //30s 时间是否结束
    PCChessBoard pccb;          //人机对战的面板
    PPChessBoard ppcb;    //人人对弈的面板

    public TimeThread(JLabel label) {
        this.label = label;
    }

    public void run() {
        //获取开始时间（毫秒），即当前时间
        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            long time = currentTime - startTime;
            //30秒倒计时
            label.setText(String.valueOf(30 - time / 1000));
            //如果30秒结束，督促
            if (label.getText().equals("0")) {
                timeOver = true;
                if(pccb != null) {
                    //超过时间限制，电脑获胜
                    pccb.WinEvent(Chess.BLACK);
                } else if(ppcb != null){
                    //超过时间限制
                    int rivalRole;      //对手执黑或执白
                    if(ppcb.getRole() == Chess.BLACK){
                        rivalRole = Chess.WHITE;
                    } else {
                        rivalRole = Chess.BLACK;
                    }
                    //该回合是我的回合，我方输了
                    if(ppcb.isTurnToMe()){
                        ppcb.WinEvent(rivalRole);
                    } else {
                        //该回合是对方的回合，我赢了
                        ppcb.WinEvent(ppcb.getRole());
                    }
                }
                timeOver = false;
                label.setText(null);
                this.interrupt();
            }
            //中断线程退出
            if (this.isInterrupted()) {
                System.out.println("中断线程退出");
                break;
            }
        }
    }

    public void setPCChessBoard(PCChessBoard pccb) {
        this.pccb = pccb;
    }

    public void setPPChessBoard(PPChessBoard ppcb){
        this.ppcb = ppcb;
    }

    public boolean isTimeOver() {
        return timeOver;
    }
}
