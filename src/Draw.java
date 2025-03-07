import java.awt.*;

public class Draw {
    public static void piece(int x, int y, int color, boolean ghost, Graphics2D g) {
        if (color == 0) return;
        Color[] tempColors = color == 1 ? Constants.RED_COLORS : Constants.YELLOW_COLORS;
        Color[] colors = new Color[tempColors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = ghost ?
                    tempColors[i].darker().darker() :
                    tempColors[i];
        }

        int pieceSize = Constants.PIECE_SIZE;
        int diameter = pieceSize - (2 * Constants.PIECE_EDGE_WIDTH);

        g.setColor(colors[4]);
        g.fillOval(x + Constants.PIECE_EDGE_WIDTH, y + Constants.PIECE_EDGE_WIDTH, diameter, diameter);

        int inset = 1;
        GradientPaint gp = new GradientPaint(
                x,
                y + 4 * Constants.PIECE_EDGE_WIDTH + inset,
                colors[5],
                x,
                y + pieceSize - (Constants.PIECE_EDGE_WIDTH + inset),
                colors[6], false);
        g.setPaint(gp);
        g.fillOval(
                x + Constants.PIECE_EDGE_WIDTH + inset,
                y + Constants.PIECE_EDGE_WIDTH + inset,
                diameter - 2 * inset,
                diameter - 2 * inset);

        inset += 3;
        g.setColor(colors[4]);
        g.fillOval(
                x + Constants.PIECE_EDGE_WIDTH + inset,
                y + Constants.PIECE_EDGE_WIDTH + inset,
                diameter - 2 * inset,
                diameter - 2 * inset);

        inset += 1;
        gp = new GradientPaint(
                x + pieceSize - (int)(2.5 * Constants.PIECE_EDGE_WIDTH + inset),
                y + (int)(0.5 * Constants.PIECE_EDGE_WIDTH) + inset,
                colors[3],
                x + pieceSize - (int)(3.5 * Constants.PIECE_EDGE_WIDTH + inset),
                y + (int)(1.5 * Constants.PIECE_EDGE_WIDTH) + inset,
                colors[2], false);
        g.setPaint(gp);
        g.fillOval(
                x + Constants.PIECE_EDGE_WIDTH + inset,
                y + Constants.PIECE_EDGE_WIDTH + inset,
                diameter - 2 * inset,
                diameter - 2 * inset);

        inset += 1;
        gp = new GradientPaint(
                x,
                y + 2 * Constants.PIECE_EDGE_WIDTH + inset,
                colors[1],
                x,
                y + pieceSize - (3 * Constants.PIECE_EDGE_WIDTH + inset),
                colors[0], false);
        g.setPaint(gp);
        g.fillOval(
                x + Constants.PIECE_EDGE_WIDTH + inset,
                y + Constants.PIECE_EDGE_WIDTH + inset,
                diameter - 2 * inset,
                diameter - 2 * inset);
    }

    public static void boardBack(Graphics2D g) {
        int pieceSize = Constants.PIECE_SIZE;
        int strokeRadius = 4;
        int inset = -1;
        Color[] colors = Constants.COVER_COLORS;
        g.setColor(colors[5]);
        for (int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for (int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                g.setStroke(new BasicStroke(2 * strokeRadius));
                g.drawOval(
                        indexX * pieceSize + Constants.BOARD_EDGE_WIDTH + strokeRadius + inset + 1,
                        indexY * pieceSize + Constants.DROP_ZONE_HEIGHT + 1,
                        pieceSize - 2 * strokeRadius - (2 * inset) - 2,
                        pieceSize - 2 * strokeRadius - (2 * inset) - 2);
            }
        }

        int edgeWidth = 4;
        int offsetWidth = 2 * edgeWidth;
        g.setStroke(new BasicStroke(3 * edgeWidth));
        g.setColor(colors[2]);

        g.drawRoundRect(
                Constants.BOARD_EDGE_WIDTH - offsetWidth + edgeWidth,
                Constants.DROP_ZONE_HEIGHT - (2 * offsetWidth) + edgeWidth,
                Constants.BOARD_COLS * pieceSize + (2 * offsetWidth) - (2 * edgeWidth) ,
                Constants.BOARD_ROWS * pieceSize + (2 * offsetWidth) - 2 * edgeWidth,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);


        GradientPaint gp1 = new GradientPaint(
                pieceSize * (Constants.BOARD_COLS - 1) - Constants.BOARD_EDGE_WIDTH,
                pieceSize * Constants.BOARD_ROWS,
                colors[0],
                pieceSize * Constants.BOARD_COLS - Constants.BOARD_EDGE_WIDTH,
                pieceSize * (Constants.BOARD_ROWS - 1),
                colors[6], false);
        g.setPaint(gp1);
        offsetWidth = edgeWidth;
        g.setStroke(new BasicStroke(2 * edgeWidth));

        g.drawRoundRect(
                Constants.BOARD_EDGE_WIDTH - offsetWidth + edgeWidth,
                Constants.DROP_ZONE_HEIGHT - (2 * offsetWidth) + edgeWidth,
                Constants.BOARD_COLS * pieceSize + (2 * offsetWidth) - (2 * edgeWidth) ,
                Constants.BOARD_ROWS * pieceSize + (2 * offsetWidth) - 2 * edgeWidth,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);

        offsetWidth = 2 * edgeWidth;

        g.drawRoundRect(
                Constants.BOARD_EDGE_WIDTH - offsetWidth + edgeWidth,
                Constants.DROP_ZONE_HEIGHT - (2 * offsetWidth) + edgeWidth,
                Constants.BOARD_COLS * pieceSize + (2 * offsetWidth) - (2 * edgeWidth) ,
                Constants.BOARD_ROWS * pieceSize + (2 * offsetWidth) - 2 * edgeWidth,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);

        g.setColor(colors[2]);
        g.fillRoundRect(
                Constants.BOARD_EDGE_WIDTH - offsetWidth + edgeWidth,
                Constants.DROP_ZONE_HEIGHT - offsetWidth + edgeWidth - 6,
                Constants.BOARD_COLS * pieceSize + (2 * offsetWidth) - (2 * edgeWidth) ,
                6,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);



    }



