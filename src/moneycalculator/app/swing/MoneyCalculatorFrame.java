package moneycalculator.app.swing;

import moneycalculator.view.MoneyDisplay;
import moneycalculator.view.MoneyDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import moneycalculator.control.CalculateCommand;
import moneycalculator.model.Currency;
import moneycalculator.view.ExchangeRateLoader;

public final class MoneyCalculatorFrame extends JFrame {

    private final Currency[] currencies;
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    private final ExchangeRateLoader exchangeRateLoader;

    public MoneyCalculatorFrame(Currency[] currencies, ExchangeRateLoader exchangeRateLoader) {
        this.currencies = currencies;
        this.exchangeRateLoader = exchangeRateLoader;

        this.setTitle("Money Calculator");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(moneyDialog(), BorderLayout.NORTH);
        this.add(moneyDisplay(), BorderLayout.SOUTH);
        new CalculateCommand(moneyDisplay, moneyDialog).execute();
        this.pack();
        this.setVisible(true);

    }

    private Component moneyDialog() {

        SwingMoneyDialog swingMoneyDialog = new SwingMoneyDialog(currencies, exchangeRateLoader);
        moneyDialog = swingMoneyDialog;
        return swingMoneyDialog;
    }

    private Component moneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        swingMoneyDisplay.on(this::pack);
        moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }

}
