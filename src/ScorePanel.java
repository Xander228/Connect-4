import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private final JLabel scoreLabel;
    private final JLabel lineLabel;
    private final JLabel comboLabel;

    private int score;
    private int lines;
    private int combo;
    private int comboCounter;

    public ScorePanel() {
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
        scoreLabel = new ScoreLabel();
        lineLabel = new ScoreLabel();
        comboLabel = new ScoreLabel();

        add(scoreLabel, BorderLayout.WEST);
        add(comboLabel, BorderLayout.CENTER);
        add(lineLabel, BorderLayout.EAST);

        this.update();
    }

    public void addScore(int score) {
        this.score += score;
        this.update();
    }

    public void addLines(int lines) {
        this.lines += lines;
        int lineMultiplier = 10 * Math.max(lines - 1, 1);
        int comboMultiplier = 1 + combo;
        this.score += lines * lineMultiplier * comboMultiplier;

        if(lines == 0) {
            comboCounter--;
            if (comboCounter <= 0) {
                comboCounter = 0;
                combo = 0;
            }
        } else {
            comboCounter = 3;
            combo++;
        }

        this.update();
    }
    
    public void update() {
        scoreLabel.setText("Score: " + score);
        lineLabel.setText("Lines: " + lines);
        comboLabel.setText("Combo: " + combo);
    }
    
}
