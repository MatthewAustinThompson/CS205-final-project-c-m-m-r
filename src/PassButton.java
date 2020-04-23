import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PassButton extends RectangularButton{


    private GameManager manager;
    private int windowWidth;
    private int windowHeight;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPosX;
    private int buttonPosY;

    RoundRectangle2D buttonDisplay;

    // Variables for drawing
    Color paleOrange = new Color(239, 164, 103);
    Color paleBlue = new Color(159, 208, 255);
    Color deepBlue = new Color(45, 58, 98);
    Color faded = new Color(170,170,170);

    private Color fillColor = paleBlue;
    private Color outlineColor = deepBlue;




    public PassButton(GameManager inputManager, MessageBoard messageBoard) {
        super(inputManager,
                (int)(messageBoard.getXRight()
                + messageBoard.getWidth()/2
                - inputManager.getWidth() / 4/2),
                (int)messageBoard.getYFloor() + 10,
                (int)inputManager.getWidth()/4,
                (int)inputManager.getHeight()/8,
                new Color(239, 164, 103),
                new Color(239, 164, 103),
                new Color(170,170,170),
                "Pass Turn"
        );

        /* super constructor has to be first line
        RectangularButton(GameManager inputManager, int inputPosX, int inputPosY, int inputButtonWidth,
                 int inputButtonHeight, Color inputFillColor, Color inputOutlineColor,
                 Color inputFadedFillColor, String inputText)
         */

        //Super input calculations
        manager = inputManager;
        windowWidth = (int) manager.getWidth();
        windowHeight = (int) manager.getHeight();

        buttonHeight = windowHeight / 8;
        buttonWidth = windowWidth / 4;

        buttonPosX = (int)(messageBoard.getXRight()
                + messageBoard.getWidth()/2
                - buttonWidth/2);

        buttonPosY = (int)(messageBoard.getYFloor() + 10);

        buttonDisplay = new RoundRectangle2D.Double(buttonPosX, buttonPosY, buttonWidth, buttonHeight, 45, 45);

    }

    //modified to extend, now use superclass methods

/*
    public void render(Graphics2D g2d){
        g2d.setColor(paleOrange);
        g2d.fill(buttonDisplay);

        Font font = new Font("Helvetica", Font.PLAIN, 25);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);


        String buttonTxt = "Pass Turn";

        //CENTER and DRAW button text
        double stringPosX = buttonDisplay.getMinX() + buttonDisplay.getWidth()/2 - metrics.getStringBounds(buttonTxt,null).getWidth()/2;
        double stringPosY = buttonDisplay.getMinY() + buttonDisplay.getHeight()/2 + metrics.getStringBounds(buttonTxt,null).getHeight()/4;
        g2d.drawString(buttonTxt,(int)stringPosX, (int)stringPosY);

    }
*/
    public boolean containsClickCoordinates(int x, int y){
        if (
                x > buttonDisplay.getMinX() &&
                y > buttonDisplay.getMinY() &&
                x < buttonDisplay.getMaxX() &&
                y < buttonDisplay.getMaxY()

        ){return true;}

        return false;

    }


}