    public static void boardCover(Graphics2D g){
        int pieceSize = Constants.PIECE_SIZE;
        int edgeWidth = 4;
        Color[] colors = Constants.COVER_COLORS;
        g.setColor(colors[0]);

        GradientPaint gp1 = new GradientPaint(
                pieceSize * (Constants.BOARD_COLS - 1),
                pieceSize * Constants.BOARD_ROWS,
                colors[0],
                pieceSize * Constants.BOARD_COLS,
                pieceSize * (Constants.BOARD_ROWS - 1),
                colors[6], false);
        g.setPaint(gp1);


        g.setStroke(new BasicStroke(4 * edgeWidth));
        for(int indexX = 1; indexX < Constants.BOARD_COLS; indexX++) {
            g.drawLine(
                    indexX * pieceSize,
                    Constants.SLOT_INSET,
                    indexX * pieceSize,
                    Constants.SLOT_INSET + pieceSize * Constants.BOARD_ROWS);
        }

        for(int indexY = 1; indexY < Constants.BOARD_ROWS; indexY++) {
            g.drawLine(
                    0,
                    Constants.SLOT_INSET + indexY * pieceSize,
                    pieceSize * Constants.BOARD_COLS,
                    Constants.SLOT_INSET + indexY * pieceSize
            );
        }

        g.setStroke(new BasicStroke(2 * edgeWidth));

        g.drawRoundRect(
                edgeWidth,
                edgeWidth,
                Constants.BOARD_COLS * pieceSize - 2 * edgeWidth,
                Constants.BOARD_ROWS * pieceSize - 2 * edgeWidth + Constants.SLOT_INSET,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);
        g.drawRoundRect(
                edgeWidth,
                edgeWidth + Constants.SLOT_INSET / 2,
                Constants.BOARD_COLS * pieceSize - 2 * edgeWidth,
                Constants.BOARD_ROWS * pieceSize - 2 * edgeWidth + Constants.SLOT_INSET / 2,
                Constants.EMPTY_EDGE_RADIUS,
                Constants.EMPTY_EDGE_RADIUS);




        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                g.drawRoundRect(
                        indexX * pieceSize + edgeWidth,
                        Constants.SLOT_INSET + indexY * pieceSize + edgeWidth,
                        pieceSize - (2 * edgeWidth),
                        pieceSize - (2 * edgeWidth),
                        pieceSize / 2,
                        pieceSize / 2);
            }
        }

        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for (int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                int strokeRadius = 4;
                g.setStroke(new BasicStroke(2 * strokeRadius));
                g.setColor(colors[4]);
                g.drawArc(
                        indexX * pieceSize + strokeRadius + 1,
                        Constants.SLOT_INSET + indexY * pieceSize + (2 * strokeRadius) + 1,
                        pieceSize - 2 * strokeRadius - 2,
                        pieceSize - 2 * strokeRadius - 2,
                        180,
                        180);
            }
        }

        edgeWidth = 3;
        int inset = 2;
        g.setColor(colors[2]);
        g.setStroke(new BasicStroke(8));
        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                g.drawOval(
                        indexX * pieceSize + edgeWidth + inset,
                        Constants.SLOT_INSET + indexY * pieceSize + edgeWidth + inset,
                        pieceSize - (2 * edgeWidth + 2 * inset),
                        pieceSize - (2 * edgeWidth + 2 * inset));
            }
        }

        g.setStroke(new BasicStroke(2 * edgeWidth - 2));
        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                GradientPaint gp = new GradientPaint(
                        indexX * pieceSize + pieceSize - (int)(2.5 * Constants.PIECE_EDGE_WIDTH + inset),
                        Constants.SLOT_INSET + indexY * pieceSize + (int)(0.5 * Constants.PIECE_EDGE_WIDTH) + inset,
                        colors[3],
                        indexX * pieceSize + pieceSize - (int)(3.5 * Constants.PIECE_EDGE_WIDTH + inset),
                        Constants.SLOT_INSET + indexY * pieceSize + (int)(1.5 * Constants.PIECE_EDGE_WIDTH) + inset,
                        colors[1], false);
                g.setPaint(gp);
                g.drawOval(
                        indexX * pieceSize + edgeWidth + inset,
                        Constants.SLOT_INSET + indexY * pieceSize + edgeWidth + inset,
                        pieceSize - (2 * edgeWidth + 2 * inset),
                        pieceSize - (2 * edgeWidth + 2 * inset));
            }
        }

        g.setColor(colors[1]);
        inset += 1;
        g.setStroke(new BasicStroke(2 * edgeWidth - 4));
        for(int indexX = 0; indexX < Constants.BOARD_COLS; indexX++) {
            for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
                g.drawOval(
                        indexX * pieceSize + edgeWidth + inset,
                        Constants.SLOT_INSET + indexY * pieceSize + edgeWidth + inset,
                        pieceSize - (2 * edgeWidth + 2 * inset),
                        pieceSize - (2 * edgeWidth + 2 * inset));
            }
        }

        g.setColor(colors[2]);
        //draw a line at the very top
        g.setStroke(new BasicStroke(2));
        g.drawLine(
                0,
                0,
                Constants.BOARD_COLS * pieceSize,
                0);



    }




}
