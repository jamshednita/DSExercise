import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainApp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/*File file =  new File("C:\\Users\\jansari1\\ValidatorJSON\\Excercise\\src\\SampleText.txt");
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		
		String line=null;
		while ((line=buffReader.readLine())!=null) {
			System.out.println(line);
			
			String[] tokens = line.split("-");
			System.out.println(tokens.length);
		}*/
		/*System.out.println(isIpAddress("68.32.65.58"));
		System.out.println(isValidIP("www.google.com"));
		//System.out.println(IPAddressLookup.isIPv4InDotDecimalNotation("68.32.65.58"));
		
		Node root = new Node(2);
		Node left = new Node(1);
		Node right = new Node(3);
		
		root.setLeft(left);
		root.setRight(right);
		doubleTree(root);
		System.out.println(root.getRight().getLeft().getData());*/
		
		Node root = new Node(1);
		Node left = new Node(2);
		Node right = new Node(3);
		
		
		Node left1 = new Node(4);
		Node right1 = new Node(5);
		
		Node left2 = new Node(6);
		Node right2 = new Node(7);
		Node left3 = new Node(8);
		Node right3 = new Node(9);

		Node left4 = new Node(10);
		Node right4 = new Node(11);
		
		Node left5 = new Node(12);
		Node right6 = new Node(13);

		Node left7 = new Node(14);
		Node right8 = new Node(15);
		
		root.setLeft(left);
		root.setRight(right);
		left.setLeft(left1);
		left.setRight(right1);
		right.setLeft(left2);
		right.setRight(right2);
		
		left1.setLeft(left3);
		left1.setRight(right3);
		right1.setLeft(left5);
		
		right2.setLeft(left4);
		
		right3.setLeft(right6);
		right3.setRight(left7);
		left4.setRight(right4);
		left7.setLeft(right8);
		
		removeKLess(root, 0, 20);
		
		System.out.println(root.getData());
	}
	
	public static boolean isIpAddress(String host) {
	    String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	    if (host.toLowerCase().contains(ip_filter.toLowerCase())){
	        return true;
	    }
	    return false;
	}
	
	public static boolean isValidIP(String ipAddr){
        
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }
	
	public static void doubleTree(Node node){
		if(node ==  null) return ;
		
		Node nn = new Node(node.getData());
		
		nn.setLeft(node.getLeft());
		node.setLeft(nn);
		
		doubleTree(nn.getLeft());
		doubleTree(node.getRight());
	}
	
	public static boolean removeKLess(Node node, int sum, int k){
		if(node.getLeft()!=null && sum+node.getData()<k){
			//boolean flag = removeKLess(node.getLeft(), sum+node.getData(), k);
			if(removeKLess(node.getLeft(), sum+node.getData(), k)){
				node.setLeft(null);
			}
		}
		if(node.getRight()!=null && sum+node.getData()<k){
			//removeKLess(node.getRight(), sum+node.getData(), k);
			if(removeKLess(node.getRight(), sum+node.getData(), k)){
				node.setRight(null);
			}
		}
		
		if(node.getLeft()==null && node.getRight()==null && sum+node.getData()<k){
			return true;
		}
		return false;
	}

}
