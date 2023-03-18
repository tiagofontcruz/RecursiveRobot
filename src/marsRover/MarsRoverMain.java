package marsRover;

import javax.swing.SwingUtilities;

public class MarsRoverMain {
		
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				MarsRoverModel theModel = new MarsRoverModel();
				MarsRoverView theView = new MarsRoverView();
				@SuppressWarnings("unused")
				MarsRoverController theController = new MarsRoverController(theModel, theView);		
				theView.setVisible(true);
				theView.enableSolvePath(false);
			}
		});
		
	}
}
