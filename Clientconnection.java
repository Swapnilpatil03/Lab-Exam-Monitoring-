/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.exam.monitoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Swapnil Patil
 */
public class Clientconnection {
    Boolean isConnected = false;
    public  Socket sock;
    public  BufferedReader reader;
    public  PrintWriter writer;
    String username, address;

    int port = 2222;
    public void conToExaminer(String ex){
        if (isConnected == false) 
        {
           

            try 
            {
                address = ex;
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
//                writer.println(username + ":has connected.:Connect");
//                writer.flush(); 
//                ta_chat.append("You are Connected to Server.\n");
                showConfirmDialog(null,"You are Connected to Server. !");
                Thread listener = new Thread(new ServerListener());
		listener.start();
                isConnected = true; 
            } 
            catch (Exception exx) 
            {
//                ta_chat.append("Cannot Connect! Try Again. \n");
               // tf_username.setEditable(true);
            }
            
           
            
        } else if (isConnected == true) 
        {
//            ta_chat.append("You are already connected. \n");
        }
    }
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (sock != null) {
                sock.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class ServerListener implements Runnable {
        
        Thread listener1 ;
        public void run() {
            try {
                System.out.println("iniiii");
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message from server: " + message);
                    // Parse timer settings and update UI
                    if (message.contains(",hours")) {
                        int hours = Integer.parseInt(message.replace(",hours", ""));
                        int minutes = Integer.parseInt(reader.readLine().replace(",minutes", ""));
                        int seconds = Integer.parseInt(reader.readLine().replace(",seconds", ""));
                      System.out.println(hours+minutes+seconds);
                    }
                    if(message.contains("Browser Detection , off")){
                        
                        System.out.println("Browser detection off");
                    }
                    if(message.contains("Browser Detection , On")){
                        
                        System.out.println("Browser detection On");
                    }
                    if(message.contains("Application Detection , off")){
                        
                        System.out.println("Application detection off");
                    }
                    if(message.contains("Application Detection , On")){
                        
                        System.out.println("Application detection On");
                    }
                    if(message.contains("Pendrive Detection , off")){
                        
                        System.out.println("Pendrive detection off");
                        listener1.interrupt();
                    }
                    if(message.contains("Pendrive Detection , On")){
                        
                        System.out.println("Pendrive detection On");
                        listener1 = new Thread(new Pendrive());
                        listener1.start();
                    }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
private class Pendrive implements Runnable {
        private volatile boolean running = true;
    public void run(){
                try
                {
                    File[] oldListRoot = File.listRoots();
                    while (running && !Thread.currentThread().isInterrupted()   ) {
                try {
                    System.out.println("Inside thread");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (File.listRoots().length > oldListRoot.length) {
                    //System.out.println("new drive detected");
                    oldListRoot = File.listRoots();
                    System.out.println("drive" + oldListRoot[oldListRoot.length - 1] + " detected");
                                            
                     showMessageDialog(null,"PENDRIVE DETECTED: Please Remove your PENDRIVE");
                                             

                } else if (File.listRoots().length < oldListRoot.length) {
                    System.out.println(oldListRoot[oldListRoot.length - 1] + " drive removed");
                                            showMessageDialog(null,"PENDRIVE DETECTED and REMOVED...");
                     showMessageDialog(null,"YOU MUST NOT DO IT AGAIN...");
                    oldListRoot = File.listRoots();
                                            
                                                    
                                                    
                }
            }
                    }
                    catch(Exception e)
                    {

                    }           
        
    }
    public void stop() {
        running = false;
    }
}
}
