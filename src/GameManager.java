import java.awt.*;
import java.util.ArrayList;

public class GameManager
{
    // The dimensions of the part of the window that the
    // GameManager can draw on
    private int width, height;

    private ArrayList<Piece> pieces;

    public GameManager(int inputWidth, int inputHeight)
    {
        width = inputWidth;
        height = inputHeight;

        pieces = new ArrayList<Piece>();
        pieces.add(new ExamplePieceMarcus(this,
                new Point(width/2, height/2), new BoardPoint(0,0)));

        pieces.add(new ExamplePieceRuth(this,
                new Point(width/3, height/3) , new BoardPoint(1,0)));
        // Add your piece to the array, but at a different position
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
        // Have each Piece draw itself
        for(Piece p : pieces)
        {
            p.render(g2d);
        }
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

    // Setters
    public void setWidth(int inputWidth)
    {
        width = inputWidth;
    }
    public void setHeight(int inputHeight)
    {
        height = inputHeight;
    }
}
