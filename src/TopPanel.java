import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class TopPanel extends JPanel {

    public TopPanel() {
        // Initialize components, set layout, etc.
        setPreferredSize( new Dimension(Constants.BOARD_WIDTH,
                                        Constants.SCORE_PANEL_HEIGHT));
        setBackground(Constants.BACKGROUND_COLOR);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5 -Constants.SCORE_PANEL_HEIGHT / 2));

        InputStream stream = TopPanel.class.getResourceAsStream("texgyreadventor-bold.otf");
        Font font = new Font("Arial", Font.BOLD, 36);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException e) {
            System.out.println("Font format exception");
        } catch (IOException e) {
            System.out.println("Load error");
        }

        font = font.deriveFont(Font.BOLD, 40);

        JLabel title = new JLabel("CONNECT 4", JLabel.CENTER);
        title.setFont(font);
        title.setForeground(Constants.PRIMARY_COLOR);
        add(title, BorderLayout.CENTER);




    }

    
}
