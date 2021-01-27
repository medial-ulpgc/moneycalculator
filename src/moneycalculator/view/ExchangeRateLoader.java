package moneycalculator.view;

import moneycalculator.model.Currency;

public interface ExchangeRateLoader {
    double getExchangeRate(Currency from, Currency to);
    
    
}
