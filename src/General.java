import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class General extends Piece
{

    public General(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.General;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the General
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);


        BufferedImage img = null;
        try
        {
            //Create BufferedImage object by reading from file
            if(this.getTeam()==Team.Computer)
            {
                img = ImageIO.read(new File("./src/Images/KingRed.png"));
            }
            else
            {
                img = ImageIO.read(new File("./src/Images/KingGreen.png"));
            }

        }
        catch (IOException e)
        {
            System.out.println("no img found");
        }
        g2d.drawImage(img, (int)this.center.x -20, (int)center.y - 23, null);
    }


    @Override
    public ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard)
    {
        ArrayList<BoardPoint> output = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Player team logic
        if(team == Team.Player)
        {
            // Check for moves to the right
            if(i < 5)
            {
                output.add(new BoardPoint(i + 1, j));
                if(location.is(4,8)) // Moving diagonally from the center of the fortress
                {
                    output.add(new BoardPoint(5, 7));
                    output.add(new BoardPoint(5, 9));
                }
            }
            // Check for moves to the left
            if(i > 3)
            {
                output.add(new BoardPoint(i - 1, j));
                if(location.is(4,8)) // Moving diagonally from the center of the fortress
                {
                    output.add(new BoardPoint(3, 7));
                    output.add(new BoardPoint(3, 9));
                }
            }
            // Check for moves down
            if(j < 9)
            {
                output.add(new BoardPoint(i, j + 1));
            }
            // Check for moves up
            if(j > 7)
            {
                output.add(new BoardPoint(i, j - 1));
            }
            // check for center spot in the "castle"
            if(!location.is(4,8))
            {
                output.add(new BoardPoint(4, 8));
            }
        }
        else if(team == Team.Computer)
        {

            // Check for moves to the right
            if(i < 5)
            {
                output.add(new BoardPoint(i + 1, j));
                if(location.is(4,1)) // Moving diagonally from the center of the fortress
                {
                    output.add(new BoardPoint(5, 0));
                    output.add(new BoardPoint(5, 2));
                }
            }
            // Check for moves to the left
            if(i > 3)
            {
                output.add(new BoardPoint(i - 1, j));
                if(location.is(4,1)) // Moving diagonally from the center of the fortress
                {
                    output.add(new BoardPoint(3, 0));
                    output.add(new BoardPoint(3, 2));
                }
            }
            // Check for moves down
            if(j < 2)
            {
                output.add(new BoardPoint(i, j + 1));
            }
            // Check for moves up
            if(j > 0)
            {
                output.add(new BoardPoint(i, j - 1));
            }
            // check for center spot in the "castle"
            if(!location.is(4, 1))
            {
                output.add(new BoardPoint(4, 1));
            }
        }
        return output;
    }
}
