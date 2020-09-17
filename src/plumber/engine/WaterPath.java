package plumber.engine;

public class WaterPath implements Comparable<WaterPath> {
	public int x;
	public int y;
	public Tubes tube;
	public Direction cameFrom;
	public boolean isFirstTime;
    public int rotation;
	public WaterPath(int x, int y, Tubes t, Direction came, boolean firstTime, int rotation)
	{
		this.x=x;
		this.y=y;
		tube=t;
		cameFrom=came;
		isFirstTime=firstTime;
        this.rotation = rotation;
		
	}

    public WaterPath(WaterPath waterPath) {
        this.x=waterPath.x;
        this.y=waterPath.y;
        tube=waterPath.tube;
        cameFrom=waterPath.cameFrom;
        isFirstTime=waterPath.isFirstTime;
        this.rotation = waterPath.rotation;
    }

    @Override
    public int compareTo(WaterPath o) {
        return (x * 10 + y) - (o.x * 10 + o.y);
    }
}
