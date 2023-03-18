package marsRover;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;

public class MarsRoverView extends JFrame {

	//DECLARATION OF CONTAINERS AND COMPONENTS ******************************************************* 	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private static JMenuBar menuBar;
	private static JMenu TerrainMenu;
	private static JMenu helpMenu;
	
	private static JMenuItem loadTerrain;
	private static JMenuItem solvePath;
	private static JMenuItem exit;
	private static JMenuItem helpGame;	
	
	private static JLabel lblCoordenates; 
	private static JLabel lblRowRoot;
	private static JLabel lblRowRootNumber;
	private static JLabel lblColRoot;
	private static JLabel lblColRootNumber;
	private static JLabel lblRowGoal;
	private static JLabel lblRowGoalNumber;
	private static JLabel lblColGoal;
	private static JLabel lblColGoalNumber;	
	private static JLabel lblBatteryCredits;
	private static JLabel lblCredits;
	private static JLabel lblNonTraversableInfo;
	private static JLabel lblTraversableInfo;
	private static JLabel lblVisitedInfo;
	private static JLabel lblPathInfo;
	private static JLabel lblStatus;
	
	//DECLARATION OF VARIABLES AND CONSTANTS *************************************************************
	private static final int MAX_DISPLAY_SIZE = 64;
	private static final int SQUARE_WiDTH = 15;
	private static final int SQUARE_HEIGHT = 15;
	private static final int SQUARE_X = 15;
	private static final int SQUARE_Y = 15;
	private static int xOffSet;
	private static int yOffSet; 
	
	public MarsRoverView() {
		
		this.setTitle("Mars Rover Pathfinder");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(contentPane);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		//DEFINITION OF CONTAINERS AND COMPONENTS **********************************************************
		menuBar = new JMenuBar();
		
		TerrainMenu = new JMenu("Map");
		helpMenu = new JMenu("Help");
		
		menuBar.add(TerrainMenu);
		menuBar.add(helpMenu);	
		
		loadTerrain = new JMenuItem("Load the terrain");
		solvePath = new JMenuItem("Send the coordinates");
		exit = new JMenuItem("Exit");
		helpGame = new JMenuItem("How it works");
				
		TerrainMenu.add(loadTerrain);
		TerrainMenu.add(solvePath);
		TerrainMenu.add(exit);
		helpMenu.add(helpGame);
		
		setJMenuBar(menuBar);						
		
		contentPane = new JPanel();
		getContentPane().add(contentPane);		
		contentPane.setLayout(null);
		
		lblCoordenates = new JLabel("Coordenates");
		lblCoordenates.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCoordenates.setBounds(20, 36, 199, 26);
		contentPane.add(lblCoordenates);
		
		lblRowRoot = new JLabel("Row Root:");
		lblRowRoot.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRowRoot.setBounds(20, 83, 93, 26);
		contentPane.add(lblRowRoot);	
		
		lblRowRootNumber = new JLabel("");
		lblRowRootNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRowRootNumber.setBounds(106, 83, 73, 26);
		contentPane.add(lblRowRootNumber);
		
		lblColRoot = new JLabel("Col Root:");
		lblColRoot.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblColRoot.setBounds(20, 103, 73, 26);
		contentPane.add(lblColRoot);	
		
		lblColRootNumber = new JLabel("");
		lblColRootNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblColRootNumber.setBounds(106, 103, 73, 26);
		contentPane.add(lblColRootNumber);
		
		lblRowGoal = new JLabel("Row Goal:");
		lblRowGoal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRowGoal.setBounds(20, 143, 73, 26);
		contentPane.add(lblRowGoal);	
		
		lblRowGoalNumber = new JLabel("");
		lblRowGoalNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRowGoalNumber.setBounds(106, 143, 73, 26);
		contentPane.add(lblRowGoalNumber);
		
		lblColGoal = new JLabel("Col Goal:");
		lblColGoal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblColGoal.setBounds(20, 163, 73, 26);
		contentPane.add(lblColGoal);	
		
		lblColGoalNumber = new JLabel("");
		lblColGoalNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblColGoalNumber.setBounds(106, 163, 73, 26);
		contentPane.add(lblColGoalNumber);
		
		lblBatteryCredits = new JLabel("Battery Credits: ");
		lblBatteryCredits.setFont(new Font("Tahoma", Font.BOLD, 16));		
		lblBatteryCredits.setBounds(20, 300, 150, 26);
		contentPane.add(lblBatteryCredits);
		
		lblCredits = new JLabel("10000");
		lblCredits.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCredits.setForeground(Color.BLUE);
		lblCredits.setBounds(20, 330, 150, 26);
		contentPane.add(lblCredits);
		
		lblNonTraversableInfo = new JLabel("\t\tNON_TRAVERSABLE = BLACK");
		lblNonTraversableInfo.setFont(new Font("Tahoma", Font.BOLD, 16));		
		lblNonTraversableInfo.setBounds(20, 394, 325, 26);
		contentPane.add(lblNonTraversableInfo);
		
		lblTraversableInfo = new JLabel("\t\tTRAVERSABLE = WHITE");
		lblTraversableInfo.setFont(new Font("Tahoma", Font.BOLD, 16));		
		lblTraversableInfo.setBounds(20, 414, 275, 26);
		contentPane.add(lblTraversableInfo);
		
		lblVisitedInfo = new JLabel("\t\tVISITED = CYAN");
		lblVisitedInfo.setFont(new Font("Tahoma", Font.BOLD, 16));		
		lblVisitedInfo.setBounds(20, 434, 175, 26);
		contentPane.add(lblVisitedInfo);
		
		lblPathInfo = new JLabel("\t\tPATH = YELLOW");
		lblPathInfo.setFont(new Font("Tahoma", Font.BOLD, 16));		
		lblPathInfo.setBounds(20, 454, 175, 26);
		contentPane.add(lblPathInfo);
		
		lblStatus = new JLabel("WAITING FOR INPUT...");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(20, 554, 195, 26);
		contentPane.add(lblStatus);		
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
	}
	
