package com.classes;

import java.io.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;



/*

Выполняется: тысячи слов в arrayCheck в режиме Fast train
возможность создания нового списка слов из программы


> возможность созданя нового списка слов из программы
добавить еще несколько пунктов в меню и скрыть их
добавление будет лишь задавать имя кнопке и делать его видимым...
а изменения нужно как-то сохранить и при следующем включении программы нужно считать
чтобы тот пункт был добавлен в список
Для хранения информации о новых пунктах можно использовать Properties


                                ##### Рефакторинг ######
              
        
> Добавить возможность установки скорости показа слов в режиме Fast train
> Добавить возможность выбора секции 
> Установка ширины в зависимости от выбранного пунка (для предложений намного шире)



Выполнено:

> добавить инфу об общем кол-ве слов в режиме Fast train
> при нажатии кнопки Стоп в поле для установки кол-ва слов помещать кол-во не показанных
слов
> выбор языка оригинала


> разбить метод getWord() на методы:
1-й выбирает слова
2-й устанавливает значения в поля
> класс для функциональности "Fast mode"
> парсить оригинал и перевод можно намного проще -- с помощью split   
> автоматический показ слов без перевода - выполнено
> добавление новых слов и предложений из программы - выполнено
> класс для функциональности "Add new content" - выполнено

*/

public class EngCoachGUI extends JFrame implements ActionListener, ItemListener, KeyListener
{
    static String menuOptions []={"Words",
						    		"Introd & others",
						    		"Other sentences",
									"Phrasal verbs",
									"My sentences",
									"All together"};
	
	
	JPanel pan=new JPanel();
	JTextField firstField=new JTextField(55);
	JTextField secondField=new JTextField(55);
	JButton nextBut=new JButton("Дальше");
	
	JMenuBar menuBar=new JMenuBar();
	
	
	JMenu menuFirst=new JMenu("Settings");
	
	ButtonGroup group = new ButtonGroup();
	JRadioButtonMenuItem radBut1 = new JRadioButtonMenuItem("Eng-Rus");
	JRadioButtonMenuItem radBut2 = new JRadioButtonMenuItem("Rus-Eng");
	JMenuItem menuItemFirstOne = new JMenuItem("Fast train");
	JMenuItem menuItemFirstAddingContent = new JMenuItem("Add new content");
	JMenuItem menuItemSetSourcePath = new JMenuItem("Change path to source file");
	
	JMenu menuSecond=new JMenu("Mode");
	ButtonGroup group2 = new ButtonGroup();
	JRadioButtonMenuItem radBut3 = new JRadioButtonMenuItem(menuOptions[0]);
	JRadioButtonMenuItem radBut4 = new JRadioButtonMenuItem(menuOptions[1]);
	JRadioButtonMenuItem radBut5 = new JRadioButtonMenuItem(menuOptions[2]);
	JRadioButtonMenuItem radBut6 = new JRadioButtonMenuItem(menuOptions[3]);
	JRadioButtonMenuItem radBut7 = new JRadioButtonMenuItem(menuOptions[4]);
	JRadioButtonMenuItem radBut8 = new JRadioButtonMenuItem(menuOptions[5]);
	
	JMenu menuThird=new JMenu("Info");
	JMenuItem menuItemAbout = new JMenuItem("About");
	JMenuItem menuItemControl = new JMenuItem("Control");
	
	Font customFont = new Font( "Serif", Font.PLAIN, 20 ) ;
	Font newFont = new Font("Verdana", 0, 14);
	// системные переменные
	
//	ArrayList<String> arrayColl=new ArrayList<String>();  // коллекция для хранения строк
//	String strRandomLine="";
//	String firstRandomLine="";
//	String secondRandomLine="";
//	// here we store original[0] and translation[1]
//	String splitedLine[]=new String[2];
//	
//	
	String fileName=ContentHandler.getEngCoachProperty("path");
	String iconPath=ContentHandler.getEngCoachProperty("icon");
//	
//	
//	String startLine="$1";           // начало считывания
//	String endLine="$2";             // конец считывания строк из текстового файла
//	int fromEngToRus=0;   		     // флаг показывает, что переводим с анг на рус
//	int counter=0;                   // счетчик для показа оригинала и перевода в два клика
//	int counter1=0;					// счетчик для проверки повторения строки в массиве строк
//	String [] arrayCheck;					// тут храним N-ть последних выведенныхх слов
//	int exceptionNumber=0;
//	//ArrayList<String> arrayCheck1=new ArrayList<String>(); 
//	boolean flag1=true;
//	boolean flag2=true;										// для записи числа, которое является размером массива исключений
//	
//	int lineNumber;            // количество выбранных из файла строк
//	int randomNumber;          // случайный номер строки (из выбранных строк)
//	int metkaBeginningEnd;     // флаг для начала и завершения считывания из текст файла
//	
//	static String [] boundaries ={"$1","$2","$3","$4","$5","$6","$7"};		// метки-границы для выбора слов из файла
//	
//	
//	String lineForFastMode="";
//	int countFastMode=0;
	
