import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
/*

Using corpus.txt as input again, write a program that takes a string (word) 
as an argument and returns 2 values: 
a) the word (string) it occurs most frequently BEFORE. 
b) the word (string) in occurs most frequently AFTER.

For example: "I like Java programming"
input string: "like"
returns: "I" and "java"

-----------------------------------------------------------------------------------------------------------------------------------------
*/

public class MostFrequentBeforeAndAfter {

	private String fileContent;
	
	MostFrequentBeforeAndAfter(String fileContent) {
		this.fileContent = fileContent;
	}
	
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	/**
	 * GetMostFrequent
	 * Fills a container class with the most frequent words that appear before and after the text passed in the param
	 * @param String text to search for
	 * @return MostFrequentBeforeAndAfterContainer, object hold a copy of the most frequent words
	 */
	public MostFrequentBeforeAndAfterContainer GetMostFrequent(String textToMatch) {
		
		Vector<String> beforeVector = new Vector<String>();
		Vector<String> afterVector = new Vector<String>();

		Map<String, Integer> beforeMap = new HashMap<String, Integer>();
		Map<String, Integer> afterMap = new HashMap<String, Integer>();

		StringTokenizer fileContentTokenized = new StringTokenizer(fileContent, " ");
		int tokenCount = fileContentTokenized.countTokens();
		
		String[] tokenizedText = new String[tokenCount]; 
		
		//separate the text into an array
		for(int i = 0; i < tokenCount; i++) {
			tokenizedText[i] = fileContentTokenized.nextElement().toString();
		}
		//Get all the before and after words, stick them into their vector of String's
		for(int i = 0; i < tokenCount; i++) {
			if(textToMatch.equals(tokenizedText[i]) == true) {
				if(i > 0 && i < tokenCount - 2) {//dont get from array at position 0 - 1 OR tokenCount + 1, out of bounds 
					beforeVector.addElement(tokenizedText[i-1].toString());
					afterVector.addElement(tokenizedText[i+1].toString());
				}
				if(i == 0) {
					beforeVector.addElement("");
				}
				if(i == tokenCount - 1) {
					afterVector.addElement("");
					
				}
			}
		}
		//Sort the vectors so we can count the frequency of the before and after words
		Collections.sort(beforeVector);
		Collections.sort(afterVector);
	
		String before = beforeVector.get(0);
		beforeMap.put(before, 1);
		int beforeCount = 1;
		for(int i=1; i < beforeVector.size(); i++) {
			if(before.equals(beforeVector.get(i)) == true) {
				beforeMap.put(beforeVector.get(i), ++beforeCount);
			} else {
				beforeMap.put(beforeVector.get(i), 1);
				
				beforeCount = 1;
			}
			before = beforeVector.get(i);
		}
		
		String after = afterVector.get(0);
		afterMap.put(before, 1);
		int afterCount = 1;
		for(int i=1; i < afterVector.size(); i++) {
			if(after.equals(afterVector.get(i)) == true) {
				afterMap.put(afterVector.get(i), ++afterCount);
			} else {
				afterMap.put(afterVector.get(i), 1);
				
				afterCount = 1;
			}
			after = afterVector.get(i);
		}

		// Get the most frequent before.
		Set<String> beforeKeys = beforeMap.keySet();
		int mostFrequentBeforeValue = 0;
		String mostFrequentBefore = null;
		// Loop over String keys.
		for (String key : beforeKeys) {
			//System.out.println("BEFORE : " + key + " " + beforeMap.get(key));
			if(mostFrequentBeforeValue < beforeMap.get(key)) {
				mostFrequentBefore = key;
				mostFrequentBeforeValue = beforeMap.get(key);
			}
		}

		// Get the most frequent after.
		Set<String> afterKeys = afterMap.keySet();
		int mostFrequentAfterValue = 0;
		String mostFrequentAfter = null;
		// Loop over String keys.
		for (String key : afterKeys) {
			//System.out.println("AFTER : " + key + " " + afterMap.get(key));
			if(mostFrequentAfterValue < afterMap.get(key)) {
				mostFrequentAfter = key;
				mostFrequentAfterValue = afterMap.get(key);
			}
		}

		return new MostFrequentBeforeAndAfterContainer(mostFrequentBefore, mostFrequentAfter);
	}
	/*
	 * simple container to hold the found strings from the search
	 */
	public class MostFrequentBeforeAndAfterContainer {
		
		MostFrequentBeforeAndAfterContainer(String before, String after) {
			this.before = before;
			this.after = after;
		}
		
		public String getBefore() {
			return before;
		}
		public void setBefore(String text) {
			before = text;
		}
		public String getAfter() {
			return after;
		}
		public void setAfter(String text) {
			after = text;
		}
		private String before;
		private String after;
	}
}
