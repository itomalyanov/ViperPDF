package com.viperPDF.collections;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.viperPDF.GUI.Utils;
import com.viperPDF.fileOverview.Engine;
import com.viperPDF.ioFiles.FileType;

public class FileFinder {

	private static Logger logger = Logger.getLogger(FileFinder.class.getName());
	
	private Queue<String> filesQueue = new LinkedList<String>();;
	private String dirPath;
	private Engine engine;
	

	public FileFinder(String path) {
		this.dirPath = path;
		traverseDir(new File(path));		
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void openFiles() throws IOException {
		for(String file : filesQueue) {
			engine = new Engine(file);
			logger.info("Opening file:" + engine.getFile().getName());
			engine.saveAs(FileType.TXT);
			logger.debug("Save selected pdf file as "+ FileType.TXT + ";" +
					" At dir:"+ engine.getFile().getAbsolutePath()); 
		}
	}
	
	
	private void openFiles(String searchPattern) throws IOException {
		
		for(String file: filesQueue) {
			
			engine = new Engine(file);
			logger.info("Opening file:" + engine.getFile().getName());
			engine.setPattern(searchPattern);
			engine.generateInfoFile();
			engine.saveAs(FileType.TXT, false);
			logger.debug("Save selected pdf file as "+ FileType.TXT + ";" +
					" At dir:"+ engine.getFile().getAbsolutePath()); 
			
		}
		
	}
	
	/**
	 * This method is to set a searched pattern for pdf files.
	 * 
	 * @param pattern
	 * @throws IOException
	 */
	public void searchInFiles(String pattern) throws IOException {
		openFiles(pattern); 
	}
	
	/**
	 * Goes through file directories, takes each file with valid extension.
	 * @param file
	 */
	private void traverseDir(File file) {

		System.out.println("viper "+file.getAbsolutePath());
		if (file.isDirectory()) {
			String[] children = file.list();

			for (String child : children) {
				
				if(Utils.checkFor(FileType.PDF, dirPath  + child) ) {
					filesQueue.offer(dirPath + "\\" + child);
				} 
				traverseDir(new File(child));
			}

		} else if (file.isFile()) {
			filesQueue.offer(file.getAbsolutePath());
		}

	}

	/**
	 * @return the files
	 */
	public Queue<String> getFiles() {
		return filesQueue;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(Queue<String> files) {
		this.filesQueue = files;
	}
  
}
