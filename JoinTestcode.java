/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.exam.monitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Swapnil Patil
 */
public class JoinTestcode extends JFrame {
    public JTextField tx1;
    Boolean isConnected = false;
    public  Socket sock;
    public  BufferedReader reader;
    public  PrintWriter writer;
    
    public InetAddress inet;
    public String pcname;
    public String ipaddress;
    String username, address;
    ArrayList<String> users = new ArrayList();
    int port = 2222;
    String ex ;
    JoinTestcode(){
        setTitle("Lab Exam Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        // Set the main layout
        setLayout(new BorderLayout());

        // Define colors
        Color lightGray = new Color(240, 240, 240); // Light Gray
        Color darkGray = new Color(64, 64, 64); // Dark Gray
        Color darkBlue = new Color(30, 144, 255); // Dark Blue
        Color white = Color.WHITE;

        // Create a panel with a titled border
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(darkGray), " Student Section ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14), darkGray));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(lightGray);

        // Create a label and text field
        JLabel lb1 = new JLabel("Enter the Test code:");
        lb1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lb1.setForeground(darkBlue);
        tx1 = new JTextField();
        tx1.setMaximumSize(new Dimension(200, 30));
        tx1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the panel
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(lb1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(tx1);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        JButton btn1 = new JButton("Join The Code");
        btn1.setBackground(darkBlue);
        btn1.setForeground(white);
        buttonPanel.add(btn1);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED); // Change color as needed
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new SelectionMode().setVisible(true); // Show the selection mode frame
            }
        });

        // Add the back button to the top-left corner
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST); // Align the button to the left
        add(topPanel, BorderLayout.NORTH);

        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener for the button
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                if (tx1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Code cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Join the Test of code: " + tx1.getText(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    ex = fetchip(tx1.getText());
//                    Clientconnection cn = new Clientconnection();
//                    cn.conToExaminer(ex);
                    setVisible(false);
                    new NewJFrame().setVisible(true);
                    
                }
                
                
            }
        });
    
}
    public void connectToExaminer(String ex){
        
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

    
   public String fetchip(String examcode) {
            String code = null;
            Connection con = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/Lab_exam_monitoring", "lab", "lab");
                String sql = "SELECT USER_ID FROM TESTCODE WHERE CODE = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,examcode);

                // Execute the query
                rs = pstmt.executeQuery();

                // Process the result
                if (rs.next()) {
                    code = rs.getString("USER_ID");
                } else {
                    System.out.println("No exam code found for IP address: " + code);
                }
            } catch (Exception e) {
                e.printStackTrace();  // Print the stack trace for debugging
            } finally {
                // Close resources in the reverse order of their creation
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Inside the code"+code);
            return code;
        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JoinTestcode().setVisible(true);
            }
        });
    }

}
