package com.example.lhadj.pja_final;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by lhadj on 12/25/2016.
 */
public class ListeningUDP extends Thread {


    ProcessingUDP processingUDP;
    ListeningUDP udp ;



    @Override
    public void run() {

        DatagramSocket socket = null;
        try {

            //Creating the Datagrame Socket to receive UDP requests
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            while (true){

                //Creating a buffer to receive the request

                byte[] buffer = new byte[1024*1024];

                //creating the datagram

                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);

                // starting a different thread to Process the UDP packet and void over-charging the server

                processingUDP = new ProcessingUDP(packet);
                processingUDP.start();

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
