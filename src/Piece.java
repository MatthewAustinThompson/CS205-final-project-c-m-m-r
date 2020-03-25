import java.awt.*;

public abstract class Piece
{
    private GameManager manager;

    // The current center of the Piece
    private Point center;

    // The location of the Piece in terms of the board
    private BoardPoint location;

    private Color fillColor;
    private Color symbolColor;

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
