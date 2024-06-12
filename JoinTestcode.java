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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

                    dispose();
                    new StudentSide().setVisible(true);
                }
            }
        });
    
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