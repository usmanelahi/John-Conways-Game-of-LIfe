package BLLayer;
import Factory.Constants;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class GameOfLife<audioStream> implements UIInterfaceIn
{
    protected Grid grid;
    protected DBInterfaceOut dbListener = null;
    protected int zoom;
    protected int speed;
    protected boolean gameStatus;

    public  GameOfLife()
    {
        this.grid = new Grid();
        this.speed = Constants.defaultSpeed;
        this.zoom = Constants.defaultZoom;
        this.grid.generation = 0;
    }

    public void start()
    {
        this.gameStatus = true;
    }
    public void stop()
    {
        this.gameStatus = false;
    }

    public void reset()
    {
        grid.reset();
        this.grid.setGeneration(0);
    }

    //IN_Interface_UI
    public boolean isGameRunning()
    {
        return this.gameStatus;
    }

    @Override
    public boolean getCellStatus(int x, int y)
    {
        return grid.getCellStatus(x, y);
    }

    @Override
    public void setCell(int x, int y, boolean status)
    {
        grid.setCell(x, y, status);
    }

    @Override
    public void next()
    {
        grid.next();
    }

    @Override
    public void clear()
    {
        grid.clear();
        this.grid.setGeneration(0);
    }
    @Override
    public void setGeneration()
    {
        this.grid.setGeneration(0);
    }

    public void setZoom(int value)
    {
        this.zoom=value;
    }
    public void setSpeed(int value)
    {
        this.speed=value;
    }
    public int getZoom()
    {
        return this.zoom;
    }
    public int getSpeed()
    {
        return this.speed;
    }
    @Override
    public void startStopButtonClick()
    {
        if(isGameRunning())
            stop();
        else
        {
            start();
            grid.saveInitialShape();
        }
    }
    @Override
    public void nextButtonClick()
    {
        grid.next();
    }
    @Override
    public void resetButtonClicked()
    {
        if(isGameRunning())
        {
            reset();
        }
        else
            clear();
    }
    @Override
    public void zoomChanged(int value)
    {
        setZoom(value);
    }
    @Override
    synchronized public void saveStateButtonClick(String name)
    {
        Hashtable h = this.grid.getCurrentShape();
        int[][] activeCells = new int[h.size()][2];
        Cell c;
        Enumeration enumerate = h.keys();
        int index=0;
        while(enumerate.hasMoreElements())
        {
            c = (Cell) enumerate.nextElement();
            activeCells[index][0] = c.x_axis;
            activeCells[index][1] = c.y_axis;
            index++;
        }
        dbListener.saveState(activeCells, name);
    }
    @Override
    synchronized public void deleteStateButtonClick(String name)
    {
        dbListener.deleteState(name);
    }
    @Override
    synchronized public void loadStateButtonClick(String name)
    {
        int[][] cell;
        ArrayList<int[][]> activeCells;
        activeCells = dbListener.loadState(name);
        grid.clear();
        for (int i = 0; i < activeCells.size(); i++)
        {
            cell = activeCells.get(i);
            this.grid.setCell(cell[0][0], cell[0][1], true);
        }
    }

    @Override
    public String[] getSavedStates()
    {
        return dbListener.getStatesNames();
    }

    @Override
    public boolean[][] getState(String name)
    {
        boolean[][] g = new boolean[grid.getCellRows()][grid.getCellColumns()];
        for(int i=0;i<grid.getCellRows();i++)
        {
            for(int j=0;j<grid.getCellColumns();j++)
            {
                g[i][j] = false;
            }
        }
        //Grid g = new Grid();
        int[][] cell;
        ArrayList<int[][]> activeCells;
        activeCells = dbListener.loadState(name);
        for (int i=0;i<activeCells.size();i++)
        {
            cell = activeCells.get(i);
            g[cell[0][0]][cell[0][1]] = true;
        }
        return g;
    }

    @Override
    public void speedChanged(int value)
    {
        setSpeed(value);
    }


    public boolean[][] getGrid()
    {
        boolean[][] g = new boolean[grid.getCellRows()][grid.getCellColumns()];
        for(int i=0;i<grid.getCellRows();i++)
        {
            for(int j=0;j<grid.getCellColumns();j++)
            {
                g[i][j] = grid.getCellStatus(i, j);
            }
        }
        return g;
    }

    @Override
    public int getGeneration() {
        return this.grid.getGeneration();
    }

    //IN_Interface_DB
    public void addDBListener(DBInterfaceOut l)
    {
        this.dbListener = l;
    }
    public void detachDB()
    {
        this.dbListener = null;
    }
    public boolean isDBAttached()
    {
        if(dbListener == null)
            return false;

        return true;
    }
    public static void main(String[] arg)
    {
        //do nothing
    }
}
