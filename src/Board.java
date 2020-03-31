import java.awt.*;

public class Board
{
    private GameManager manager;
    private int windowWidth;
    private int windowHeight;

    // The spaces on the board
    private Piece[][] spaces;  // store a Piece, or null at each
    private boolean[][] highlighted; // if a space is highlighted


    // Variables for drawing
    private double lineWidth;
    private double pointRadius;
    private Color defaultColor;
    private Color highlightedColor;

    public Board(GameManager inputManager)
    {
        manager = inputManager;
        windowWidth = (int)manager.getWidth();
        windowHeight = (int)manager.getHeight();

        this.initializePieces();
        this.initializeHighlighted();

        lineWidth = 10;
        pointRadius = 20;
        defaultColor = Color.BLUE;
        highlightedColor = Color.MAGENTA;
    }


    // Puts all of the Pieces in their starting positions in spaces
    public void initializePieces()
    {
        spaces = new Piece[9][10]; // 9 columns, each column has 10 spaces
    }

    public void initializeHighlighted()
    {
        highlighted = new boolean[9][10]; // 9 columns, each column has 10 spaces
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                highlighted[i][j] = false;
            }
        }
    }
}
