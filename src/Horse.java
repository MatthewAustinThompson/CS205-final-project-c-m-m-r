import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Horse extends Piece
{

    // The octagon shape of the piece
    private Path2D.Double outline;

    public Horse(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
    {
        super(inputManager, inputCenter, inputLocation);
        team = inputTeam;
        if(team == Team.Player)
        {
            symbolColor = Color.CYAN;
        }
        else
        {
            symbolColor = Color.RED;
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
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Horse"); // the number of pixels the string is long
        g2d.drawString("Horse", (int)this.center.x - pixelLength/2, (int)center.y);
    }

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

    @Override
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Check for moves to the right
        if(i < 7)
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
        if(i > 1)
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
        if(j < 8)
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
        if(j > 1)
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
