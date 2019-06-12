import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apfloat.ApintMath;


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
		for (int i = 0; ; i++) {
			if (i % numThreads != index) {// divide the work between the threads
				continue;
			}
			res = getRowResult(i);
			result = result.add(res);
			if (res.scaleByPowerOfTen(precision).compareTo(ONE) < 0 ){//is it time to stop?
				break;
			}
		}
		Logger.log("Thread " + index + " stopped. Execution time: " + (System.currentTimeMillis() - startedAt) + " ms.");
	}

	public CalculationThread(int index, int numThreads, int precision) {
		super();
		this.index = index;
		this.numThreads = numThreads;
		this.precision = precision;
	}


	private BigDecimal getRowResult(int k) {
		BigDecimal top = new BigDecimal( 3 - (4*k)*(4*k) );
		BigDecimal bot = new BigDecimal(ApintMath.factorial(2*k + 1, 10).toBigInteger());
		BigDecimal res = top.divide(bot, precision, RoundingMode.HALF_UP);
		return res;
	}
	
	// e = Sum (
	//	[ 3 - (4k)^2 ] / (2k+1)!
	// )
}
