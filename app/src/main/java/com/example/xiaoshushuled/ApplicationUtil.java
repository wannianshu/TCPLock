package com.example.xiaoshushuled;

import android.app.Activity;
import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ApplicationUtil extends Application{

    private Socket socket;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    Activity activity;
    boolean tcpIs = false;
    public void init(String ip,String prot,Activity activity) {
        this.activity = activity;
        try {
            final String ADDRESS =ip;
            final int PORT = Integer.parseInt(prot) ;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //与服务器建立连接
                    try {
                        socket = new Socket(ADDRESS, PORT);
                        dos = new DataOutputStream(socket.getOutputStream());
                        dis = new DataInputStream(socket.getInputStream());
                        System.out.println("连接成功！！！！！！！！！！！");
                        tcpIs = true;
                        showToast("连接成功");
                    } catch (Exception e) {
                        System.out.println("连接失败？？？？？？？？？？？");
                        showToast("连接失败");
                        e.printStackTrace();
                    }
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean isTcpIs() {
        return tcpIs;
    }

    public void setTcpIs(boolean tcpIs) {
        this.tcpIs = tcpIs;
    }

    public void SendM(String m){
        SendMessage sendMessage = new SendMessage(m);
        sendMessage.start();
    }
    /**
     * 发送指令
     */
    class  SendMessage extends Thread{
        String message;
        public SendMessage(String m){
            this.message = m;
        }
        @Override
        public void run() {
            if(dos != null){
                try {
                    byte[] m = message.getBytes();
                    dos.write(m);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(String msg){
        Looper.prepare();
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
