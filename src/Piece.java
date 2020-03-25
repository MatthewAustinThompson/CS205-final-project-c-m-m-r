import java.awt.*;

public abstract class Piece
{
    protected GameManager manager;

    // The current center of the Piece
    protected Point center;

    // The location of the Piece in terms of the board
    protected BoardPoint location;

    protected Color fillColor;
    protected Color symbolColor;

    public Piece(GameManager inputManager, Point inputCenter, BoardPoint inputLocation)
    {
        manager = inputManager;
        center = inputCenter;
        location = inputLocation;
    }

    public abstract void tick();

    public abstract void render(Graphics2D g2d);

    // Getters
    public Point getCenter()
    {
        return center;
    }
    public BoardPoint getLocation()
    {
        return location;
    }

    // Setters
    public void setCenter(Point inputCenter)
    {
        center = inputCenter;
    }
    public void setLocation(BoardPoint inputLocation)
    {
        location = inputLocation;
    }
}
