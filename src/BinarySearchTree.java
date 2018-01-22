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

	public static void main(String[] args) {
		int []pre = {10,5,1,7,40,50};
		
		Node bstRootOld = buildBSTOld(pre, 0, 5, 0);
		System.out.println(bstRootOld.getData());
		
		CIndex ind = new CIndex();
		Node bstRoot = buildBST(pre, Integer.MIN_VALUE, Integer.MAX_VALUE, ind);
		System.out.println(bstRoot.getData());
		
		Node bstRootStk = buildBSTStack(pre);
		System.out.println(bstRootStk.getData());
	}
	
}
/**
 * Description - This is a helper class used for tracking indexes.
 * 
 * @author JANSARI1
 *
 */
class CIndex{
	int index=0;
}
