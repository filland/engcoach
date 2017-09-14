package com.classes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class FastTrainGUI extends JFrame implements ActionListener{

	/*
	 * 
	 * add radiobuttons to point what language you
	 * are going to use. 
	 * And add combobox to point out a section 
	 * 
	 * */
	
	
	
	// variable for a EngCoach JFrame
	private JFrame engCoachFrame;
	private FastTrain fastTrain;
	
	
	
	
	private JLabel lab1 = new JLabel("Enter a desirable number of words:");
	private JLabel lab2 = new JLabel("123"); // this one is for showing number of words
	private JTextField randomOutput = new JTextField(25);
	private JTextField setNumberOfWordsField = new JTextField("50", 5);
	private JButton startBut = new JButton("Start");
	private JButton stopBut = new JButton("Stop");
	private JButton pauseBut = new JButton("Pause"); // is used as "Resume" too
//	private JPanel pan = new JPanel();
	private JPanel pan = new JPanel(new BorderLayout());

	
	private JPanel panForFieldsAndLabels = new JPanel();
	private JPanel panForButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	
	
	private JPanel panForSectionAndLang = new JPanel();
	private JPanel panForRadioButtons = new JPanel();
	private JPanel panForCheckBox = new JPanel();
	private JPanel panForIntervalCheckBox=new JPanel();
	
	
	ButtonGroup group = new ButtonGroup();
	JRadioButton engIsOriginalRadBut=new JRadioButton("Eng");
	JRadioButton rusIsOriginalRadBut=new JRadioButton("Rus");
	
	private String sections []=EngCoachGUI.menuOptions;
	private JComboBox<String> combo=new JComboBox<>(sections);
	
	
	
	private String intervals[]={"1","1.5", "2", "2.5", "3",
								"3.5", "4", "4.5", "5", "6", "8",
								"10", "15", "20", "30", "40"};
	
	private JComboBox<String> intervalsCombo=new JComboBox<>(intervals);
	
	
	
	
	
	
	
	
	
	private boolean pausePressed=false;
	
	
	private Font font = new Font("Verdana", 0, 14);
	{
		//sections=new ArrayList[number of sections];
		UIManager.put("Label.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("Button.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("MenuBar.font", font);
		
	}
	
	
	
	
	public FastTrainGUI(JFrame engCoachFrame){
		this.engCoachFrame=engCoachFrame;
		
	}
	
	public void setFastTrain(FastTrain fastTrain){
		this.fastTrain=fastTrain;
	}
	
	{	
		setTitle("Hurry up to translate");
		setDefaultCloseOperation(1);
		setSize(600,190);
		setLocationRelativeTo(null);
		//setResizable(false);
		
		//pauseBut.setEnabled(false);
		randomOutput.setHorizontalAlignment(JTextField.CENTER);
		setNumberOfWordsField.setHorizontalAlignment(JTextField.CENTER);
		
		
		//randomOutput.setFont(customFont);
		randomOutput.setEditable(false);

//		pan.setLayout(null);
		
		add(pan);
		
		pan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


//		lab1.setBounds(75, 10, 250, 30);
//		lab2.setBounds(75, 10, 250, 30);
//		randomOutput.setBounds(30, 40, 340, 30);
//		setNumberOfWordsField.setBounds(150, 40, 80, 30);
//		startBut.setBounds(150, 80, 80, 30);
//		stopBut.setBounds(100, 80, 80, 30);
//		pauseBut.setBounds(200, 80, 100, 30);

		
		
//		group.add(engIsOriginalRadBut);
//		panForSectionAndLang.add(engIsOriginalRadBut);
//		group.add(rusIsOriginalRadBut);
//		panForSectionAndLang.add(rusIsOriginalRadBut);
//		
//		
//		
//		pan.add(lab1);
//		pan.add(setNumberOfWordsField);
//		pan.add(startBut);
//		
//		pan.add(lab2);
//		pan.add(randomOutput);
//		pan.add(pauseBut);
//		pan.add(stopBut);
		

		panForRadioButtons.setBorder(BorderFactory.createTitledBorder("Pick a language:"));
		
		panForSectionAndLang.add(panForRadioButtons);
		panForSectionAndLang.add(panForCheckBox);
		panForSectionAndLang.add(panForIntervalCheckBox);
		
		
		engIsOriginalRadBut.setSelected(true);
		group.add(engIsOriginalRadBut);
//		engIsOriginalRadBut.setPreferredSize(new Dimension(50, 25));
		panForRadioButtons.add(engIsOriginalRadBut);
		group.add(rusIsOriginalRadBut);
//		rusIsOriginalRadBut.setPreferredSize(new Dimension(50, 25));
		panForRadioButtons.add(rusIsOriginalRadBut);
		
		combo.setBorder(BorderFactory.createTitledBorder("Pick a section:"));
		panForCheckBox.add(combo);
		
		
		intervalsCombo.setPreferredSize(new Dimension(130, 60));
		intervalsCombo.setBorder(BorderFactory.createTitledBorder("Set an interval, sec:"));
		panForIntervalCheckBox.add(intervalsCombo);
		
		
		panForFieldsAndLabels.setLayout(new BoxLayout(panForFieldsAndLabels, BoxLayout.PAGE_AXIS));
		panForFieldsAndLabels.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		lab1.setHorizontalAlignment(JTextField.CENTER);
		panForFieldsAndLabels.add(lab1);
		panForFieldsAndLabels.add(setNumberOfWordsField);
		panForButtons.add(startBut);
		
		panForFieldsAndLabels.add(lab2);
		
		panForFieldsAndLabels.add(randomOutput);
		panForButtons.add(pauseBut);
		panForButtons.add(stopBut);

		
		pan.add(panForSectionAndLang, BorderLayout.NORTH);
		pan.add(panForFieldsAndLabels, BorderLayout.CENTER);
		pan.add(panForButtons, BorderLayout.SOUTH);
		
		
		pauseBut.setVisible(false);
		stopBut.setVisible(false);
		randomOutput.setVisible(false);
		lab2.setVisible(false);
		pack();
		setVisible(true);
		
		
		startBut.addActionListener(this);
		stopBut.addActionListener(this);
		pauseBut.addActionListener(this);
		
		engIsOriginalRadBut.addActionListener(this);
		rusIsOriginalRadBut.addActionListener(this);
		combo.addActionListener(this);
		intervalsCombo.addActionListener(this);
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==startBut){
						
			// спрячем панель с настройкой языка и секции
			panForSectionAndLang.setVisible(false);
			
			// спрячем поле, в которое вводили кол-во слов для показа
			setNumberOfWordsField.setVisible(false);		
			// спрячем этот лейбл
			lab1.setVisible(false);							
			// тут пишем сколько слов будет показано/ сколько уже показано
			lab2.setVisible(true);							
			startBut.setVisible(false);							// спрячем кнопку "Start"
			pauseBut.setVisible(true);							// покажем кнопку "Pause"
			stopBut.setVisible(true);							// покажем кнопку "Stop"
			
			
			//setNumberOfWordsField.setText(" ");
			
			// покажем поле, в которое будут выводиться слова
			randomOutput.setVisible(true);					
							
			engCoachFrame.setVisible(false);		// спрячем главный фрейм
			pack();	
			
			
			// get number of words and interval in seconds and START train		
			fastTrain.startTrain(Integer.parseInt(setNumberOfWordsField.getText()), 
								Float.parseFloat((String) intervalsCombo.getSelectedItem()));
			
			
			// т.к. процесс закончен спрячем окно для вывода случайных слов		
			//engCoachFrame.setVisible(true);		
			// покажем главный фрейм
			//setVisible(true);				
		}
		
		
		
		
		
		if (e.getSource()==stopBut) {
			fastTrain.stopTrain();		// прерываем поток
			// set the number of unshowed words
			setNumberOfWordsField.setText(""+fastTrain.getRemainingNumberOfWords());
			
			// покажем панель с настройкой языка и секции
			panForSectionAndLang.setVisible(true);
			
			startBut.setVisible(true);							// покажем кнопку "Start"
			stopBut.setVisible(false);							// спрячем кнопку "Stop"
			pauseBut.setVisible(false);
			lab1.setVisible(true);							// покажем этот лейбл
			lab2.setVisible(false);							// спрячем
			setNumberOfWordsField.setVisible(true);
			randomOutput.setVisible(false);							
			engCoachFrame.setVisible(true);
			setVisible(true);
			pack();
		}
		
		
		
		
		if(e.getSource()==pauseBut){
			if(pausePressed==false){
				fastTrain.pauseTrain();
				pauseBut.setText("Resume");
				pausePressed=true;
			}else{
				fastTrain.resumeTrain();
				pauseBut.setText("Pause");
				pausePressed=false;
			}
		}
		
		
		
		
		//											### Radio Buttons
		
		if(e.getSource()==engIsOriginalRadBut){
			fastTrain.setOriginalLang(false);
		}
		
		if(e.getSource()==rusIsOriginalRadBut){
			fastTrain.setOriginalLang(true);
		}
		
		
		
		//										 ### ComboBox
		
		if (combo.getSelectedItem() == sections[0]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[0], 
									ContentHandler.boundaries[1]);
		}
		if (combo.getSelectedItem() == sections[1]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[1], 
									ContentHandler.boundaries[2]);
		}
		if (combo.getSelectedItem() == sections[2]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[2], 
									ContentHandler.boundaries[3]);
		}
		if (combo.getSelectedItem() == sections[3]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[3], 
									ContentHandler.boundaries[4]);
		}
		if (combo.getSelectedItem() == sections[4]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[4], 
									ContentHandler.boundaries[5]);
		}
		if (combo.getSelectedItem() == sections[5]) {
			fastTrain.setBoundaries(ContentHandler.boundaries[0], 
									ContentHandler.boundaries[5]);
		}

		
	}	
	

	
	public JTextField getDestinationField() {
		return randomOutput;
	}
	
	public JLabel getLabelForShowingCurrentInfo() {
		return lab2;
	}
}


