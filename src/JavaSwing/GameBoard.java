//game board(grid)
package JavaSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Factory.Constants;
import BLLayer.GameOfLife;
import BLLayer.UIInterfaceIn;

//main board of game of life
public class GameBoard extends JPanel implements ActionListener, MouseListener, MouseMotionListener
{
    UIInterfaceIn gameControls;

    //actual grid rows & columns
    protected static final int cols = Constants.gridCols;
    protected static final int rows = Constants.gridRows;
    protected static int zoomSize;
    protected static int speed;
    int boardWidth, boardHeight;    //screen resolution width & height

    //grid movement starting coordinate(row, column)
    public int currentBoardWindowX, currentBoardWindowY;  //used in grid movement

    boolean[][] gameGridArr;
    boolean isMouseLeftButtonClicked = false;


    public GameBoard(int boardWidth, int boardHeight, GameOfLife g)
    {
        this.gameControls = g;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        zoomSize = gameControls.getZoom();
        speed = gameControls.getZoom();
        currentBoardWindowX = (cols)/2-((boardWidth/ zoomSize)/2);
        currentBoardWindowY = (rows)/2-((boardHeight/ zoomSize)/2);

        gameGridArr = new boolean[Constants.gridRows][Constants.gridCols];

        setSize(boardWidth, boardHeight);
        setLayout(null);
        setBackground(Color.black);
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        grid(g);
        drawGraphics(g);
    }


    private void grid(Graphics g)
    {
        g.setColor(Color.darkGray);

        for(int i = 0; i< boardWidth / zoomSize; i++)
        {
            for (int j = 0; j< boardHeight / zoomSize; j++)
            {
                g.drawRect((i * zoomSize), (j* zoomSize), zoomSize, zoomSize);
            }
        }

    }

    private void drawGraphics(Graphics g)
    {
        g.setColor(Color.yellow);

        for(int x = 0; x< boardHeight / zoomSize; x++)
        {
            for(int y = 0; y< boardWidth / zoomSize; y++)
            {
                try
                {
                    try
                    {
                        if (gameGridArr[(x + currentBoardWindowY)][(y + currentBoardWindowX)])
                            g.fillRect((1 + y * zoomSize), (1 + x * zoomSize), zoomSize - 2, zoomSize - 2);
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        // do nothing
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    // do nothing
                }

            }
        }

    }

    public void updateBoard(boolean[][] grid)
    {
        setDimensions(boardWidth, boardHeight);
        for(int i = 0; i< rows; i++)
        {
            for(int j = 0; j< cols; j++)
            {
                gameGridArr[i][j] = grid[i][j];
            }
        }
        repaint();
    }

    public void setDimensions(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        int changedZoomValue = gameControls.getZoom();

        if(zoomSize < changedZoomValue)
        {
            currentBoardWindowX = currentBoardWindowX + ((boardWidth/ zoomSize) - (boardWidth/changedZoomValue))/2;
            currentBoardWindowY = currentBoardWindowY + ((boardHeight/ zoomSize) - (boardHeight/changedZoomValue))/2;
        }
        else if (zoomSize > changedZoomValue)
        {
            int xOriginCalculation = ((currentBoardWindowX - ((boardWidth/changedZoomValue)/2 - (boardWidth/ zoomSize)/2))+(boardWidth/changedZoomValue));
            int yOriginCalculation = ((currentBoardWindowY - ((boardHeight/changedZoomValue)/2 - (boardHeight/ zoomSize)/2))+(boardHeight/changedZoomValue));
            if(xOriginCalculation < cols && currentBoardWindowX !=0)
                currentBoardWindowX = currentBoardWindowX - ((boardWidth/changedZoomValue)/2 - (boardWidth/ zoomSize)/2);
            else if (currentBoardWindowX != 0)
                currentBoardWindowX = currentBoardWindowX - ((boardWidth/changedZoomValue) - (boardWidth/ zoomSize));

            if (yOriginCalculation < rows && currentBoardWindowY !=0)
                currentBoardWindowY = currentBoardWindowY - ((boardHeight/changedZoomValue)/2 - (boardHeight/ zoomSize)/2);
            else if (currentBoardWindowY != 0 )
                currentBoardWindowY = currentBoardWindowY - ((boardHeight/changedZoomValue) - (boardHeight/ zoomSize));

            xOriginCalculation = currentBoardWindowX +(boardWidth/changedZoomValue);
            yOriginCalculation = currentBoardWindowY +(boardHeight/changedZoomValue);
            if(currentBoardWindowX <0)
                currentBoardWindowX =0;
            if(currentBoardWindowY <0)
                currentBoardWindowY =0;
            if(xOriginCalculation > cols)
            {
                currentBoardWindowX = cols-xOriginCalculation;
            }
            if(yOriginCalculation > rows)
            {
                currentBoardWindowY = rows-yOriginCalculation;
            }
        }
        zoomSize = changedZoomValue;
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }


    public void mouseDragged(MouseEvent e)
    {
        try
        {
            int x = (e.getX() / zoomSize) + currentBoardWindowX;
            int y = (e.getY() / zoomSize) + currentBoardWindowY;
            if (!gameControls.getCellStatus(y, x) && isMouseLeftButtonClicked) {
                gameControls.setCell(y, x, true);
                //mouseClicked(e);
            } else if (gameControls.getCellStatus(y, x) && !isMouseLeftButtonClicked)
                gameControls.setCell(y, x, false);

            repaint();
            gameControls.setGeneration();
            updateBoard(gameControls.getGrid());
        }
        catch (ArrayIndexOutOfBoundsException e3)
        {
            //do nothing
        }
    }
    public void mouseMoved(MouseEvent e)
    {
        //do nothing
    }
    synchronized public void mouseClicked(MouseEvent e)
    {
        //do nothing
    }
    synchronized public void mousePressed(MouseEvent e)
    {
        try
        {
            int x = (e.getX() / zoomSize) + currentBoardWindowX;
            int y = (e.getY() / zoomSize) + currentBoardWindowY;
            isMouseLeftButtonClicked = true;

            if (!gameControls.getCellStatus(y, x) && isMouseLeftButtonClicked)
            {
                gameControls.setCell(y, x, true);
            }
            else if (gameControls.getCellStatus(y, x))
                gameControls.setCell(y, x, false);

            gameControls.setGeneration();
            updateBoard(gameControls.getGrid());
        }
        catch (ArrayIndexOutOfBoundsException e2)
        {
            //do nothing
        }

    }
    public void mouseReleased(MouseEvent e)
    {
        isMouseLeftButtonClicked = false;
    }
    public void mouseEntered(MouseEvent e)
    {
        //do nothing
    }
    public void mouseExited(MouseEvent e)
    {
        //do nothing
    }

}
