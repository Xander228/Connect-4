import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BoardPanel extends JPanel {
    //Game board array formatted in cols x rows (x,y)
    private int[][] board = new int[Constants.BOARD_COLS][Constants.BOARD_ROWS];
    
    //Create object variable to hold the current piece in hand
    private Piece ghost;

    //Create a counter to count the number of gameLoops elapsed since the last drop



    public BoardPanel(){
        // Initialize components, set layout
        setBackground(Constants.ACCENT_COLOR);
        setPreferredSize( new Dimension(Constants.BOARD_WIDTH, 
                                        Constants.BOARD_HEIGHT));

        setGhost(new Piece(Constants.RED_STARTS, 0, 0, true));

        Piece piece1 = new Piece(false, 0, 0, false);
        add(piece1);
        piece1.setBoardCoords(0,0);

        Piece piece2 = new Piece(true, 0, 0, false);
        add(piece2);
        piece2.setPixelCoords(Constants.PIECE_SIZE * 3 / 2,Constants.PIECE_SIZE * 3 / 4);

        Piece piece3 = new Piece(false, 0, 0, false);
        add(piece3);
        piece3.setPixelCoords(Constants.PIECE_SIZE * 5 / 2, Constants.PIECE_SIZE / 2);

        Piece piece4 = new Piece(true, 0, 0, false);
        add(piece4);
        piece4.setPixelCoords(Constants.PIECE_SIZE * 7 / 2, Constants.PIECE_SIZE / 4);

        BoardCover boardCover = new BoardCover(Constants.BOARD_EDGE_WIDTH, Constants.DROP_ZONE_HEIGHT);
        add(boardCover,0);

        addListeners();

        EventQueue.invokeLater(() -> new Thread(() -> {
            ghost.resetClock();
            piece1.resetClock();
            piece2.resetClock();
            piece3.resetClock();
            piece4.resetClock();
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ghost.updatePosition();
                if(piece1.drop(i1)) i1++;
                if(piece2.drop(i2)) i2++;
                if(piece3.drop(i3)) i3++;
                if(piece4.drop(i4)) i4++;

                if(i1 >= Constants.BOARD_ROWS) i1 = 0;
                if(i2 >= Constants.BOARD_ROWS) i2 = 0;
                if(i3 >= Constants.BOARD_ROWS) i3 = 0;
                if(i4 >= Constants.BOARD_ROWS) i4 = 0;
                repaint();
            }
        }).start());
    }

    public void addListeners() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(ghost != null) {
                    ghost.moveTo(Math.clamp(Math.round((double)e.getX() / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS) * Constants.PIECE_SIZE - Constants.PIECE_SIZE / 2.0, 0);
                    repaint();
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if(ghost != null) {
                    ghost.moveTo(Math.clamp(Math.round((double)e.getX() / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS) * Constants.PIECE_SIZE - Constants.PIECE_SIZE / 2.0, 0);
                    repaint();
                }
            }
        });

    }

    public void setGhost(Piece piece) {
        removeAll();
        ghost = piece;
        add(ghost);
    }

    public boolean tryLock() {
        if (ghost == null) return false;
        if (!ghost.isValidPosition(board)) return false;
        ghost.lock(board);
        remove(ghost);
        ghost = null;
        return true;

    }


    private void paintBoard(Graphics2D g2d) {
        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                Draw.piece(indexX * Constants.PIECE_SIZE + Constants.BOARD_EDGE_WIDTH,
                        indexY * Constants.PIECE_SIZE + Constants.DROP_ZONE_HEIGHT,
                        board[indexX][indexY],
                        false,
                        g2d);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
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
        Draw.boardBack(g2d);
        paintBoard(g2d);

    }
    

}
