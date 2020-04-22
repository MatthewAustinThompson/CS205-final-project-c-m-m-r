import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Soldier extends Piece
{


    public Soldier(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Soldier;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Soldier
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);


        BufferedImage img = null;
        try {
            //Create BufferedImage object by reading from file
            if(this.getTeam()==Team.Computer){
                img = ImageIO.read(new File("./src/Images/PawnRed.png"));
            }
            else {
                img = ImageIO.read(new File("./src/Images/PawnGreen.png"));
            }

        } catch (IOException e) {
            System.out.println("no img found");
        }
        //Graphics2d object.drawImage(Image object, x coord, y coord, idk but set null)
        g2d.drawImage(img, (int)this.center.x -20, (int)center.y - 22, null);

        /*
        // Write the word "Soldier"
        g2d.setFont(new Font("Courier", Font.PLAIN, 10));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Soldier"); // the number of pixels the string is long
        g2d.drawString("Soldier", (int)this.center.x - pixelLength/2, (int)center.y + 5);
        */

    }


    @Override
    public ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard)
    {
        ArrayList<BoardPoint> output = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Check for moves to the right
        //if(i < 8 && !board.containsPiece(i + 1, j))
        if(i < 8)
        {
            output.add(new BoardPoint(i + 1, j));
        }
        // Check for moves to the left
        //if(i > 0 && !board.containsPiece(i - 1, j))
        if(i > 0)
        {
            output.add(new BoardPoint(i - 1, j));
        }
        // Check for moves down
        //if(j < 9 && !board.containsPiece(i, j + 1) && team == Team.Computer)
        if(j < 9 && team == Team.Computer)
        {
            output.add(new BoardPoint(i, j + 1));
        }
        // Check for moves up
        //if(j >= 1 && !board.containsPiece(i, j - 1) && team == Team.Player)
        if(j >= 1 && team == Team.Player)
        {
            output.add(new BoardPoint(i, j - 1));
        }
        // special cases
        //if(team == Team.Player && i >= 3 && i <= 5 && j <= 2 && j > 0 && !board.containsPiece(4, 1))
        if(team == Team.Player && i >= 3 && i <= 5 && j <= 2 && j > 0 && !location.is(4,1))
        {
            output.add(new BoardPoint(4, 1));
        }
        //if(team == Team.Computer && i >= 3 && i <= 5 && j < 9 && j >= 7 && !board.containsPiece(4, 8))
        if(team == Team.Computer && i >= 3 && i <= 5 && j < 9 && j >= 7 && !location.is(4,8))
        {
            output.add(new BoardPoint(4, 8));
        }
        //if(location.is(4,8) && team == Team.Computer && !board.containsPiece(3, 9) )
        if(location.is(4,8) && team == Team.Computer)
        {
            output.add(new BoardPoint(3, 9));
            output.add(new BoardPoint(5, 9));
        }
        //if(location.is(4,1) && team == Team.Player && !board.containsPiece(3, 0))
        if(location.is(4,1) && team == Team.Player)
        {
            output.add(new BoardPoint(3, 0));
            output.add(new BoardPoint(5, 0));
        }

        return output;
    }
}
