/**
 * The JSILearning Class acts as in intermediary for agents to communicate
 * with the JStella Atari Emulator and experiment with different machine learning
 * techniques
 *
 * @author  Ian Cichy
 * @version 1.0
 * @since  8-01-16
 */
package jstella.learning;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import jstella.core.JSAudio;
import jstella.core.JSConsole;
import jstella.core.JSConstants.Jack;
import jstella.core.JSController;
import jstella.core.JSRiot;
import jstella.core.JSTIA;
import jstella.core.JSVideo;
import jstella.runner.InputMaster;
import jstella.runner.Intercessor;
import jstella.runner.JStellaMain;
import static jstella.core.JSConstants.*;

public class JSILearning {

	//Variables from JStella
	private JStellaMain JSM;
	private Intercessor JSI;
	private InputMaster IPM;
	private JSConsole JSC;
	private JSController JSL;
	private JSRiot JSR;
	private JSTIA JST;
	private JSVideo JSV;
	private JSAudio JSA;

	//Variables from JSLearning
	private JSIAgent agent;
	private JSIGame game;

	//Variables for timing
	private Timer controlTimer;
	private Timer imageTimer;

	//Variables for images
	private int framesToAverage;
	private int frameSampleRate;
	private ArrayList<BufferedImage> gameImages;
	private BufferedImage image;
	private boolean imagesEnabled;
	private boolean imagesActive;

