// BoardPoint acts like a Point, except the coordinates
// are only ints. This is used to keep track of locations
// on the board. Also, the coordinates are private so they
// can only be modified through setters.
public class BoardPoint
{
    private int x, y;

    public BoardPoint(int inputX, int inputY)
    {
        this.setX(inputX);
        this.setY(inputY);
    }

    // Getters
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    // Setters
    public void setX(int inputX)
    {
        if(inputX < 0)
        {
            System.out.println("Warning: x clipped to 0.");
            x = 0;
        }
        else if(inputX > 8)
        {
            System.out.println("Warning: x clipped to 8");
            x = 8;
        }
        else
        {
            x = inputX;
        }
    }
    public void setY(int inputY)
    {
        if(inputY < 0)
        {
            System.out.println("Warning: y clipped to 0.");
            y = 0;
        }
        else if(inputY > 9)
        {
            System.out.println("Warning: y clipped to 9");
            y = 8;
        }
        else
        {
            y = inputY;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }
        if (!(o instanceof BoardPoint)) {
            return false;
        }

        BoardPoint otherBoardPoint = (BoardPoint) o;

        if(x == otherBoardPoint.getX() && y == otherBoardPoint.getY()){
            return true;
        } else {
            return false;
        }
    }

}
