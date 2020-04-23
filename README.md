# JANGGI
Marcus Elia, Ruth Kollar, Christopher Suitor, Matthew Thompson

UVM CS205 Final Project
#

Janggi is a Korean board game reminiscent of Chess.
One major difference of Janggi from Chess is that, in Janggi, you move along the lines.

**Capture the enemy's General to win!**

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
The General can move one unit in each direction. However, the General cannot leave the 
General's Camp. **The General's Camp is the 3x3 square with an X in the middle.** Once the 
General's life is threatened, you must protect them. If you or the computer fails to 
protect the life of the General, the game ends.

### The Guards
The guards are the General's personal protectors. They cannot leave the General's Camp either, 
but they can move one unit in any direction.

### The Horses
The horses can move one unit either up, down, left, or right followed by a diagonal movement 
either left or right. The final result is a path that looks similar to the "L" movement that 
Knights in chess make.

### The Elephants
Interestingly, non-native to Korea, the elephants are critical pieces to this game. 
They move one unit either up, down, left, or right followed by two diagonal movements in 
the same direction. Confused? Play our game and see for yourself :)

### The Chariots
The chariots are reminiscent of the rooks from Chess. They can move as many units possible 
(unless their path is blocked) either up, down, left, or right. The only difference from 
the rook is that the chariots can move along the diagonals when they enter the General's Camp.

### The Soldiers
Similar to pawns in Chess, the soldiers are Janggi's grunts. They can move one unit away from 
their General's camp, left, or right. They can also move along the diagonals in the General's 
Camp.

## Fun Game Rule: Pass
Unlike Chess, you can pass your turn as long as you're not in check. The game ends in a draw 
if both the player and computer consecutively pass.