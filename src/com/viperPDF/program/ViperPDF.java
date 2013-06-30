package com.viperPDF.program;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.viperPDF.GUI.PdfSearchGUI;

public class ViperPDF  {

	static Logger log = Logger.getLogger(ViperPDF.class.getName());
	
	public static void main(String[] arg) {
	    log.debug("Debug info!");
	  
		SwingUtilities.invokeLater(new Runnable(){
			@SuppressWarnings("static-access")
			public void run(){
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new PdfSearchGUI().createAndShowGUI("Viper PDF Search");
			}
		});
		log.info("Info message!");
	}
}
