package moneycalculator;
import java.util.Scanner;

public class MoneyCalculator {
    private Double amount = 0.;
    private Double convertedAmount=0.;
    
    public static void main(String[] args)  {
        new MoneyCalculator().execute();
    }
    private static double getExchangeRate(String from, String to) {
                return 0.86;
     
    }
    private void execute(){
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduce una cantidad en dólares: ");
        Scanner scanner = new Scanner(System.in);
        amount = Double.parseDouble(scanner.next());
    }

    private void output() {
        System.out.println( amount+ " dólares se corresponden a "+ convertedAmount+ " euros.");
    }

    private void process() {
        double exchangeRate = getExchangeRate("USD","EUR");
        convertedAmount = exchangeRate*amount;
    }
    
}
