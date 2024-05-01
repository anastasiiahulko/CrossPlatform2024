package tcpWork.operation;

import tcpWork.model.MetroCard;

public class AddMetroCardOperation extends ShowBalanceOperation implements Operation {
    private MetroCard card = null;

    public AddMetroCardOperation() {
        card = new MetroCard();
    }

    public tcpWork.model.MetroCard getCard() {
        return card;
    }

    @Override
    public void execute() {
        // Логіка додавання нової карти
    }
}
