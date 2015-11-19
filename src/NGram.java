import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/* 
		An n-gram is a contiguous sequence of n words from a given sequence of text.

		For the text file provided (resources/corpus.txt), for each value of n (where 1<=n<=3), write a function which outputs a list of all 
		n-grams	and their frequency, sorted by frequency. You can assume a word is any token (including punctuation) surrounded by whitespace. 

		[hint: java StringTokenizer or String.split()]

		The output should be in the format "{n-gram>}\t{count}". Sample output and values from the corpus.txt are shown below:

		== Sample output ==

		(for n=1)
		the	108
		is	35		
		we	18

		(for n=2)
		Madam President	15
		I would	11
		on the	8

		(for n=3)
		I would like	6
		the principle of	3
		=====================

		Extend the previous function to display and sort all unigrams (a unigram is an n-gram where n = 1 i.e. a single word or token) 
		by their RELATIVE frequency (this is: number of occurrences of a word / total number of words).

		e.g. "the" occurs 108 times and there are 2108 words overall so the relative frequency of "the" = 108/2108 = 0.051 (to 3 decimal places) 
		
	-----------------------------------------------------------------------------------------------------------------------------------------
	*/
/**
 * 	
 * @author Derek Keogh
 * 
 */
public class NGram {
	/**
	 * 
	 * Simple enum to restrict the size of the N_GRAMS available
	 * ie. 
	 * N_GRAM_ONE "Hello"
	 * N_GRAM_TWO "Hello and",
	 * N_GRAM_THREE "Hello and goodbye",;
	 *
	 */
	public static enum N_GRAMS {
		N_GRAM_ONE(1),
		N_GRAM_TWO(2),
		N_GRAM_THREE(3);
		
		private int value;
		
		private N_GRAMS(int value) {
			this.value = value;
		}
	}
	//Don't really need to keep this, could just put it in the tokenized string, but just in case,
	//I'll keep the original because I will be replacing the carriage return-newline pair with a space
	private String fileContent;
	private String fileContentStripped;
	static final String DEFAULT_TOKEN = " ";
	private String token;
	//The file content in a tokenized form
	private StringTokenizer fileContentTokenized;
	private int tokenCount;
	String[] tokenizedText;
	//I'm using three separate maps, one for each size of n_gram
	private Map<String, Integer> n_1;
	private Map<String, Integer> n_2;
	private Map<String, Integer> n_3;

