package com.hqt.communication.multigram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hqt.communication.R;
import com.hqt.communication.config.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import static com.hqt.communication.config.Constant.PORT;

/**
 * Reference:
 * https://developer.android.com/reference/java/net/MulticastSocket.html
 */
public class ReceivedMultigramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_multigram);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress group = InetAddress.getByName(Constant.HOST);
                    byte[] buf = new byte[256];
                    MulticastSocket clientSocket = new MulticastSocket(PORT);
                    clientSocket.joinGroup(group);

                    while (true) {
                        DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                        clientSocket.receive(msgPacket);
                        final String msg = new String(buf, 0, buf.length);
                        System.out.println("Socket 1 received msg: " + msg);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReceivedMultigramActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
