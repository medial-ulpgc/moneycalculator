package moneycalculator.persistence.file;

import moneycalculator.persistence.FileSystemHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import moneycalculator.model.Currency;
import moneycalculator.model.ExchangeRate;
import moneycalculator.persistence.ExchangeRateLoader;

public class FileExchangeRateLoader implements ExchangeRateLoader {

    private final String fileName;
    private List<ExchangeRate> exchangeRates;
    private final boolean ignoreFirstLine;

    public FileExchangeRateLoader(String fileName, boolean ignoreFirstLine) {
        this.fileName = fileName;
        exchangeRates = new ArrayList();
        this.ignoreFirstLine = ignoreFirstLine;
    }

    public void load() {

        exchangeRates = FileSystemHandler
                .loadTextFile(fileName, this::textLineToExchangeRates, ignoreFirstLine)
                .collect(Collectors.toList());

    }

    private Stream<ExchangeRate> textLineToExchangeRates(Stream<String> lines) {
        return lines
                .map((entry) -> entry.split(";"))
                .filter(array -> array.length == 3)
                .map(array -> new ExchangeRate(array[0], array[1], Double.parseDouble(array[2])));
    }

    @Override
    public double getExchangeRate(Currency from, Currency to) {
        if(from.equals(to))return 1;
        return Stream.concat(directExchangeRate(from, to), inverseExchangeRate(from, to))
                .findAny()
                .orElseThrow(() -> new ExchangeRateNotFoundException(from + "=>" + to)).getRate();

    }

    private Stream<ExchangeRate> directExchangeRate(Currency from, Currency to) {
        return exchangeRates
                .stream()
                .filter(exchangeRate -> exchangeRate.getFrom().equals(from) && exchangeRate.getTo().equals(to))
                .limit(1);

    }

    private Stream<ExchangeRate> inverseExchangeRate(Currency from, Currency to) {
        return directExchangeRate(to, from)
                .map(ExchangeRate::inverse);
    }

    private ExchangeRate composeExchangeRate(Currency to, Currency from) {
        throw new UnsupportedOperationException("Not supported yet.");
        /*    Stream<ExchangeRate> filter = exchangeRates
            .stream()
            .filter(exchangeRate-> exchangeRate.getFrom().equals(from) || exchangeRate.getFrom().equals(to));
        Stream<ExchangeRate> filter2 = exchangeRates
            .stream()
            .filter(exchangeRate-> exchangeRate.getTo().equals(from) || exchangeRate.getTo().equals(to));
         */
    }

}
