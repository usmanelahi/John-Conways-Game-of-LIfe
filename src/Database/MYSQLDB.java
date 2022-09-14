//MYSQL DB stores data in tables using sql
package Database;

import BLLayer.DBInterfaceOut;

import java.sql.*;
import java.util.ArrayList;


public class MYSQLDB implements DBInterfaceOut
{
    Connection connect;
    String serverName, password;

    public MYSQLDB(String server, String pass)
    {
        this.serverName = server;
        this.password = pass;
    }

    public void Connection()
    {
        try
        {
            String url = "jdbc:mysql://localhost/gol";
            connect = DriverManager.getConnection(url, serverName, password);
        }
        catch (Exception e)
        {
            System.out.println("NOT-CONNECTED");
            e.printStackTrace();
        }
    }

    public void saveState(int[][] activeCells , String name)     //GETTING HASHTABLE AND ADDING X AND Y AXIS TO DATABASE
    {
        Connection();                                            //CREATING CONNECTION BETWEEN MYSQL AND JAVA
        int x_axis, y_axis;

        String sql;
        for(int i=0;i<activeCells.length;i++)
        {
            x_axis = activeCells[i][0];
            y_axis = activeCells[i][1];
            sql = "Insert Into CELLS (X_Axis,Y_Axis,Name) Values ('" + x_axis + "','" + y_axis + "','"+name+"')";  //RUN THE SQL COMMAND
            try
            {
                Statement statement = connect.createStatement();
                statement.executeUpdate(sql);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

    }


    public void deleteRecentState()
    {
        Connection();
        String names;
        String sql = "SELECT * from CELLS order by Id DESC LIMIT 1";
        try
        {
            Statement statement = connect.createStatement();
            ResultSet RT = statement.executeQuery(sql);
            if(!RT.next())
                return;
            names = RT.getString("Name");
            PreparedStatement statement1 = connect.prepareStatement("Delete FROM CELLS where Name=?");
            statement1.setString(1,names);
            statement1.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }

    }

    @Override
    public ArrayList<int[][]> loadState(String name)
    {
        ArrayList<int[][]> activeCells = new ArrayList<>();
        int[][] cell;
        Connection();
        String sql = "SELECT * from CELLS where Name='"+ name +"'";                                                   // CALLING LoadState() TO RETRIEVE THE DATA FROM MYSQL
        try
        {
            Statement statement = connect.createStatement();
            ResultSet RT = statement.executeQuery(sql);
            while (RT.next())                                      //GETTING DATA FROM COLUMNS AND PLACING IN HASHTABLE
            {
                cell = new int[1][2];
                cell[0][0] = RT.getInt("X_Axis");
                cell[0][1] = RT.getInt("Y_Axis");
                activeCells.add(cell);
            }
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return activeCells;
    }


    public ArrayList<int[][]> loadRecentState()
    {
        ArrayList<int[][]> activeCells = new ArrayList<>();
        int[][] cell;
        Connection();
        String names;
        String sql = "SELECT * from CELLS order by Id DESC LIMIT 1,1";

        try
        {
            Statement statement = connect.createStatement();
            ResultSet RT = statement.executeQuery(sql);
            if(!RT.next())
                return activeCells;

            names = RT.getString("Name");

            sql = "SELECT * from CELLS where Name='"+ names +"'";
            RT = statement.executeQuery(sql);
            while (RT.next())                                      //GETTING DATA FROM COLUMNS AND PLACING IN ArrayList
            {
                cell = new int[1][2];
                cell[0][0] = RT.getInt("X_Axis");
                cell[0][1] = RT.getInt("Y_Axis");
                activeCells.add(cell);
            }
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return activeCells;
    }


    public void deleteState(String names)
    {
        Connection();
        try
        {
            connect.createStatement();
            PreparedStatement statement1 = connect.prepareStatement("Delete FROM CELLS where Name=?");
            statement1.setString(1,names);
            statement1.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
    }


    public String[] getStatesNames()
    {
        Connection();
        String[] stateNames = new String[30];
        int val = 0;
        String sql = "SELECT DISTINCT(CELLS.NAME) from CELLS";
        try
        {
            Statement statement = connect.createStatement();
            ResultSet RT = statement.executeQuery(sql);
            while (RT.next())
            {
                stateNames[val] = RT.getString("Name");
                val = val +1;
            }
            stateNames[val] = "\0";
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return stateNames;
    }
}