	EngCoach eng=new EngCoach(firstField, secondField);
	

	public EngCoachGUI()
	{
		super("EngCoach");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(pan);
		
		setFonts(newFont);
		
		firstField.setHorizontalAlignment(JTextField.CENTER);
		secondField.setHorizontalAlignment(JTextField.CENTER);
		
		// добавление иконки и загрузка файла иконки в файл .class
		//ClassLoader ldr = this.getClass().getClassLoader();
		
		
//		ClassLoader ldr = this.getClass().getClassLoader() ;
//		java.net.URL iconURL = ldr.getResource(iconPath) ;
//		
		ImageIcon duke = new ImageIcon(iconPath) ;
		setIconImage(duke.getImage());
		
		//ImageIcon per=new ImageIcon(iconPath);
//		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		
		radBut1.setSelected(true);
		radBut3.setSelected(true);
		
		firstField.setFont(customFont);
		secondField.setFont(customFont);
		
		pan.setFocusable(true);
		
		
		
		menuBar.add(menuFirst);                 // добавляем меню в бар
		group.add(radBut1);						// добавляем радкнопку в группу
		menuFirst.add(radBut1);					// добавлем радкнопку в меню
		group.add(radBut2);
		menuFirst.add(radBut2);
		menuFirst.addSeparator();				// рисуем разделительную черту
		menuFirst.add(menuItemFirstOne);		// добавляем в меню Item 
		menuFirst.add(menuItemFirstAddingContent);
		
		menuFirst.add(menuItemSetSourcePath);
		
		menuBar.add(menuSecond);
		group2.add(radBut8);
		menuSecond.add(radBut8);
		group2.add(radBut3);
		menuSecond.add(radBut3);
		group2.add(radBut4);
		menuSecond.add(radBut4);
		group2.add(radBut5);
		menuSecond.add(radBut5);
		group2.add(radBut6);
		menuSecond.add(radBut6);
		group2.add(radBut7);
		menuSecond.add(radBut7);
		
		menuBar.add(menuThird);
		menuThird.add(menuItemAbout);
		menuThird.add(menuItemControl);
	
		pan.add(menuBar);		// добавляем бар со всеми приблудами на панель
		pan.add(firstField);
		pan.add(secondField);
		pan.add(nextBut);
		
		setSize(790, 200);                                      // задает размер окна (если удалить строку, то элементы на Panel слипнуться)
		setJMenuBar(menuBar);									// это строка, по видимому, устанавливает панель для меню
		
		setLocationRelativeTo(null);                           // Frame в центр экрана
		setVisible(true);
		
		nextBut.addActionListener(this);
		radBut1.addItemListener(this);
		radBut2.addItemListener(this);
		radBut3.addItemListener(this);
		radBut4.addItemListener(this);
		radBut5.addItemListener(this);
		radBut6.addItemListener(this);
		radBut7.addItemListener(this);
		radBut8.addItemListener(this);
		
		menuItemFirstOne.addActionListener(this);
		menuItemFirstAddingContent.addActionListener(this);
		menuItemSetSourcePath.addActionListener(this);
		
		menuItemAbout.addActionListener(this);
		menuItemControl.addActionListener(this);
		
		
	}
	