	//GETTERS AND SETTERS **********************************************************	
	public void mapGenerationMessage(String errorMessage, String status) {
		MarsRoverView.addTextToStatus(status);
		JOptionPane.showMessageDialog(this, errorMessage);									
	}
	
	public void displayMessage(String errorMessage) {	
		JOptionPane.showMessageDialog(this, errorMessage);									
	}
	
	public void addTextToCredits(int credits) {			
		lblCredits.setText(credits + " units");
	}
	
	public static void addTextToStatus(String status) {			
		lblStatus.setText(status);
	}
	
	public void clearLblCredits() {			
		lblCredits.setText("10000 units");
	}
	
	public void enableSolvePath(boolean status) {
		solvePath.setEnabled(status);
	}
		
	public static void updateLblRowRootNumber(int rowRootNumber) {
		lblRowRootNumber.setText(rowRootNumber + "");
	}
	
	public static void updateLblColRootNumber(int colRootNumber) {
		lblColRootNumber.setText(colRootNumber + "");
	}

	public static void updateLblRowGoalNumber(int rowGoalNumber) {
		lblRowGoalNumber.setText(rowGoalNumber + "");
	}
	
	public static void updateLblColGoalNumber(int colGoalNumber) {
		lblColGoalNumber.setText(colGoalNumber + "");
	}

	public String inputMessage(String message, String inputM) {	
		inputM = JOptionPane.showInputDialog(this, message);
		return inputM;
	}
	
	public void paint(Graphics g) {
		super.paint(g);		
	}
	
