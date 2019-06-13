import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apfloat.ApintMath;

import javax.swing.*;

public class CalculationThread extends Thread{
    private String name;
    private int k;
    private int t;
    private int p;
    private int index;
    private Boolean finished;
    private Boolean quiet;
    private long runtime;
    private BigDecimal sum = BigDecimal.ZERO;
    private JTextArea console;

    public CalculationThread(String name, int k, int t, int p, int index, Boolean quiet, JTextArea console) {
        this.name = name;
        this.k = k;
        this.t = t;
        this.p = p;
        this.index = index;
        this.finished = false;
        this.quiet = quiet;
        this.console = console;
    }

    public void run() {
        if (!quiet  && console == null) {
            System.out.println(name + " started");
        } else if(!quiet && console != null){
            console.append(name + " started\n");
        }
        long start =  System.currentTimeMillis();

        for (int i = index; i <= k; i+=t) {
            if (!quiet  && console == null) {
                System.out.println(name + " calculating for k=" + i);
            }

            sum = sum.add(calculateValue(i));
        }

        runtime = System.currentTimeMillis() - start;
        if (!quiet && console == null) {
            System.out.println(name + " stopped.");
        } else if (!quiet && console != null) {
            console.append(name + " stopped.\n");
        }

        this.finished = true;
    }


    private BigDecimal calculateValue(int k){
        BigDecimal numerator = new BigDecimal(3 - 4 * Math.pow(k, 2));
        BigDecimal denominator = new BigDecimal(ApintMath.factorial(2*k + 1, 10).toBigInteger());
        BigDecimal result = numerator.divide(denominator, p, RoundingMode.HALF_UP);
        return result;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getThreadName() {
        return name;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public long getRuntime() {
        return runtime;
    }
}
