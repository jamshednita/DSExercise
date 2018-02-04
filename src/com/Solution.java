package com.learnhonestly.gstest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] in={2,123,1231};
		System.out.println(findQualifiedNumbers(in));
		
		System.out.println(findPossibleSmallestNumberMatchingPattern("NNN"));

	}

	 static String findQualifiedNumbers(int[] numberArray) {
	        List<Integer> output = new ArrayList<Integer>();
	        for (Integer in : numberArray) {
	        	String num = in.toString();
	            if(num.indexOf('1')>-1 && num.indexOf('2')>-1 && num.indexOf('3')>-1)
	                output.add(in);
			}
	            
	        if(output.size()==0){
	            return "-1";
	        }else{
	            Collections.sort(output);
	            StringBuilder outBuilder = new StringBuilder();
	            output.forEach(out->{
	            	if(outBuilder.toString().length()>0)
	            		outBuilder.append(",");
	                outBuilder.append(out.toString());
	            });
	            return outBuilder.toString();
	       }
	 }
	 
	 static int findPossibleSmallestNumberMatchingPattern(String pattern) {
		 StringBuilder outBuilder = new StringBuilder();
		 
	        for(int i=0; i<pattern.length();i++){
	        	if(i==0 && pattern.charAt(i)=='N')
	        		outBuilder.append("12");
	        	else if(i==0 && pattern.charAt(i)=='M')
	        		outBuilder.append("21");
	        	else{
	        		if(pattern.charAt(i)=='M'){
	        			Integer lastDigit = new Integer(outBuilder.substring(outBuilder.length()-1));
	        			
	        			int tempLast=lastDigit;
	        			int j= outBuilder.length()-1;
	        			while(j>0){
	        				if(Integer.valueOf(outBuilder.substring(j-1,j))>tempLast){
	        					tempLast=Integer.valueOf(outBuilder.substring(--j, j+1));
	        				}else
	        					break;
	        			}
	        			// Add one to each element of subString --
	        			String sub=outBuilder.substring(j);
	        			StringBuilder tempBuild= new StringBuilder();
	        			for(int x=0; x<sub.length(); x++){
	        				tempBuild.append(Integer.valueOf(sub.substring(x, x+1))+1);
	        			}
	        			outBuilder.replace(j, outBuilder.length(), tempBuild.toString());
	        			outBuilder.append(lastDigit);
	        		}else{
	        			outBuilder.append(outBuilder.length()+1);
	        		}
	        	}
	        			        	
	        }
	        
	        return new Integer(outBuilder.toString());
	 }
}
