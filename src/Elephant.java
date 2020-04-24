import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Elephant extends Piece
{

    // x-------------->
    //|0==============x8| y
    //| COMPUTER SIDE   | |
    //|                 | |
    //|                 | |
    //|  PLAYER SIDE    | |
    //|y9===============| V

    int xOut; //Relative to the first move
    int yOut; //Relative to the first move

    ArrayList<BoardPoint> move1ArrayList = new ArrayList<BoardPoint>();
    BoardPoint m1Square1;
    BoardPoint m1Square2;
    BoardPoint m1Square3;
    BoardPoint m1Square4;
    BoardPoint m2Square1;
    BoardPoint m2Square2;
    BoardPoint m3Square1;
    BoardPoint m3Square2;


    public Elephant(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
    {
        super(inputManager, inputCenter, inputLocation);
        team = inputTeam;
        if(team == Team.Player)
        {
            symbolColor = new Color(0.1f, 0.4f, 0.8f);
        }
        else
        {
            symbolColor = new Color(0.8f, 0.1f, 0.4f);
        }
        fillColor = Color.WHITE;
        pieceType = PieceType.Elephant;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Elephant
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);


        BufferedImage img = null;
        try {
            //Create BufferedImage object by reading from file
            if(this.getTeam()==Team.Computer){
            img = ImageIO.read(new File("./src/Images/ElephantRed2.png"));
            }
            else {
                img = ImageIO.read(new File("./src/Images/ElephantGreen2.png"));
            }

        } catch (IOException e) {
            System.out.println("no img found");
        }
        //Graphics2d object.drawImage(Image object, x coord, y coord, idk but set null)
        g2d.drawImage(img, (int)this.center.x -20, (int)center.y - 20, null);
    }

    // ==== Temporarily commented out ====
    /*
    //Checks that the path square: Exists on board && Contains no pieces
    public boolean isValidElephantPathSquare(BoardPoint point) {

        if (point.existsOnBoard() && !(board.containsPiece(point)) && !(point.getWasClipped()) ){
            return true;
        }
        else {
            return false;
        }

    }


    @Override
    //Elephant's full movement consists of 3 parts: Move 1,2,3
    //Define Path: Move 1, Move 2
    //The piece is blocked from moving if ANY square on its Path is OCCUPIED or OFF THE BOARD.
    //Move1: 1 square any direction
    //Move2: diagonal 1 square (+-X by 1, 1 in same )
    //Move3: diagonal 1 square (+-X by 1 Same as its Move 2, away from team side by 1)
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();

        int i = location.getX();
        int j = location.getY();


        // x-------------->
        //|0==============x8| y
        //| COMPUTER SIDE   | |
        //|                 | |
        //|                 | |
        //|  PLAYER SIDE    | |
        //|y9===============| V


        //Calculate Move 1 board points
        m1Square1 = new BoardPoint(i+1,j); //m1Square1: (X+1, y+0)
        m1Square2 = new BoardPoint(i-1,j); //m1Square2: (X-1, y+0)
        m1Square3 = new BoardPoint(i,j+1); //m1Square3: (X+0, y+1)
        m1Square4 = new BoardPoint(i,j-1); //m1Square4: (X+0, y-1)


        //=======================================================
        //The full elephant move must be found in pieces: move 1, move 2, move3
        //If move 1 and move 2 are off-board or occupied by any piece
        // This blocks the entire move.
        //=======================================================
        //For each m1Square, either:
        // 1.) the X coord was varied => (xOut defined)
        // 2.) or the Y coord was varied => (yOut defined)
        //The subsequent m2Square values are found:
        // If xOut defined => yOut can be defined: 1 or -1
        // if yOut defined => xOut can be defined: 1 or -1
        //The destination location, m3Square values are then found
        //By adding the now defined xOut and yOut values to m2Square

        //=======================================================
        //m1Square1: (X+1, y+0)
        //=======================================================
        //xOut =1, yOut = +- 1
        if (isValidElephantPathSquare(m1Square1) ){
            xOut = 1;
            m2Square1 = new BoardPoint( m1Square1.getX() + xOut, m1Square1.getY() + 1 );
            m2Square2 = new BoardPoint(m1Square1.getX() + xOut, m1Square1.getY() + -1);

            if (isValidElephantPathSquare(m2Square1)){
                yOut = 1;
                m3Square1 = new BoardPoint(m2Square1.getX()+xOut,m2Square1.getY() + yOut );

                if (!m3Square1.getWasClipped() && m3Square1.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square1.getX(),m3Square1.getY()));
                }
            }

            if (isValidElephantPathSquare(m2Square2)){
                yOut = -1;
                m3Square2 = new BoardPoint(m2Square2.getX()+xOut,m2Square2.getY() + yOut );

                if (!m3Square2.getWasClipped() && m3Square2.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square2.getX(),m3Square2.getY()));
                }
            }
        }

        //=======================================================
        //m1Square2: (X-1, y+0)
        //=======================================================
        //xOut = -1, yOut = +- 1
        if (isValidElephantPathSquare(m1Square2) ){
            xOut = -1;
            m2Square1 = new BoardPoint( m1Square2.getX() + xOut, m1Square2.getY() + 1 );
            m2Square2 = new BoardPoint(m1Square2.getX() + xOut, m1Square2.getY() + -1);

            if (isValidElephantPathSquare(m2Square1)){
                yOut = 1;
                m3Square1 = new BoardPoint(m2Square1.getX()+xOut,m2Square1.getY() + yOut );

                if (!m3Square1.getWasClipped() && m3Square1.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square1.getX(),m3Square1.getY()));
                }
            }

            if (isValidElephantPathSquare(m2Square2)){
                yOut = -1;
                m3Square2 = new BoardPoint(m2Square2.getX()+xOut,m2Square2.getY() + yOut );

                if (!m3Square2.getWasClipped() && m3Square2.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square2.getX(),m3Square2.getY()));
                }
            }
        }

        //=======================================================
        //m1Square3: (X+0, y+1)
        //=======================================================
        //xOut = +- 1, yOut = 1
        if (isValidElephantPathSquare(m1Square3) ){
            yOut = 1;
            m2Square1 = new BoardPoint( m1Square3.getX() + 1, m1Square3.getY() + yOut );
            m2Square2 = new BoardPoint(m1Square3.getX() -1, m1Square3.getY() + yOut);

            if (isValidElephantPathSquare(m2Square1)){
                xOut = 1;
                m3Square1 = new BoardPoint(m2Square1.getX()+xOut,m2Square1.getY() + yOut );

                if (!m3Square1.getWasClipped() && m3Square1.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square1.getX(),m3Square1.getY()));
                }
            }

            if (isValidElephantPathSquare(m2Square2)){
                xOut = -1;
                m3Square2 = new BoardPoint(m2Square2.getX()+xOut,m2Square2.getY() + yOut );

                if (!m3Square2.getWasClipped() && m3Square2.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square2.getX(),m3Square2.getY()));
                }
            }
        }

        //=======================================================
        //m1Square4: (X+0, y-1)
        //=======================================================
        //xOut = +- 1, yOut = -1
        if (isValidElephantPathSquare(m1Square4) ){
            yOut = -1;
            m2Square1 = new BoardPoint( m1Square4.getX() + 1, m1Square4.getY() + yOut );
            m2Square2 = new BoardPoint(m1Square4.getX() -1, m1Square4.getY() + yOut);

            if (isValidElephantPathSquare(m2Square1)){
                xOut = 1;
                m3Square1 = new BoardPoint(m2Square1.getX()+xOut,m2Square1.getY() + yOut );

                if (!m3Square1.getWasClipped() && m3Square1.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square1.getX(),m3Square1.getY()));
                }

                for (BoardPoint t : targetingSquares)  {
                    System.out.println(t.getX() + "," + t.getY());
                }
            }

            if (isValidElephantPathSquare(m2Square2)){
                xOut = -1;
                m3Square2 = new BoardPoint(m2Square2.getX()+xOut,m2Square2.getY() + yOut );

                if (!m3Square2.getWasClipped() && m3Square2.existsOnBoard()) {
                    targetingSquares.add(new BoardPoint(m3Square2.getX(),m3Square2.getY()));
                }
            }
        }

        for (BoardPoint t : targetingSquares)  {
            System.out.println(t.getX() + "," + t.getY());
        }


    }*/

    @Override
    public ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard)
    {
        ArrayList<BoardPoint> output = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Check for moves to the right
        //if(i < 7 && !board.containsPiece(i + 1, j))
        if(i < 6 && hypotheticalBoard[i+1][j] == null)
        {
            if(j > 1 && hypotheticalBoard[i+2][j-1] == null) // not too high
            {
                output.add(new BoardPoint(i + 3, j - 2));
            }
            if(j < 8 && hypotheticalBoard[i+2][j+1] == null) // not too low
            {
                output.add(new BoardPoint(i + 3, j + 2));
            }
        }
        // Check for moves to the left
        //if(i > 1 && !board.containsPiece(i - 1, j))
        if(i > 2 && hypotheticalBoard[i-1][j] == null)
        {
            if(j > 1 && hypotheticalBoard[i-2][j-1] == null) // not too high
            {
                output.add(new BoardPoint(i - 3, j - 2));
            }
            if(j < 8 && hypotheticalBoard[i-2][j+1] == null) // not too low
            {
                output.add(new BoardPoint(i - 3, j + 2));
            }
        }
        // Check for moves down
        //if(j < 8 && !board.containsPiece(i, j + 1))
        if(j < 7 && hypotheticalBoard[i][j + 1] == null)
        {
            if(i > 1 && hypotheticalBoard[i-1][j+2] == null) // not too far left
            {
                output.add(new BoardPoint(i - 2, j + 3));
            }
            if(i < 7 && hypotheticalBoard[i+1][j+2] == null) // not too far right
            {
                output.add(new BoardPoint(i + 2, j + 3));
            }
        }
        // Check for moves up
        //if(j > 1 && !board.containsPiece(i, j - 1))
        if(j > 2 && hypotheticalBoard[i][j - 1] == null)
        {
            if(i > 1 && hypotheticalBoard[i-1][j-2] == null) // not too far left
            {
                output.add(new BoardPoint(i - 2, j - 3));
            }
            if(i < 7 && hypotheticalBoard[i+1][j-2] == null) // not too far right
            {
                output.add(new BoardPoint(i + 2, j - 3));
            }
        }
        return output;
    }
}
