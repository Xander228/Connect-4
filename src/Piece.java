import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Piece extends JComponent {

    private boolean isRed;
    
    private final boolean ghost;
    private Piece ghostPiece;
    private int boardX, boardY;
    private double pixelX, pixelY;
    private double desiredX, desiredY;
    public double velocityX, velocityY;
    public double maxVelocityX = 10000;
    private long lastDropTime;
    private long lastUpdateTime;

    //type is a number 0 - 6 that refers to the type of tetromino
    public Piece(boolean isRed, int x, int y, boolean ghost) {
        this.isRed = isRed;

        this.ghost = ghost;

        setPixelCoords(x, y);
        setVisible(true);

        EventQueue.invokeLater(this::addListeners);
    }


    private void addListeners(){
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPixelCoords(pixelX, pixelY);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setPixelCoords(pixelX, pixelY);
            }
        });

    }

    private void removeAllListeners() {
        for(ComponentListener listener : getComponentListeners()){
            removeComponentListener(listener);
        }
    }


    public void resetClock() {
        lastDropTime = System.currentTimeMillis();
        lastUpdateTime = System.currentTimeMillis();
    }
    public boolean drop(int cellsFilled) {
        double boundary = Constants.BOARD_HEIGHT - ((cellsFilled + 1) * Constants.PIECE_SIZE);

        if (pixelY == boundary && velocityY == 0) {
            setPixelCoords(pixelX, 0);
            return true;
        }
        long time = System.currentTimeMillis();
        double dt = (time - lastDropTime) / 1000.0;
        double newY = pixelY + (velocityY * dt) + (0.5 * Constants.GRAVITY * dt * dt);
        velocityY += Constants.GRAVITY * dt;

        if (newY > boundary) {
            double overshoot = newY - boundary;
            double initialVelocity = Math.sqrt(velocityY * velocityY - 2 * Constants.GRAVITY * overshoot);
            double overshootTime = 2 * overshoot / (velocityY + initialVelocity);
            if(Double.isNaN(overshootTime)) {
                setPixelCoords(pixelX, boundary);
                velocityY = 0;
                return true;
            }
            velocityY = -(.4 * initialVelocity);
            double correctedY =
                    pixelY - overshoot
                    + (velocityY * overshootTime)
                    + (0.5 * Constants.GRAVITY * overshootTime * overshootTime);
            setPixelCoords(pixelX, newY);
            velocityY += Constants.GRAVITY * overshootTime;


            setPixelCoords(pixelX, correctedY);
            if(Math.abs(velocityY) < 30) {
                velocityY = 0;
                setPixelCoords(pixelX, boundary);
            }
        }
        else setPixelCoords(pixelX, newY);

        lastDropTime = time;
        return false;
    }


    public double getPixelX() {
        return pixelX;
    }

    public double getPixelY() {
        return pixelY;
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public boolean isRed() {
        return isRed;
    }

    public void moveTo(double x, double y) {
        desiredX = x;
        desiredY = y;
    }

    public void updatePosition() {
        long time = System.currentTimeMillis();
        double dt = (time - lastUpdateTime) / 1000.0;
        velocityX = Math.clamp((desiredX > pixelX ? 1 : -1) * Math.pow(Math.abs(desiredX - pixelX), 1.2) * 5, -maxVelocityX, maxVelocityX);


        setPixelCoords(pixelX + (velocityX * dt), pixelY);
        lastUpdateTime = time;
    }

    public void setPixelCoords(double x, double y) {
        this.pixelX = x;
        this.pixelY = y;
        setBounds(
                (int)x,
                (int)y,
                Constants.PIECE_SIZE * 5,
                Constants.PIECE_SIZE * 5);
    }
    
    public void setBoardCoords(int x, int y) {
        this.boardX = x;
        this.boardY = y;

        setPixelCoords(
                x * Constants.PIECE_SIZE + Constants.BOARD_EDGE_WIDTH,
                y * Constants.PIECE_SIZE + Constants.DROP_ZONE_HEIGHT
        );
    }


    public void lock(int[][] board) {
        board[boardX][boardY] = isRed? 1 : -1;

    }

    public boolean isValidPosition(int x, int y, int[][]board){
        return !(x < 0 || y < 0 ||
                x > Constants.BOARD_COLS - 1 ||
                y > Constants.BOARD_ROWS - 1) && board[x][y] == 0;
    }

    public boolean isValidPosition(int[][] board) {
        return !(boardX < 0 || boardY < 0 ||
                boardX > Constants.BOARD_COLS - 1 ||
                boardY > Constants.BOARD_ROWS - 1) && board[boardX][boardY] == 0;
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2d);

        Draw.piece(
                0,
                0,
                isRed ? 1 : -1,
                ghost,
                g2d);

    }

}
