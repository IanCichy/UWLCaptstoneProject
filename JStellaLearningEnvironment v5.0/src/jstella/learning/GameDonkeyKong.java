/**
 * Created By: Ian Cichy
 * 
 * Updated: March, 26th, 2017
 * 
 *    ....______..._______..__...._..___..._.._______..__...__....___..._.._______..__...._.._______....
 *    ...|......|.|.......||..|..|.||...|.|.||.......||..|.|..|..|...|.|.||.......||..|..|.||.......|...
 *    ...|.._....||..._...||...|_|.||...|_|.||....___||..|_|..|..|...|_|.||..._...||...|_|.||....___|...
 *    ...|.|.|...||..|.|..||.......||......_||...|___.|.......|..|......_||..|.|..||.......||...|.__....
 *    ...|.|_|...||..|_|..||.._....||.....|_.|....___||_....._|..|.....|_.|..|_|..||.._....||...||..|...
 *    ...|.......||.......||.|.|...||...._..||...|___...|...|....|...._..||.......||.|.|...||...|_|.|...
 *    ...|______|.|_______||_|..|__||___|.|_||_______|..|___|....|___|.|_||_______||_|..|__||_______|...
 */

package jstella.learning;
import java.awt.event.KeyEvent;

public class GameDonkeyKong implements JSIGame {

	JSILearning JSI;

	public GameDonkeyKong(JSILearning J){
		JSI = J;
	}

