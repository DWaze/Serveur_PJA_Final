package com.example.lhadj.pja_final;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */
public class SendingUDP extends Thread{

    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    @Override
    public void run() {
        DatagramSocket sc = null;
        try {
            sc = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                /// requesting the latest list of files from the users
                sc.setBroadcast(true);
                RequestDetail rq = new RequestDetail("",new ArrayList<ListFile>(),"","",0,"");
                byte[] b =serialize(rq);
                DatagramPacket pack = new DatagramPacket(b,b.length, InetAddress.getByName("192.168.202.255"),9999);
                sc.send(pack);
                sleep(2000);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
