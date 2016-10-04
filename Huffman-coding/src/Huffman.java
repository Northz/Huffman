import java.io.IOException;
import java.io.InputStream;

/**
 * Contains all the methods required to generate the following:
 *  1.  Frequency list.
 *  2.  Huffman tree.
 *  3.  Encoding map.
 * 
 * @author Dan Huy Ngoc Nguyen <dangu15@student.sdu.dk>
 *
 */
public class Huffman {
	private int heapSize;
	
	/**
	 * Creates a frequency list of characters for the given input file.
	 * @param in The input stream.
	 * @return int[] frequencyList.
	 * @throws IOException
	 */
	public int[] frequencyList(InputStream in) throws IOException {
		int[] frequencyList = new int[256];
		int nextByte;
		while ( (nextByte = in.read()) != -1 ) {
			if (frequencyList[nextByte] == 0) {
				heapSize++;
			}
			frequencyList[nextByte]++;
			//System.out.println("freq is " + frequencyList[nextByte] + " -> int " +nextByte);
		}
		//System.out.println("reached return");
		return frequencyList;
	}
	
	/**
	 * Creates a Huffman tree from a frequency list.
	 * 
	 * @param frequencyList The frequency list.
	 * @return A Huffman tree.
	 */
	public Node huffmanTree(int[] frequencyList) {
		//creates the priority queue
		PQHeap pq = new PQHeap(heapSize);
		int i = 0;
		for (int freq : frequencyList) {
			if (freq > 0) {
				//Node is created with frequency as the key and
				//byte as the symbol
				Node k = new Node(freq);
				k.symbol = i;
				//System.out.println("inserting element with freq "+freq+ " and symbol " + i);
				pq.insert(new Element(freq, k));
			}
			i++;
		}
		//creates the Huffman tree
		//following the pseudo code from
		//the textbook
		i=0;
		while (i < heapSize-1) {
			Element x = pq.extractMin();
			Element y = pq.extractMin();
			Node z = new Node(x.key+y.key);
			z.left = (Node) x.data;
			z.right = (Node) y.data;
			//System.out.println("freq sum: " + z.key);
			//System.out.println("left key -> "+z.left.key);
			//System.out.println("right key -> "+z.right.key +"\n");
			pq.insert(new Element(x.key+y.key, z));
			i++;
		}
		Element resultElement = pq.extractMin();
		Node resultNode = (Node) resultElement.data;
		//System.out.println("reached return");
		return resultNode;
	}
	
	/**
	 * Creates an encoding map for the input file.
	 * @param huffmanTree The Huffman Tree.
	 * @return A String list containing the new binary mapping.
	 */
	public String[] encodingMap (Node huffmanTree) {
		String[] map = new String[256];
		String code = "";
		huffmanWalk(huffmanTree, map,code);
		//System.out.println("returned");
		return map;
	}
	
	/**
	 * Traverses the Huffman Tree ->
	 * used by the method Encode.encodingMap.
	 * 
	 * @param x the root of the tree.
	 * @param map the map.
	 * @param code the bit code, should be initialized 
	 * as an empty string for best results.
	 * @return An array to encode the given file with.
	 */
	public static void huffmanWalk(Node x, String[] map, String code) {
		if (x!=null) {
			//System.out.println("NODE " + x.key);
			if (x.left!=null) {
				code = code +"0";
				//System.out.println("left "+code);
				huffmanWalk(x.left, map, code);
			}
			if (x.symbol!=-1) {
				//System.out.println("LEAF " + x.symbol);
				map[x.symbol] = code;
			}
			//a successful recursive call to a right node
			//always involves a prior backtrack, this is why
			//the last bit from the code string is removed
			//before the new bit is concatenated
			if (x.right!=null) {
				code = code.substring(0, code.length()-1) +"1";
				//System.out.println("right" + code);
				huffmanWalk(x.right, map, code);
			}
			//System.out.println("backtrack: " + code);
		}
	}
}
