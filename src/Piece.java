import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Piece extends JComponent {

    private boolean isRed;
    
    private boolean isGhost;
    private Piece ghostPiece;
    private int boardX, boardY;
    private double pixelX, pixelY;
    private double desiredX, desiredY;
    private double velocityX, velocityY;
    private long time;
    private long lastUpdateTime;
    private boolean droppingInBoard;


    //type is a number 0 - 6 that refers to the type of tetromino
    public Piece(boolean isRed, int x, int y, boolean isGhost) {
        this.isRed = isRed;

        this.isGhost = isGhost;

        setPixelCoords(x, y);
        setVisible(true);

        desiredX = x;
        desiredY = y;
        droppingInBoard = false;

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
        lastUpdateTime = System.nanoTime() / 1000000;
    }

    public void dropIn() {
        desiredY = 0;
    }

    public void dropOut() {
        desiredY = Constants.BOARD_HEIGHT + 2 * Constants.PIECE_SIZE;
        droppingInBoard = true;
    }

    public void startDrop(int cellsFilled) {
        if (desiredX == -1) return;
        setGhost(false);
        desiredY = Constants.BOARD_HEIGHT - ((cellsFilled + 1) * Constants.PIECE_SIZE) - Constants.BOTTOM_GAP_HEIGHT;
        droppingInBoard = true;
    }

    public boolean doDrop(){
        if(droppingInBoard && (isGhost || Math.abs(pixelX - desiredX) > Constants.PIECE_SIZE / 8.0)) return false;
        if (pixelY == desiredY && velocityY == 0) return droppingInBoard;

        double dt = (time - lastUpdateTime) / 1000.0;
        double newY = pixelY + (velocityY * dt) + (0.5 * Constants.GRAVITY * dt * dt);

        Car car = new Car("Toyota", "Camry", 2020);
        Car car2 = new Car("Toyota", "Tacoma", 2022);




        double newVelocityY = velocityY + Constants.GRAVITY * dt;

        if (newY > desiredY) {
            double overshoot = newY - desiredY;
            double initialVelocity = Math.sqrt(velocityY * velocityY + 2 * Constants.GRAVITY * overshoot);

            double overshootTime = (newVelocityY - initialVelocity) / Constants.GRAVITY;

            velocityY = -(Constants.BOUNCE_MULTIPLIER * initialVelocity);
            double correctedY =
                    pixelY - overshoot
                            + (velocityY * overshootTime)
                            + (0.5 * Constants.GRAVITY * overshootTime * overshootTime);
            velocityY += Constants.GRAVITY * overshootTime;
            setPixelCoords(pixelX, correctedY);
        }
        else {
            velocityY = newVelocityY;
            setPixelCoords(pixelX, newY);
        }

        if(Math.abs(pixelY - desiredY) < .5 && Math.abs(velocityY) < 20) {
            velocityY = 0;
            setPixelCoords(pixelX, desiredY);
            if(!droppingInBoard) return false;
            setBoardCoords(
                    (int)Math.round(pixelX / Constants.PIECE_SIZE) - 1,
                    (int)Math.round(pixelY / Constants.PIECE_SIZE) - 1);
            return true;
        }
        return false;
    }

    public boolean moveToCol(double x) {
        if(!isGhost) return false;
        desiredX = x;
        return true;
    }

    public void updateColPos() {
        if (desiredX == -1) return;
        boardX = (int) Math.round((pixelX - Constants.BOARD_EDGE_WIDTH) / Constants.PIECE_SIZE);

        if(Math.abs(pixelX - desiredX) < .5) {
            setPixelCoords(desiredX, pixelY);
            velocityX = 0;
            return;
        }

        double dt = (time - lastUpdateTime) / 1000.0;
        velocityX = Math.clamp((desiredX > pixelX ? 1 : -1) * (Math.pow(Math.abs(desiredX - pixelX), 1.2) * Constants.X_VEL_GAIN + 5), -Constants.MAX_X_VELOCITY, Constants.MAX_X_VELOCITY);
        setPixelCoords(pixelX + (velocityX * dt), pixelY);
    }

    public boolean updatePosition() {
        time = System.nanoTime() / 1000000;
        updateColPos();
        boolean dropped = doDrop();
        lastUpdateTime = time;
        return dropped;
    }

    public boolean isRed() {
        return isRed;
    }

    public boolean isBelowBoard() {
        return pixelY > Constants.BOARD_HEIGHT + Constants.PIECE_SIZE;
    }

    public void setPixelCoords(double x, double y) {
        if (pixelX == x && pixelY == y) return;

        boolean skipBounds = (int)pixelX == x && (int)pixelY == y;
        this.pixelX = x;
        this.pixelY = y;

        if (skipBounds) return;
        setBounds(
                (int) x,
                (int) y,
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
        new Thread(() -> {
            System.out.println(new BoardCalculator(board, 4, isRed).calculateBestMove());
        }).start();
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
                isGhost,
                g2d);
    }

    public void setGhost(boolean ghost) {
        this.isGhost = ghost;
    }

    public boolean isDropping() {
        return droppingInBoard;
    }
}
