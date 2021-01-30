package moneycalculator.app.swing;

import moneycalculator.model.Currency;
import moneycalculator.app.persistence.file.FileCurrencyListLoader;
import moneycalculator.app.persistence.file.FileExchangeRateLoader;

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
}
