import java.awt.*;

public class Constants
{

    public static final int BOARD_COLS = 7;
    public static final int BOARD_ROWS = 6;
    public static final int WINNING_LENGTH = 4;

    public static final boolean RED_STARTS = true;
    
    public static final int PIECE_SIZE = 60;
    public static final int PIECE_EDGE_WIDTH = PIECE_SIZE / 7;
    public static final int EMPTY_EDGE_RADIUS = PIECE_SIZE / 10;

    public static final int DROP_ZONE_HEIGHT = PIECE_SIZE;
    public static final int SLOT_INSET = 5;
    public static final int BOTTOM_GAP_HEIGHT = 2;

    public static final int BOARD_EDGE_WIDTH = PIECE_SIZE / 2;

    public static final int BOARD_WIDTH = BOARD_COLS * PIECE_SIZE + (2 * BOARD_EDGE_WIDTH);
    public static final int BOARD_HEIGHT = BOARD_ROWS * PIECE_SIZE + DROP_ZONE_HEIGHT + BOTTOM_GAP_HEIGHT;

    public static final int GAMEOVER_HEIGHT = 100;
    public static final int GAMEOVER_WIDTH = 300;

    public static final double GRAVITY = 12500;
    public static final double BOUNCE_MULTIPLIER = .5;
    public static final int X_VEL_GAIN = 3;
    public static double MAX_X_VELOCITY = 10000;

    public static final int SCORE_PANEL_HEIGHT = 40;
    
    public static final  Color BACKGROUND_COLOR = new Color((int)0x354c87);
    public static final  Color ACCENT_COLOR = new Color((int)0x171e3a);
    public static final  Color ACCENT_COLOR_2 = new Color((int)0x293669);

    public static final  Color PRIMARY_COLOR = new Color((int)0xFFFFFF);

    public static final Color[] COVER_COLORS = {
            new Color((int)0x3959f0),
            new Color((int)0x3858ee),
            new Color((int)0x1b4377),
            new Color((int)0x7ba8fc),
            new Color((int)0x314ec9),
            new Color((int)0x205286),
            new Color((int)0x4969f0)
    };

    public static final Color[] RED_COLORS = {
            new Color((int)0xcb212f),
            new Color((int)0xdf4544),
            new Color((int)0xdc2133),
            new Color((int)0xffffff),
            new Color((int)0xb01513),
            new Color((int)0xcd1e2d),
            new Color((int)0xef7f75)};

    public static final Color[] YELLOW_COLORS = {
            new Color((int) 0xfdca08), // Vibrant warm yellow
            new Color((int) 0xf8e771), // Bright saturated yellow
            new Color((int) 0xf9d109), // Strong yellow with high saturation
            new Color((int) 0xffffff), // White remains unchanged
            new Color((int) 0xc87a07), // Deeper but still vibrant yellow
            new Color((int) 0xf5df11), // Rich and saturated yellow
            new Color((int) 0xfbfba3)};

}
