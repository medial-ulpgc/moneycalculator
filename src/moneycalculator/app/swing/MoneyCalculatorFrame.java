package moneycalculator.app.swing;

import moneycalculator.view.MoneyDisplay;
import moneycalculator.view.MoneyDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import moneycalculator.control.CalculateCommand;
import moneycalculator.control.Command;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.view.ExchangeRateLoader;
import moneycalculator.view.swing.ui.SwingMoneyDialog;
import moneycalculator.view.swing.ui.SwingMoneyDisplay;

public final class MoneyCalculatorFrame extends JFrame {

    private final Currency[] currencies;
    private MoneyDialog moneyDialog;
    private MoneyDisplay moneyDisplay;
    private final ExchangeRateLoader exchangeRateLoader;
    private final Map<String,Command> commands;
    public MoneyCalculatorFrame(Currency[] currencies, ExchangeRateLoader exchangeRateLoader) {
        this.currencies = currencies;
        this.exchangeRateLoader = exchangeRateLoader;
        this.commands=createCommands();
        this.setTitle("Money Calculator");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        commands.entrySet().stream()
                .map(this::createButton)
                .forEach(panel::add);
        return panel;
    }

    private Component createButton(Map.Entry<String,Command> entry) {       
        JButton button = new JButton(entry.getKey());
        button.addActionListener((e)->entry.getValue().execute());
        return button;
    }

    private ActionListener calculate() {
        return (ActionEvent e) -> {
            Money money = moneyDialog.get();
            moneyDisplay.display(money);
            

        };

    }

    private Component moneyDisplay() {
        SwingMoneyDisplay swingMoneyDisplay = new SwingMoneyDisplay();
        swingMoneyDisplay.on(this::pack);
        moneyDisplay = swingMoneyDisplay;
        return swingMoneyDisplay;
    }

    private Map<String, Command> createCommands() {
        HashMap<String, Command> hashMap = new HashMap<>();
        hashMap.put("Calculate",new CalculateCommand(()->moneyDisplay, ()->moneyDialog));
        
        return hashMap;
    }

}
