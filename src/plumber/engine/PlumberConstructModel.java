package plumber.engine;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class PlumberConstructModel {

    private int valveY;
    private int valveX;
    private int height,width;
    private TubeShape[][] gameBoard;
    private int[][] gameBoardOriginal;
    private ArrayList<WaterPath> path;


    public ArrayList<WaterPath> getRemovedPipes() {
        return removedPipes;
    }

    private ArrayList<WaterPath> removedPipes;
    private WaterPath[] removedPipesArray;
    private ArrayList<ArrayList<WaterPath>> solutions;




	public PlumberConstructModel(int[][] array, int h, int w)
	{
		height=h;
		width=w;
		gameBoard=new TubeShape[height][width];
        gameBoardOriginal = new int[height][width];

		path=new ArrayList<WaterPath>();
        removedPipes = new ArrayList<>();
        solutions = new ArrayList<>();
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
			{
                gameBoardOriginal[i][j] = array[i][j];
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

    public ArrayList<WaterPath> shortestPath(int removedCount) {

        ArrayList<WaterPath> bestSolution = null;
        findAllPaths(path, Direction.NO_WAY);

        if (solutions.size() == 0) {
            return null;
        }


        //Single solution only
        if (solutions.size() > 1) {
            //System.out.println("MANY_SOLUTIONS " + solutions.size());
            return null;
        }

        //Long solutions only
        if (solutions.get(0).size() < 8) {
           // System.out.println("SINGLE_SHORT_SOLUTION " + solutions.get(0).size());
            return null;
        }

//        for (WaterPath p : solutions.get(0)) {
//            if ( p.tube == Tubes.TUBE &&  p.rotation > 1) {
//                System.out.println("Sol size....");
//            }
//
//        }

       // System.out.println("SINGLE_SOLUTION " + solutions.size() + "  Length = " +  solutions.get(0).size());
    //    System.out.println("Total solutions : " + solutions.size());

        //Trying to remove random pipes
        boolean notSolved = true;
        int attemptsCount = 1;
        Random random = new Random();

        ArrayList<WaterPath> sol = new ArrayList<>();

        while(notSolved && (attemptsCount--) > 0) {
            ArrayList<WaterPath> singleSolution = new ArrayList<>(solutions.get(0));

            removedPipes.clear();
            sol.clear();

            //Creating available pipes, container and valve also available
            for (int i = 1; i < solutions.get(0).size() -1; i++ ) {
                //System.out.println("Adding element ։ " + solutions.get(0).get(i).tube);
                if(solutions.get(0).get(i).x == 0 || solutions.get(0).get(i).y == 0 ||
                solutions.get(0).get(i).x == height - 1 || solutions.get(0).get(i).y == width - 1) {
                  //  System.out.println("Skipping");
                    continue;
                }
//                if (solutions.get(0).get(i).tube == Tubes.TUBE && solutions.get(0).get(i).rotation > 1) {
//                    System.out.println("ALARM....");
//                }

                WaterPath element = new WaterPath(singleSolution.get(i));
                element.rotation =  solutions.get(0).get(i + 1).rotation;

                //Checking repeatations
                boolean isRepeated = false;
                for (int j = 0; j < sol.size(); j++) {
                    if (sol.get(j).x == element.x && sol.get(j).y == element.y) {
                        isRepeated = true;
                    }
                }

                if (!isRepeated) sol.add(element);

            }


            //Peeking random pipes
          //  System.out.println("Sol size ։ " + sol.size());
            if (sol.size() <= removedCount) {
                continue;
            }
            removedPipesArray = new WaterPath[removedCount];
            for (int i = 0; i < removedCount; i++) {
                int index = random.nextInt(sol.size());
               // System.out.println("Peeking random index ։ " + i);
              //  System.out.println("Peeking random element։ " + index);


                //Choosing the second one, not the same type as the first one
//                if (i == 1) {
//                    if (sol.get(index).tube == removedPipes.get(0).tube) {
//                        i--;
//                        continue;
//                    }
//
//                }
                removedPipes.add(sol.get(index));
                removedPipesArray[i] = sol.get(index);
                sol.remove(index);
            }

            Arrays.sort(removedPipesArray);
            //Printing removed
            boolean[][] imArray = new boolean[height][width];
            for (int i = 0; i< removedCount; i++) {
               // System.out.println("Removed " + removedPipes.get(i).tube + " " + removedPipes.get(i).rotation + " " + removedPipes.get(i).x + " " + removedPipes.get(i).y);
                //Making irotatable construct cells
                imArray[removedPipes.get(i).x][removedPipes.get(i).y] = true;
            }
            //Generating permutations
            int perIndex = 1;
          //  System.out.println("Best solution length = " + singleSolution.size());
          //  System.out.println("==============Original Table============");
            //printGameTable();

            int bestSolutionSize = singleSolution.size();
            bestSolution = singleSolution;

            do {
                int[][] tempBoard = new int[height][width];

                for(int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        tempBoard[i][j] = gameBoardOriginal[i][j];
                    }
                }

               // System.out.println("Changing board...");
                for (int r = 0; r < removedPipesArray.length; r++) {
                    //System.out.println("In " + removedPipes.get(r).x + " " + removedPipes.get(r).y + "  placing " + gameBoardOriginal[removedPipesArray[r].x][removedPipesArray[r].y] + " " + removedPipesArray[r].rotation );
                    // System.out.println("Original position " + removedPipesArray[r].x + " " + removedPipesArray[r].y);

                    tempBoard[removedPipes.get(r).x][removedPipes.get(r).y] = gameBoardOriginal[removedPipesArray[r].x][removedPipesArray[r].y] + removedPipesArray[r].rotation ;

                }
                for (int r = 0; r < removedPipesArray.length; r++) {
                    // System.out.println("In " + removedPipes.get(r).x + " " + removedPipes.get(r).y + "  placing " + gameBoardOriginal[removedPipesArray[r].x][removedPipesArray[r].y] );
                //    System.out.println("Original position " + removedPipesArray[r].x + " " + removedPipesArray[r].y);
                    //tempBoard[removedPipes.get(r).x][removedPipes.get(r).y] = gameBoardOriginal[removedPipesArray[r].x][removedPipesArray[r].y];
                }
                PlumberModelImmovable tempModel = new PlumberModelImmovable(tempBoard,imArray, height, width);
                // tempModel.printGameTable();
                ArrayList<WaterPath> shortest = tempModel.shortestPath();
                if (shortest == null) {
                 //   System.out.println("No solution");
                } else {
                    //System.out.println("NEW SOLUTION PERMUTATION: " + shortest.size());
                    if (shortest.size() < bestSolutionSize) {
                      //  System.out.println("New minimum");
                        bestSolution = shortest;
                        bestSolutionSize = shortest.size();
                        return null;
                    }
                }
              //  System.out.println("Best Solution Size is " + bestSolution.size());
                for (int i = 0; i < removedPipesArray.length; i++) {
                  //  System.out.println(removedPipesArray[i].x + " " + removedPipesArray[i].y);
                }
              //  System.out.println("-------------------------------------" + perIndex++);
            }
            while((removedPipesArray = (WaterPath[]) nextPermutation(removedPipesArray)) != null);





            }




        //Trying selecting removed pipes
       // System.out.println("Best Solution Size is " + bestSolution.size());
        return bestSolution;
    }

    private static Comparable[] nextPermutation( final Comparable[] c ) {
        // 1. finds the largest k, that c[k] < c[k+1]
        int first = getFirst( c );
        if ( first == -1 ) return null; // no greater permutation
        // 2. find last index toSwap, that c[k] < c[toSwap]
        int toSwap = c.length - 1;
        while ( c[ first ].compareTo( c[ toSwap ] ) >= 0 )
            --toSwap;
        // 3. swap elements with indexes first and last
        swap( c, first++, toSwap );
        // 4. reverse sequence from k+1 to n (inclusive)
        toSwap = c.length - 1;
        while ( first < toSwap )
            swap( c, first++, toSwap-- );
        return c;
    }

    // finds the largest k, that c[k] < c[k+1]
    // if no such k exists (there is not greater permutation), return -1
    private static int getFirst( final Comparable[] c ) {
        for ( int i = c.length - 2; i >= 0; --i )
            if ( c[ i ].compareTo( c[ i + 1 ] ) < 0 )
                return i;
        return -1;
    }

    // swaps two elements (with indexes i and j) in array
    private static void swap( final Comparable[] c, final int i, final int j ) {
        final Comparable tmp = c[ i ];
        c[ i ] = c[ j ];
        c[ j ] = tmp;
    }

    private void findAllPaths(ArrayList<WaterPath> currentPath, Direction cameFrom) {

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
