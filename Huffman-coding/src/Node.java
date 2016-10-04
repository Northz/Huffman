/**
 * Represents the node structure in the tree,
 * left and right node points to null
 * to indicate the end of the tree.
 * Slight alteration with the addition of 
 * a symbol variable to store symbol data.
 * 
 * @author Dan Huy Ngoc Nguyen <dangu15@student.sdu.dk>
 * 
 */
public class Node {
	
	int key;
	Node left;
	Node right;
	//initialized as -1 to account for symbol
	//with value 0;
	int symbol=-1;
	
	public Node(int k) {
		this.key = k;
	}
}
