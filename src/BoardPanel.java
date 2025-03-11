import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    //Game board array formatted in cols x rows (x,y)
    private int[][] board = new int[Constants.BOARD_COLS][Constants.BOARD_ROWS];
    
    //Create object variable to hold the current piece in hand
    private Piece ghost;
    int lastMouseX = 0;

    //Create a counter to count the number of gameLoops elapsed since the last drop

    public BoardPanel(){
        // Initialize components, set layout
        setBackground(Constants.ACCENT_COLOR);
        setPreferredSize( new Dimension(Constants.BOARD_WIDTH, 
                                        Constants.BOARD_HEIGHT));
        setGhost(new Piece(
                Constants.RED_STARTS,
                (Constants.BOARD_WIDTH - Constants.PIECE_SIZE) / 2,
                -Constants.PIECE_SIZE,
                true));
        ghost.resetClock();
        ghost.dropIn();
        ghost.moveToCol(Math.clamp(Math.round((double) lastMouseX / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS) * Constants.PIECE_SIZE - Constants.PIECE_SIZE / 2.0);

        BoardCover boardCover = new BoardCover(Constants.BOARD_EDGE_WIDTH, Constants.DROP_ZONE_HEIGHT);
        add(boardCover, 0);

        addListeners();
        EventQueue.invokeLater(() -> {
            new GameModeDialog((Frame) this.getTopLevelAncestor());
            new Thread(() -> {
                ghost.resetClock();
                while (true) {
                    if (ghost.updatePosition()) {
                        ghost.lock(board);
                        remove(ghost);
                        boolean winner = isWinner();
                        boolean tie = isTie();
                        if (winner || tie) {
                            dropBoardPieces();
                            new GameOverDialog((MainFrame) this.getTopLevelAncestor(), tie, ghost.isRed());
                            return;
                        }
                        setGhost(new Piece(
                                !ghost.isRed(),
                                lastMouseX - Constants.PIECE_SIZE / 2,
                                -Constants.PIECE_SIZE,
                                true));
                        ghost.resetClock();
                        ghost.dropIn();
                        ghost.moveToCol(Math.clamp(Math.round((double) lastMouseX / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS) * Constants.PIECE_SIZE - Constants.PIECE_SIZE / 2.0);
                    }
                    repaint();
                }
            }).start();
        });
    }

    public void addListeners() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(ghost != null) {
                    ghost.moveToCol(Math.clamp(Math.round((double)e.getX() / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS) * Constants.PIECE_SIZE - Constants.PIECE_SIZE / 2.0);
                    lastMouseX = e.getX();
                    repaint();
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1 || ghost == null) return;
                if (ghost.isDropping()) return;
                int col = Math.clamp(Math.round((double)e.getX() / Constants.PIECE_SIZE), 1, Constants.BOARD_COLS);
                int cells = getCellsFilled(col - 1);
                if(cells  >= Constants.BOARD_ROWS) return;
                ghost.startDrop(cells);
                repaint();
            }
        });

    }

    public void setGhost(Piece piece) {
        ghost = piece;
        add(ghost);
    }

    public int getCellsFilled(int col) {
        for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
            if(board[col][indexY] != 0) {
                return Constants.BOARD_ROWS - indexY;
            }
        }
        return 0;
    }

    public boolean isWinner() {
        for (int col = 0; col < Constants.BOARD_COLS; col++){
            for (int row = 0; row < Constants.BOARD_ROWS; row++){
                if (checkVertical(row, col)) return true;
                if (checkHorizontal(row, col)) return true;
                if (checkDiagonalUp(row, col)) return true;
                if (checkDiagonalDown(row, col)) return true;
            }
        }
        return false;
    }


    public boolean isTie() {
        for (int row = 0; row < Constants.BOARD_ROWS; row++) {
            for (int col = 0; col < Constants.BOARD_COLS; col++) {
                if (board[col][row] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkVertical(int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col][row + i];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) {
            for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
                board[col][row + i] = 2 * board[col][row + i];
            }
            return true;
        }
        return false;
    }

    private boolean checkHorizontal(int row, int col) {
        int total = 0;
        if(col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) {
            for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
                board[col + i][row] = 2 * board[col + i][row];
            }
            return true;
        }
        return false;
    }

    private boolean checkDiagonalUp(int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS ||
                col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row + i];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) {
            for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
                board[col + i][row + i] = 2 * board[col + i][row + i];
            }
            return true;
        }
        return false;
    }

    private boolean checkDiagonalDown(int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS ||
                col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row + ((Constants.WINNING_LENGTH - 1) - i)];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) {
            for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
                board[col + i][row + ((Constants.WINNING_LENGTH - 1) - i)] = 2 * board[col + i][row + ((Constants.WINNING_LENGTH - 1) - i)];
            }
            return true;
        }
        return false;
    }

    private void dropBoardPieces(){
        ArrayList<Piece> pieces = new ArrayList<>();
        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                if(board[indexX][indexY] == 0) continue;
                Piece piece = new Piece(
                        board[indexX][indexY] > 0,
                        indexX * Constants.PIECE_SIZE + Constants.BOARD_EDGE_WIDTH,
                        indexY * Constants.PIECE_SIZE + Constants.DROP_ZONE_HEIGHT,
                        false);
                if(Math.abs(board[indexX][indexY]) == 1) {
                    add(piece, -1);
                    pieces.add(piece);
                    piece.resetClock();
                    piece.dropOut();
                } else {
                    add(piece, 0);
                }
                board[indexX][indexY] = 0;
            }
        }
        while (true) {
            boolean done = true;
            for (Piece piece : pieces) {
                piece.updatePosition();
                if(!piece.isBelowBoard()) done = false;
            }
            repaint();
            if(done) break;
        }
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
