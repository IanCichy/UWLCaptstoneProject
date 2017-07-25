package jstella.learning;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class AgentPrintMemory implements JSIAgent {

	private JSILearning JSI;
int x = 5;
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
		 * Variables Related To Images
		 */
		//JSI.setImagesEnabled(true); //Default Value : false
		//JSI.setFramesToAverage(10); //This is the default value
		//JSI.setframeSampleRate(1); //This is the default value
		
		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(new File("src/ROM/DonkeyKong.bin"));

		/**
		 * Section 1
		 */
		/*
		for(int x = 0; x <128; x++){
			values.add(x,new ArrayList<Integer>());
		}
		 */



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
		
		/*
		x++;
		
		BufferedImage screen = JSI.getGameImage();
		if(JSI.getGameImage()!= null){
	        try {
	            ImageIO.write(screen, "png",
	                    new File("example"+x+".png"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		*/
		
		

		/** Also uncomment section 1 in the constructor
		 *  Section 1.1
		 */
		/*
		recordMemoryValues();
		*/
		
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
		
		//JSI.getMemoryNoClone()[0] = 50;
		//JSI.getMemoryNoClone()[1] = 50;
		//JSI.getMemoryNoClone()[2] = 50;
		//JSI.getMemoryNoClone()[3] = 50;
		//JSI.getMemoryNoClone()[4] = 50;
		//JSI.getMemoryNoClone()[5] = 50;
		//JSI.getMemoryNoClone()[6] = 50;
		//JSI.getMemoryNoClone()[7] = 50;
		//JSI.getMemoryNoClone()[8] = 50;
		//JSI.getMemoryNoClone()[9] = 50;
		//JSI.getMemoryNoClone()[10] = 50;
		//JSI.getMemoryNoClone()[11] = 50;
		//JSI.getMemoryNoClone()[12] = 50;
		//JSI.getMemoryNoClone()[13] = 50;
		//JSI.getMemoryNoClone()[14] = 50;
		//JSI.getMemoryNoClone()[15] = 50;
		//JSI.getMemoryNoClone()[16] = 50;
		//JSI.getMemoryNoClone()[17] = 50;
		//JSI.getMemoryNoClone()[18] = 50;
		//JSI.getMemoryNoClone()[19] = 50;
		//JSI.getMemoryNoClone()[20] = 50;
		//JSI.getMemoryNoClone()[21] = 50;
		//JSI.getMemoryNoClone()[22] = 50;
		//JSI.getMemoryNoClone()[23] = 50;
		//JSI.getMemoryNoClone()[24] = 50;
		//JSI.getMemoryNoClone()[25] = 50;
		//JSI.getMemoryNoClone()[26] = 50;
		//JSI.getMemoryNoClone()[27] = 50;
		//JSI.getMemoryNoClone()[28] = 50;
		//JSI.getMemoryNoClone()[29] = 50;
		//JSI.getMemoryNoClone()[30] = 50;
		//JSI.getMemoryNoClone()[31] = 50;
		//JSI.getMemoryNoClone()[32] = 50;
		//JSI.getMemoryNoClone()[33] = 50;
		//JSI.getMemoryNoClone()[34] = 50;
		//JSI.getMemoryNoClone()[35] = 50;
		//JSI.getMemoryNoClone()[36] = 50;
		//JSI.getMemoryNoClone()[37] = 50;
		//JSI.getMemoryNoClone()[38] = 50;
		//JSI.getMemoryNoClone()[39] = 50;
		//JSI.getMemoryNoClone()[40] = 50;
		//JSI.getMemoryNoClone()[41] = 50;
		//JSI.getMemoryNoClone()[42] = 50;
		//JSI.getMemoryNoClone()[43] = 50;
		//JSI.getMemoryNoClone()[44] = 50;
		//JSI.getMemoryNoClone()[45] = 50;
		//JSI.getMemoryNoClone()[46] = 50;
		//JSI.getMemoryNoClone()[47] = 50;
		//JSI.getMemoryNoClone()[48] = 50;
		//JSI.getMemoryNoClone()[49] = 50;
		//JSI.getMemoryNoClone()[50] = 50;
		//JSI.getMemoryNoClone()[51] = 50;
		//JSI.getMemoryNoClone()[52] = 50;
		//JSI.getMemoryNoClone()[53] = 50;
		//JSI.getMemoryNoClone()[54] = 50;
		//JSI.getMemoryNoClone()[55] = 50;
		//JSI.getMemoryNoClone()[56] = 50;
		//JSI.getMemoryNoClone()[57] = 50;
		//JSI.getMemoryNoClone()[58] = 50;
		//JSI.getMemoryNoClone()[59] = 50;
		//JSI.getMemoryNoClone()[60] = 50;
		//JSI.getMemoryNoClone()[61] = 50;
		//JSI.getMemoryNoClone()[62] = 50;
		//JSI.getMemoryNoClone()[63] = 50;
		//JSI.getMemoryNoClone()[64] = 50;
		//JSI.getMemoryNoClone()[65] = 50;
		//JSI.getMemoryNoClone()[66] = 50;
		//JSI.getMemoryNoClone()[67] = 50;
		//JSI.getMemoryNoClone()[68] = 50;
		//JSI.getMemoryNoClone()[69] = 50;
		//JSI.getMemoryNoClone()[70] = 50;
		//JSI.getMemoryNoClone()[71] = 50;
		//JSI.getMemoryNoClone()[72] = 50;
		//JSI.getMemoryNoClone()[73] = 50;
		//JSI.getMemoryNoClone()[74] = 50;
		//JSI.getMemoryNoClone()[75] = 50;
		//JSI.getMemoryNoClone()[76] = 50;
		//JSI.getMemoryNoClone()[77] = 50;
		//JSI.getMemoryNoClone()[78] = 50;
		//JSI.getMemoryNoClone()[79] = 50;
		//JSI.getMemoryNoClone()[80] = 50;
		//JSI.getMemoryNoClone()[81] = 50;
		//JSI.getMemoryNoClone()[82] = 50;
		//JSI.getMemoryNoClone()[83] = 50;
		//JSI.getMemoryNoClone()[84] = 50;
		//JSI.getMemoryNoClone()[85] = 50;
		//JSI.getMemoryNoClone()[86] = 50;
		//JSI.getMemoryNoClone()[87] = 50;
		//JSI.getMemoryNoClone()[88] = 50;
		//JSI.getMemoryNoClone()[89] = 50;
		//JSI.getMemoryNoClone()[90] = 50;
		//JSI.getMemoryNoClone()[91] = 50;
		//JSI.getMemoryNoClone()[92] = 50;
		//JSI.getMemoryNoClone()[93] = 50;
		//JSI.getMemoryNoClone()[94] = 50;
		//JSI.getMemoryNoClone()[95] = 50;
		//JSI.getMemoryNoClone()[96] = 50;
		//JSI.getMemoryNoClone()[97] = 50;
		//JSI.getMemoryNoClone()[98] = 50;
		//JSI.getMemoryNoClone()[99] = 50;
		//JSI.getMemoryNoClone()[100] = 50;
		//JSI.getMemoryNoClone()[101] = 50;
		//JSI.getMemoryNoClone()[102] = 50;
		//JSI.getMemoryNoClone()[103] = 50;
		//JSI.getMemoryNoClone()[104] = 50;
		//JSI.getMemoryNoClone()[105] = 50;
		//JSI.getMemoryNoClone()[106] = 50;
		//JSI.getMemoryNoClone()[107] = 50;
		//JSI.getMemoryNoClone()[108] = 50;
		//JSI.getMemoryNoClone()[109] = 50;
		//JSI.getMemoryNoClone()[110] = 50;
		//JSI.getMemoryNoClone()[111] = 50;
		//JSI.getMemoryNoClone()[112] = 50;
		//JSI.getMemoryNoClone()[113] = 50;
		//JSI.getMemoryNoClone()[114] = 50;
		//JSI.getMemoryNoClone()[115] = 50;
		//JSI.getMemoryNoClone()[116] = 50;
		//JSI.getMemoryNoClone()[117] = 50;
		//JSI.getMemoryNoClone()[118] = 50;
		//JSI.getMemoryNoClone()[119] = 50;
		//JSI.getMemoryNoClone()[120] = 50;
		//JSI.getMemoryNoClone()[121] = 50;
		//JSI.getMemoryNoClone()[122] = 50;
		//JSI.getMemoryNoClone()[123] = 50;
		//JSI.getMemoryNoClone()[124] = 50;
		//JSI.getMemoryNoClone()[125] = 50;
		//JSI.getMemoryNoClone()[126] = 50;
		//JSI.getMemoryNoClone()[127] = 50;
		


		/**
		 * Section 4
		 * Allows the user to print out the value at the specified
		 * location in memory
		 */
		
		//System.out.println(mem[0]);
		//System.out.println(mem[1]);
		//System.out.println(mem[2]);
		//System.out.println(mem[3]);
		//System.out.println(mem[4]);
		//System.out.println(mem[5]);
		//System.out.println(mem[6]);
		//System.out.println(mem[7]);
		//System.out.println(mem[8]);
		//System.out.println(mem[9]);
		//System.out.println(mem[10]);
		//System.out.println(mem[11]);
		//System.out.println(mem[12]);
		//System.out.println(mem[13]);
		//System.out.println(mem[14]);
		//System.out.println(mem[15]);
		//System.out.println(mem[16]);
		//System.out.println(mem[17]);
		//System.out.println(mem[18]);
		//System.out.println(mem[19]);
		//System.out.println(mem[20]);
		//System.out.println(mem[21]);
		//System.out.println(mem[22]);
		//System.out.println(mem[23]);
		//System.out.println(mem[24]);
		//System.out.println(mem[25]);
		//System.out.println(mem[26]);
		//System.out.println(mem[27]);
		//System.out.println(mem[28]);
		//System.out.println(mem[29]);
		//System.out.println(mem[30]);
		//System.out.println(mem[31]);
		//System.out.println(mem[32]);
		//System.out.println(mem[33]);
		//System.out.println(mem[34]);
		//System.out.println(mem[35]);
		//System.out.println(mem[36]);
		//System.out.println(mem[37]);
		//System.out.println(mem[38]);
		//System.out.println(mem[39]);
		//System.out.println(mem[40]);
		//System.out.println(mem[41]);
		//System.out.println(mem[42]);
		//System.out.println(mem[43]);
		//System.out.println(mem[44]);
		//System.out.println(mem[45]);
		//System.out.println(mem[46]);
		//System.out.println(mem[47]);
		//System.out.println(mem[48]);
		//System.out.println(mem[49]);
		//System.out.println(mem[50]);
		//System.out.println(mem[51]);
		//System.out.println(mem[52]);
		//System.out.println(mem[53]);
		//System.out.println(mem[54]);
		//System.out.println(mem[55]);
		//System.out.println(mem[56]);
		//System.out.println(mem[57]);
		//System.out.println(mem[58]);
		//System.out.println(mem[59]);
		//System.out.println(mem[60]);
		//System.out.println(mem[61]);
		//System.out.println(mem[62]);
		//System.out.println(mem[63]);
		//System.out.println(mem[64]);
		//System.out.println(mem[65]);
		//System.out.println(mem[66]);
		//System.out.println(mem[67]);
		//System.out.println(mem[68]);
		//System.out.println(mem[69]);
		//System.out.println(mem[70]);
		//System.out.println(mem[71]);
		//System.out.println(mem[72]);
		//System.out.println(mem[73]);
		//System.out.println(mem[74]);
		//System.out.println(mem[75]);
		//System.out.println(mem[76]);
		//System.out.println(mem[77]);
		//System.out.println(mem[78]);
		//System.out.println(mem[79]);
		//System.out.println(mem[80]);
		//System.out.println(mem[81]);
		//System.out.println(mem[82]);
		//System.out.println(mem[83]);
		//System.out.println(mem[84]);
		//System.out.println(mem[85]);
		//System.out.println(mem[86]);
		//System.out.println(mem[87]);
		//System.out.println(mem[88]);
		//System.out.println(mem[89]);
		//System.out.println(mem[90]);
		//System.out.println(mem[91]);
		//System.out.println(mem[92]);
		//System.out.println(mem[93]);
		//System.out.println(mem[94]);
		//System.out.println(mem[95]);
		//System.out.println(mem[96]);
		//System.out.println(mem[97]);
		//System.out.println(mem[98]);
		//System.out.println(mem[99]);
		//System.out.println(mem[100]);
		//System.out.println(mem[101]);
		//System.out.println(mem[102]);
		//System.out.println(mem[103]);
		//System.out.println(mem[104]);
		//System.out.println(mem[105]);
		//System.out.println(mem[106]);
		//System.out.println(mem[107]);
		//System.out.println(mem[108]);
		//System.out.println(mem[109]);
		//System.out.println(mem[110]);
		//System.out.println(mem[111]);
		//System.out.println(mem[112]);
		//System.out.println(mem[113]);
		//System.out.println(mem[114]);
		//System.out.println(mem[115]);
		//System.out.println(mem[116]);
		//System.out.println(mem[117]);
		//System.out.println(mem[118]);
		//System.out.println(mem[119]);
		//System.out.println(mem[120]);
		//System.out.println(mem[121]);
		//System.out.println(mem[122]);
		//System.out.println(mem[123]);
		//System.out.println(mem[124]);
		//System.out.println(mem[125]);
		//System.out.println(mem[126]);
		//System.out.println(mem[127]);
		


		//DO NOT MODIFY CODE BELOW THIS COMMENT
		return new int[] {0};
	}

}
