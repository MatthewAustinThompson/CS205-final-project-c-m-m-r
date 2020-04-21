
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageBoard {

    //=========================================
    //Game Board Stuff
    //=========================================
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;
    private int messageBoardWidth;
    private int messageBoardHeight;
    private RoundRectangle2D outRec,fillRec;
    TurnDisplaySign turnDisplaySign;

    //=========================================
    //Drawing Stuff
    //=========================================
    private Color fillColor;
    private Color outlineColor;
    int msgBoardPositionX;
    int msgBoardPositionY;

    //=========================================
    //Message Board Stuff
    //=========================================
    private ArrayList<String> messagesToDisplay;
    String concatMessages = "";

    public MessageBoard(GameManager inputManager, TurnDisplaySign turnDisplaySign)
    {
        this.turnDisplaySign = turnDisplaySign;
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        messageBoardHeight = windowHeight/4;
        messageBoardWidth = windowWidth/4;

        msgBoardPositionX = (windowWidth - (int) (windowWidth * .37) );
        msgBoardPositionY = ((int) this.turnDisplaySign.getSignPositionYFloor() + 40 );


        fillColor = new Color(128,178,245);
        outlineColor = new Color(45,58,98);
    }


    // ===========================================
    //       Add to MSG BOARD
    // ===========================================
    public void addMessageToMessageBoard(String msg) {
        concatMessages = concatMessages + "\n" + msg;
    }

    // ===========================================
    //
    //       Drawing the Message Board Sign
    //
    // ===========================================
    public void render(Graphics2D g2d)
    {
        //add string messages, turn into text object

        //The board is as tall as it needs to be + a little padding


        //define font to use
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        FontMetrics metrics = g2d.getFontMetrics(font);

        // Outline rectanlge
        outRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 30, 30);
        g2d.setColor(outlineColor);
        g2d.fill(outRec);

        // Fill rectanlge
        fillRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 35, 35);
        fillRec.setFrame(outRec.getMinX() + 8,outRec.getMinY() + 6,messageBoardWidth * .94, messageBoardHeight * .85);
        g2d.setColor(fillColor);
        g2d.fill(fillRec);



        //FIXED ERROR: Metrics was created w font object.  Resetting font to different font than what used metrics causes
        //measurements not to align
        double stringPosX = outRec.getMinX() + outRec.getWidth()/2 ;
        double stringPosY = fillRec.getMinY() + 20;



    }



}
