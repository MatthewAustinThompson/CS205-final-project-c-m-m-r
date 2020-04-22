import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class StartScreen {
    // ===============================

    private GameManager manager;
    private int windowWidth;
    private int windowHeight;

    // ===============================
    // Variables for drawing
    private int lineWidth;
    private int pointRadius;
    private int buttonX;
    private int buttonY;
    private int buttonWidth;
    private int buttonHeight;

    // ===============================
    RoundRectangle2D buttonVisual;
    private final Color txtColor = new Color(45,58,98);
    private final Color bgColor =  new Color(128,178,245);
    private final Color buttonColor = new Color(247,183,109);

    String gameName = "Janggi";
    String buttonText = "Start Game";


    public StartScreen(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        lineWidth = 8;
        pointRadius = 10;
    }


    // ===============================
    //
    //       Drawing the Screen
    //
    // ===============================
    public void render(Graphics2D g2d)
    {
        // ==============NAMES=================


        // ==============BACKGROUND=================
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, windowWidth, windowHeight);

        // ==============FONT=================
        Font font = new Font("Helvetica", Font.PLAIN, 54);
        FontMetrics metrics = g2d.getFontMetrics(font);

        Font smallerFont = new Font("Helvetica", Font.PLAIN, 40);
        FontMetrics smallerMetrics = g2d.getFontMetrics(smallerFont);



        // ==============WINDOW POSITION=================
        int titleX = (int) (windowWidth / 2 - metrics.getStringBounds(gameName,null).getWidth()/2);
        int titleY = windowHeight / 4;

        buttonWidth = (int) (windowWidth * .25);
        buttonHeight = (int) (windowHeight * .20);

        // ==============BUTTON POSITION=================
        buttonX = windowWidth / 2 - buttonWidth/2;
        buttonY = windowHeight * 6 / 10;

        //
        // ==============DRAW TITLE=================
        g2d.setFont(font);
        g2d.setColor(txtColor);
        g2d.drawString(gameName, titleX, titleY);

        // ==============DRAW BUTTON=================
        g2d.setFont(smallerFont);
        g2d.setColor(buttonColor);
        buttonVisual = new RoundRectangle2D.Double(buttonX, buttonY, buttonWidth, buttonHeight, 45, 45);

        g2d.fill( buttonVisual);



        // ==============DRAW TEXT IN BUTTON=================
        g2d.setFont(smallerFont);
        g2d.setColor(Color.white);

        int stringPosX = (int) (buttonVisual.getMinX() + buttonVisual.getWidth()/2
                - smallerMetrics.getStringBounds(buttonText,null).getWidth()/2);

        int stringPosY = (int) (buttonVisual.getMinY() + buttonVisual.getHeight()/2
                - smallerMetrics.getHeight()/8);

        g2d.drawString(buttonText,stringPosX,stringPosY);

    }

    public boolean buttonPress(int mx, int my)
    {
        if(mx >= buttonX && mx <= buttonX + buttonWidth && my >= buttonY && my <= buttonY + buttonHeight){
            return true;
        } else {
            return false;
        }
    }

    // Getters
    public int getButtonX(){
        return buttonX;
    }
    public int getButtonY(){
        return buttonY;
    }
    public int getWindowWidth()
    {
        return windowWidth;
    }
    public int getWindowHeight()
    {
        return windowHeight;
    }

}
