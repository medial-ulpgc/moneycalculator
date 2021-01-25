package moneycalculator.app.cmd.file;

public class CurrencyNotFoundException extends RuntimeException{

    public CurrencyNotFoundException(String string) {
        super(string);
    }
    
}
