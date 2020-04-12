import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Chariot extends Piece
{


    public Chariot(GameManager inputManager, Point inputCenter, BoardPoint inputLocation, Team inputTeam)
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
        pieceType = PieceType.Chariot;
        this.initializeOctagon();
    }


    @Override
    public void tick()
    {

    }


    // =============================================
    //
    //              Drawing the Chariot
    //
    // =============================================


    @Override
    public void render(Graphics2D g2d)
    {
        // Draw the octagon
        g2d.setColor(fillColor);
        g2d.fill(outline);

        // Write the word "Chariot"
        g2d.setFont(new Font("Courier", Font.PLAIN, 10));
        g2d.setColor(symbolColor);
        int pixelLength = g2d.getFontMetrics().stringWidth("Chariot"); // the number of pixels the string is long
        g2d.drawString("Chariot", (int)this.center.x - pixelLength/2, (int)center.y + 5);
    }


    @Override
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
        int i = location.getX();
        int j = location.getY();

        // Check for moves to the right
        if(i < 8)
        {
            for(int index = i+1; index <= 8; index++)
            {
                if(board.containsEnemy(board.getPieceAt(i,j), new BoardPoint(index,j)))
                {
                    targetingSquares.add(new BoardPoint(index, j));
                    index = 8;
                } else if(board.containsTeammate(board.getPieceAt(i,j), new BoardPoint(index,j)))
                {
                    index = 8;
                } else
                {
                    targetingSquares.add(new BoardPoint(index, j));
                }
            }
        }

        // Check for moves to the left
        if(i > 0)
        {
            for(int index = i-1; index >= 0; index--)
            {
                if(board.containsEnemy(board.getPieceAt(i,j), new BoardPoint(index,j)))
                {
                    targetingSquares.add(new BoardPoint(index, j));
                    index = 0;
                } else if(board.containsTeammate(board.getPieceAt(i,j), new BoardPoint(index,j)))
                {
                    index = 0;
                } else
                {
                    targetingSquares.add(new BoardPoint(index, j));
                }
            }
        }

        // Check for moves down
        if(j < 9)
        {
            for(int index = j+1; index <= 9; index++)
            {
                if(board.containsEnemy(board.getPieceAt(i,j), new BoardPoint(i,index)))
                {
                    targetingSquares.add(new BoardPoint(i, index));
                    index = 9;
                } else if(board.containsTeammate(board.getPieceAt(i,j), new BoardPoint(i,index)))
                {
                    index = 9;
                } else
                {
                    targetingSquares.add(new BoardPoint(i, index));
                }
            }
        }

        // Check for moves up
        if(j > 0)
        {
            for(int index = j-1; index >= 0; index--)
            {
                if(board.containsEnemy(board.getPieceAt(i,j), new BoardPoint(i,index)))
                {
                    targetingSquares.add(new BoardPoint(i, index));
                    index = 0;
                } else if(board.containsTeammate(board.getPieceAt(i,j), new BoardPoint(i,index)))
                {
                    index = 0;
                } else
                {
                    targetingSquares.add(new BoardPoint(i, index));
                }
            }
        }

        // Palace cases for blue side
        // Chariot in bottom left
        if(i == 3 && j == 9)
        {
            if(board.containsPiece(4, 8))
            {
                if(board.containsEnemy(board.getPieceAt(3,9), new BoardPoint(4,8)))
                {
                    targetingSquares.add(new BoardPoint(4, 8));
                }
            } else if(board.containsTeammate(board.getPieceAt(3,9), new BoardPoint(5,7)))
            {
                targetingSquares.add(new BoardPoint(4, 8));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 8));
                targetingSquares.add(new BoardPoint(5, 7));
            }
        }
        // Chariot in bottom right
        if(i == 5 && j == 9)
        {
            if(board.containsPiece(4, 8))
            {
                if (board.containsEnemy(board.getPieceAt(5, 9), new BoardPoint(4, 8))) {
                    targetingSquares.add(new BoardPoint(4, 8));
                }
            } else if(board.containsTeammate(board.getPieceAt(5,9), new BoardPoint(3,7)))
            {
                targetingSquares.add(new BoardPoint(4, 8));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 8));
                targetingSquares.add(new BoardPoint(3, 7));
            }
        }
        // Chariot in top left
        if(i == 3 && j == 7)
        {
            if(board.containsPiece(4, 8))
            {
                if (board.containsEnemy(board.getPieceAt(3, 7), new BoardPoint(4, 8))) {
                    targetingSquares.add(new BoardPoint(4, 8));
                }
            } else if(board.containsTeammate(board.getPieceAt(3,7), new BoardPoint(5,9)))
            {
                targetingSquares.add(new BoardPoint(4, 8));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 8));
                targetingSquares.add(new BoardPoint(5, 9));
            }
        }
        // Chariot in top right
        if(i == 5 && j == 7)
        {
            if(board.containsPiece(4, 8))
            {
                if (board.containsEnemy(board.getPieceAt(5, 7), new BoardPoint(4, 8))) {
                    targetingSquares.add(new BoardPoint(4, 8));
                }
            } else if(board.containsTeammate(board.getPieceAt(5,7), new BoardPoint(3,9)))
            {
                targetingSquares.add(new BoardPoint(4, 8));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 8));
                targetingSquares.add(new BoardPoint(3, 9));
            }
        }
        // Chariot in center
        if(i == 4 && j == 8)
        {
            if(!board.containsPiece(3, 9) || board.containsEnemy(board.getPieceAt(4, 8), new BoardPoint(3, 9)))
            {
                targetingSquares.add(new BoardPoint(3, 9));
            }
            if(!board.containsPiece(5, 9) || board.containsEnemy(board.getPieceAt(4, 8), new BoardPoint(5, 9)))
            {
                targetingSquares.add(new BoardPoint(5, 9));
            }
            if(!board.containsPiece(3, 7) || board.containsEnemy(board.getPieceAt(4, 8), new BoardPoint(3, 7)))
            {
                targetingSquares.add(new BoardPoint(3, 7));
            }
            if(!board.containsPiece(5, 7) || board.containsEnemy(board.getPieceAt(4, 8), new BoardPoint(5, 7)))
            {
                targetingSquares.add(new BoardPoint(5, 7));
            }
        }

        // Palace cases for red side
        // Chariot in top left
        if(i == 3 && j == 0)
        {
            if(board.containsPiece(4, 1))
            {
                if(board.containsEnemy(board.getPieceAt(3,0), new BoardPoint(4,1)))
                {
                    targetingSquares.add(new BoardPoint(4, 1));
                }
            } else if(board.containsTeammate(board.getPieceAt(3,0), new BoardPoint(5,2)))
            {
                targetingSquares.add(new BoardPoint(4, 1));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 1));
                targetingSquares.add(new BoardPoint(5, 2));
            }
        }
        // Chariot in top right
        if(i == 5 && j == 0)
        {
            if(board.containsPiece(4, 1))
            {
                if (board.containsEnemy(board.getPieceAt(5, 0), new BoardPoint(4, 1))) {
                    targetingSquares.add(new BoardPoint(4, 1));
                }
            } else if(board.containsTeammate(board.getPieceAt(5,0), new BoardPoint(3,2)))
            {
                targetingSquares.add(new BoardPoint(4, 1));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 1));
                targetingSquares.add(new BoardPoint(3, 2));
            }
        }
        // Chariot in bottom left
        if(i == 3 && j == 2)
        {
            if(board.containsPiece(4, 1))
            {
                if (board.containsEnemy(board.getPieceAt(3, 2), new BoardPoint(4, 1))) {
                    targetingSquares.add(new BoardPoint(4, 1));
                }
            } else if(board.containsTeammate(board.getPieceAt(3,2), new BoardPoint(5,0)))
            {
                targetingSquares.add(new BoardPoint(4, 1));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 1));
                targetingSquares.add(new BoardPoint(5, 0));
            }
        }
        // Chariot in bottom right
        if(i == 5 && j == 2)
        {
            if(board.containsPiece(4, 1))
            {
                if (board.containsEnemy(board.getPieceAt(5, 2), new BoardPoint(4, 1))) {
                    targetingSquares.add(new BoardPoint(4, 1));
                }
            } else if(board.containsTeammate(board.getPieceAt(5,2), new BoardPoint(3,0)))
            {
                targetingSquares.add(new BoardPoint(4, 1));
            } else
            {
                targetingSquares.add(new BoardPoint(4, 1));
                targetingSquares.add(new BoardPoint(3, 0));
            }
        }
        // Chariot in center
        if(i == 4 && j == 1)
        {
            if(!board.containsPiece(3, 0) || board.containsEnemy(board.getPieceAt(4, 1), new BoardPoint(3, 0)))
            {
                targetingSquares.add(new BoardPoint(3, 0));
            }
            if(!board.containsPiece(5, 0) || board.containsEnemy(board.getPieceAt(4, 1), new BoardPoint(5, 0)))
            {
                targetingSquares.add(new BoardPoint(5, 0));
            }
            if(!board.containsPiece(3, 2) || board.containsEnemy(board.getPieceAt(4, 1), new BoardPoint(3, 2)))
            {
                targetingSquares.add(new BoardPoint(3, 2));
            }
            if(!board.containsPiece(5, 2) || board.containsEnemy(board.getPieceAt(4, 1), new BoardPoint(5, 2)))
            {
                targetingSquares.add(new BoardPoint(5, 2));
            }
        }
    }
}
