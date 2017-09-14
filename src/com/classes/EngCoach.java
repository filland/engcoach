package com.classes;


import javax.swing.*;


public class EngCoach extends ContentHandler {
	
	private JTextField firstField;
	private JTextField secondField;
	
//	private Font customFont = new Font( "Serif", Font.PLAIN, 20 ) ;
//	private Font newFont = new Font("Verdana", 0, 14);
	
	// path to the source file
//	String filePath=ContentHandler.getEngCoachProperty("path");
	// path to the icon
//	String iconPath="other/capital-letter.png";
	
	
	
	
	// системные переменные
	
	// here we store sections which are written from the file
//	private List<String> sections[];
	
//	ArrayList<String> arrayColl=new ArrayList<String>();  // коллекция для хранения строк
//	String strRandomLine="";
	
	// here we store original[0] and translation[1]
//	String splitedLine[]=new String[2];
	
	
//	String startLine="$1";           // начало считывания
//	String endLine="$2";             // конец считывания строк из текстового файла
//	int fromEngToRus=0;   		     // флаг показывает, что переводим с анг на рус
	int counter=0;                   // счетчик для показа оригинала и перевода в два клика
//	int counter1=0;					// счетчик для проверки повторения строки в массиве строк
	
	// для записи слов из файла в коллекции. 
	// Означает индекс элемента массива sections[]
//	int counter2=0;					 
	
	// тут храним N-ть последних выведенныхх слов
//	String [] arrayCheck;					
	
	// number of unique words from a section
//	int exceptionNumber=0;
	
//	boolean flag1=true;
	
	// для записи числа, которое является размером массива исключений
//	boolean flag2=true;										
	
//	int lineNumber;            // количество выбранных из файла строк
//	int randomNumber;          // случайный номер строки (из выбранных строк -- lineNumber)
//	int metkaBeginningEnd;     // флаг для начала и завершения считывания из текст файла
	
//	static String [] boundaries;		// метки-границы для выбора слов из файла
	
	
	// to set a list to write a new content in it
//	private int currentSection=0;		

	
	// 					### setWordInField
	String splitedLineSetWordInField[]=new String[2];
	
	
//	{
//		// read number of sections from the properties file
//		int k=Integer.parseInt(ContentHandler.getEngCoachProperty("sections")) + 1;
//		// one was added because each sections has the start and the end
//		
//		// and create the exact number of marks
//		boundaries= new String[k];
//		for (int i = 0; i < k; i++) {
//			boundaries[i]="$"+(i+1);
//		}
//		
//		
//		// create a number of collections equals the number of marks minus one is
//		sections=new ArrayList[boundaries.length-1];
//		for (int i = 0; i < sections.length; i++) {
//			sections[i]=new ArrayList<>();
//		}
//		
//		
//		// data from the file is read once the program is started
////		readFile();				
//	}
	
