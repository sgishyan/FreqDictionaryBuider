package plumber.engine;


import mobiloids.Pair;
import rus.Word;
import wordwars.CollectLetters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class PlumberModelOld {

    private int valveY;
    private int valveX;
    private int height,width;
    private TubeShape[][] gameBoard;
    private ArrayList<WaterPath> path;
    private ArrayList<ArrayList<WaterPath>> solutions;



	
	public PlumberModelOld(int[][] array, int h, int w)
	{		
		height=h;
		width=w;		
		gameBoard=new TubeShape[height][width];
		path=new ArrayList<WaterPath>();
        solutions = new ArrayList<>();
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
			{
				int code=array[i][j];
				int tube=code/10;
				int rotation=code%10;
				//System.out.println("Code#" + code);
				switch(tube)
				{
					case 1:
						gameBoard[i][j]=new Tube(rotation);
						break;
						
					case 2:
						gameBoard[i][j]=new Angle(rotation);
						break;
						
					case 3:
						gameBoard[i][j]=new DoubleTube(rotation);
						break;
						
					case 4:
						gameBoard[i][j]=new DoubleAngle(rotation);
						break;
						
					case 5:
						gameBoard[i][j]=new ValveTube(rotation);
                        valveX = i;
                        valveY = j;
                        path.add(new WaterPath(valveX, valveY, Tubes.VALVE, Direction.NO_WAY, true, rotation));
                        //System.out.println("Valve found");
						break;
						
					case 6:
						gameBoard[i][j]=new EndTube(rotation);
						break;

                    case 7:
                        gameBoard[i][j]=new EmptyTube(rotation);
                        break;


				}
				
				
			}
	}

    public ArrayList<WaterPath> shortestPath () {


        findAllPaths(path, Direction.NO_WAY);
        if (solutions.size() == 0) {
            return null;
        }


    //    System.out.println("Total solutions : " + solutions.size());
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).size() < min) {
                min = solutions.get(i).size();
                minIndex = i;
            }
        }
       // System.out.println("Best solutions : " + min);
        return solutions.get(minIndex);
    }

    public void findAllPaths(ArrayList<WaterPath> currentPath, Direction cameFrom) {

        int curX = currentPath.get(currentPath.size() - 1).x;
        int curY = currentPath.get(currentPath.size() - 1).y;
        int oldX = curX;
        int oldY = curY;
        int startRotation = gameBoard[curX][curY].getRotation();
      //  System.out.println("( "  + curX + " ; " + curY + " )  : " + currentPath.size() + " " + cameFrom) ;
        // Log.i("New Vertex:", curX + " " + curY);
        //Adding start

        Direction weCameFrom = cameFrom;

        if (curX < 0 || curX >= height || curY < 0 || curY >= width)
            return;

        //Checking if there was a loop, in that case just don't rotate
        boolean isThereALoop = false;
        for (int i = 0; i < currentPath.size() - 1; i++) {
            if(currentPath.get(i).x == curX && currentPath.get(i).y == curY) {
                isThereALoop = true;
                break;
            }
        }
        int rotationsLimit = gameBoard[curX][curY].getUniquePositionsCount();
        if (gameBoard[curX][curY] instanceof DoubleTube) {
            rotationsLimit = 1;
        }
        if (isThereALoop) {
            rotationsLimit = 1;
        }
        //System.out.println("Rotations " + gameBoard[curX][curY].getUniquePositionsCount());
        for (int r = 0; r < rotationsLimit; r++) {


            //System.out.println("try");
            weCameFrom=gameBoard[curX][curY].getDirection(cameFrom);
            switch (weCameFrom) {
                case UP:
                    curX--;
                    break;
                case DOWN:
                    curX++;
                    break;
                case LEFT:
                    curY--;
                    break;
                case RIGHT:
                    curY++;
                    break;

                case NO_WAY:
                    curX = oldX;
                    curY = oldY;
                    gameBoard[curX][curY].rotate();
                    continue;

            }

            boolean isFirstTime = true;
            for (WaterPath p : currentPath)
                if (p.x == curX && p.y == curY) {
                    isFirstTime = false;
                    break;
                }
            if (curX < 0 || curX >= height || curY < 0 || curY >= width) {
                curX = oldX;
                curY = oldY;
                gameBoard[curX][curY].rotate();
                continue;
            }

            ArrayList<WaterPath> newPath = new ArrayList<>(currentPath);
            newPath.add(new WaterPath(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(weCameFrom), isFirstTime, gameBoard[oldX][oldY].getRotation()));

            weCameFrom = convertDirection(weCameFrom);
            if (gameBoard[curX][curY].getTubeType() == TubeType.END) {
                solutions.add(newPath);
            } else {
                findAllPaths(newPath, weCameFrom);
            }
            curX = oldX;
            curY = oldY;
            gameBoard[curX][curY].rotate();

        }
        gameBoard[curX][curY].rotationsNumber = startRotation;
    }


    //Determines is there any path from current valve to end tube.
	//This function  also creates an array list which contains every node of the path.
	//Returns true if path found, false otherwise.
	private boolean makePath(int startX,int startY)
	{
		path.clear();
		int curX=startX;
		int curY=startY;
       // Log.i("New Vertex:", curX + " " + curY);
		//Adding start
		path.add(new WaterPath(curX,curY, gameBoard[curX][curY].getTubesType(), Direction.NO_WAY,true, gameBoard[curX][curY].getRotation()));
		Direction weCameFrom= Direction.NO_WAY;

		while(true)
		{
			if(curX<0 || curX>=height || curY<0 || curY>=width)
				return false;


//			if(path.size()>=width*height)
//				return false;

			//dummy parameter
			weCameFrom=gameBoard[curX][curY].getDirection(weCameFrom);
			switch(weCameFrom)
			{
				case UP:
					curX--;
					break;
				case DOWN:
					curX++;
					break;
				case LEFT:
					curY--;
					break;
				case RIGHT:
					curY++;
					break;

				case NO_WAY:
					return false;


				//Cannot be in a normal state
				default:
					break;
			}
			boolean isFirtTime=true;
			for(WaterPath p: path )
				if(p.x==curX && p.y==curY)
				{
					isFirtTime=false;
					break;
				}
			if(curX<0 || curX>=height || curY<0 || curY>=width)
				return false;

			path.add(new WaterPath(curX,curY, gameBoard[curX][curY].getTubesType(),convertDirection(weCameFrom),isFirtTime, gameBoard[curX][curY].getRotation()));


			if(gameBoard[curX][curY].getTubeType()==TubeType.END)
				return true;


			weCameFrom=convertDirection(weCameFrom);
          //  Log.i("New Vertex:", curX + " " + curY + " We came from " + weCameFrom);
		}
		
		
	}
	
	//this function converts last move direction according to new square
	private Direction convertDirection(Direction lastMove)
	{
		switch(lastMove)
		{
			case UP:
				return Direction.DOWN;
			case DOWN:
				return Direction.UP;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			//Cannot be in a normal state
			default:
				return Direction.NO_WAY;
		}
	}

    public TouchStatus tryValve() {
        boolean pathFound= makePath(valveX, valveY);
        if(pathFound)
            return TouchStatus.PATH_FOUND;
        else
            return TouchStatus.NO_PATH_FOUND;
    }
	
	public TouchStatus makeTouch(int x,int y)
	{
		if(x<0 || x>=height || y<0 || y>=width)
			return TouchStatus.CANNOT_ROTATE;
		
	
		if(gameBoard[x][y].getTubeType()== TubeType.TUBE)
		{
			gameBoard[x][y].rotate();
			return TouchStatus.ROTATED;
		}
		
		if(gameBoard[x][y].getTubeType()==TubeType.END)
		{
			return TouchStatus.CANNOT_ROTATE;
		}
		
		if(gameBoard[x][y].getTubeType()==TubeType.VALVE)
		{
			
			boolean pathFound= makePath(x, y);
			if(pathFound)
				return TouchStatus.PATH_FOUND;
			else
				return TouchStatus.NO_PATH_FOUND;
		}
		
		
		//Dummy return
		return TouchStatus.CANNOT_ROTATE;		
	}
	
	
	public ArrayList<WaterPath> getPath()
	{
		return path;
	}
	
	public TubeShape getWorldPiece(int x,int y)
	{
		return gameBoard[x][y];
	}
	
	public void printGameTable()
	{
		
		
		System.out.println("-------------------------------");
		System.out.println();
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				System.out.print(gameBoard[i][j]+ " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("-------------------------------");
		
		
	}

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i= 0; i< height; i++) {
            for(int j = 0; j< width; j++) {
                out.append(gameBoard[i][j].toString());
            }
            out.append("\n");
        }
        return out.toString();
    }


}
