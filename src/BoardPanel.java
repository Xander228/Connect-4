import javax.swing.*;
import java.awt.*;

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

        EventQueue.invokeLater(() -> new Thread(() -> {
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

    public void  updateGhostCoords(int pixelX, int pixelY) {
        if (ghost == null) return;

        double boardPixelX = pixelX / (double)Constants.PIECE_SIZE;
        double boardPixelY = pixelY / (double)Constants.PIECE_SIZE;

        int boardX = (int)Math.round(boardPixelX);
        int boardY = (int)Math.round(boardPixelY);
        int nextClosestX = boardPixelX - boardX > 0 ? boardX + 1 : boardX - 1;
        int nextClosestY = boardPixelY - boardY > 0 ? boardY + 1 : boardY - 1;
        int otherClosestX = boardPixelX - boardX > 0 ? boardX - 1 : boardX + 1;
        int otherClosestY = boardPixelY - boardY > 0 ? boardY - 1 : boardY + 1;


        if (ghost.isValidPosition(boardX, boardY, board))
            ghost.setBoardCoords(boardX, boardY);

        else if (ghost.isValidPosition(boardX, nextClosestY, board))
            ghost.setBoardCoords(boardX, nextClosestY);
        else if (ghost.isValidPosition(nextClosestX, boardY, board))
            ghost.setBoardCoords(nextClosestX, boardY);
        else if (ghost.isValidPosition(otherClosestX, boardY, board))
            ghost.setBoardCoords(otherClosestX, boardY);
        else if (ghost.isValidPosition(boardX, otherClosestY, board))
            ghost.setBoardCoords(boardX, otherClosestY);

        else if (ghost.isValidPosition(nextClosestX, nextClosestY, board))
            ghost.setBoardCoords(nextClosestX, nextClosestY);
        else if (ghost.isValidPosition(otherClosestX, nextClosestY, board))
            ghost.setBoardCoords(otherClosestX, nextClosestY);
        else if (ghost.isValidPosition(nextClosestX, otherClosestY, board))
            ghost.setBoardCoords(nextClosestX, otherClosestY);
        else if (ghost.isValidPosition(otherClosestX, otherClosestY, board))
            ghost.setBoardCoords(otherClosestX, otherClosestY);

        else ghost.setBoardCoords(Constants.BOARD_COLS, Constants.BOARD_ROWS);
        repaint();
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
