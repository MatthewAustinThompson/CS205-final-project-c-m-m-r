import java.awt.*;
import java.util.ArrayList;

public class GameManager
{
    // The dimensions of the part of the window that the
    // GameManager can draw on
    private int width, height;

    private ArrayList<Piece> pieces;

    private Board board;

    // When the player clicks on a Piece
    private Piece selectedPiece;

    // If a Piece has been captured
    private Piece toBeRemoved;

    public GameManager(int inputWidth, int inputHeight)
    {
        width = inputWidth;
        height = inputHeight;

        pieces = new ArrayList<Piece>();

        selectedPiece = null;
        toBeRemoved = null;
        /*pieces.add(new ExamplePieceMarcus(this,
                new Point(width/2, height/2), new BoardPoint(0,0)));

        pieces.add(new ExamplePieceRuth(this,
                new Point(width/3, height/3) , new BoardPoint(1,0)));

        pieces.add(new ExamplePieceChris(this,
                new Point(width/2, height/4) , new BoardPoint(2,1)));

        pieces.add(new ExamplePieceMatthew(this,
                new Point(width/4, height/6) , new BoardPoint(2,2)));*/

        board = new Board(this);

        this.addPiece(PieceType.Horse, Team.Player, new BoardPoint(4,5));
        this.addPiece(PieceType.Horse, Team.Player, new BoardPoint(3,3));
        this.addPiece(PieceType.Solider, Team.Player, new BoardPoint(0,6));
        this.addPiece(PieceType.Solider, Team.Player, new BoardPoint(2,6));
        this.addPiece(PieceType.Solider, Team.Player, new BoardPoint(4,6));
        this.addPiece(PieceType.Solider, Team.Player, new BoardPoint(6,6));
        this.addPiece(PieceType.Solider, Team.Player, new BoardPoint(8,6));
        this.addPiece(PieceType.Solider, Team.Computer, new BoardPoint(0,3));
        this.addPiece(PieceType.Solider, Team.Computer, new BoardPoint(2,3));
        this.addPiece(PieceType.Solider, Team.Computer, new BoardPoint(4,3));
        this.addPiece(PieceType.Solider, Team.Computer, new BoardPoint(6,3));
        this.addPiece(PieceType.Solider, Team.Computer, new BoardPoint(8,3));
        this.addPiece(PieceType.Guard, Team.Player, new BoardPoint(3,9));
        this.addPiece(PieceType.Guard, Team.Player, new BoardPoint(5,9));
        this.addPiece(PieceType.Guard, Team.Computer, new BoardPoint(3,0));
        this.addPiece(PieceType.Guard, Team.Computer, new BoardPoint(5,0));
        this.updatePieces();
    }

    public void tick()
    {
        // Have each Piece update each frame, if applicable
        for(Piece p : pieces)
        {
            p.tick();
        }
        if(toBeRemoved != null)
        {
            pieces.remove(toBeRemoved);
            toBeRemoved = null;
        }
    }

    public void render(Graphics2D g2d)
    {
        board.render(g2d);
        // Have each Piece draw itself
        for(Piece p : pieces)
        {
            p.render(g2d);
        }
        board.drawHighlights(g2d);
    }


    // Getters
    public double getWidth()
    {
        return width;
    }
    public double getHeight()
    {
        return height;
    }
    public Board getBoard()
    {
        return board;
    }

    // Setters
    public void setWidth(int inputWidth)
    {
        width = inputWidth;
    }
    public void setHeight(int inputHeight)
    {
        height = inputHeight;
    }

    // ================================
    //
    //         Managing Pieces
    //
    // ================================
    public void addPiece(PieceType pieceType, Team team, BoardPoint location)
    {
        if(board.containsPiece(location))
        {
            System.out.println("Error: cannot insert a Piece in non-empty space.");
            return;
        }
        Piece p;
        Point center = board.boardPointToPoint(location);
        switch(pieceType)
        {
            case Guard:
                p = new Guard(this, center, location, team);
                break;
            case Horse:
                p = new Horse(this, center, location, team);
                break;
            case Solider:
                p = new Soldier(this, center, location, team);
                break;
            default:
                return;
        }
        pieces.add(p);
        board.getSpaces()[location.getX()][location.getY()] = p;
    }
    public void updatePieces()
    {
        for(Piece p : pieces)
        {
            p.update();
        }
    }


    // ================================
    //
    //           User Input
    //
    // ================================
    public void reactToClick(int mx, int my)
    {
        // If a Piece is selected, either move it or unselect it
        if(selectedPiece != null)
        {
            BoardPoint bp = board.clickIsOnLegalMove(mx, my, selectedPiece);
            if(bp != null)
            {
                // If we are capturing, remove the piece getting captured
                if(board.containsPiece(bp))
                {
                    toBeRemoved = board.getPieceAt(bp);
                }
                // Now do the move
                board.move(selectedPiece, bp);
                this.updatePieces(); // the pieces need to update where they can move
            }
            selectedPiece = null;
            board.unhighlightAll();
            return;
        }
        else
        {
            for(Piece p : pieces)
            {
                if(p.containsClick(mx, my))
                {
                    selectedPiece = p;
                    board.highlightLegalMoves(p);
                    return;
                }
            }
        }
        selectedPiece = null;
        board.unhighlightAll();
    }
}
