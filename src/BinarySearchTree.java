package com.binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree {
	
	// A utility function to search a given key in BST
	public static Node search(Node node, int key){
		
		// Base Cases: root node is null or key is present at root
		if(node == null || node.getData() == key) return node;
		
		// root's val is greater than root's key
		if(node.getData() > key)
			return search(node.getLeft(), key);
		
		// root's val is lesser than root's key
		else return search(node.getRight(), key);
	}
	
	/* A recursive function to insert a new key in BST */
	public static Node insert(Node node, int key){
		
		/* If the tree is empty, return a new node */
		if(node == null){
			node = new Node(key);
			return node;
		}
		
		 /* Otherwise, recur down the tree */
		if(node.getData() > key){
			node.setLeft(insert(node.getLeft(), key));
		}else 
			node.setRight(insert(node.getRight(), key));
		
		/* return the (unchanged) node pointer */
		return node;
	}
	
	// A utility function to do inorder traversal of BST
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.getData());
            inorderRec(root.right);
        }
    }
    
    // Recursive function to delete a key node in BST
    static Node deleteRec(Node node, int key){
    	if(node == null)
    		return null;
    	
    	if(node.getData() > key){
    		node.setLeft(deleteRec(node.getLeft(), key));
    	}else if(node.getData() < key){
    		node.setRight(deleteRec(node.getRight(), key));
    	}else{
    		if(node.getLeft() == null)
    			return node.getRight();
    		else if(node.getRight() == null)
    			return node.getLeft();

    		node.setData(findInOrderSuccessor(node.getRight()));
    		
    		node.setRight(deleteRec(node.getRight(), node.getData()));
    	}
    	
    	return node;
    }

	private static int findInOrderSuccessor(Node right) {
		// TODO Auto-generated method stub
		int min = Integer.MAX_VALUE;
		Node temp = right;
		while(temp != null){
			if(temp.getData() < min){
				min = temp.getData();
			}
			
			temp = temp.getLeft();
		}
		return min;
	}
	/**
	 * @author JANSARI1
	 * 
	 * Time Complexity = O(n^2).
	 * 
	 * @param pre - Input array on which BST needs to be constructed.
	 * @param st - A start marker for the current recursive call.
	 * @param end - An end marker for the current recursive call.
	 * @param curr - A pointer to pre aaray for current iterating element.
	 * @return - Root node of the constructed BST.
	 */
	public static Node buildBSTOld(int [] pre, int st, int end, int curr){
		if(st>pre.length || curr>end || curr<st) return null;
		
		Node root = null;
		
		if(curr == st){
			root =  new Node(pre[st++]);
		}
		
		int next_max_index=0;
		
		for(next_max_index=st; next_max_index<pre.length; next_max_index++){
			if(pre[next_max_index]>pre[curr])
				break;
		}
		
		root.setLeft(buildBSTOld(pre, st, next_max_index-1, curr+1));
		root.setRight(buildBSTOld(pre, next_max_index, end, next_max_index));
		
		return root;
	}
	/**
	 * Description - This method has time complexity - O(n). It works on idea of range(min....max). ex- if first preorder element is 10 then all next element lesser than 10 is part of left subtree and all greater are of right subtree.
	 * 
	 * @author JANSARI1
	 * 
	 * @param pre - Input array on which BST needs to be constructed.
	 * @param min - A minimum boundary indicator.
	 * @param max - A maximum boundary indicator.
	 * @param indx - Current array element indicator that is in process.
	 * @return Root node of newly constructed BST.
	 */
	public static Node buildBST(int[] pre, int min, int max, CIndex indx){
		if(indx.index>=pre.length /*|| pre[indx.index]<min || pre[indx.index]>max*/){
			return null;
		}
		
		Node root=null;
		if(pre[indx.index]>min && pre[indx.index]<max){
			root= new Node(pre[indx.index++]);
			
			if(indx.index<pre.length){
				root.setLeft(buildBST(pre, min, root.getData(), indx));
				root.setRight(buildBST(pre, root.getData(), max, indx));
			}
		}
		return root;
	}
	/**
	 * Description - This method uses stack data structure to construct BST from pre-order array.
	 * 
	 * @author JANSARI1
	 * 
	 * @param pre - Input array on which BST needs to be constructed.
	 * @return - Root node of BST.
	 */
	public static Node buildBSTStack(int[] pre){
		Node root=new Node(pre[0]);
		
		Stack<Node> stk = new Stack<Node>();
		stk.push(root);
		
		for (int i = 1; i < pre.length; i++) {
			Node temp=null;
			
			while(!stk.isEmpty() && pre[i]>stk.peek().getData()){
				temp=stk.pop();
			}
			Node newNode = new Node(pre[i]);
			if(temp != null /*&& !stk.isEmpty()*/){
				temp.setRight(newNode);
			}else{
				stk.peek().setLeft(newNode);
			}
			stk.push(newNode);	
		}
		
		return root;
	}
	/**
	 * Description - Convert a BST to a Binary Tree such that sum of all greater keys is added to every key.
	 * 
	 * @author JANSARI1
	 * 
	 * @param root - root node of the BST.
	 * @param sum - A helper object to tackdown the sum to be added to next node in reverse in-order fashion.
	 */
	public static void addGreaterUtil(Node root, CIndex sum){
		if(root==null)
			return ;
		addGreaterUtil(root.getRight(), sum);
		
		sum.index+=root.getData();
		root.setData(sum.index);
		
		addGreaterUtil(root.getLeft(), sum);
	}
	
	/**
	 * 
	 * @param root - Root node of the tree which is converted to BST keeping structure intact.
	 */
	public static void binaryTree2BST(Node root){
		List<Integer> inOrderKeysList = new ArrayList<Integer>();
		
		getInorder(root,inOrderKeysList);
		Collections.sort(inOrderKeysList);
		storeIntoBST(root,inOrderKeysList, new CIndex());
	}

	private static void storeIntoBST(Node root, List<Integer> inOrderKeysList, CIndex ind) {
		if(root == null)
			return;
		storeIntoBST(root, inOrderKeysList, ind);
		root.setData(inOrderKeysList.get(ind.index++));
		storeIntoBST(root.getRight(), inOrderKeysList, ind);
	}

	private static void getInorder(Node root, List<Integer> inOrderKeysList) {
		if(root == null)
			return;
		getInorder(root.getLeft(), inOrderKeysList);
		inOrderKeysList.add(root.getData());
		getInorder(root.getRight(), inOrderKeysList);
	}
