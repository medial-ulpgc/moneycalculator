package moneycalculator.ui.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import moneycalculator.ui.MoneyDialog;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.persistence.ExchangeRateLoader;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {
    private final Currency[] currencies;
    private Double amount;
    private Currency from;
    private Currency to;
    private final ExchangeRateLoader exchangeRateLoader;

    SwingMoneyDialog(Currency[] currencies, ExchangeRateLoader exchangeRateLoader) {
        this.currencies = currencies;
        this.add(amount());
        this.add(currencyComponent((currency)->this.from=currency));
        this.add(currencyComponent((currency)->this.to=currency));
        this.exchangeRateLoader = exchangeRateLoader;
    }

    
    
    @Override
    public Money get() {
        double exchangeRate = exchangeRateLoader.getExchangeRate(from, to);
        return new Money (amount*exchangeRate, to);
    }
    
    

    private Component amount() {
        JTextField textField = new JTextField("100");
        textField.setColumns(10);
        textField.getDocument().addDocumentListener(amountChanged());
        amount= Double.parseDouble(textField.getText());
        return textField;
         
    }

    private Component currencyComponent(Consumer<Currency> saveSelection) {
        JComboBox comboBox = new JComboBox(currencies);
        comboBox.addItemListener(currencyChanged(saveSelection));
        saveSelection.accept((Currency) comboBox.getSelectedItem());
        return comboBox;
    }

    private ItemListener currencyChanged(Consumer<Currency> saveSelection) {
        return (ItemEvent e) -> {
            if(e.getStateChange()==ItemEvent.SELECTED){
                saveSelection.accept((Currency)e.getItem());
            }
        };
    }

    private DocumentListener amountChanged() {
        return new DocumentListener(){
            
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
                    
                    amount = Double.parseDouble( document.getText(0, document.getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(SwingMoneyDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        };
    }
    
}
