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

    public GameManager(int inputWidth, int inputHeight)
    {
        width = inputWidth;
        height = inputHeight;

        pieces = new ArrayList<Piece>();

        selectedPiece = null;
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
        this.addPiece(PieceType.Horse, Team.Computer, new BoardPoint(4,6));
        this.addPiece(PieceType.Horse, Team.Player, new BoardPoint(3,3));
        this.addPiece(PieceType.Horse, Team.Computer, new BoardPoint(2,1));
        this.updatePieces();
    }

    public void tick()
    {
        // Have each Piece update each frame, if applicable
        for(Piece p : pieces)
        {
            p.tick();
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
            case Horse:
                p = new Horse(this, center, location, team);
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
        if(selectedPiece != null)
        {
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
