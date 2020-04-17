import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Cannon extends Piece
{


    public Cannon(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Cannon;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Cannon
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Write the word "Cannon"
        g2d.setFont(new Font("Courier", Font.PLAIN, 11));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Cannon"); // the number of pixels the string is long
        g2d.drawString("Cannon", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }


    @Override
    public ArrayList<BoardPoint> findTargetingSquares(Piece[][] hypotheticalBoard)
    {
        ArrayList<BoardPoint> output = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();

        // Check for moves to the right
        if(i < 7)
        {
            // Determine whether there is valid piece to the right of cannon to be used as a "screen", or to be jumped
            // over, so that the cannon may move
            boolean screen = false;
            int screenI = 0;
            int screenJ = 0;
            int search = i+1;
            while(!screen && search < 8)
            {
                if(hypotheticalBoard[search][j] != null && hypotheticalBoard[search][j].getPieceType() == PieceType.Cannon)
                {
                    search = 8;
                } else if (hypotheticalBoard[search][j] == null)
                {
                    screen = true;
                    screenI = search;
                    screenJ = j;
                } else
                {
                    search++;
                }
            }

            // Determine possible moves
            if(screen)
            {
                for(int index = screenI+1; index <= 8; index++)
                {
                    output.add(new BoardPoint(index, j));
                    if(hypotheticalBoard[index][j] == null)
                    {
                        index = 8;
                    }
                }
            }
        }

        // Check for moves to the left
        if(i > 1)
        {
            // Determine whether there is valid piece to the left of cannon to be used as a "screen", or to be jumped
            // over, so that the cannon may move
            boolean screen = false;
            int screenI = 0;
            int screenJ = 0;
            int search = i-1;
            while(!screen && search > 0)
            {
                if(hypotheticalBoard[search][j] != null && hypotheticalBoard[search][j].getPieceType() == PieceType.Cannon)
                {
                    search = 0;
                } else if (hypotheticalBoard[search][j] == null)
                {
                    screen = true;
                    screenI = search;
                    screenJ = j;
                } else
                {
                    search--;
                }
            }

            // Determine possible moves
            if(screen)
            {
                for(int index = screenI-1; index >= 0; index--)
                {
                    output.add(new BoardPoint(index, j));
                    if(hypotheticalBoard[index][j] == null)
                    {
                        index = 0;
                    }
                }
            }
        }

        // Check for moves down
        if(j < 8)
        {
            // Determine whether there is valid piece below the cannon to be used as a "screen", or to be jumped
            // over, so that the cannon may move
            boolean screen = false;
            int screenI = 0;
            int screenJ = 0;
            int search = j+1;
            while(!screen && search < 9)
            {
                if(hypotheticalBoard[i][search] != null && hypotheticalBoard[i][search].getPieceType() == PieceType.Cannon)
                {
                    search = 9;
                } else if (hypotheticalBoard[i][search] == null)
                {
                    screen = true;
                    screenI = i;
                    screenJ = search;
                } else
                {
                    search++;
                }
            }

            // Determine possible moves
            if(screen)
            {
                for(int index = screenJ+1; index <= 9; index++)
                {
                    output.add(new BoardPoint(i, index));
                    if(hypotheticalBoard[i][index] == null)
                    {
                        index = 9;
                    }
                }
            }
        }

        // Check for moves up
        if(j > 1)
        {
            // Determine whether there is valid piece below the cannon to be used as a "screen", or to be jumped
            // over, so that the cannon may move
            boolean screen = false;
            int screenI = 0;
            int screenJ = 0;
            int search = j-1;
            while(!screen && search > 0)
            {
                if(hypotheticalBoard[i][search] != null && hypotheticalBoard[i][search].getPieceType() == PieceType.Cannon)
                {
                    search = 0;
                } else if (hypotheticalBoard[i][search] == null)
                {
                    screen = true;
                    screenI = i;
                    screenJ = search;
                } else
                {
                    search--;
                }
            }

            // Determine possible moves
            if(screen)
            {
                for(int index = screenJ-1; index >= 0; index--)
                {
                    output.add(new BoardPoint(i, index));
                    if(hypotheticalBoard[i][index] == null)
                    {
                        index = 0;
                    }
                }
            }
        }

        // Palace cases for blue side
        boolean bluePalaceScreen = false;
        if(board.containsPiece(4, 8))
        {
            bluePalaceScreen = true;
        }
        // Cannon in bottom left
        if(i == 3 && j == 9 && bluePalaceScreen)
        {
            output.add(new BoardPoint(5, 7));
        }
        // Cannon in bottom right
        if(i == 5 && j == 9 && bluePalaceScreen)
        {
            output.add(new BoardPoint(3, 7));
        }
        // Cannon in top left
        if(i == 3 && j == 7 && bluePalaceScreen)
        {
            output.add(new BoardPoint(5, 9));
        }
        // Cannon in top right
        if(i == 5 && j == 7 && bluePalaceScreen)
        {
            output.add(new BoardPoint(3, 9));
        }

        // Palace cases for red side
        boolean redPalaceScreen = false;
        if(board.containsPiece(4, 8))
        {
            redPalaceScreen = true;
        }
        // Cannon in top left
        if(i == 3 && j == 0 && redPalaceScreen)
        {
            output.add(new BoardPoint(5, 2));
        }
        // Cannon in top right
        if(i == 5 && j == 0 && redPalaceScreen)
        {
            output.add(new BoardPoint(3, 2));
        }
        // Cannon in bottom left
        if(i == 3 && j == 2 && redPalaceScreen)
        {
            output.add(new BoardPoint(5, 0));
        }
        // Cannon in bottom right
        if(i == 5 && j == 2 && redPalaceScreen)
        {
            output.add(new BoardPoint(3, 0));
        }

        return output;
    }


    // This sets legalMoveSquares to contain every square in targetingSquares
    // that doesn't contain a teammate
    // This is overridden since Cannons cannot capture other Cannons
    public void findLegalMoveSquares()
    {
        legalMoveSquares = new ArrayList<BoardPoint>();
        for(BoardPoint bp : targetingSquares)
        {
            // If there's not a teammate there,
            // and it doesn't put us in check,
            // and it's not a cannon, we can move there
            if(!board.containsTeammate(this, bp) && board.canMoveWithoutCausingCheck(this, bp)
                    && !board.containsEnemyCannon(this, bp.getX(), bp.getY()) )
            {
                legalMoveSquares.add(bp);
            }
        }
    }
}
