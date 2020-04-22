import java.awt.*;
import java.util.ArrayList;

public class GameManager
{
    // The dimensions of the part of the window that the
    // GameManager can draw on
    private int width, height;

    private ArrayList<Piece> pieces;

    private StartScreen startScreen;
    private Board board;


    // to determine rendering between the start screen and board
    private boolean playing;
    // When the user has clicked to play
    private boolean needToStartGame;

    // When the player clicks on a Piece
    private Piece selectedPiece;

    // If a Piece has been captured
    private Piece toBeRemoved;
    // If all Pieces need to update their moves
    private boolean needsToUpdate;

    // Detecting when the game ends
    private boolean playerCanMove;
    private boolean playerIsInCheck;
    private boolean computerCanMove;
    private boolean computerIsInCheck;

    //======TURNS===================
    private TurnDisplaySign turnDisplaySign;
    private MessageBoard messageBoard;
    private PassButton passButton;
    private ArrayList<String> messagesToAdd;  // store messages to add to the message board

    private boolean testingWithoutTurns = false; //SWITCH TO TRUE TO TEST PIECE MOVEMENT FREEELY
    private final int playerTurnMarker = 1;
    private final int computerTurnMarker = -1;
    private int turnMarker = playerTurnMarker;



    public GameManager(int inputWidth, int inputHeight)
    {
        width = inputWidth;
        height = inputHeight;

        playing = false;
        needToStartGame = false;
        startScreen = new StartScreen(this);
    }

    public void tick()
    {
        if(needToStartGame)
        {
            this.startGame();
            needToStartGame = false;
        }
        // Have each Piece update each frame, if applicable
        if(playing)
        {
            for(Piece p : pieces)
            {
                p.tick();
            }
            if(toBeRemoved != null)
            {
                pieces.remove(toBeRemoved);
                toBeRemoved = null;
            }
            if(needsToUpdate)
            {
                updatePieces();
                needsToUpdate = false;
                updateComputerCanMove();
                updateComputerIsInCheck();
                updatePlayerCanMove();
                updatePlayerIsInCheck();
                isGameOver();
                for(String message : messagesToAdd)
                {
                    messageBoard.addMessageToMessageBoard(message);
                }
                messagesToAdd = new ArrayList<String>();
            }
        }

    }

    public void render(Graphics2D g2d)
    {
        if(!playing || needToStartGame)
        {
            startScreen.render(g2d);
        }
        else
        {
            //===============================================================
            // PUT DISPLAY STUFF HERE
            //===============================================================
            board.render(g2d);
            turnDisplaySign.render(g2d);
            messageBoard.render(g2d);
            passButton.render(g2d);


            // Have each Piece draw itself
            for(Piece p : pieces)
            {
                p.render(g2d);
            }
            board.drawHighlights(g2d);
            if(selectedPiece != null)
            {
                selectedPiece.drawHighlight(g2d);
            }
        }
    }


    // Getters
    public double getWidth()
    {
        return width;
    }
    public double getHeight()
    {
        return height;
    }
    public Board getBoard()
    {
        return board;
    }
    public boolean getPlaying()
    {
        return playing;
    }
    public boolean isPlayersTurn()
    {
        return turnMarker == playerTurnMarker;
    }

    // Setters
    public void setWidth(int inputWidth)
    {
        width = inputWidth;
    }
    public void setHeight(int inputHeight)
    {
        height = inputHeight;
    }

    // ================================
    //
    //         Managing Pieces
    //
    // ================================

