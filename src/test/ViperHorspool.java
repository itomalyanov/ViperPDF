/**
 * 
 */
package test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author viper : November 18, 2012
 * 
 */
public class ViperHorspool {

	private String pattern, text; // Pattern, Text
	private int pattLenght; // pattern length;
	private long textLenght; // text length
	private Map<Character, Integer> badCharacter;
	
	/**
	 * 
	 */
	public ViperHorspool() {

	}

	public void setPattern(String pattern) {
		
		pattLenght = pattern.length();
		this.pattern = pattern;
		badCharacter = new HashMap<Character, Integer>(pattLenght);
		horospoolInitbatChar();
	}

	private void horospoolInitbatChar() {

		for(int i = 0; i < pattLenght-1; i++) {
			badCharacter.put(pattern.charAt(i), pattLenght-i-1);
		}
	}
	
	public void setText(String text) {
		textLenght = text.length();
		text = text;
		
	}

	public void ilian(String txt, String pattern) {
		setText(txt);
		setPattern(pattern);
		search(pattern, txt);
//		viperSearch(txt, pattern);
	}

	public int search(String pattern, String text) {
		int M = pattern.length(), N = text.length();
		int[] right = new int[256];
		for (int c = 0; c < 256; c++)
			right[c] = -1;
		for (int j = 0; j < M; j++)
			right[pattern.charAt(j)] = j;
		int i = 0; // offset
		while (i < N - M) {
			int skip = 0;
			for (int j = M - 1; j >= 0; j--) {
				if (pattern.charAt(j) != text.charAt(i + j)) {
					skip = Math.max(1, j - right[text.charAt(i + j)]);
					break;
				}
			}
			if (skip == 0)
				return i; // found
			i = i + skip;
		}
		return -1;
	}

	private boolean viperSearch(String txt, String pattern) {

		int i = 0, j;
		char tmpCharAtText;
		while (i <= textLenght - pattLenght) {
			j = pattLenght - 1;
			tmpCharAtText = text.charAt(i + j);
			while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
				System.out.println("pattern chart at:" + i + " is:"
						+ pattern.charAt(i));
				System.out.println("pattern chart at:" + i + " is:"
						+ pattern.charAt(i));
				j--;
			}

			// if(j<=0) return true;
			i += pattLenght - 1;
			i -= badCharacter.get(tmpCharAtText);
		}
		return false;
	}

	public static void main(String[] args) {

		ViperHorspool vip = new ViperHorspool();

		String t = "Here we are working only with english alphabetic!";
		String p = "working";

		vip.ilian(t, p);
		if (t.charAt(7) == p.charAt(0)) {
			System.out.println("stawa");
		}
	}
}
