package JavaSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class FrameForViewState extends JFrame implements KeyListener, ActionListener
{
    private final JFrame viewStateFrame = new JFrame("View State Frame");
    private final BoardForViewState board;
    JButton exitBtn;

    double bottomControlsPanelRatio = 15.75;    //ratio used to calculate area for game button

    public FrameForViewState(boolean[][] grid)
    {
        viewStateFrame.setLayout(null);

        viewStateFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        viewStateFrame.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        board = new BoardForViewState(screenSize.width, (int) (screenSize.height-(screenSize.getHeight()*bottomControlsPanelRatio/100)), grid);

        exitBtn = new JButton("Exit");


        viewStateFrame.addKeyListener(this);

        viewStateFrame.add(board);
        viewStateFrame.setVisible(true); //making the frame visible

        int menu1Y = (screenSize.height - (int) (screenSize.getHeight() * bottomControlsPanelRatio/100) + (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)/3 - (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)*26/100);
        int menu1Height = (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)*26/100;

        int exitBtnWidth = (screenSize.width * 20 /100);
        int exitBtnX = (screenSize.width/2) - exitBtnWidth/2;


        //adding Button and it's properties to Frame
        exitBtn.setBounds(exitBtnX, menu1Y , exitBtnWidth, menu1Height);

        exitBtn.setBackground(Color.darkGray);
        exitBtn.setForeground(Color.black);

        exitBtn.addActionListener(this);

        exitBtn.setLayout(null);
        exitBtn.setFocusable(false);
        viewStateFrame.add(exitBtn);
    }


    @Override
    public void keyTyped(KeyEvent eventPerformed)
    {
        //do nothing
    }


    //key pressed events
    @Override
    public void keyPressed(KeyEvent eventPerformed)
    {
        if(eventPerformed.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            viewStateFrame.dispatchEvent(new WindowEvent(viewStateFrame, WindowEvent.WINDOW_CLOSING));
        }
        else if (eventPerformed.getKeyCode() == KeyEvent.VK_UP)
        {
            //move grid upward
            try
            {
                if(board.currentBoardWindowY !=0)
                    board.currentBoardWindowY -=1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }

        }
        else if (eventPerformed.getKeyCode() == KeyEvent.VK_DOWN)
        {
            //move grid downward
            try
            {
                if(board.currentBoardWindowY < GameBoard.rows - board.boardHeight / BoardForViewState.zoomSize)
                    board.currentBoardWindowY += 1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (eventPerformed.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            //move grid right
            try
            {

                if(board.currentBoardWindowX < GameBoard.cols -board.boardWidth / BoardForViewState.zoomSize)
                    board.currentBoardWindowX += 1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (eventPerformed.getKeyCode() == KeyEvent.VK_LEFT)
        {
            //move grid left
            try
            {
                if(board.currentBoardWindowX !=0)
                    board.currentBoardWindowX -= 1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent error)
    {
        //do nothing
    }


    //exit button event
    @Override
    public void actionPerformed(ActionEvent eventPerformed)
    {
        if(eventPerformed.getSource() == exitBtn)
        {
            viewStateFrame.dispatchEvent(new WindowEvent(viewStateFrame, WindowEvent.WINDOW_CLOSING));
        }

    }
}
