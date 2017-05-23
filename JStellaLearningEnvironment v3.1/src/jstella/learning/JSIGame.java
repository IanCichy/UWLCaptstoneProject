package jstella.learning;

import java.awt.event.KeyEvent;

public interface JSIGame {

	/**
	 * Gets the current score in game
	 * @return an integer that represents number of points earned in any given game
	 */
	public int getScore();

	/**
	 * Gets the current number of lives
	 * @return an integer that represents the amount of lives player0 has in game
	 *      if this feature is present, else 0
	 */
	public int getLives();

	/**
	 * Gets the current level in game
	 * @return an integer that represents the level in game 
	 *      if this feature is present, else 0
	 */
	public int getLevel();

	/**
	 * Allows the user to build a set of values from memory or computation 
	 * @return an integer array that represents the current state of the game
	 */
	public int[] getState();
	
	/**
	 * Allows the user to compute and needed state representation 
	 * variables outside of the default getState method
	 */
	public void computeState();

	/**
	 * Gets the status of the game
	 * @return a boolean that represents if the game is still going/playable or not
	 */
	public boolean getPlayableStatus();

	/**
	 * Gets the valid actions for any given game
	 * @return an integer array that represents actions that can be taken in a game
	 *      These combinations range from 1-3 commands at once from the options:
	 *      NULL, UP, LEFT, DOWN, RIGHT, SPACE
	 *      18 Total Actions
	 */
	default int[][] getValidActions(){
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
}
