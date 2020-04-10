import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Guard extends Piece
{


    public Guard(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Solider;
        this.initializeOctagon();
    }

    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Guard
    //
    // =============================================

    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Write the word "Soldier"
        g2d.setFont(new Font("Courier", Font.PLAIN, 14));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Guard"); // the number of pixels the string is long
        g2d.drawString("Guard", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }



    @Override
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();
        // Player team logic
        if(team == Team.Player){
            // Check for moves to the right
            if(i < 5 && (!board.containsPiece(i + 1, j) || board.getPieceAt(i + 1, j).getTeam() != team))
            {
                if(j > 6) // not too high
                {
                    targetingSquares.add(new BoardPoint(i + 1, j));
                    if(!board.containsPiece(i+1, j-1) && location.equals(new BoardPoint(4,8))){
                        targetingSquares.add(new BoardPoint(i + 1, j-1));
                    }
                }
                if(j < 9) // not too low
                {
                    targetingSquares.add(new BoardPoint(i + 1, j));
                    if(!board.containsPiece(i+1, j+1) && location.equals(new BoardPoint(4,8))){
                        targetingSquares.add(new BoardPoint(i + 1, j+1));
                    }
                }
            }
            // Check for moves to the left
            if(i > 3 && (!board.containsPiece(i - 1, j) || board.getPieceAt(i - 1, j).getTeam() != team))
            {
                if(j > 6) // not too high
                {
                    targetingSquares.add(new BoardPoint(i - 1, j));
                    if(!board.containsPiece(i-1, j-1) && location.equals(new BoardPoint(4,8))){
                        targetingSquares.add(new BoardPoint(i - 1, j-1));
                    }
                }
                if(j < 9) // not too low
                {
                    targetingSquares.add(new BoardPoint(i - 1, j));
                    if(!board.containsPiece(i-1, j+1) && location.equals(new BoardPoint(4,8))){
                        targetingSquares.add(new BoardPoint(i - 1, j+1));
                    }
                }
            }
            // Check for moves down
            if(j < 9 && (!board.containsPiece(i, j + 1) || board.getPieceAt(i , j + 1).getTeam() != team))
            {
                if(i > 6) // not too far left
                {
                    targetingSquares.add(new BoardPoint(i, j + 1));
                }
                if(i < 9) // not too far right
                {
                    targetingSquares.add(new BoardPoint(i, j + 1));
                }
            }
            // Check for moves up
            if(j > 7 && (!board.containsPiece(i, j - 1) || board.getPieceAt(i, j - 1).getTeam() != team))
            {
                if(i > 2) // not too far left
                {
                    targetingSquares.add(new BoardPoint(i, j - 1));
                }
                if(i < 6) // not too far right
                {
                    targetingSquares.add(new BoardPoint(i, j - 1));
                }
            }
            // check for center spot in the "castle"
            if(!board.containsPiece(4, 8) || board.getPieceAt(4, 8).getTeam() != team){
                targetingSquares.add(new BoardPoint(4, 8));
            }

        } else if(team == Team.Computer){

            // Check for moves to the right
            if(i < 5 && (!board.containsPiece(i + 1, j) || board.getPieceAt(i + 1, j).getTeam() != team))
            {
                if(j > 0) // not too high
                {
                    targetingSquares.add(new BoardPoint(i + 1, j));
                    if(!board.containsPiece(i+1, j-1) && location.equals(new BoardPoint(4,1))){
                        targetingSquares.add(new BoardPoint(i + 1, j-1));
                    }
                }
                if(j < 2) // not too low
                {
                    targetingSquares.add(new BoardPoint(i + 1, j));
                    if(!board.containsPiece(i+1, j+1) && location.equals(new BoardPoint(4,1))){
                        targetingSquares.add(new BoardPoint(i + 1, j+1));
                    }
                }
            }
            // Check for moves to the left
            if(i > 3 && (!board.containsPiece(i - 1, j) || board.getPieceAt(i - 1, j).getTeam() != team))
            {
                if(j > 0) // not too high
                {
                    targetingSquares.add(new BoardPoint(i - 1, j));
                    if(!board.containsPiece(i-1, j-1) && location.equals(new BoardPoint(4,1))){
                        targetingSquares.add(new BoardPoint(i - 1, j-1));
                    }
                }
                if(j < 2) // not too low
                {
                    targetingSquares.add(new BoardPoint(i - 1, j));
                    if(!board.containsPiece(i-1, j+1) && location.equals(new BoardPoint(4,1))){
                        targetingSquares.add(new BoardPoint(i - 1, j+1));
                    }
                }
            }
            // Check for moves down
            if(j < 2 && (!board.containsPiece(i, j + 1) || board.getPieceAt(i, j + 1).getTeam() != team))
            {
                if(i > 3) // not too far left
                {
                    targetingSquares.add(new BoardPoint(i, j + 1));
                }
                if(i < 5) // not too far right
                {
                    targetingSquares.add(new BoardPoint(i, j + 1));
                }
            }
            // Check for moves up
            if(j > 0 && (!board.containsPiece(i, j - 1) || board.getPieceAt(i, j - 1).getTeam() != team))
            {
                if(i > 3) // not too far left
                {
                    targetingSquares.add(new BoardPoint(i, j - 1));
                }
                if(i < 5) // not too far right
                {
                    targetingSquares.add(new BoardPoint(i, j - 1));
                }
            }
            // check for center spot in the "castle"
            if(!board.containsPiece(4, 1) || board.getPieceAt(4, 1).getTeam() != team){
                targetingSquares.add(new BoardPoint(4, 1));
            }
        }

    }
}