    // This resets the board to its starting position and initializes variables to
    // play the game
    public void startGame()
    {
        pieces = new ArrayList<Piece>();

        selectedPiece = null;
        toBeRemoved = null;
        needsToUpdate = false;

        playerCanMove = true;
        playerIsInCheck = false;
        computerCanMove = true;
        computerIsInCheck = false;

        turnDisplaySign = new TurnDisplaySign(this);
        messageBoard = new MessageBoard(this, turnDisplaySign);
        messagesToAdd = new ArrayList<String>();
        passButton = new PassButton(this,messageBoard);

        board = new Board(this);

        this.addPiece(PieceType.Horse, Team.Player, new BoardPoint(1,9));
        this.addPiece(PieceType.Horse, Team.Player, new BoardPoint(7,9));
        this.addPiece(PieceType.Horse, Team.Computer, new BoardPoint(1,0));
        this.addPiece(PieceType.Horse, Team.Computer, new BoardPoint(7,0));

        this.addPiece(PieceType.Soldier, Team.Player, new BoardPoint(0,6));
        this.addPiece(PieceType.Soldier, Team.Player, new BoardPoint(2,6));
        this.addPiece(PieceType.Soldier, Team.Player, new BoardPoint(4,6));
        this.addPiece(PieceType.Soldier, Team.Player, new BoardPoint(6,6));
        this.addPiece(PieceType.Soldier, Team.Player, new BoardPoint(8,6));
        this.addPiece(PieceType.Soldier, Team.Computer, new BoardPoint(0,3));
        this.addPiece(PieceType.Soldier, Team.Computer, new BoardPoint(2,3));
        this.addPiece(PieceType.Soldier, Team.Computer, new BoardPoint(4,3));
        this.addPiece(PieceType.Soldier, Team.Computer, new BoardPoint(6,3));
        this.addPiece(PieceType.Soldier, Team.Computer, new BoardPoint(8,3));

        this.addPiece(PieceType.Guard, Team.Player, new BoardPoint(3,9));
        this.addPiece(PieceType.Guard, Team.Player, new BoardPoint(5,9));
        this.addPiece(PieceType.Guard, Team.Computer, new BoardPoint(3,0));
        this.addPiece(PieceType.Guard, Team.Computer, new BoardPoint(5,0));

        this.addPiece(PieceType.General, Team.Player, new BoardPoint(4,8));
        this.addPiece(PieceType.General, Team.Computer, new BoardPoint(4,1));

        this.addPiece(PieceType.Elephant, Team.Player, new BoardPoint(2,9));
        this.addPiece(PieceType.Elephant, Team.Player, new BoardPoint(6,9));
        this.addPiece(PieceType.Elephant, Team.Computer, new BoardPoint(2,0));
        this.addPiece(PieceType.Elephant, Team.Computer, new BoardPoint(6,0));

        this.addPiece(PieceType.Chariot, Team.Player, new BoardPoint(0,9));
        this.addPiece(PieceType.Chariot, Team.Player, new BoardPoint(8,9));
        this.addPiece(PieceType.Chariot, Team.Computer, new BoardPoint(0,0));
        this.addPiece(PieceType.Chariot, Team.Computer, new BoardPoint(8,0));

        this.addPiece(PieceType.Cannon, Team.Player, new BoardPoint(1,7));
        this.addPiece(PieceType.Cannon, Team.Player, new BoardPoint(7,7));
        this.addPiece(PieceType.Cannon, Team.Computer, new BoardPoint(1,2));
        this.addPiece(PieceType.Cannon, Team.Computer, new BoardPoint(7,2));

        this.updatePieces();
    }

    public void addPiece(PieceType pieceType, Team team, BoardPoint location)
    {
        if(board.containsPiece(location))
        {
            System.out.println("Error: cannot insert a Piece in non-empty space.");
            return;
        }
        Piece p;
        Point center = board.boardPointToPoint(location);
        switch(pieceType)
        {
            case Guard:
                p = new Guard(this, center, location, team);
                break;
            case Horse:
                p = new Horse(this, center, location, team);
                break;
            case Soldier:
                p = new Soldier(this, center, location, team);
                break;
            case General:
                p = new General(this, center, location, team);
                break;
            case Elephant:
                p = new Elephant(this, center, location, team);
                break;
            case Chariot:
                p = new Chariot(this, center, location, team);
                break;
            case Cannon:
                p = new Cannon(this, center, location, team);
                break;
            default:
                return;
        }
        pieces.add(p);
        board.getSpaces()[location.getX()][location.getY()] = p;
    }
    public void updatePieces()
    {
        for(Piece p : pieces)
        {
            p.update();
        }
    }


