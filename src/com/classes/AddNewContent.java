package com.classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

/*
 нужен метод ля чтения файла с данными 
 и записи разделов по коллекциям - done
 
// change that wierd algorism of parsing a line
// to a much more simple one via split
 
 
 */

public class AddNewContent {

	private JFrame frame;	
	
	private JComboBox<String> combo;
	private JTextField o1s[];
	private JTextField t1s[];

	// path to a the file with content
	private String filePath = ContentHandler.getEngCoachProperty("path");
	private ArrayList<String> lists[];
	// mark to find the beginning of a section
	private String startLine="$1";
	// mark to find the end of a section
	private String endLine="$2";
	
	
	// names of sections
	String sections[] = {"--//--", 
						"Words", 
						"Introd & others",
						"Other sentences", 
						"Phrasal verbs", 
						"My sentences",
					};
	
	// mark-boundaries which are for getting content from the file
	String boundaries [] = ContentHandler.boundaries;
	
	// to walk the file and fill arrayLists from array !lists! 
	private int counter1=0;
	
	// to set a list to write a new content in it
	private int numberOfList=-1;
	
		

	public AddNewContent() {
		 createGUI();
		 readFile();
	 }

	private void createGUI() {
		frame = new JFrame();
		frame.setTitle("Add new content");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(1);

		JPanel pan = new JPanel(new BorderLayout(10, 10));
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel panComboBox = new JPanel();
		JPanel panFields = new JPanel(new GridLayout(6, 2, 5, 5));
		JPanel panButtons = new JPanel(new FlowLayout());


		lists = new ArrayList[sections.length];
		for (int i = 0; i < lists.length; i++) {
			lists[i] = new ArrayList<String>();
		}

		combo = new JComboBox<>(sections);
		panComboBox.add(new JLabel("Choose a section to load: "));
		panComboBox.add(combo);
		JLabel lab = new JLabel("Content will be loaded into: " + sections[0]);
		panComboBox.add(lab);
		
		o1s = new JTextField[5];
		t1s = new JTextField[5];

		for (int i = 0; i < o1s.length; i++) {
			if (i == 0){
				panFields.add(new JLabel("Original: "));
				panFields.add(new JLabel("Translation: "));
			}
			
			o1s[i] = new JTextField(30);
			panFields.add(o1s[i]);
			t1s[i] = new JTextField(30);
			panFields.add(t1s[i]);
		}

		JButton add = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		panButtons.add(add);
		panButtons.add(cancel);

		pan.add(panComboBox, BorderLayout.NORTH);
		pan.add(panFields, BorderLayout.CENTER);
		pan.add(panButtons, BorderLayout.SOUTH);

		frame.add(pan);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent itemEvent) {

				t1s[2].setText(filePath);
				
				if ((itemEvent.getItemSelectable() == combo)
						&& (itemEvent.getStateChange() == ItemEvent.SELECTED)) {

					String subject = (String) itemEvent.getItem();

					// тут будет добавление в коллекцию
					switch (subject) {
						case "Words" :
							setTargetSection(0);
							
							break;
						case "Introd & others" :
							setTargetSection(1);
							
							break;
						case "Other sentences" :
							setTargetSection(2);
							
							break;
						case "Phrasal verbs" :
							setTargetSection(3);
							
							break;
						case "My sentences" :
							setTargetSection(4);
							
							break;
					}

					lab.setText("Content will be loaded into: " + subject);
				}
			}
		});
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// add content from text fields
				addContentInSection();
				// write that content in the file
				writeFile();
				
				JOptionPane.showMessageDialog(null, "Данные успешно дабавлены в файл", "Инфо", 1);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
	}

	private void readFile() {
		try {
			BufferedReader b = new BufferedReader(new FileReader(filePath));
			
			String line="";
			int MetkaBeginningEnd=1;
			
			while((line=b.readLine())!=null)
			{
				
			
				if(counter1==4) o1s[2].setText(line);
				
				if(line.startsWith(endLine)) // конец поля считывания
				{
					MetkaBeginningEnd=2;
					
					// to stop reading the file
					if(counter1==5 && endLine.equals("$7")) {
						System.out.println("break");
						break;
					}
					System.out.println(counter1 +"   "+ endLine);
	
					nextSection();		
				}
			
				if(MetkaBeginningEnd==1 && line.isEmpty()==false)  // запись строки в коллекцию
				{
					// to avoid duplication
					if(line.equals("$1")) line="";
					
					lists[counter1].add(line);
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
	
	// read source file and add each section into a separate list
	private boolean writeFile(){
		boolean success=false;

		
		try {
			BufferedWriter b=new BufferedWriter(new FileWriter("D://Dropbox/java/EngMaterialsForTest1.txt"));
			
			// walk through the array and add data from each list
			for (int i = 0; i < lists.length; i++) {
				b.append(boundaries[i]);
				
				b.append("\n\n\n");
				
				for (String line : lists[i]) {
					b.append(line+"\n");
				}
				
				b.append("\n\n\n");
				
				// add the last mark "$7"
				if(i==(lists.length-1)) b.append(boundaries[i+1]);
			}
			b.close();
			
			success=true;
			
		} catch (IOException e) {
			System.out.println("Some problems during writing in the file");
		}
		
		return success;
	}
	
	
	// to move between sections (for example: first section is from $1 to $2, 
	// the second one is from $2 to $3)
	private void nextSection() {
		++counter1;

		startLine=boundaries[counter1];
		endLine=boundaries[counter1+1];
	}
	
	// to set a list to write a new content in it
	private void setTargetSection(int numberOfList){
		if (lists.length < numberOfList) {
			JOptionPane.showMessageDialog(null, 
					"There is no such section", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			this.numberOfList=numberOfList;
		}
	}
	
	
	/*
	 * This method gets data from fields and add them into 
	 * a certain list which had been selected before
	 * */
	private void addContentInSection(){
		String line="";
		
		for (int i = 0; i < o1s.length; i++) {
			if(o1s[i].getText().isEmpty() || t1s[i].getText().isEmpty()) continue;
			line=o1s[i].getText() + " - " + t1s[i].getText();
			
			lists[numberOfList].add(line);
		}
	}
	
	
	/*
	 * This one used just to
	 * make sure that all is right
	 * */
	public void show(int list){
		for (String string : lists[list]) {
			System.out.println(string);
		}
	}
}
