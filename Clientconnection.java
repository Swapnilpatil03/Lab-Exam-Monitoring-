/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.exam.monitoring;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.Timer;

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
    public boolean startp = false;
    public boolean startbr = false;
    public boolean startapd = false;
     ObjectInputStream in;
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
                in = new ObjectInputStream(sock.getInputStream());
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
 ObjectInputStream objectInputStream = null;
 ArrayList<String> browserListData = new ArrayList<>();
 ArrayList<String> appListData = new ArrayList<>();
 private static int hours = 0 ;
 private static int minutes = 0;
 private static int seconds = 0;
 StudentMain studentMain = StudentMain.getInstance();
    private class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    
                   if(message.contains("pdon")){
                       System.out.println(message);
                       Thread pendriveThread = new Thread(new Pendrive());
                       pendriveThread.start();
                       startp = true;
                       System.out.println(startp);
                   }
                   if(message.contains("pdoff")){
                       System.out.println(message);
                       startp=false;
                   }
                   
                   // -------------> for Browser Detection
                   if(message.contains("brdon")){
                      
                       System.out.println(message);
                       startbr = true;
                       Thread browserd = new Thread(new Browsedetect());
                       browserd.start();
                     
                   }
                   if(message.contains("brdoff")){
                       System.out.println(message);
                       startbr=false;
                       browserListData.clear();
                   }
                   if(message.contains(",banbrowsers")){
                       String b = message.replace(",banbrowsers","");
                       browserListData.add(b); 
                       
                   }
                   if(message.contains(",Browseadd")){
                       String ad = message.replace(",Browseadd","");
                       System.out.println("WE GOT THE MESSAGE"+ad);
                       browserListData.add(ad);
                       
                   }
                   if(message.contains(",Browseremove")){
                       String ad = message.replace(",Browseremove", "");
                        System.out.println("WE GOT THE MESSAGE"+ad);
                       browserListData.remove(ad);
                       
                   }
                   // ----------------> App detection
                   
                   if(message.contains("appdon")){
                       System.out.println(message);
                       Thread appd = new Thread(new Appdetect());
                       appd.start();
                       startapd = true;
                     
                   }
                   if(message.contains("appdoff")){
                       System.out.println(message);
                       startapd = false;
                   }
                   if(message.contains(",banapps")){
                       String b = message.replace(",banapps","");
                       appListData.add(b); 
                   }                 
                   if(message.contains(",appadd")){
                       String ad = message.replace(",Browseadd","");
                       System.out.println("WE GOT THE MESSAGE"+ad);
                       appListData.add(ad);
                       
                   }
                   if(message.contains(",appremove")){
                       String ad = message.replace(",Browseremove", "");
                        System.out.println("WE GOT THE MESSAGE"+ad);
                       appListData.remove(ad);
                       
                   }
                   if(message.contains(",hours")){
                       hours = Integer.parseInt(message.replace(",hours", ""));
                   }
                   if(message.contains(",minutes")){
                       minutes = Integer.parseInt(message.replace(",minutes", ""));
                   }if(message.contains(",seconds")){
                       seconds = Integer.parseInt(message.replace(",seconds", ""));
                       System.out.println("Data  is :"+hours+"\n"+minutes+"\n"+seconds);
                       
                       if (studentMain != null) {
                       studentMain.onTime(hours , minutes , seconds);
                       }else {
                        System.out.println("StudentMain instance is null");
                    }
                       
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
                        while (startp) {
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
    
    private class Browsedetect implements Runnable{
        
        public void run(){
            try
           {
               String line;
           
               String pidInfo =" ";
            
                
                 while(startbr){
                 pidInfo =" ";
                 Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");

                 BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

                     while ((line = input.readLine()) != null) 
                     {
                              pidInfo+=line; 
                     }
                      input.close();
                      for (String browser : browserListData) {
                          System.out.println(browser);
                      if(pidInfo.contains(browser))
                      {   
                           closeApplication(browser);
                                
                      }
                      }
               //       Thread.sleep(5000);
                }
               // showConfirmDialog(null,"HELLO UC BROWSER DETECTED !!!!!!");
                
           }
           catch(Exception e)
           {
               
           }
            
        }
        
        
    }
    
    
    
    
    
    private class Appdetect implements Runnable{
        public void run(){
            if(startapd)
                {
                    

                    String appname;
                   
                    try
                    {
                        String line;

                        String pidInfo =" ";
                            
                           while(startapd)
                           {
                               
                            pidInfo =" ";
                            Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");

                                BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

                                while ((line = input.readLine()) != null)
                                {
                                     line.toLowerCase();
                                    pidInfo.toLowerCase();
                                    pidInfo+=line;
                                }
                                input.close();
                                for (String app : appListData){
                                    System.out.println(app);
                                    if(pidInfo.contains(app))
                                    {
                                        closeApplication(app);                                     
                                    }
                                }
                         
                            }
                        }
                        catch(Exception e)
                        {

                        }
                    }
        }
    }
    private void closeApplication(String appname) {
        try {
            // Get the process ID (PID) of the application
             JFrame parentFrame = new JFrame();

             System.out.println("Dialog");
        // Show the JOptionPane dialog
        JOptionPane optionPane = new JOptionPane("Restricted App opened!!!", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = optionPane.createDialog("Confirm");

        // Set dialog to be modal (blocks user input to other windows while visible)
        dialog.setModal(true);
        dialog.setAlwaysOnTop(true);

        // Create a thread to close the dialog after 2 seconds
        Thread closeDialogThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Wait for 2 seconds
                if (dialog.isShowing()) {
                    dialog.dispose();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        closeDialogThread.start();

        // Show the dialog
        dialog.setVisible(true);            
            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String pid = null;
            
            while ((line = input.readLine()) != null) {
                if (line.toLowerCase().contains(appname)) {
                    String[] parts = line.trim().split("\\s+");
                    pid = parts[1]; // The PID is usually the second part
                    break;
                }
            }
            input.close();

            // Terminate the process using its PID
            if (pid != null) {
                Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
