package tcpWork.server;

import tcpWork.operation.AddMetroCardOperation;
import tcpWork.operation.AddMoneyOperation;
import tcpWork.model.MetroCardBank;
import tcpWork.operation.PayMoneyOperation;
import tcpWork.operation.RemoveCardOperation;
import tcpWork.operation.ShowBalanceOperation;
import tcpWork.operation.StopOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler extends Thread {
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean work = true;
    private tcpWork.model.MetroCardBank bank = null;
    private Socket socket = null;

    public ClientHandler(MetroCardBank bank, Socket socket) {
        this.bank = bank;
        this.socket = socket;
        this.work = true;
        try {
            this.is = new ObjectInputStream(socket.getInputStream());
            this.os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void run() {
        synchronized (bank) {
            System.out.println("Client Handler Started for: " + socket);
            while (work) {
                try {
                    Object obj = is.readObject();
                    processOperation(obj);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error: " + e);
                    try {
                        finish();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            try {
                System.out.println("Client Handler Stopped for: " + socket);
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    private void processOperation(Object obj) throws IOException, ClassNotFoundException {
        if (obj instanceof StopOperation) {
            finish();
        } else if (obj instanceof AddMetroCardOperation) {
            addCard(obj);
        } else if (obj instanceof AddMoneyOperation) {
            addMoney(obj);
        } else if (obj instanceof PayMoneyOperation) {
            payMoney(obj);
        } else if (obj instanceof RemoveCardOperation) {
            removeCard(obj);
        } else if (obj instanceof ShowBalanceOperation) {
            showBalance(obj);
        } else {
            error();
        }
    }

    private void finish() throws IOException {
        work = false;
        os.writeObject("Finish Work " + socket);
        os.flush();
    }

    private void addCard(Object obj) throws IOException, ClassNotFoundException {
        bank.addCard(((AddMetroCardOperation) obj).getCard());
        os.writeObject("Card Added");
        os.flush();
    }

    private void addMoney(Object obj) throws IOException, ClassNotFoundException {
        AddMoneyOperation op = (AddMoneyOperation) obj;
        boolean res = bank.addMoney(op.getSerNum(), op.getMoney());
        if (res) {
            os.writeObject("Balance Added");
            os.flush();
        } else {
            os.writeObject("Cannot Balance Added");
            os.flush();
        }
    }

    private void payMoney(Object obj) throws IOException, ClassNotFoundException {
        PayMoneyOperation op = (PayMoneyOperation) obj;
        boolean res = bank.payMoney(op.getSerNum(), op.getFare());
        if (res) {
            os.writeObject("Money Paid");
            os.flush();
        } else {
            os.writeObject("Cannot Pay Money");
            os.flush();
        }
    }

    private void removeCard(Object obj) throws IOException, ClassNotFoundException {
        RemoveCardOperation op = (RemoveCardOperation) obj;
        boolean res = bank.removeCard(op.getSerNum());
        if (res) {
            os.writeObject("Metro Card Successfully Removed: " + op.getSerNum());
            os.flush();
        } else {
            os.writeObject("Cannot Remove Card " + op.getSerNum());
            os.flush();
        }
    }

    private void showBalance(Object obj) throws IOException, ClassNotFoundException {
        ShowBalanceOperation op = (ShowBalanceOperation) obj;
        double balance = bank.getBalance(op.getSerNum());
        if (balance >= 0) {
            os.writeObject("Card: " + op.getSerNum() + ", balance: " + balance);
            os.flush();
        } else {
            os.writeObject("Cannot Show Balance for Card: " + op.getSerNum());
            os.flush();
        }
    }

    private void error() throws IOException {
        os.writeObject("Bad Operation");
        os.flush();
    }
}
