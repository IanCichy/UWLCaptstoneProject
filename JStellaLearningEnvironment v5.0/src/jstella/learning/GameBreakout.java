/**
 * Created By: Ian Cichy
 * 
 * Updated: March, 26th, 2017
 * 
 *    ...._______..______...._______.._______..___..._.._______..__...__.._______....
 *    ...|.._....||...._.|..|.......||..._...||...|.|.||.......||..|.|..||.......|...
 *    ...|.|_|...||...|.||..|....___||..|_|..||...|_|.||..._...||..|.|..||_....._|...
 *    ...|.......||...|_||_.|...|___.|.......||......_||..|.|..||..|_|..|..|...|.....
 *    ...|.._...|.|....__..||....___||.......||.....|_.|..|_|..||.......|..|...|.....
 *    ...|.|_|...||...|..|.||...|___.|..._...||...._..||.......||.......|..|...|.....
 *    ...|_______||___|..|_||_______||__|.|__||___|.|_||_______||_______|..|___|.....
 */

package jstella.learning;
import java.awt.event.KeyEvent;

public class GameBreakout implements JSIGame {

	JSILearning JSI;

	public GameBreakout(JSILearning J){
		JSI = J;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	Breakout Score is 4 bits, 
	 * 		2 High Bits
	 * 		2 Low Bits
	 * 	Put together they make up the 4 digit score that is shown on screen.
	 *  They are stored as integer values in memory, but converted to hex values
	 *  to display on the screen. To deal with this we read the values as hex
	 *  and then parse them as an integer for the agent.
	 */
	public int getScore() {
		try{
			String highBits =  JSI.getMemoryAsHex()[76];
			String lowBits = JSI.getMemoryAsHex()[77];
			String value = highBits + "" + lowBits;
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
	 *  The Player0 lives are kept at memory position 57,
	 *  so we simply return this value.
	 */
	public int getLives() {
		return JSI.getMemory()[57];
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Breakout does not track how many times
	 *  the screen is cleared, or level. 
	 */
	public int getLevel() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Breakout only allows the player to move left, right and not to move.
	 *  These represent all 3 possible actions. 
	 */
	public int[][] getValidActions() {
		return new int[][] { 
			{KeyEvent.VK_LEFT},
			{KeyEvent.VK_RIGHT},
			{0}
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
	 *  Memory position 52 holds a value that is always 255
	 *  while Player0 is alive, and non-255 otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getPlayableStatus() {
		return JSI.getMemory()[52] == 255;
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
 *    Memory Position : Valid Values : Description
 *    0		:	0,3,12,15,48,51,60,63	:	Row 1 - Bricks 16-18  - Applies to Positions 0-5
 *    1		:	0,3,12,15,48,51,60,63	:	Row 2 - Bricks 16-18  - Decides which bricks to render based on the value
 *    2		:	0,3,12,15,48,51,60,63	:	Row 3 - Bricks 16-18  - Numbered Left -> Right (Low Order Bits)
 *    3		:	0,3,12,15,48,51,60,63	:	Row 4 - Bricks 16-18  -     ---- ---- ----
 *    4		:	0,3,12,15,48,51,60,63	:	Row 5 - Bricks 16-18  -    |  3 | 12 | 48 |
 *    5		:	0,3,12,15,48,51,60,63	:	Row 6 - Bricks 16-18  -     ---- ---- ----
 *    6		:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 1 - Bricks 12-15  - Applies to Positions 6-11
 *    7		:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 2 - Bricks 12-15  - Decides which bricks to render based on the value 
 *    8		:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 3 - Bricks 12-15  - Numbered Left <- Right
 *    9		:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 4 - Bricks 12-15  -     ---- ---- ---- ----
 *    10	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 5 - Bricks 12-15  -    |192 | 48 | 12 |  3 |
 *    11	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 6 - Bricks 12-15  -     ---- ---- ---- ----
 *    12	:	0,48,192,240	:	Row 1 - Bricks 10-11  - Applies to Positions 12-17
 *    13	:	0,48,192,240	:	Row 2 - Bricks 10-11  - Decides which bricks to render based on the value 
 *    14	:	0,48,192,240	:	Row 3 - Bricks 10-11  - Numbered Left -> Right (High Order Bits)
 *    15	:	0,48,192,240	:	Row 4 - Bricks 10-11  -     ---- ---- 
 *    16	:	0,48,192,240	:	Row 5 - Bricks 10-11  -    | 48 |192 | 
 *    17	:	0,48,192,240	:	Row 6 - Bricks 10-11  -     ---- ----  
 *    18	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 1 - Bricks 6-9  - Applies to Positions 18-23
 *    19	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 2 - Bricks 6-9  - Decides which bricks to render based on the value 
 *    20	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 3 - Bricks 6-9  - Numbered Left -> Right
 *    21	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 4 - Bricks 6-9  -     ---- ---- ---- ---- 
 *    22	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 5 - Bricks 6-9  -    |  3 | 12 | 48 |192 |
 *    23	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 6 - Bricks 6-9  -     ---- ---- ---- ---- 
 *    24	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 1 - Bricks 2-5  - Applies to Positions 24-29
 *    25	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 2 - Bricks 2-5  - Decides which bricks to render based on the value 
 *    26	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 3 - Bricks 2-5  - Numbered Left <- Right
 *    27	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 4 - Bricks 2-5  -     ---- ---- ---- ----
 *    28	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 5 - Bricks 2-5  -    |192 | 48 | 12 |  3 |
 *    29	:	0,3,12,15,48,51,60,63,192,195,204,207,240,243,252,255	:	Row 6 - Bricks 2-5  -     ---- ---- ---- ----
 *    30	:	0,192	:	Row 1 - Brick 1  - Applies to Positions 30-35
 *    31	:	0,192	:	Row 2 - Brick 1  - Decides which bricks to render based on the value 
 *    32	:	0,192	:	Row 3 - Brick 1  - (High Order Bits)
 *    33	:	0,192	:	Row 4 - Brick 1  -     ---- 
 *    34	:	0,192	:	Row 5 - Brick 1  -    |192 |
 *    35	:	0,192	:	Row 6 - Brick 1  -     ---- 
 *    36	:	0,1,3,7,15,31,63,255	:  Animates a Section of Bricks Upon Game Reset
 *    37	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    38	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    39	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    40	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    41	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    42	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    43	:	0,3,15,63,255			:  Animates a Section of Bricks Upon Game Reset
 *    44	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    45	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    46	:	0,3,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    47	:	0,3,31,255				:  Animates a Section of Bricks Upon Game Reset
 *    48	:	0,7,63,255				:  Animates a Section of Bricks Upon Game Reset
 *    49	:	0,1,240					:  Animates a Section of Bricks Upon Game Reset
 *    50	: 	0-255		:	Mode of Game (1-player, 2-player, breakout, breakthrough, etc..)
 *    51	: 	0-255		:	Mode of Game (1-player, 2-player, breakout, breakthrough, etc..)
 *    52	:	0,255		:	inGame Bit (0 if dead or in menu, 255 if playing)
 *    53	: 	0			:	Nothing
 *    54	:   0			:	Paddle Moveable (MUST BE 0)
 *    55	:	0,192,224	:	
 *    56	:	0,255		:	Secondary Paddle (No (0), Yes (255))
 *    57	: 	0,1,2,3,4,5	:	Player0 Lives (Balls Remaining)
 *    58	: 	0			:	Nothing
 *    59	: 	0			:	Nothing
 *    60	: 	6			:	Nothing
 *    61	:	0			:	Nothing
 *    62	: 	70			:	Ball & Paddle Color
 *    63	:	182			:	Edge of screen Indicator Color
 *    64	: 	134			:	Row 1 Brick Color
 *    65	: 	198			:	Row 2 Brick Color
 *    66	: 	22			:	Row 3 Brick Color
 *    67	: 	38			:	Row 4 Brick Color
 *    68	: 	54			:	Row 5 Brick Color
 *    69	: 	70 			:	Row 6 Brick Color
 *    70	: 	0-186		:	Paddle Position Limit (Left (0) <- Right (186))
 *    71	:	0-255		:	Counter
 *    72	: 	55-191		:	Paddle X Position (Left (55) -> Right (191))
 *    73	:	0			:	Nothing
 *    74	:	0-255		:	Counter
 *    75	:	0-255		:	Counter
 *    76	: 	0-255		: 	Upper Bits of Score value (Stored as an Integer, displayed on screen as a Hex value)
 *    77	: 	0-255		: 	Lower Bits of Score value (Stored as an Integer, displayed on screen as a Hex value)
 *    78	:	0			:	Nothing
 *    79	:	0			:	Nothing
 *    80	:	0,5,10								:	Offset for Score
 *    81	: 	241									: 	High Digit of Score Display
 *    82	:	0,5,10,15,20,25,30,35,40,45,50		:	Decides Which Digit to Display or None
 *    83	: 	242									:	Middle Digit of Score Display
 *    84	:	0,5,10,15,20,25,30,35,40,45			:	Decides Which Digit to Display or None
 *    85	: 	242									:	Low Digit of Score Display
 *    86	:	0,5,10,15,20,25						:	Decides Which Digit to Display or None
 *    87	: 	241									:	Lives Digit Display
 *    88	:	5									:	Decides Which Digit to Display or None
 *    89	: 	242									: 	Level Number Display
 *    90	:	0-255		:	Counter
 *    91	:	0-255		:	Counter
 *    92	:	0,255		:	
 *    93	:	0-255		: 	Color Filter for Screen in Menu or When Dead
 *    94	:	0-255		:
 *    95	:	0-5,64-71,128-135		:	Changes in relation to ball hits (ex.. 64-64-(ball hit)-135-134-133-132-131-130-129-128-128)
 *    96	:	0,28,22,18,16,12,10		:	Last Row of Bricks Hit ((28) Bottom Row -> (10) Top Row)
 *    97	:	0					:	Nothing
 *    98	:	0					:	Nothing
 *    99	: 	57-198				:	Ball X Position
 *    100	:	0,128				:	Relates to Direction of Ball
 *    101	: 	25-190				:	Ball Y Position
 *    102	:	0,128				:	Relates to Direction of Ball
 *    103	: 	0,1,2,3,252,254,255	:	Ball X Direction
 *    104	:	0,128				:	Relates to Direction of Ball
 *    105	: 	0,1,2,254,255		:	Ball Y Direction
 *    106	: 	0,128				:	Ball X Speed
 *    107	: 	0,6,16,65,66,67,68,69,70,71,72,73,74,75,76,129,130,131,132,133,134,135,136,137,138,139,140,144	:	Ball Y Speed
 *    108	:	0,4,6				:
 *    109 	: 	0,2					:	Type of Bounce (Normal,Sharp)
 *    110	:	0,255				:	(255) While Playing Game
 *    111	:	0,255				:	(255) While Playing Game
 *    112	:	0,255				:	(255) While Playing Game
 *    113	:	0,255				:	(255) While Playing Game
 *    114	:	0,255				:	(255) While Playing Game
 *    115	:	0,255				:	(255) While Playing Game
 *    116	:	0,255				:	(255) While Playing Game
 *    117	:	0					:	Nothing
 *    118	:	0					:	Nothing	
 *    119	:	0-5,25				: 	Animates a Section of Bricks Upon Game Reset
 *    120	:	0					:	Nothing
 *    121	:	0,150				:	
 *    122	:	150,186,246			:	
 *    123	:	214,241				:	
 *    124	:	70,117				:	
 *    125	:	264,247				:	
 *    126	:	219					:	
 *    127   :	242					:	
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
