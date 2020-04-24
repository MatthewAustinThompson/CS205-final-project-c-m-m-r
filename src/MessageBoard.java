import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MessageBoard {

    //=========================================
    //Game Board Stuff
    //=========================================
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;
    private int messageBoardWidth;
    private int messageBoardHeight;
    private boolean needsToUpdate = false;
    private RoundRectangle2D outRec,fillRec, coverMsgRec;
    TurnDisplaySign turnDisplaySign;

    //=========================================
    //Drawing Stuff
    //=========================================
    Color paleOrange = new Color(240, 188, 140);
    Color paleBlue = new Color(159,208,255);
    Color deepBlue =  new Color(0,54,108);
    private Color fillColor = paleBlue;
    private Color outlineColor = paleBlue;
    private Color textColor = deepBlue;
    int msgBoardPositionX;
    int msgBoardPositionY;

    //=========================================
    //Message Board Stuff
    //=========================================
    int visibleMsgCounter = 0;
    int msgSpacing;

    double stringPosX = 0;
    double stringPosY = 0;
    private ArrayList<String> messagesToDisplay = new ArrayList<String>();

    double coverWidth;
    double coverHeight;
    int coverX;
    int coverY;

    public MessageBoard(GameManager inputManager, TurnDisplaySign turnDisplaySign)
    {
        this.turnDisplaySign = turnDisplaySign;
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        messageBoardHeight = (int) (windowHeight * .38);
        messageBoardWidth = (int) (windowWidth * .375);

        //msgBoardPositionX = ( (int) this.turnDisplaySign.getSignPositionXLeft() - 30);
        msgBoardPositionX = (int) (this.turnDisplaySign.getSignPositionXLeft() +
                this.turnDisplaySign.getOuterWidth() * .5
                - messageBoardWidth * .5 );

        msgBoardPositionY = ((int) this.turnDisplaySign.getSignPositionYFloor() + 40 );

        outRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 25, 25);
        fillRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 30, 30);

    }


    // ===========================================
    //       Add to MSG BOARD
    // ===========================================
    public void addMessageToMessageBoard(String msg) {
        messagesToDisplay.add(msg);
    }

    // ===========================================
    //
    //       Drawing the Message Board Sign
    //
    // ===========================================
    public void render(Graphics2D g2d) throws ConcurrentModificationException
    {

        //==============================
        // FONT to use
        //==============================

        Font font = new Font("Helvetica", Font.PLAIN, 20);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.setFont(font);

        Font smallerFont = new Font("Helvetica", Font.PLAIN, 20);
        FontMetrics smallerMetrics = g2d.getFontMetrics(smallerFont);

        msgSpacing = (int) (metrics.getHeight() * 1.50);

        //==============================
        // RECTANGLES
        //==============================

        // Outline rectangle
        outRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 25, 25);
        g2d.setColor(outlineColor);
        g2d.fill(outRec);

        /*
        =====================================
        BOARD IS ALL ONE COLOR, FILLREC NOW UNNECESSARY
        (keeping code just in case)
        =====================================
        fillRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 30, 30);
        double fillRecWidth = messageBoardWidth * .94;
        double fillRecHeight = messageBoardHeight * .90;
        int fillRecPosX = (int)(outRec.getMinX() + outRec.getWidth()/2 - fillRecWidth/2);
        int fillRecPosY = (int)(outRec.getMinY() + outRec.getHeight()/2 - fillRecHeight/2);
        fillRec.setFrame(fillRecPosX,fillRecPosY,fillRecWidth,fillRecHeight);
        g2d.setColor(fillColor);
        g2d.fill(fillRec); */


        //FIXED ERROR: Metrics was created w font object.  Resetting font to different font than what used metrics causes
        //measurements not to align


        //==========================================
        //PRINT THE MESSAGES THAT HAVE BEEN ADDED
        //==========================================
        g2d.setFont(smallerFont);

        g2d.setColor(textColor);
        this.visibleMsgCounter = 0;

        if (!messagesToDisplay.isEmpty()) {
            for (String msg : messagesToDisplay) {
                //==============
                //The x value of text is :
                 // + start at rectangle's left X position
                // + half the rectangle's width to center by the text's edge
                // - half the text's width to center by the text's center
                //==============
                stringPosX = outRec.getMinX() + (outRec.getWidth() * .5) - (smallerMetrics.getStringBounds(msg, null).getWidth() * .5);
                stringPosY = outRec.getMinY() + (outRec.getHeight() * .15 + (this.visibleMsgCounter * msgSpacing));
                g2d.drawString(msg, (int) stringPosX, (int) (stringPosY) );

                visibleMsgCounter++;

                /*=========================CLEARED EXTERNALLY=====================================
                //CLEARING WHEN OVERFILLED NOW UNNECESSARY, keeping code in case
                if ((stringPosY + (10 * visibleMsgCounter) + 15) > outRec.getMaxY()) {

                        // Clear board
                        coverMsgRec = new RoundRectangle2D.Double(msgBoardPositionX, msgBoardPositionY, messageBoardWidth, messageBoardHeight, 30, 30);
                        g2d.setColor(fillColor);
                        g2d.fill(coverMsgRec);

                        //Place new string on top of cleared board
                        this.visibleMsgCounter = 1;
                        stringPosY = outRec.getMinY() + (outRec.getHeight() * .15 + (this.visibleMsgCounter * msgSpacing));

                        g2d.setColor(textColor);
                        g2d.drawString(msg, (int) stringPosX, (int) stringPosY);
                }
                 */
            }
        }
    }

    public double getYFloor(){
        return outRec.getMaxY();
    }

    public double getXRight(){
        return outRec.getMinX();
    }

    public double getWidth(){
        return outRec.getWidth();
    }

    public void clear(){
        messagesToDisplay.clear();
    }

    public boolean needsToUpdate(){
        return needsToUpdate;
    }

    public void setNeedsToUpdate(boolean changedUpdateStatus){
        this.needsToUpdate = changedUpdateStatus;
    }

}
