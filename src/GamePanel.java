import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class GamePanel extends JLayeredPane {


    final private BoardPanel boardPanel; //Declare the matrixPanel
    final private TopPanel topPanel;

    private class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final Color color2;

        RoundedBorder(Color color, Color color2){
            this.color = color;
            this.color2 = color2;

        }


        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setColor(color2);
            int radii = 10;
            g2d.fillRoundRect(x, y, width, height, radii * 2, radii * 2);
            g2d.setColor(color);
            int offset = 3;
            g2d.fillRoundRect(
                    x + offset, y + offset,
                    width - offset * 2, height - offset * 2,
                    radii * 2 - offset, radii * 2 - offset);
        }
    }


    //GamePanel constructor
    public GamePanel() {



        //Set up the panel properties

        setLayout(new OverlayLayout(this));

        JPanel innerPanel = new JPanel();
        add(innerPanel, 0); //Adds the piecePanel object to the bottom of the parent panel

        innerPanel.setLayout(new BorderLayout(10, 10));
        innerPanel.setBackground(Constants.BACKGROUND_COLOR);
        innerPanel.setBorder(BorderFactory.createMatteBorder(5, 10, 10, 10, Constants.BACKGROUND_COLOR));

        JPanel outerBoardPanel = new JPanel();
        innerPanel.add(outerBoardPanel); //Adds the matrixPanel object to the center of the parent panel

        topPanel = new TopPanel();
        innerPanel.add(topPanel, BorderLayout.NORTH);

        outerBoardPanel.setBorder(
                new RoundedBorder(Constants.ACCENT_COLOR, Constants.ACCENT_COLOR_2));
        outerBoardPanel.setBackground(Constants.BACKGROUND_COLOR);

        boardPanel = new BoardPanel(); //Create the MatrixPanel object
        outerBoardPanel.add(boardPanel, BorderLayout.CENTER);



    }

}
