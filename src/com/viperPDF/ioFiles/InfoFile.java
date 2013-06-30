package com.viperPDF.ioFiles;
/**
 * Class InfoFile represent the structure of the info.xml file 
 * which will be stored in the memory for future, with information about 
 * for what is it opened;
 *  
 * 
 * @author viper
 * @date 
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.viperPDF.GUI.Utils;
import com.viperPDF.fileOverview.Engine;
//import org.w3c.dom.Document;

public class InfoFile extends SystemFile {
 
	private final String ROOT_TAG = "PdfFileInfo";
	
	private List<String> documentInfo;
	private List<String> matches;
	private List<String> documentResearch;
	
	private StringBuilder documentText;
		
	private String diTitle;
	private String diFileName;
	private String diAuthor;
	private int diPages;
	private long diSize;
	private String diSizeUnit;
	private String date;
	
	private List<String> drSearcFor;
	private String drSearcForPattern;
	private int drSearchForPatternId;
	private String drSearchedOn;
	
	private List<String> drMathces;
	private List<String> drMMatch;
	private int drMMatchId;
	private String drMMatchString;
	private int drMMatchedPage;
	private int drMMatchedRow;
	
	private File outputFile;
	private Engine inputEngine;
	
	public InfoFile(String file, Engine engine) {
		String infoFileName = Utils.changeFileExtension(file, FileType.INFO_XML_FILE);
		setInputEngine(engine);
		outputFile = new File(FileType.INFO_XML_PATH.getType() + infoFileName); //.getType()
	}
	
	
	private void assignValues() {
		
		setDiTitle(inputEngine.getPdDocInfo().getTitle());
		setDiAuthor(inputEngine.getPdDocInfo().getAuthor());
		setDiPages(inputEngine.getPdDoc().getNumberOfPages());
		setDiSize(inputEngine.getFile().length());
		setDiSizeUnit("KB");
		setDiFileName(inputEngine.getFile().getName());
		
		setDrSearcForPattern(inputEngine.getPattern());
//		setDrSearcForPattern(drSearcForPattern); 
		setDrSearchedOn(new SimpleDateFormat("dd/mm/yy - HH:mm:ss").format(Calendar.getInstance().getTime()));
		setDocumentText(inputEngine.pdfToText);
	}
	
	/**
	 * Generate XML code for InfoFile.
	 * 
	 * @return
	 */
 	public Document domGenerator() {
 		assignValues();
		Element  rootElement = new Element(ROOT_TAG);
		Document document = new Document(rootElement);
		
		Element docInfo = new Element(InfoXmlTags.DOCUMENT_INFO_L.getTag());
			Element diTitle  = new Element(InfoXmlTags.TITLE.getTag()).setText(getDiTitle());
			Element diAuthor = new Element(InfoXmlTags.AUTHOR.getTag()).setText(getDiAuthor());
			Element diPages  = new Element(InfoXmlTags.PAGES.getTag()).setText(Integer.toString(getDiPages()));
			Element diSize   = new Element(InfoXmlTags.SIZE.getTag()).setText(Long.toString(getDiSize()));
			diSize.setAttribute(InfoXmlTags.UNIT.getTag(), getDiSizeUnit());
			
		docInfo.addContent(diTitle)
				.addContent(diAuthor)
				.addContent(diPages)
				.addContent(diSize);
		
		Element docResearch =  new Element(InfoXmlTags.DOCUMENT_RESEARCH_L.getTag());
		
		Element drSearcFor = new Element(InfoXmlTags.SEARCH_FOR.getTag());
		drSearcFor.setAttribute(InfoXmlTags.ID.getTag(), "1");
		 
		Element drSearchPattern = new Element(InfoXmlTags.PATTERN.getTag()).setText(getDrSearcForPattern());
		Element drSearchOn =  new Element(InfoXmlTags.DATE.getTag()).setText(getDrSearchedOn());

		drSearcFor.addContent(drSearchPattern).addContent(drSearchOn);
		
		docResearch.addContent(drSearcFor); 
    
		// Output parsed text in separated   tag file.
		Element docText = new Element(InfoXmlTags.DOCUMENT_TEXT.getTag());
		docText.addContent(inputEngine.getPdfToText().toString());
		
		rootElement.addContent(docInfo.detach())
				   .addContent(docResearch.detach())
				   .addContent(docText.detach());

		return document;	
	}
	
	/**
	 * Output generated XML for info file.
	 */
	public void outputGenerated(Document document) {

		try {
			//File outFile = new File(FileType.INFO_XML_PATH.getType() + getOutputFile());
			FileWriter writer =  new FileWriter(getOutputFile());
			XMLOutputter outputter =  new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
			outputter.output(document, writer);
			writer.close();
			
		} catch (IOException e) { 
			 e.printStackTrace();
		}	
	}
	  
	
	public List<String> getDocumentInfo() {
		return documentInfo;
	}
	public void setDocumentInfo(List<String> documentInfo) {
		this.documentInfo = documentInfo;
	}
	public List<String> getMatches() {
		return matches;
	}
	public void setMatches(List<String> matches) {
		this.matches = matches;
	}
	public List<String> getDocumentResearch() {
		return documentResearch;
	}
	public void setDocumentResearch(List<String> documentResearch) {
		this.documentResearch = documentResearch;
	}
	public StringBuilder getDocumentText() {
		return documentText;
	}
	public void setDocumentText(StringBuilder pdfToText) {
		this.documentText = pdfToText;
	}
	public String getDiTitle() {
		return diTitle;
	}
	public void setDiTitle(String diTitle) {
		this.diTitle = diTitle;
	}
	public int getDiPages() {
		return diPages;
	}
	public void setDiPages(int diPages) {
		this.diPages = diPages;
	}
	public long getDiSize() {
		return diSize;
	}
	public void setDiSize(long l) {
		this.diSize = l;
	}
	public String getDiSizeUnit() {
		return diSizeUnit;
	}
	public void setDiSizeUnit(String diSizeUnit) {
		this.diSizeUnit = diSizeUnit;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getDrSearcFor() {
		return drSearcFor;
	}
	public void setDrSearcFor(List<String> drSearcFor) {
		this.drSearcFor = drSearcFor;
	}
	public String getDrSearcForPattern() {
		return drSearcForPattern;
	}
	public void setDrSearcForPattern(String drSearcForPattern) {
		this.drSearcForPattern = drSearcForPattern;
	}
	public String getDrSearchedOn() {
		return drSearchedOn;
	}
	public void setDrSearchedOn(String drSearchedOn) {
		this.drSearchedOn = drSearchedOn;
	}
	public List<String> getDrMathces() {
		return drMathces;
	}
	public void setDrMathces(List<String> drMathces) {
		this.drMathces = drMathces;
	}
	public List<String> getDrMMatch() {
		return drMMatch;
	}
	public void setDrMMatch(List<String> drMMatch) {
		this.drMMatch = drMMatch;
	}
	public int getDrMMatchId() {
		return drMMatchId;
	}
	public void setDrMMatchId(int drMMatchId) {
		this.drMMatchId = drMMatchId;
	}
	public String getDrMMatchString() {
		return drMMatchString;
	}
	public void setDrMMatchString(String drMMatchString) {
		this.drMMatchString = drMMatchString;
	}
	public int getDrMMatchedPage() {
		return drMMatchedPage;
	}
	public void setDrMMatchedPage(int drMMatchedPage) {
		this.drMMatchedPage = drMMatchedPage;
	}
	public int getDrMMatchedRow() {
		return drMMatchedRow;
	}
	public void setDrMMatchedRow(int drMMatchedRow) {
		this.drMMatchedRow = drMMatchedRow;
	}
	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public Engine getInputEngine() {
		return inputEngine;
	}

	public void setInputEngine(Engine inputEngine) {
		this.inputEngine = inputEngine;
	}

	/**
	 * @return the drSearchForPatternId
	 */
	public int getDrSearchForPatternId() {
		return drSearchForPatternId;
	}

	/**
	 * @param drSearchForPatternId the drSearchForPatternId to set
	 */
	public void setDrSearchForPatternId(int drSearchForPatternId) {
		this.drSearchForPatternId = drSearchForPatternId;
	}


	/**
	 * @return the diFileName
	 */
	public String getDiFileName() {
		return diFileName;
	}


	/**
	 * @param diFileName the diFileName to set
	 */
	public void setDiFileName(String diFileName) {
		this.diFileName = diFileName;
	} 

	/**
	 * @return the diAuthor
	 */
	public String getDiAuthor() {
		return diAuthor;
	}


	/**
	 * @param diAuthor the diAuthor to set
	 */
	public void setDiAuthor(String diAuthor) {
		this.diAuthor = diAuthor;
	}
	
}
