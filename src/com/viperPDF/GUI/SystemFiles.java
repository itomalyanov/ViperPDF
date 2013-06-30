package com.viperPDF.GUI;

/**
 * Enum class to contains the system files.
 * 	1. INFO    Path to info file which contains the information about which file has already been started.
 *  2. TEMP    Path to temporary file where is stored the parsed text from pdf file. 
 *  3. SEARCH  Path to search log file.
 *  
 * @author viper
 *
 */
public enum SystemFiles {
	
	INFO(""), TEMP(""), SEARCH("");
	
	private final String path;
	
	private SystemFiles(String path) {
		this.path = path;
	}
	
	public String getValue()
	{
		return path;
	}
	 
}