	//Variables MISC
	private boolean extraComputation;

	
	/**
	 * This method will be called by the JStella Emulator itself inside the JSMain class.
	 * It will then proceed to gather all necessary resources to facilitate the Agent and information 
	 * requests for any game.
	 * 
	 * <br><br>
	 * <b>The game and agent classes must be specified in this method.</b>
	 * <br><br>
	 * 
	 * @param JSMain A copy of the main class controlling the JStella Emulator
	 */
	public JSILearning(JStellaMain JSMain){
		JSM = JSMain;
		JSI = JSM.getIntercessor();
		IPM = JSI.getInputMaster();
		JSC = JSI.getConsole();
		JSL = JSC.getController(Jack.LEFT);
		JSR = JSC.getRiot();
		JST = JSC.getTIA();
		JSV = JSC.getVideo();
		JSA = JSC.getAudio();

		//Set JSI in the console
		JSC.setJSILearning(this);

		//Create the Timer
		controlTimer = new Timer();
		imageTimer = new Timer();

		//Initialize image variables
		framesToAverage = 4;
		frameSampleRate = 2;
		gameImages = new ArrayList<BufferedImage>();
		image = null;
		imagesEnabled = false;
		imagesActive = false;
		extraComputation = false;

		//Add Agent and Game-------------------------------
		//SELECT YOUR GAME BELOW
		game = new GameDonkeyKong(this);
		//SELECT YOUR AGENT BELOW
		agent = new AgentDonkeyKong(this);
		//Add Agent and Game-------------------------------
	}

	
	/**
	 * This is the primary method handling communication between the agent Class and the JStella Emulator.
	 * It will be automatically called by the JSConsole Class before each frame of the game is rendered and 
	 * as such should never be called manually. 
	 * <p>
	 * Will call for the games state to be computed if any extra computation is needed. 
	 * Then will query the agent for actions to send to the system.
	 * Additionally, this method keeps track of toggling image gathering on/off based on the Agents request.
	 */
	public void doLearning(){
		//If images are enabled but the timer is not running toggle it on
		//else if images are disabled and the timer is running toggle it off
		if(imagesEnabled && !imagesActive){
			imagesActive = true;
			toggleImages(imagesActive);
		}
		else if(!imagesEnabled && imagesActive){
			imagesActive = false;
			toggleImages(imagesActive);
		}

		//Let the game compute any additional states it needs
		if(extraComputation)
			game.computeState();

		//Send Actions to the game, Call to Agent to get Actions
		sendInput(agent.getAction());
	}

	
	/**
	 * Processes any given input command by sending it to the JStella InputMaster Class directly as a key press event.
	 * This will result in the game performing the set of actions given.
	 * Subsequently, generates the key release event for the same key one frame later.
	 * If the same key is pressed for multiple consecutive frames the motion will appear seamless. 
	 * 
	 * @param actions an array of 1 to 3 integer key-codes that correspond to a given joystick action sent by the Agent.
	 * @throws IllegalArgumentException if the array supplied has invalid actions or is outside of the allowed size range
	 */
	public void sendInput(int[] actions){		
		//Check input to ensure it is a valid action set
		if(actions==null || actions.length > 3 || actions.length < 1){
			try{
				throw new IllegalArgumentException();
			}
			catch(IllegalArgumentException e){
				System.err.println(e + "You must supply a maximum of three actions and a minimum of one.");
			}
		}
		else{
			try{
				if(Arrays.asList(actions).contains(KeyEvent.VK_UP) && Arrays.asList(actions).contains(KeyEvent.VK_DOWN))
					throw new IllegalArgumentException();
				if(Arrays.asList(actions).contains(KeyEvent.VK_LEFT) && Arrays.asList(actions).contains(KeyEvent.VK_RIGHT))
					throw new IllegalArgumentException();

				for(int t : actions)
					if(!((t==KeyEvent.VK_UP) || (t==KeyEvent.VK_DOWN) || (t==KeyEvent.VK_LEFT) || (t==KeyEvent.VK_RIGHT) || (t==KeyEvent.VK_SPACE) || (t==0)))
						throw new IllegalArgumentException();
			}
			catch(IllegalArgumentException e){
				System.err.println(e + "The action submitted was not one of the 18 allowed.");
			}
		}
		//end input check

		for(int aVKCode : actions){
			//Digital Pin One : UP
			if((JSL.read(DigitalPin.One)) && KeyEvent.VK_UP == aVKCode){
				IPM.processInputKeyEvent(aVKCode, true);
				controlTimer.schedule(new TimerTask() {
					public void run() {
						IPM.processInputKeyEvent(aVKCode, false);
					}
				}, JSI.getDelayNTSC());
			}
			//Digital Pin Two : DOWN
			if((JSL.read(DigitalPin.Two)) && KeyEvent.VK_DOWN == aVKCode){
				IPM.processInputKeyEvent(aVKCode, true);
				controlTimer.schedule(new TimerTask() {
					public void run() {
						IPM.processInputKeyEvent(aVKCode, false);
					}
				}, JSI.getDelayNTSC());
			}
			//Digital Pin Three : LEFT
			if((JSL.read(DigitalPin.Three)) && KeyEvent.VK_LEFT == aVKCode){
				IPM.processInputKeyEvent(aVKCode, true);
				controlTimer.schedule(new TimerTask() {
					public void run() {
						IPM.processInputKeyEvent(aVKCode, false);
					}
				}, JSI.getDelayNTSC());
			}
			//Digital Pin Four : RIGHT
			if((JSL.read(DigitalPin.Four)) && KeyEvent.VK_RIGHT == aVKCode){
				IPM.processInputKeyEvent(aVKCode, true);
				controlTimer.schedule(new TimerTask() {
					public void run() {
						IPM.processInputKeyEvent(aVKCode, false);
					}
				}, JSI.getDelayNTSC());
			}
			//Digital Pin Six : SPACE
			if((JSL.read(DigitalPin.Six)) && KeyEvent.VK_SPACE == aVKCode){
				IPM.processInputKeyEvent(aVKCode, true);
				controlTimer.schedule(new TimerTask() {
					public void run() {
						IPM.processInputKeyEvent(aVKCode, false);
					}
				}, JSI.getDelayNTSC());
			}
		}
	}

	
	/**
	 * Returns a BufferedImage which is built as a composite of multiple images 
	 * taken from the system separated by a number of game frames.
	 * Any pixel that is identical in all sample images will remain the same in the output image while 
	 * any pixel that differs in one or more image will be red (Red (255,0,0) will never be displayed in any image as it is not a valid NTSC color).
	 * <br><br>
	 * *Note: This method has a startup delay before it will return the first valid image of:
	 * <br>(framesToAverage * frameSampleRate * FrameDelay = DelayInMilliseconds)<br>
	 * If any call is made to this method before this time has elapsed it will return null.
	 *          
	 * @return A composite image built from a set of images
	 * @see <a href="http://www.qotile.net/minidig/docs/tia_color.html">Color Information</a>
	 */
	public BufferedImage getGameImage(){
		if(imagesEnabled)
			return image;
		else
			return null;
	}


