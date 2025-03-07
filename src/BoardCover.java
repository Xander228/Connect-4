import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoardCover extends JComponent {
    private int pixelX, pixelY;

    BoardCover(int pixelX, int pixelY) {
        setOpaque(false);
        this.pixelX = pixelX;
        this.pixelY = pixelY;
        EventQueue.invokeLater(this::addListeners);
    }

    private void addListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCoords();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updateCoords();
            }
        });
    }

    private void updateCoords() {
        setBounds(
                pixelX,
                pixelY - Constants.SLOT_INSET,
                Constants.PIECE_SIZE * Constants.BOARD_COLS,
                Constants.PIECE_SIZE * Constants.BOARD_ROWS + Constants.SLOT_INSET);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Draw.boardCover(g2d);
    }
}

