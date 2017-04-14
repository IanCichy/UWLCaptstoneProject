/**
 * Created By: Ian Cichy
 * 
 * Updated: March, 26th, 2017
 * 
 *    ....__...__..___..._______.._______..___...___......_______....._______.._______..__...__..__...__.._______..__...._..______.....
 *    ...|..|_|..||...|.|.......||.......||...|.|...|....|.......|...|.......||.......||..|_|..||..|_|..||..._...||..|..|.||......|....
 *    ...|.......||...|.|.._____||.._____||...|.|...|....|....___|...|.......||..._...||.......||.......||..|_|..||...|_|.||.._....|...
 *    ...|.......||...|.|.|_____.|.|_____.|...|.|...|....|...|___....|......_||..|.|..||.......||.......||.......||.......||.|.|...|...
 *    ...|.......||...|.|_____..||_____..||...|.|...|___.|....___|...|.....|..|..|_|..||.......||.......||.......||.._....||.|_|...|...
 *    ...|.||_||.||...|.._____|.|._____|.||...|.|.......||...|___....|.....|_.|.......||.||_||.||.||_||.||..._...||.|.|...||.......|...
 *    ...|_|...|_||___|.|_______||_______||___|.|_______||_______|...|_______||_______||_|...|_||_|...|_||__|.|__||_|..|__||______|....
 */

package jstella.learning;

public class GameMissileCommand implements JSIGame {

	JSILearning JSI;

