import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameOverDialog extends JDialog{
    GameOverDialog(MainFrame frame, boolean tie, boolean redWin){
        super(frame,"GameOver");
        this.setSize(Constants.GAMEOVER_WIDTH, Constants.GAMEOVER_HEIGHT);
        this.setLocationRelativeTo(frame);
        this.setUndecorated(true);
        //this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setBackground(Constants.BACKGROUND_COLOR);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setBorder(BorderFactory.createEtchedBorder());
        dialogPanel.setBackground(Constants.ACCENT_COLOR);
        dialogPanel.setLayout(new BorderLayout(0, 0));

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Constants.BACKGROUND_COLOR);
        String textString = tie ? "TIE GAME" : (redWin ? "RED WINS" : "YELLOW WINS");
        JLabel text = new JLabel(textString, JLabel.CENTER);
        text.setFont(new Font("Arial", Font.BOLD, 26));
        text.setForeground(Constants.PRIMARY_COLOR);
        textPanel.add(text, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createMatteBorder(5, 25, 5, 25, Constants.BACKGROUND_COLOR));
        buttonPanel.setLayout(new BorderLayout(0, 0));
        buttonPanel.setBackground(Constants.BACKGROUND_COLOR);

        class GameButton extends JButton {
            GameButton(String text){
                super(text);
                this.setFocusable(false);
                this.setPreferredSize(new Dimension(100, 30));
                this.setBackground(Constants.ACCENT_COLOR_2);
                this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Constants.ACCENT_COLOR));
                this.setFont(new Font("Arial", Font.BOLD, 16));
                this.setForeground(Constants.PRIMARY_COLOR);
            }
        }

        JButton restart = new GameButton("New Game");

        restart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GameOverDialog.super.dispose();
                frame.startNewGame();
            }
        });

        JButton end = new GameButton("Exit");
        end.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });


        buttonPanel.add(restart, BorderLayout.WEST);
        buttonPanel.add(end, BorderLayout.EAST);

        dialogPanel.add(textPanel);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(dialogPanel);
        this.setVisible(true);
    }

}
