package jstella.learning;

public interface JSIAgent {

	/**
	 * Allows for agent customization with standardized return values
	 * @return an array of integers corresponding to one of the 18 possible sets of input actions
	 */
	public int[] getAction();

}
