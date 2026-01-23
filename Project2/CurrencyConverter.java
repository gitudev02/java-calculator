import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {
        // Step 1: Define some sample exchange rates (relative to USD)
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);       // base
        rates.put("INR", 83.0);      // 1 USD = 83 INR (example rate)
        rates.put("EUR", 0.92);      // 1 USD = 0.92 EUR
        rates.put("GBP", 0.81);      // 1 USD = 0.81 GBP
        rates.put("JPY", 148.0);     // 1 USD = 148 JPY

        Scanner scanner = new Scanner(System.in);

        // Step 2: Get user input
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        System.out.print("From currency (USD, INR, EUR, GBP, JPY): ");
        String from = scanner.next().toUpperCase();

        System.out.print("To currency (USD, INR, EUR, GBP, JPY): ");
        String to = scanner.next().toUpperCase();

        // Step 3: Validate input
        if (!rates.containsKey(from) || !rates.containsKey(to)) {
            System.out.println("Unsupported currency!");
            return;
        }

        // Step 4: Convert
        double amountInUSD = amount / rates.get(from);   // convert to USD
        double converted = amountInUSD * rates.get(to);  // convert to target

        // Step 5: Show result
        System.out.printf("%.2f %s = %.2f %s%n", amount, from, converted, to);

        scanner.close();
    }
}
