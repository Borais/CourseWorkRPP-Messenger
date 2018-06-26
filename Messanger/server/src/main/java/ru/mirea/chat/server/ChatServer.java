package ru.mirea.chat.server;

import ru.mirea.network.TCPConnectionListener;
import sun.rmi.transport.tcp.TCPConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener{



    public static void main(String[] args) throws IOException {
        new ChatServer();


    }

    private final ArrayList<ru.mirea.network.TCPConnection> connections = new ArrayList<ru.mirea.network.TCPConnection>();

    private  ChatServer(){
        System.out.println("Server running...");
        try{
            ServerSocket serverSocket = new ServerSocket(2620);
            while (true){
                try{
                    new ru.mirea.network.TCPConnection(this, serverSocket.accept());
                }catch (IOException e){
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
//      это может пригодиться

//    public void serverSender() throws IOException {
//        ServerSocket serverSocket = new ServerSocket(8189);
//        Socket sr = serverSocket.accept();
//        FileInputStream fr = new FileInputStream("C:\\Program Files (x86)\\image.jpg");
//        byte b[] = new byte[20002];
//        fr.read(b,0,b.length);
//        OutputStream os = sr.getOutputStream();
//    }


    public synchronized void onConnectionReady(ru.mirea.network.TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    public synchronized void onReceiveString(ru.mirea.network.TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    public synchronized void onDisconnect(ru.mirea.network.TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    public synchronized void onException(ru.mirea.network.TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value){
            System.out.println(value);
            final int ent = connections.size();
            for (int i = 0; i < ent; i++) connections.get(i).sendString(value);
    }
}