	private void setFonts(Font f){
		
		UIManager.put("Label.font", f);
		UIManager.put("TextField.font", f);
		UIManager.put("Button.font", f);
		UIManager.put("ComboBox.font", f);
		UIManager.put("MenuBar.font", f);
	}
	
	

//		public String getRandomWord() { // получим случайную строку из коллекции
//			lineNumber = arrayColl.size(); // получаем число строк
//																			
//			do {
//				// получаем случайное число  в  этом диапазоне
//				randomNumber = (int) Math.floor(Math.random() * lineNumber); 
//
//			} while (arrayColl.get(randomNumber).length() < 5);
//
//			// возвращаем элемент с полученным номером
//			return arrayColl.get(randomNumber);
//		}

		
//		// fist click -- a word is put in the firstField
//		// second click -- its translation is put in the secondField
//		public void setWordInField() {
//			secondField.setText("");		
//			
//			if (counter == 0) {
//				counter++;
//
//				firstField.setText(splitedLine[0]);
//
//				lineForFastMode = splitedLine[0];
//				splitedLine[0] = "";
//				arrayColl.clear();
//
//			} else 	 // выводим текст во вторую строку
//			{
//				secondField.setText(splitedLine[1]);
//				splitedLine[1] = "";
//				counter=0;
//			}

//		}

//		public void readSection() {
//			
//			// clear this collection
//			arrayColl.clear();
//			
//			try {
//				BufferedReader file = new BufferedReader(new FileReader(fileName));
//
//				String line = "";
//				while ((line = file.readLine()) != null) {
//					if (line.startsWith(endLine)) // конец поля считывания
//					{
//						metkaBeginningEnd = 2;
//					}
//
//					if (metkaBeginningEnd == 1) // запись строки в коллекцию
//					{
//						if (line.isEmpty() == false) {
//							arrayColl.add(line);
//						}
//					}
//
//					// начало поля считывания;
//					// стоит после endLine чтобы не записывать поисковой символ
//					if (line.startsWith(startLine)) {
//						metkaBeginningEnd = 1;
//					}
//				}
//				file.close();
//			} catch (IOException e) {
//				firstField.setText("Ошибка");
//			}
//		}

		
//		public void makeWordUnique() {
//
//			// есть ли в массиве такая строка
//			if (Arrays.asList(arrayCheck).contains(strRandomLine)) {
//				// до тех пор, пока строка не будет отличаться от строк в массиве
//				while (Arrays.asList(arrayCheck).contains(strRandomLine)) {
//					strRandomLine = getRandomWord();
//					System.out.println(strRandomLine);
//				}
//			}
//			
//			// добавляем слово в массив для уникальных строк
//			arrayCheck[counter1] = strRandomLine;
//		}

//		public String[] getWord() // получим слова для вывода на экран
//		{
//			String splitedLineSys[] = new String[2];
//
//			// получим случайную строку из коллекции
//			strRandomLine = getRandomWord();
//
//			if (flag2 == true) {
//				// получим ~60% от числа строк в разделе
//				exceptionNumber = Math.round(((lineNumber * 6) / 10) - 10);
//				System.out.println(exceptionNumber+" : Size of the check array");
//
//				// создадим массив с таким числом элементов
//				arrayCheck = new String[exceptionNumber];
//				flag2 = false;
//			}
//	
//			// check if this line is unique
//			// if it's already been used then get another line
//			makeWordUnique();
//	
//			
//			counter1++;
//			if (counter1 == exceptionNumber)
//				counter1 = 0;
//
//			// weather by "-" or by "—"
//			splitedLineSys = strRandomLine.split("-|—", 2);
//			// удаляем пробелы в начале в конце
//			splitedLineSys[0] = splitedLineSys[0].trim();
//			splitedLineSys[1] = splitedLineSys[1].trim();
//			
//			
//			// to create translation from Rus to Eng
//			if (fromEngToRus==1) {
//				String per=splitedLineSys[0];
//				splitedLineSys[0]=splitedLineSys[1];
//				splitedLineSys[1]=per;
//			}
//			
//			System.out.println(counter1 + "  " + splitedLineSys[0]+" - "+splitedLineSys[1]);
//			
//			/*
//			 * now splitedLineSys[0] contains original, splitedLineSys[1] contains
//			 * translation
//			 */
//			return splitedLineSys;
//		}
	

	
	
