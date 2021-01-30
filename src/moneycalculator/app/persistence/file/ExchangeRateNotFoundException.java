
package moneycalculator.app.persistence.file;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String string) {
        super(string);
    }
    
}
