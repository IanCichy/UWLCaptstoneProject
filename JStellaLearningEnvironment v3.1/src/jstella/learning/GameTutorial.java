package jstella.learning;

public class GameTutorial implements JSIGame {
	
	JSILearning JSI;
	
	public GameTutorial(JSILearning J){
		JSI = J;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLives() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void computeState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getPlayableStatus() {
		// TODO Auto-generated method stub
		return false;
	}

}
