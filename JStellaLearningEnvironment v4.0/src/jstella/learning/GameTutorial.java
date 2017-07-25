package jstella.learning;

import java.awt.event.KeyEvent;

public class GameTutorial implements JSIGame {
	
	JSILearning JSI;
	
	public GameTutorial(JSILearning J){
		JSI = J;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	Most games have score that is 1,2,or 3 bytes, 
	 * 		High byte
	 * 		Middle byte
	 * 		Low byte
	 * 	Put together they make up the 2,4,6 digit score that is shown on screen.
	 *  They are stored as integer values in memory, but converted to hex values
	 *  to display on the screen. To deal with this we read the values as hex
	 *  and then parse them as an integer for the agent.
	 */
	public int getScore() {
		// 6 Digit Score
		// High, Middle, and Low Bytes
		/**
		 * 
		try{
			String highByte =  JSI.getMemoryAsHex()[115];
			String middleByte = JSI.getMemoryAsHex()[113];
			String lowByte = JSI.getMemoryAsHex()[111];

			if(highByte.length() != 2)
				highByte = "0" + highByte;
			if(middleByte.length() != 2)
				middleByte = "0" + middleByte;
			if(lowByte.length() != 2)
				lowByte = "0" + lowByte;

			String value = highByte + "" + middleByte + "" + lowByte;
			return Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			return 0;
		}
		*
		**/
		
		// 4 Digit Score
		// High and Low Bytes
		/**
		 * 
		try{
			String highByte =  JSI.getMemoryAsHex()[76];
			String lowByte = JSI.getMemoryAsHex()[77];
			String value = highByte + "" + lowByte;
			return Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			return 0;
		}
		*
		*/
		
		// 2 Digit Score
		/**
		 * 
		try{
			String Byte =  JSI.getMemoryAsHex()[36];
			String value = Byte + "";
			return Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			return 0;
		}
		*
		*/
		
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLives()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  The Player0 lives are kept at some memory position,
	 *  so we simply return this value.
	 *  
	 *  If there is a non-traditional value for lives, such as in missile command,
	 *  the implementation may look different
	 */
	public int getLives() {
		/**
		 * return JSI.getMemory()[57];
		 */
		
		/** Breakout Lives implementation
		int x = 0;
		if(JSI.getMemory()[41] == 56)
			x++;
		if(JSI.getMemory()[43] == 56)
			x++;
		if(JSI.getMemory()[45] == 56)
			x++;
		if(JSI.getMemory()[47] == 56)
			x++;
		if(JSI.getMemory()[49] == 56)
			x++;
		if(JSI.getMemory()[51] == 56)
			x++;
		return x;
		 */

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  The Level is kept at some memory position,
	 *  so we simply return this value.
	 */
	public int getLevel() {
		/**
		 * return JSI.getMemory()[103];
		 */
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Return the subset of the following actions that the agent
	 *  may take when playing a game. 
	 */
	public int[][] getValidActions(){
		return new int[][] {
			{0},
			{KeyEvent.VK_UP},
			{KeyEvent.VK_DOWN},
			{KeyEvent.VK_LEFT},
			{KeyEvent.VK_RIGHT},
			{KeyEvent.VK_SPACE},
			{KeyEvent.VK_UP, KeyEvent.VK_LEFT},
			{KeyEvent.VK_UP, KeyEvent.VK_RIGHT},
			{KeyEvent.VK_UP, KeyEvent.VK_SPACE},
			{KeyEvent.VK_DOWN, KeyEvent.VK_LEFT},
			{KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT},
			{KeyEvent.VK_DOWN, KeyEvent.VK_SPACE},
			{KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE}
		};
	}
	
	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getState()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Gives the current state information to the agent, 
	 *  varies depending on the algorithm, test, and goals.
	 */
	public int[] getState() {
		return JSI.getMemory();
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#computeState()
	 *  - - - - - - - - - - - - - - - - - - -
	 *  Can be used to compute additional state space information. 
	 */
	public void computeState() {
		//Compute the path of an object, or additional values
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getAliveStatus()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Memory position X holds some value that is always N
	 *  while Player0 is alive, and non-N otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getPlayableStatus() {

		/**		
		 * return JSI.getMemory()[52] == 255;
		 */
		
		/**		
		 * return JSI.getMemory()[101] == 0;
		 */

		return false;
	}

	
}
