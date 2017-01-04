package com.example.lhadj.pja_final;

public class Main {

    public static void main(String[] args) {
//starting the server
        ListeningUDP listeningUDP = new ListeningUDP();
        listeningUDP.start();
        SendingUDP sendingUDP = new SendingUDP();
        sendingUDP.start();
        ListeningTCP listeningTCP = new ListeningTCP();
        listeningTCP.start();

    }
}
