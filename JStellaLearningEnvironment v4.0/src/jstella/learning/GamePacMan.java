/**
 * Created By: Ian Cichy
 * 
 * Updated: March, 26th, 2017
 * 
 *    ...._______.._______.._______..........__...__.._______..__...._....
 *    ...|.......||..._...||.......|........|..|_|..||..._...||..|..|.|...
 *    ...|...._..||..|_|..||.......|..____..|.......||..|_|..||...|_|.|...
 *    ...|...|_|.||.......||......_|.|____|.|.......||.......||.......|...
 *    ...|....___||.......||.....|..........|.......||.......||.._....|...
 *    ...|...|....|..._...||.....|_.........|.||_||.||..._...||.|.|...|...
 *    ...|___|....|__|.|__||_______|........|_|...|_||__|.|__||_|..|__|...
 */

package jstella.learning;
import java.awt.event.KeyEvent;

public class GamePacMan implements JSIGame {

	JSILearning JSI;

	public GamePacMan(JSILearning J){
		JSI = J;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	Pac-Man Score is 6 bits, 
	 * 		2 High Bits
	 * 		2 Middle Bits
	 * 		2 Low Bits
	 * 	Put together they make up the 6 digit score that is shown on screen.
	 *  They are stored as integer values in memory, but converted to hex values
	 *  to display on the screen. To deal with this we read the values as hex
	 *  and then parse them as an integer for the agent.
	 */
	public int getScore() {
		try{
			String highBits =  JSI.getMemoryAsHex()[80];
			String middleBits = JSI.getMemoryAsHex()[78];
			String lowBits = JSI.getMemoryAsHex()[76];

			if(highBits.length() != 2)
				highBits = "0" + highBits;
			if(middleBits.length() != 2)
				middleBits = "0" + middleBits;
			if(lowBits.length() != 2)
				lowBits = "0" + lowBits;

			String value = highBits + "" + middleBits + "" + lowBits;
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
	 *  The Player0 lives are kept at memory position 24,
	 *  so we simply return this value.
	 */
	public int getLives() {
		return JSI.getMemory()[24];
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Pac-Man does not track how many times
	 *  the screen is cleared, or level. 
	 */
	public int getLevel() {
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Pac-Man allows the player to move left, right, up, down and not to move.
	 *  These represent all 5 possible actions. 
	 */
	public int[][] getValidActions() {
		return new int[][] {
			{0},
			{KeyEvent.VK_UP},
			{KeyEvent.VK_DOWN},
			{KeyEvent.VK_LEFT},
			{KeyEvent.VK_RIGHT}
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
	 * @see jstella.learning.JSIGame#getAliveStatus()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Memory position 2 holds a value that is always 0
	 *  while Pac-Man is alive, and non-zero otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getPlayableStatus(){
		return JSI.getMemory()[2]==0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#computeState()
	 *  - - - - - - - - - - - - - - - - - - -
	 *  No additional state computation is needed for the current state space. 
	 */
	public void computeState() {}
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
 *    0		:	0-255		: Game Timer (Do not change)
 *    1		:	0-5			:
 *    2		:	0-255		: 0 while alive, non-zero while dead
 *    3		:	0			:
 *    4		:	6			:
 *    5		:	0-255		:
 *    6		:	0-3			:
 *    7		:	0-255		: Power pellets active
 *    8		:	0-255		: Power pellets active
 *    9		:	0-255		: Power pellets active
 *    10	:	0-255		: Power pellets active
 *    11	:	0-255		: Power pellets active
 *    12	:	0-255		: Power pellets active
 *    13	:	0-255		: Power pellets active
 *    14	:	0-255		: Power pellets active   0 all gone
 *    15	:	0-255		: Power pellets active	 255 all active
 *    16	:	0-255		: Power pellets active	 controls 8 pellets each (bit wise)
 *    17	:	0-255		: Power pellets active
 *    18	:	0-255		: Power pellets active
 *    19	:	0-255		: Power pellets active
 *    20	:	0-255		: Power pellets active
 *    21	:	0-255		: Power pellets active
 *    22	:	0-255		: Power pellets active
 *    23	:	0-255		: Power pellets active
 *    24	:	0-3			: Lives
 *    25	:	0-255		: Ghost speed (Default 8)
 *    26	:	0-255		: Controls power pellets
 *    27	:	10			:
 *    28	:	0			:
 *    29	:	0			:
 *    30	:	0			:
 *    31	:	0			:
 *    32	:	0			:
 *    33	:	0			:
 *    34	:	0			:
 *    35	:	0			:
 *    36	:	0			:
 *    37	:	0			:
 *    38	:	0			:
 *    39	:	0			:
 *    40	:	0			:
 *    41	:	0			:
 *    42	:	0			:
 *    43	:	0			:
 *    44	:	0			:
 *    45	:	0			:
 *    46	:	0			:
 *    47	:	9			:
 *    48	:	0-255		: 
 *    49	:	0-255		: x position of pac man
 *    50	:	0-255		: x position of ghost 1
 *    51	:	0-255		: x position of ghost 2
 *    52	:	0-255		: x position of ghost 3
 *    53	:	0-255		: x position of ghost 4
 *    54	:	0-255		: y position of pac man
 *    55	:	0-255		: y position of ghost 1
 *    56	:	0-255		: y position of ghost 2
 *    57	:	0-255		: y position of ghost 3
 *    58	:	0-255		: y position of ghost 4
 *    59	:	0-255		: pac man movement direction
 *    60	:	0-255		: ghost 1 movement direction
 *    61	:	0-255		: ghost 2 movement direction
 *    62	:	0-255		: ghost 3 movement direction
 *    63	:	0-255		: ghost 4 movement direction
 *    64	:	0-255		:
 *    65	:	0-255		:
 *    66	:	0-255		:
 *    67	:	0-255		:
 *    68	:	0-255		:
 *    69	:	0-7			:
 *    70	:	0-7			:
 *    71	:	0-7			:
 *    72	:	0-7			:
 *    73	:	0-7			:
 *    74	:	0			:	
 *    75	:	0-255		:
 *    76	:	0-255		: Low bits of score
 *    77	:	0			:
 *    78	:	0,1,2,237	: middle bits of score
 *    79	:	0			:
 *    80	:	0,15		: high bits of score
 *    81	:	0			:
 *    82	:	0-64		:
 *    83	:	0-255		:
 *    84	:	0			:
 *    85	:	0			:
 *    86	:	0			:
 *    87	:	0			:
 *    88	:	0,254		:
 *    89	:	0-255		:
 *    90	:	0,254		:
 *    91	:	7			: controls ghost and pac man sprites
 *    92	:	6			: controls ghost and pac man sprites
 *    93	:	0-255		: controls ghost and pac man sprites
 *    94	:	253			: controls ghost and pac man sprites
 *    95	:	0-255		: controls ghost and pac man sprites
 *    96	:	253			: controls ghost and pac man sprites
 *    97	:	0-255		: controls ghost and pac man sprites
 *    98	:	0,65,126	: controls ghost and pac man sprites
 *    99	:	0			:
 *    100	:	0-255		:
 *    101	:	0-255		:
 *    102	:	0			:
 *    103	:	0-255		: bonus pellet spawn
 *    104	:	0,1,2,8		: 
 *    105	:	0-255		:
 *    106	:	0			:
 *    107	:	0			:
 *    108	:	0-255		:
 *    109	:	0-255		:
 *    110	:	0-255		:
 *    111	:	247,255		: playfield color
 *    112	:	7			: playfield color
 *    113	:	0-255		: playfield color
 *    114	:	0-255		:
 *    115	:	9			:
 *    116	:	0-255		:
 *    117	:	0-255		:
 *    118	:	0,251		:
 *    119	:	0,1,30		:
 *    120	:	0,251		:
 *    121	:	0-255		:
 *    122	:	0-255		:
 *    123	:	0-255		:
 *    124	:	0-255		:
 *    125	:	0-255		:
 *    126	:	26,208		:
 *    127	:	240,247		:
 *    
 *    
 */