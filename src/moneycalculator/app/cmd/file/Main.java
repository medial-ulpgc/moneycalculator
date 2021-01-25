package moneycalculator.app.cmd.file;

import java.util.Arrays;
import java.util.Scanner;
import moneycalculator.model.Currency;
import moneycalculator.persistence.file.FileCurrencyListLoader;
import moneycalculator.persistence.file.FileExchangeRateLoader;

public class Main {

    private Double amount;
    private Double convertedAmount;
    private String inputCurrency;
    private String outputCurrency;
    private FileExchangeRateLoader exchangeRateLoader;
    private Currency[] currencies;
    private Currency from;
    private Currency to;

    public static void main(String[] args) {
        new Main().execute();
    }

    private void execute() {
        load();
        input();
        process();
        output();
    }

    private void load() {
        exchangeRateLoader = new FileExchangeRateLoader("data\\ExchangeRates.txt", true);
        exchangeRateLoader.load();
        FileCurrencyListLoader currencyListLoader = new FileCurrencyListLoader("data\\Currencies.txt", true);
        currencies = currencyListLoader.currencies();
    }

    private void input() {
        //MoneyCalculatorFrame mcf = new MoneyCalculatorFrame(new Currency[]{});
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el código de la divisa origen: ");
        inputCurrency = scanner.next();
        System.out.println("Introduce una cantidad en " + inputCurrency + ": ");
        amount = Double.parseDouble(scanner.next());
        System.out.println("Introduce el código de la divisa destino : ");
        outputCurrency = scanner.next();
    }

    private void process() {
        from = fetchCurrency(inputCurrency);
        to = fetchCurrency(outputCurrency);
        double exchangeRate = exchangeRateLoader.getExchangeRate(from, to);
        convertedAmount = exchangeRate * amount;
    }

    private void output() {
        System.out.println(amount + from.getSymbol() + " se corresponden a " + convertedAmount + to.getSymbol() + ".");
    }

    private Currency fetchCurrency(String currencyCode) {
        return Arrays.stream(currencies)
                .filter(currency -> currency.getCode().equals(currencyCode))
                .findAny()
                .orElseThrow(() -> new CurrencyNotFoundException(currencyCode));
    }
}
