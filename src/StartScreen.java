import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class StartScreen {
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;

    // Variables for drawing
    private int lineWidth;
    private int pointRadius;
    private Color defaultColor;
    private int buttonX;
    private int buttonY;
    private int buttonWidth;
    private int buttonHeight;

    public StartScreen(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        lineWidth = 8;
        pointRadius = 10;
        defaultColor = Color.lightGray;
    }


    // ===============================
    //
    //       Drawing the Board
    //
    // ===============================
    public void render(Graphics2D g2d)
    {
        g2d.setColor(defaultColor);
        // background
        g2d.fillRect(0, 0, windowWidth, windowHeight);

        // Draw the grid
        int titleX = windowWidth / 2;
        int titleY = windowHeight / 4;
        buttonX = windowWidth / 2;
        buttonY = windowHeight * 6 / 10;
        buttonWidth = windowWidth / 8;
        buttonHeight = windowHeight / 10;

        // Draw the title
        g2d.setFont(new Font("Courier", Font.PLAIN, 48));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Janggi", titleX, titleY);

        // draw button
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setFont(new Font("Courier", Font.PLAIN, 32));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Start", buttonX + (buttonWidth / 4), buttonY + (buttonHeight / 2));
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
