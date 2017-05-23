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
 *    0		:
 *    1		:
 *    2		:
 *    3		:
 *    4		:
 *    5		:
 *    6		:
 *    7		:
 *    8		:
 *    9		:
 *    10	:
 *    11	:	
 *    12	:
 *    13	:
 *    14	:
 *    15	:
 *    16	:
 *    17	:
 *    18	:
 *    19	:
 *    20	:
 *    21	:
 *    22	:
 *    23	:
 *    24	:
 *    25	:
 *    26	:
 *    27	:
 *    28	:
 *    29	:
 *    30	:
 *    31	:
 *    32	:
 *    33	:
 *    34	:
 *    35	:
 *    36	:
 *    37	:
 *    38	:
 *    39	:
 *    40	:
 *    41	:
 *    42	:
 *    43	:
 *    44	:
 *    45	:
 *    46	:
 *    47	:
 *    48	:
 *    49	:
 *    50	:
 *    51	:
 *    52	:
 *    53	:
 *    54	:
 *    55	:
 *    56	:
 *    57	:
 *    58	:
 *    59	:
 *    60	:
 *    61	:
 *    62	:
 *    63	:
 *    64	:
 *    65	:
 *    66	:
 *    67	:
 *    68	:
 *    69	:
 *    70	:
 *    71	:
 *    72	:
 *    73	:
 *    74	:
 *    75	:
 *    76	:
 *    77	:
 *    78	:
 *    79	:
 *    80	:
 *    81	:
 *    82	:
 *    83	:
 *    84	:
 *    85	:
 *    86	:
 *    87	:
 *    88	:
 *    89	:
 *    90	:
 *    91	:
 *    92	:
 *    93	:
 *    94	:
 *    95	:
 *    96	:
 *    97	:
 *    98	:
 *    99	:
 *    100	:
 *    101	:
 *    102	:
 *    103	:
 *    104	:
 *    105	:
 *    106	:
 *    107	:
 *    108	:
 *    109	:
 *    110	:
 *    111	:
 *    112	:
 *    113	:
 *    114	:
 *    115	:
 *    116	:
 *    117	:
 *    118	:
 *    119	:
 *    120	:
 *    121	:
 *    122	:
 *    123	:
 *    124	:
 *    125	:
 *    126	:
 *    127	:
 *    
 *       
 *    ...._______.._______..______...._______.._______..__...._....___......_______..__...__.._______..__...__.._______....
 *    ...|.......||.......||...._.|..|.......||.......||..|..|.|..|...|....|..._...||..|.|..||.......||..|.|..||.......|...
 *    ...|.._____||.......||...|.||..|....___||....___||...|_|.|..|...|....|..|_|..||..|_|..||..._...||..|.|..||_....._|...
 *    ...|.|_____.|.......||...|_||_.|...|___.|...|___.|.......|..|...|....|.......||.......||..|.|..||..|_|..|..|...|.....
 *    ...|_____..||......_||....__..||....___||....___||.._....|..|...|___.|.......||_....._||..|_|..||.......|..|...|.....
 *    ...._____|.||.....|_.|...|..|.||...|___.|...|___.|.|.|...|..|.......||..._...|..|...|..|.......||.......|..|...|.....
 *    ...|_______||_______||___|..|_||_______||_______||_|..|__|..|_______||__|.|__|..|___|..|_______||_______|..|___|.....
 *    
 *       *NOT TO SCALE
 *    ___________________________________________________________________________________________
 *    |																							|
 *    |																							| Brick Number : Position (Pixel Location)
 *    |																							|	1	:	57-64
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	2	:	65-72
 *    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |	3	:	73-80
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	4	:	81-88
 *    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |	|	5	:	89-96
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	6	:	97-104
 *    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |	7	:	105-112
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	8	:	113-120
 *    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |	|	9	:	121-128
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	10	:	129-136
 *    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |	11	:	137-144
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	12	:	145-152
 *    |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18 |	13	:	153-160
 *    |---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----|	14	:	161-168
 *    |																							|	15	:	169-176
 *    |																							|	16	:	177-184
 *    |																							|	17	:	185-192
 *    |																							|	18	:	193-200
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |									  ------------											|	
 *    |									 |	 Paddle   |											|	
 *    |									  ------------											|	
 *    
 *   
 *    
 */