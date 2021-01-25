
package moneycalculator.model;

public class ExchangeRate {
    private final Currency from;
    private final Currency to;
    private final double rate;

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

    public ExchangeRate(Currency from, Currency to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }
    public ExchangeRate(String from, String to, double rate) {
        this.from = new Currency(from,"","");
        this.to = new Currency(to,"","");;
        this.rate = rate;
    }
    
    public ExchangeRate inverse(){
        return new ExchangeRate(to,from,1/rate);
    }
    
    
}
