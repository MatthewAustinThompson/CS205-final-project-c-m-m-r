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
