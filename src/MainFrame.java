import javax.swing.*;

public class MainFrame extends JFrame {
    private static GamePanel gamePanel; //Declare the gamePanel
    JPanel backgroundPanel; //Declare the backgroundPanel


    //TetrisFrame constructor
    public MainFrame() {
        //Set up the frame properties
        setTitle("Connect 4"); //Title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Behavior on close, exits the program when the frame is closed
        setResizable(false); //sets the frame to a fixed size, not resizeable by a user

        startNewGame(); //Start a new game

        //Set the frame focusable for KeyListener, allowing it to accept key inputs when in focus
        setFocusable(true);
        //Set the frame visible
        setVisible(true);
    }

    //Replaces old gamePanel with a new instance, then restarts the timer
    public void startNewGame(){
        if(backgroundPanel != null) remove(backgroundPanel); //Removes the old gamePanel object from the frame

        //GamePanel is a panel within the frame that contains sub panels related to gameplay
        backgroundPanel = new JPanel(); //Create the BackgroundPanel object
        backgroundPanel.setBackground(Constants.BACKGROUND_COLOR); //Set the background color of the panel
        gamePanel = new GamePanel(); //Create new gamePanel object
        backgroundPanel.add(gamePanel); //Add the gamePanel object to the backgroundPanel
        add(backgroundPanel); //Add the backgroundPanel to the frame
        pack(); //Resizes the frame to fit the gamePanel
    }

    static GamePanel getGamePanel(){
        return gamePanel;
    }

    //Main method
    public static void main(String[] args) {
        new MainFrame(); //Create new TetrisFrame object
    }
}