package jstella.learning;

import java.io.File;

public class AgentMissileCommand implements JSIAgent {

	private JSILearning JSI;
	File ROMFILE = new File ("src/ROM/MissileCommand.bin");

	public AgentMissileCommand(JSILearning J){	
		/**
		 * Set JSILearning Object
		 */
		JSI = J;

		/**
		 * Boolean Flags To Set
		 */
		JSI.setSoundEnabled(false); //Default Value : true
		//JSI.setKeysEnabled(false); //Default Value : true
		//JSI.setMouseEnabled(false); //Default Value : true
		//JSI.setVideoEnabled(false);  //Default Value : true

		/**
		 * Variables Related To Emulation
		 */
		//JSI.setFrameDelay(1); //Default Value : 17 (60 frames a second)
		//JSI.setAgentCallDelay(1); //Default Value : 1 (Agent called every frame)

		/**
		 * Variables Related To Images
		 */
		//JSI.setImagesEnabled(false); //Default Value : false
		//JSI.setFramesToAverage(4); //This is the default value
		//JSI.setframeSampleRate(3); //This is the default value		
		
		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(ROMFILE);
	}
	
	public int[] getAction() {	
		
		
		//Example random movement action selector
		
		if(!JSI.getROMPlayableStatus())
			JSI.reset();
		
		//Create the array of actions to return
		int [] actions = new int[3];
		//Get the valid actions from the game
		int[][] validActions = JSI.getROMValidActions();
		//Select an action at random
		actions = validActions[(int) (Math.random() * validActions.length)];
		
		
		
		
		return actions;
	}
	
}









