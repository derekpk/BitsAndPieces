import java.io.IOException;


public class StringIntegerSpeed {

	public static void main(String... args) throws IOException {
	    for (int i = 0; i < 3; i++) {
	        long svo = perfStringValueOf();
	        long qqp = perfQuoteQuotePlus();
	        System.out.printf("String.valueOf took an average of %.3f us and \"\"+ took an average of %.3f us%n", svo / 1e3, qqp / 1e3);
	    }
	}

	private static long perfStringValueOf() {
	    long start = System.nanoTime();
	    final int runs = 100000;
	    String s;
	    for (int i = 0; i < runs; i++) {
	        s = String.valueOf(i * i);
	        // ensure s is not optimised away
	        if (s.length() < 1) throw new AssertionError();
	    }
	    long time = System.nanoTime() - start;
	    return time / runs;
	}

	private static long perfQuoteQuotePlus() {
	    long start = System.nanoTime();
	    final int runs = 100000;
	    String s;
	    for (int i = 0; i < runs; i++) {
	        s = "" + i * i;
	        // ensure s is not optimised away
	        if (s.length() < 1) throw new AssertionError();
	    }
	    long time = System.nanoTime() - start;
	    return time / runs;
	}
	
}
