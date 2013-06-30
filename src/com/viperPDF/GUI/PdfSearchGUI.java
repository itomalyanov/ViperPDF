package com.viperPDF.GUI;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.viperPDF.collections.FileFinder;
import com.viperPDF.fileOverview.Engine;
import com.viperPDF.hellpers.Info;
import com.viperPDF.ioFiles.FileType;

public class PdfSearchGUI extends JPanel implements ActionListener, ItemListener {
 
	private static final String NEW_LINE = "\n";
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(PdfSearchGUI.class.getName());
	
	
	JButton openButton, saveButton, closeButton;
	JTextArea textAriaLog;
	JFileChooser fileChooser;
//	JProgressBar progressBar;
	JTextField searchedText;
	
	/**
	 * 
	 */
	JCheckBox saveAsTextCh;
	
	StringBuffer extraOptions;
	public PdfSearchGUI() {
		
		super(new BorderLayout());
//		PropertyConfigurator.configure("log4j.properties");
		
		// Create and add Text Aria to panel
		textAriaLog = new JTextArea(15,40);
		textAriaLog.setMargin(new Insets(5,5,5,5));
		textAriaLog.setEditable(false);
		JScrollPane logScrollPane =  new JScrollPane(textAriaLog);

		
		fileChooser =  new JFileChooser();		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.addChoosableFileFilter(new Filter());
//		fileChooser.setAcceptAllFileFilterUsed(false); 
		
//		JPanel textPanel = new JPanel();
		JLabel searchingLabel =  new JLabel("Type a text to search:");
		searchedText =  new JTextField(20);
//		textPanel.add(searchingLabel);
//		textPanel.add(searchedText);
		
		
		openButton = new JButton("Open a FileChooser ...");
		openButton.addActionListener(this);
		
		saveButton = new JButton("Save File ...");
		saveButton.addActionListener(this);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		
		saveAsTextCh = new JCheckBox("Save parsed file as .txt");
		saveAsTextCh.setSelected(false);
//		saveAsTextCh.addItemListener(this);
		
//		progressBar = new JProgressBar(0, 0);
//		progressBar.setValue(0);
//		progressBar.setStringPainted(true);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(closeButton);
		
		JPanel ChBoxPanel = new JPanel();
//		ChBoxPanel.add(searchingLabel);
		ChBoxPanel.add(searchedText);
//		ChBoxPanel.add(saveAsTextCh);
		
//		JPanel progressPanel =  new JPanel();
//		progressPanel.add(progressBar);
//		progressPanel.getSize();
		
		add(buttonPanel, BorderLayout.PAGE_END);
//		add(textPanel, BorderLayout.CENTER);
		add(ChBoxPanel, BorderLayout.EAST);
//		add(logScrollPane, BorderLayout.CENTER);
//		add(progressPanel, BorderLayout.PAGE_END);
		
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		
		if(actionEvent.getSource() == openButton) {
			String searchedText = this.searchedText.getText();
			
			int returnVal = fileChooser.showOpenDialog(PdfSearchGUI.this);	
			
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				
				FileFinder getFiles;
//				Engine engine;
				try {
					long sTime = System.currentTimeMillis();
					
					getFiles = new FileFinder(fileChooser.getSelectedFile().getAbsolutePath());
					getFiles.searchInFiles(searchedText);
//					engine = new Engine(fileChooser.getSelectedFile().getAbsolutePath()); 
//					textAriaLog.append("Opening: "+ engine.getFile().getName() + "."+ NEW_LINE); 
//					engine.saveAs(FileType.TXT); 
					//TODO Searching Engine !!! 	
					System.out.print( "system finished for: " + 
								new Info().toSeconds(System.currentTimeMillis() - sTime)+ 
								" Seconds!");

				} catch (IOException e1) { 
					System.out.println("exception here!");
					e1.printStackTrace();
				}
				
			} else {
				textAriaLog.append("Open command cancelled by user." + NEW_LINE);
			}
			textAriaLog.setCaretPosition(textAriaLog.getDocument().getLength());
		
		} else if (actionEvent.getSource() == saveButton) {
            int returnVal = fileChooser.showSaveDialog(PdfSearchGUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                textAriaLog.append("Saving: " + file.getName() + "." + NEW_LINE);
            } else {
                textAriaLog.append("Save command cancelled by user." + NEW_LINE);
            }
            textAriaLog.setCaretPosition(textAriaLog.getDocument().getLength());
        } else if(actionEvent.getSource() == closeButton) {
//        	this.setVisible(false);
        	System.exit(0);
        }
	}
	
	public static void createAndShowGUI(String title) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(new PdfSearchGUI());	
		frame.pack();
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		int index = 0;
		Object source = e.getItemSelectable();
		char c = '-';
		
		if(source == saveAsTextCh) {
			index = 0;
			c = 't';
		}
	}
}
