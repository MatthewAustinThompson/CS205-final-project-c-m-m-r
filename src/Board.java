import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

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
    private int pointRadius;
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

        lineWidth = 8;
        pointRadius = 10;
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

                // Move down the column
                y += distanceBetweenPoints;
            }
            y = distanceBetweenPoints/2;
            x += distanceBetweenPoints;
        }
    }


    void drawHighlights(Graphics2D g2d)
    {
        int x = distanceBetweenPoints/2;
        int y = distanceBetweenPoints/2;
        for(int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                // fill in the circle on the intersection
                if (highlighted[i][j])
                {
                    g2d.setColor(highlightedColor);
                    drawPoint(g2d, x, y);
                }
                // Move down the column
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


    public void drawPoint(Graphics2D g2d, int x, int y)
    {
        g2d.fillOval(x - pointRadius, y - pointRadius, 2*pointRadius, 2*pointRadius);
    }


    public Point boardPointToPoint(BoardPoint bp)
    {
        int i = bp.getX();
        int j = bp.getY();
        return new Point(distanceBetweenPoints/2 + i * distanceBetweenPoints,
                distanceBetweenPoints/2 + j * distanceBetweenPoints);
    }

    // ==============================
    //
    //      Handling Highlighting
    //
    // ==============================
    void highlightPoint(BoardPoint bp)
    {
        highlighted[bp.getX()][bp.getY()] = true;
    }
    void highlightPoint(int i, int j)
    {
        highlighted[i][j] = true;
    }
    void unhighlightAll()
    {
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                highlighted[i][j] = false;
            }
        }
    }


    public void highlightLegalMoves(Piece p)
    {
        ArrayList<BoardPoint> bps = p.getLegalMoveSquares();
        for(BoardPoint bp : bps)
        {
            this.highlightPoint(bp);
        }
    }


    // Getters
    public Piece[][] getSpaces()
    {
        return spaces;
    }
    public int getWindowWidth()
    {
        return windowWidth;
    }
    public int getWindowHeight()
    {
        return windowHeight;
    }
    public int getDistanceBetweenPoints()
    {
        return distanceBetweenPoints;
    }


    // ==================================================
    //
    //       Getting information about the board
    //
    // ==================================================
    // Check if a space is empty
    public boolean containsPiece(int i, int j)
    {
        if(i < 0 || i > 8 || j < 0 || j > 9)
        {
            System.out.println("Error: requested space not on board.");
            return false;
        }
        return spaces[i][j] != null;
    }
    public boolean containsPiece(BoardPoint bp)
    {
        return containsPiece(bp.getX(), bp.getY());
    }


    // Return the Piece at a given space
    public Piece getPieceAt(int i, int j)
    {
        if(i < 0 || i > 8 || j < 0 || j > 9)
        {
            System.out.println("Error: requested space not on board.");
            return null;
        }
        return spaces[i][j];
    }
    public Piece getPieceAt(BoardPoint bp)
    {
        return getPieceAt(bp.getX(), bp.getY());
    }


    // Check if a square has a teammate relative to the given Piece
    public boolean containsTeammate(Piece p, BoardPoint bp)
    {
        Piece otherPiece = this.getPieceAt(bp);
        if(otherPiece == null)
        {
            return false;
        }
        return otherPiece.getTeam() == p.getTeam();
    }


    // Check if a square has an enemy relative to the given Piece
    public boolean containsEnemy(Piece p, BoardPoint bp)
    {
        Piece otherPiece = this.getPieceAt(bp);
        if(otherPiece == null)
        {
            return false;
        }
        return otherPiece.getTeam() != p.getTeam();
    }
    // Wrapper
    public boolean containsEnemy(Piece p, int i, int j)
    {
        return containsEnemy(p, new BoardPoint(i, j));
    }

    public boolean containsEnemyCannon(Piece p, int i, int j)
    {
        return containsEnemy(p, i, j) && spaces[i][j].getPieceType() == PieceType.Cannon;
    }


    // Returns Euclidean distance between a boardPoint and two coordinates
    // Used for seeing if a click is close to a point
    public double distance(BoardPoint bp, int mx, int my)
    {
        Point p = this.boardPointToPoint(bp);
        return Math.sqrt((p.x - mx)*(p.x - mx) + (p.y - my)*(p.y - my));
    }


    // Given that (mx, my) is the location of a user click, (and assume that
    // p is the highlighted Piece), if the click is on a space that p can move
    // to, this returns the Board coordinates of the space. Otherwise, returns null.
    public BoardPoint clickIsOnLegalMove(int mx, int my, Piece p)
    {
        for(BoardPoint bp : p.getLegalMoveSquares())
        {
            // If there is a Piece there to capture, is the click on the octagon?
            if(this.containsPiece(bp) && this.getPieceAt(bp).containsClick(mx, my))
            {
                return bp;
            }
            else if(distance(bp, mx, my) < pointRadius)
            {
                return bp;
            }
        }
        return null;
    }


    // ==================================================
    //
    //                 Moving Pieces
    //
    // ==================================================
    public boolean move(Piece p, BoardPoint bp)
    {
        if(!p.getLegalMoveSquares().contains(bp))
        {
            return false;
        }
        // Get the Piece's previous location
        BoardPoint oldBP = p.getLocation();

        // Move the Piece
        spaces[bp.getX()][bp.getY()] = p;
        p.setLocation(bp);
        p.setCenter(boardPointToPoint(bp));

        // Set the old space to empty
        spaces[oldBP.getX()][oldBP.getY()] = null;
        return true;
    }

    // ==================================================
    //
    //                 Detecting Check
    //
    // ==================================================
    // This returns true if the specified Team's General is in check on the
    // give board
    public boolean boardHasCheck(Piece[][] boardSpaces, Team team)
    {

        BoardPoint generalLoc = new BoardPoint(0,0); // initialize to default value
        boolean generalFound = false;

        // Iterate through all of this Team's Pieces to find the General
        for(int i = 0; i < boardSpaces.length; i++)
        {
            for(int j = 0; j < boardSpaces[0].length; j++)
            {
                if(boardSpaces[i][j] != null && boardSpaces[i][j].getPieceType() == PieceType.General &&
                boardSpaces[i][j].getTeam() == team)
                {
                    generalLoc = boardSpaces[i][j].getLocation();
                    generalFound = true;
                    break;
                }
            }
        }
        if(!generalFound)
        {
            System.out.println("Could not find the General when looking for check.");
            return false;
        }

        // Iterate through all opposing Pieces, and see if they are looking at the
        // General's space
        for(Piece[] column : boardSpaces)
        {
            for(Piece p : column)
            {
                if(p != null && p.getTeam() != team)
                {
                    if(p.findTargetingSquares(boardSpaces).contains(generalLoc))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Helper function: returns a copy of the current Board
    public Piece[][] copyBoard()
    {
        Piece[][] copyOfBoard = new Piece[spaces.length][spaces[0].length];
        for(int i = 0; i < spaces.length; i++)
        {
            for(int j = 0; j < spaces[0].length; j++)
            {
                copyOfBoard[i][j] = spaces[i][j];
            }
        }
        return copyOfBoard;
    }

    // Tests if the Piece can move to the given space without putting its own
    // team in check.
    public boolean canMoveWithoutCausingCheck(Piece p, BoardPoint bp)
    {
        if(!p.getTargetingSquares().contains(bp))
        {
            System.out.println("Problem: you are seeing if a Piece would cause check at a space it can't even move to.");
            return false;
        }
        Piece[][] hypotheticalBoard = this.copyBoard();
        BoardPoint prevLoc = p.getLocation();
        hypotheticalBoard[bp.getX()][bp.getY()] = p;
        p.setLocation(bp);
        hypotheticalBoard[prevLoc.getX()][prevLoc.getY()] = null;
        boolean answer = this.boardHasCheck(hypotheticalBoard, p.getTeam());
        p.setLocation(prevLoc);
        return !answer;
    }
}
