package moneycalculator;
import java.util.Scanner;

public class MoneyCalculator {

    public static void main(String[] args)  {
        System.out.println("Introduce una cantidad en dólares: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        double exchangeRate = getExchangeRate("USD","EUR");
        System.out.println("" + exchangeRate * amount );
    }
    private static double getExchangeRate(String from, String to) {
                return 0.86;
        
    }
    
}
