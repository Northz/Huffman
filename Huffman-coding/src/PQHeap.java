/** name: Dan Huy Ngoc Nguyen
 *  sdu-mail: dangu15
 */
public class PQHeap implements PQ{
	
	Element[] heapArray;
	public int size;
	/**initializes constructor to create array
	 * with specified storage size
	 * with empty cells(null)
	 */
	public PQHeap(int storageSize) {
		heapArray = new Element[storageSize];
	}
	
	/**locates the index of the
	 * parent node and its
	 * left and right children
	 * for an array where index 0 is included
	 */
	public int parent(int i) {
		return (i-1)/2;
	}
	
    public int left(int i){
        return 2*i+1;
    }

    public int right(int i){
        return 2*i+2;
    }
    /**Stores the size of the heap
     * and not the length of the array
     */
    public int heapSize(Element[] array){
        size = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] != null){
                size++;
            }
        }
        return size;
    }
    
    /**rewritten maxHeapify method from the textbook
     * conditions have been reversed/changed
     * to create a minHeapify method
     */
    public void minHeapify(Element[] array, int i){
        int l = left(i);
        int r = right(i);
        // stores current node value as smallest.
        int smallest = i;
        // stores a new value in smallest if 
        // the min-heap property does not hold
        if (l < size && array[l].key < array[smallest].key){
            smallest = l;
        }
        if (r < size && array[r].key < array[smallest].key){
            smallest = r;
        }
        // exchanges the parent and child
        // to maintain the min-heap property
        // performs recursive call to minHeapify
        if (smallest != i){
        	exchange(array, i, smallest);
            minHeapify(array, smallest);
        }
    }
    /**exchanges parent and child 
     * by storing parent in a temporary array 
     */
    public void exchange(Element[] array, int parent, int smallest) {
    	Element tmp = array[parent];
    	array[parent] = array[smallest];
        array[smallest] = tmp;
    }
    
	public Element extractMin() {
		size = heapSize(heapArray);
		// stores the first element, assumed to be smallest key
		Element min = heapArray[0];
		// places the last key to the top of the heap,
		// deleting the smallest key from the heap in the process
		heapArray[0] = heapArray[size-1];
		heapArray[size-1] = null;
		// current size of the heap is updated and the
		// min-heap property is restored
		size--;
		minHeapify(heapArray, 0);
		// smallest key is returned
        return min;
	}
	/** inserts an element at the bottom of the heap
	 *  and makes sure the min-heap property is kept
	 */
	public void insert(Element e){
		int i = heapSize(heapArray);
	    heapArray[i] = e;
	    while (i > 0 && heapArray[parent(i)].key > heapArray[i].key){
	        exchange(heapArray,i,parent(i));
	        i = parent(i);
	    }
	}
}
