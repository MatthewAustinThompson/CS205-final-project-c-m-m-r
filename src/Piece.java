import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public abstract class Piece
{
    // ========================================
    //
    //            Basic properties
    //
    // ========================================
    protected GameManager manager;
    protected Board board;
    protected Team team;
    protected PieceType pieceType;

    // ========================================
    //
    //         Board and Movement Things
    //
    // ========================================

    // The location of the Piece in terms of the board
    protected BoardPoint location;

    // The squares on the board this Piece is looking at
    // (open squares and squares with other Pieces)
    protected ArrayList<BoardPoint> targetingSquares;

    // The squares on the Board this Piece can actually move to
    protected ArrayList<BoardPoint> legalMoveSquares;

    // ========================================
    //
    //           Drawing the Piece
    //
    // ========================================

    // The current center of the Piece
    protected Point center;
    // Colors for drawing
    protected Color fillColor;
    protected Color symbolColor;

    // The octagon shape of the piece
    protected Path2D.Double outline;

    // If this Piece has been selected by clicking on it
    protected boolean isHighlighted;

    // Constructor
    public Piece(GameManager inputManager, Point inputCenter, BoardPoint inputLocation)
    {
        manager = inputManager;
        board = manager.getBoard();
        center = inputCenter;
        location = inputLocation;
        targetingSquares = new ArrayList<BoardPoint>();
        legalMoveSquares = new ArrayList<BoardPoint>();
        isHighlighted = false;
    }

    public abstract void tick();

    public abstract void render(Graphics2D g2d);

    // Creates the octagonal outline of the Piece as a Path2D object
    public void initializeOctagon()
    {
        // Octagon geometry stuff
        int sideLength = 20;
        double r1 = sideLength/2.0;
        double r2 = sideLength*(1 + Math.sqrt(2))/2;

        // Trace out an octagon
        // A Path2D is just a sequence of points that
        // specifies line segments
        outline = new Path2D.Double();

        // Start at the top left
        outline.moveTo(center.x - r1, center.y - r2);

        // Go around clockwise
        outline.lineTo(center.x + r1, center.y - r2);
        outline.lineTo(center.x + r2, center.y - r1);
        outline.lineTo(center.x + r2, center.y + r1);
        outline.lineTo(center.x + r1, center.y + r2);
        outline.lineTo(center.x - r1, center.y + r2);
        outline.lineTo(center.x - r2, center.y + r1);
        outline.lineTo(center.x - r2, center.y - r1);
        // End where you started
        outline.lineTo(center.x - r1, center.y - r2);
    }

    // Highlight the outline of the piece
    public void drawHighlight(Graphics2D g2d)
    {
        if(this.isHighlighted)
        {
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(new Color(170, 231, 255, 255));
            g2d.draw(this.outline);
        }
    }


    // Getters
    public Point getCenter()
    {
        return center;
    }
    public BoardPoint getLocation()
    {
        return location;
    }
    public Team getTeam()
    {
        return team;
    }
    public PieceType getPieceType()
    {
        return pieceType;
    }
    public ArrayList<BoardPoint> getTargetingSquares()
    {
        return targetingSquares;
    }
    public ArrayList<BoardPoint> getLegalMoveSquares()
    {
        return legalMoveSquares;
    }
    public boolean getIsHighlighted()
    {
        return isHighlighted;
    }


    // Setters
    public void setCenter(Point inputCenter)
    {
        // When the Piece moves, the paths have to get translated
        AffineTransform tx = new AffineTransform();
        tx.translate(inputCenter.x - center.x, inputCenter.y - center.y);
        outline = (Path2D.Double) tx.createTransformedShape(outline);

        center = inputCenter;
    }
    public void setLocation(BoardPoint inputLocation)
    {
        location = inputLocation;
    }
    public void setTeam(Team t)
    {
        team = t;
    }
    public void setPieceType(PieceType pt)
    {
        pieceType = pt;
    }
    public void setIsHighlighted(boolean input)
    {
        isHighlighted = input;
    }

    // ================================
    //
    //             Moving
    //
    // ================================
    // Every time something on the board changes, this has to be called to update both lists
    // of squares
    public void update()
    {
        this.findTargetingSquares();
        this.findLegalMoveSquares();
    }
    // This sets targetingSquares to contain all squares that this Piece is
    // able to move to, or capture at, or protect teammates at
    public abstract ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard);

    // Wrapper method
    public void findTargetingSquares()
    {
        targetingSquares = findTargetingSquares(board.getSpaces());
    }

    // This sets legalMoveSquares to contain every square in targetingSquares
    // that doesn't contain a teammate
    public void findLegalMoveSquares()
    {
        legalMoveSquares = new ArrayList<BoardPoint>();
        for(BoardPoint bp : targetingSquares)
        {
            // If there's not a teammate there,
            // and it doesn't put us in check, we can move there
            if(!board.containsTeammate(this, bp) && board.canMoveWithoutCausingCheck(this, bp))
            {
                legalMoveSquares.add(bp);
            }
        }
    }

    // for clicking
    public boolean containsClick(int mx, int my)
    {
        return outline.contains(mx, my);
    }

    public String toString()
    {
        String s = "";
        if(team == Team.Player)
        {
            s += "Player's ";
        }
        else
        {
            s += "Computer's ";
        }
        if(pieceType == PieceType.General)
        {
            s += "General at ";
        }
        else if(pieceType == PieceType.Horse)
        {
            s += "Horse at ";
        }
        else if(pieceType == PieceType.Chariot)
        {
            s += "Chariot at ";
        }
        else if(pieceType == PieceType.Guard)
        {
            s += "Guard at ";
        }
        else if(pieceType == PieceType.Soldier)
        {
            s += "Soldier at ";
        }
        else if(pieceType == PieceType.Elephant)
        {
            s += "Elephant at ";
        }
        else if(pieceType == PieceType.Cannon)
        {
            s += "Cannon at ";
        }
        s += location.getX() + "," + location.getY();
        return s;
    }

    // Return true if the Piece currently has at least one space it can move to
    public boolean canMove()
    {
        return !legalMoveSquares.isEmpty();
    }
}
