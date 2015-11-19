/* 
	---------- Q1 -----------

	The nth 'Factorial' F(n) for positive integers n is defined by:
		
	F(0) = 1
	F(n) = 1*2*3*...*(n-1)*n
		
	So:
	F(2) = 1*2 = 2
	F(3) = 1*2*3 = 6
	etc.

	(1a): Write an iterative (non-recursive) function to compute F(n) 
	(1a): Write a recursive function to compute F(n)

    -----------------------------------------------------------------------------------------------------------------------------------------
	*/
public class Factorial {
	private int factorialOfThis;
	private int nonRecursiveResult = 1;
	private int recursiveResult = 1;

	/**
	 * set the number that you want to get the factorial of
	 * @param factorialOfThis
	 */
	Factorial(int factorialOfThis) {
		this.factorialOfThis = factorialOfThis;
	}
	/**
	 * NON-Recursive factorial calculator
	 * @return result of the calculation
	 */
	public int nonRecursiveFactortial() {
		if (this.factorialOfThis < 0)
			System.out.println("Number should be non-negative.");
		else {
			for (int c = 1; c <= this.factorialOfThis; c++)
				nonRecursiveResult = nonRecursiveResult * c;
		}
		return nonRecursiveResult;
	}
		
	/**
	 * Method that calls the recursive calculator
	 * @return the result of the recursive calculation
	 */
	public int recursiveFactortial() {
		if (this.factorialOfThis < 0)
			System.out.println("Number should be non-negative.");
		else {
			recursiveResult = recursive(this.factorialOfThis);
		}
		return recursiveResult;
	}
	/**
	 * Recursive factorial calculator
	 * @param fact
	 * @return result of the calculation
	 */
	private int recursive(int fact) {
		if (fact == 0 || fact == 1)
			return 1;

		return recursive(fact - 1) * fact;
	}

	/**
	 * getter
	 * @return
	 */
	public int getFactorialOfThis() {
		return factorialOfThis;
	}

	/**
	 * setter
	 * @param factorialOfThis
	 */
	public void setFactorialOfThis(int factorialOfThis) {
		this.factorialOfThis = factorialOfThis;
	}

	/**
	 * setter
	 * @param nonRecursiveResult
	 */
	public void setNonRecursiveResult(int nonRecursiveResult) {
		this.nonRecursiveResult = nonRecursiveResult;
	}

	/**
	 * getter
	 * @return nonRecursiveResult
	 */
	public int getNonRecursiveResult() {
		return nonRecursiveResult;
	}

	/**
	 * setter
	 * @param recursiveResult
	 */
	public void setRecursiveResult(int recursiveResult) {
		this.recursiveResult = recursiveResult;
	}

	/**
	 * getter
	 * @return recursiveResult
	 */
	public int getRecursiveResult() {
		return recursiveResult;
	}
	
}