    // ================================
    //
    //           User Input
    //
    // ================================
    public void reactToClick(int mx, int my)
    {
        if(!playing)
        {
            reactToClickStart(mx, my);
        }
        else if(turnMarker == playerTurnMarker)
        {
            reactToClickPlayer(mx, my);
        }
        else if(turnMarker == computerTurnMarker)
        {
            reactToClickComputer(mx, my);
        }
        /*else
        {
            //If the pass button was clicked, switch turns
            if (passButton.containsClick(mx, my)){

                //Switch teams & let user know
                turnMarker = (-turnMarker);

                if (!testingWithoutTurns) {
                    //System.out.println("It is now Team " + convertTeamMarkerToTeam(turnMarker) + "'s turn.");
                    messagesToAdd.add(convertTeamMarkerToTeam(-turnMarker) + " has passed their turn.");
                    messagesToAdd.add("It is now Team " + convertTeamMarkerToTeam(turnMarker) + "'s turn.");
                }

            }

            // If a Piece is selected, either move it or unselect it
            if(selectedPiece != null)
            {
                BoardPoint bp = board.clickIsOnLegalMove(mx, my, selectedPiece);

                //Can't move piece unless it is that team's turn but can select and unselect to see valid moves
                if(bp != null  &&
                        ( ( convertTeamMarkerToTeam(turnMarker) == selectedPiece.getTeam() ) ||
                                testingWithoutTurns) )
                {
                    // If we are capturing, remove the piece getting captured
                    if(board.containsPiece(bp))
                    {
                        toBeRemoved = board.getPieceAt(bp);
                    }
                    // Now do the move
                    board.move(selectedPiece, bp);

                    //If moved, switch teams & let user know
                    turnMarker = (-turnMarker);

                    if(testingWithoutTurns) {
                        messagesToAdd.add("You have switched the turn enforcement OFF.");
                    }

                    if (!testingWithoutTurns) {
                        //System.out.println("It is now Team " + convertTeamMarkerToTeam(turnMarker) + "'s turn.");
                        messagesToAdd.add("It is now Team " + convertTeamMarkerToTeam(turnMarker) + "'s turn.");
                    }



                    needsToUpdate = true; // the pieces need to update where they can move
                }



                if (!testingWithoutTurns) {
                    if ( (convertTeamMarkerToTeam(turnMarker) != selectedPiece.getTeam())){
                        //System.out.println("Only pieces from Team " + convertTeamMarkerToTeam(turnMarker) + " may move.");
                        messagesToAdd.add("Only pieces from Team " + convertTeamMarkerToTeam(turnMarker) + " may move.");
                    }
                }

                selectedPiece.setIsHighlighted(false);
                selectedPiece = null;
                board.unhighlightAll();
                return;
            }
            else
            {
                for(Piece p : pieces)
                {
                    if(p.containsClick(mx, my))
                    {
                        selectedPiece = p;
                        selectedPiece.setIsHighlighted(true);
                        board.highlightLegalMoves(p);
                        return;
                    }
                }
            }
            if(selectedPiece != null)
            {
                selectedPiece.setIsHighlighted(false);
            }
            selectedPiece = null;
            board.unhighlightAll();
        }*/
    }

    // React to a click on the start screen
    public void reactToClickStart(int mx, int my)
    {
        if(startScreen.buttonPress(mx, my))
        {
            playing = true;
            needToStartGame = true;
        }
    }

    // React to a click on the player's turn
    public void reactToClickPlayer(int mx, int my)
    {
        // If the user clicks on the pass button
        if (passButton.containsClick(mx, my))
        {

            //Switch teams & let user know
            turnMarker = computerTurnMarker;
        }
        // If a Piece is selected, either move it or unselect it
        else if(selectedPiece != null)
        {
            BoardPoint bp = board.clickIsOnLegalMove(mx, my, selectedPiece);
            if(bp != null)
            {
                // If we are capturing, remove the piece getting captured
                if(board.containsPiece(bp))
                {
                    toBeRemoved = board.getPieceAt(bp);
                }
                // Now do the move
                board.move(selectedPiece, bp);

                turnMarker = computerTurnMarker;
                needsToUpdate = true; // the pieces need to update where they can move
            }
            // We have either moved the Piece, or clicked off it. In either case,
            // unhighlight the piece.
            selectedPiece.setIsHighlighted(false);
            selectedPiece = null;
            board.unhighlightAll();
            return;
        }
        else
        {
            for(Piece p : pieces)
            {
                if(p.containsClick(mx, my) && p.getTeam() == Team.Player)
                {
                    selectedPiece = p;
                    selectedPiece.setIsHighlighted(true);
                    board.highlightLegalMoves(p);
                    return;
                }
            }
        }
        if(selectedPiece != null)
        {
            selectedPiece.setIsHighlighted(false);
        }
        selectedPiece = null;
        board.unhighlightAll();
    }

