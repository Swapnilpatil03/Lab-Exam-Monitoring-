import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ExaminerSide extends JFrame {

    public ExaminerSide() {
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
        JTextField tx1 = new JTextField();
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
                String code = tx1.getText();
                if (code.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Code cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Starting moderation with code: " + code, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExaminerSide().setVisible(true);
            }
        });
    }
}
