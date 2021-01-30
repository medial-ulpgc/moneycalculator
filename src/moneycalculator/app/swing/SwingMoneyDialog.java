package moneycalculator.app.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import moneycalculator.view.MoneyDialog;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.view.ExchangeRateLoader;

public final class SwingMoneyDialog extends JPanel implements MoneyDialog {

    private final Currency[] currencies;
    private Double amount;
    private Consumer<Color> setAmountBackground;
    private Currency from;
    private Currency to;
    private final ExchangeRateLoader exchangeRateLoader;
    private final List<DialogChange> dialogChangeSubscribers = new ArrayList<>();

    public SwingMoneyDialog(Currency[] currencies, ExchangeRateLoader exchangeRateLoader) {
        this.currencies = currencies;
        this.exchangeRateLoader = exchangeRateLoader;
        this.add(amount());
        this.add(currencyComponent((currency) -> this.from = currency));
        this.add(currencyComponent((currency) -> this.to = currency));
        
    }

    @Override
    public Money get() {
        double exchangeRate = exchangeRateLoader.getExchangeRate(from, to);
        return new Money(amount * exchangeRate, to);
    }

    private Component amount() {
        JTextField textField = new JTextField("100");
        textField.setColumns(10);
        setAmountBackground = (color) -> textField.setBackground(color);
        
        textField.getDocument().addDocumentListener(amountChanged());
        amount = Double.parseDouble(textField.getText());
        return textField;

    }

    private Component currencyComponent(Consumer<Currency> saveSelection) {
        JComboBox comboBox = new JComboBox<Currency>(currencies);
        comboBox.addItemListener(currencyChanged(saveSelection));
        saveSelection.accept((Currency) comboBox.getSelectedItem());
        return comboBox;
    }

    private void notifyDialogChangeSubscribers() {
        Money money = get();
        dialogChangeSubscribers.forEach(subscriber -> subscriber.execute(money));
    }

    private ItemListener currencyChanged(Consumer<Currency> saveSelection) {
        return (ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                saveSelection.accept((Currency) e.getItem());
                notifyDialogChangeSubscribers();

            }
        };
    }

    private DocumentListener amountChanged() {
        return new DocumentListener() {
            

            @Override
            public void insertUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                amountChanged(e.getDocument());

            }

            private void amountChanged(Document document) {
                try {

                    amount = Double.parseDouble(document.getText(0, document.getLength()));
                    setAmountBackground.accept(Color.WHITE);
                    notifyDialogChangeSubscribers();
                } catch (BadLocationException e) {
                    Logger.getLogger(SwingMoneyDialog.class.getName()).log(Level.SEVERE, null, e);
                } catch (NumberFormatException e) {
                    setAmountBackground.accept(Color.PINK);
                }
            }

        };
    }

    @Override
    public void on(DialogChange dialogChange) {
        dialogChangeSubscribers.add(dialogChange);
    }

}
