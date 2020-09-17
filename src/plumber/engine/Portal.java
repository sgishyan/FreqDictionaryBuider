package plumber.engine;

public class Portal extends TubeShape {


	public Portal(int startRotation)
	{		
		super(startRotation);	
		uniquePositionsCount = 4;
	}
	


	
	@Override
	public Direction getDirection(Direction from) {
		
		
		switch(rotationsNumber)
		{
			case 0:
				if(from== Direction.UP)
					return Direction.PORTAL;
				break;
				
			case 1:	
				if(from== Direction.RIGHT)
					return Direction.PORTAL;
				break;

            case 2:
                if(from== Direction.DOWN)
                    return Direction.PORTAL;
                break;

            case 3:
                if(from== Direction.LEFT)
                    return Direction.PORTAL;
                break;

            default:
					return Direction.NO_WAY;
		}
		return Direction.NO_WAY;
	}



	@Override
	public boolean canRotate() {
		return false;
	}

    public void rotate()
    {
        //Do nothing . Can not rotate.
    }


    @Override
	public TubeType getTubeType() {
		return TubeType.PORTAL;
	}
	
	@Override
	public Tubes getTubesType() {
		return Tubes.PORTAL;
	}
	
	
	public String toString()
	{
		switch(rotationsNumber)
		{
            case 0:
                return "|T";
            case 1:
                return "T-";
            case 2:
                return "T|";
            case 3:
                return "-T";
            default:
                return "T";
		}
	}

	@Override
	public int getTubesTypeIndex()
	{
		return 9;
	}

}
