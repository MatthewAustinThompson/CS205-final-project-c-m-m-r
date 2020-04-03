import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Horse extends Piece
{


    public Horse(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Horse;
        this.initializeOctagon();
    }

    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Horse
    //
    // =============================================

    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Write the word "Horse"
        g2d.setFont(new Font("Courier", Font.PLAIN, 18));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Horse"); // the number of pixels the string is long
        g2d.drawString("Horse", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }



    @Override
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Check for moves to the right
        if(i < 7 && !board.containsPiece(i + 1, j))
        {
            if(j > 0) // not too high
            {
                targetingSquares.add(new BoardPoint(i + 2, j - 1));
            }
            if(j < 9) // not too low
            {
                targetingSquares.add(new BoardPoint(i + 2, j + 1));
            }
        }
        // Check for moves to the left
        if(i > 1 && !board.containsPiece(i - 1, j))
        {
            if(j > 0) // not too high
            {
                targetingSquares.add(new BoardPoint(i - 2, j - 1));
            }
            if(j < 9) // not too low
            {
                targetingSquares.add(new BoardPoint(i - 2, j + 1));
            }
        }
        // Check for moves down
        if(j < 8 && !board.containsPiece(i, j + 1))
        {
            if(i > 0) // not too far left
            {
                targetingSquares.add(new BoardPoint(i - 1, j + 2));
            }
            if(i < 8) // not too far right
            {
                targetingSquares.add(new BoardPoint(i + 1, j + 2));
            }
        }
        // Check for moves up
        if(j > 1 && !board.containsPiece(i, j - 1))
        {
            if(i > 0) // not too far left
            {
                targetingSquares.add(new BoardPoint(i - 1, j - 2));
            }
            if(i < 8) // not too far right
            {
                targetingSquares.add(new BoardPoint(i + 1, j - 2));
            }
        }
    }
}
