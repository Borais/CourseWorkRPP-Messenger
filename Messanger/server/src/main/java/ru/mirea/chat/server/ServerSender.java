package ru.mirea.chat.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSender {

    public static void main(String[] args) throws IOException {
        while (true){
        ServerSocket serverSocket = new ServerSocket(6777);
        Socket sr = serverSocket.accept();
        FileInputStream fr = new FileInputStream("C:\\Images\\image1.jpg");
        byte b[] = new byte[100000000];
        fr.read(b,0,b.length);
        OutputStream os = sr.getOutputStream();
        os.write(b, 0 ,b.length);
    }
    }
}