	public void repaintMap(char[][] path) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	repaintMap()
		//
		// Method parameters	:	char[][] path
		//
		// Method return		:	void
		//
		// Synopsis				:   This method paints an array multidimensional with the terrain on the screen.							
		//							Also, paints the rover's path.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		paint(getGraphics());		
		int[] roverPosition = new int[2];
		roverPosition[0] = 0;
		roverPosition[1] = 0;
		drawMap(getGraphics(), path, roverPosition);
	}
	
	public void animatePath(char[][] path, ArrayList<int[]> roversPath, int index) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	animatePath()
		//
		// Method parameters	:	char[][] path, ArrayList<int[]> roversPath, int index
		//
		// Method return		:	void
		//
		// Synopsis				:   This method animates an array multidimensional with the terrain on the screen.							
		//							Also, animates the rover's path.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=		
		drawMap(getGraphics(), path, roversPath.get(index));
	}
	
	public void drawMap(Graphics g, char[][] path, int[] roverPosition) {		
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	drawMap()
		//
		// Method parameters	:	Graphics g, char[][] path, int[] roverPosition
		//
		// Method return		:	void
		//
		// Synopsis				:   This method animates an array multidimensional with the terrain on the screen.							
		//							Also, animates the rover's path.
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int row = 0;																							//Declares and initiates the row.
		int col = 0;                                                                                            //Declares and initiates the column.
		xOffSet = roverPosition[0];                                                                             //Collects to the x offset the first position of the array.
		yOffSet = roverPosition[1];                                                                             //Collects to the y offset the second position of the array.
		if (xOffSet >= path.length - (MAX_DISPLAY_SIZE / 2)) {			                                        //Checks if the offset is outside the display's boundaries. 
			xOffSet = path.length - (MAX_DISPLAY_SIZE / 2);                                                     //Sets the offset to the correct position.
			                                                                                                    
		}else if (xOffSet <= MAX_DISPLAY_SIZE / 2) {			                                                //Checks if the x offset is inside the display's boundaries.
			xOffSet = MAX_DISPLAY_SIZE / 2;                                                                     //Sets the x offset the current position.
		}                                                                                                       //
		                                                                                                        //
		if (yOffSet >= path.length - (MAX_DISPLAY_SIZE / 2)) {			                                        //Checks if the y offset is outside the display's boundaries. 
			yOffSet = path.length - (MAX_DISPLAY_SIZE / 2);                                                     //Sets the y offset to the correct position.                  
			                                                                                                                                                                
		} else if (yOffSet <= MAX_DISPLAY_SIZE / 2) {			                                                //Checks if the offset is inside the display's boundaries.  
			yOffSet = MAX_DISPLAY_SIZE / 2;                                                                     //Sets the offset the current position.                     
		}                                                                                                       
		                                                                                                        
		xOffSet -= MAX_DISPLAY_SIZE / 2;                                                                        //Updates in this case 32 squares from x offset.
		yOffSet -= MAX_DISPLAY_SIZE / 2;                                                                        //Updates in this case 32 squares from y offset.
		                                                                                                        
		g.translate(350, 55);                                                                                   //Translates the map in the x and y coordinates on the form.
		for (row = xOffSet; row < MAX_DISPLAY_SIZE + xOffSet; row++) {                                          //Loops the x offset to update the drawing of the squares.
			for (col = yOffSet; col < MAX_DISPLAY_SIZE + yOffSet; col++) {                                      //Loops the y offset to update the drawing of the squares.
				Color color;	                                                                                //Declares the square color.
				switch (path[row][col]) {                                                                       //Changes the color according to the symbol in the position.
					case MarsRoverModel.NON_TRAVERSABLE:                                                        
						color = Color.BLACK;                                                                    
					break;                                                                                      
					case MarsRoverModel.GOAL:                                                                   
						color = Color.RED;                                                                      
					break;                                                                                      
					case MarsRoverModel.VISITED:	                                                            
						color = Color.CYAN;                                                                     
					break;                                                                                      
					case MarsRoverModel.PATH:                                                                   
						color = Color.YELLOW;                                                                   
					break;                                                                                      
					default:                                                                                    
						//TRAVERSABLE                                                                           
						color = Color.WHITE;                                                                    
				}                                                                                               
				if (row == roverPosition[0] && col == roverPosition[1]) {	                                    //Checks the position of the Rover.
					color = Color.GREEN;                                                                        //This is the color of the Rover.
				}
				//DRAWING THE SQUARES
				g.setColor(color);                                                                              //Sets the color.
				g.fillRect(SQUARE_X * (col - yOffSet), SQUARE_Y *                                               //Sets the fill color.
						(row - xOffSet), SQUARE_WiDTH, SQUARE_HEIGHT);                                          
				g.setColor(Color.BLACK);                                                                        //Sets the border's color.
				g.drawRect(SQUARE_X * (col - yOffSet), SQUARE_Y *                                               //Draws the rectangles. 
						(row - xOffSet), SQUARE_WiDTH, SQUARE_HEIGHT);                                          
			}
		}	
	}	

	private void closeWindow() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	closeWindow()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method closes the application.							
		//							
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-11-01		Tiago   				The exit is in the menu.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
	
	//THE METHODS BELLOW ARE IMPLEMENTED IN THE CONTROLLER CLASS.
	
	public void loadTerrain(ActionListener listenerForLoadTerrain) {
		loadTerrain.addActionListener(listenerForLoadTerrain);
	}
	
	public void solvePath(ActionListener listenerForSolvePath) {
		solvePath.addActionListener(listenerForSolvePath);
	}
	
	public void help(ActionListener listenerForHelp) {
		helpGame.addActionListener(listenerForHelp);
	}
}
