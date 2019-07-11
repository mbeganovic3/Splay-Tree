package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par; //parent...not necessarily required, but can be useful in splay tree
	boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
	//I personally use it to indicate to my SPLT insert whether or not we increment size.

	BST_Node(String data){ 
		this.data=data;
		this.justMade=true;
	}

	BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
		this.data=data;
		this.left=left;
		this.right=right;
		this.par=par;
		this.justMade=true;
	}

	// --- used for testing  ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

	public String getData(){ return data; }
	public BST_Node getLeft(){ return left; }
	public BST_Node getRight(){ return right; }

	// --- end used for testing -------------------------------------------


	// --- Some example methods that could be helpful ------------------------------------------
	//
	// add the meat of correct implementation logic to them if you wish

	// you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
	// make them take more or different parameters
	// have them return different types
	//
	// you may use recursive or iterative implementations

	/*
  public BST_Node containsNode(String s){ return false; } //note: I personally find it easiest to make this return a 
  														Node,(that being the node splayed to root), you are however free to
  														 do what you wish.

  public BST_Node insertNode(String s){ return false; } //Really same logic as above note

  public boolean removeNode(String s){ return false; } //I personal do not use the removeNode internal method in my 
  																impl since it is rather easily done in SPLT, feel free to try to 
  																delegate this out, 
  																however we do not
   																"remove" like we do in BST
  public BST_Node findMin(){ return left; } 
  public BST_Node findMax(){ return right; }
  public int getHeight(){ return 0; }
	 */
	//you could have this return or take in 
	//whatever you want..so long as it will do the job 
	//internally. As a caller of SPLT functions, I should really have no
	// idea if you are "splaying or not"
	//I of course, will be checking with tests and by eye to make sure you are indeed splaying
	//Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!
	/* 
  sources used: ics.uci.edu and wiki
  1. x has no grandparent (zig)
  If x is left child of root y, then rotate (xy)R.
  Else if x is right child of root y, then rotate (yx)L.

  2. x is LL or RR grandchild (zig-zig)
  If x is left child of y, and y is left child of z, 
  then rotate at grandfather (yz)R and then rotate at father (xy)R.
  Else if x is right child of y, and y is right child of z, 
  then rotate at grandfather (yz)L and then rotate at father (xy)L.

  3. x is LR or RL grandchild (zig-zag)
  If x is right child of y, and y is left child of z, 
  then rotate at father (yx)L and then rotate at grandfather (xz)R.
  Else if x is left child of y, and y is right child of z, 
  then rotate at father (yx)R and then rotate at grandfather (xz)L.*/
	
	private void splay(BST_Node toSplay) {
		while(toSplay.par != null) { // instead might be (toSplay != root)
			BST_Node grandPar = toSplay.par.par;
			if(grandPar == null) { // X has no grandparent 
				if(toSplay.par.left == toSplay) { // x is L child of root 
					rotRight(toSplay.par);
				}else if(toSplay.par.right == toSplay) { // x is R child of root 
					rotLeft(toSplay.par);
				}
			}else if(grandPar.left == toSplay.par && toSplay.par.left == toSplay) { //x is LL grandchild
				rotRight(grandPar);
				rotRight(toSplay.par);
			}else if(grandPar.right == toSplay.par && toSplay.par.right == toSplay) { //x is RR grandchild
				rotLeft(grandPar);
				rotLeft(toSplay.par);
			}else if(grandPar.left == toSplay.par && toSplay.par.right == toSplay) { //x is LR grandchild
				rotLeft(toSplay.par);
				rotRight(toSplay.par);
			}else if(grandPar.right == toSplay.par && toSplay.par.left == toSplay) { //x is RL grandchild
				rotRight(toSplay.par);
				rotLeft(toSplay.par);
			}
		}
	}
	// contains is an access so have to splay 
	public BST_Node containsNode(String s){ //it was me
		if(data.equals(s)) {
			splay(this);
			return this;
		}
		if(data.compareTo(s)>0){//s lexiconically less than data
			if(left != null) return left.containsNode(s);
			splay(this);
			return this;
		}
		if(data.compareTo(s)<0){
			if(right != null) return right.containsNode(s);
			splay(this);
			return this;
		}
		return null; //shouldn't hit
	}

	// if you have your insert method return a BST_node, you can then accordingly reassign the root of the SPLT to be the node that was splayed 
	//to the top (the node that will be returned by your insertNode method). If you choose to do
	//this, you also have to find a way to figure out when to increment the size, in case the insert method did not end up being successful 
	//and just splayed to the top an already existing node. The "justmade" field, which Stotts already put into your boiler code, is helpful in that case.
	public BST_Node insertNode(String s){
		if(data.compareTo(s)>0){
			if(left != null) return left.insertNode(s);
			left=new BST_Node(s,null,null,this);
			BST_Node root = left;
			splay(left);
			return root;
		}
		if(data.compareTo(s)<0){
			if(right != null) return right.insertNode(s);
			right=new BST_Node(s,null,null,this);
			BST_Node root = right;
			splay(right);
			return root;
		}
		splay(this);//ie we have a duplicate
		return this;
	}
	// find min is an access so have to splay 
	public BST_Node findMin(){
		if(left!=null)return left.findMin();
		splay(this);
		return this;
	}
	// find max is an access so have to splay 
	public BST_Node findMax(){
		if(right!=null)return right.findMax();
		splay(this);
		return this;
	}
	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}
	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")+",Right: "+((this.right!=null)?right.data:"null");
	}
	// --------------------------------------------------------------------
	// you may add any other methods you want to get the job done
	// --------------------------------------------------------------------
	private void rotRight(BST_Node toRot) { 
		BST_Node toRotChild = toRot.left;
		if(toRotChild != null) {
			toRot.left = toRotChild.right;
			if(toRotChild.right != null) {
				toRotChild.right.par = toRot;
			}
			toRotChild.par = toRot.par;
		}
		if(toRot.par != null) {
			if (toRot == toRot.par.left) {
				toRot.par.left = toRotChild;
			}else {
				toRot.par.right = toRotChild;
			}
		} 
		if(toRotChild != null) {
			toRotChild.right = toRot;
		}
		toRot.par = toRotChild;
	}
	private void rotLeft(BST_Node toRot) { 
		BST_Node toRotChild = toRot.right;
		if(toRotChild != null) {
			toRot.right = toRotChild.left;
			if(toRotChild.left != null) {
				toRotChild.left.par = toRot;
			}
			toRotChild.par = toRot.par;
		}
		if(toRot.par != null) {
			if (toRot == toRot.par.left) {
				toRot.par.left = toRotChild;
			}else {
				toRot.par.right = toRotChild;
			}
		} 
		if(toRotChild != null) {
			toRotChild.left = toRot;
		}
		toRot.par = toRotChild;  
	}
}