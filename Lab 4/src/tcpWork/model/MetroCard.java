package tcpWork.model;

import java.io.Serializable;

public class MetroCard implements Serializable {
    private String serNum;
    private User user;
    private String college;
    private double balance;

    public MetroCard() {
        // Пустий конструктор
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MetroCard{" +
                "serNum='" + serNum + '\'' +
                ", user=" + user +
                ", college='" + college + '\'' +
                ", balance=" + balance +
                '}';
    }
}