	/**
	 * This constructor will use a default token which is set to a single space " "
	 * @param fileContent
	 * @throws NullPointerException
	 */
	NGram(final String fileContent) throws NullPointerException {
		init(fileContent, DEFAULT_TOKEN);
	}
	/**
	 * Just in case you need to supply a different token
	 * @param fileContent
	 * @param token
	 * @throws NullPointerException
	 */
	NGram(final String fileContent, final String token) throws NullPointerException {
		init(fileContent, token);
	}
	/**
	 * 
	 * @param fileContent
	 * @param token
	 * @throws NullPointerException
	 */
	private void init(final String fileContent, final String token) throws NullPointerException { 
		this.fileContent = fileContent;
		this.token = token;
		this.fileContentStripped = fileContent.replace("\r\n", this.token);
		
		n_1 = new HashMap<String, Integer>();
		n_2 = new HashMap<String, Integer>();
		n_3 = new HashMap<String, Integer>();
		
		this.buildTheTokenizedString();
		
		n_1 = this.fillTheMapWithNWords(N_GRAMS.N_GRAM_ONE);
		n_2 = this.fillTheMapWithNWords(N_GRAMS.N_GRAM_TWO);
		n_3 = this.fillTheMapWithNWords(N_GRAMS.N_GRAM_THREE);
	
	}
	/**
	 * Split the file content using the token, weather it was supplied of uses the default
	 */
	private void buildTheTokenizedString() { 
		fileContentTokenized = new StringTokenizer(this.fileContentStripped, this.token);
		
		tokenCount = fileContentTokenized.countTokens();
		tokenizedText = new String[tokenCount]; 
		
		for(int i = 0; i < tokenCount; i++) {
			tokenizedText[i] = fileContentTokenized.nextElement().toString();
		}
	}
	/**
	 * Get the number of words specified in n_gramCount and return them as one String
	 * It will put the token back in between the words if there's more than 1 to be retrieved 
	 * @param startIndex
	 * @param nGramOne
	 * @return
	 */
	private String getN_WordsFromTokenizedString(final int startIndex, final N_GRAMS nGram) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < nGram.value; i++) {
			ret.append(tokenizedText[i + startIndex]);
			if(i < nGram.value-1)
				ret.append(this.token);
		}
		return ret.toString();
	}
	/**
	 * 
	 * @param nGram
	 * @return
	 */
	private HashMap<String, Integer> fillTheMapWithNWords(final N_GRAMS nGram) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put(getN_WordsFromTokenizedString(0, nGram), 1);

		for (int i = nGram.value; i < tokenCount - nGram.value; i++) {
			if (map.get(getN_WordsFromTokenizedString(i, nGram)) != null) {
				int currValue = map.get(getN_WordsFromTokenizedString(i, nGram)) + 1;
				map.put(getN_WordsFromTokenizedString(i, nGram), currValue);
			} else {
				map.put(getN_WordsFromTokenizedString(i, nGram), 1);
			}
		}
		
	    return sortByValues(map);
	}
	/**
	 * A little helper to get an amount of spaces to make display alignment look nicer
	 * @param len
	 * @return String containing a few spaces
	 */
	private String getSomeSpaces(final int len) {
		int maxNumberOfSpaces = 8;
		StringBuffer ret = new StringBuffer();

		for(int i = maxNumberOfSpaces; i > 0 + len; i--) {
			ret.append(" ");
		}
		return ret.toString();
		
	}
	/**
	 * Does exactly what it say in the method name:-)
	 * @param nGram
	 */
	public void displayOneNGram(final N_GRAMS nGram) {
		switch(nGram) {
		case N_GRAM_ONE:
			displayOneNGram(n_1, nGram);
			break;
		case N_GRAM_TWO:
			displayOneNGram(n_2, nGram);
			break;
		case N_GRAM_THREE:
			displayOneNGram(n_3, nGram);
			break;
			default:
				System.out.println("You can only choose from");
		}
	}	
	/**
	 * Does exactly what it say in the method name:-)
	 * I adjusted the layout of the output, its looks a little bit more readable this way(only in my opinion).
	 */
	private void displayOneNGram(final Map<String, Integer> map, final N_GRAMS nGram) {
		// Get the most frequent before.
		Set<String> keys = map.keySet();
		// Loop over String keys
		System.out.println("************* For n=" + nGram.value + " *************");
		for (String key : keys) {
			int valLen = map.get(key).toString().length();
			String deci = String.format("%.3f", (float)map.get(key) / this.tokenCount);
			System.out.println("FREQUENCY : " +  map.get(key) + "," + getSomeSpaces(valLen) + deci + "\t{" + key + "}");
		}
	}

	/**
	 * Does exactly what it say in the method name:-)
	 */
	public void displayTheNGrams() {
		
		displayOneNGram(n_1, N_GRAMS.N_GRAM_ONE);
		displayOneNGram(n_2, N_GRAMS.N_GRAM_TWO);
		displayOneNGram(n_3, N_GRAMS.N_GRAM_THREE);
		System.out.println("Total word count = " + tokenCount);
	}
	/**
	 * This will sort the entries in the map by value, which in this case the value is the frequency of the n-gram
	 * @param map
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	private static HashMap<String, Integer> sortByValues(Map<String, Integer> map) {
		LinkedList list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Copy the sorted list in HashMap using LinkedHashMap to preserve the
		// insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
	

	/**
	 * getter
	 * @return
	 */
	public StringTokenizer getFileContentTokenized() {
		return fileContentTokenized;
	}
	/**
	 * setter
	 * @param fileContentTokenized
	 */
	public void setFileContentTokenized(StringTokenizer fileContentTokenized) {
		this.fileContentTokenized = fileContentTokenized;
	}
	/**
	 * getter
	 * @return
	 */
	public int getTokenCount() {
		return tokenCount;
	}
	/**
	 * setter
	 * @param tokenCount
	 */
	public void setTokenCount(int tokenCount) {
		this.tokenCount = tokenCount;
	}
	/**
	 * getter
	 * @return
	 */
	public String getFileContent() {
		return fileContent;
	}
	/**
	 * setter
	 * @param fileContent
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}
