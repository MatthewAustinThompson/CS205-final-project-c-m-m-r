import java.awt.*;
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

    // Constructor
    public Piece(GameManager inputManager, Point inputCenter, BoardPoint inputLocation)
    {
        manager = inputManager;
        board = manager.getBoard();
        center = inputCenter;
        location = inputLocation;
        targetingSquares = new ArrayList<BoardPoint>();
        legalMoveSquares = new ArrayList<BoardPoint>();
    }

    public abstract void tick();

    public abstract void render(Graphics2D g2d);

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


    // Setters
    public void setCenter(Point inputCenter)
    {
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

    // Moving
    // This sets targetingSquares to contain all squares that this Piece is
    // able to move to, or capture at, or protect teammates at
    public abstract void findTargetingSquares();

    // This sets legalMoveSquares to contain every square in targetingSquares
    // that doesn't contain a teammate
    public void findLegalMoveSquares()
    {
        legalMoveSquares = new ArrayList<BoardPoint>();
        for(BoardPoint bp : targetingSquares)
        {
            // If there's not a teammate there, we can move there
            if(!board.containsTeammate(this, bp))
            {
                legalMoveSquares.add(bp);
            }
        }
    }
}
