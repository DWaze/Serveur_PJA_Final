package com.example.lhadj.pja_final;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */
public class ListeningTCP extends Thread {

    //the array list that contains the Que of the ip addresses if the download requests
    public static ArrayList<InetAddress> addesInetAddresses = new ArrayList<InetAddress>() ;


    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(8888);
            while (true){
                //listening the tcp in port 8888
                Socket s = socket.accept();
                //saving the socket in the variable S and start a thread that handle the socket and restart the receiving
                ProcessingTCP processingTCP = new ProcessingTCP(s);
                processingTCP.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
