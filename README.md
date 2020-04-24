# JANGGI
Marcus Elia, Ruth Kollar, Christopher Suitor, Matthew Thompson

UVM CS205 Final Project
#

Janggi is a Korean board game reminiscent of Chess.
One major difference between Janggi and Chess is that, in Janggi, you move along the lines.

**Checkmate the enemy's General to win!**

The pieces include:
- General
- Guards
- Horses
- Elephants
- Chariots
- Cannons
- Soldiers

### The General
Like the King in Chess, the General is the piece to be protected. 
The General can move one unit in each direction (including diagonally). However, the General cannot leave the 
General's Camp. **The General's Camp is the 3x3 square with an X in the middle.** Once the 
General's life is threatened, you must protect them. (If an opposing piece can
capture your General, that called "being in check"). If you are in check,
the game will force you to move out of check. It will also not allow you to
move into check.  If you are in check and there is no way to protect your
general, that is called checkmate.

### The Guards
The guards are the General's personal protectors. They cannot leave the General's Camp either, 
but they can move one unit in any direction.

### The Horses
The horses can move one unit either up, down, left, or right followed by a diagonal movement 
either left or right. The final result is a path that looks similar to the "L" movement that 
Knights in chess make. The main difference is that they cannot jump.

### The Elephants
Interestingly, non-native to Korea, the elephants are critical pieces to this game. 
They move one unit either up, down, left, or right followed by two diagonal movements in 
the same direction.  Essentially, an elephant moves like a horse, but goes one
 unit further diagonally from where it is moving from. 
 Confused? Play our game and see for yourself :)

### The Chariots
The chariots are reminiscent of the rooks from Chess. They can move as many units possible 
(unless their path is blocked) either up, down, left, or right. The only difference from 
the rook is that the chariots can move along the diagonals when they enter the General's Camp.

### The Soldiers
Similar to pawns in Chess, the soldiers are Janggi's grunts. They can move one unit away from 
their General's camp, left, or right. They can also move along the diagonals in the General's 
Camp. Unlike pawns, they capture exactly the same way that they move.

### The Cannons
Cannons have the most complicated movement. They move in straight lines,
like the Chariots, but they must jump over another piece. After jumping over
a piece, they can move as far as they want in that direction until they reach
another piece or the edge of the board. Cannons cannot jump over other cannons,
and they cannot capture the other player's cannons. You will notice that a
cannon cannot move in the default starting position.

## Passing
Unlike Chess, there is no stalemate. If you cannot move legally and you are 
not in check, you pass your turn and the other team gets to move again. The
pass button will light up if you have no legal moves.

### Draws
If the generals ever face each other with no pieces in between, the other team
must move to prevent the generals from facing. If they choose not to, the game
ends in a draw.