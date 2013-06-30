package com.viperPDF.ioFiles;

import org.jdom2.Document;

public abstract class SystemFile {

	
	public abstract Document domGenerator() ;
	
	public abstract void outputGenerated(Document document);
	
}
