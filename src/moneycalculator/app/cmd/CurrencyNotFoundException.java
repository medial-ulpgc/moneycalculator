package moneycalculator.app.cmd;

public class CurrencyNotFoundException extends RuntimeException{

    public CurrencyNotFoundException(String string) {
        super(string);
    }
    
}
