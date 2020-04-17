import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public abstract class ExamplePieceChris extends Piece
{
    // The octagon shape of the piece
    private Path2D.Double outline;

    // The shape you draw in the middle
    private Path2D.Double symbol;

    public ExamplePieceChris(GameManager inputManager, Point inputCenter, BoardPoint inputLocation)
    {
        super(inputManager, inputCenter, inputLocation);
        symbolColor = Color.BLACK;
        fillColor = Color.ORANGE;
        this.initializeOctagon();
        this.initializeSymbol();
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics2D g2d)
    {
        // Fill in the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Draw the symbol
        g2d.setColor(symbolColor);
        g2d.fill(symbol);
    }

    public void initializeOctagon()
    {
        // Octagon geometry stuff
        int sideLength = 30;
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

    public void initializeSymbol()
    {
        symbol = new Path2D.Double();

        symbol.moveTo(center.x, center.y);
        symbol.lineTo(center.x-20, center.y - 15);
        symbol.lineTo(center.x-20, center.y + 15);
        symbol.moveTo(center.x, center.y);
        symbol.lineTo(center.x+20, center.y - 15);
        symbol.lineTo(center.x+20, center.y + 15);
    }

    // Overriding setters
    public void setCenter(Point inputCenter)
    {
        // When the Piece moves, the paths have to get translated
        AffineTransform tx = new AffineTransform();
        tx.translate(inputCenter.x - center.x, inputCenter.y - center.y);
        outline = (Path2D.Double) tx.createTransformedShape(outline);
        symbol = (Path2D.Double) tx.createTransformedShape(symbol);

        center = inputCenter;
    }

    @Override
    public void findTargetingSquares() {

    }
}
