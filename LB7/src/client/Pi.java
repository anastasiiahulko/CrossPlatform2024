package client;

import compute.Task;
import java.math.BigDecimal;

public class Pi implements Task<BigDecimal> {
    private static final long serialVersionUID = 227L;
    private static final BigDecimal FOUR = BigDecimal.valueOf(4);
    private final int digits;

    public Pi(int digits) {
        this.digits = digits;
    }

    public BigDecimal execute() {
        return computePi(digits);
    }

    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR);
        return pi.setScale(digits, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal arctan(int x, int scale) {
        BigDecimal result, numer, term;
        BigDecimal xBig = BigDecimal.valueOf(x);
        BigDecimal invX = BigDecimal.ONE.divide(xBig, scale, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal invX2 = invX.multiply(invX);
        numer = invX;
        result = numer;
        int i = 1;
        do {
            numer = numer.multiply(invX2);
            int denom = 2 * i + 1;
            term = numer.divide(BigDecimal.valueOf(denom), scale, BigDecimal.ROUND_HALF_EVEN);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        } while (term.compareTo(BigDecimal.ZERO) != 0);
        return result;
    }
}
