package plumber.engine;


import mobiloids.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class PlumberPressureModel {

    private int valveY;
    private int valveX;
    private int height,width;
    private TubeShape[][] gameBoard;
    private ArrayList<ArrayList<WaterPath>> flow;
    //private ArrayList<ArrayList<WaterPath>> solutions;
    private int valvesNumber;
    private ArrayList<ArrayList<ArrayList<WaterPath>>> allSolutions;
    private Pair<Integer, Integer> container;
    private ArrayList<Pair<Integer, Integer>> valves;
    private boolean[][] usageArray;
    private ArrayList<WaterPath> path;
    private Stack<TubeShape[][]> history;
    private ArrayList<ArrayList<WaterPath>> singleValveSolutions;

    public PlumberPressureModel(int[][] array, int h, int w, int valvesNumber, Pair<Integer, Integer> container, ArrayList<Pair<Integer, Integer>> valves)
	{
        this.valvesNumber = valvesNumber;
        this.container = container;
        this.valves = new ArrayList<>(valves);
        this.flow = new ArrayList<>();
		height=h;
		width=w;
        history = new Stack<>();
		gameBoard=new TubeShape[height][width];
        usageArray = new boolean[height][width];
		path=new ArrayList<WaterPath>();
        singleValveSolutions = new ArrayList<>();
        //solutions = new ArrayList<>();
        allSolutions = new ArrayList<>();
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
                        //valves.add(new mobiloids.Pair<>(i, j));
                        //valveX = i;
                        //valveY = j;
                        //path.add(new WaterPath(valveX, valveY, Tubes.VALVE, Direction.NO_WAY, true, rotation));
                        //System.out.println("Valve found");
						break;
						
					case 6:
						gameBoard[i][j]=new EndTube(rotation);
                        container = new Pair<>(i, j );
                        //System.out.println("container " + i + "  " +  j);
						break;

                    case 7:
                        gameBoard[i][j]=new EmptyTube(rotation);
                        break;
				}
				
				
			}
       // System.out.println("container size = " + containers.size());
	}


    public void resetGameBoard() {


        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
            {
                if (gameBoard[i][j].getTubeType() == TubeType.VALVE) {
                    continue;
                }
                if (!usageArray[i][j]) {
                    gameBoard[i][j].rotationsNumber = 0;
                }
            }

    }
    public ArrayList<ArrayList<WaterPath>> findAllShortestPaths() {
        //System.out.println("-------------------------------");
        ArrayList<ArrayList<WaterPath>> path = null;
        ArrayList<ArrayList<WaterPath>> allSolutions = new ArrayList<>();
       // for (int i = 0; i < valvesNumber; i++) {
            //solutions.clear();
            //System.out.println("Searching shortest paths");
            path  = shortestPath(0);
           // System.out.println("Found shortest paths");
            if (path == null) {
                return null;
            }
            //System.out.println("Path..            resetGameBoard();
        //}
        return path;
    }

    public ArrayList<ArrayList<WaterPath>> shortestPath (int valveIndex) {


        //path = new ArrayList<>();
        int pathX = valves.get(valveIndex).getKey();
        int pathY = valves.get(valveIndex).getValue();
        path.add(new WaterPath(pathX, pathY, Tubes.VALVE, Direction.NO_WAY, true, gameBoard[pathX][pathY].getRotation()));
        findAllPaths(path, Direction.NO_WAY, valveIndex);
        if (allSolutions.size() == 0) {
            return null;
        }

        //ToDo only unique solutions
        if (allSolutions.size() != 1) {
            return null;
        }





        //Checking incorrect solutions
        HashMap<Integer, Integer> rotations = new HashMap<>();

        System.out.println("Total solutions : " + allSolutions.size());
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        int length = 0;
        for (int i = 0; i < allSolutions.size(); i++) {
            length = 0;
            for (ArrayList<WaterPath> wp : allSolutions.get(i)) {
                //Checking too short paths
                if (wp.size() < 5) {
                    return null;
                }
                length += wp.size();
            }
            System.out.println("Solution  : " + i + " size= " + length);
            if (length < min) {
                min = length;
                minIndex = i;
            }
        }



        //ToDo checking multi usage
        boolean multiUse = false;

        int usageArray[][] = new int[height][width];


        mainLoop:
        for (int i = 0; i < allSolutions.get(0).size(); i++) {
            ArrayList<WaterPath> wp = allSolutions.get(0).get(i);
            for (WaterPath w : wp) {
                if (w.tube == Tubes.END_TUBE) {
                    continue;
                }
                if (usageArray[w.x][w.y] == 0) {
                    usageArray[w.x][w.y] = i + 1;
                }else {
                    if (usageArray[w.x][w.y] != i + 1) {
                        multiUse = true;
                        System.out.println("Multi use : " + w.x + " " + w.y);
                        //break mainLoop;
                    }
                }
            }
        }

        if (!multiUse) {
            return null;
        }

        int usageArrayRotation[][] = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                usageArrayRotation[i][j] = -1;
            }
        }
        boolean wrongRotation = false;
        secondLoop:
        for (int i = 0; i < allSolutions.get(0).size(); i++) {
            ArrayList<WaterPath> wp = allSolutions.get(0).get(i);
            for (WaterPath w : wp) {

                if (usageArrayRotation[w.x][w.y] == -1) {
                    usageArrayRotation[w.x][w.y] = w.rotation;
                } else {
                    if (usageArrayRotation[w.x][w.y] != w.rotation) {
                        wrongRotation = true;
                        break secondLoop;
                    }

                }
            }
        }


        if (wrongRotation) {
            System.out.println("Wrong rotation");
            return null;
        }


        System.out.println("Best solutions : " + min);
       return allSolutions.get(minIndex);
    }

    private void findAllPaths(ArrayList<WaterPath> currentPath, Direction cameFrom, int valveIndex) {

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

        if (usageArray[curX][curY]) {
            rotationsLimit = 1;
        }
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
            if(gameBoard[curX][curY].getTubeType() == TubeType.END) {
                System.out.println("End " + curX + " " + curY + " " + weCameFrom);
            }

            ArrayList<WaterPath> newPath = new ArrayList<>(currentPath);
//            System.out.println("New_PATH : " + newPath);
//            System.out.println("New_TUBETYPE : " + gameBoard[curX][curY].getTubesType());
            newPath.add(new WaterPath(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(weCameFrom), isFirstTime, gameBoard[oldX][oldY].getRotation()));

            weCameFrom = convertDirection(weCameFrom);
            if (gameBoard[curX][curY].getTubeType() == TubeType.END) {

                   System.out.println("Found New Container: " + valveIndex + " " + curX + " " + curY);
                   //solutions.add(newPath);
                   holdPath(newPath);
                   //The last path was found
                   if (valveIndex == valvesNumber - 1) {
                       addSolution();
                       removePath();

                   } else {
                       path.clear();
                       int pathX = valves.get(valveIndex + 1).getKey();
                       int pathY = valves.get(valveIndex + 1).getValue();
                       path.add(new WaterPath(pathX, pathY, Tubes.VALVE, Direction.NO_WAY, true, gameBoard[pathX][pathY].getRotation()));
                       //System.out.println("Searching container: " + (valveIndex + 1));
                       history.push(gameBoard);
                       findAllPaths(path, Direction.NO_WAY, valveIndex + 1);
                       removePath();
                       gameBoard = history.pop();
                      // System.out.println("Returning to container: " + valveIndex);
                      // resetGameBoard();

                   }
            } else {
                findAllPaths(newPath, weCameFrom, valveIndex);
            }
            curX = oldX;
            curY = oldY;
            gameBoard[curX][curY].rotate();

        }
        gameBoard[curX][curY].rotationsNumber = startRotation;


    }

    private void addSolution() {
//        System.out.println("Solution Found");
//        for ( ArrayList<WaterPath> p : flow) {
//            System.out.println(p.size());
//        }
        if (flow.size() == valvesNumber) {
            allSolutions.add(new ArrayList<>(flow));
        }
    }

    private void holdPath(ArrayList<WaterPath> newPath) {

        flow.add(newPath);
      //  System.out.println("Hold Path" + flow.size());
        for(WaterPath w : newPath) {
            usageArray[w.x][w.y] = true;
        }
    }

    private void removePath() {
        ArrayList<WaterPath> removedPath = flow.remove(flow.size() - 1);
      //  System.out.println("Remove Path " + flow.size());
        for(WaterPath w : removedPath) {
            usageArray[w.x][w.y] = false;
        }
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


    public int findSingleValvePathsCount(int containerIndex) {

        path.clear();
        singleValveSolutions.clear();
        int pathX = valves.get(containerIndex).getKey();
        int pathY = valves.get(containerIndex).getValue();
        path.add(new WaterPath(pathX, pathY, Tubes.VALVE, Direction.NO_WAY, true, gameBoard[pathX][pathY].getRotation()));
        findAllSingleValvePaths(path, Direction.NO_WAY);
        return singleValveSolutions.size();
    }

    private void findAllSingleValvePaths(ArrayList<WaterPath> currentPath, Direction cameFrom) {

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
                singleValveSolutions.add(newPath);
            } else {
                findAllSingleValvePaths(newPath, weCameFrom);
            }
            curX = oldX;
            curY = oldY;
            gameBoard[curX][curY].rotate();

        }
        gameBoard[curX][curY].rotationsNumber = startRotation;
    }
}
