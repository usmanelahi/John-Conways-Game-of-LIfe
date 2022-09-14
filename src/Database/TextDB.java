//Text DB stores data in text files
package Database;

import BLLayer.Cell;
import BLLayer.DBInterfaceOut;

import java.io.*;
import java.util.ArrayList;


public class TextDB implements DBInterfaceOut
{

    public TextDB()
    {
        //do nothing
    }
    public void deleteRecentState()                        //Delete The Recent State
    {
        BufferedReader br;
        File obj;
        String data, recentSavedFile=null;
        File f = new File("SaveStates.txt");
        if(!f.exists() )
        {
            return;
        }
        try
        {
            br = new BufferedReader(new FileReader("SaveStates.txt"));
            while ((data = br.readLine()) != null)
            {
                recentSavedFile = data;
            }
            br.close();
            if(recentSavedFile==null)
                return;
            DeleteLine(recentSavedFile);
            obj = new File(recentSavedFile);
            obj.delete();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static  void  DeleteLine(String name)
    {
        File inputFile = new File("SaveStates.txt");
        File tempFile = new File("myTempFile.txt");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null)
            {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(name))
                    continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void deleteState(String name)                        //Delete The Given State
    {
        DeleteLine(name);
        File obj = new File(name);
        obj.delete();
    }
    public void saveState(int[][] activeCells,String name)                 //SAVE STATE IN FILE
    {
        //int[][] activeCells = new int[10][2];
        Cell cell;
        File file_obj = new File("SaveStates.txt");
        BufferedWriter out;
        //Enumeration e = ht.elements();
        int x_axis,y_axis;
        try
        {
            if (file_obj.exists())
            {
                try
                {
                    out = new BufferedWriter(new FileWriter("SaveStates.txt",true));
                    out.write(name+".txt");
                    out.append('\n');
                    out.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                file_obj.createNewFile();
                try
                {
                    out = new BufferedWriter(new FileWriter("SaveStates.txt"));
                    out.write(name+".txt");
                    out.append('\n');
                    out.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        try
        {
            out = new BufferedWriter(new FileWriter(name+".txt"));
            for(int i=0;i<activeCells.length;i++)
            {
                x_axis = activeCells[i][0];
                y_axis = activeCells[i][1];
                out.write(String.valueOf(x_axis));
                out.append(',');
                out.write(String.valueOf(y_axis));
                out.append('\n');
            }
            out.close();
        }
        catch (IOException exp2)
        {
            System.out.println(" Error: Can't write to file");
            exp2.printStackTrace();
        }
    }
    public  ArrayList<int[][]> loadRecentState()
    {
        ArrayList<int[][]> activeCells = new ArrayList<>();
        int[][] cell;
        BufferedReader br;
        //Hashtable<Cell, Cell> ht =new Hashtable<>();
        String data,recentSavedFile=null;
        int x_Axis = 0, y_Axis=0, val=0;
        int[] array1 = new int[200];
        File f = new File("SaveStates.txt");
        if(!f.exists() )
        {
            return activeCells;
        }
        try
        {
            br = new BufferedReader(new FileReader("SaveStates.txt"));     //GETTING RECENT NAME OF FILE
            while ((data = br.readLine()) != null)
            {
                recentSavedFile = data;
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(recentSavedFile==null)
            return activeCells;
        try
        {
            br = new BufferedReader(new FileReader(recentSavedFile));
            while((data = br.readLine()) != null)
            {
                for( int i = 0; i<=data.length();i++)                              //Extracting FROM RECENT FILE
                {
                    if(i==data.length())
                    {
                        for (int j = 0; j < val; j++) {                            //CONVERTING INT ARRAY TO INTEGER
                            y_Axis = 10 * y_Axis + array1[j];
                        }
                        break;
                    }
                    if(Character.isDigit(data.charAt(i)))                          //STORING IN INT ARRAY
                    {
                        array1[val] = data.charAt(i)-48;
                        val = val+1;
                    }
                    else if(data.charAt(i) == ',' )                                  //CONVERTING INT ARRAY TO INTEGER
                    {
                        for (int j = 0; j < val; j++) {
                            x_Axis = 10 * x_Axis + array1[j];
                        }
                        val = 0;
                    }
                }
                cell = new int[1][2];
                cell[0][0] = x_Axis;
                cell[0][1] = y_Axis;

                activeCells.add(cell);
                val = 0;
                x_Axis = 0;
                y_Axis = 0;
            }
            br.close();
        }
        catch (FileNotFoundException e)
        {

            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return activeCells;
    }
    public String[] getStatesNames()
    {
        String[] statesName = new String[60];
        int val = 0;
        BufferedReader br;
        File f = new File("SaveStates.txt");
        if(!f.exists())
        {
            return null;
        }
        try
        {
            br =new BufferedReader(new FileReader("SaveStates.txt"));
            while((statesName[val] = br.readLine())!=null)
            {
                val = val+1;
            }
            statesName[val] = "\0";
            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return statesName;
    }

    public ArrayList<int[][]> loadState(String Name)
    {
        ArrayList<int[][]> activeCells = new ArrayList<>();
        int[][] cell;
        BufferedReader br;
        String data;
        int x_Axis = 0,y_Axis=0,val=0;
        int[] array1 = new int[200];
        try
        {
            br = new BufferedReader(new FileReader(String.valueOf(Name)));
            while((data = br.readLine()) != null)
            {
                for( int i = 0; i<=data.length();i++)
                {
                    if(i==data.length())
                    {
                        for (int j = 0; j < val; j++)
                        {
                            y_Axis = 10 * y_Axis + array1[j];
                        }
                        break;
                    }
                    if(Character.isDigit(data.charAt(i)))
                    {
                        array1[val] = data.charAt(i)-48;
                        val = val+1;
                    }
                    else if(data.charAt(i) == ',' )
                    {
                        for (int j = 0; j < val; j++) {
                            x_Axis = 10 * x_Axis + array1[j];
                        }
                        val = 0;
                    }
                }
                cell = new int[1][2];
                cell[0][0] = x_Axis;
                cell[0][1] = y_Axis;

                activeCells.add(cell);
                val = 0;
                x_Axis = 0;
                y_Axis = 0;
            }
            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return activeCells;
    }
}
