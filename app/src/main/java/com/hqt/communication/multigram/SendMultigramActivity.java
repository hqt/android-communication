package com.hqt.communication.multigram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hqt.communication.R;
import com.hqt.communication.config.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Reference:
 * https://developer.android.com/reference/java/net/MulticastSocket.html
 */
public class SendMultigramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_multigram);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "Hello";
                    InetAddress group = InetAddress.getByName(Constant.HOST);
                    MulticastSocket s = new MulticastSocket(6789);
                    s.joinGroup(group);

                    while (true) {
                        DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, Constant.PORT);
                        s.send(hi);

                        // get their responses!
                        byte[] buf = new byte[1000];
                        DatagramPacket recv = new DatagramPacket(buf, buf.length);
                        s.receive(recv);


                        Log.e("hqthao", "send send send ...");
                        Thread.sleep(5000);
                    }

                    // OK, I'm done talking - leave the group...
                    // s.leaveGroup(group);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
