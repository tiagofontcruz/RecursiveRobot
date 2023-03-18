package marsRover;

import java.util.ArrayList;
import java.util.Random;

class MarsRoverModel {
	
	private char[][] map;                                                           //Declares the multidimensional array of characters for the map (terrain).
	private int[] insertCoordenates;                                                //Declares the array to insert two positions (row and Column) in the ArrayList.
	private ArrayList<int[]> roversPath;                                            //Declares the arrayList to save the visited spots.
	private boolean isTraversable;                                                  //Declares the data member to store the validation of the recursive method.
	private int batteryCredits;		                                                //Declares the data member to store battery's credits. 
	private int rowRoot;                                                            //Declares the data member to store starting row position.
	private int colRoot;                                                            //Declares the data member to store starting column position.
	private int rowGoal;                                                            //Declares the data member to store final row position.
	private int colGoal;                                                            //Declares the data member to store final column position.
	private int rowBounder;
	private int colBounder;
	public static final float DENSITY = 0.1f;                                       //Declares and initializes the constant to store the density of the obstacles. 
	public static final int TERRAIN_SIZE = 1024;                                    //Declares and initializes the constant to store the size (square) of the map.
	public static final char NON_TRAVERSABLE = '◘';                                 //Declares and initializes the constant to store the non-traversable character.
	public static final char TRAVERSABLE = '◌';                                     //Declares and initializes the constant to store the traversable character.
	public static final char VISITED = '○';                                         //Declares and initializes the constant to store the visited character.
	public static final char PATH = '●';                                            //Declares and initializes the constant to store the path character.
	public static final char GOAL = '◙'; 	                                        //Declares and initializes the constant to store the goal character.
	
	//CONSTRUCTOR ******************************************************************
	public MarsRoverModel(){                                                        
		batteryCredits = 10000;		                                                
		map = new char[TERRAIN_SIZE][TERRAIN_SIZE];		                            
	}                                                                               
	                                                                                
	public void createTerrain() {                                                   
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	createTerrain()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method creates an array multidimensional with the terrain.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		batteryCredits = 10000;                                                     //Initializes the battery credits.
		map = new char[TERRAIN_SIZE][TERRAIN_SIZE];                                 //Instantiates the map.
		roversPath = new ArrayList<>();                                             //Instantiates the the visited spots map.
		Random rng = new Random();                                                  //Declares and instantiates a random variable. 
		char character = TRAVERSABLE;                                               //Declares and initializes a variable for the characters.
		double rand = 0;                                                            //Declares and initializes a variable that receives the random value.
		int row = 0;                                                                //Declares and initializes the row.
		int col = 0;                                                                //Declares and initializes the column.
		for (row = 0; row < TERRAIN_SIZE; row++) {                                  //Loops the map's row.
			for (col = 0; col < TERRAIN_SIZE; col++) {                              //Loops the map's column.
				rand = rng.nextDouble();                                            //Generates a random value between 0.0 and 1.0 and stores into a variable.
				if (rand < DENSITY) {                                               //Checks if the random number is lower then the density.
					character = NON_TRAVERSABLE;                                    //Stores the non traversable character into the a variable.
				} else {                                                            //else
					character = TRAVERSABLE;                                        //Stores the traversable character into the a variable.
				}                                                                   
				map[row][col] = character;                                          //Insert the character into the array's map (terrain).
			}                                                                       
		}                                                                           
	}                                                                               
	                                                                                
