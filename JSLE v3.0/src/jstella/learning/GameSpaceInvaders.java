/**
 * Created By: Ian Cichy
 * 
 * Updated: March, 26th, 2017
 * 
 *    ...._______.._______.._______.._______.._______....___...__...._..__...__.._______..______..._______..______...._______....
 *    ...|.......||.......||..._...||.......||.......|..|...|.|..|..|.||..|.|..||..._...||......|.|.......||...._.|..|.......|...
 *    ...|.._____||...._..||..|_|..||.......||....___|..|...|.|...|_|.||..|_|..||..|_|..||.._....||....___||...|.||..|.._____|...
 *    ...|.|_____.|...|_|.||.......||.......||...|___...|...|.|.......||.......||.......||.|.|...||...|___.|...|_||_.|.|_____....
 *    ...|_____..||....___||.......||......_||....___|..|...|.|.._....||.......||.......||.|_|...||....___||....__..||_____..|...
 *    ...._____|.||...|....|..._...||.....|_.|...|___...|...|.|.|.|...|.|.....|.|..._...||.......||...|___.|...|..|.|._____|.|...
 *    ...|_______||___|....|__|.|__||_______||_______|..|___|.|_|..|__|..|___|..|__|.|__||______|.|_______||___|..|_||_______|... 
 */
 
package jstella.learning;
import java.awt.event.KeyEvent;

public class GameSpaceInvaders implements JSIGame {

	JSILearning JSI;

