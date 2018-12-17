package com.demo1.client.model;

import com.demo1.client.comman.Coord;
import com.demo1.client.comman.Message;
import com.demo1.client.comman.MessageType;
import com.demo1.client.tools.MapGradeRecordDialog;
import com.demo1.client.tools.MapFindRival;
import com.demo1.client.tools.MapPPMainBoard;
import com.demo1.client.tools.MapUserModel;
import com.demo1.client.view.PPMainBoard;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: Gobang
 * @Date: 2018-12-10 20:38
 * @Author: long
 * @Description:客户端和服务器通信的线程
 */
public class ClientConnServerThread extends Thread {

    private Socket s;
    private ObjectInputStream ois;      //输入流
    private ObjectOutputStream oos;     //输出流
    private Message getMess;            //接收到的信息
    private Message sendMess;           //要发送的信息
    private PPMainBoard ppMB;     //用户对应的游戏界面

    public ClientConnServerThread(Socket s) {
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    @Override
    public void run() {
        while (true) {
            //不停读取从服务器发来的信息
            try {
                ois = new ObjectInputStream(s.getInputStream());
                getMess = (Message) ois.readObject();

             /*   //普通的信息包
                int messType = m.getMesType();
                if(messType == MessageType.COMMON_MESSAGE){
                    //把从服务器获得消息，显示到该显示的聊天界面
                    //稍后再做
                }
                //返回在线用户的信息包*/

                //判断服务器发过来的消息包的类型
                switch (getMess.getMesType()) {
                    //服务器返回用户对战记录
                    case MessageType.RECORD_RESPONSE:
                        //拿到客户端的历史记录Dialog,更新数据
                        MapGradeRecordDialog.getGradeRecordDialog(getMess.getU().getName()).updateGradeRecord(getMess.getGrlist());
                        break;
                    //服务器返回等待对战的用户
                    case MessageType.RESPONSE_WAIT_VERSUS_USERS:
                        //得到消息包的等待对战的用户列表，在FindRival显示
                        MapFindRival.getFindRival(getMess.getU().getName()).showWaitUser(getMess.getUserList());
                        break;
                    //服务器转发过来的挑战信息
                    case MessageType.LAUNCH_A_CHALLENGE:
                        //取得该用户的游戏界面
                        ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        //通知该用户收到了挑战信息
                        int n = JOptionPane.showConfirmDialog
                                (ppMB, getMess.getSender() + "向你发起了挑战，是否接受?", "挑战请求", JOptionPane.YES_NO_CANCEL_OPTION);
                        //根据n回应对方的挑战
                        sendMess = new Message();
                        sendMess.setU(MapUserModel.getUser(getMess.getGetter()));
                        sendMess.setMesType(MessageType.RESPONSE_A_CHALLENGE);
                        //设置sendMess的发起人和接收人
                        sendMess.setSender(getMess.getGetter());
                        sendMess.setGetter(getMess.getSender());
                        //该用户接受了挑战
                        if (n == JOptionPane.YES_OPTION) {
                            sendMess.setAcceptChallenge(true);
                            //设置对手Rival
                            ppMB.setRival(getMess.getU());
                            //被挑战者优先开始
                            ppMB.myFirstStart();

                            //设置"寻找对手"按钮状态
                            JButton jb1 = ppMB.getFindRival();
                            jb1.setText("游戏开始...");
                            jb1.setEnabled(false);
                        } else {
                            //用户拒绝了挑战
                            sendMess.setAcceptChallenge(false);
                        }
                        //把信息发送给服务器，让服务器进行转发
                        oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(sendMess);
                        break;
                    //对手回应了挑战信息
                    case MessageType.RESPONSE_A_CHALLENGE:
                        ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        JButton jb_findRival = ppMB.getFindRival();
                        //通知用户收到了对手的回应
                        //判断对方是接受挑战还是拒绝挑战
                        //若对手接受挑战
                        if (getMess.isAcceptChallenge()) {
                           /* JOptionPane.showMessageDialog
                                    (ppMB1, getMess.getSender() + "接受了你的挑战，现在开始游戏", "对方回应", JOptionPane.INFORMATION_MESSAGE);*/
                            //设置对手
                            ppMB.setRival(getMess.getU());
                            //让对手先开始游戏
                            ppMB.rivalFirstStart();
                            //设置"寻找对手"按钮状态
                            jb_findRival.setText("游戏开始...");
                        }
                        //若对手拒绝挑战
                        else {
                            JOptionPane.showMessageDialog
                                    (ppMB, "很遗憾，" + getMess.getSender() + "拒绝了你的挑战", "对方回应", JOptionPane.INFORMATION_MESSAGE);
                            jb_findRival.setText("寻找对手");
                            jb_findRival.setEnabled(true);
                        }
                        break;
                    //处理对手发送过来的聊天信息
                    case MessageType.SEND_CHAT_CONTENT:
                        if (ppMB == null) {
                            ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        }
                        //把对手发过来的聊天信息显示在聊天面板内
                        ppMB.getTalkArea().append(getMess.getSender() + "：" + getMess.getChatContent() + "\n");
                        break;
                    //处理对手发过来的棋子信息
                    case MessageType.CHESS_COORD:
                        if (ppMB == null) {
                            ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        }
                        //获取对手刚下的棋子信息
                        Coord coord = getMess.getCoord();
                        //显示对手的棋子，我的回合
                        ppMB.getPpcb().myTurn(coord);
                        break;
                    //应答对方发送的请求悔棋操作
                    case MessageType.REQUEST_UNDO_CHESS:
                        if (ppMB == null) {
                            ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        }
                        int n1 = JOptionPane.showConfirmDialog
                                (ppMB, getMess.getSender() + "请求悔棋，您是否同意?", "悔棋请求", JOptionPane.YES_NO_CANCEL_OPTION);
                        //根据n1回应对方的挑战
                        sendMess = new Message();
                        sendMess.setMesType(MessageType.RESPONSE_UNDO_CHESS);
                        sendMess.setSender(ppMB.u.getName());
                        sendMess.setGetter(getMess.getSender());
                        //该用户同意悔棋
                        if (n1 == JOptionPane.YES_OPTION) {
                            sendMess.setAgreedUndoChess(true);
                            //执行悔棋操作
                            ppMB.getPpcb().backstep();
                            //轮到对手的回合
                            ppMB.getPpcb().undoToRivalTurn();
                        }
                        //该用户不同意悔棋
                        else {
                            sendMess.setAgreedUndoChess(false);
                        }
                        oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(sendMess);
                        break;
                    //收到对方的悔棋回应
                    case MessageType.RESPONSE_UNDO_CHESS:
                        if (ppMB == null) {
                            ppMB = MapPPMainBoard.getPPMainBoard(getMess.getGetter());
                        }
                        //如果对方同意悔棋，执行悔棋操作
                        if (getMess.isAgreedUndoChess()) {
                            ppMB.getPpcb().backstep();
                            //轮到我的回合
                            ppMB.getPpcb().undoToMyTurn();
                        } else {
                            JOptionPane.showMessageDialog
                                    (ppMB, "很遗憾，" + getMess.getSender() + "拒绝了你的悔棋请求~", "信息", JOptionPane.INFORMATION_MESSAGE);
                        }
                        ppMB.getBack().setText("悔棋");
                        ppMB.getBack().setEnabled(true);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
