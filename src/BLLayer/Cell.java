//Basic Cell Class with cell status(active or not active) and activeNeighbours
package BLLayer;
public class Cell
{
    protected int x_axis;
    protected int y_axis;
    protected int activeNeighbours;
    protected boolean isAlive;

    public Cell(int x, int y)
    {
        this.x_axis = x;
        this.y_axis = y;
        this.isAlive = false;
        this.activeNeighbours = 0;
    }
    public int getActiveNeighbours(){return activeNeighbours;}

    public boolean equals(Object o)
    {
        if(!(o instanceof Cell))
            return false;
        return x_axis==((Cell)o).x_axis && y_axis==((Cell)o).y_axis;
    }
    public boolean isAlive(){return isAlive;}
    public void setCellStatus(boolean status)
    {
        this.isAlive=status;
    }
}
