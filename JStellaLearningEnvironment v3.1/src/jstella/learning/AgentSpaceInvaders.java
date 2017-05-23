package jstella.learning;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;

public class AgentSpaceInvaders implements JSIAgent {

	private JSILearning JSI;

	private File ROMFILE = new File ("SRC\\ROM\\SpaceInvaders.bin");

	double gamma = 0.9,
			eps = 0.9,
			alpha = 0.9,
			lambda = 0.9;
	int episodesToRun = 1600,
			episodeNumber = 0,
			currentScore = 0,
			scoreDifference = 0,
			currentLives = 0,
			liveDifference = 0,
			highScore = 0,
			scoreAVG = 0,
			stepCounter = 0;

	double timeInEpisodes = 1.25;//1.25; 
	int movesPerEpisode = 30000;
	long rewardSum = 0;
	boolean learning = true;

	int prevBounceCount = 0;
	int currentBounceCount = 0;

	int[] actions = new int[3];
	boolean deadFlag = false,
			episodeFlag = false;
	int[] Cstate, Pstate;

	//HashTables	
	Hashtable<StateActionPair, Double> stateAction = new Hashtable<StateActionPair, Double>();


	public AgentSpaceInvaders(JSILearning J){	
		/**
		 * Set JSILearning Object
		 */
		JSI = J;

		/**
		 * Boolean Flags To Set
		 */
		JSI.setSoundEnabled(false); //Default Value : true
		//JSI.setKeysEnabled(false); //Default Value : true
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

		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(ROMFILE);

		if(!learning){
			String fileName = "SATable_SpaceInvaders_B_2.txt";
			try{
				//Read in data about the state space from the given file
				BufferedReader reader = new BufferedReader( new FileReader( fileName ));
				String line = reader.readLine();
				line = reader.readLine();
				while(line != null)
				{
					String[] record = line.split("\\s");			
					int state[] = new int[4];
					state[0] = Integer.parseInt(record[1]);
					state[1] = Integer.parseInt(record[2]);
					state[2] = Integer.parseInt(record[3]);
					state[3] = Integer.parseInt(record[4]);
					//state[4] = Integer.parseInt(record[5]);
					//	state[5] = Integer.parseInt(record[6]);


					if(record.length==8){
						int action[] = new int[1];
						action[0] = Integer.parseInt(record[6]);
						StateActionPair sap = new StateActionPair(state,action);
						stateAction.put(sap, Double.parseDouble(record[7]));
					}
					else{
						int action[] = new int[2];
						action[0] = Integer.parseInt(record[6]);
						action[1] = Integer.parseInt(record[7]);
						StateActionPair sap = new StateActionPair(state,action);
						stateAction.put(sap, Double.parseDouble(record[8]));
					}

					line = reader.readLine();
				}
				reader.close();
			}
			catch(Exception e){
				System.out.println("ERROR");
				System.out.println(e);
			}		
		}
	}

	public int[] getAction() {		
		//YOUR CODE STARTS HERE		
		if(!learning){//if we are done follow only the best policy	
			Cstate = JSI.getROMState();

			//if(JSI.getMemory()[101]==1)
			//	return new int[]{KeyEvent.VK_SPACE};

			actions = getBestActionByState(Cstate);//choose best action
			return actions;
		}



		if(episodeNumber < episodesToRun && episodeFlag){//Reset everything at the end of the episode
			episodeNumber++;

			//eps = doRapidReduction(episodeNumber,eps);
			//eps = doNormalReduction(episodeNumber,eps);
			eps = doSlowReduction(episodeNumber,eps);

			//	deadFlag = false;
			episodeFlag = false;
			//	Pstate = null;
			//	Cstate = null;
			System.out.println("\n\n MOVING TO EPISODE: " + episodeNumber + "\n\n");

			//Save the avgscore and highscore every 5 runs
			scoreAVG += currentScore;
			if(episodeNumber%5==0){
				try{
					PrintWriter writer = new PrintWriter(new FileWriter("Score_SpaceInvaders_B_6.txt", true));
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
				PrintWriter writer = new PrintWriter(new FileWriter("Reward_SpaceInvaders_B_6.txt", true));
				writer.write("Reward Total " + rewardSum + " AVG " + RS + " Randomness: " + eps + "\n");			
				writer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			rewardSum=0;

			//Print out stateAction table every 50 episodes
			if(episodeNumber%50==0){
				try{
					PrintWriter writer = new PrintWriter(new FileWriter("SATable_SpaceInvaders_B_6.txt"));
					Set<StateActionPair> keys = stateAction.keySet();
					for(StateActionPair key: keys)
						writer.write(key+" "+stateAction.get(key)+"\n");				
					writer.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			//JSI.loadROM(ROMFILE);
			//JSI.reset();
			//actions = getRandomAction();
			//return actions;
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
			double stepReward = 1;
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
			if(!JSI.getROMPlayableStatus() || JSI.getROMLives()==0){
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




			if(Cstate[0] == 1 && JSI.getMemory()[85] != 246 )
				stepReward += 2;

			if(Cstate[1] >= -5 && Cstate[1] <= 7)
				stepReward -= 2;

			if(Cstate[2] >= -5 && Cstate[2] <= 7)
				stepReward -= 2;

			//stepReward +=scoreDifference;

			//if(Cstate[2]!= 0){
			//	stepReward -=1;
			//}

			//if(liveDifference <0 ){
			//	stepReward -=1000;

			//}

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
		else if(x%50 == 0 && x >=50)
			return (0.9/Math.floor(x/50.0));
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
		if(bestActions == null){
			return getRandomAction();
		}
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

