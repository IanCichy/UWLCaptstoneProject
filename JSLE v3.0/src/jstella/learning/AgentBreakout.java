package jstella.learning;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;


public class AgentBreakout implements JSIAgent {

	private JSILearning JSI;

	File ROMFILE = new File ("src/ROM/Breakout.bin");
	String fileName = "src/StateSpaces/SATable_Breakout_D_9.txt";

	double gamma = 0.9,
			eps = 0.9,
			alpha = 0.9,
			lambda = 0.9;
	int episodesToRun = 800, //usually 1600
			episodeNumber = 0,
			currentScore = 0,
			scoreDifference = 0,
			currentLives = 0,
			liveDifference = 0,
			highScore = 0,
			scoreAVG = 0,
			stepCounter = 0;

	int reductionType = 2;
	double timeInEpisodes = 2.0; //usually 2.5
	int movesPerEpisode = 30000;

	long rewardSum = 0;
	boolean learning =  true;

	int prevDirection = 0;
	int currentDirection = 0;

	int[] actions = new int[3];
	boolean deadFlag = false,
			episodeFlag = false;
	int[] Cstate, Pstate;

	//HashTables	
	Hashtable<StateActionPair, Double> stateAction = new Hashtable<StateActionPair, Double>();


	public AgentBreakout(JSILearning J){	
		/**
		 * Set JSILearning Object
		 */
		JSI = J;

		/**
		 * Boolean Flags To Set
		 */
		JSI.setSoundEnabled(false); //Default Value : true
		JSI.setKeysEnabled(false); //Default Value : true
		//JSI.setVideoEnabled(false);  //Default Value : true

		/**
		 * Variables Related To Emulation
		 */
		JSI.setFrameDelay(1); //Default Value : 17 (60 frames a second)
		//JSI.setAgentCallDelay(1); //Default Value : 1 (Agent called every frame)

		/**
		 * Variables Related To Images
		 */
		//JSI.setImagesEnabled(false); //Default Value : false
		//JSI.setFramesToAverage(4); //This is the default value
		//JSI.setNumberOfFramesBetween(3); //This is the default value		

		if(!learning){

			try{
				//Read in data about the state space from the given file
				BufferedReader reader = new BufferedReader( new FileReader( fileName ));
				String line = reader.readLine();
				line = reader.readLine();
				while(line != null)
				{
					String[] record = line.split("\\s");			
					int state[] = new int[6];
					state[0] = Integer.parseInt(record[1]);
					state[1] = Integer.parseInt(record[2]);
					state[2] = Integer.parseInt(record[3]);
					state[3] = Integer.parseInt(record[4]);
					state[4] = Integer.parseInt(record[5]);
					state[5] = Integer.parseInt(record[6]);

					int action[] = new int[1];
					action[0] = Integer.parseInt(record[8]);

					StateActionPair sap = new StateActionPair(state,action);
					stateAction.put(sap, Double.parseDouble(record[9]));

					line = reader.readLine();
				}
				reader.close();
			}
			catch(Exception e){
				System.out.println(e);
			}		
		}

		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(ROMFILE);
	}

