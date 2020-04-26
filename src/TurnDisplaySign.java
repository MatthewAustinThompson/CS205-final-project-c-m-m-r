import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class TurnDisplaySign {

    private GameManager manager;
    private int windowWidth;
    private int windowHeight;
    private int turnBoardWidth;
    private int turnBoardHeight;

    RoundRectangle2D outRec, fillRec;

    // Variables for drawing
    private int lineWidth;
    private int pointRadius;
    Color paleOrange = new Color(255,207,165);
    Color paleBlue = new Color(159,208,255);
    Color deepBlue =  new Color(45,58,98);

    private Color fillColor = paleBlue;
    private Color outlineColor = deepBlue;

    int signPositionX;
    int signPositionY;


    public TurnDisplaySign(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        turnBoardHeight = windowHeight/8;
        turnBoardWidth = windowWidth/4;

        signPositionX = (windowWidth - (int) (windowWidth * .345) );
        signPositionY = (windowHeight - (int) (windowHeight * 0.9) );


        lineWidth = 8;
        pointRadius = 10;

        outRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 45, 45);
        fillRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 35, 35);
    }


    // ===============================
    //
    //       Drawing the Turn Sign
    //
    // ===============================
    public void render(Graphics2D g2d)
    {

        Font font = new Font("Helvetica", Font.PLAIN, 25);
        FontMetrics metrics = g2d.getFontMetrics(font);

        // Outline
        outRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 45, 45);
        g2d.setColor(outlineColor);
        g2d.fill(outRec);

        // Fill
        fillRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 35, 35);
        fillRec.setFrame(outRec.getMinX() + 8,outRec.getMinY() + 6,turnBoardWidth * .94, turnBoardHeight * .85);
        g2d.setColor(fillColor);
        g2d.fill(fillRec);


        // Draw the title
        g2d.setColor(outlineColor);

        g2d.setFont(font);

        String turnMessage = manager.getWhoseTurnItIs() + "\'s Move";

        double stringPosX = outRec.getMinX() + outRec.getWidth()/2 - metrics.getStringBounds(turnMessage,null).getWidth()/2;
        double stringPosY = outRec.getMinY() + outRec.getHeight()/2 + metrics.getStringBounds(turnMessage,null).getHeight()/4;

        g2d.drawString(turnMessage,(int)stringPosX, (int)stringPosY);
    }

    public double getSignPositionXLeft() {
        return outRec.getMinX();
    }

    public double getSignPositionYFloor() {
        return outRec.getMaxY();
    }

    public double getOuterWidth() {
        return outRec.getWidth();
    }
}

