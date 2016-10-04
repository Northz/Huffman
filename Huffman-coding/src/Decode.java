import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The file decompression class. Can only be executed once
 * per Encode.java execution due to deleting the tempPath.txt file
 * 
 * @author Dan Huy Ngoc Nguyen <dangu15@student.sdu.dk>
 * 
 */
public class Decode {
	public static void main(String[] args) throws Exception {
		Huffman huff = new Huffman();
		
		String tempPath = getSavedPath(args[0]);
		
		FileInputStream input = new FileInputStream(tempPath);
		
		int[] frequency = huff.frequencyList(input);
		int freqSum = getFrequencySum(frequency);
		Node huffman = huff.huffmanTree(frequency);
		String[] map = huff.encodingMap(huffman);
		input.close();
		
		FileInputStream input2 = new FileInputStream(args[0]);
		FileOutputStream output2 = new FileOutputStream(args[1]);
		
		BitInputStream in = new BitInputStream(input2);
		BitOutputStream out = new BitOutputStream(output2);
		
		int byteCounter = 0;
		int bit;
		String code = "";
		while ( (bit = in.readBit()) != -1 && byteCounter < freqSum) {
			code = code + bit;
			//System.out.println(code);
			int i = 0;
			for (String seq : map) {
				if (seq!=null && seq.equals(code)) {
					//System.out.println("writing int " +i);
					String sequence = "";
					//accounts for the missing sign bits
					//when using the .toBinaryString() method
					//appends the corresponding amount of 
					//missing "0"s to the beginning of the string
					int signBits = 8-Integer.toBinaryString(i).length();
					while (signBits > 0) {
						sequence = sequence + "0";
						signBits--;
					}
					sequence = sequence + Integer.toBinaryString(i);
					//System.out.println(sequence);
					String[] binarySequence = sequence.split("");
					for (String b : binarySequence) {
						if (b.equals("0")) {
							out.writeBit(0);
						}
						if (b.equals("1")) {
							out.writeBit(1);
						}
					}
					code = "";
					byteCounter++;
					//System.out.println(byteCounter);
				}
				i++;
			}
		}
	}
	
	/**
	 * Computes the sum of frequencies in the list.
	 * 
	 * @param freqList The frequency list.
	 * @return The sum of all frequencies.
	 */
	public static int getFrequencySum (int[] freqList) {
		int sum = 0;
		for (int freq : freqList) {
			if (freq > 0) {
				sum = sum + freq;
			}
		}
		//System.out.println(sum);
		return sum;
	}
	
	/**
	 * Locates the temporary path file and DELETES it in the process.
	 * The behavior of this algorithm shadows Encode.savePath(String path).
	 * 
	 * @param arg The location of the compressed file.
	 * @return The path to the original file.
	 * @throws IOException
	 */
	public static String getSavedPath(String arg) throws IOException {
		String thePath = "";
		File currentDirectory = new File(arg).getParentFile();
		if (currentDirectory!=null) {
			File pathFile = new File(currentDirectory.getAbsolutePath()+"\\TempPath.txt");
			Path p = pathFile.toPath();
			BufferedReader reader = Files.newBufferedReader(p);
			thePath = reader.readLine();
			Files.delete(p);
		} else {
			Path p = Paths.get("C:\\TempPath.txt");
			BufferedReader reader = Files.newBufferedReader(p);
			thePath = reader.readLine();
			Files.delete(p);
		}
		//System.out.println(thePath);
		return thePath;
	}
}