	public GameSpaceInvaders(JSILearning J){
		JSI = J;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getScore()
	 *  - - - - - - - - - - - - - - - - - - - 
	 * 	Space Invaders Score is 4 bits, 
	 * 		2 High Bits
	 * 		2 Low Bits
	 * 	Put together they make up the 4 digit score that is shown on screen.
	 *  They are stored as integer values in memory, but converted to hex values
	 *  to display on the screen. To deal with this we read the values as hex
	 *  and then parse them as an integer for the agent.
	 */
	public int getScore() {
		try{
			String highBits =  JSI.getMemoryAsHex()[102];
			String lowBits = JSI.getMemoryAsHex()[104];
			String score = highBits + "" + lowBits;
			return Integer.parseInt(score);
		}
		catch(NumberFormatException e){
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLives()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  The Player0 lives are kept at memory position 73,
	 *  so we simply return this value.
	 */
	public int getLives() {
		return JSI.getMemory()[73];
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getLevel()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Space Invaders does not track how many times
	 *  the screen is cleared, or level. 
	 */
	public int getLevel() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getValidActions()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Space Invaders only allows the player to move left, right and shoot.
	 *  These three actions result in the 6 possible action pairs. 
	 *  This includes {0} or the do nothing action.
	 */
	public int[][] getValidActions() {
		return new int[][] {
			{0},
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
		int[] mem = JSI.getMemory();
		int[] state = new int[3];

		int AlienPositionY = mem[16];
		int Row1Aliens = mem[18];
		int Row2Aliens = mem[19];
		int Row3Aliens = mem[20];
		int Row4Aliens = mem[21];
		int Row5Aliens = mem[22];
		int Row6Aliens = mem[23];
		int AlienPositionX = mem[26];
		int ShipPosition = mem[28]+1;
		int SaucerPositionX = mem[30];
		int AlienProjectileYOne = mem[81];
		int AlienProjectileYTwo = mem[82];
		int AlienProjectileXOne = mem[83];
		int AlienProjectileXTwo = mem[84];
		int ShipProjectileY = mem[85];
		int ShipProjectileX = mem[87];

		int mybullet = JSI.getMemory()[28]+9;
		int alienoffset = JSI.getMemory()[26];
		int underAliensRow = 0;
		int underBarrierNumber = 0;
		int AlienBulletOneOffset = 0;
		int AlienBulletTwoOffset = 0;
	
		
		//Alien Bullets
		if(AlienProjectileXOne > ShipPosition + 7)
			AlienBulletOneOffset = AlienProjectileXOne - ShipPosition - 7;
		else if(AlienProjectileXOne <= ShipPosition + 6)
			AlienBulletOneOffset = AlienProjectileXOne - ShipPosition - 6;
		else
			AlienBulletOneOffset = 0;

		if(AlienProjectileXTwo > ShipPosition + 7)
			AlienBulletTwoOffset = AlienProjectileXTwo - ShipPosition - 7;
		else if(AlienProjectileXTwo <= ShipPosition + 6)
			AlienBulletTwoOffset = AlienProjectileXTwo - ShipPosition - 6;
		else
			AlienBulletTwoOffset = 0;
		
		
		//========Barriers==============
		// Decides if a ball is under a barrier
		// Will say no if the barrier is less than 40% intact
		int barrier1 = JSI.getMemory()[43]+
				JSI.getMemory()[44]+
				JSI.getMemory()[45]+
				JSI.getMemory()[46]+
				JSI.getMemory()[47]+
				JSI.getMemory()[48]+
				JSI.getMemory()[49]+
				JSI.getMemory()[50]+
				JSI.getMemory()[51];
		
		int barrier2 = JSI.getMemory()[52]+
				JSI.getMemory()[53]+
				JSI.getMemory()[54]+
				JSI.getMemory()[55]+
				JSI.getMemory()[56]+
				JSI.getMemory()[57]+
				JSI.getMemory()[58]+
				JSI.getMemory()[59]+
				JSI.getMemory()[60];
		
		int barrier3 = JSI.getMemory()[61]+
				JSI.getMemory()[62]+
				JSI.getMemory()[63]+
				JSI.getMemory()[64]+
				JSI.getMemory()[65]+
				JSI.getMemory()[66]+
				JSI.getMemory()[67]+
				JSI.getMemory()[68]+
				JSI.getMemory()[69];
		
		if((mybullet >= 44 && mybullet <= 51) && barrier1 > 600)
			underBarrierNumber = 3;
		else if((mybullet >= 76 && mybullet <= 83) && barrier2 > 600)
			underBarrierNumber = 5;
		else if((mybullet >= 108 && mybullet <= 115) && barrier3 > 600)
			underBarrierNumber = 7;
		//========Barriers==============

		
		
		
		
		//===========================================================
		int hasAliens1 = 0, 
				hasAliens2 = 0,
				hasAliens3 = 0,
				hasAliens4 = 0,
				hasAliens5 = 0,
				hasAliens6 = 0;
		int max = 0;
		for(int x = 18; x < 24; x++)
			if(JSI.getMemory()[x]>max)
				max = JSI.getMemory()[x];
		String alienPositions = Integer.toBinaryString(max);
		alienPositions = new StringBuilder(alienPositions).reverse().toString();
		try{
			hasAliens1 = Integer.parseInt(alienPositions.substring(0,1)); 
			hasAliens2 = Integer.parseInt(alienPositions.substring(1,2)); 
			hasAliens3 = Integer.parseInt(alienPositions.substring(2,3));
			hasAliens4 = Integer.parseInt(alienPositions.substring(3,4));
			hasAliens5 = Integer.parseInt(alienPositions.substring(4,5));
			hasAliens6 = Integer.parseInt(alienPositions.substring(5));
		}catch(Exception e){}

		if(hasAliens1 == 1 && mybullet >= alienoffset+1 && mybullet <= alienoffset+8)
			underAliensRow = 1;
		else if(hasAliens2 == 1 && mybullet >= alienoffset+17 && mybullet <= alienoffset+24)
			underAliensRow = 1;
		else if(hasAliens3 == 1 && mybullet >= alienoffset+33 && mybullet <= alienoffset+40)
			underAliensRow = 1;
		else if(hasAliens4 == 1 && mybullet >= alienoffset+49 && mybullet <= alienoffset+56)
			underAliensRow = 1;
		else if(hasAliens5 == 1 && mybullet >= alienoffset+65 && mybullet <= alienoffset+72)
			underAliensRow = 1;
		else if(hasAliens6 == 1 && mybullet >= alienoffset+81 && mybullet <= alienoffset+88)
			underAliensRow = 1;
		//===========================================================

		state[0] = underAliensRow; // 0 or 1
		state[1] = AlienBulletOneOffset; // 
		state[2] = AlienBulletTwoOffset;
		//state[3] = ShipPosition; // Real X position
		
		/*
		state[0] = underBarrierNumber;
		state[1] = underAliensRow; // 1 2 3 4 5 6
		state[2] = AlienBulletOneOffset;
		state[3] = AlienBulletTwoOffset;
		state[4] = ShipPosition;
		*/

		/*
		state[0] = AlienPositionY;
		state[1] = Row1Aliens;
		state[2] = Row2Aliens;
		state[3] = Row3Aliens;
		state[4] = Row4Aliens;
		state[5] = Row5Aliens;
		state[6] = Row6Aliens;
		state[7] = AlienPositionX;
		state[8] = ShipPosition;
		state[9] = SaucerPositionX;
		state[10] = AlienProjectileYOne;
		state[11] = AlienProjectileYTwo;
		state[12] = AlienProjectileXOne;
		state[13] = AlienProjectileXTwo;
		state[14] = ShipProjectileY;
		state[15] = ShipProjectileX;
		 */
		 
		return state;
	}

	/*
	 * (non-Javadoc)
	 * @see jstella.learning.JSIGame#getAliveStatus()
	 *  - - - - - - - - - - - - - - - - - - - 
	 *  Memory position 101 holds a value that is always 0
	 *  while Player0 is alive, and non-zero otherwise.
	 *  Simply used as a boolean to tell if the game is still active.
	 */
	public boolean getAliveStatus() {
		return JSI.getMemory()[101] == 0;
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
 *    .______...._______..__...__....___......_______..__...__.._______..__...__.._______.
 *    |...._.|..|..._...||..|_|..|..|...|....|..._...||..|.|..||.......||..|.|..||.......|
 *    |...|.||..|..|_|..||.......|..|...|....|..|_|..||..|_|..||..._...||..|.|..||_....._|
 *    |...|_||_.|.......||.......|..|...|....|.......||.......||..|.|..||..|_|..|..|...|..
 *    |....__..||.......||.......|..|...|___.|.......||_....._||..|_|..||.......|..|...|..
 *    |...|..|.||..._...||.||_||.|..|.......||..._...|..|...|..|.......||.......|..|...|..
 *    |___|..|_||__|.|__||_|...|_|..|_______||__|.|__|..|___|..|_______||_______|..|___|..
 *    
 *    
 *    
 *    Memory Position : Values : Description
 *    0		:	0-5		:	
 *    1		:	0-255	:	Animates the Ship Upon Death & Spawn
 *    2		:	0-255	:	Detects Bullets Hitting Barrier Sections
 *    3		:	0-255	:	
 *    4		:	0-255	:	
 *    5		:	162		:	
 *    6		:	0-255	:	
 *    7		:	183		:	
 *    8		:	0-255	:	
 *    9		:	0-255	:	
 *    10	:	0-255	:	Detects Bullets Hitting Barrier Sections
 *    11	:	9,255	:	Controls the Look of Alien Sprites
 *    12	:	0,255	:	Nothing
 *    13	:	0,50,66,82,114,130	:	Controls the Limit of Alien Block Movement
 *    14	:	0,255	:	
 *    15	:	0,255	:	
 *    16	:	0,5,10...65,76		:	Y Position of Alien Block
 *    17	:	1-36	: 	How Often the Alien Block Moves (36) Slowest -> (1) Fastest
 *    18	:	0-63	:	Alive Aliens in Row 1 - - -
 *    19	:	0-63	:	Alive Aliens in Row 2 - Uses the 6 Low Order Bits
 *    20	:	0-63	:	Alive Aliens in Row 3 -	Where 1 is an Alive Alien
 *    21	:	0-63	:	Alive Aliens in Row 4 -	and 0 is a Dead Alien
 *    22	:	0-63	:	Alive Aliens in Row 5 -	(00111111) Normal Row
 *    23	:	0-63	:	Alive Aliens in Row 6 - - -
 *    24	:	16,17,18,19,80,81,82,83,144		:	Different SpaceShip States (1 Ship, 2 Ships, etc..)
 *    25	:	0			:	
 *    26	:	23-66		:	X Position of Alien Block
 *    27	:	43			:	X Position of Barriers
 *    28	:	35-117		:	X Position of SpaceShip
 *    29	:	117			:	
 *    30	:	1-151,180	:	X Position of FlyingSaucer (Flying 1 -> 151, else 180)
 *    31	:	0			:	
 *    32	:	36			:	
 *    33	:	63			:					
 *    34	:	63			:	
 *    35	:	63			:	
 *    36	:	63			:	
 *    37	:	63			:	
 *    38	:	63			:	
 *    39	:	110			:	
 *    40	:	0			:	
 *    41	:	23			:	
 *    42	:	0,1,4,128	:	Look of SpaceShip ((0) Normal, (1) Re-spawning, (4) Dying, (128) on Menu)
 *    43	:	0-60			:	Barrier 1 - - - - - - - - - - - - - -
 *    44	:	0-126			:	Barrier 1  - - - - - - - - - -
 *    45	:	0-126			:	Barrier 1 - - - - - -
 *    46	:	0-126			:	Barrier 1
 *    47	:	0-126			:	Barrier 1	All 3 Barriers Work the Same
 *    48	:	0-255			:	Barrier 1	  and Have the Same Shape
 *    49	:	0-255			:	Barrier 1
 *    50	:	0-255			:	Barrier 1
 *    51	:	0-195			:	Barrier 1
 *    52	:	0-60			:	Barrier 2		0 0 1 1 1 1 0 0 
 *    53	:	0-126			:	Barrier 2		0 1 1 1 1 1 1 0
 *    54	:	0-126			:	Barrier 2		0 1 1 1 1 1 1 0
 *    55	:	0-126			:	Barrier 2		0 1 1 1 1 1 1 0
 *    56	:	0-126			:	Barrier 2		0 1 1 1 1 1 1 0
 *    57	:	0-255			:	Barrier 2		1 1 1 1 1 1 1 1
 *    58	:	0-255			:	Barrier 2		1 1 1 1 1 1 1 1
 *    59	:	0-255			:	Barrier 2		1 1 1 1 1 1 1 1
 *    60	:	0-195			:	Barrier 2		1 1 0 0 0 0 1 1
 *    61	:	0-60			:	Barrier 3
 *    62	:	0-126			:	Barrier 3
 *    63	:	0-126			:	Barrier 3	Value Ranges are not Sequential
 *    64	:	0-126			:	Barrier 3	 They Follow Bitwise Patterns
 *    65	:	0-126			:	Barrier 3	    and Skip Many Values
 *    66	:	0-255			:	Barrier 3	
 *    67	:	0-255			:	Barrier 3 - - - - - -
 *    68	:	0-255			:	Barrier 3  - - - - - - - - - -
 *    69	:	0-195			:	Barrier 3 - - - - - - - - - - - - - -
 *    70	:	0-5,253-255		:	Counter for FlyingSaucer Appearing ((0-5) Count-down, (253-255) Ship on Screen)
 *    71	:	0,191-211		:	Menu Colors when Game is Over ((0) Playing Game, (Others) Player Died)
 *    72	:	0-255			:	
 *    73	:	0-3				:	Player0 Lives
 *    74	:	0-255			:
 *    75	:	0,1,2,6			:
 *    76	:	0,3,4,5			:
 *    77	:	0-72			:	Animation Timer for Alien and Spaceship Death
 *    78	:	0-79			:	Timer for Bullet Return
 *    79	:	4,8,12,16,20,24			:	
 *    80	:	4,8,12,16,20,24,28,32	:
 *    81	:	0-255 			:	Y Position of Alien Bullet 1
 *    82	:	0-255			:	Y Position of Alien Bullet 2
 *    83	:	27-134			:	X Position of Alien Bullet 1
 *    84	:	27-134			:	X Position of Alien Bullet 2
 *    85	:	0-255			:	Y Position of Player0 Bullet
 *    86	:	0-255			:	Y Position of Player1 Bullet
 *    87	:	27-134			:	X Position of Player0 Bullet
 *    88	:	27-134			:	X Position of Player1 Bullet
 *    89	:	0,80			:
 *    90	:	0-255			:
 *    91	:	24				:
 *    92	:	0-111			:	Controls Different Game Mode Features (Moving Barriers, Invisible Ships, etc...)
 *    93	:	0,52,131,229,231,241,243,245,247		:	(52) While Alive, (Others) While Dead
 *    94	:	0,82,129,131,145,147,149,151,229		:	(82) While Alive, (Others) While Dead
 *    95	:	0,1,3,5,7,21,23,115,196					:	(196) While Alive, (Others) While Dead
 *    96	:	0,37,39,49,51,53,55,65,246				:	(246) While Alive, (Others) While Dead
 *    97	:	0,20,163,197,199,209,211,213,215		:	(20) While Alive, (Others) While Dead
 *    98	:	0,7,176,192,194,196,198,212,214			:	(7) While Alive, (Others) While Dead
 *    99	:	0,183,193,195,197,199,209,211			:	(0) While Alive, (Others) While Dead
 *    100	:	0,33,35,37,39,49,51,85,226				:	(226) While Alive, (Others) While Dead
 *    101	:	0,128									:	(0) While Alive, (128) While Dead
 *    102	:	0-255		:	High Bits of Score Player0
 *    103	:	0-255		:	High Bits of Score Player1
 *    104	:	0-255		:	Low Bits of Score Player0
 *    105	:	0-255		:	Low Bits of Score Player1
 *    106	:	0-255		:
 *    107	:	1-63		:
 *    108	:	0			:	Nothing
 *    109	:	0,1,4,128	:	Look of SpaceShip ((0) Normal, (1) Re-spawning, (4) Dying, (128) on Menu)
 *    110	:	0-255		:
 *    111	:	0,1,2,3,4,5,9,252,255		:	
 *    112	:	0,171,180,189,255			:
 *    113	:	0							:
 *    114	:	0,1,40,50,120,130,189,255	:
 *    115	:	0,252		: 	Barriers Exist (0) Yes, (252) No
 *    116	:	0,2			:
 *    117	:	0			:
 *    118	:	0,5,16,21,32,37,48			:	Detects Collisions of Spaceship Bullet and Aliens
 *    119	:	0			:
 *    120	:	0,50,84,89,94,99			:	Different Spaceship flashing states
 *    121	:	0,252,255					:	Alternates Between 252,255 After Each Death
 *    122	:	0			:
 *    123	:	0			:
 *    124	:	148,167,219,235				:	
 *    125	:	251,253,254					:	
 *    126	:	7,22,109,156,166,192,233	:	
 *    127	:   242,243,244,245,254			:
 *    
 *     
 *    ._______.._______..______...._______.._______..__...._....___......_______..__...__.._______..__...__.._______.
 *    |.......||.......||...._.|..|.......||.......||..|..|.|..|...|....|..._...||..|.|..||.......||..|.|..||.......|
 *    |.._____||.......||...|.||..|....___||....___||...|_|.|..|...|....|..|_|..||..|_|..||..._...||..|.|..||_....._|
 *    |.|_____.|.......||...|_||_.|...|___.|...|___.|.......|..|...|....|.......||.......||..|.|..||..|_|..|..|...|..
 *    |_____..||......_||....__..||....___||....___||.._....|..|...|___.|.......||_....._||..|_|..||.......|..|...|..
 *    ._____|.||.....|_.|...|..|.||...|___.|...|___.|.|.|...|..|.......||..._...|..|...|..|.......||.......|..|...|..
 *    |_______||_______||___|..|_||_______||_______||_|..|__|..|_______||__|.|__|..|___|..|_______||_______|..|___|..
 *    
 *       *NOT TO SCALE
 *    ___________________________________________________________________________________________
 *    |																							|
 *    |																							|	Alien Column : Position (Pixel Offset from Memory Position[26])
 *    |				   C1		 C2		   C3		 C4		   C5		 C6 					|	1	:	1-8
 *    |			 	  ---- 	    ----      ----      ----      ----      ---- 				    |	2	:	17-24
 *    |			     | A6 |    |    |    |    |    |    |    |    |    |    | 				    |	3	:	33-40
 *    | 			  ---- 	    ----      ----      ----      ----      ---- 					|	4	:	49-56
 *    |  		     | A5 |    |    |    |    |    |    |    |    |    |    |					|	5	:	65-72
 *    | 			  ----      ---- 	  ----      ----      ----      ---- 					|	6	:	81-88
 *    |    		     | A4 |    |    |    |    |    |    |    |    |    |    |					|
 *    | 			  ----      ---- 	  ----      ----      ----      ---- 					|	Barrier Number : Offset (Pixels)
 *    |     		 | A3 |    |    |    |    |    |    |    |    |    |    |					|	1	:	44-51
 *    | 			  ----      ---- 	  ----      ----      ----      ---- 					|	2	:	76-83
 *    |   		     | A2 |    |    |    |    |    |    |    |    |    |    | 					|	3	:	108-115
 *    | 			  ----      ---- 	  ----      ----      ----      ---- 					|
 *    |  		     | A1 |    |    |    |    |    |    |    |    |    |    |					|
 *    | 			  ----      ----      ----      ----      ----      ---- 					|
 *    |																							|
 *    |																							|
 *    |																							|	
 *    |																							|	
 *    |					....					....					....					|	
 *    |				   ......				   ......				   ......					|	
 *    |				   ......				   ......				   ......					|	
 *    |				  ........				  ........				  ........					|
 *	  |				  ........				  ........				  ........					|
 *    |				  ..    ..				  ..    ..				  ..    ..					|	
 *    |				   																			|	
 *    |				   																			|	
 *    |																							|	
 *    |				  ____																		|	
 *    |				 _|	 |_ 																	|	
 *    |			|	|______|															|		|	
 *    	
 *    
 *    
 */
