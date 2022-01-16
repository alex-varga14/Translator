package src;

import java.io.*;
/* TranslationText
 * Serializable representation of the data file. Has the serialVersionUID of 19.
 * No method in this class throws an exception.
*/
public class TranslationText implements Serializable {
	static final long serialVersionUID = 19;
	private String sentence;
	private String [] months;
	private String [] days;
	
	public TranslationText(String [] m, String [] d, String formattedSentence) {
		this.months = m;
		this.days = d;
		this.sentence = formattedSentence;
	}
	public String getSentence() {
		return this.sentence;
	}

	public String [] getMonths() {
		return this.months;
	}

	public String [] getDays() {
		return this.days;
	}

	public String getMonth(int i) {
		return this.months[i];
	}
	
	public String getDay(int i) {
		return this.days[i];
	}
}
