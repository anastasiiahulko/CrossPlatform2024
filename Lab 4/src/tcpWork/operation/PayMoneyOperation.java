package tcpWork.operation;

import java.io.Serializable;

public class PayMoneyOperation extends CardOperation implements Serializable {
    private String serNum;
    private double fare;

    public PayMoneyOperation(String serNum, double fare) {
        this.serNum = serNum;
        this.fare = fare;
    }

    public PayMoneyOperation() {
        this.serNum = null;
        this.fare = 0.0;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    @Override
    public void execute() {
        // Логіка операції
    }


}