    // React to a click on the computer's turn
    public void reactToClickComputer(int mx, int my)
    {
        // If the user clicks on the pass button
        if(passButton.containsClick(mx, my))
        {
            //Switch teams & let user know
            turnMarker = playerTurnMarker;
        }
        // If a Piece is selected, either move it or unselect it
        else if(selectedPiece != null)
        {
            BoardPoint bp = board.clickIsOnLegalMove(mx, my, selectedPiece);
            if(bp != null)
            {
                // If we are capturing, remove the piece getting captured
                if(board.containsPiece(bp))
                {
                    toBeRemoved = board.getPieceAt(bp);
                }
                // Now do the move
                board.move(selectedPiece, bp);

                turnMarker = playerTurnMarker;
                needsToUpdate = true; // the pieces need to update where they can move
            }
            // We have either moved the Piece, or clicked off it. In either case,
            // unhighlight the piece.
            selectedPiece.setIsHighlighted(false);
            selectedPiece = null;
            board.unhighlightAll();
            return;
        }
        else
        {
            for(Piece p : pieces)
            {
                if(p.containsClick(mx, my) && p.getTeam() == Team.Computer)
                {
                    selectedPiece = p;
                    selectedPiece.setIsHighlighted(true);
                    board.highlightLegalMoves(p);
                    return;
                }
            }
        }
        if(selectedPiece != null)
        {
            selectedPiece.setIsHighlighted(false);
        }
        selectedPiece = null;
        board.unhighlightAll();
    }

    // ===================================
    //
    //             Checkmate
    //
    // ===================================
    public boolean updatePlayerCanMove()
    {
        for(Piece p : pieces)
        {
            if(p.getTeam() == Team.Player && p.canMove())
            {
                playerCanMove = true;
                return true;
            }
        }
        playerCanMove = false;
        return false;
    }
    public boolean updateComputerCanMove()
    {
        for(Piece p : pieces)
        {
            if(p.getTeam() == Team.Computer && p.canMove())
            {
                computerCanMove = true;
                return true;
            }
        }
        computerCanMove = false;
        return false;
    }
    public boolean updatePlayerIsInCheck()
    {
        if(board.boardHasCheck(board.getSpaces(), Team.Player))
        {
            playerIsInCheck = true;
            return true;
        }
        else
        {
            playerIsInCheck = false;
            return false;
        }
    }
    public boolean updateComputerIsInCheck()
    {
        if(board.boardHasCheck(board.getSpaces(), Team.Computer))
        {
            computerIsInCheck = true;
            return true;
        }
        else
        {
            computerIsInCheck = false;
            return false;
        }
    }

    public boolean isGameOver()
    {
        if(turnMarker == playerTurnMarker && playerIsInCheck && !playerCanMove)
        {
            messagesToAdd.add("Player has been checkmated.");
            messagesToAdd.add("Computer wins.");
            return true;
        }
        if(turnMarker == computerTurnMarker && computerIsInCheck && !computerCanMove)
        {
            messagesToAdd.add("Computer has been checkmated.");
            messagesToAdd.add("Player wins.");
            return true;
        }
        return false;
    }

    public Team convertTeamMarkerToTeam(int turnMarker){
        if (turnMarker == playerTurnMarker){return Team.Player;}
        if (turnMarker == computerTurnMarker){return Team.Computer;}
        return Team.Player;
    }

    public Team getWhoseTurnItIs(){
        return convertTeamMarkerToTeam(this.turnMarker);
    }
}
