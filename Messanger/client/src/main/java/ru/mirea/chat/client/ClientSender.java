package ru.mirea.chat.client;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientSender {

    public  void CS() throws Exception{

        byte b[] = new byte [100000000];
        Socket sr = new Socket("25.73.0.101", 6777 );
        InputStream is = sr.getInputStream();
        FileOutputStream fr = new FileOutputStream("C:\\tmp\\image2.jpg");
        is.read(b, 0 ,b.length);
        fr.write(b, 0, b.length);
    }


}
