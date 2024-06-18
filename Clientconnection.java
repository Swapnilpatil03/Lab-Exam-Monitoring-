/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.exam.monitoring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showConfirmDialog;

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
    
}
