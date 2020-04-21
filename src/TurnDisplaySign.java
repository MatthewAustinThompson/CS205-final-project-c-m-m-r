import javafx.scene.text.Text;

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

    // Variables for drawing
    private int lineWidth;
    private int pointRadius;
    private Color fillColor;
    private Color outlineColor;
    int signPositionX;
    int signPositionY;

    Text text;

    public TurnDisplaySign(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        turnBoardHeight = windowHeight/8;
        turnBoardWidth = windowWidth/4;

        signPositionX = (windowWidth - (int) (windowWidth * .37) );
        signPositionY = (windowHeight - (int) (windowHeight * 0.9) );


        lineWidth = 8;
        pointRadius = 10;
        fillColor = new Color(128,178,245);
        outlineColor = new Color(45,58,98);
    }


    // ===============================
    //
    //       Drawing the Turn Sign
    //
    // ===============================
    public void render(Graphics2D g2d)
    {

        Font font = new Font("Helvetica", Font.PLAIN, 20);
        FontMetrics metrics = g2d.getFontMetrics(font);

        // Outline
        RoundRectangle2D outRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 45, 45);
        g2d.setColor(outlineColor);
        g2d.fill(outRec);

        // Fill
        RoundRectangle2D fillRec = new RoundRectangle2D.Double(signPositionX, signPositionY, turnBoardWidth, turnBoardHeight, 35, 35);
        fillRec.setFrame(outRec.getMinX() + 8,outRec.getMinY() + 6,turnBoardWidth * .94, turnBoardHeight * .85);
        g2d.setColor(fillColor);
        g2d.fill(fillRec);


        // Draw the title
        g2d.setColor(outlineColor);

        g2d.setFont(font);

        String turnMessage = manager.getWhoseTurnItIs() + "\'s Move";

        //FIXED ERROR: Metrics was created w font object.  Resetting font to different font than what used metrics causes
        //measurements not to align
        double stringPosX = outRec.getMinX() + outRec.getWidth()/2 - metrics.getStringBounds(turnMessage,null).getWidth()/2;
        double stringPosY = outRec.getMinY() + outRec.getHeight()/2 + metrics.getStringBounds(turnMessage,null).getHeight()/4;

        //g2d.drawString(turnMessage, signPositionX + turnBoardWidth/15, (int) (signPositionY+turnBoardHeight/1.8));
        g2d.drawString(turnMessage,(int)stringPosX, (int)stringPosY);
    }



}
