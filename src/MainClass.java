
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainClass {
	
	private static String fileContent;

	public static void main(String str_params[])
	{
		//Factorial
		System.out.println("*********************** Factorial ***********************");
		Factorial factor = new Factorial(5);
		System.out.println("Non Recursive = " + factor.nonRecursiveFactortial());
		System.out.println("Recursive = " + factor.recursiveFactortial());

		//First thing to do is read in the content for later use, Q2 and Q3 use this
		//fileContent = readFile("/home/derek/workspace/Extended/src/corpus.txt");
		fileContent = readFile("C:/Users/dell/workspace/BitsAndPieces/resources/corpus.txt");

		if(fileContent != null) {
			System.out.println("*********************** NGrams ***********************");
			NGram Q2 = new NGram(fileContent);
			
			//You could call the following to display all n-gram maps
			//Q2.displayTheNGrams();
			// OR display them one at a time
			Q2.displayOneNGram(NGram.N_GRAMS.N_GRAM_THREE);
			Q2.displayOneNGram(NGram.N_GRAMS.N_GRAM_TWO);
			Q2.displayOneNGram(NGram.N_GRAMS.N_GRAM_ONE);

			System.out.println("*********************** MostFrequentBeforeAndAfter ***********************");
			MostFrequentBeforeAndAfter Q3 = new MostFrequentBeforeAndAfter(fileContent);
			MostFrequentBeforeAndAfter.MostFrequentBeforeAndAfterContainer words = Q3.GetMostFrequent("the");
			System.out.println("Most Frequent before : " + words.getBefore());
			System.out.println("Most Frequent after : " + words.getAfter());
		} else {
			System.out.println("Problem with the fileContent for Question 2 and 3");
		}
	}
	
	/**
	 * readFile()
	 * @param, String containing the filename
	 * @return, String with the contents of the file
	 */
	public static String readFile(String filename){
		
		BufferedInputStream bufferedInput = null;
        byte[] buffer = new byte[1024];
        StringBuffer content = new StringBuffer();;
        try {
            
            //Construct the BufferedInputStream object
            bufferedInput = new BufferedInputStream(new FileInputStream(filename));
            
            int bytesRead = 0;
            
            //Keep reading from the file while there is any content
            //when the end of the stream has been reached, -1 is returned
            while ((bytesRead = bufferedInput.read(buffer)) != -1) {
                
                //Process the chunk of bytes read
                //in this case we just construct a String and print it out
                String str_chunk = new String(buffer, 0, bytesRead);

                content.append(str_chunk);
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedInputStream
            try {
                if (bufferedInput != null)
                    bufferedInput.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }		
		return content.toString();
	}
}
