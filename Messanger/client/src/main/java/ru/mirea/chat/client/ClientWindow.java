package ru.mirea.chat.client;

import ru.mirea.network.TCPConnection;
import ru.mirea.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP_ADDR = "25.73.0.101";
    private static final int PORT = 2620;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientWindow();

            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("Borais");
    private final JTextField fieldInput = new JTextField();
    private final JButton senderButton = new JButton("Send File");

    private TCPConnection connection;



    private ClientWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);

        fieldInput.addActionListener(this);
        add(senderButton, BorderLayout.EAST);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);

        senderButton.addActionListener(actionListener);


        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);

        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }




    ActionListener actionListener = new TestActionListener();

    public void actionPerformed(ActionEvent e) {

        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(fieldNickname.getText() + ":" + msg);
//          senderButton.addActionListener(new ActionListener() {
//              public void actionPerformed(ActionEvent e) {
//                  ClientSender cs = new ClientSender();
//                  try {
//                      cs.CS();
//                  } catch (Exception e1) {
//                      e1.printStackTrace();
//                  }
//              }
//          });





// {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    byte b[] = new byte[40000];
//                    Socket sr = new Socket("Locahost", 6777);
//                    InputStream is = sr.getInputStream();
//                    FileOutputStream fr = new FileOutputStream("C:\\tmp\\rollyl.txt");
//                    is.read(b, 0, b.length);
//                    fr.write(b, 0, b.length);
//                }catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                } catch (FileNotFoundException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });



//        try {
//            ClientWindow.clientSender();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    }


    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close");
    }

    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private  synchronized  void printMsg(final String msg){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                TestFrame frame = new TestFrame();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
}
