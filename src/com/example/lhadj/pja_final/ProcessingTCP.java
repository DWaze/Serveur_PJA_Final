package com.example.lhadj.pja_final;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lhadj on 12/25/2016.
 */
public class ProcessingTCP extends Thread {
    Socket s ;

    public ProcessingTCP(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {

            //staring the redirecting of the flux of data froma user to another using the que list of download requests
            BufferedInputStream in = new BufferedInputStream(s.getInputStream());
            //creating a connection with the user that asks for a download from the list of download que
            Socket s =  new Socket(ListeningTCP.addesInetAddresses.get(0),7777);
            BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
            while (true){
                //reading from a client and sending to the other client without saving any data to the server
                int line = in.read();
                if(line<0){
                    break;
                }
                out.write(line);
            }
            ListeningTCP.addesInetAddresses.remove(0);
            //closing  buffers and connections
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