	private boolean isFinished(int rowCurrent, int colCurrent) {                    
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	isFinished()
		//
		// Method parameters	:	int rowCurrent, int colCurrent
		//
		// Method return		:	boolean
		//
		// Synopsis				:   This method verifies if the rover arrived in the Goal position.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if (rowCurrent == rowGoal && colCurrent == colGoal) {			            //Checks if the current position of the rover arrived in the goal.
			return true;                                                            
		}                                                                           
		return false;                                                               
	}                                                                               
                                                                                    
	private boolean isValid(int rowCurrent, int colCurrent) {                       
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	isValid()
		//
		// Method parameters	:	int rowCurrent, int colCurrent
		//
		// Method return		:	boolean
		//
		// Synopsis				:   This method validates the current position of the rover, making sure that it is not out of the boundaries.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if (rowCurrent >= 0 && rowCurrent <= rowBounder                             //Checks if the rover is inside the boundaries of the array.
			&& colCurrent >= 0 && colCurrent <= colBounder) {                          
			if(getMap(rowCurrent, colCurrent) == TRAVERSABLE                        //Checks if the rover is in a traversable position,
					|| getMap(rowCurrent, colCurrent) == GOAL) {				    //or if the position is the goal position.
				return true;                                                        
			}                                                                       
		}                                                                           
		return false;                                                               
	}                                                                               
		                                                                            
	public boolean pathFinder(int rowCurrent, int colCurrent) {                     
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	pathFinder()
		//
		// Method parameters	:	int rowCurrent, int colCurrent
		//
		// Method return		:	boolean
		//
		// Synopsis				:   This method updates the position of the rover into a multidimensional array.							
		//							Also, uses a auxiliary ArrayList to save the visited spots.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				Recursive method using backtracking.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if (isValid(rowCurrent, colCurrent)) {			                            //Checks if is a valid position.
			if(batteryCredits > 0) {                                                //Checks if the rover is not running out of battery. 
				decrementBaterry();                                                 //Decreases the battery's credits in each movement.
			}else {				                                                    
				return false;                                                       
			}                                                                       
			insertCoordenates = new int[]{rowCurrent, colCurrent};                  //Inserts the coordinates into the an auxiliary array.
			if (isFinished(rowCurrent, colCurrent)) {                               //Checks if the rover arrive to the goal spot.
				roversPath.add(insertCoordenates);                                  //Inserts the coordinates into the arrayList to save the visited spot. 
				setMap(rowCurrent, colCurrent, PATH);				                //Inserts the path character in the map.
				return true;                                                        
			}else {                                                                 //else
				setMap(rowCurrent, colCurrent, VISITED);	                        //Inserts the visited character in the map.
				roversPath.add(insertCoordenates);                                  //Inserts the coordinates into the arrayList to save the visited spot.
			}                                                                       
                                                                                    
			if (rowGoal - rowCurrent < colGoal - colCurrent) {                      //Checks if the row goal is lesser then the column goal, 
																					//to decide if goes right or down first.
				isTraversable = pathFinder(rowCurrent, colCurrent + 1);				//Goes right.

				if (!isTraversable) {                                               //Checks if cannot go to the right.
					roversPath.add(insertCoordenates);                              //Inserts the coordinates into the arrayList to save the visited spot.
					isTraversable = pathFinder(rowCurrent + 1, colCurrent);         //Goes down.
				}						                                            
			}else {                                                                 //else
				isTraversable = pathFinder(rowCurrent + 1, colCurrent);             //Goes down.

				if (!isTraversable) {                                               //Checks if cannot go down.
					roversPath.add(insertCoordenates);                              //Inserts the coordinates into the arrayList to save the visited spot.
					isTraversable = pathFinder(rowCurrent, colCurrent + 1);         //Goes right.
				}                                                                   
			}                                                                       
			
			if (!isTraversable) {                                               	//Checks if cannot go to the right.
				roversPath.add(insertCoordenates);                              	//Inserts the coordinates into the arrayList to save the visited spot.
				isTraversable = pathFinder(rowCurrent, colCurrent - 1);         	//Goes left.
			}                                                                  		
			
			if (!isTraversable) {                                               	//Checks if cannot go left.
				roversPath.add(insertCoordenates);                              	//Inserts the coordinates into the arrayList to save the visited spot.
				isTraversable = pathFinder(rowCurrent - 1, colCurrent);         	//Goes up.
			}                                                                   	
                                                                                    
			if(isTraversable){				                                        //Checks if it could go in any direction.
				setMap(rowCurrent, colCurrent, PATH);                               //Inserts the path character in the map.
			} else {                                                                //else
				roversPath.add(insertCoordenates);                                  //Inserts the coordinates into the arrayList to save the visited spot.
			}                                                                       
			return isTraversable;                                                   
		}	                                                                        
		roversPath.remove(roversPath.size() - 1);                                   //Removes the last position (goal position) in the rover's path.
		return false;                                                               
	}
	
	//GETTERS AND SETTERS **********************************************************	
	public int getRowRoot() {
		return rowRoot;
	}

	public void setRowRoot(int rowRoot) {
		this.rowRoot = rowRoot;
	}

	public int getColRoot() {
		return colRoot;
	}

	public void setColRoot(int colRoot) {
		this.colRoot = colRoot;
	}

	public int getRowGoal() {
		return rowGoal;
	}

	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}

	public int getColGoal() {
		return colGoal;
	}

	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}

	public int getBatteryCredits() {
		return batteryCredits;
	}

	public void setBatteryCredits(int batteryCredits) {		
		this.batteryCredits = batteryCredits;
	}
	
	public void decrementBaterry() {
		batteryCredits -= 1;
	}
	public ArrayList<int[]> getRoversPath() {
		return roversPath;
	}

	public char getMap(int row, int col) {		
		return map[row][col];
	}
	
	public void setMap(int row, int col, char character) {		
		map[row][col] = character;
	}
	
	public char[][] getMapArray() {
		return map;
	}
	
	public void setGoal(int rowGoal, int colGoal) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setGoal()
		//
		// Method parameters	:	int rowCurrent, int colCurrent
		//
		// Method return		:	void
		//
		// Synopsis				:   This method sets the goal and allows the rover to go around the goal by trying all sides 							
		//							to get there if the goal is isolated. 
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int bounderMargin = 2;
		this.rowGoal = rowGoal;
		this.colGoal = colGoal;
		this.map[rowGoal][colGoal] = MarsRoverModel.GOAL;
		//CHECK THE BOUNDERIES
		if ((rowGoal + bounderMargin) < TERRAIN_SIZE - 1) {
			rowBounder = rowGoal + bounderMargin;
		}else {
			rowBounder = rowGoal + ((TERRAIN_SIZE - 1) - rowGoal);
		}
		if ((colGoal + bounderMargin) < TERRAIN_SIZE - 1) {
			colBounder = colGoal + bounderMargin;
		}else {
			colBounder = colGoal + ((TERRAIN_SIZE - 1) - colGoal);
		}
	}
}