	/*
	 * (non-Javadoc)
	 *  - - - - - - - - - - - - - - - - - - - 
	 * Will turn on/off the Timer recording images
	 * @param isActive: A Boolean value to turn on/off (true/false) the 
	 *      Timer that is recording the images
	 */
	private void toggleImages(Boolean isActive){
		if(isActive){
			imageTimer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					getImagePart();
				}
			}, 0, frameSampleRate * JSI.getDelayNTSC());
		}
		else{
			imageTimer.cancel();
		}
	}


	/*
	 * (non-Javadoc)
	 *  - - - - - - - - - - - - - - - - - - - 
	 * Adds an image to the gameImages ArrayList which is treated like a queue
	 * Will remove the oldest image if the addition makes the size > 'framesToAverage'
	 * Checks to see if there are 'framesToAverage' images and if so merges them
	 */
	private void getImagePart(){
		while(gameImages.size() >= framesToAverage)
			gameImages.remove(0);

		gameImages.add(cloneImage(JSV.getBackBuffer()));
		if(gameImages.size() == framesToAverage)
			mergeImages();
	}


	/*
	 * (non-Javadoc)
	 *  - - - - - - - - - - - - - - - - - - - 
	 * Merges all images in the gameImages ArrayList to one composite image.
	 * Will retain any pixels color that is the same in all images
	 * Will highlight any pixels color that differs in any one or more of them
	 * This then sets a BufferedImage object in the class so the Agent has the most up to date image
	 */
	private void mergeImages(){
		try{
			//Used for debugging testing   --
			/*
			int test = 0;
			for(BufferedImage B : gameImages){
				File out = new File("Image" + test + ".png");
				ImageIO.write(B, "png", out);
				test++;
			}
			 */
			//End debugging testing   --

			BufferedImage out = new BufferedImage(gameImages.get(0).getWidth(), gameImages.get(0).getHeight(), BufferedImage.TYPE_INT_ARGB);
			//Loop through all pixel locations
			for(int x = 0; x < out.getWidth(); x++){
				for(int y = 0; y < out.getHeight(); y++){
					//Loop through all images comparing the pixel location
					boolean same = true;
					for(int i = 0; i < gameImages.size()-1; i++){
						if(!(gameImages.get(i).getRGB(x, y) == gameImages.get(i + 1).getRGB(x, y))){
							same = false;
							break;
						}
					}
					//If the pixel color is the same in all images
					//else highlight the pixel red
					if(same)
						out.setRGB(x, y, gameImages.get(0).getRGB(x, y));
					else
						out.setRGB(x, y, Color.red.getRGB());
				}
			}

			//Used for debugging testing   --
			/*
			File outputfile = new File("out.png");
			ImageIO.write(out, "png", outputfile);
			 */
			//End debugging testing   --
			image = out;
		}
		catch(Exception e){}
	}


	/*
	 * (non-Javadoc)
	 *  - - - - - - - - - - - - - - - - - - - 
	 * Returns a BufferedImage which is a clone of the BufferedImage
	 * sent to it as the BufferedImage type has no built in clone functionality
	 * @param toCopy: A BufferedImage to be cloned
	 * @return BufferedImage: The same image as they input cloned
	 */
	private BufferedImage cloneImage(BufferedImage toClone){
		ColorModel cm = toClone.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = toClone.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}


	/**
	 * Development Method - May force the system into unstable states
	 * <p>
	 * Returns the current memory space in use by the JStella system without cloning it first.
	 * This allows direct control over the current memory and can lead to unstable run states.
	 * @return an integer array of length 128 
	 */
	protected int[] getMemoryNoClone(){
		return JSR.getMyRAM();
	}


	/**
	 * Development Method - May force the system into unstable states
	 * <p>
	 * Returns the current set of TIA registers in use by the JStella system without cloning it first.
	 * @return an integer array of length 45 
	 */
	protected int[] getTIARegistersNoClone(){
		return JST.getMyTIAPokeRegister();
	}


	/**
	 * Returns the current memory space in use by the JStella system.
	 * @return an integer array of length 128 
	 */
	public int[] getMemory(){
		return JSR.getMyRAM().clone();
	}


	/**
	 * Returns the current memory space in use by the JStella system converted from
	 * integer values to Hex values.
	 * @return a String array of length 128
	 */
	public String[] getMemoryAsHex(){
		int[] mem = JSR.getMyRAM().clone(); 
		String[] mems = new String[mem.length];

		for(int x = 0; x < mem.length; x++){
			mems[x] = Integer.toHexString(mem[x]);
		}
		return mems;
	}


	/**
	 * Sets the delay in frames between two consecutive image captures.
	 * @param sampleRate the number of frames (must be in the range 1 to 10 inclusive)
	 * @throws IllegalArgumentException if the value is outside of the allowed range
	 */
	public void setframeSampleRate(int sampleRate){
		try{
			if(sampleRate >= 1 && sampleRate <= 10)
				frameSampleRate = sampleRate;
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){
			System.err.println(e + 
					"\nThe value provided for the frame sample rate was outside of the allowed range."
					+ "The value must be greater than or equal to 1.\n");
		}
	}


	/**
	 * Returns the delay in frames between two consecutive image captures.
	 * @return an integer number of frames
	 */
	public int getframeSampleRate(){
		return frameSampleRate;
	}


	/**
	 * Sets the total number of frames to gather that will be part of the composite image.
	 * @param numFrames the number of frames (must be in the range 1 to 8 inclusive)
	 * @throws IllegalArgumentException if the value is outside of the allowed range
	 */
	public void setFramesToAverage(int numFrames){
		try{
			if(numFrames >= 1 && numFrames <= 8)
				framesToAverage = numFrames;
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){
			System.err.println(e + 
					"\nThe value provided for the number of frames to average was outside of the allowed range."
					+ "The value must be greater than or equal to 1.\n");
		}
	}


	/**
	 * Returns the total number of frames to gather that will be part of the composite image.
	 * @return an integer number of frames
	 */
	public int getFramesToAverage(){
		return framesToAverage;
	}


	/**
	 * Sets the time in milliseconds between each consecutively rendered frame.
	 * The default value is 17 milliseconds, or 60 frames a second.
	 * This value can be set as low as 1 millisecond, or 1000 frames a second.
	 * *Can be unstable when using a frame delay above 17
	 * @param myFrameDelay an integer delay that must be greater than 0
	 * @throws IllegalArgumentException if the delay is outside of the allowed range
	 */
	public void setFrameDelay(int myFrameDelay){
		try{
			if(myFrameDelay > 0){
				JSI.stopTimer();
				JSI.setDelayNTSC(myFrameDelay);
				JSI.emulateResetPress();
				JSI.startTimer();
			}
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){
			System.err.println(e + 
					"\nThe value provided for the frame delay was outside of the allowed range."
					+ "The value must be greater than zero.\n");
		}
	}


	/**
	 * Returns the current delay in milliseconds between each frame.
	 * @return a number of milliseconds
	 */
	public int getFrameDelay(){
		return JSI.getDelayNTSC();
	}


	/**
	 * Sets how often the Agent is called in relation to the number of game frames executing.
	 * <br>
	 * <ul>
	 * <li>0 = no calls to Agent</li>
	 * <li>1 = one call to Agent every frame</li>
	 * <li>n = one call to Agent every n frames</li>
	 * </ul>
	 * @param myCallDelay an integer delay that must be non negative
	 * @throws IllegalArgumentException if the delay is outside of the allowed range
	 */
	public void setAgentCallDelay(int myCallDelay){
		try{
			if(myCallDelay >= 0)
				JSC.setMyAgentCallDelay(myCallDelay);
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException e){
			System.err.println(e + 
					"\nThe value provided for the call delay was outside of the allowed range."
					+ "The value must be greater than or equal to zero.\n");
		}
	}


	/**
	 * Returns the current delay between consecutive Agent calls.
	 * @return a number of frames
	 */
	public int getAgentCallDelay(){
		return JSC.getMyAgentCallDelay();
	}


	/**
	 * Will disable/enable sounds playing in the JStella Emulator.
	 * @param aSoundEnabled sets the value (on/off) with (true/false)
	 */
	public void setSoundEnabled(boolean aSoundEnabled){
		JSA.setSoundEnabled(aSoundEnabled);
	}


	/**
	 * Will disable/enable video display in the JStella Emulator.
	 * @param aVideoEnabled sets the value (on/off) with (true/false)
	 */
	public void setVideoEnabled(boolean aVideoEnabled){
		JSV.setVideoEnabled(aVideoEnabled);
	}


	/**
	 * Will disable/enable the keyboard listener in the JStella Emulator.
	 * @param aKeysEnabled sets the value (on/off) with (true/false)
	 */
	public void setKeysEnabled(boolean aKeysEnabled){
		IPM.setKeysEnabled(aKeysEnabled);
	}


	/**
	 * Will disable/enable the mouse listener in the JStella Emulator
	 * @param aMouseEnabled sets the value (on/off) with (true/false)
	 */
	public void setMouseEnabled(boolean aMouseEnabled){
		IPM.setMouseEnabled(aMouseEnabled);
	}


	/**
	 * Will disable/enable image building for Agent request.
	 * @param aImagesEnabled sets the value (on/off) with (true/false)
	 */
	public void setImagesEnabled(boolean aImagesEnabled) {
		imagesEnabled = aImagesEnabled;
	}


	/**
	 * Sets and the loads the current ROM in the JStella system.
	 * @param aROMFile a file location of a ROM to be loaded
	 */
	public void loadROM(File aROMFile){
		try {
			JSM.loadROM(aROMFile);
		} 
		catch (IOException e) {
			System.out.println(e +
					"\nROM Read Error or ROM Does Not Exist");
		}
	}

	
	/**
	 * Resets the system ROM cartridge to the initial state.
	 */
	public void reset(){
		JSI.emulateResetPress();
	}

	
	/**
	 * Resets the ROM cartridge to the initial state and starts the game by emulating a system reset press.
	 */
	public void startGame(){
		IPM.processInputKeyEvent(KeyEvent.VK_F1, true);
		controlTimer.schedule(new TimerTask() {
			public void run() {
				IPM.processInputKeyEvent(KeyEvent.VK_F1, false);
			}
		}, JSI.getDelayNTSC());
	}

	
	/**
	 * Stops the JStella Emulator and exits the system.
	 */
	public void exit(){
		JSI.emulateResetPress();
		System.exit(0);
	}


	//-------------------------------------/--------------------------------------------------------------\--------------------------------------
	//------------------------------------/------------------  _____     ____    __  __  ------------------\-------------------------------------
	//-----------------------------------/------------------- |  __ \   / __ \  |  \/  | -------------------\------------------------------------
	//----------------------------------/-------------------- | |__) | | |  | | | \  / | --------------------\-----------------------------------
	//----------------------------------\-------------------- |  _  /  | |  | | | |\/| | --------------------/-----------------------------------
	//-----------------------------------\------------------- | | \ \  | |__| | | |  | | -------------------/------------------------------------
	//------------------------------------\------------------ |_|  \_\  \____/  |_|  |_| ------------------/-------------------------------------
	//-------------------------------------\--------------------------------------------------------------/--------------------------------------


	/**
	 * Returns the current score in the loaded ROM if this feature is present, else -1
	 * @return a number of points earned in game
	 */
	public int getROMScore(){
		return game.getScore();
	}

	
	/**
	 * Returns the current number of lives in the loaded ROM if this feature is present, else -1
	 * @return an amount of lives player0 has left in game
	 */
	public int getROMLives(){
		return game.getLives();
	}

	
	/**
	 * Returns the current level in the loaded ROM if this feature is present, else -1
	 * @return a level achieved in game
	 */
	public int getROMLevel(){
		return game.getLevel();
	}

	
	/**
	 * Returns the playable status of the character/controllable object in the loaded ROM
	 * This value is true if the game is still playable, else if the game is over
	 * @return a status of the game
	 */
	public boolean getROMPlayableStatus(){
		return game.getPlayableStatus();
	}

	
	/**
	 * Returns the set of valid actions in the loaded ROM
	 * <br><br>
	 * The only possible actions are combinations of 1 to 3 commands at once (limited by the joystick) from the options:
	 * <br>
	 * <ul>
	 * <li>NULL</li>
	 * <li>UP</li>
	 * <li>DOWN</li>
	 * <li>LEFT</li>
	 * <li>RIGHT</li>
	 * </ul>
	 * 18 Total Actions Possible
	 * @return a set of actions that can be taken in the game
	 */
	public int[][] getROMValidActions(){
		return game.getValidActions();
	}

	/**
	 * Returns the state representation of the game as determined by the games class.
	 * @return the state of the game
	 */
	public int[] getROMState(){
		return game.getState();
	}
}