	public int[] getAction() {		
		//YOUR CODE STARTS HERE		
		if(!learning){//if we are done follow only the best policy	
			Cstate = JSI.getROMState();
			actions = getBestActionByState(Cstate);//choose best action

			if(!JSI.getROMAliveStatus()){
				JSI.reset();
			}
			
			if(JSI.getMemory()[101]==0)
				return new int[]{KeyEvent.VK_SPACE};



			return actions;
		}


		if(episodeNumber < episodesToRun && episodeFlag){//Reset everything at the end of the episode
			episodeNumber++;

			if(reductionType==0)
				eps = doRapidReduction(episodeNumber,eps);
			else if(reductionType==1)
				eps = doNormalReduction(episodeNumber,eps);
			else if(reductionType==2)
				eps = doSlowReduction(episodeNumber,eps);

			episodeFlag = false;

			System.out.println(" Moving to episode: " + episodeNumber + "\n");

			//Save the avgscore and highscore every 5 runs
			scoreAVG += currentScore;
			if(episodeNumber%5==0){
				try{
					PrintWriter writer = new PrintWriter(new FileWriter("Score_Breakout_E_2.txt", true));
					scoreAVG = scoreAVG/5;
					writer.write("HS " + highScore+" AVG " + scoreAVG +"\n");				
					writer.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			scoreAVG=0;
			highScore=0;

			//Save the reward every episode
			double RS =rewardSum/movesPerEpisode;
			try{
				PrintWriter writer = new PrintWriter(new FileWriter("Reward_Breakout_E_2.txt", true));
				writer.write("Reward Total " + rewardSum + " AVG " + RS + "\n");			
				writer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			rewardSum=0;

			//Print out stateAction table every 50 episodes
			if(episodeNumber%50==0){
				try{
					PrintWriter writer = new PrintWriter(new FileWriter("SATable_Breakout_E_2.txt"));
					Set<StateActionPair> keys = stateAction.keySet();
					for(StateActionPair key: keys)
						writer.write(key+" "+stateAction.get(key)+"\n");				
					writer.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}
		else if(episodeNumber >= episodesToRun){//If we have finished reset the system so we can watch the final policy
			//JSI.setFrameDelay(17);
			//JSI.loadROM(ROMFILE);
			//JSI.reset();
			//learning = false;
			JSI.exit();
		}
		else{
			stepCounter ++;

			//Step Reward
			double stepReward = -1;
			if(Cstate != null){
				Pstate = Cstate.clone();
			}
			else{
				Cstate = JSI.getROMState();
				//If this StateActionPair is new, add it with unknown reward
				int[][] validActions = JSI.getROMValidActions();
				for(int[] a : validActions){
					if(!stateAction.containsKey(new StateActionPair(Cstate, a))){
						stateAction.put(new StateActionPair(Cstate, a), 0.0);
					}
				}
				actions = getRandomAction();
				return actions;
			}
			Cstate = JSI.getROMState();

			//If this StateActionPair is new, add it with unknown reward
			int[][] validActions = JSI.getROMValidActions();
			for(int[] a : validActions){
				if(!stateAction.containsKey(new StateActionPair(Cstate, a))){
					stateAction.put(new StateActionPair(Cstate, a), 0.0);
				}
			}

			//OPTIONS--------------------------------

			//Find the Score Difference to see if we gained points
			scoreDifference = (JSI.getROMScore() - currentScore);
			currentScore = JSI.getROMScore();

			if(currentScore > highScore)
				highScore = currentScore;

			//Find the live difference to see if we lost a life
			liveDifference = JSI.getROMLives() - currentLives;
			currentLives = JSI.getROMLives();


			//RESET IF DEAD
			if(!JSI.getROMAliveStatus()){
				deadFlag = false;
				episodeFlag = false;
				Pstate = null;
				Cstate = null;
				JSI.loadROM(ROMFILE);
				JSI.reset();
				actions = getRandomAction();
				return actions;
			}

			if(stepCounter >= movesPerEpisode){
				stepCounter = 0;
				episodeFlag = true;
			}

			//Keep track of direction to see if we hit the ball
			currentDirection = JSI.getMemory()[103];
			if(currentDirection > 200 && prevDirection < 10){	
				stepReward += 750;
			}
			prevDirection = currentDirection;


			//1-10 used this
			//if(Cstate[0]==0)
			//	stepReward += 1;

			//11-12 used this // also back to 5 var state
			if(JSI.getMemory()[99] > JSI.getMemory()[72] &&
					JSI.getMemory()[99] < JSI.getMemory()[72]+14)
				stepReward +=1;

			if(actions[0] == 37)
				stepReward += -1; 
			else if (actions[0] == 39)
				stepReward += -1;

			if(liveDifference < 0)
				stepReward -= -100;

			//if(Cstate[0] == 1)
			//	stepReward = 3;
			//else if(Cstate[0] == 2)
			//	stepReward = 8;
			//else if(Cstate[0] == 3)
			//	stepReward = 8;
			//else if(Cstate[0] == 4)
			//	stepReward = 3;

			//if(scoreDifference >0)
			//	stepReward += scoreDifference*10;

			if(JSI.getMemory()[101]==0)
				return new int[]{KeyEvent.VK_SPACE};


			//END OPTIONS----------------------------
			StateActionPair sap = new StateActionPair(Pstate,actions);
			//Get the current reward value of this StateActionPair
			double currentReward = stateAction.get(sap);
			//find Max Q(s',a')
			actions = getBestActionByState(Cstate);
			double maxReward = 0.0;
			//if(stateAction.containsKey(new StateActionPair(Pstate,actions)))
			maxReward = stateAction.get(new StateActionPair(Cstate,actions));
			double e = currentReward + alpha * (stepReward + gamma * (maxReward - currentReward));
			//System.out.println("CurrentReward: " + currentReward + "\n stepReward: " + stepReward + "\n MaxReward: " + maxReward + "\n ----- \n");
			stateAction.replace(sap, e);
			rewardSum += stepReward;
		}

		//Set next action a', chosen E-greedily based on Q(s',a')
		double r = Math.random();
		if(r<=eps)
			actions = getRandomAction();//choose random action
		else
			actions = getBestActionByState(Cstate);//choose best action			

		//YOUR CODE ENDS HERE
		return actions;
	}



	/*
	 * Normal Reduction
	 * Pace: 10 episodes
	 * Reduce By: (0.9/Math.floor(x/10.0))
	 */
	private double doNormalReduction(int x, Double D){
		if(x>=(episodesToRun/timeInEpisodes))
			return 0;
		else if(x%10 == 0 && x >=10)
			return (0.9/Math.floor(x/10.0));
		else
			return D;
	}

	/*
	 * Rapid Reduction
	 * Pace: 4 episodes
	 * Reduce By: (0.9/Math.floor(x/4.0))
	 */
	private double doRapidReduction(int x, Double D){
		if(x>=(episodesToRun/timeInEpisodes))
			return 0;
		else if(x%4 == 0 && x >=4)
			return (0.9/Math.floor(x/4.0));
		else
			return D;
	}

	/*
	 * Slow Reduction
	 * Pace: 25 episodes
	 * Reduce By: (0.9/Math.floor(x/25.0))
	 */
	private double doSlowReduction(int x, Double D){
		if(x>=(episodesToRun/timeInEpisodes))
			return 0;
		else if(x%25 == 0 && x >=25)
			return (0.9/Math.floor(x/25.0));
		else
			return D;
	}

	/**
	 * Returns the best action for the given state s of the ROM
	 *      Will give priority to any (s,action) pair that still has
	 *      a 0 value as it has not been tried yet
	 */
	private int[] getBestActionByState(int[] s) {
		int[] bestActions = null;
		double bestScore = Double.MIN_VALUE;
		double curScore = 0;

		int[][] possibleActions = JSI.getROMValidActions();
		for(int[] a : possibleActions){
			if(stateAction.containsKey(new StateActionPair(s,a))){
				curScore = stateAction.get(new StateActionPair(s,a));
				if(curScore == 0){
					bestActions = a;
					break;
				}
				else if(curScore > bestScore){
					bestScore = curScore;
					bestActions = a;
				}
			}
		}
		if(bestActions == null)
			return getRandomAction();

		return bestActions;
	}

	/*
	 * Returns a random action from the possible actoins for the ROM
	 */
	private int[] getRandomAction() {
		int[][] Actions = JSI.getROMValidActions();
		int rnd = (int) (Math.random()*Actions.length);
		return Actions[rnd];
	}

}

