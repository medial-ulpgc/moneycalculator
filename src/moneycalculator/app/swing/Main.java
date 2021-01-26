package moneycalculator.app.swing;

import java.util.Arrays;
import moneycalculator.app.cmd.file.CurrencyNotFoundException;
import moneycalculator.model.Currency;
import moneycalculator.persistence.file.FileCurrencyListLoader;
import moneycalculator.persistence.file.FileExchangeRateLoader;
import moneycalculator.ui.swing.MoneyCalculatorFrame;

public class Main {

    private FileExchangeRateLoader exchangeRateLoader;
    private Currency[] currencies;

    public static void main(String[] args) {
        new Main().execute();
    }

    private void execute() {
        load();
        input();

    }

    private void load() {
        exchangeRateLoader = new FileExchangeRateLoader("data\\ExchangeRates.txt", true);
        exchangeRateLoader.load();
        FileCurrencyListLoader currencyListLoader = new FileCurrencyListLoader("data\\Currencies.txt", true);
        currencies = currencyListLoader.currencies();
    }

    private void input() {
        MoneyCalculatorFrame mcf = new MoneyCalculatorFrame(currencies, exchangeRateLoader);

    }

    private Currency fetchCurrency(String currencyCode) {
        return Arrays.stream(currencies)
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findAny()
                .orElseThrow(() -> new CurrencyNotFoundException(currencyCode));
    }
}
