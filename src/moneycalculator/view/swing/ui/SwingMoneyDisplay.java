package moneycalculator.view.swing.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import moneycalculator.view.MoneyDisplay;
import moneycalculator.model.Money;

public class SwingMoneyDisplay extends JPanel implements MoneyDisplay {

    private Money money;
    private final List<DisplayEvent> displayListeners = new ArrayList<>();

    @Override
    public void display(Money money) {
        this.money = money;
        this.removeAll();
        this.add(amount());
        this.add(currency());
        this.updateUI();
        displayListeners.forEach(DisplayEvent::action);

    }

    private Component amount() {
        return new JLabel(String.valueOf(money.getAmount()));
    }

    private JLabel currency() {
        return new JLabel(String.valueOf(money.getCurrency()));
    }

    public void on(DisplayEvent displayEvent) {
        this.displayListeners.add(displayEvent);
    }

    public interface DisplayEvent {
        public void action();
    }

}
