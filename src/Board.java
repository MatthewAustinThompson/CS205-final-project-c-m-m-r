import java.awt.*;

public class Board
{
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;

    // The spaces on the board
    private Piece[][] spaces;  // store a Piece, or null at each
    private boolean[][] highlighted; // if a space is highlighted


    // Variables for drawing
    private int distanceBetweenPoints;
    private int lineWidth;
    private double pointRadius;
    private Color defaultColor;
    private Color highlightedColor;

    public Board(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();
        distanceBetweenPoints = (int)Math.min(windowWidth/10, windowHeight/11);

        this.initializePieces();
        this.initializeHighlighted();

        lineWidth = 10;
        pointRadius = 20;
        defaultColor = Color.BLUE;
        highlightedColor = Color.MAGENTA;
    }


    // Puts all of the Pieces in their starting positions in spaces
    public void initializePieces()
    {
        spaces = new Piece[9][10]; // 9 columns, each column has 10 spaces
    }

    public void initializeHighlighted()
    {
        highlighted = new boolean[9][10]; // 9 columns, each column has 10 spaces
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                highlighted[i][j] = false;
            }
        }
    }

    // ===============================
    //
    //       Drawing the Board
    //
    // ===============================
    public void render(Graphics2D g2d)
    {
        int x = distanceBetweenPoints/2;
        int y = distanceBetweenPoints/2;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                drawHorizontalSegment(g2d, x, x+distanceBetweenPoints, y);
                y += distanceBetweenPoints;
            }
            y = distanceBetweenPoints/2;
            x += distanceBetweenPoints;
        }
    }
    // Draws a rectangle connecting x1 and x2 at height y. x1 must be less than x2.
    public void drawHorizontalSegment(Graphics2D g2d, int x1, int x2, int y)
    {
        g2d.fillRect(x1, y - lineWidth/2, x2 - x1, lineWidth);
    }
}
