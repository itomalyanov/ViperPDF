package com.viperPDF.fileOverview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

//import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;
import com.viperPDF.GUI.Utils;
import com.viperPDF.ioFiles.FileType;
import com.viperPDF.ioFiles.GenerateDOM;
import com.viperPDF.ioFiles.InfoFile;

/**
 * Class Engine;
 * Get selected file and put it through the PDF Box parser.
 * Get the value returned as text from PDFTextStripper and save it 
 * 	as separated (txt) file.
 * 
 *  //TODO 
 *  	1. Make a temporary text file to search in! 
 *  	
 * 
 * @author viper
 *
 */
public class Engine {

	static final Logger logger = Logger.getLogger(Engine.class.getName());

	public String filePath;
	private File selectedFile;
	private BufferedReader stream;
	private String pattern;
	public StringBuilder pdfToText;
	
//	private InfoFile tempFile;
	private GenerateDOM generateDOM;
	private File outFile;
	 
	/**
	 * PDF BOX VARIABELS
	 */
	private PDFParser 				parser;
	private PDFTextStripper 		pdfStripper;
	private PDDocument 			 	pdDoc;
	private COSDocument 			cosDoc;
	private PDDocumentInformation 	pdDocInfo;
	private StringBuilder 			StringBuilder;

	public Engine(String filePath, String pattern) {
		this(filePath);
		setPattern(pattern);
	}
	
	public Engine(File file, String pattern) throws IOException {
		this(file);
		setPattern(pattern);
	}
	
	/**
	 * Construct Engine class by given file path as a String.
	 *  1.setFile();
	 *  2.parseFileToText();
	 *  3.generateDOM();
	 *  
	 * @param filePath
	 */
	public Engine(String filePath)
	{
		this.filePath = filePath;
		setFile(new File(filePath));
		
		try {
			parseFileToText();
//			generateDOM = new GenerateDOM(FileType.INFO_XML_FILE, this);
		} catch (IOException e) {
			logger.debug("Can not open the input file! " + filePath);
			e.printStackTrace();
		}
	}
	
	/**
	 * Construct Engine class by given file, throws IOException;
	 *  1.setFile();
	 *  2.parseFileToText();
	 *  3.generateDOM()
	 *  
	 * @param path
	 * @throws IOException
	 */
	public Engine(File path) throws IOException {
		filePath = path.getAbsolutePath();
		setFile(path);
		parseFileToText();
//		generateDOM = new GenerateDOM(FileType.INFO_XML_FILE, this);
	}
	
	
	public void generateInfoFile() {
		generateDOM = new GenerateDOM(this);
		generateDOM.infoFile();
	}
	
	/**
	 * Parse given file to a string!
	 * 
	 * @param file
	 * @return String 
	 * @throws IOException 
	 */
	public String parseFileToText() throws IOException {
		StringBuilder parsedText = new StringBuilder();
		
		try { 
			parser = new PDFParser(new FileInputStream(selectedFile));
		} catch (IOException e) {
			logger.debug("PDFParse is unable to create new objec for input stream "+ selectedFile +"!\r\n", e);
			e.printStackTrace();
		}	
		
		try { 
			parser.parse(); 
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdDocInfo = pdDoc.getDocumentInformation();
		  
			List<Integer> pages = deliver(pdDoc.getNumberOfPages());
			logger.info("Open file:" +  selectedFile.getName());
			logger.info("Delivering pages. Pages count:" + pdDoc.getNumberOfPages());	
			for (int i = 0; i < pages.size(); i++) {
				if (i % 2 == 0) {
					pdfStripper.setStartPage(pages.get(i));
					pdfStripper.setEndPage(pages.get(i + 1));

					parsedText.append(pdfStripper.getText(pdDoc));
				}
			}
			setPdfToText(parsedText);
		} catch (IOException e) {
			logger.debug("An exception occured in parsing the PDF Document.", e); 
			e.printStackTrace();
		} finally {
			if (cosDoc != null){ cosDoc.close(); }
			if (pdDoc != null) {pdDoc.close();}
		} 
 
		return parsedText.toString();		
	}

	/** 
	 * @TODO check where this function is used or is better to use 
	 * @param fileName
	 * @throws IOException
	 */
	public void saveAs(FileType type) throws IOException { 
		String textToSave = getPdfToText().toString();
		String fileDestination = Utils.changeFileExtension(this.filePath, type);
		PrintStream printStream = null;
		
		try {
			logger.info("Prints the text to the text file!");
			outFile = new File(fileDestination);
			printStream = new PrintStream(outFile, "UTF-8");
			printStream.println(textToSave); 
//			openFile(outFile);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} finally {
			if (printStream != null) { printStream.close(); }
		} 
	}// end of saveAsText

	/**
	 * Save parsed text as given <b>FileType</b> and option for open saved file.
	 * @param type
	 * @param start
	 * @throws IOException
	 */
	public void saveAs(FileType type,  boolean start) throws IOException{
		
		if(start == true) {
			saveAs(type);	
			openFile(outFile);
		} else {
			saveAs(type);
		} 
	}
	
	/**
	 * Opens saved text file.
	 * @param file
	 * @throws IOException
	 */
	private void openFile(File file) throws IOException {
		this.outFile = file;
		java.awt.Desktop.getDesktop().edit(outFile);
	}
	
	/**
	 * Deliver the pages on small parts. If the pages are less than 100 the
	 * method passes delivering.
	 * 
	 * @param pageNumbers
	 *            number of pages
	 * @return List with "pagination"
	 */
	public List<Integer> deliver(int pageNumbers) {

		List<Integer> pages = new ArrayList<Integer>();

		int start = 1;
		int deliver;
		int parts;

		if (pageNumbers <= 100) {
			pages.add(start);
			pages.add(pageNumbers);

			return pages;
		}

		if ( pageNumbers > 1999 ) { parts = 20; }
		else 			  { parts = 10; }

		deliver = pageNumbers / parts;
		int stop = deliver;

		if ((pageNumbers % parts) == 0) {

			for (int i = 0; i < parts; i++) {
				pages.add(start);
				pages.add(stop);
				start = stop + 1;
				stop += deliver;
			}
		} else {
			for (int i = 0; i < parts; i++) {
				pages.add(start);
				pages.add(stop);

				if (i == parts - 2) {
					start = stop + 1;
					stop += deliver + (pageNumbers % 10);
				} else {
					start = stop + 1;
					stop += deliver;
				}
			}
		}

		return pages;
	}

	public File getFile() {
		return selectedFile;
	}

	public void setFile(File file) {
		this.selectedFile = file;
	}

	public BufferedReader getStream() {
		return stream;
	}

	public void setStream(BufferedReader stream) {
		this.stream = stream;
	}
	
	public StringBuilder getPdfToText() {
		return pdfToText;
	}

	public void setPdfToText(StringBuilder pdfToText) {
		this.pdfToText = pdfToText;
	}
  

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
  

	/**
	 * @return the pdDoc
	 */
	public PDDocument getPdDoc() {
		return pdDoc;
	}

	/**
	 * @return the cosDoc
	 */
	public COSDocument getCosDoc() {
		return cosDoc;
	}

	/**
	 * @return the pdDocInfo
	 */
	public PDDocumentInformation getPdDocInfo() {
		return pdDocInfo;
	}

	/**
	 * @param selectedFile the selectedFile to set
	 */
	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	
}