/**
	 * Description - Given a sorted array. Write a function that creates a Balanced Binary Search Tree using array elements.
	 * 
	 * @param sortedArr - Input Array from which BST to be build.
	 * @param curr - Current processing element pointer.
	 * @param n - size indicator of of current sub-array in recursive call.
	 * @return Root node of BST.
	 */
	public static Node sortedArray2BST(int[] sortedArr, CIndex curr, int n){
		
		if(n<=0)
			return null;
		
		Node left = sortedArray2BST(sortedArr, curr, n/2);
		Node root = new Node(sortedArr[curr.index++]);
		
		root.setLeft(left);
		root.setRight(sortedArray2BST(sortedArr, curr, n-n/2-1));
		
		return root;
	}
	/**
	 * Description - Recursive function to transform a BST to sum tree. This function traverses the tree in reverse inorder so that we have visited all greater key nodes of the currently visited node
	 *
	 * @param root - Current recurring node.
	 * @param sum - To track current sum in recursion.
	 */
	public static void transformTreeUtil(Node root, CIndex sum){
		if(root == null) return;
		
		transformTreeUtil(root.getRight(), sum);
		sum.index+=root.getData();
		root.setData(sum.index-root.getData());
		transformTreeUtil(root.getLeft(), sum);
	}
	/**
	 * 
	 * Description - Construct all possible BSTs for keys 1 to N
	 * 
	 * @param start - start indicator for recursive call.
	 * @param end - end indicator for recursive call.
	 * @return - List of all BST root nodes.
	 */
	public static List<Node> buildAllBST1toN(int start, int end){
		List<Node> rootList = new ArrayList<Node>();
		if(start>end){
			rootList.add(null); // Adding null to make sure that any of left or right tree list should go into for-each iteration.
			return rootList;
		}
		 
		for (int i = start; i <= end; i++) {
			Node root = new Node(i);
			
			List<Node> left = buildAllBST1toN(start, i-1);
			List<Node> right = buildAllBST1toN(i+1, end);
	
			for (Node rnode : right) {
				for (Node lnode : left) {
					root.setLeft(lnode);
					root.setRight(rnode);
					rootList.add(root);
				}
			}
		}
		
		return rootList;
	}
	/**
	 * 
	 * Description - Given a Binary Search Tree, convert it into a Min-Heap containing the same elements in O(n) time. Do this in-place.
	 * 
	 * @param root
	 * @return
	 */
	public static Node minHeap4mBST(Node root){
		
		if(root == null) return null;
		
		Node inPlaceLL4mBST = buildLL4mBST(root,new CIndex());
		
		return buildMinHeapUtil(inPlaceLL4mBST);
	}

	/**
	 * 
	 * Description - Supporting method for minHeap4mBST
	 * 
	 * @param node
	 * @return
	 */
	private static Node buildMinHeapUtil(Node node) {
		if(node == null) return null;
		
		Node head = node;
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		
		while (queue.size()>0 && head!=null) {
			Node temp = queue.poll();
			
			Node left = head.getRight();
			Node right = left.getRight();
			
			head=right;
			
			temp.setLeft(left);
			temp.setRight(right);
			if(left!=null)
				queue.add(left);
			if(right!=null)
				queue.add(right);
		}
		
		while(queue.size()>0){
			Node temp = queue.poll();
			
			temp.setLeft(null);
			temp.setRight(null);
		}
		return node;
	}
    /**
     * 
     * Description - Supporting method for minHeap4mBST
     * 
     * @param root
     * @param cIndex
     * @return
     */
	private static Node buildLL4mBST(Node root, CIndex cIndex) {
		if(root == null)
			return null;
		
		buildLL4mBST(root.getRight(), cIndex);
		
		if(cIndex.ptr == null){
			cIndex.ptr = root;
		}else{
			Node temp = cIndex.ptr;
			temp.setLeft(root);
			root.setRight(temp);
			
			cIndex.ptr=root;
		}
		
		buildLL4mBST(root.getLeft(), cIndex);
		
		return cIndex.ptr;
	}
	/**
	 * Description -Given a binary search tree which is also a complete binary tree. 
	 * The problem is to convert the given BST into a Min Heap with the condition that all the values in the left subtree of a node should be less than all the values in the right subtree of the node. 
	 * This condition is applied on all the nodes in the so converted Min Heap.
	 * 
	 * @param node
	 */
	public static void minHeap4mCBST(Node node, int n){
		int[] arr = new int[n];
		
		populateInorder(node, arr, new CIndex());
		cnstrctMinHeapPreOrder(node, arr, new CIndex());
	}

	private static void cnstrctMinHeapPreOrder(Node node, int[] arr, CIndex cIndex) {
		if(node == null || cIndex.index>arr.length-1)
			return;
		
		node.setData(arr[cIndex.index++]);
		cnstrctMinHeapPreOrder(node.getLeft(), arr, cIndex);
		cnstrctMinHeapPreOrder(node.getRight(), arr, cIndex);
	}

	private static void populateInorder(Node node, int[] arr, CIndex curr) {
		
		if(node == null || curr.index>arr.length-1){
			return;
		}
		
		populateInorder(node.getLeft(), arr, curr);
		arr[curr.index++]=node.getData();
		populateInorder(node.getRight(), arr, curr);
	}

	public static Node buildBST4mLvlOdrTraversal(int[] arr){
		Node root = new Node(arr[0]);
		
		Queue<DetailNode> que = new LinkedList<DetailNode>();
		
		DetailNode droot=new DetailNode(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
		
		que.add(droot);
		
		int i=1;
		
		while(i<arr.length && que.size()>0){
			DetailNode dn=que.poll();
			int leftKey = arr[i];
			if(leftKey < dn.ptr.getData() && leftKey>dn.min && leftKey<dn.max){
				Node left = new Node(leftKey);
				dn.ptr.setLeft(left);
				
				que.add(new DetailNode(left, dn.min, dn.ptr.getData()));
				
				i++;
			}
			
			int rightKey = arr[i];
			if(rightKey > dn.ptr.getData() && rightKey>dn.min && rightKey<dn.max){
				Node right = new Node(rightKey);
				dn.ptr.setRight(right);
				
				que.add(new DetailNode(right, dn.ptr.getData(), dn.max));
				
				i++;
			}
			
		}
		
		
		return root;
	}
	public static void main(String[] args) {
		/*int []pre = {10,5,1,7,40,50};
		
		Node bstRootOld = buildBSTOld(pre, 0, 5, 0);
		System.out.println(bstRootOld.getData());
		
		CIndex ind = new CIndex();
		Node bstRoot = buildBST(pre, Integer.MIN_VALUE, Integer.MAX_VALUE, ind);
		System.out.println(bstRoot.getData());
		
		Node bstRootStk = buildBSTStack(pre);
		System.out.println(bstRootStk.getData());
		
		int[] sortedArr={10,20,30,40,50,60,70};
		Node saRoot = sortedArray2BST(sortedArr, new CIndex(), sortedArr.length);
		System.out.println(saRoot.getData());
		List<Node> oneToNList = buildAllBST1toN(1, 3);
		System.out.println(oneToNList.size());*/
		/*int []pre = {4,2,1,3,6,5};
		Node bstRoot = buildBST(pre, Integer.MIN_VALUE, Integer.MAX_VALUE, new CIndex());
		System.out.println(bstRoot.getData());*/
		
		/*Node minHeapNode = minHeap4mBST(bstRoot);
		
		System.out.println(minHeapNode.getData());*/
		
		/*minHeap4mCBST(bstRoot, 6);
		
		System.out.println(bstRoot.getData());*/
		
		int lvl[] = {7, 4, 12, 3, 6, 8, 1, 5, 10};
		
		Node lvlNode = buildBST4mLvlOdrTraversal(lvl);
		System.out.println(lvlNode.getData());
	}
	
}
/**
 * Description - This is a helper class used for tracking indexes.
 * 
 * @author JANSARI1
 *
 */
class CIndex{
	Node ptr=null;
	int index=0;
}
class DetailNode{
	Node ptr;
	int min,max;
	
	public DetailNode(Node ptr, int min, int max){
		this.ptr=ptr;
		this.min=min;
		this.max=max;
	}
}