	public EngCoach() {
		
	}
	
	
	public EngCoach(JTextField original, JTextField translation) {
		firstField=original;
		secondField=translation;
		
	}
	
//	// set a pair to be showed
//	public void setPair(String [] pair){
//		splitedLineSetWordInField=pair;
//	}
//	
//	
//	// set boundaries for readSection method
//	public void setBoundaries(String startLine, String endLine){
//		
//		this.startLine=startLine;
//		this.endLine=endLine;
//		
//		// to create a new array of unique words
//		flag2=true;
//		
//		// to zero number of elements stored into
//		// array with unique words
//		counter1=0;
//	}
//	
//	
//	public void readSection() {
//		
//		// clear this collection
//		arrayColl.clear();
//		
//		try {
//			BufferedReader file = new BufferedReader(new FileReader(filePath));
//
//			String line = "";
//			while ((line = file.readLine()) != null) {
//				if (line.startsWith(endLine)) // конец поля считывания
//				{
//					metkaBeginningEnd = 2;
//				}
//
//				if (metkaBeginningEnd == 1) // запись строки в коллекцию
//				{
//					if (line.isEmpty() == false) {
//						arrayColl.add(line);
//					}
//				}
//
//				// начало поля считывания;
//				// стоит после endLine чтобы не записывать поисковой символ
//				if (line.startsWith(startLine)) {
//					metkaBeginningEnd = 1;
//				}
//			}
//			file.close();
//		} catch (IOException e) {
//			firstField.setText("Ошибка");
//		}
//	}
//
//	
//	
//	// получим случайную строку из коллекции
//	public String getRandomWord() { 
//		// получаем число строк
//		lineNumber = arrayColl.size(); 
//																		
//		do {
//			// получаем случайное число  в  этом диапазоне
//			randomNumber = (int) Math.floor(Math.random() * lineNumber); 
//
//		} while (arrayColl.get(randomNumber).length() < 5);
//
//		// возвращаем элемент с полученным номером
//		return arrayColl.get(randomNumber);
//	}
//
//	
//	public void makeWordUnique() {
//
//		// есть ли в массиве такая строка
//		if (Arrays.asList(arrayCheck).contains(strRandomLine)) {
//			// до тех пор, пока строка не будет отличаться от строк в массиве
//			while (Arrays.asList(arrayCheck).contains(strRandomLine)) {
//				strRandomLine = getRandomWord();
//				System.out.println(strRandomLine);
//			}
//		}
//		
//		// добавляем слово в массив для уникальных строк
//		arrayCheck[counter1] = strRandomLine;
//	}
//	
//	
//	
//	// получим слова для вывода на экран
//	public String[] getWord() 
//	{
//		String splitedLineSys[] = new String[2];
//
//		// получим случайную строку из коллекции
//		strRandomLine = getRandomWord();
//
//		if (flag2 == true) {
//			// получим ~60% от числа строк в разделе
//			exceptionNumber = Math.round(((lineNumber * 6) / 10) - 10);
//			System.out.println(exceptionNumber+" : Size of the check array");
//
//			// создадим массив с таким числом элементов
//			arrayCheck = new String[exceptionNumber];
//			flag2 = false;
//		}
//
//		// check if this line is unique
//		// if it's already been used then get another line
//		makeWordUnique();
//
//		
//		counter1++;
//		if (counter1 == exceptionNumber)
//			counter1 = 0;
//
//		// weather by "-" or by "—"
//		splitedLineSys = strRandomLine.split("-|—", 2);
//		// удаляем пробелы в начале в конце
//		splitedLineSys[0] = splitedLineSys[0].trim();
//		splitedLineSys[1] = splitedLineSys[1].trim();
//		
//		
//		// to create translation from Rus to Eng
//		if (fromEngToRus==1) {
//			String per=splitedLineSys[0];
//			splitedLineSys[0]=splitedLineSys[1];
//			splitedLineSys[1]=per;
//		}
//		
//		System.out.println(counter1 + "  " + splitedLineSys[0]+" - "+splitedLineSys[1]);
//		
//		/*
//		 * now splitedLineSys[0] contains original, splitedLineSys[1] contains
//		 * translation
//		 */
//		return splitedLineSys;
//	}
	
	
	// set a pair to be showed
	public void setPair(String [] pair){
		splitedLineSetWordInField=pair;
	}
		
	
	
	// fist click -- a word is put in the firstField
	// second click -- its translation is put in the secondField
	public void setWordInField() {
		
		secondField.setText("");		
		
		if (counter == 0) {
			counter++;
			
			readSection();
			setPair(getWord());
			
			firstField.setText(splitedLineSetWordInField[0]);

//			splitedLine[0] = "";
//			sections[currentSection].clear();

		} else 	 // выводим текст во вторую строку
		{
			secondField.setText(splitedLineSetWordInField[1]);
			
//			splitedLine[1] = "";
			counter=0;
		}

	}
	
	
	
	
	
	
}
