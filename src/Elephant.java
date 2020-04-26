import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Elephant extends Piece
{
    int xOut; //Relative to the first move
    int yOut; //Relative to the first move

    ArrayList<BoardPoint> move1ArrayList = new ArrayList<BoardPoint>();
    BoardPoint m1Square1;
    BoardPoint m1Square2;
    BoardPoint m1Square3;
    BoardPoint m1Square4;
    BoardPoint m2Square1;
    BoardPoint m2Square2;
    BoardPoint m3Square1;
    BoardPoint m3Square2;


    public Elephant(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Elephant;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Elephant
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
            img = ImageIO.read(new File("./src/Images/ElephantRed2.png"));
            }
            else
            {
                img = ImageIO.read(new File("./src/Images/ElephantGreen2.png"));
            }

        } catch (IOException e) {
            System.out.println("no img found");
        }
        g2d.drawImage(img, (int)this.center.x -20, (int)center.y - 20, null);
    }

    @Override
    public ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard)
    {
        ArrayList<BoardPoint> output = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Check for moves to the right
        if(i < 6 && hypotheticalBoard[i+1][j] == null)
        {
            if(j > 1 && hypotheticalBoard[i+2][j-1] == null) // not too high
            {
                output.add(new BoardPoint(i + 3, j - 2));
            }
            if(j < 8 && hypotheticalBoard[i+2][j+1] == null) // not too low
            {
                output.add(new BoardPoint(i + 3, j + 2));
            }
        }
        // Check for moves to the left
        if(i > 2 && hypotheticalBoard[i-1][j] == null)
        {
            if(j > 1 && hypotheticalBoard[i-2][j-1] == null) // not too high
            {
                output.add(new BoardPoint(i - 3, j - 2));
            }
            if(j < 8 && hypotheticalBoard[i-2][j+1] == null) // not too low
            {
                output.add(new BoardPoint(i - 3, j + 2));
            }
        }
        // Check for moves down
        if(j < 7 && hypotheticalBoard[i][j + 1] == null)
        {
            if(i > 1 && hypotheticalBoard[i-1][j+2] == null) // not too far left
            {
                output.add(new BoardPoint(i - 2, j + 3));
            }
            if(i < 7 && hypotheticalBoard[i+1][j+2] == null) // not too far right
            {
                output.add(new BoardPoint(i + 2, j + 3));
            }
        }
        // Check for moves up
        if(j > 2 && hypotheticalBoard[i][j - 1] == null)
        {
            if(i > 1 && hypotheticalBoard[i-1][j-2] == null) // not too far left
            {
                output.add(new BoardPoint(i - 2, j - 3));
            }
            if(i < 7 && hypotheticalBoard[i+1][j-2] == null) // not too far right
            {
                output.add(new BoardPoint(i + 2, j - 3));
            }
        }
        return output;
    }
}
