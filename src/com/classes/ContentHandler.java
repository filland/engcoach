package com.classes;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIManager;

// this class is frozen yet because of
// unbearable amount of a bad code in EngCoach class
// I will continue create this common class
// when EngCoach class will be splited into two (visualization and model classes)


// Agenda: 

/*			         	Summary
 * 
 * I intend to create the following methods:
 * 
 * > readFile 
 * > writeFile ??? this is not a joint method
 * > getRandomWord (if you dont care weather it unique or not) ??? probable this one can be made private
 * > getWord ( if you need unique words)
 * > makeWordUnique ???
 * 
 * 
 * 
 * 
 * > setPathToSourceFile (and ones related to it)
 * 
 * */


public class ContentHandler {
	
	//    				 ### Properties ###
	
	private static Properties prop=new Properties();
	private static File file=new File("properties");
	// in this map is stored keys and values from properties file
	private static HashMap<String, String> propertiesMap=new HashMap<>();
	
	
	// this block is used to create a jar file
	boolean isCreatedJarFile=Boolean.valueOf(ContentHandler.getEngCoachProperty("jar"));
	
	File jarPath=new File(ContentHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String propertiesPath=getAbsolutePath();
	
	
	
    private String getAbsolutePath(){
    	File jarPath=new File(ContentHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	return jarPath.getParentFile().getAbsolutePath();
    }
    
	
	// the following variables are to be deleted ??????
	
	ArrayList<String> arrayColl=new ArrayList<String>();  // коллекция для хранения строк
	String strRandomLine="";
//	String firstRandomLine="";
//	String secondRandomLine="";
	// here we store original[0] and translation[1]
	String splitedLine[]=new String[2];
	
	
	String filePath=ContentHandler.getEngCoachProperty("path");
	
	
	String startLine="$1";           // начало считывания
	String endLine="$2";             // конец считывания строк из текстового файла
	boolean fromEngToRus=false;   		     //  если равен FALSE, то анг на рус, если TRUE -- наоборот
//	int counter=0;                   // счетчик для показа оригинала и перевода в два клика
	int counter1=0;					// счетчик для проверки повторения строки в массиве строк
	String [] arrayCheck;					// тут храним N-ть последних выведенныхх слов
	int exceptionNumber=0;
	//ArrayList<String> arrayCheck1=new ArrayList<String>(); 
	boolean flag1=true;
	boolean flag2=true;										// для записи числа, которое является размером массива исключений
	
	int lineNumber;            // количество выбранных из файла строк
	int randomNumber;          // случайный номер строки (из выбранных строк)
	int metkaBeginningEnd;     // флаг для начала и завершения считывания из текст файла
	
//	static String [] boundaries ={"$1","$2","$3","$4","$5","$6","$7"};		// метки-границы для выбора слов из файла
	static String [] boundaries;											// метки-границы для выбора слов из файла
	
	String lineForFastMode="";
	int countFastMode=0;
	
	
	
	
	
	
	// initialization block
	{
		// read number of sections from the properties file
		int k=Integer.parseInt(ContentHandler.getEngCoachProperty("sections")) + 1;
		// one was added because each sections has the start and the end
		
		// and create the exact number of marks
		boundaries= new String[k];
		for (int i = 0; i < k; i++) {
			boundaries[i]="$"+(i+1);
		}
		
		for (int i = 0; i < boundaries.length; i++) {
			System.out.println(boundaries[i]);
		}
//			
//			sections=new ArrayList[boundaries.length-1];
//			for (int i = 0; i < sections.length; i++) {
//				sections[i]=new ArrayList<>();
//			}
		
		

//		// the following part of initialization blog is for the executable jar file
		
		if (isCreatedJarFile) {
			String sysString = propertiesMap.get("path");
			sysString = propertiesPath + "/" + sysString;
			propertiesMap.put("path", sysString);
			System.out.println(" propertiesPath-" + sysString);
		}	
			
		System.out.println("Absolute path to main method   "+propertiesPath);
		System.out.println(" propertiesPath-" + propertiesMap.get("path"));
		System.out.println("Создается jar file  "+isCreatedJarFile);
	}
	
	
	
	
	
	private static void readEngCoachProperties(){
		try {
			prop.load(new FileReader(file));
			
			for (String key : prop.stringPropertyNames()) {
				propertiesMap.put(key, prop.getProperty(key));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// this method is static because we have one properties file
	// for the whole application
	public static String getEngCoachProperty(String key) {
		readEngCoachProperties();
		
		return propertiesMap.get(key);
	}
	
	public static void setPathToSourceFile(String newPath){
		
		// replace all left slaches (\) to right one (/)
		newPath=newPath.replace("\\", "/");
		System.out.println(newPath);

		try {
			// load properties file
			prop.load(new FileReader(file));
			// set a new value to the key "path"
			prop.setProperty("path", newPath);
			// save changes
			prop.store(new FileWriter(file), "The key PATH was changed");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/*
	 * if pass TRUE then original is Rus
	 * if pass FALSE that original is Eng
	 * 
	 * */
	public void setOriginalLang(boolean zeroOrOne){
		fromEngToRus=zeroOrOne;
	}
	
	
	// set boundaries for readSection method
	public void setBoundaries(String startLine, String endLine){
		
		this.startLine=startLine;
		this.endLine=endLine;
		
		// to create a new array of unique words
		flag2=true;
		
		// to zero number of elements stored into
		// array with unique words
		counter1=0;
		
		// as the section was changed lets read new one
		readSection();
	}
	
	
	public void readSection() {
		
		// clear this collection
		arrayColl.clear();
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(filePath));

			String line = "";
			while ((line = file.readLine()) != null) {
				if (line.startsWith(endLine)) // конец поля считывания
				{
					metkaBeginningEnd = 2;
				}

				if (metkaBeginningEnd == 1) // запись строки в коллекцию
				{
					if (line.isEmpty() == false) {
						arrayColl.add(line);
					}
				}

				// начало поля считывания;
				// стоит после endLine чтобы не записывать поисковой символ
				if (line.startsWith(startLine)) {
					metkaBeginningEnd = 1;
				}
			}
			file.close();
		} catch (IOException e) {
//				firstField.setText("Ошибка");
		}
	}

		
		
		// получим случайную строку из коллекции
		private String getRandomWord() { 
			// получаем число строк
			lineNumber = arrayColl.size(); 
																			
			do {
				// получаем случайное число  в  этом диапазоне
				randomNumber = (int) Math.floor(Math.random() * lineNumber); 

			} while (arrayColl.get(randomNumber).length() < 5);

			// возвращаем элемент с полученным номером
			return arrayColl.get(randomNumber);
		}

		
		private void makeWordUnique() {

			// есть ли в массиве такая строка
			if (Arrays.asList(arrayCheck).contains(strRandomLine)) {
				// до тех пор, пока строка не будет отличаться от строк в массиве
				while (Arrays.asList(arrayCheck).contains(strRandomLine)) {
					strRandomLine = getRandomWord();
					System.out.println(strRandomLine);
				}
			}
			
			// добавляем слово в массив для уникальных строк
			arrayCheck[counter1] = strRandomLine;
		}
		
		
		
		// получим слова для вывода на экран
		public String[] getWord() 
		{
			String splitedLineSys[] = new String[2];

			// получим случайную строку из коллекции
			strRandomLine = getRandomWord();

			if (flag2 == true) {
				// получим ~60% от числа строк в разделе
				exceptionNumber = Math.round(((lineNumber * 6) / 10) - 10);
				System.out.println(exceptionNumber+" : Size of the check array");

				// создадим массив с таким числом элементов
				arrayCheck = new String[exceptionNumber];
				flag2 = false;
			}

			// check if this line is unique
			// if it's already been used then get another line
			makeWordUnique();

			
			counter1++;
			if (counter1 == exceptionNumber)
				counter1 = 0;

			// weather by "-" or by "—"
			splitedLineSys = strRandomLine.split("-|—", 2);
			// удаляем пробелы в начале в конце
			splitedLineSys[0] = splitedLineSys[0].trim();
			splitedLineSys[1] = splitedLineSys[1].trim();
			
			
			// to create translation from Rus to Eng
			if (fromEngToRus==true) {
				String per=splitedLineSys[0];
				splitedLineSys[0]=splitedLineSys[1];
				splitedLineSys[1]=per;
			}
			
			System.out.println(counter1 + "  " + splitedLineSys[0]+" - "+splitedLineSys[1]);
			
			/*
			 * now splitedLineSys[0] contains original, splitedLineSys[1] contains
			 * translation
			 */
			return splitedLineSys;
		}
	
	
	
	
	
}
