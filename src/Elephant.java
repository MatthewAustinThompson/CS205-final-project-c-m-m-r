import java.awt.*;
import java.awt.geom.Path2D;
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

    final int outwardsRelativeToComputer = 1;
    final int outwardsRelativeToPlayer = -1;
    int outwardsRelativeToTeam;

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

        // Write the word "Elephant"
        g2d.setFont(new Font("Courier", Font.PLAIN, 9));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Elephant"); // the number of pixels the string is long
        g2d.drawString("Elephant", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }


    //FXN isValidElephantPathSquare checks that the path square: Exists on board && Contains no enemy OR ally piece
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
    //Move2: diagonal 1 square (+-X by 1, away from team side by 1)
    //Move3: diagonal 1 square (+-X by 1 Same as its Move 2, away from team side by 1)
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();

        int i = location.getX();
        int j = location.getY();

        if(getTeam() == Team.Player) {outwardsRelativeToTeam = outwardsRelativeToPlayer;}
        if(getTeam() == Team.Computer) {outwardsRelativeToTeam = outwardsRelativeToComputer;}

        // x-------------->
        //|0==============x8| y
        //| COMPUTER SIDE   | |
        //|                 | |
        //|                 | |
        //|  PLAYER SIDE    | |
        //|y9===============| V


        //Calculate Move 1 board points
        m1Square1 = new BoardPoint(i+1,j);
        m1Square2 = new BoardPoint(i-1,j);
        m1Square3 = new BoardPoint(i,j+1);
        m1Square4 = new BoardPoint(i,j-1);

        //Clear each time recalculation occurs, add new recalc
        move1ArrayList.clear();
        move1ArrayList.add(m1Square1);
        move1ArrayList.add(m1Square2);
        move1ArrayList.add(m1Square3);
        move1ArrayList.add(m1Square4);
        //============================
        //For each m1 in move1arrayList: check isValidElephantPathSquare(m1)
        //============================
        for (BoardPoint m1Square: move1ArrayList) {
            //============================T
            System.out.print("looking at m1square: ");
            System.out.println(m1Square.getX() + "," + m1Square.getY());
            //============================T

            //If m1 is empty and on board, calculate m2: a move2 from that m1
            if (isValidElephantPathSquare(m1Square)){
                //============================T
                System.out.println("square valid +++");
                //============================T

                //Calculate m2 using m1 coords (+-1 , out 1)
                m2Square1 = new BoardPoint( m1Square.getX() + 1, m1Square.getY() + outwardsRelativeToTeam );
                m2Square2 = new BoardPoint( m1Square.getX() -1 , m1Square.getY() + outwardsRelativeToTeam );

                //if m2's are valid, calculate the m3 based on that m2
                //each m3 must move in the same x direction as the m2 it was calculated from
                //============================T
                System.out.print("looking at m2square1: ");
                System.out.println(m2Square1.getX() + "," + m2Square1.getY());
                //============================T
                if (isValidElephantPathSquare(m2Square1)) {
                    //============================T
                    System.out.println("square valid +++");
                    //============================T
                    //m2Square1 moved in + 1 in X direction
                    m3Square1 = new BoardPoint( m2Square1.getX() + 1, m2Square1.getY() + outwardsRelativeToTeam );

                    //If m3Square exists on board add it to targeting squares
                    //============================T
                    System.out.print("looking at m3square1: ");
                    System.out.println(m3Square1.getX() + "," + m3Square1.getY());
                    //============================T
                    if ( m3Square1.existsOnBoard() && !m3Square1.getWasClipped() ){
                        targetingSquares.add(m3Square1);
                        //============================T
                        System.out.println("square valid +++");
                        //============================T
                    }

                }

                //============================T
                System.out.print("looking at m2square2: ");
                System.out.println(m2Square2.getX() + "," + m2Square2.getY());
                //============================T
                if (isValidElephantPathSquare(m2Square2)) {
                    //============================T
                    System.out.println("square valid +++");
                    //============================T

                    //m2Square2 moved - 1 in X direction
                    m3Square2 = new BoardPoint(m2Square2.getX() - 1, m2Square2.getY() + outwardsRelativeToTeam);

                    //If m3Square exists on board add it to targeting squares
                    //============================T
                    System.out.print("looking at m3square2: ");
                    System.out.println(m3Square2.getX() + "," + m3Square2.getY());
                    //============================T
                    if ( m3Square2.existsOnBoard() && !m3Square2.getWasClipped() ){
                        targetingSquares.add(m3Square2);
                        //============================T
                        System.out.println("square valid +++");
                        //============================T
                    }

                }

            }
        }

    }
}
