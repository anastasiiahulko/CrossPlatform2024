package tcpWork.operation;

public class ShowBalanceOperation extends CardOperation {
    private String serNum;

    public ShowBalanceOperation(String serNum) {
        this.serNum = serNum;
    }

    public ShowBalanceOperation() {
        this.serNum = null;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }


    @Override
    public void execute() {
        // Логіка виведення балансу
    }
}

