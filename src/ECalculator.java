import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class ECalculator {
	static int precision = 0;
	static int numThreads = 0;
	static String outputFile = "result.txt";
	static BigDecimal result = new BigDecimal(0);
	static CalculationThread[] threads;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		long startedAt = System.currentTimeMillis();
		try {
			readCommandLineParameters(args);
		} catch (ParseException|NumberFormatException e) {
			Logger.log("Error in parsing the command line parameters.");
			return;
		}
		
		threads = new CalculationThread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CalculationThread(i, numThreads, precision + 2);
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
			result = result.add(threads[i].getResult());
		}
		
		PrintWriter pw = new PrintWriter(outputFile);
		pw.write(result.toString());
		pw.close();
		
		Logger.log(numThreads + " threads used.");
		Logger.log("Total execution time is: " + (System.currentTimeMillis() - startedAt) + " ms.", true);
	}

	private static void readCommandLineParameters(String[] args)
			throws ParseException {
		Options options = new Options();
		options.addOption("p", true, "Number members to calculate");
		options.addOption("t", true, "Number of threads");
		options.addOption("o", true, "Output file");
		options.addOption("q", false, "Quiet mode");
		
		CommandLineParser defaultParser = new DefaultParser();
		CommandLine cmd = defaultParser.parse(options, args);
		precision = Integer.parseInt(cmd.getOptionValue("p"));
		numThreads = Integer.parseInt(cmd.getOptionValue("t"));
		if (precision < 1 || numThreads < 1) {
			throw new NumberFormatException("p and t must be positive.");
		}
		if (cmd.hasOption("o")) {
			outputFile = cmd.getOptionValue("o");
		}
		if (cmd.hasOption("q")) {
			Logger.setQuietMode(true);
		}
	}

}
