import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionMode extends JFrame {

    public SelectionMode() {
        // Set the properties for the JFrame
        setTitle("Student-Examiner Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Create a panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Select Mode");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 63, 65));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(211, 211, 211));
        add(titlePanel, BorderLayout.NORTH);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(new Color(211, 211, 211));

        // Create the Student button
        JButton studentButton = new JButton("Student");
        studentButton.setFont(new Font("Arial", Font.PLAIN, 18));
        studentButton.setBackground(new Color(30, 144, 255));
        studentButton.setForeground(Color.WHITE);
        studentButton.setFocusPainted(false);
        studentButton.setPreferredSize(new Dimension(120, 40));
        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Student mode selected");
            }
        });

        // Create the Examiner button
        JButton examinerButton = new JButton("Examiner");
        examinerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        examinerButton.setBackground(new Color(34, 139, 34));
        examinerButton.setForeground(Color.WHITE);
        examinerButton.setFocusPainted(false);
        examinerButton.setPreferredSize(new Dimension(120, 40));
        examinerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new TestCode().setVisible(true));
            }
        });

        // Add buttons to the panel with some spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(studentButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(examinerButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectionMode();
            }
        });
    }
}
