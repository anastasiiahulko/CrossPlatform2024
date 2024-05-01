package tcpWork.model;

import java.util.ArrayList;

public class MetroCardBank {
    private ArrayList<tcpWork.model.MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    // Додати картку у сховище
    public void addCard(tcpWork.model.MetroCard newCard) {
        store.add(newCard);
    }

    // Пошук картки за серійним номером
    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    // Видалення картки за серійним номером
    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    // Поповнення рахунку для картки за серійним номером
    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            tcpWork.model.MetroCard card = store.get(index);
            card.setBalance(card.getBalance() + money);
            return true;
        }
        return false;
    }

    // Оплата поїздки для картки за серійним номером
    public boolean payMoney(String serNum, double fare) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            double balance = card.getBalance();
            if (balance >= fare) {
                card.setBalance(balance - fare);
                return true;
            }
        }
        return false;
    }

    // Отримання балансу картки за серійним номером
    public double getBalance(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            return store.get(index).getBalance();
        }
        return -1;
    }
}