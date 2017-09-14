package com.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Agenda: carry 


/*
 * now i working on
 * dividing EngCoach's
 *  big methods into
 * several small and clear ones
 * 
 * 
 * I came up with an idea
 * that some methods are used in both classes: EngCoach and AddNewContent
 * so I can create a class which constains these duplicate methods and than
 * EngCoach and AddNewContent will inheret from this class
 * This way I can make my app less unreadable and add one of the OOP principle
 * */


/*
 * method getWord() can be divided into:
 * 	> getWord()	
 * 	> setWordInField()
 *  > makeWordUnique()
 *  > readFile() - this one can be taken from AddNewContent.java
 * 
 * 
 * 
 * */

public class poligon{

	
	// ### readFile ###
	private String [] boundaries;
	
	
	private String filePath= "D://Dropbox/java/EngMaterialsForTest1.txt";
	private List<String> sections[];
	// mark to find the beginning of a section
	private String startLine="$1";
	// mark to find the end of a section
	private String endLine="$2";
	
	// to walk the file and fill arrayLists from array !sections! 
	private int counter1=0;
	
	// to set a list to write a new content in it
	private int numberOfList=-1;	
	

	// this initialization block is after all variables
	{
		// read number of sections from the properties file
		int k = Integer.parseInt(ContentHandler.getEngCoachProperty("sections"));
		// one was added because each sections has the start and the end

		// and create the number of marks
		boundaries = new String[k+1];
		for (int i = 0; i < k; i++) {
			boundaries[i] = "$" + (i + 1);
			System.out.println(boundaries[i] + "--");
		}

		sections = new ArrayList[boundaries.length - 1];
		for (int i = 0; i < sections.length; i++) {
			sections[i] = new ArrayList<>();
		}

	}
	
	private void readFile() {
		try {
			BufferedReader b = new BufferedReader(new FileReader(filePath));
			
			String line="";
			int MetkaBeginningEnd=1;
			
			while((line=b.readLine())!=null)
			{
				
			
				if(line.startsWith(endLine)) // конец поля считывания
				{
					MetkaBeginningEnd=2;
					
					// to stop reading the file
					if(counter1==5 && endLine=="$7") {
						System.out.println("break");
						break;
					}
					System.out.println(endLine +" -- "+counter1);
	
					nextSection();		
				}
			
				if(MetkaBeginningEnd==1 && line.isEmpty()==false)  // запись строки в коллекцию
				{
					// to avoid duplication
					if(line.equals("$1")) line="";
					
					sections[counter1].add(line);
					//System.out.println(line);
				}
				
				//начало поля считывания; 
				//стоит после endLine чтобы не записывать поисковой символ
				if(line.startsWith(startLine)) 
				{
					MetkaBeginningEnd=1;
				}
			}
			b.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File in not found");
		} catch (IOException e) {
			System.out.println("A problem during reading the file");
		}
	}
	
	
	// to move beetwen sections (for example: first section is from $1 to $2, 
	// the second one is from $2 to $3)
	private void nextSection() {
		++counter1;

		startLine=boundaries[counter1];
		endLine=boundaries[counter1+1];
	}
	
	
	
	public void show(int list){
		for (String string : sections[list]) {
			System.out.println(string);
		}
	}
	
	public static void main(String[] args) {
//		JFrame f=new JFrame("hello");
//		
//		// initialize gui for fast train mode
//		FastTrainGUI gui=new FastTrainGUI(f);
//		
//		// create fast train model and add field iw which words will be
//		// displayed and add label to display number of words to show
//		FastTrain train=new FastTrain(gui.getDestinationField(), 
//									  gui.getLabelForShowingCurrentInfo());
//		gui.setFastTrain(train);
		
		poligon p=new poligon();
		p.readFile();
		
		p.show(2);
		
	}
}




