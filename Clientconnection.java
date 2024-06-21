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
    public boolean start = false;
    
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
                
                isConnected = true; 
                Thread readerThread = new Thread(new IncomingReader());
                readerThread.start();
                
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
 
    private class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                   if(message.contains("pdon")){
                       System.out.println(message);
                       Thread pendriveThread = new Thread(new Pendrive());
                       pendriveThread.start();
                       start = true;
                       System.out.println(start);
                   }
                   if(message.contains("pdoff")){
                       System.out.println(message);
                       start=false;
                       
                   }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    

    private class Pendrive implements Runnable {
        public void run() {
            try
                    {
                        File[] oldListRoot = File.listRoots();
                        while (start) {
					try {
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
    }
 
}
