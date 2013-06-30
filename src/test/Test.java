/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dodge_viper : 16.09.2012
 *
 */
public class Test {

	public static void main(String[] args) {
		String ilian = "илиански";
		if(ilian.charAt(0) == ilian.charAt(2)) {
			System.out.println(ilian.charAt(2));	
		}
		
	}
	
	
	public List deliver(int num) {		
		
		int deliver = num / 10 ;
		
		List pages = new ArrayList();
		
		int start = 1;
		int stop = deliver;
		if((num % 10) == 0) {
			
			for (int i = 0; i < 10; i++) {
				pages.add(start);
				pages.add(stop);
				start = stop + 1;
				stop += deliver;
			}
		} else {
			for (int i = 0; i < 10; i++) {
				pages.add(start);
				pages.add(stop);

				if(i == 8) {
					start = stop + 1;
					stop += deliver + (num % 10);		
				} else {
					start = stop + 1;
					stop += deliver;
				}
			}
		}
		
		for(int i = 0; i <pages.size(); ++i){
			if(i%2 == 0) {
				System.out.println(pages.get(i) +" - "+ pages.get(i+1) + " and [i] is: "+i);
			}
			
			
//			System.out.println(pages.get(i));
		}
		
		return pages;
	}
	
	
}
