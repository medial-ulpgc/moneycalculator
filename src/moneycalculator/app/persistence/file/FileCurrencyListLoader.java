package moneycalculator.app.persistence.file;

import java.util.List;
import java.util.stream.Collectors;
import moneycalculator.view.CurrencyListLoader;
import java.util.stream.Stream;
import moneycalculator.model.Currency;

public class FileCurrencyListLoader implements CurrencyListLoader {

    private final String fileName;
    private final boolean ignoreFirstLine;

    public FileCurrencyListLoader(String fileName, boolean ignoreFirstLine) {
        this.fileName = fileName;
        this.ignoreFirstLine = ignoreFirstLine;
    }

    @Override
    public Currency[] currencies() {

        List<Currency> collect = FileSystemHandler
                .loadTextFile(fileName, this::toCurrencies, ignoreFirstLine)
                .collect(Collectors.toList());
        return collect.toArray(new Currency[collect.size()]);

    }

    private Stream<Currency> toCurrencies(Stream<String> stream) {
        return stream
                .map((entry) -> entry.split(";"))
                .filter(array -> array.length == 3)
                .map(array -> new Currency(array[0], array[1], array[2]));
    }

}
