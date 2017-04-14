package jstella.learning;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;



public class AgentPrintMemory implements JSIAgent {

	private JSILearning JSI;
	int count = 0;
	int x = 0;
	int y = 48;


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
		//JSI.setKeysEnabled(false); //Default Value : true
		//JSI.setVideoEnabled(false);  //Default Value : true

		/**
		 * Variables Related To Emulation
		 */
		//JSI.setFrameDelay(1); //Default Value : 17 (60 frames a second)
		//JSI.setAgentCallDelay(4); //Default Value : 1 (Agent called every frame)

		/**
		 * Variables Related To Images
		 */
		//JSI.setImagesEnabled(false); //Default Value : false
		//JSI.setFramesToAverage(4); //This is the default value
		//JSI.setNumberOfFramesBetween(3); //This is the default value

		/**
		 * Select A ROM File To Play
		 */
		JSI.loadROM(new File("SRC\\ROM\\MissileCommand.bin"));
		//for(int x = 0; x <128; x++){
		//	values.add(x,new ArrayList<Integer>());
		//}
	}

	private void recordMemoryValues(){
		int[] mem = JSI.getMemory();

		for(int x = 0; x < mem.length; x++)
			if(!values.get(x).contains(mem[x]))
				values.get(x).add(mem[x]);
		count++;


		if(count>=60000){
			try{
				PrintWriter writer = new PrintWriter(new FileWriter("memstateMISLE.txt"));

				for(int x = 0; x < values.size(); x++){
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

		//recordMemoryValues();

		/*
		JSI.DEVgetMemoryNoClone()[0] = 50;
		JSI.DEVgetMemoryNoClone()[1] = 0;
		JSI.DEVgetMemoryNoClone()[2] = 0;
		JSI.DEVgetMemoryNoClone()[3] = 0;
		JSI.DEVgetMemoryNoClone()[4] = 0;
		JSI.DEVgetMemoryNoClone()[5] = 0;
		JSI.DEVgetMemoryNoClone()[6] = 0;
		JSI.DEVgetMemoryNoClone()[7] = 0;
		JSI.DEVgetMemoryNoClone()[8] = 0;
		JSI.DEVgetMemoryNoClone()[9] = 0;
		JSI.DEVgetMemoryNoClone()[10] = 0;
		JSI.DEVgetMemoryNoClone()[11] = 50;
		JSI.DEVgetMemoryNoClone()[12] = 50;
		JSI.DEVgetMemoryNoClone()[13] = 50;
		JSI.DEVgetMemoryNoClone()[14] = 50;
		JSI.DEVgetMemoryNoClone()[15] = 50;
		JSI.DEVgetMemoryNoClone()[16] = 50;
		JSI.DEVgetMemoryNoClone()[17] = 50;
		JSI.DEVgetMemoryNoClone()[18] = 63;
		JSI.DEVgetMemoryNoClone()[19] = 63;
		JSI.DEVgetMemoryNoClone()[20] = 63;
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
		JSI.DEVgetMemoryNoClone()[31] = 255;
		JSI.DEVgetMemoryNoClone()[32] = 255;
		JSI.DEVgetMemoryNoClone()[33] = 255;
		JSI.DEVgetMemoryNoClone()[34] = 255;
		JSI.DEVgetMemoryNoClone()[35] = 255;
		JSI.DEVgetMemoryNoClone()[36] = 255;
		JSI.DEVgetMemoryNoClone()[37] = 255;
		JSI.DEVgetMemoryNoClone()[38] = 255;
		JSI.DEVgetMemoryNoClone()[39] = 255;
		JSI.DEVgetMemoryNoClone()[40] = 255;
		JSI.DEVgetMemoryNoClone()[41] = 255;
		JSI.DEVgetMemoryNoClone()[42] = 255;
		JSI.DEVgetMemoryNoClone()[43] = 255;
		JSI.DEVgetMemoryNoClone()[44] = 255;
		JSI.DEVgetMemoryNoClone()[45] = 255;
		JSI.DEVgetMemoryNoClone()[46] = 255;
		JSI.DEVgetMemoryNoClone()[47] = 255;
		JSI.DEVgetMemoryNoClone()[48] = 255;
		JSI.DEVgetMemoryNoClone()[49] = 240;
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
		JSI.DEVgetMemoryNoClone()[87] = 70;
		JSI.DEVgetMemoryNoClone()[88] = 80;
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
		JSI.DEVgetMemoryNoClone()[108] = 255;
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

		//JSI.DEVgetMemoryNoClone()[19]=50;
		//JSI.DEVgetMemoryNoClone()[20]=70;
		//JSI.DEVgetMemoryNoClone()[21]=60;
		//JSI.DEVgetMemoryNoClone()[22]=40;
		
		
		JSI.DEVgetMemoryNoClone()[127]=100;
		System.out.println(mem[127]);


		//JSI.DEVgetMemoryNoClone()[28]=0;
		//JSI.DEVgetMemoryNoClone()[29]=50;
		//System.out.println(mem[28]+", "+mem[29]);
		
		
		
		
		return new int[] {0};
	}

}
