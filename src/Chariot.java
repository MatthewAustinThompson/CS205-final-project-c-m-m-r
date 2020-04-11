import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Chariot extends Piece
{


    public Chariot(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
    {
        super(inputManager, inputCenter, inputLocation);
        team = inputTeam;
        if(team == Team.Player)
        {
            symbolColor = new Color(0.1f, 0.4f, 0.8f);
        }
        else
        {
            symbolColor = new Color(0.8f, 0.1f, 0.4f);
        }
        fillColor = Color.WHITE;
        pieceType = PieceType.Chariot;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Chariot
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Write the word "Chariot"
        g2d.setFont(new Font("Courier", Font.PLAIN, 10));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Chariot"); // the number of pixels the string is long
        g2d.drawString("Chariot", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }


    @Override
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();

        // Check for moves to the right
        if(i < 8 && (!board.containsPiece(i + 1, j) || board.getPieceAt(i + 1, j).getTeam() != team))
        {
            if(j > 0) // not too high
            {
                targetingSquares.add(new BoardPoint(i + 1, j));
            }
            if(j < 9) // not too low
            {
                targetingSquares.add(new BoardPoint(i + 1, j));
            }
        }
        // Check for moves to the left
        if(i > 1 && (!board.containsPiece(i - 1, j)  || board.getPieceAt(i - 1, j).getTeam() != team))
        {
            if(j > 0) // not too high
            {
                targetingSquares.add(new BoardPoint(i - 1, j));
            }
            if(j < 9) // not too low
            {
                targetingSquares.add(new BoardPoint(i - 1, j));
            }
        }
        // Check for moves down
        if(j < 9 && (!board.containsPiece(i, j + 1)  || board.getPieceAt(i, j + 1).getTeam() != team) && team == Team.Computer)
        {
            if(i > 0) // not too far left
            {
                targetingSquares.add(new BoardPoint(i, j + 1));
            }
            if(i < 8) // not too far right
            {
                targetingSquares.add(new BoardPoint(i, j + 1));
            }
        }
        // Check for moves up
        if(j >= 1 && (!board.containsPiece(i, j - 1) || board.getPieceAt(i, j - 1).getTeam() != team) && team == Team.Player)
        {
            if(i > 0) // not too far left
            {
                targetingSquares.add(new BoardPoint(i, j - 1));
            }
            if(i < 8) // not too far right
            {
                targetingSquares.add(new BoardPoint(i, j - 1));
            }
        }
        // special cases
        if(team == Team.Player && i >= 3 && i <= 5 && j <= 2 && j > 0 && (!board.containsPiece(4, 1) || board.getPieceAt(4, 1).getTeam() != team)){
            targetingSquares.add(new BoardPoint(4, 1));
        }
        if(team == Team.Computer && i >= 3 && i <= 5 && j < 9 && j >= 7 && (!board.containsPiece(4, 8) || board.getPieceAt(4, 8).getTeam() != team)){
            targetingSquares.add(new BoardPoint(4, 8));
        }
        if(location.equals(new BoardPoint(4,8)) && team == Team.Computer
                && (!board.containsPiece(3, 9) || board.getPieceAt(3, 9).getTeam() != team)){
            targetingSquares.add(new BoardPoint(3, 9));
        }
        if(location.equals(new BoardPoint(4,8)) && team == Team.Computer
                && (!board.containsPiece(5, 9) || board.getPieceAt(5, 9).getTeam() != team)){
            targetingSquares.add(new BoardPoint(5, 9));
        }
        if(location.equals(new BoardPoint(4,1)) && team == Team.Player
                && (!board.containsPiece(3, 0) || board.getPieceAt(3, 0).getTeam() != team)){
            targetingSquares.add(new BoardPoint(3, 0));
        }
        if(location.equals(new BoardPoint(4,1)) && team == Team.Player
                && (!board.containsPiece(5, 0) || board.getPieceAt(5, 0).getTeam() != team)){
            targetingSquares.add(new BoardPoint(5, 0));
        }
    }
}
