package src;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
	
	private String codeFile;
	private TranslationText translation;
	private static final String REGEX = "([a-z]{2}-[A-Z]{2})";
	private static final Pattern PATTERN= Pattern.compile(REGEX);
	
	public String translate(int monthNum, int dayNum, int year) {
		String tmp;
		if(monthNum > 12 || dayNum > 31 || dayNum <= 0 || monthNum <= 0) {
			throw new IllegalArgumentException("ERROR: Month or Day Provided is Invalid.");
		} else if (year > 2021) {
			throw new IllegalArgumentException("ERROR: Year provided is Invalid.");
		} else {
			tmp = String.format(translation.getSentence(), translation.getDay(dayNum-1), translation.getMonth(monthNum-1),  year);
			return tmp;
		}
	}
	
	public Translator(String langCode) {
		/* Constructor
		   * Accepts a String of a two-letter language code, dash, and two-letter region
		   * code, e.g., te-IN and throws an IllegalArgumentException if the language and
		   * region code are not in the correct format. Language codes are ISO 639-1 and
		   * region codes are ISO 3166, but this method only checks the format of the String, 
		   * not the validity of the codes. It calls importTranslation().
		  */
		if(langCode.length() != 5) {
			throw new IllegalArgumentException("ERROR: INVALID CODE");
		}
		Matcher m = PATTERN.matcher(langCode);
		if(m.find()) {
					this.codeFile = m.group() + ".ser";	
		}
		else {
			throw new IllegalArgumentException("ERROR: INVALID CODE PROVIDED");
		}
		importTranslation();
	}
	
	public TranslationText getTranslation() {
		return this.translation;
	}
	
	public void importTranslation() {
		/* importTranslation()
		   * Calls deserialize() if the appropriate file exists, otherwise calls importFromText().
		   * No arguments. Returns void.
		  */
			File name = new File(codeFile);
			try {
				if(name.exists())
				{
					//System.out.println("Serialized File Found, deserialization required:");
					deserialize();
				}
				else {
					//System.out.println(".txt file found.");
					var fileName = codeFile.replace("ser", "txt");
					File tmp = new File(fileName);
					if(tmp.exists()!=true) {
						throw new ArgFileNotFoundException();
					}
					else {
					importFromText();
					}
				}
		} catch (ArgFileNotFoundException e) {
			e.printStackTrace();
	        System.exit(1);
		}
		return;
	}

	public void importFromText() throws ArgFileNotFoundException {
		String txtFile = codeFile.replace("ser", "txt");
		File file = new File(txtFile);
		if(file.exists()) {
		try {
				BufferedReader in = new BufferedReader(new FileReader((txtFile)));
				String[] ms = new String[12];
				String[] ds = new String[31];
				String formatted;
				for(int i = 0; i < 12; i++) {
					ms[i]= in.readLine();
				}
				for(int i = 0; i < 31; i++) {
					ds[i] = in.readLine();
				}
				formatted = in.readLine();
				translation = new TranslationText(ms, ds, formatted);
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("ERROR: FILE ISSUES");
			System.exit(1);
		  }
		} else {
			throw new ArgFileNotFoundException();
		}
	}
	
	public void serialize() {
		/* serialize()
		  * Creates a serialized object file of the TranslationText object, with the
		  * name format la-CO.ser, where la is the two-letter language code and CO is
		  * the two-letter region code. An example of a serialized object file can be
		  * found in the exercise directory as es-BO.ser
		  * I/O exceptions can be thrown.
		   * No arguments. Returns void.
		  */
		try {
			//FileOutputStream fileOut = new FileOutputStream("C:/Users/Alex School/eclipse-workspace/assignments/"+ codeFile + ".ser");
			FileOutputStream fileOut = new FileOutputStream(codeFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.translation);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved");
		}
		catch (IOException i){
			i.printStackTrace();
		}
	}
	
	public void deserialize() {
		/* deserialize()
		   * Creates a TranslationText object from a .ser file. The files are named
		   * xx-YY.ser, where xx is the two-letter language code and YY is the two-
		   * letter region code. es-bo.ser is an example. It can throw I/O exceptions.
		   * No arguments. Returns void.
		  */
		translation = null;
		try {
			//FileInputStream fileIn = new FileInputStream("C:/Users/Alex School/eclipse-workspace/assignments/" + codeFile + ".ser");
			FileInputStream fileIn = new FileInputStream(codeFile);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			translation = (TranslationText) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("TranslatinText Class not found.");
			c.printStackTrace();
			return;
		}
	} 
}


