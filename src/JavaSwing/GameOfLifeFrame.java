//game of life frame
package JavaSwing;

import Factory.Constants;
import BLLayer.GameOfLife;

import javax.print.DocFlavor;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class GameOfLifeFrame extends JFrame implements KeyListener, ActionListener
{
    URL fileURL;
    Clip clip;

    private final JFrame gameFrame = new JFrame("Game Of Life");
    private final GameBoard board;
    JButton startBtn;
    JButton nextBtn;
    JButton resetBtn;

    JButton saveStateBtn;
    JButton deleteStateBtn;
    JButton loadStateBtn;
    JButton viewStateBtn;
    JLabel genLabel;
    JLabel zoomLabel;
    JLabel speedLabel;

    JSlider speedSlider;
    JSlider zoomSlider;
    double bottomControlsPanelRatio = 15.75;

    public GameOfLifeFrame(GameOfLife g)
    {
        fileURL = this.getClass().getClassLoader().getResource("JavaSwing/mi.wav");
        AudioInputStream audioInputStream;
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(fileURL);
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }


        gameFrame.setLayout(null);

        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        board = new GameBoard(screenSize.width, (int) (screenSize.height-(screenSize.getHeight()*bottomControlsPanelRatio/100)), g);

        startBtn = new JButton("Start");
        nextBtn = new JButton("Next");
        resetBtn = new JButton("Clear");
        saveStateBtn = new JButton("Save State");
        deleteStateBtn = new JButton("Delete State");
        loadStateBtn = new JButton("Load State");
        viewStateBtn = new JButton("View State");
        genLabel = new JLabel("0");
        zoomLabel = new JLabel("Zoom");
        speedLabel = new JLabel("Speed");
        speedSlider = new JSlider(1, 7, 4);
        zoomSlider = new JSlider(1, 7, 4);


        gameFrame.addKeyListener(this);

        gameFrame.add(board);
        gameFrame.setVisible(true);//making the frame visible
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting buttons position (dynamically according to resolution)
        int menu1Y = (screenSize.height - (int) (screenSize.getHeight() * bottomControlsPanelRatio/100) + (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)/3 - (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)*26/100);
        int menu1Height = (int) (screenSize.getHeight() * bottomControlsPanelRatio/100)*26/100;
        int menu1Spacing = (int) (screenSize.getWidth()*3/100);

        int menu2Y = menu1Y + menu1Height*2;
        int menu2Spacing = (int) (screenSize.width * 1.56 /100);

        int startBtnWidth = (screenSize.width * 20 /100);
        int starBtnX = (screenSize.width/2) - startBtnWidth/2;

        int nextBtnWidth = (screenSize.width *12/ 100);
        int nextBtnX = (starBtnX + startBtnWidth + menu1Spacing);

        int resetBtnWidth = (screenSize.width *12/ 100);
        int resetBtnX = (nextBtnX + nextBtnWidth + menu1Spacing - 10);

        int zoomSliderWidth = (screenSize.width *12/ 100);
        int zoomSliderX = starBtnX - menu1Spacing - zoomSliderWidth;

        int speedSliderWidth = (screenSize.width *12/ 100);
        int speedSliderX = zoomSliderX - zoomSliderWidth - menu1Spacing + 10;

        int menu2BtnWidth = (screenSize.width/2)/4 - menu2Spacing/2;
        int saveBtnX = (screenSize.width/4) - menu2Spacing/2;
        int deleteBtnX =  saveBtnX + menu2BtnWidth + menu2Spacing;
        int loadBtnX = deleteBtnX + menu2BtnWidth +menu2Spacing;
        int viewBtnX = loadBtnX + menu2BtnWidth +menu2Spacing;

        //adding Buttons to Frame
        startBtn.setBounds(starBtnX, menu1Y , startBtnWidth, menu1Height);
        nextBtn.setBounds(nextBtnX, menu1Y+2, nextBtnWidth, menu1Height-2);
        resetBtn.setBounds(resetBtnX, menu1Y+2, resetBtnWidth, menu1Height-2);
        speedSlider.setBounds(speedSliderX, menu1Y+2, speedSliderWidth, menu1Height-2);
        zoomSlider.setBounds(zoomSliderX, menu1Y+2, zoomSliderWidth, menu1Height-2);

        saveStateBtn.setBounds(saveBtnX, menu2Y, menu2BtnWidth, menu1Height);
        deleteStateBtn.setBounds(deleteBtnX, menu2Y, menu2BtnWidth, menu1Height);
        loadStateBtn.setBounds(loadBtnX, menu2Y, menu2BtnWidth, menu1Height);
        viewStateBtn.setBounds(viewBtnX, menu2Y, menu2BtnWidth, menu1Height);

        zoomLabel.setBounds(zoomSliderX+5, menu1Y+30, 100, 20);
        speedLabel.setBounds(speedSliderX+5, menu1Y+30, 100, 20);
        genLabel.setBounds(resetBtnX+resetBtnWidth+20, menu1Y, 100, 30);


        //Zoom Changed Listener
        zoomSlider.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                zoom_Slider_StateChanged(evt);
            }

            private void zoom_Slider_StateChanged(ChangeEvent evt)
            {
                int zoom;

                if (zoomSlider.getValue() == GameBoard.zoomSize /5)
                    return;

                zoom =(zoomSlider.getValue()*5);
                board.gameControls.zoomChanged(zoom);
                board.updateBoard(board.gameControls.getGrid());
                genLabel.setText(Integer.toString(board.gameControls.getGeneration()));

            }
        } );
        //Speed Changed Listener
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                speed_Slider_StateChanged(evt);
            }

            private void speed_Slider_StateChanged(ChangeEvent evt)
            {
                int speed;

                if (speedSlider.getValue() == GameBoard.speed /160)
                    return;

                speed = Constants.minSpeed - speedSlider.getValue()*160;
                board.gameControls.speedChanged(speed);
                board.updateBoard(board.gameControls.getGrid());
                genLabel.setText(Integer.toString(board.gameControls.getGeneration()));


            }
        } );


        //add action key listeners to button
        startBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        saveStateBtn.addActionListener(this);
        deleteStateBtn.addActionListener(this);
        loadStateBtn.addActionListener(this);
        viewStateBtn.addActionListener(this);


        //setting button's properties
        startBtn.setLayout(null);
        startBtn.setFocusable(false);
        nextBtn.setLayout(null);
        nextBtn.setFocusable(false);
        resetBtn.setLayout(null);
        resetBtn.setFocusable(false);
        saveStateBtn.setLayout(null);
        saveStateBtn.setFocusable(false);
        deleteStateBtn.setLayout(null);
        deleteStateBtn.setFocusable(false);
        loadStateBtn.setLayout(null);
        loadStateBtn.setFocusable(false);
        viewStateBtn.setLayout(null);
        viewStateBtn.setFocusable(false);
        speedSlider.setLayout(null);
        speedSlider.setFocusable(false);
        zoomSlider.setLayout(null);
        zoomSlider.setFocusable(false);

        startBtn.setBackground(Color.darkGray);
        startBtn.setForeground(Color.black);
        nextBtn.setBackground(Color.darkGray);
        nextBtn.setForeground(Color.black);
        resetBtn.setBackground(Color.darkGray);
        resetBtn.setForeground(Color.black);
        saveStateBtn.setBackground(Color.darkGray);
        saveStateBtn.setForeground(Color.black);
        deleteStateBtn.setBackground(Color.darkGray);
        deleteStateBtn.setForeground(Color.black);
        loadStateBtn.setBackground(Color.darkGray);
        loadStateBtn.setForeground(Color.black);
        viewStateBtn.setBackground(Color.darkGray);
        viewStateBtn.setForeground(Color.black);
        speedSlider.setBackground(Color.darkGray);
        speedSlider.setForeground(Color.black);
        zoomSlider.setBackground(Color.darkGray);
        zoomSlider.setForeground(Color.black);

        //add buttons to frame
        if(board.gameControls.isDBAttached())
        {
            gameFrame.add(saveStateBtn);
            gameFrame.add(deleteStateBtn);
            gameFrame.add(loadStateBtn);
            gameFrame.add(viewStateBtn);
        }
        gameFrame.add(startBtn);
        gameFrame.add(nextBtn);
        gameFrame.add(resetBtn);
        gameFrame.add(genLabel);
        gameFrame.add(speedSlider);
        gameFrame.add(zoomSlider);
        gameFrame.add(zoomLabel);
        gameFrame.add(speedLabel);

    }


    @Override
    public void keyTyped(KeyEvent e)
    {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
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
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            try
            {
                if(board.currentBoardWindowY < GameBoard.rows - board.boardHeight / GameBoard.zoomSize)
                    board.currentBoardWindowY += 1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            try
            {
                if(board.currentBoardWindowX < Constants.gridCols -board.boardWidth / GameBoard.zoomSize)
                    board.currentBoardWindowX += 1;
                board.repaint();
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
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
    public void keyReleased(KeyEvent e)
    {
        //do nothing
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == startBtn)
        {
            board.gameControls.startStopButtonClick();
            startBtn.setText("Stop");
            resetBtn.setText("Reset");
            //clip.loop(Clip.LOOP_CONTINUOUSLY);

            Thread GameLoop = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    saveStateBtn.setVisible(false);
                    loadStateBtn.setVisible(false);
                    viewStateBtn.setVisible(false);
                    deleteStateBtn.setVisible(false);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                    //game loop continues if in running state
                    while(board.gameControls.isGameRunning())
                    {
                        board.gameControls.next();
                        board.updateBoard(board.gameControls.getGrid());
                        genLabel.setText(Integer.toString(board.gameControls.getGeneration()));
                        try
                        {
                            Thread.sleep(board.gameControls.getSpeed());
                        }
                        catch(InterruptedException e)
                        {
                            //do nothing
                        }
                    }
                    startBtn.setText("Start");
                    resetBtn.setText("Clear");

                    saveStateBtn.setVisible(true);
                    loadStateBtn.setVisible(true);
                    viewStateBtn.setVisible(true);
                    deleteStateBtn.setVisible(true);
                    clip.stop();
                }
            });
            GameLoop.start();
        }
        else if(e.getSource() == nextBtn)
        {
            board.gameControls.nextButtonClick();
            board.updateBoard(board.gameControls.getGrid());
            genLabel.setText(Integer.toString(board.gameControls.getGeneration()));

        }
        else if(e.getSource() == resetBtn)
        {
            board.gameControls.resetButtonClicked();
            board.updateBoard(board.gameControls.getGrid());
            genLabel.setText(Integer.toString(board.gameControls.getGeneration()));
        }
        else if(e.getSource() == saveStateBtn)
        {
            NameInputDialogBox box = new NameInputDialogBox();
            String stateName = null;
            stateName = box.get_string();
            if(stateName!=null)
            {
                board.gameControls.saveStateButtonClick(stateName);
                board.updateBoard(board.gameControls.getGrid());
                genLabel.setText(Integer.toString(board.gameControls.getGeneration()));
            }
        }
        else if(e.getSource() == deleteStateBtn)
        {
            String[] s = board.gameControls.getSavedStates();
            StateListPanel statesListPanel = new StateListPanel(s);
            final String[] selectedState = {null};

            Thread deleteStateLoop = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(selectedState[0] == null)
                    {
                        selectedState[0] = statesListPanel.getSelectedState();
                    }
                    if(selectedState[0] == null)
                        return;
                    board.gameControls.deleteStateButtonClick(selectedState[0]);
                    board.updateBoard(board.gameControls.getGrid());
                    genLabel.setText(Integer.toString(board.gameControls.getGeneration()));
                    selectedState[0]= null;
                }
            });
            deleteStateLoop.start();
        }
        else if(e.getSource() == loadStateBtn)
        {
            String[] s = board.gameControls.getSavedStates();
            StateListPanel statesListPanel = new StateListPanel(s);
            final String[] selectedState = {null};

            Thread loadStateLoop = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(selectedState[0] == null)
                    {
                        selectedState[0] = statesListPanel.getSelectedState();
                    }
                    if(selectedState[0] == null)
                        return;
                    board.gameControls.loadStateButtonClick(selectedState[0]);
                    board.updateBoard(board.gameControls.getGrid());
                    genLabel.setText(Integer.toString(board.gameControls.getGeneration()));
                    selectedState[0]= null;
                }
            });
            loadStateLoop.start();
        }
        else if(e.getSource() == viewStateBtn)
        {
            String[] s = board.gameControls.getSavedStates();
            StateListPanel statesListPanel = new StateListPanel(s);
            final String[] selectedState = {null};


            Thread viewStateLoop = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while(selectedState[0] == null)
                    {
                        selectedState[0] = statesListPanel.getSelectedState();
                    }

                    if(selectedState[0] == null)
                        return;
                    boolean[][] g = board.gameControls.getState(selectedState[0]);
                    FrameForViewState f2 = new FrameForViewState(g);
                    selectedState[0]= null;
                }
            });
            viewStateLoop.start();



        }

    }
}