	/**
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	DonkeyKong Score is 4 bits, 
	 * 		2 High Bits
	 * 		2 Low Bits
	 * 	Put together they make up the 4 digit score that is shown on screen.
	 *  They are stored as integer values in memory, but converted to hex values
	 *  to display on the screen. To deal with this we read the values as hex
	 *  and then parse them as an integer for the agent. However, Donkey Kong
	 *  only cares about the number of Hundreds you have, so only the high
	 *  bits are needed to accurately report the score.
	 */
	public int getScore() {
		try{
			String Bits =  JSI.getMemoryAsHex()[36];
			String value = Bits + "";
			return Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLives()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  The lives are kept at memory position 35,
	 *  so we simply return this value.
	 */
	public int getLives() {
		return JSI.getMemory()[35];
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  The level is kept at memory position 78,
	 *  so we simply return this value.
	 */
	public int getLevel() {
		return JSI.getMemory()[78];
	}
	
	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  DonkeyKong allows the player to move left, right, up, down and to jump (button press).
	 *  These five actions result in the 8 possible actions pairs. 
	 *  This includes {0} or the do nothing action. 
	 */
	public int[][] getValidActions() {
		return new int[][] {
			{0},
			{KeyEvent.VK_UP},
			{KeyEvent.VK_DOWN},
			{KeyEvent.VK_LEFT},
			{KeyEvent.VK_RIGHT},
			{KeyEvent.VK_SPACE},
			{KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
			{KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE}
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

	/**
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getPlayableStatus()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Memory position 25 holds a value that is always 0
	 *  while Player0 is alive, and non-zero otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getPlayableStatus() {
		return JSI.getMemory()[12] != 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#computeState()
	 *  - - - - - - - - - - - - - - - - - - -
	 *  No additional state computation is needed for the current state space. 
	 */
	public void computeState() {
		
	}
}

/***
 *    ....______...._______..__...__....___......_______..__...__.._______..__...__.._______....
 *    ...|...._.|..|..._...||..|_|..|..|...|....|..._...||..|.|..||.......||..|.|..||.......|...
 *    ...|...|.||..|..|_|..||.......|..|...|....|..|_|..||..|_|..||..._...||..|.|..||_....._|...
 *    ...|...|_||_.|.......||.......|..|...|....|.......||.......||..|.|..||..|_|..|..|...|.....
 *    ...|....__..||.......||.......|..|...|___.|.......||_....._||..|_|..||.......|..|...|.....
 *    ...|...|..|.||..._...||.||_||.|..|.......||..._...|..|...|..|.......||.......|..|...|.....
 *    ...|___|..|_||__|.|__||_|...|_|..|_______||__|.|__|..|___|..|_______||_______|..|___|.....
 *    
 *    
 *    
 *    Memory Position : Values : Description
 *    0		:	0-145		: controls barrels/enemies
 *    1		:	0-145		: controls barrels/enemies
 *    2		:	0-145		: controls barrels/enemies
 *    3		:	0-145		: controls barrels/enemies
 *    4		:	0-255		: controls the look of mario sprite
 *    5		:	0-31		: Mario jump timer (0 on ground, then 31 down to 0 when jumping)
 *    6		:	0-3			: Mario hammer timer (0 without, then 3-2-1-0 for timer)
 *    7		:	0			:
 *    8		:	0-68		:
 *    9		:	0,1,2		: game timer, 2,1,0 constant
 *    10	:	0-7			:
 *    11	:	0-255		:
 *    12	:	0-255		: game timer
 *    13	:	0-255		:
 *    14	:	0-255		:
 *    15	:	0-5			: 
 *    16	:	0,1			: graphics display
 *    17	:	0,255		: game timer
 *    18	:	0			: background (playfield) color
 *    19	:	41-124		: Mario x location
 *    20	:	0-255		: Mario direction (8 facing right, 0 left)
 *    21	:	0-255		: control hammer spawn (127 there, 127-167 active, 112 gone)
 *    22	:	255			: hammer active bit
 *    23	:	0-3			: Hammer head position
 *    24	:	0-255		: Hammer sprite
 *    25	:	0-255		: barrel sprite
 *    26	:	0-255		: scaffold color
 *    27	:	0-255		: Mario y position
 *    28	:	0,1			:
 *    29	:	0,2,8		:
 *    30	:	0,3			:
 *    31	:	0,4			:
 *    32	:	0,5,11		:
 *    33	:	0,1,2		:
 *    34	:	0-80		:
 *    35	:	0-255		: Lives
 *    36	:	0-255		: Score
 *    37	:	0-255		:
 *    38	:	0-255		:
 *    39	:	0-255		:
 *    40	:	0-255		:
 *    41	:	0-255		:
 *    42	:	0-255		:
 *    43	:	0-255		:
 *    44	:	0-255		:
 *    45	:	0-255		:
 *    46	:	0-255		:
 *    47	:	0-255		:
 *    48	:	0-255		:
 *    49	:	0-255		:
 *    50	:	0-255		:
 *    51	:	0-255		:
 *    52	:	0-255		:
 *    53	:	0-255		:
 *    54	:	0-255		:
 *    55	:	0-255		:
 *    56	:	0-255		:
 *    57	:	0-255		:
 *    58	:	0-255		:
 *    59	:	0-255		: Controls mario graphics
 *    60	:	0-255		: Controls mario graphics
 *    61	:	0-255		:
 *    62	:	0-255		:
 *    63	:	0-125		:
 *    64	:	0-125		:
 *    65	:	0-125		: barrel 4 x
 *    66	:	0-125		: barrel 3 x
 *    67	:	0-255		: barrel 2 x
 *    68	:	0-255		: barrel 1 x
 *    69	:	0-255		: barrel 4 y
 *    70	:	0-255		: barrel 3 y
 *    71	:	0-20		: barrel 2 y
 *    72	:	0-20		: barrel 1 y
 *    73	:	0-20		:
 *    74	:	0-20		:
 *    75	:	0-255		:
 *    76	:	0-255		:
 *    77	:	0-255		:
 *    78	:	0-255		: Level
 *    79	:	0-255		:
 *    80	:	0-6			:
 *    81	:	0-255		:
 *    82	:	0-255		:
 *    83	:	0-255		:
 *    84	:	0-255		:
 *    85	:	0-255		:
 *    86	:	0-255		:
 *    87	:	0,16,145	:
 *    88	:	0,253		:
 *    89	:	0,83,113	:
 *    90	:	0,250,255	:
 *    91	:	0,83,227	:
 *    92	:	0,250,254	:
 *    93	:	0,2,83		:
 *    94	:	0,250,254	:
 *    95	:	0-255		:
 *    96	:	0.252		:
 *    97	:	0-255		:
 *    98	:	0-255		:
 *    99	:	14-45		:
 *    100	:	0,12		:
 *    101	:	0-255		:
 *    102	:	0-255		:
 *    103	:	0,255		:
 *    104	:	0-33		: barrel speed
 *    105	:	0-255		:
 *    106	:	0,255		:
 *    107	:	0-255		:
 *    108	:	0,168,169	:
 *    109	:	0,247,248	:
 *    110	:	0,216,218	:
 *    111	:	0,247,248	:
 *    112	:	93			:
 *    113	:	93			:
 *    114	:	93			:
 *    115	:	93			:
 *    116	:	93			:
 *    117	:	93			:
 *    118	:	0-255		:
 *    119	:	0-255		:
 *    120	:	0-255		:
 *    121	:	0-255		:
 *    122	:	0-255		:
 *    123	:	0-255		:
 *    124	:	0-255		:
 *    125	:	0-255		:
 *    126	:	13			:
 *    127	:	243			:
 *    		
 *       
 *    
 */