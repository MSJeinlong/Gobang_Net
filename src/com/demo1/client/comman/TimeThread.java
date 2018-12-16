package com.demo1.client.comman;

import com.demo1.client.view.PCChessBoard;

import javax.swing.*;

/**
 * 计时线程
 *
 * @author admin
 */
public class TimeThread extends Thread {
    JLabel label; //计时标签
    private boolean timeOver;   //30s 时间是否结束
    PCChessBoard cb;

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
                if(cb != null) {
                    //超过时间限制，电脑获胜
                    cb.WinEvent(Chess.BLACK);
                }
               /* startTime = System.currentTimeMillis();     //重新获取开始时间*/
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

    public void setCb(PCChessBoard cb) {
        this.cb = cb;
    }

    public boolean isTimeOver() {
        return timeOver;
    }
}
