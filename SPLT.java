package SPLT_A4;

public class SPLT implements SPLT_Interface{
	private BST_Node root;
	private int size;

	public SPLT() {
		this.size = 0;
	} 

	public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
		return root;
	}

	// if you have your insert method return a BST_node, you can then accordingly reassign the root of the SPLT to be the node that was splayed 
	//to the top (the node that will be returned by your insertNode method). If you choose to do
	//this, you also have to find a way to figure out when to increment the size, in case the insert method did not end up being successful 
	//and just splayed to the top an already existing node
	@Override
	public void insert(String s) { //It was me
		// TODO Auto-generated method stub
		if (empty()) {
			root = new BST_Node(s);
		}else root = root.insertNode(s);
		if(root.justMade == true) // insert was a success so increase size 
			size++; 
			root.justMade = false; // reset
	}
	public void remove(String s) {
		if(root == null || contains(s) == false) return; //do a "contains" on the node to be removed; this will splay it to the root if it is in the tree
		BST_Node right = root.right;
		if(right != null && root.left == null) { // no L subtree
			root = root.right;
			root.par = null;
		}else if(right == null && root.left != null) { // no R subtree
			root = root.left;
			root.par = null; 
		}else if(root.left != null && right != null) { // both L and R
			root = root.left.findMax(); //do a "findMax" on L; this will splay the max node to the root,
			root.right = right;
			right.par = root; 
		} 
		size -=1;	   
	}
	@Override
	public String findMin() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			//return root.findMin().data;
			root = root.findMin();
			return root.data;
		}
	}

	@Override
	public String findMax() {
		// TODO Auto-generated method stub
		if (empty()) {
			return null;
		} else {
			//return root.findMax().data;
			root = root.findMax();
			return root.data;
		}
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(String s) {
		// TODO Auto-generated method stub
		if (empty()) {
			return false;
		}
		//return root.containsNode(s);
		root = root.containsNode(s);
		return root.data.equals(s);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		if (empty()) {
			return -1;
		} else {
			return root.getHeight();
		}
	}
}