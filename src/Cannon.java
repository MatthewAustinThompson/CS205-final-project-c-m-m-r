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
    public void findTargetingSquares()
    {
        targetingSquares = new ArrayList<BoardPoint>();
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
                if(board.containsPiece(search, j) && board.getPieceAt(search, j).getPieceType() == PieceType.Cannon)
                {
                    search = 8;
                } else if (board.containsPiece(search, j))
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
                    if(board.containsPiece(index, j))
                    {
                        if(board.containsEnemy(board.getPieceAt(i, j), new BoardPoint(index, j)) &&
                                board.getPieceAt(index, j).getPieceType() != PieceType.Cannon)
                        {
                            targetingSquares.add(new BoardPoint(index, j));
                            index = 8;
                        } else
                        {
                            index = 8;
                        }
                    } else
                    {
                        targetingSquares.add(new BoardPoint(index, j));
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
                if(board.containsPiece(search, j) && board.getPieceAt(search, j).getPieceType() == PieceType.Cannon)
                {
                    search = 0;
                } else if (board.containsPiece(search, j))
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
                    if(board.containsPiece(index, j))
                    {
                        if(board.containsEnemy(board.getPieceAt(i, j), new BoardPoint(index, j)) &&
                                board.getPieceAt(index, j).getPieceType() != PieceType.Cannon)
                        {
                            targetingSquares.add(new BoardPoint(index, j));
                            index = 0;
                        } else
                        {
                            index = 0;
                        }
                    } else
                    {
                        targetingSquares.add(new BoardPoint(index, j));
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
                if(board.containsPiece(i, search) && board.getPieceAt(i, search).getPieceType() == PieceType.Cannon)
                {
                    search = 9;
                } else if (board.containsPiece(i, search))
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
                    if(board.containsPiece(i, index))
                    {
                        if(board.containsEnemy(board.getPieceAt(i, j), new BoardPoint(i, index)) &&
                                board.getPieceAt(i, index).getPieceType() != PieceType.Cannon)
                        {
                            targetingSquares.add(new BoardPoint(i, index));
                            index = 9;
                        } else
                        {
                            index = 9;
                        }
                    } else
                    {
                        targetingSquares.add(new BoardPoint(i, index));
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
                if(board.containsPiece(i, search) && board.getPieceAt(i, search).getPieceType() == PieceType.Cannon)
                {
                    search = 0;
                } else if (board.containsPiece(i, search))
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
                    if(board.containsPiece(i, index))
                    {
                        if(board.containsEnemy(board.getPieceAt(i, j), new BoardPoint(i, index)) &&
                                board.getPieceAt(i, index).getPieceType() != PieceType.Cannon)
                        {
                            targetingSquares.add(new BoardPoint(i, index));
                            index = 0;
                        } else
                        {
                            index = 0;
                        }
                    } else
                    {
                        targetingSquares.add(new BoardPoint(i, index));
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
            if((board.containsEnemy(board.getPieceAt(3, 9), new BoardPoint(5, 7)) &&
                    board.getPieceAt(5, 7).getPieceType() != PieceType.Cannon) || !board.containsPiece(5, 7))
            {
                targetingSquares.add(new BoardPoint(5, 7));
            }
        }
        // Cannon in bottom right
        if(i == 5 && j == 9 && bluePalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(5, 9), new BoardPoint(3, 7)) &&
                    board.getPieceAt(3, 7).getPieceType() != PieceType.Cannon) || !board.containsPiece(3, 7))
            {
                targetingSquares.add(new BoardPoint(3, 7));
            }
        }
        // Cannon in top left
        if(i == 3 && j == 7 && bluePalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(3, 7), new BoardPoint(5, 9)) &&
                    board.getPieceAt(5, 9).getPieceType() != PieceType.Cannon) || !board.containsPiece(5, 9))
            {
                targetingSquares.add(new BoardPoint(5, 9));
            }
        }
        // Cannon in top right
        if(i == 5 && j == 7 && bluePalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(5, 7), new BoardPoint(5, 7)) &&
                    board.getPieceAt(3, 9).getPieceType() != PieceType.Cannon) || !board.containsPiece(3, 9))
            {
                targetingSquares.add(new BoardPoint(3, 9));
            }
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
            if((board.containsEnemy(board.getPieceAt(3, 0), new BoardPoint(5, 2)) &&
                    board.getPieceAt(5, 2).getPieceType() != PieceType.Cannon) || !board.containsPiece(5, 2))
            {
                targetingSquares.add(new BoardPoint(5, 2));
            }
        }
        // Cannon in top right
        if(i == 5 && j == 0 && redPalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(5, 0), new BoardPoint(3, 2)) &&
                    board.getPieceAt(3, 2).getPieceType() != PieceType.Cannon) || !board.containsPiece(3, 2))
            {
                targetingSquares.add(new BoardPoint(3, 2));
            }
        }
        // Cannon in bottom left
        if(i == 3 && j == 2 && redPalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(3, 2), new BoardPoint(5, 0)) &&
                    board.getPieceAt(5, 0).getPieceType() != PieceType.Cannon) || !board.containsPiece(5, 0))
            {
                targetingSquares.add(new BoardPoint(5, 0));
            }
        }
        // Cannon in bottom right
        if(i == 5 && j == 2 && redPalaceScreen)
        {
            if((board.containsEnemy(board.getPieceAt(5, 2), new BoardPoint(3, 0)) &&
                    board.getPieceAt(3, 0).getPieceType() != PieceType.Cannon) || !board.containsPiece(3, 0))
            {
                targetingSquares.add(new BoardPoint(3, 0));
            }
        }
    }
}
