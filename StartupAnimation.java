import javax.swing.*;
import java.awt.*;

public class StartupAnimation {

    public static void main(String[] args) {
        // Create and show the splash screen
        SplashScreen splash = new SplashScreen();
        splash.showSplash();

        // Simulate some startup tasks (e.g., loading resources)
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(50); // Simulate time-consuming task
                splash.updateProgress(i+11); // Update progress bar
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen and open the main application
        splash.closeSplash();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

class SplashScreen {
    private final JWindow window;
    private final JProgressBar progressBar;

    public SplashScreen() {
        window = new JWindow();
        JPanel content = (JPanel) window.getContentPane();
        content.setLayout(new BorderLayout());

        // Background image with overlay and rounded corners
        JLabel background = new JLabel(new ImageIcon("Images/bg_img.gif"));
        // background.setLayout(new BorderLayout());
        content.add(background);

        // Title in the center
        
        JLabel label = new JLabel(" LAB EXAM MONITORING SYSTEM " , JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.BLACK);
        content.add(label, BorderLayout.NORTH);

        // Progress bar at the bottom
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(5, 12, 156)); 
        progressBar.setBackground(new Color(254, 250, 246)); // Dark background
        content.add(progressBar, BorderLayout.SOUTH);

        window.setSize(850, 550);
        window.setLocationRelativeTo(null);
    }

    public void showSplash() {
        window.setVisible(true);
    }

    public void updateProgress(int percent) {
        progressBar.setValue(percent);
    }

    public void closeSplash() {
        window.setVisible(false);
        window.dispose();
    }
}

// This Page is all about Startup animation of application
