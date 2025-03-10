import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel() {
        // Initialize components, set layout, etc.
        setPreferredSize( new Dimension(Constants.BOARD_WIDTH,
                                        Constants.SCORE_PANEL_HEIGHT));
        setBackground(Constants.BACKGROUND_COLOR);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Constants.BACKGROUND_COLOR));
        setLayout(new BorderLayout(0, 0));


        class ScoreLabel extends JLabel {
            ScoreLabel(){
                super("", JLabel.CENTER);
                this.setFont(new Font("Arial", Font.BOLD, 16));
                this.setForeground(Constants.PRIMARY_COLOR);
                this.setBorder(BorderFactory.createMatteBorder(0, 15, 0, 15, Constants.BACKGROUND_COLOR));
            }
        }

    }

    
}
