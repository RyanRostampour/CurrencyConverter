import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class CurrencyConvert {
    private static final String API_KEY = "fbf9d8dcce68bbd23a1358c6";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the input currency (e.g., USD, CAD, EUR): ");
        String inputCurrency = sc.nextLine().toUpperCase();

        System.out.println("Enter the output currency (e.g., USD, CAD, EUR): ");
        String outputCurrency = sc.nextLine().toUpperCase();

        System.out.println("Enter the amount: ");
        double amount = sc.nextDouble();

        convertCurrency(inputCurrency, outputCurrency, amount);

        sc.close();
    }

    public static void convertCurrency(String inputCurrency, String outputCurrency, double amount) {
        try {

            URL url = new URL("https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + inputCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            inputStream.close();

            JsonObject rates = jsonObject.getJsonObject("conversion_rates");

            double exchangeRate = rates.getJsonNumber(outputCurrency).doubleValue();

            System.out.println("1 " + inputCurrency + " = " + exchangeRate + " " + outputCurrency);
            System.out.println(amount + " " + inputCurrency + " = " + (amount * exchangeRate) + " " + outputCurrency);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
