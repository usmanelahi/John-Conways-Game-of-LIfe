//used to view saved states
package JavaSwing;

import Factory.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardForViewState extends JPanel implements ActionListener
{
    protected static final int cols = Constants.gridCols;
    protected static final int rows = Constants.gridRows;
    protected static int zoomSize;

    //grid movement starting coordinate(row, column)
    protected int currentBoardWindowX, currentBoardWindowY;

    int boardWidth, boardHeight;
    boolean[][] viewGridArr;

    public BoardForViewState(int boardWidth, int boardHeight, boolean[][] grid)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        zoomSize = Constants.maxZoomOut;

        this.currentBoardWindowX = (cols)/2-((boardWidth/ zoomSize)/2);
        this.currentBoardWindowY = (rows)/2-((boardHeight/ zoomSize)/2);

        viewGridArr = grid;

        setSize(boardWidth, boardHeight);
        setLayout(null);
        setBackground(Color.black);

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
        g.setColor(Color.pink);

        for(int x = 0; x< boardHeight / zoomSize; x++)
        {
            for(int y = 0; y< boardWidth / zoomSize; y++)
            {
                try
                {
                    if(viewGridArr[x+ currentBoardWindowY][y+ currentBoardWindowX])
                        g.fillRect((1+y* zoomSize), (1+x* zoomSize), zoomSize -2, zoomSize -2);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    // do nothing
                }

            }
        }

    }
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

}
