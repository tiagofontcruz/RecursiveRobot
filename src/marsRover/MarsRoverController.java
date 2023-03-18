package marsRover;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarsRoverController {
	
	private MarsRoverModel theModel;                                                                            //Declares the model object (core methods).
	private MarsRoverView theView;                                                                              //Declares the view object (graphic interface).
	private boolean correctRoot;                                                                                //Declares a boolean that verifies if the coordinates are valid. 
	private boolean correctGoal;                                                                                //Declares a boolean that verifies if the coordinates are valid.
	private static long start;                                                                                  //Declares a start number to measure the operation's nanoseconds. 
	private static long end;                                                                                    //Declares a end number to measure the operation's nanoseconds.
	private static long execution;                                                                              //Declares a calculation to measure the operation's nanoseconds.

	//CONSTRUCTOR **********************************************************************************************
	public MarsRoverController(MarsRoverModel theModel, MarsRoverView theView) {		                        
		this.theModel = theModel;                                                                               //Instantiates the model object (core methods).
		this.theView = theView;                                                                                 //Instantiates the view object (graphic interface).
		this.theView.loadTerrain(new LoadTerrain());                                    						//Call a method and instantiates a object to load file
		this.theView.solvePath(new SolvePath());                                                      			//Call a method and instantiates a object to save file
		this.theView.help(new HelpGame());                                                              		//Call a method and instantiates a object to use help menu		
		correctRoot = false;                                                                                    //Define the coordinate's validation as false (start).
		correctGoal = false;	                                                                                //Define the coordinate's validation as false (end).
	}	
	
	class LoadTerrain implements ActionListener {		
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method crates the terrain.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-11-01		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			theView.mapGenerationMessage("Click OK to generate the terrain.", "GENERATING TERRAIN...");			//Shows two messages. One in pop-up and other in a label.
			start = System.nanoTime();                                                                          //Starts to counter's time.
			theView.clearLblCredits();                                                                          //Clears the battery's credits on the screen.
			theModel.createTerrain();                                                                           //Generates the terrain.
			theView.repaintMap(theModel.getMapArray());                                                         //Paints the terrain in the screen.
			end = System.nanoTime();                                                                            //Ends the counter's time.
			execution = end - start;			                                                                //Calculates the time in nanoseconds.
			MarsRoverView.addTextToStatus("WAITING FOR INPUT...");                                              //Updates the status on the screen.
			theView.displayMessage("Execution time: " + execution + " nanoseconds");                            //Displays how many nanoseconds the operation take long.
			theView.enableSolvePath(true);                                                                      //Enables to send the coordinates in menu.
		}                                                                                                       
	}                                                                                                           

	class SolvePath implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method solver the Mars Rover's path.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-11-01		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			                                                                                                    
			//ORIGIN                                                                                            
			int rowRoot = 1;                                                                                    //Declares and initiates staring row.
			int colRoot = 1;				                                                                    //Declares and initiates staring column.
			String numberRoot = "";																				//Declares and initiates a auxiliary variable.
			do {                                                                                                
				correctRoot = false;	                                                                    
				do {				
					correctRoot = false;
					try {
						rowRoot = Integer.parseInt(theView.inputMessage("Row root coordinate:", numberRoot))-1; //Tries to input the starting row.
					} catch (Exception ex) {                                                                        
						correctRoot = true;
						theView.displayMessage("Use only numbers between 1 and 1024.\n"                             
								+ ex);                                                                              
					}                                                                                               
				}while(correctRoot);
				correctRoot = false;
				do {
					correctRoot = false;
					try {
						colRoot = Integer.parseInt(theView.inputMessage("Column root coordinate:", numberRoot))-1; //Tries to input the starting row.						
					} catch (Exception ex) {                                                                        
						correctRoot = true;
						theView.displayMessage("Use only numbers between 1 and 1024.\n"                             
								+ ex);                                                                              
					}                                                                                               
				}while(correctRoot);
				correctRoot = false;
				if (rowRoot >= 0 & rowRoot < MarsRoverModel.TERRAIN_SIZE && colRoot >= 0						//Checks if the coordinates are in the grid boundaries
						&& colRoot < MarsRoverModel.TERRAIN_SIZE													
					&&	theModel.getMap(rowRoot, colRoot) == MarsRoverModel.TRAVERSABLE) {			        	//and if it is a traversable position.
					theModel.setRowRoot(rowRoot);                                                           	//Sets the starting row.
					theModel.setColRoot(colRoot);                                                               //Sets the starting column.
					MarsRoverView.updateLblRowRootNumber(rowRoot + 1);                                          //Updates the row label.
					MarsRoverView.updateLblColRootNumber(colRoot + 1);                                          //Updates the column label.
					correctRoot = true;                                                                         
				}else {                                                                                         
					theView.displayMessage("Root coordinates are not set in a valid position.");			    
				}                                                                                                                                                                                
			}while(!correctRoot);                                                                               
			                                                                                                    
			//DESTINATION                                                                                       
			int rowGoal = MarsRoverModel.TERRAIN_SIZE - 1;                                                      //Declares and initiates ending row.
			int colGoal = MarsRoverModel.TERRAIN_SIZE - 1;                                                      //Declares and initiates ending column.
			do {				                                                                                
				correctGoal = false;                                                                            
				String numberGoal = "";                                                                         //Declares and initiates a auxiliary variable.
				do {	                                                                                        
					correctGoal = false;                                                                        
					try {					                                                                      
						rowGoal = Integer.parseInt(theView.inputMessage("Row goal coordinate:", numberGoal))-1; //Tries to input the ending row.
					} catch (Exception ex) {                                                                        
						correctGoal = true;
						theView.displayMessage("Use only numbers between 1 and 1024.\n"                             
								+ ex);                                                                              
					}                                                                                               
				}while(correctGoal);
				correctGoal = false;
				do {
					correctGoal = false;
					try {
						colGoal = Integer.parseInt(theView.inputMessage("Column goal coordinate:", numberGoal))-1; //Tries to input the ending row.
					} catch (Exception ex) {                                                                        
						correctGoal = true;
						theView.displayMessage("Use only numbers between 1 and 1024.\n"                             
								+ ex);                                                                              
					}                
				}while(correctGoal);
				correctGoal = false;
				if (rowGoal >= 0 & rowGoal < MarsRoverModel.TERRAIN_SIZE && colGoal >= 0                        //Checks if the coordinates are in the grid boundaries
						&& colGoal < MarsRoverModel.TERRAIN_SIZE                                                
					&&	theModel.getMap(rowGoal, colGoal) == MarsRoverModel.TRAVERSABLE                         //and if it is a traversable position.
					&& rowRoot < rowGoal && colRoot < colGoal) {                                                //Also checks if the starting coordinates are lesser then 
                                                                                                                //the ending coordinates.
					theModel.setGoal(rowGoal, colGoal);                                                         //Insert the ending position at once because of the ArrayList.
					                                                                                            
					MarsRoverView.updateLblRowGoalNumber(rowGoal + 1);                                          //Updates the row label.
					MarsRoverView.updateLblColGoalNumber(colGoal + 1);                                          //Updates the column label.
					correctGoal = true;                                                                         
				}else {                                                                                         
					theView.displayMessage("Goal coordinates are not set in a valid position.");                
				}                                                                                               
			}while(!correctGoal);                                                                               
			                                                                                                    
			theView.enableSolvePath(false);                                                                     //Enables again to set the coordinates.
			                                                                                                    
			//EXECUTE                                                                                           
			if (correctRoot && correctGoal) {                                                                   //Checks if the coordinates are correct.
				theModel.setBatteryCredits(10000);                                                              //Resets the battery's credits.
				theView.mapGenerationMessage("Click OK to compute the data.", "COMPUTING DATA...");             //Shows two messages. One in pop-up and other in a label.
				start = System.nanoTime();                                                                      //Starts to counter's time.
				int index = 0;                                                                                  //Declares and initiates the index to run in the loop.
				if (theModel.pathFinder(theModel.getRowRoot(), theModel.getColRoot())) {                        //Tests the path finder calling the recursive method.
					end = System.nanoTime();                                                                    //Ends to counter's time.
					execution = end - start;					                                                //Calculates the time in nanoseconds.
					theView.mapGenerationMessage("Execution time: " + execution                                 //Shows two messages. One in pop-up and other in a label.
							+ " nanoseconds", "PRINTING DATA...");                                              
                                                                                                                
					theModel.setGoal(rowGoal, colGoal);					                                        //Insert the ending position at once because of the ArrayList.
					for(index = 0; index < theModel.getRoversPath().size(); index++) {                          //Runs a loop to animate the graphics.
						theView.animatePath(theModel.getMapArray(), theModel.getRoversPath(), index);			//Calls the method to animate the graphics. 
						theView.addTextToCredits(theModel.getBatteryCredits());						            //Updates the battery's credits.
					}                                                                                           
					MarsRoverView.addTextToStatus("WAITING FOR INPUT...");                                      //Updates the status on the screen.
				}else {                                                                                         
					end = System.nanoTime();                                                                    //Ends to counter's time. 
					execution = end - start;					                                                //Calculates the time in nanoseconds.
					theView.mapGenerationMessage("Execution time: " + execution                                 //Shows two messages. One in pop-up and other in a label.
							+ " nanoseconds", "PRINTING DATA...");                                              
					theModel.setGoal(rowGoal, colGoal);                                                         //Insert the ending position at once because of the ArrayList.
					for(index = 0; index < theModel.getRoversPath().size(); index++) {                          //Runs a loop to animate the graphics.          
						theView.animatePath(theModel.getMapArray(), theModel.getRoversPath(), index);			//Calls the method to animate the graphics.     
						theView.addTextToCredits(theModel.getBatteryCredits());						            //Updates the battery's credits.                
					}                                                                                           
					theView.displayMessage("Destination not reached successfully.\nClick OK");                  //Displays a message in a pop-up.
				}				                                                                                
			}else {			                                                                                    
				theView.displayMessage("Something went wrong!");                                                //Displays a message in a pop-up.
			}                                                                                                   
			MarsRoverView.addTextToStatus("WAITING FOR INPUT...");			                                    //Updates the status on screen.
		}                                                                                                       
	}
	
	class HelpGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method displays the software's instructions.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-09-21		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			theView.displayMessage("- In order to Mars Rover find the path you must generate a terrain.\n"      //Display a message in Menu "Help" option
					+ "- To generate a terrain you must load the terrain in Map -> Load the terrain.\n"                                           
					+ "- To find the path you must send the coordinates.\n"					                    
					+ "- The Goal's coordinates must be greater than the Root's coordinates.\n"
					+ "- You can use only once each random map.");                     
		}		
	}
}