	public GameMissileCommand(JSILearning J){
		JSI = J;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	Missile Command Score is 6 bits, 
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
			String highBits =  JSI.getMemoryAsHex()[115];
			String middleBits = JSI.getMemoryAsHex()[113];
			String lowBits = JSI.getMemoryAsHex()[111];

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
	 *  The 'lives' for Space Invaders is a count of how many
	 *  cities are still alive. There are 6 cities, one each at
	 *  memory positions 41,43,45,47,49,51. We return the sum of 
	 *  all 6 positions.
	 */
	public int getLives() {
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
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Space Invaders level is kept at memory position 103,
	 *  so we simply return this value.
	 */
	public int getLevel() {
		return JSI.getMemory()[103];
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Space Invaders allows all 18 possible system actions. 
	 *  Since the JSIGame class has a default method for this, 
	 *  we will skip one here.
	 */

	/**
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getState()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Gives the current state information to the agent, 
	 *  varies depending on the algorithm, test, and goals.
	 */
	public int[] getState() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getAliveStatus()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Memory position 52 holds a value that is always 255
	 *  while Player0 is alive, and non-255 otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getAliveStatus() {
		return false;
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
 *    0		:	0-255		:	Game Timer (Needed to run the game)
 *    1		:	0-255		:
 *    2		:	0-255		:
 *    3		:	0-84		:
 *    4		:	0-255		:
 *    5		:	0-255		:
 *    6		:	0-255		:
 *    7		:	0-255		:
 *    8		:	0-255		:
 *    9		:	0-80		:
 *    10	:	9-151	 	:	X Position of Cursor (Possible Not Real Value ???)
 *    11	:	0-255		:		
 *    12	:	0,254,255	:
 *    13	:	0			:
 *    14	:	10-83		:	Y Position of Cursor
 *    15	:	0			:
 *    16	:	9-151	 	:	X Position of Cursor
 *    17	:	0,1,2,3,4,6,255		:	Number of Missiles ((0) When [18] has a Value, Else Value X)
 *    18	:	0,1,2,3,4,6,255		:	Number of Missiles ((0) When [17] has a Value, Else Value X)
 *    19	:	0-85		:	Y Position of Missiles (Always Matches [20])
 *    20	:	0-85		: 	Y Position of Missiles (Always Matches [19])
 *    21	:	0-255		:	X Position of Missiles (Always Matches [22])
 *    22	:	0-255		:	X Position of Missiles (Always Matches [21])
 *    23	:	0,16,240	:	Draws Missile Trails (Swaps Between (16 & 240) while [24] is the other)
 *    24	:	0,16,240	:	Draws Missile Trails (Swaps Between (16 & 240) while [23] is the other)
 *    25	:	0-255		:	X Position Offset for Missile Spawning (Alternately Paired with [26])
 *    26	:	0-255		:	X Position Offset for Missile Spawning (Alternately Paired with [25])
 *    27	:	32,45,58...	:	Speed of Missiles
 *    28	:	0-255		:	Causes Missile to Move Downward	(Value will be Next Value for [29])
 *    29	:	0-255		:	Causes Missile to Move Downward	(Value is Always the Previous Value of [28])
 *    30	:	0-255		:	Animation Timer for Player Bullet Explosion 3
 *    31	:	0-255		:	Animation Timer for Player Bullet Explosion 2	
 *    32	:	0-255		:	Animation Timer for Player Bullet Explosion 1
 *    33	:	240,248		:	
 *    34	:	0,8,128,136	:	Decides which Player Bullet to Fire, or None if all Three are out
 *    35	:	0-255		: --
 *    36	:	0-255		: -
 *    37	:	0-255		: Detects Collisions with Missiles
 *    38	:	0-255		: -
 *    39	:	0-255		: -
 *    40	:	0-255		: --
 *    41	:	0,56,67,76,85,94,103,112	:	City 1 ((56) Alive, (Others) Dying Animation)
 *    42	:	255							:
 *    43	:	0,56,67,76,85,94,103,112	:	City 2 ((56) Alive, (Others) Dying Animation)
 *    44	:	255							:
 *    45	:	0,56,67,76,85,94,103,112	:	City 3 ((56) Alive, (Others) Dying Animation)
 *    46	:	255							:
 *    47	:	0,56,67,76,85,94,103,112	:	City 4 ((56) Alive, (Others) Dying Animation)
 *    48	:	255							:
 *    49	:	0,56,67,76,85,94,103,112	:	City 5 ((56) Alive, (Others) Dying Animation)
 *    50	:	255							:
 *    51	:	0,56,67,76,85,94,103,112	:	City 6 ((56) Alive, (Others) Dying Animation)
 *    52	:	255							:
 *    53	: 	0,128,160,192,224	:	Bullet 3 Animations -- ((0) None,		
 *    54	:	0,128,160,192,224	:	Bullet 2 Animations -   (128, 192) In Transit,
 *    55	:	0,128,160,192,224	:	Bullet 1 Animations --  (160, 224) Exploding)
 *    56	:	0-81	:	Bullet 3 Distance Left Until Destination Y
 *    57	:	0-81	:	Bullet 2 Distance Left Until Destination Y
 *    58	:	0-81	:	Bullet 1 Distance Left Until Destination Y
 *    59	:	9-82	:	Bullet 3 Total Distance to Destination Y
 *    60	:	9-82	:	Bullet 2 Total Distance to Destination Y
 *    61	:	9-82	:	Bullet 1 Total Distance to Destination Y
 *    62	:	1-72	:	Bullet 3 Total Distance to Destination X
 *    63	:	1-72	:	Bullet 2 Total Distance to Destination X
 *    64	:	1-72	:	Bullet 1 Total Distance to Destination X
 *    65	:	0-80	:	Bullet 3 Distance Left Until Destination X
 *    66	:	0-80	:	Bullet 2 Distance Left Until Destination X
 *    67	:	0-80	:	Bullet 1 Distance Left Until Destination X
 *    68	:	0-79	:	Bullet 3 X Position (Default 79)
 *    69	:	0-79	:	Bullet 2 X Position	(Default 79)
 *    70	:	0-79	:	Bullet 1 X Position	(Default 79)
 *    71	:	0-83	:	Bullet 3 Y Position	(Default 1)
 *    72	:	0-83	:	Bullet 2 Y Position	(Default 1)
 *    73	:	0-83	:	Bullet 1 Y Position	(Default 1)
 *    74	:	0,1,2	:	Animation of Bullet Explosion
 *    75	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 1
 *    76	:	0,255											:	Display of Score Digit 1
 *    77	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 2
 *    78	:	0,255											:	Display of Score Digit 2
 *    79	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 3
 *    80	:	0,255											:	Display of Score Digit 3
 *    81	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 4
 *    82	:	0,255											:	Display of Score Digit 4
 *    83	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 5
 *    84	:	0,255											:	Display of Score Digit 5
 *    85	:	0,48,136,143,150,157,164,171,178,185,192,199 	:	Selects the Digit to Display for Digit 6
 *    86	:	0,255											:	Display of Score Digit 6
 *    87	:	0,7,15	:
 *    88	:	0-255	:
 *    89	:	0-255	:
 *    90	:	0-255	:
 *    91	:	64-67,135,195,199	:
 *    92	:	0-255	:
 *    93	:	0-10	:	Ammo in Current Clip
 *    94	:	0-255	:	Counter to Manage level clearing activities, Also Counts Number of Missiles Yet to Fire in a level
 *    95	:	0,1,2,4,5,7,16,18,32,34,48,50,64,66		:	Changes the Background and Cities State Depending on Game State
 *    96	:	0-19	:	Used to Add Score for Intact Cities and Other End Level Tasks
 *    97	:	0-255	:
 *    98	:	0-255	:	Changes the Background Color When a City is Destroyed
 *    99	:	0-255	:
 *    100	:	0-255	:
 *    101	:	0-255	:	
 *    102	:	0-255	:
 *    103	: 	0-255	:	Game Level
 *    104	:	0-255	:	Timer
 *    105	:	1-52	:	Mode of Game ((1) Default)
 *    106	:	255		:
 *    107	:	0-255	:	Counts Number of Enemy Bullets Yet to Fire in a level
 *    108	:	0-63	:	Displays Cities (Based on Binary String (1 1 1 1 1 1))
 *    109	:	0		:
 *    110	:	0,16,21	:	Color of Bullet Explosion
 *    111	:	0-255	:	High Bits of Game Score
 *    112	:	0		:
 *    113	:	0-255	:	Middle Bits of Game Score
 *    114	:	0		:
 *    115	:	0-255	:	Low Bits of Game Score
 *    116	:	0		:
 *    117	:	0		:	Must be Zero
 *    118	:	0		:
 *    119	:	0		:
 *    120	:	0		:
 *    121	:	0		:
 *    122	:	0		:
 *    123	:	0,255	:	Game Over (Strobes (255) Upon Game Loss)
 *    124	:	0,120,212	:
 *    125	:	251		:
 *    126	:	0,43,130,141,151,233,189	:
 *    127	:	0,249,250	:
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
 *    |																							|
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    |																							|	
 *    
 *   
 *    
 */