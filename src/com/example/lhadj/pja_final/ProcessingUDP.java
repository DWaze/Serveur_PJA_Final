package com.example.lhadj.pja_final;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */
public class ProcessingUDP extends Thread {
    DatagramPacket packet ;
    ObjectInputStream inputStream;
    RequestDetail requestDetail;
    public static ArrayList<RequestDetail> requestDetails=new ArrayList<RequestDetail>();


    //Serialisation and Deserialization of the object it allow us to transform the object into bytes and vise ver sa


    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }

    public ProcessingUDP(DatagramPacket packet) {
        this.packet = packet;
    }

    @Override
    public void run() {

        try {
            //Saving the ip address of the server

            String myAddress = "/192.168.202.1";

            //getting the ip address of the sender
            String SenderAdr = packet.getAddress()+"";

            //void processing the server ip address request when broadCast is made
            if(!myAddress.equals(SenderAdr)){

                //Deserialize the byte data that has been received to transform it an object RequestDetail
                RequestDetail requestDetail = (RequestDetail)deserialize(packet.getData());

                //Reading the request Details and or instructions
                switch (requestDetail.getQuery()){
                    case 0 :        // Refreshing the current list of files
                        int position =-1;
                        /*checking if the files of the owner of the received list of files already exists in the current
                        list of files*/
                        for(int i = 0;i<=requestDetails.size()-1;i++){
                            if(requestDetails.get(i).getOwnerIp().equals(requestDetail.getOwnerIp())){
                                //if it exists the position of the owner in the array list of files is saved
                                position=i;
                            }
                        }

                        if(position!=-1){
                            //if the owner exists in the current list of files we only make an update of it's element in
                            //the current list of files
                            requestDetails.set(position,requestDetail);
                        }else{
                            //if the owner is not in the current list of files add them to the list
                            requestDetails.add(requestDetail);
                        }
                        break;

                    case 1:        //Download Request from another client
                        //add the download request to the cue
                        ListeningTCP.addesInetAddresses.add(packet.getAddress());
                        DatagramSocket socket = new DatagramSocket();
                        byte[] b = serialize(requestDetail);
                        DatagramPacket datagramPacket = new DatagramPacket(b,b.length, InetAddress.getByName(requestDetail.getDestIp()),9999);
                        socket.send(datagramPacket);
                        socket.close();
                        break;
                    case 2:        //Sending latest List of files
                        DatagramSocket socket1= new DatagramSocket();
                        byte[] b1= serialize(requestDetails);
                        DatagramPacket packet1 = new DatagramPacket(b1,b1.length,packet.getAddress(),4444);
                        socket1.send(packet1);
                        socket1.close();
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
