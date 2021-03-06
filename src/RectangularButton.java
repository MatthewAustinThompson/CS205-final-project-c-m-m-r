import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RectangularButton
{
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonPosX;
    private int buttonPosY;

    RoundRectangle2D buttonDisplay;
    private boolean isFaded;
    private boolean isHovered;

    private Color fillColor;
    private Color outlineColor;
    private Color fadedFillColor;
    private Color hoverColor; // when the mouse hovers over this

    private String text;



    public RectangularButton(GameManager inputManager, int inputPosX, int inputPosY, int inputButtonWidth,
                             int inputButtonHeight, Color inputFillColor, Color inputOutlineColor,
                             Color inputFadedFillColor, String inputText)
    {
        manager = inputManager;
        windowWidth = (int) manager.getWidth();
        windowHeight = (int) manager.getHeight();

        buttonPosX = inputPosX;
        buttonPosY = inputPosY;
        buttonWidth = inputButtonWidth;
        buttonHeight = inputButtonHeight;

        fillColor = inputFillColor;
        outlineColor = inputOutlineColor;
        fadedFillColor = inputFadedFillColor;
        hoverColor = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 128); // lower alpha

        text = inputText;

        isFaded = false;
        isHovered = false;

        buttonDisplay = new RoundRectangle2D.Double(buttonPosX, buttonPosY, buttonWidth, buttonHeight, 45, 45);

    }


    public void render(Graphics2D g2d)
    {
        if(!isFaded)
        {
            if(isHovered)
            {
                g2d.setColor(hoverColor);
            }
            else
            {
                g2d.setColor(fillColor);
            }
        }
        else
        {
            g2d.setColor(fadedFillColor);
        }
        g2d.fill(buttonDisplay);

        Font font = new Font("Helvetica", Font.PLAIN, 25);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        //CENTER and DRAW button text
        double stringPosX = buttonDisplay.getMinX() + buttonDisplay.getWidth()/2 - metrics.getStringBounds(text,null).getWidth()/2;
        double stringPosY = buttonDisplay.getMinY() + buttonDisplay.getHeight()/2 + metrics.getStringBounds(text,null).getHeight()/4;
        g2d.drawString(text,(int)stringPosX, (int)stringPosY);

    }

    public boolean containsClick(int x, int y)
    {
        if (isFaded)
        {
            return false;
        }

        return x > buttonDisplay.getMinX() && y > buttonDisplay.getMinY() &&
              x < buttonDisplay.getMaxX() && y < buttonDisplay.getMaxY();
    }

    public void reactToMouseMotion(int mx, int my)
    {
        if(containsClick(mx, my))
        {
            setIsHovered(true);
        }
        else
        {
            setIsHovered(false);
        }
    }

    // Setters
    public void setIsFaded(boolean input)
    {
        isFaded = input;
    }
    public void setIsHovered(boolean input)
    {
        isHovered = input;
    }
}
