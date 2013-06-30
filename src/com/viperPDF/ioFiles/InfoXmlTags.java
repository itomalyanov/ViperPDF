package com.viperPDF.ioFiles;

public enum InfoXmlTags { 	
	PDF_FILE_INFO ("PdfFileInfo"),
	DOCUMENT_INFO_L ("documentInfo"), 
	DOCUMENT_TEXT ("documentText"), 
	DOCUMENT_RESEARCH_L ("documentResearch"), 
	SEARCH_FOR ("searchFor"), 
	MATCHES_L ("matches"), 
	TITLE ("title"), 
	AUTHOR ("author"),
	PAGES ("pages"), 
	SIZE ("size"),
	UNIT ("unit"),
	DATE ("date"), 
	PATTERN ("pattern"), 
	ID ("id"), 
	STRING ("string"), 
	PAGE ("page"), 
	ROW ("row");    
	
	
	private String tag;
    
	private InfoXmlTags(String tag) 
	{
		this.tag = tag;
	}
	
	
	public String getTag() {
		return tag;
	}
}
