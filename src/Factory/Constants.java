package Factory;

public class Constants
{
    //constants
    public final static int minSpeed = 1260;
    public final static int maxSpeed = 180;
    public final static int defaultSpeed = 720;
    public final static int defaultZoom = 20;
    public final static int maxZoomOut = 5;
    public final static int maxZoomIn = 35;

    //resolution requirements minimum(width 1400, height 750)
    public static int gridResolutionWidth = 1840;
    public static int getGridResolutionHeight = 874;

    //grid rows and columns
    public static int gridRows = getGridResolutionHeight/maxZoomOut;
    public static int gridCols = gridResolutionWidth/maxZoomOut;
}
