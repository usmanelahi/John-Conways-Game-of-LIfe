//BL layer use this interface to interact with DB
package BLLayer;

import java.util.ArrayList;
import java.util.Hashtable;

public interface DBInterfaceOut
{
    void saveState(int[][] activeCells, String name);
    void deleteRecentState();
    ArrayList<int[][]> loadState(String name);
    ArrayList<int[][]> loadRecentState();
    void deleteState(String names);
    String[] getStatesNames();
}
