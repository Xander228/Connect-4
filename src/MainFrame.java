import javax.swing.*;

public class MainFrame extends JFrame {
    private static GamePanel gamePanel; //Declare the gamePanel


    //TetrisFrame constructor
    public MainFrame() {
        //Set up the frame properties
        setTitle("Block Blast"); //Title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Behavior on close, exits the program when the frame is closed
        setResizable(false); //sets the frame to a fixed size, not resizeable by a user

        //GamePanel is a panel within the frame that contains sub panels related to gameplay
        JPanel backgroundPanel = new JPanel(); //Create the BackgroundPanel object
        backgroundPanel.setBackground(Constants.BACKGROUND_COLOR); //Set the background color of the panel
        gamePanel = new GamePanel(); //Create new gamePanel object
        backgroundPanel.add(gamePanel); //Add the gamePanel object to the backgroundPanel
        add(backgroundPanel); //Add the backgroundPanel to the frame
        pack(); //Resizes the frame to fit the gamePanel

        //Set the frame focusable for KeyListener, allowing it to accept key inputs when in focus
        setFocusable(true);
        //Set the frame visible
        setVisible(true);
    }

    //Gets the scores and creates a game over object to display the GameOverPanel, then stops the game timer
    private void gameOver(){
        int[] scores = new int[3]; //Gets an array of the scores from the gamePanel
        new GameOver(this, scores[0], scores[1], scores[2]); //Creates the GameOver object which displays the scores and end options
    }

    //Replaces old gamePanel with a new instance, then restarts the timer
    public void startNewGame(){
        gamePanel = new GamePanel(); //Replaces the existing gamePanel object with the new one
        this.add(gamePanel); //Re-adds the new gamePanel object to the frame
        pack(); //Ensures that the new gamePanel is at its desired size and removes old panel
    }

    static GamePanel getGamePanel(){
        return gamePanel;
    }

    //Main method
    public static void main(String[] args) {
        new MainFrame(); //Create new TetrisFrame object
    }
}