package com.viperPDF.GUI;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class Filter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().endsWith(".pdf");
	}

	@Override
	public String getDescription() {
		return "PDF";
	}

}
