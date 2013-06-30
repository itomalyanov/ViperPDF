package com.viperPDF.ioFiles;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viperPDF.fileOverview.Engine;


/**
 * Class used to read a XML template file and generate DOM object from it.
 * 
 * @name GenerateDOM.java
 * @author viper
 * 
 */

public class GenerateDOM extends DefaultHandler  {

	static final Logger logger = Logger.getLogger(GenerateDOM.class.getName());
	
	private static Document document;
	private static SystemFile tmpFile;
	
	private String tmpTag;
	private StringBuilder parsedText;
	private Engine inputEngine; 
	private String testGit;
	
	public GenerateDOM(Engine engine) {
		parsedText = new StringBuilder();
		setInputEngine(engine);
		parsedText.append(inputEngine.getPdfToText().toString());
	}
	
	/**
	 *  Output XML info file for SystemFiles-> InfoFile
	 */
	public void infoFile() {
		tmpFile = new InfoFile(inputEngine.getFile().getName(), inputEngine);
		outputXML();
	}
	
	/**
	 * Construct GenerateDOM class with fileType and Engine object.
	 * 	1. Set temporary file.
	 *  2. Set parsedText.
	 *  3. Output XML.
	 *  
	 * @param fileType
	 * @param engine
	 */
	public GenerateDOM(FileType fileType, Engine engine) {
		parsedText = new StringBuilder(); 
		tmpFile = new InfoFile(engine.getFile().getName(), engine);
		
		setInputEngine(engine);
		parsedText.append(inputEngine.getPdfToText().toString());
		outputXML();
	}
	 
	public void generateInfoFileDOM(Engine engine) {
		
	}
	
	private void outputXML() {
		Document xmlDoc = tmpFile.domGenerator();
		tmpFile.outputGenerated(xmlDoc);
	}
	

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tmpTag = new String(ch, start, length);
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
 
	}


	/**
	 * Create new instance of InfoFile class.
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) 
			throws SAXException {
 
		 
	}


	public StringBuilder getParsedText() {
		return parsedText;
	}


	public void setParsedText(StringBuilder parsedText) {
		this.parsedText = parsedText;
	}

	
 	public static void main (String args[]) {
		
		File file;
		GenerateDOM genDom;
		try {
			
			//file = new File("E:\\ViperHP\\00_GitRepo\\ViperPdfSearching\\com.viperPdf.first\\ViperPDF\\viperPdfTemplate.xml");
			//genDom = new GenerateDOM(file);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public Engine getInputEngine() {
		return inputEngine;
	}


	public void setInputEngine(Engine inputEngine) {
		this.inputEngine = inputEngine;
	}

}
