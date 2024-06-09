import javax.swing.*;
import java.awt.*;

public class FooterUtil {

    public static void addFooter(JFrame frame) {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        JLabel footerLabel = new JLabel("Made by: Your_Name", JLabel.CENTER);
        footerPanel.add(footerLabel, BorderLayout.CENTER);
        
        frame.add(footerPanel, BorderLayout.SOUTH);
    }
}

