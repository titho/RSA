import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int k = 5000;
        int t = 1;
        int p = 10000;
        Boolean quiet = false;
        String outputFile = "result.txt";
        boolean gui = false;

        for (int i = 0; i < args.length; i++) {

            if(Main.checkKparameter(args[i]))
            {
                k = Integer.parseInt(args[i+1]);
            }
            if(Main.checkTparameter(args[i])){
                t = Integer.parseInt(args[i+1]);
            }
            if(Main.checkPparameter(args[i])){
                p = Integer.parseInt(args[i+1]);
            }
            if(Main.checkQparameter(args[i])){
                quiet = true;
            }
            if (Main.checkOparameter(args[i])) {
                outputFile = args[i+1];
            }
            if (Main.checkGUIparameter(args[i])) {
                gui = true;
                break;
            }
        }

        if (gui && !quiet) {
            GUIFrame frame = new GUIFrame();
            frame.init();
        } else {
            executeInConsole(k, t, p, quiet, outputFile, null);
        }
    }

    public static void executeInConsole(int k, int t, int p, boolean quiet, String outputFile, JTextArea console){
        long startRuntime = System.currentTimeMillis();
        ArrayList<CalculationThread> threads = new ArrayList<>();


        for (int i = 0; i < t; i++) {
            CalculationThread thread = new CalculationThread("Thread_" + i, k, t, p, i, quiet, console);
            threads.add(thread);
            thread.start();
        }

        while (true) {
            boolean finished = true;
            for (CalculationThread thread : threads) {
                finished = finished && thread.isFinished();
            }
            if (finished) {
                break;
            }
        }

        long calculationRuntime = System.currentTimeMillis() - startRuntime;

        if (!quiet){
            if (console == null) {
                System.out.println("Threads used in the current run: " + t);
                System.out.println("Total runtime: " + calculationRuntime);
            } else {

                console.append("Threads used in the current run: " + t + "\n");
                console.append("Total runtime: " + calculationRuntime + "\n");
            }
        }

        if (console == null) {
            for (CalculationThread thread : threads) {
                System.out.println(thread.getThreadName() + " execution time was (millis): " + thread.getRuntime());
            }
        } else {
            for (CalculationThread thread : threads) {
                console.append(thread.getThreadName() + " execution time was (millis): " + thread.getRuntime() + "\n");
            }
        }


        BigDecimal total_sum = BigDecimal.ZERO;
        for (CalculationThread thread : threads) {
            total_sum = total_sum.add(thread.getSum());
        }
        saveFile(total_sum, threads, calculationRuntime, outputFile);
    }


    public static void saveFile(BigDecimal e, ArrayList<CalculationThread> threads, Long calculationRuntime, String fileName){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(fileName));

            for (CalculationThread thread : threads) {
                writer.write(thread.getThreadName() + " execution time was (millis): " + thread.getRuntime());
                writer.newLine();
            }

            writer.write("Threads used in the current run: " + threads.size());
            writer.newLine();

            writer.write("Total runtime: " + calculationRuntime);
            writer.newLine();

            writer.write("e = " + e.toString());
            writer.newLine();

        }
        catch ( IOException exception)
        {
        }

        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException exception)
            {
            }
        }
    }

    public static Boolean checkKparameter(String argument) {
        return argument.toLowerCase().contains("-k");
    }

    public static Boolean checkTparameter(String argument) {
        return argument.toLowerCase().contains("-t");
    }

    public static Boolean checkPparameter(String argument) {
        return argument.toLowerCase().contains("-p");
    }

    public static Boolean checkQparameter(String argument) {
        return argument.toLowerCase().contains("-q");
    }

    public static Boolean checkOparameter(String argument) {
        return argument.toLowerCase().contains("-o");
    }

    public static Boolean checkGUIparameter(String argument) {
        return argument.toLowerCase().contains("-gui");
    }
}
