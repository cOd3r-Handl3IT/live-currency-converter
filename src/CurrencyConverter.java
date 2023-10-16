import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;
import java.io.*;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class CurrencyConverter 
{
	public static void main(String[] args) throws IOException
	{
		boolean running = true;
		do 
		{
			HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();						// map numbers to strings as currency codes
			
			currencyCodes.put(1,  "USD");																	// add currency code United States
			currencyCodes.put(2,  "CAD");																	// add currency code Canada
			currencyCodes.put(3,  "EUR");																	// add currency code European Union
			currencyCodes.put(4,  "HKD");																	// add currency code Hong Kong
			currencyCodes.put(5,  "INR");																	// add currency code India
			
			int from, to, runAgain;
			String fromCode, toCode;																		// letters of currency codes
			double amount;																					// amount to be converted
			
			
			Scanner userInput = new Scanner(System.in);
			

			System.out.println(" _     _   _      ____                                  \r\n"
					+ "| |   | | \\ \\  / | |_                                   \r\n"
					+ "|_|__ |_|  \\_\\/  |_|__                                  \r\n"
					+ " __    _     ___   ___   ____  _      __    _           \r\n"
					+ "/ /`  | | | | |_) | |_) | |_  | |\\ | / /`  \\ \\_/        \r\n"
					+ "\\_\\_, \\_\\_/ |_| \\ |_| \\ |_|__ |_| \\| \\_\\_,  |_|         \r\n"
					+ " __    ___   _      _      ____  ___  _____  ____  ___  \r\n"
					+ "/ /`  / / \\ | |\\ | \\ \\  / | |_  | |_)  | |  | |_  | |_) \r\n"
					+ "\\_\\_, \\_\\_/ |_| \\|  \\_\\/  |_|__ |_| \\  |_|  |_|__ |_| \\ \n\n");				// welcome screen
			
			
			System.out.println("1: US Dollars\t\t(USD) \n"
							+ "2: Canadian Dollars\t(CAD) \n"
							+ "3: EU Dollars\t\t(Euros/EUR) \n"
							+ "4: Hong Kong Dollars\t(HKD) \n"
							+ "5: Indian Rupees\t(INR) \n");
			System.out.print("What currency to convert FROM (1-5)? ");										// from currency sendHttpGETRequest
			from = userInput.nextInt();
			
			while(from < 1 || from > 5) 																	// check for invalid user input
			{
				System.out.println("\nTHAT'S NOT A VALID OPTION! Please enter a value from 1-5.\n\n");
				System.out.println("1: US Dollars\t\t(USD) \n"
						+ "2: Canadian Dollars\t(CAD) \n"
						+ "3: EU Dollars\t\t(Euros/EUR) \n"
						+ "4: Hong Kong Dollars\t(HKD) \n"
						+ "5: Indian Rupees\t(INR) \n");
				System.out.print("What currency to convert FROM (1-5)? ");									// from currency sendHttpGETRequest
				from = userInput.nextInt();
			}
			fromCode = currencyCodes.get(from);
			
			
			System.out.println("\n1: US Dollars\t\t(USD) \n"
					+ "2: Canadian Dollars\t(CAD) \n"
					+ "3: EU Dollars\t\t(Euros/EUR) \n"
					+ "4: Hong Kong Dollars\t(HKD) \n"
					+ "5: Indian Rupees\t(INR) \n");
			System.out.print("What currency to convert TO (1-5)? ");										// to currency sendHttpGETRequest
			to = userInput.nextInt();
			
			while(to < 1 || to > 5) 																		// check for invalid user input
			{
				System.out.println("\nTHAT'S NOT A VALID OPTION! Please enter a value from 1-5.\n\n");
				System.out.println("1: US Dollars\t\t(USD) \n"
						+ "2: Canadian Dollars\t(CAD) \n"
						+ "3: EU Dollars\t\t(Euros/EUR) \n"
						+ "4: Hong Kong Dollars\t(HKD) \n"
						+ "5: Indian Rupees\t(INR) \n");
				System.out.print("What currency to convert FROM (1-5)? ");									// from currency sendHttpGETRequest
				to = userInput.nextInt();
			}
			toCode = currencyCodes.get(to);
			
			System.out.print("\nAmount of " + fromCode + " you wish to convert to " + toCode + " : ");		// amount of currency to be converted
			amount = userInput.nextFloat();
			
			sendHttpGETRequest(fromCode, toCode, amount);													// call convert currency method
					
			System.out.println("\nWould you like to use the currency converter again?");					// prompt user to re-run
			System.out.println("1: YES\n2: NO ");
			runAgain = userInput.nextInt();
			while(runAgain != 1 && runAgain != 2) 															// check for invalid user input
			{
				System.out.println("\n\nTHAT'S NOT A VALID OPTION! Please enter a value from 1-2.\n");
				System.out.println("\nWould you like to use the currency converter again?");					// prompt user to re-run
				System.out.println("1: YES\n2: NO ");
				runAgain = userInput.nextInt();
			}
			switch (runAgain) 
			{
				case 1:
					running = true;
					break;
				case 2:
					running = false;
					break;
			}
		}
		while (running);
		
		System.out.println("\nThank you for using cOd3r-Handl3IT's Live Currency Converter!");				// branding exit message
	}
		
	
	public static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException // create HTTP get request
	{
		String GET_URL = "https://v6.exchangerate-api.com/v6/3fdf535928f07bea47ac38f7/pair/" + fromCode + "/" + toCode + "/" + amount;
		URL url = new URL(GET_URL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod("GET");
		int responseCode = httpURLConnection.getResponseCode();
		
		if(responseCode == HttpURLConnection.HTTP_OK) 														// check for success
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) 													// read data from response until finished
			{
				response.append(inputLine);
			}
			in.close();
			
			// System.out.println(response);																	// confirm returned data from URL (debugging)
			
			JSONObject jsonObject = new JSONObject(response.toString());
			double exchangeRate = jsonObject.getDouble("conversion_result");
			System.out.println("\n" + amount + " " + fromCode + " = " + exchangeRate + " " + toCode);
		}
		else 
		{
			System.out.println("GET request failed!");
		}
	}
}














