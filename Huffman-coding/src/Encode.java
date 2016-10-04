import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * The file compression class. Does not work for files 
 * containing only 1 type of character.
 * 
 * @author Dan Huy Ngoc Nguyen <dangu15@student.sdu.dk>
 * 
 */
public class Encode {
	
	public static void main(String[] args) throws Exception {
		Huffman huff = new Huffman();
		
		FileInputStream input = new FileInputStream(args[0]);
		
		int[] frequency = huff.frequencyList(input);
		savePath(args[0]);
		Node huffman = huff.huffmanTree(frequency);
		String[] map = huff.encodingMap(huffman);

		input.close();
		FileInputStream input2 = new FileInputStream(args[0]);
		FileOutputStream output2 = new FileOutputStream(args[1]);
		BitOutputStream out = new BitOutputStream(output2);
		
		//writes to output file
		int nextByte;
		//System.out.println("start");
		while ( (nextByte = input2.read()) != -1 ) {
			//the binary sequence stored in the map
			//is split into a sequence list. 
			//the bits in the sequence list are then
			//written to the output file one by one
			//System.out.println(map[nextByte] + "  -> " + nextByte);
			String[] sequenceList = map[nextByte].split("");
			for (String bit : sequenceList) {
				if (bit.equals("0")) {
					//System.out.println("0");
					out.writeBit(0);
				}
				if (bit.equals("1")) {
					//System.out.println("1");
					out.writeBit(1);
				}
			}
		}
		input2.close();
		out.close();
	}
	
	/**
	 * Creates a new text file which stores the path to the input file.
	 * This file is placed in the same folder as the input file.
	 * Default save location is "C:\TempPath.txt".
	 * Might require admin privileges to function.
	 * 
	 * The intent behind this implementation is
	 * to allow the user to decode the compressed file
	 * at a different runtime. Storing a path should
	 * generally be cheaper than storing an array.
	 * 
	 * @param path The path to the input file.
	 * @throws IOException
	 */
	public static void savePath(String path) throws IOException {
		//locates the directory
		File currentDirectory = new File(path).getParentFile();
		//creates the file and writes the path
		//if a directory was found
		if (currentDirectory!=null) {
			File newFile = new File(currentDirectory.getAbsolutePath()+"\\TempPath.txt");
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(path);
			bw.close();
		//otherwise it resorts to the default location
		} else {
			File newFile = new File("C:\\TempPath.txt");
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(path);
			bw.close();
		}
	}
}