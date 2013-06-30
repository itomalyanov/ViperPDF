/**
 * 
 */
package com.viperPDF.fileOverview;

/**
 * @author viper : Oct 24, 2012
 *
 */
public class Searching {
	
	private final int R; // the radix
	private int[] right;	//the bad-character skip array
	
	private char[] patChar; // store the pattern as a char array
	private String patString; // Stores the pattern as a String
	
	public Searching(String pattern) {
		this.R = 256;
		this.patString = pattern;
		
		this.right = new int[R];
		
		for(int c = 0; c < R; c++)
			right[c] = -1;
		for(int j = 0; j < patString.length(); j++)
			right[patString.charAt(j)] = j;
	}

	public int searchIn(String text) {
		int M = patString.length();
		int N = text.length();
		int skip;
		
		for(int i=0; i <= N - M; i += skip){
			skip = 0;
			for(int j = M-1; j >= 0; j--) {
				if(patString.charAt(j) != text.charAt(i+j)){
					skip = Math.max(1, j-right[text.charAt(i+j)]);
					break;
				}
			}
			if (skip == 0) return i; 	// found
		}
		return N;						// not found
	}
}
