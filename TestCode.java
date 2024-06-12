
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TestCode extends JFrame {
    public String examcode;
    public JTextField tx1;
    String generatedCode;

    public TestCode() {
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
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(darkGray), "Exam Code Entry", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14), darkGray));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(lightGray);

        // Create a label and text field
        JLabel lb1 = new JLabel("Enter the code:");
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
        JButton generateCodeButton = new JButton("Generate Test Code");
        generateCodeButton.setBackground(Color.GREEN);
        generateCodeButton.setForeground(Color.black);
        generateCodeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(generateCodeButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        Random rand = new Random();
        
       generateCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rand_int1 = rand.nextInt(1000);
                int rand_int2 = rand.nextInt(1000);
                int rand_int3 = rand.nextInt(1000);
                generatedCode = ""+rand_int1+"-"+rand_int2+"-"+rand_int3;
                tx1.setText(generatedCode);
            }
        });

        
        
        
        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        JButton btn1 = new JButton("Start Moderating");
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
                    JOptionPane.showMessageDialog(null, "Starting moderation with code: " + tx1.getText(), "Info", JOptionPane.INFORMATION_MESSAGE);

                    dispose();
                    new ExaminerSideGUI().setVisible(true);
                    try {
                        dbconnection();
                    } catch (Exception ae) {
                        System.out.println(ae);
                    }
                }
            }
        });
    }

    public void dbconnection() throws ClassNotFoundException {
        PreparedStatement pstmt = null;
        Connection con = null;

        try {
            examcode = tx1.getText();
            System.out.println(examcode);

            String userId = getLocalIpAddress();
            System.out.println(userId);
            // Load the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Establish the connection to the database
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Lab_exam_monitoring", "lab", "lab");

            // Get the local IP address
            
            // Define the SQL statement with placeholders
            String sql = "INSERT INTO TESTCODE (USER_ID, CODE) VALUES (?, ?)";

            // Create a PreparedStatement
            pstmt = con.prepareStatement(sql);

            // Set the values for the placeholders
            pstmt.setString(1, userId);
            pstmt.setString(2, examcode); // Use the instance variable examcode

            // Execute the update
            pstmt.executeUpdate();

            System.out.println("Data saved.....");

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Driver Not Found");
        } catch (SQLException se) {
            System.out.println("Incorrect Query");
            se.printStackTrace();
        } catch (UnknownHostException uhe) {
            System.out.println("Unable to get local IP address");
            uhe.printStackTrace();
        } finally {
            // Close the PreparedStatement and Connection to free resources
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private String getLocalIpAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestCode().setVisible(true);
            }
        });
    }
}
