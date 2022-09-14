package Console;

import java.util.Objects;
import java.util.Scanner;
import Factory.Constants;
import BLLayer.GameOfLife;
import BLLayer.UIInterfaceIn;

public class ConsoleUI
{
    UIInterfaceIn gameControls;
    private static final int rows= Constants.gridRows;
    private static final int cols=Constants.gridCols;

    //grid movement starting coordinate(row, column)
    protected int currentBoardWindowX;
    protected int currentBoardWindowY;

    //console grid printing width & height
    private int consoleBoardWidth;
    private int consoleBoardHeight;

    private int speed;
    private int zoomSize;

    private static boolean[][] cellGridArr;
    private static boolean[][] viewStateGridArr;

    public ConsoleUI(GameOfLife g)
    {
        currentBoardWindowX = 0;
        currentBoardWindowY = 0;
        speed = Constants.defaultSpeed;
        zoomSize = Constants.defaultZoom;
        consoleBoardWidth = cols/ zoomSize;
        consoleBoardHeight = rows/ zoomSize;
        cellGridArr = new boolean[rows][cols];
        viewStateGridArr = new boolean[rows][cols];

        this.gameControls = g;
        updateBoard(gameControls.getGrid());
        label:
        while(true)
        {
            clearConsole();
            // draw_grid();
            System.out.println("""
                     Choose your option:
                    1-> Start/Stop
                    2->Next State
                    3->Reset/Clear
                    4->Set Cell
                    5->Exit
                    6->Save State
                    7->Load State
                    8->View State
                    9->Delete State
                    10->Speed Increase
                    11->Speed Decrease
                    12->Zoom In
                    13->Zoom out
                    14->Grid move up
                    15->Grid move down
                    16->Grid move right
                    17->grid move left""");
            Scanner input_obj = new Scanner(System.in);
            String choice = input_obj.nextLine();
            switch (choice)
            {
                case "1":
                    startStopButtonClicked();
                    break;
                case "2":
                    nextButtonClicked();
                    break;
                case "3":
                    resetButtonClicked();
                    break;
                case "4":
                    if (selectCell() == -1)
                    {
                        System.out.println("You have entered illegal col or row number! ");
                        break label;
                    }
                    break;
                case "5":
                    break label;
                case "6":
                    saveStateButtonClicked();
                    break;
                case "7":
                    loadStateButtonClicked();
                    break;
                case "8":
                    viewStatesButtonClicked();
                    break;
                case "9":
                    deleteStateButtonClicked();
                    break;
                case "10":
                    incSpeed();
                    break;
                case "11":
                    decSpeed();
                    break;
                case "12":
                    incZoom();
                    break;
                case "13":
                    decZoom();
                    break;
                case "14":
                    gridMove(14);
                    break;
                case "15":
                    gridMove(15);
                    break;
                case "16":
                    gridMove(16);
                    break;
                case "17":
                    gridMove(17);
                    break;
            }
        }
    }
    // Drawing grid along with filling active cells with 0 based on cell_life hashmap
    public void drawBoard()
    {
        for (int i = 0; i < consoleBoardHeight; i++)
        {
            for (int j = 0; j < consoleBoardWidth; j++)
            {
                //System.out.print(" ");
                if(cellGridArr[i+ currentBoardWindowY][j+ currentBoardWindowX])
                    System.out.print(" # ");
                else
                    System.out.print(" . ");

            }
            System.out.print(" \n");
        }
        System.out.print("\n\n");
    }
    public void drawViewStateBoard()
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols ; j++)
            {

                if(viewStateGridArr[i][j])
                    System.out.print(" 0 ");
                else
                    System.out.print(" . ");

            }
            System.out.print(" \n");

        }
    }
    public int selectCell()
    {
        int x, y;
        Scanner input_obj= new Scanner(System.in);
        System.out.println("-- Set Cell --\n");
        System.out.println("Enter Row# (0 -"+ rows+" )");
        x= input_obj.nextInt();
        if(x < 0 || x > rows)
        {
            System.out.println("You have entered illegal row number! ");
            return -1;
        }
        System.out.println("Enter Column# (0 -"+ cols+" )");
        y= input_obj.nextInt();
        if(y < 0 || y > cols)
        {
            System.out.println("You have entered illegal col number! ");
            return -1;
        }

        setCell(x,y);
        return 0;
    }
    public void setCell(int a, int b)
    {
        if(!gameControls.getCellStatus(a, b))
            gameControls.setCell(a, b, true);
        else
            gameControls.setCell(a, b, false);
        updateBoard(gameControls.getGrid());
    }
    public void updateBoard(boolean[][] grid)
    {
        for(int i = 0; i< rows; i++)
        {
            for(int j = 0; j< cols; j++)
            {
                cellGridArr[i][j] = grid[i][j];
            }
        }
        drawBoard();

    }
    public void updateViewStateBoard(boolean[][] grid)
    {
        for(int i = 0; i< rows; i++)
        {
            for(int j = 0; j< cols; j++)
            {
                viewStateGridArr[i][j] = grid[i][j];
            }
        }
        drawViewStateBoard();
    }

    //function to clear the whole screen!
    private static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected void startStopButtonClicked()
    {
        System.out.println(" Play button clicked");
        //start = true;
        gameControls.startStopButtonClick();
        Thread GameLoop = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(gameControls.isGameRunning())
                {
                    gameControls.next();
                    updateBoard(gameControls.getGrid());

                    try
                    {
                        Thread.sleep(speed);
                    }
                    catch(InterruptedException e)
                    {
                        //do nothing
                    }
                }
            }
        });
        GameLoop.start();
    }
    protected void nextButtonClicked()
    {
        //clear_full_screen();
        System.out.println(" Next Button clicked");
        gameControls.nextButtonClick();
        updateBoard(gameControls.getGrid());
    }
    // clear or reset button!
    protected void resetButtonClicked()
    {
        System.out.println(" Reset button clicked");
        gameControls.resetButtonClicked();
        updateBoard(gameControls.getGrid());
    }
    protected void loadStateButtonClicked()
    {
        String[] list = gameControls.getSavedStates();
        if(list != null)
        {
            System.out.println("Enter name of the state from following: \n");
            for (int i = 0; !Objects.equals(list[i], "\0"); i++) {
                System.out.println(i + ". " + list[i] + " ");
            }
            System.out.println("\n");
            Scanner view_input = new Scanner(System.in);
            String choice = view_input.nextLine();
            gameControls.loadStateButtonClick(choice);
            updateBoard(gameControls.getGrid());
        }
        else
            System.out.println("No state is saved yet! \n");
    }

    protected void saveStateButtonClicked()
    {
        System.out.println(" Save button clicked");
        Scanner input_state_name = new Scanner(System.in);
        // state name!
        System.out.print("Enter State Name-> ");
        String state_name = input_state_name.nextLine();
        gameControls.saveStateButtonClick(state_name);
        System.out.print("Out from save State\n");
        updateBoard(gameControls.getGrid());
    }
    protected void deleteStateButtonClicked()
    {
        String[] list = gameControls.getSavedStates();
        if(list != null)
        {
            System.out.println("Enter name of the state from following: \n");
            for (int i = 0; !Objects.equals(list[i], "\0"); i++) {
                System.out.println(i + ". " + list[i] + " ");
            }
            System.out.println("\n");
            Scanner view_input = new Scanner(System.in);
            String choice = view_input.nextLine();
            gameControls.deleteStateButtonClick(choice);
        }
        else
            System.out.println("No state is saved yet! \n");
    }
    protected void decSpeed()
    {
        if(speed<Constants.minSpeed)
        {
            gameControls.speedChanged(speed + 180);
            speed += 180;
        }
        updateBoard(gameControls.getGrid());
    }
    protected void incSpeed()
    {
        if(speed>Constants.maxSpeed)
        {
            gameControls.speedChanged(speed - 180);
            speed -= 180;
        }
        updateBoard(gameControls.getGrid());
    }
    protected void incZoom()
    {
        if(zoomSize <Constants.maxZoomIn)
        {
            gameControls.zoomChanged(zoomSize + 5);
            zoomSize += 5;
        }
        consoleBoardWidth = cols/ zoomSize;
        consoleBoardHeight = rows/ zoomSize;
        updateBoard(gameControls.getGrid());
    }
    protected void decZoom()
    {
        if(zoomSize >Constants.maxZoomOut)
        {
            gameControls.zoomChanged(zoomSize - 5);
            zoomSize -= 5;
        }
        consoleBoardWidth = cols/ zoomSize;
        consoleBoardHeight = rows/ zoomSize;
        updateBoard(gameControls.getGrid());
    }

    protected void viewStatesButtonClicked()
    {
        String[] list = gameControls.getSavedStates();

        if(list != null)
        {
            System.out.println("Enter name of the state from following: \n");
            for (int i = 0; !Objects.equals(list[i], "\0"); i++)
            {
                System.out.println(i + ". " + list[i] + " ");
            }
            System.out.println("\n");
            Scanner view_input = new Scanner(System.in);
            String choice = view_input.nextLine();
            boolean[][] g = gameControls.getState(choice);
            updateViewStateBoard(g);
        }
        else
            System.out.println("No state is saved yet! \n");
    }
    public void gridMove(int choice)
    {
        if (choice == 14)
        {
            try
            {
                if(currentBoardWindowY !=0)
                    currentBoardWindowY -= 4;
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }

        }
        else if (choice == 15)
        {
            try
            {
                if(currentBoardWindowY < rows - consoleBoardHeight)
                    currentBoardWindowY += 4;
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (choice == 16)
        {
            try
            {
                if(currentBoardWindowX < cols - consoleBoardWidth)
                    currentBoardWindowX += 4;
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        else if (choice == 17)
        {
            try
            {
                if(currentBoardWindowX !=0)
                    currentBoardWindowX -= 4;
            }
            catch (ArrayIndexOutOfBoundsException error)
            {
                //do nothing
            }
        }
        updateBoard(gameControls.getGrid());
    }
}


