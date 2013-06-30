package com.viperPDF.GUI;

import javax.swing.JOptionPane;

import com.viperPDF.ioFiles.FileType;


public class Utils {

	public static final String pdf = "pdf"; 
	
	
	/**
	 * Change the file extension.
	 * 
	 * @param file File name or full file path.
	 * @param extension The new extension.
	 * @return File name or path with new extension
	 */
	public static String changeFileExtension (String file, FileType extension) {
		String fullPath = file.substring(0, file.lastIndexOf('.'));
		
		return fullPath.concat("." + extension.getType());
	}
	
	/**
	 * Gets \w+ after the last dot of document path.
	 * @param file path to file.
	 * @param extension system acceptable extension.
	 * @return extension of the file if matched with system file types, else null.
	 */
	private static String getFileExtension(String file, FileType extension) { 
		
		String fileExt = file.substring(file.lastIndexOf(".") + 1);
		if(fileExt.equalsIgnoreCase(extension.getType())) {
			return extension.getType();
		} 
		return null;
	}
	
	/**
	 * Gets file extension only by given path, 
	 * it's not matted if the extension is acceptable for the system.
	 * 
	 * @param file
	 * @return extension of the file like "pdf, txt, doc"
	 */
	private static String getFileExtension(String file) {
		String fileExtension = file.substring(file.lastIndexOf(".") + 1); 
		return fileExtension;
	}
	
	/**
	 * Change the given file name with newName and the same extension.
	 * 
	 * @param file     File to be renamed.
	 * @param newName  New name for file.
	 * @return		 	The file with new name and same extension.
	 */
	public static String changeFileName(String file, String newName) {
		
		
		return newName;
	}
	
	/**
	 * Check if given file absolute path is acceptable for FileType.
	 * @param type set File type to be checked for;
	 * @param path set absolute path to file;
	 * @return
	 */
	public static boolean checkFor(FileType type, String path) {
		
		if(getFileExtension(path).equals(type.getType())) {
			return true;
		} 
		
		return false;
	}
	
	public static void infoBox(String messageInfo, String path) {
		JOptionPane.showMessageDialog(null,	messageInfo, "Info Box!", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
