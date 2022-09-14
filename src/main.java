import Console.ConsoleUI;
import Database.MYSQLDB;
import Database.TextDB;
import Factory.Constants;
import BLLayer.GameOfLife;
import JavaSwing.GameOfLifeFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class main
{
    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        new Constants();
        GameOfLife game = new GameOfLife();

        //MYSQLDB db = new MYSQLDB("root", "root1234");
        TextDB db = new TextDB();
        game.addDBListener(db);

        new GameOfLifeFrame(game);
        //new ConsoleUI(game);
    }
}
