package com.viperPDF.ioFiles;

/**
 * Contains the fail extensions which could be used!
 * 
 * @author viper
 * 
 */
public enum FileType {
	TXT("txt"), INFO_XML_FILE("info.xml"), PDF("pdf"), INFO_XML_PATH(
			"E:\\GitVersion\\ViperPDF\\system\\workingFiles\\");
	
	private String type;

	private FileType(String stype) {
		this.type = stype;
		getType();
	}

	public String getType() {
		return type;
	}
}
