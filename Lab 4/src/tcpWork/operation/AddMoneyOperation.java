package tcpWork.operation;

public class AddMoneyOperation extends ShowBalanceOperation implements Operation {
    private String serNum;
    private double money;

    public AddMoneyOperation(String serNum, double money) {
        this.serNum = serNum;
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public String getSerNum() {
        return serNum;
    }

    @Override
    public void execute() {
        // Логіка поповнення рахунку
    }
}


