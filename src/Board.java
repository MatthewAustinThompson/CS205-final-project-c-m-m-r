import java.awt.*;
import java.awt.geom.Path2D;

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
        distanceBetweenPoints = (int)Math.min(windowWidth/9, windowHeight/10);

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
        g2d.setColor(defaultColor);

        // Draw the grid
        int x = distanceBetweenPoints/2;
        int y = distanceBetweenPoints/2;
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(i != 8) // don't draw horizontal lines past the right edge
                {
                    drawHorizontalSegment(g2d, x, x+distanceBetweenPoints, y);
                }
                if(j != 9) // don't draw vertical lines below the bottom edge
                {
                    drawVerticalSegment(g2d, x, y, y + distanceBetweenPoints);
                }

                y += distanceBetweenPoints;
            }
            y = distanceBetweenPoints/2;
            x += distanceBetweenPoints;
        }

        // Draw the diagonals
        int xCenter = distanceBetweenPoints*9/2;
        int yUpper = distanceBetweenPoints*3/2;
        int yLower = distanceBetweenPoints*17/2;
        // Upper fortress
        drawDiagonalSegment(g2d, xCenter - distanceBetweenPoints, yUpper - distanceBetweenPoints,
                xCenter, yUpper);
        drawDiagonalSegment(g2d, xCenter - distanceBetweenPoints, yUpper + distanceBetweenPoints,
                xCenter, yUpper);
        drawDiagonalSegment(g2d, xCenter, yUpper,
                xCenter + distanceBetweenPoints, yUpper - distanceBetweenPoints);
        drawDiagonalSegment(g2d, xCenter, yUpper,
                xCenter + distanceBetweenPoints, yUpper + distanceBetweenPoints);
        // Lower fortress
        drawDiagonalSegment(g2d, xCenter - distanceBetweenPoints, yLower - distanceBetweenPoints,
                xCenter, yLower);
        drawDiagonalSegment(g2d, xCenter - distanceBetweenPoints, yLower + distanceBetweenPoints,
                xCenter, yLower);
        drawDiagonalSegment(g2d, xCenter, yLower,
                xCenter + distanceBetweenPoints, yLower - distanceBetweenPoints);
        drawDiagonalSegment(g2d, xCenter, yLower,
                xCenter + distanceBetweenPoints, yLower + distanceBetweenPoints);
    }
    // Draws a rectangle connecting x1 and x2 at height y. x1 must be less than x2.
    public void drawHorizontalSegment(Graphics2D g2d, int x1, int x2, int y)
    {
        g2d.fillRect(x1, y - lineWidth/2, x2 - x1, lineWidth);
    }
    // Must have y1 < y2
    public void drawVerticalSegment(Graphics2D g2d, int x, int y1, int y2)
    {
        g2d.fillRect(x - lineWidth/2, y1 , lineWidth, y2 - y1);
    }
    // Draws a segment at a 45 degree angle. Must have x1 < x2.
    public void drawDiagonalSegment(Graphics2D g2d, int x1, int y1, int x2, int y2)
    {
        Path2D.Double rect = new Path2D.Double();
        double amount = lineWidth / Math.sqrt(2)/2; // since it's a 45 degree angle

        // If the line is angled down
        if(y1 < y2)
        {
            rect.moveTo(x1 + amount, y1 - amount);
            rect.lineTo(x2 + amount, y2 - amount);
            rect.lineTo(x2 - amount, y2 + amount);
            rect.lineTo(x1 - amount, y1 + amount);
            rect.closePath();
        }
        else // angled up
        {
            rect.moveTo(x1 - amount, y1 - amount);
            rect.lineTo(x2 - amount, y2 - amount);
            rect.lineTo(x2 + amount, y2 + amount);
            rect.lineTo(x1 + amount, y1 + amount);
            rect.closePath();
        }
        g2d.fill(rect);
    }
}
