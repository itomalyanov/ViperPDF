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

	private String p, t; // Pattern, Text
	private int pL; // pattern length;
	private long tL; // text length
	private Map<Character, Integer> badCharacter;
	
	/**
	 * 
	 */
	public ViperHorspool() {

	}

	public void setPattern(String pattern) {
		
		pL = pattern.length();
		p = pattern;
		badCharacter = new HashMap<Character, Integer>(pL);
		horospoolInitbatChar();
	}

	private void horospoolInitbatChar() {

		for(int i = 0; i < pL-1; i++) {
			badCharacter.put(p.charAt(i), pL-i-1);
		}
	}
	
	public void setText(String text) {
		tL = text.length();
		t = text;
		
	}

	public void ilian(String txt, String pattern) {
		setText(txt);
		setPattern(pattern);
//		search(pattern, txt);
		viperSearch(txt, pattern);
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
		while (i <= tL - pL) {
			j = pL - 1;
			tmpCharAtText = t.charAt(i + j);
			while (j >= 0 && p.charAt(j) == t.charAt(i + j)) {
				System.out.println("pattern chart at:" + i + " is:"
						+ p.charAt(i));
				System.out.println("pattern chart at:" + i + " is:"
						+ p.charAt(i));
				j--;
			}

			// if(j<=0) return true;
			i += pL - 1;
			i -= badCharacter.get(tmpCharAtText);
		}
		return false;
	}

	public static void main(String[] args) {

		ViperHorspool vip = new ViperHorspool();

		String t = "илиан методиев томаляновsdttrtddsrreerwreterrewwerwreetr";
		String p = "малянов";

		vip.ilian(t, p);
		if (t.charAt(7) == p.charAt(0)) {
			System.out.println("stawa");
		}
	}
}
