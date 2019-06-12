import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.BigInteger;

public class CalculationThread extends Thread {
	private static final BigDecimal ONE = new BigDecimal(1);
	private int index;
	private int numThreads;
	private int precision;
	private BigDecimal result = new BigDecimal(0);

	public BigDecimal getResult() {
		return result;
	}

	@Override
	public void run() {
		Logger.log("Thread " + index + " started.");
		long startedAt = System.currentTimeMillis();
		BigDecimal res;
		for (int i = 0;; i++) {
			if (i % numThreads != index) { // divide the work between the threads
				continue;
			}
			res = getRowResult(i);
			result = result.add(res);
			if (res.scaleByPowerOfTen(precision).compareTo(ONE) < 0) { // is it time to stop?
				break;
			}
		}
		Logger.log(
				"Thread " + index + " stopped. Execution time: " + (System.currentTimeMillis() - startedAt) + " ms.");
	}

	public CalculationThread(int index, int numThreads, int precision) {
		super();
		this.index = index;
		this.numThreads = numThreads; 
		this.precision = precision;
	}

	private BigDecimal getRowResult(int k) {
		BigDecimal top = new BigDecimal(3 - 4 * k * k);
		BigDecimal bot = new BigDecimal(calculateFactorial(2 * k + 1));
		BigDecimal res = top.divide(bot, precision, RoundingMode.HALF_UP);
//		Logger.log(res.toString());
		return res;
	}

	private BigInteger calculateFactorial(int number) {
		BigInteger result = new BigInteger("1");
		for (int i = 1; i <= number; i++) {
			result = result.multiply(new BigInteger("" + i));
		}
		return result;
	}
	// e = Sum (
	// [ 3 - (4k)^2 ] / (2k+1)!
	// )
}
