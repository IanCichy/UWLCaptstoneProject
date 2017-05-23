package jstella.learning;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class AgentPrintMemory implements JSIAgent {

	private JSILearning JSI;

	int counter = 0;
	ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();	

	public AgentPrintMemory(JSILearning J){	
		/**
		 * Set JSILearning Object
		 */
		JSI = J;

		/**
		 * Boolean Flags To Set
		 */
		JSI.setSoundEnabled(false); //Default Value : true
		//JSI.setKeysEnabled(true); //Default Value : true
		//JSI.setMouseEnabled(false); //Default Value : true
		//JSI.setVideoEnabled(true);  //Default Value : true

		/**
		 * Variables Related To Emulation
		 */
		//JSI.setFrameDelay(1); //Default Value : 17 (60 frames a second)
		//JSI.setAgentCallDelay(4); //Default Value : 1 (Agent called every frame)


		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(new File("src/ROM/Breakout.bin"));

		/**
		 * Section 1
		 */
		//for(int x = 0; x <128; x++){
		//	values.add(x,new ArrayList<Integer>());
		//}




	}

	/**
	 * This method will record the values in all 128 bytes of memory based on uniqueness and position.
	 *  A file will then be created that has 128 row, 1 for each memory position.
	 *  Each row will contain each unique value that was seen while the game was running.
	 *  
	 *  File save will happen ever 60 seconds.
	 */
	private void recordMemoryValues(){
		int[] mem = JSI.getMemory();

		for(int x = 0; x < mem.length; x++)
			if(!values.get(x).contains(mem[x]))
				values.get(x).add(mem[x]);
		counter++;

		if(counter>=6000){
			System.out.println("Exporting to file...");
			counter = 0;
			try{
				PrintWriter writer = new PrintWriter(new FileWriter("GameMemoryState.txt"));

				for(int x = 0; x < values.size(); x++){
					Collections.sort(values.get(x));
					for(int y = 0; y < values.get(x).size(); y++){
						writer.write(values.get(x).get(y) + " ");
					}
					writer.write("\n");
				}			
				writer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public int[] getAction(){
		int[] mem = JSI.getMemory();

		/** Also uncomment section 1 in the constructor
		 *  Section 1.1
		 */
		//recordMemoryValues();

		/** Secion 2
		 *  Use this to print all 128 bytes of memory each frame
		 */
		//for(int x : mem)
		//	System.out.print(x + ",");
		//System.out.println();

		/**
		 * Section 3
		 * Allows the user to change the value in memory at the specified location
		 * This method has no guarantee of game performance or stability
		 * and may cause the emulator to crash or error
		 */
		/*
		JSI.DEVgetMemoryNoClone()[0] = 50;
		JSI.DEVgetMemoryNoClone()[1] = 50;
		JSI.DEVgetMemoryNoClone()[2] = 50;
		JSI.DEVgetMemoryNoClone()[3] = 50;
		JSI.DEVgetMemoryNoClone()[4] = 50;
		JSI.DEVgetMemoryNoClone()[5] = 50;
		JSI.DEVgetMemoryNoClone()[6] = 50;
		JSI.DEVgetMemoryNoClone()[7] = 50;
		JSI.DEVgetMemoryNoClone()[8] = 50;
		JSI.DEVgetMemoryNoClone()[9] = 50;
		JSI.DEVgetMemoryNoClone()[10] = 50;
		JSI.DEVgetMemoryNoClone()[11] = 50;
		JSI.DEVgetMemoryNoClone()[12] = 50;
		JSI.DEVgetMemoryNoClone()[13] = 50;
		JSI.DEVgetMemoryNoClone()[14] = 50;
		JSI.DEVgetMemoryNoClone()[15] = 50;
		JSI.DEVgetMemoryNoClone()[16] = 50;
		JSI.DEVgetMemoryNoClone()[17] = 50;
		JSI.DEVgetMemoryNoClone()[18] = 50;
		JSI.DEVgetMemoryNoClone()[19] = 50;
		JSI.DEVgetMemoryNoClone()[20] = 50;
		JSI.DEVgetMemoryNoClone()[21] = 50;
		JSI.DEVgetMemoryNoClone()[22] = 50;
		JSI.DEVgetMemoryNoClone()[23] = 50;
		JSI.DEVgetMemoryNoClone()[24] = 50;
		JSI.DEVgetMemoryNoClone()[25] = 50;
		JSI.DEVgetMemoryNoClone()[26] = 50;
		JSI.DEVgetMemoryNoClone()[27] = 50;
		JSI.DEVgetMemoryNoClone()[28] = 50;
		JSI.DEVgetMemoryNoClone()[29] = 50;
		JSI.DEVgetMemoryNoClone()[30] = 50;
		JSI.DEVgetMemoryNoClone()[31] = 50;
		JSI.DEVgetMemoryNoClone()[32] = 50;
		JSI.DEVgetMemoryNoClone()[33] = 50;
		JSI.DEVgetMemoryNoClone()[34] = 50;
		JSI.DEVgetMemoryNoClone()[35] = 50;
		JSI.DEVgetMemoryNoClone()[36] = 50;
		JSI.DEVgetMemoryNoClone()[37] = 50;
		JSI.DEVgetMemoryNoClone()[38] = 50;
		JSI.DEVgetMemoryNoClone()[39] = 50;
		JSI.DEVgetMemoryNoClone()[40] = 50;
		JSI.DEVgetMemoryNoClone()[41] = 50;
		JSI.DEVgetMemoryNoClone()[42] = 50;
		JSI.DEVgetMemoryNoClone()[43] = 50;
		JSI.DEVgetMemoryNoClone()[44] = 50;
		JSI.DEVgetMemoryNoClone()[45] = 50;
		JSI.DEVgetMemoryNoClone()[46] = 50;
		JSI.DEVgetMemoryNoClone()[47] = 50;
		JSI.DEVgetMemoryNoClone()[48] = 50;
		JSI.DEVgetMemoryNoClone()[49] = 50;
		JSI.DEVgetMemoryNoClone()[50] = 50;
		JSI.DEVgetMemoryNoClone()[51] = 50;
		JSI.DEVgetMemoryNoClone()[52] = 50;
		JSI.DEVgetMemoryNoClone()[53] = 50;
		JSI.DEVgetMemoryNoClone()[54] = 50;
		JSI.DEVgetMemoryNoClone()[55] = 50;
		JSI.DEVgetMemoryNoClone()[56] = 50;
		JSI.DEVgetMemoryNoClone()[57] = 50;
		JSI.DEVgetMemoryNoClone()[58] = 50;
		JSI.DEVgetMemoryNoClone()[59] = 50;
		JSI.DEVgetMemoryNoClone()[60] = 50;
		JSI.DEVgetMemoryNoClone()[61] = 50;
		JSI.DEVgetMemoryNoClone()[62] = 50;
		JSI.DEVgetMemoryNoClone()[63] = 50;
		JSI.DEVgetMemoryNoClone()[64] = 50;
		JSI.DEVgetMemoryNoClone()[65] = 50;
		JSI.DEVgetMemoryNoClone()[66] = 50;
		JSI.DEVgetMemoryNoClone()[67] = 50;
		JSI.DEVgetMemoryNoClone()[68] = 50;
		JSI.DEVgetMemoryNoClone()[69] = 50;
		JSI.DEVgetMemoryNoClone()[70] = 50;
		JSI.DEVgetMemoryNoClone()[71] = 50;
		JSI.DEVgetMemoryNoClone()[72] = 50;
		JSI.DEVgetMemoryNoClone()[73] = 50;
		JSI.DEVgetMemoryNoClone()[74] = 50;
		JSI.DEVgetMemoryNoClone()[75] = 50;
		JSI.DEVgetMemoryNoClone()[76] = 50;
		JSI.DEVgetMemoryNoClone()[77] = 50;
		JSI.DEVgetMemoryNoClone()[78] = 50;
		JSI.DEVgetMemoryNoClone()[79] = 50;
		JSI.DEVgetMemoryNoClone()[80] = 50;
		JSI.DEVgetMemoryNoClone()[81] = 50;
		JSI.DEVgetMemoryNoClone()[82] = 50;
		JSI.DEVgetMemoryNoClone()[83] = 50;
		JSI.DEVgetMemoryNoClone()[84] = 50;
		JSI.DEVgetMemoryNoClone()[85] = 50;
		JSI.DEVgetMemoryNoClone()[86] = 50;
		JSI.DEVgetMemoryNoClone()[87] = 50;
		JSI.DEVgetMemoryNoClone()[88] = 50;
		JSI.DEVgetMemoryNoClone()[89] = 50;
		JSI.DEVgetMemoryNoClone()[90] = 50;
		JSI.DEVgetMemoryNoClone()[91] = 50;
		JSI.DEVgetMemoryNoClone()[92] = 50;
		JSI.DEVgetMemoryNoClone()[93] = 50;
		JSI.DEVgetMemoryNoClone()[94] = 50;
		JSI.DEVgetMemoryNoClone()[95] = 50;
		JSI.DEVgetMemoryNoClone()[96] = 50;
		JSI.DEVgetMemoryNoClone()[97] = 50;
		JSI.DEVgetMemoryNoClone()[98] = 50;
		JSI.DEVgetMemoryNoClone()[99] = 50;
		JSI.DEVgetMemoryNoClone()[100] = 50;
		JSI.DEVgetMemoryNoClone()[101] = 50;
		JSI.DEVgetMemoryNoClone()[102] = 50;
		JSI.DEVgetMemoryNoClone()[103] = 50;
		JSI.DEVgetMemoryNoClone()[104] = 50;
		JSI.DEVgetMemoryNoClone()[105] = 50;
		JSI.DEVgetMemoryNoClone()[106] = 50;
		JSI.DEVgetMemoryNoClone()[107] = 50;
		JSI.DEVgetMemoryNoClone()[108] = 50;
		JSI.DEVgetMemoryNoClone()[109] = 50;
		JSI.DEVgetMemoryNoClone()[110] = 50;
		JSI.DEVgetMemoryNoClone()[111] = 50;
		JSI.DEVgetMemoryNoClone()[112] = 50;
		JSI.DEVgetMemoryNoClone()[113] = 50;
		JSI.DEVgetMemoryNoClone()[114] = 50;
		JSI.DEVgetMemoryNoClone()[115] = 50;
		JSI.DEVgetMemoryNoClone()[116] = 50;
		JSI.DEVgetMemoryNoClone()[117] = 50;
		JSI.DEVgetMemoryNoClone()[118] = 50;
		JSI.DEVgetMemoryNoClone()[119] = 50;
		JSI.DEVgetMemoryNoClone()[120] = 50;
		JSI.DEVgetMemoryNoClone()[121] = 50;
		JSI.DEVgetMemoryNoClone()[122] = 50;
		JSI.DEVgetMemoryNoClone()[123] = 50;
		JSI.DEVgetMemoryNoClone()[124] = 50;
		JSI.DEVgetMemoryNoClone()[125] = 50;
		JSI.DEVgetMemoryNoClone()[126] = 50;
		JSI.DEVgetMemoryNoClone()[127] = 50;
		 */


		/**
		 * Section 4
		 * Allows the user to print out the value at the specified
		 * location in memory
		 */
		/*
		System.out.println(mem[0]);
		System.out.println(mem[1]);
		System.out.println(mem[2]);
		System.out.println(mem[3]);
		System.out.println(mem[4]);
		System.out.println(mem[5]);
		System.out.println(mem[6]);
		System.out.println(mem[7]);
		System.out.println(mem[8]);
		System.out.println(mem[9]);
		System.out.println(mem[10]);
		System.out.println(mem[11]);
		System.out.println(mem[12]);
		System.out.println(mem[13]);
		System.out.println(mem[14]);
		System.out.println(mem[15]);
		System.out.println(mem[16]);
		System.out.println(mem[17]);
		System.out.println(mem[18]);
		System.out.println(mem[19]);
		System.out.println(mem[20]);
		System.out.println(mem[21]);
		System.out.println(mem[22]);
		System.out.println(mem[23]);
		System.out.println(mem[24]);
		System.out.println(mem[25]);
		System.out.println(mem[26]);
		System.out.println(mem[27]);
		System.out.println(mem[28]);
		System.out.println(mem[29]);
		System.out.println(mem[30]);
		System.out.println(mem[31]);
		System.out.println(mem[32]);
		System.out.println(mem[33]);
		System.out.println(mem[34]);
		System.out.println(mem[35]);
		System.out.println(mem[36]);
		System.out.println(mem[37]);
		System.out.println(mem[38]);
		System.out.println(mem[39]);
		System.out.println(mem[40]);
		System.out.println(mem[41]);
		System.out.println(mem[42]);
		System.out.println(mem[43]);
		System.out.println(mem[44]);
		System.out.println(mem[45]);
		System.out.println(mem[46]);
		System.out.println(mem[47]);
		System.out.println(mem[48]);
		System.out.println(mem[49]);
		System.out.println(mem[50]);
		System.out.println(mem[51]);
		System.out.println(mem[52]);
		System.out.println(mem[53]);
		System.out.println(mem[54]);
		System.out.println(mem[55]);
		System.out.println(mem[56]);
		System.out.println(mem[57]);
		System.out.println(mem[58]);
		System.out.println(mem[59]);
		System.out.println(mem[60]);
		System.out.println(mem[61]);
		System.out.println(mem[62]);
		System.out.println(mem[63]);
		System.out.println(mem[64]);
		System.out.println(mem[65]);
		System.out.println(mem[66]);
		System.out.println(mem[67]);
		System.out.println(mem[68]);
		System.out.println(mem[69]);
		System.out.println(mem[70]);
		System.out.println(mem[71]);
		System.out.println(mem[72]);
		System.out.println(mem[73]);
		System.out.println(mem[74]);
		System.out.println(mem[75]);
		System.out.println(mem[76]);
		System.out.println(mem[77]);
		System.out.println(mem[78]);
		System.out.println(mem[79]);
		System.out.println(mem[80]);
		System.out.println(mem[81]);
		System.out.println(mem[82]);
		System.out.println(mem[83]);
		System.out.println(mem[84]);
		System.out.println(mem[85]);
		System.out.println(mem[86]);
		System.out.println(mem[87]);
		System.out.println(mem[88]);
		System.out.println(mem[89]);
		System.out.println(mem[90]);
		System.out.println(mem[91]);
		System.out.println(mem[92]);
		System.out.println(mem[93]);
		System.out.println(mem[94]);
		System.out.println(mem[95]);
		System.out.println(mem[96]);
		System.out.println(mem[97]);
		System.out.println(mem[98]);
		System.out.println(mem[99]);
		System.out.println(mem[100]);
		System.out.println(mem[101]);
		System.out.println(mem[102]);
		System.out.println(mem[103]);
		System.out.println(mem[104]);
		System.out.println(mem[105]);
		System.out.println(mem[106]);
		System.out.println(mem[107]);
		System.out.println(mem[108]);
		System.out.println(mem[109]);
		System.out.println(mem[110]);
		System.out.println(mem[111]);
		System.out.println(mem[112]);
		System.out.println(mem[113]);
		System.out.println(mem[114]);
		System.out.println(mem[115]);
		System.out.println(mem[116]);
		System.out.println(mem[117]);
		System.out.println(mem[118]);
		System.out.println(mem[119]);
		System.out.println(mem[120]);
		System.out.println(mem[121]);
		System.out.println(mem[122]);
		System.out.println(mem[123]);
		System.out.println(mem[124]);
		System.out.println(mem[125]);
		System.out.println(mem[126]);
		System.out.println(mem[127]);
		 */


		//DO NOT MODIFY CODE BELOW THIS COMMENT
		return new int[] {0};
	}

}
