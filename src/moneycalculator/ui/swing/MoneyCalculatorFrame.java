package moneycalculator.ui.swing;

import moneycalculator.ui.MoneyDisplay;
import moneycalculator.ui.MoneyDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.persistence.ExchangeRateLoader;

public final class MoneyCalculatorFrame extends JFrame {

    private final Currency[] currencies;
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    private ExchangeRateLoader exchangeRateLoader;

    public MoneyCalculatorFrame(Currency[] currencies, ExchangeRateLoader exchangeRateLoader) {
        this.currencies = currencies;

        this.setTitle("Money Calculator");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.exchangeRateLoader = exchangeRateLoader;
        this.add(moneyDialog(), BorderLayout.NORTH);
        this.add(toolbar(), BorderLayout.CENTER);
        this.add(moneyDisplay(), BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);

    }

    private Component moneyDialog() {

        SwingMoneyDialog swingMoneyDialog = new SwingMoneyDialog(currencies, exchangeRateLoader);
        moneyDialog = swingMoneyDialog;
        return swingMoneyDialog;
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.add(createButton());
        return panel;
    }

    private Component createButton() {
        JButton button = new JButton("calculate");
        button.addActionListener(calculate());
        return button;
    }

    private ActionListener calculate() {
        return (ActionEvent e) -> {
            Money money = moneyDialog.get();
            moneyDisplay.display(money);
            this.pack();

        };

    }

    private Component moneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }

}
