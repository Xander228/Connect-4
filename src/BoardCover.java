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
        Draw.boardCover(g2d);
    }
}