	public void actionPerformed(ActionEvent event)	
	{
		
		if(event.getSource()==menuItemFirstOne)            // показ набора слов через короткий инт времени
		{
			// initialize gui for fast train mode
			FastTrainGUI gui=new FastTrainGUI(this);
			
			// create fast train model and add the field in which words will be
			// displayed and add the label to display a number of words to show
			FastTrain train=new FastTrain(gui.getDestinationField(), 
										  gui.getLabelForShowingCurrentInfo());
			gui.setFastTrain(train);
					
		}
		
		
		
		
		if(event.getSource()==nextBut)		// Кнопка "Дальше"	
		{
			
			eng.setWordInField();
		}	
		
		
		if(event.getSource()==menuItemAbout)
		{
			JOptionPane.showMessageDialog(this,
			"Программа предназначена для помощи\nизучающим иностранные языки\n\n"
			+ "Версия программы: 4.0.3\n"
			+ "Создатель программы — Курбатов Алексей",
			"About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		if(event.getSource()==menuItemControl)
		{
			JOptionPane.showMessageDialog(this,
			"• Язык оригинала\n"
			+ "В программе существует возможность установить язык оригинала при\n"
			+ "переводе в разделе \"Settings\": либо с Rus "+
			"на Eng, либо наоборот.\n• Можно выбрать раздел для тренировки в меню\"Mode\":\n"+ 
			"- All together — слова из всех разделов\n"+
			"- Words — в этом разделе только слова (почти)\n"+
			"- Introd & others — тут всякие полезные штуки для разговора\n"+
			"- Other sentences — в этом разделе только предложения, скаченные из интернета\n"+
			"- Phrasal verbs — тут только фразовые глаголы\n"+
			"- My sentences — тут собраны предложения, которые автор данной программы\n"
			+ "посчитал очень полезными и жизненными\n"
			+ "• Fast train\n"
			+ "Это режим для тренировки быстрого перевода слов и преложений. Возможно\n"
			+ "установить интервал между показом слов (предложений).\n"+
			"• Добавление новых слов и предложений\n"
			+ "В файл, где хранятся слова и предложения, можно добавить новые данные.\n"
			+ "Для этого нужно открыть меню Settings, затем нажать на Add new content.\n"
			+ "Далее следовать здравому смыслу\n"
			+ "• Исходный файл\n"
			+ "Чтобы изменить путь к исходному файлу нужно открыть меню Settings, затем\n"
			+ "нажать на Change path to source file и выбрать нужный файл.\n"
			+ "",
			"Control", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
		if(event.getSource()==menuItemFirstAddingContent)		// Кнопка "Добавить новый контент"	
		{
			AddNewContent newCont=new AddNewContent();
		}
		
		if (event.getSource()==menuItemSetSourcePath) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);
			
			if(returnVal==0){
				File f=fc.getSelectedFile();
				ContentHandler.setPathToSourceFile(f.getPath());
			}
			
			
		}
		
	}

	
	
	public void itemStateChanged(ItemEvent event)
	{
		if(event.getItemSelectable()==radBut1)
		{
			// translate from Eng to Rus
			eng.setOriginalLang(false);
//			fromEngToRus=0;
		}
		
		// in the first line will be set Rus text
		if(event.getItemSelectable()==radBut2)
		{
			//translate from Rus to Eng
			eng.setOriginalLang(true);
//			fromEngToRus=1;
		}
		
		if(event.getItemSelectable()==radBut8)  // все вместе
		{
			
			eng.setBoundaries("$1", "$6");
//			startLine="$1";
//			endLine="$6";
//			flag2=true;
//			counter1=0;
		}
		
		if(event.getItemSelectable()==radBut3)  // слова
		{
			eng.setBoundaries("$1", "$2");
//			startLine="$1";
//			endLine="$2";
//			flag2=true;
//			counter1=0;
		}
		
		if(event.getItemSelectable()==radBut4)		// introd & others
		{
			eng.setBoundaries("$2", "$3");
			
//			startLine="$2";
//			endLine="$3";
//			flag2=true;
//			counter1=0;

		}
		
		if(event.getItemSelectable()==radBut5)		// other sentences
		{
			eng.setBoundaries("$3", "$4");
			
//			startLine="$3";
//			endLine="$4";
//			flag2=true;
//			counter1=0;
			
		}
		
		if(event.getItemSelectable()==radBut6)		// фразовые глаголы
		{
			eng.setBoundaries("$4", "$5");
			
//			startLine="$4";
//			endLine="$5";
//			flag2=true;
//			counter1=0;
		}
		
		if(event.getItemSelectable()==radBut7)		// из книги
		{
			eng.setBoundaries("$5", "$6");
			
//			startLine="$5";
//			endLine="$6";
//			flag2=true;
//			counter1=0;
		}
	}
	
	public void keyPressed(KeyEvent e3){}
	public void keyTyped(KeyEvent e3){}
	public void keyReleased(KeyEvent e3)			// создание доп клавиш для переключения
	{
//		System.out.println(e3.getKeyCode());
//		
//		if(e3.getKeyCode()==32||e3.getKeyCode()==10)
//		{
//			getWord();
//		}
	}
	
	
	public static void main(String [] args)
	{
		EngCoachGUI gui=new EngCoachGUI();
	}
}



