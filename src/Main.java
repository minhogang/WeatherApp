import java.awt.*;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    public static void main(String[] args) throws EmptyOrInvalidArguments, Exception {
        String API_KEY = "de2fd82e32608db37b4964f69900f105";
/*        if (args.length == 0) {
            throw new EmptyOrInvalidArguments("No arguments were spectified");
        } else if (!args[0].equals("--current") && !args[0].equals("--fiveday")) {
            throw new EmptyOrInvalidArguments(String.format("Argument %s is invalid", args[0]));
        } else if (args[0].equals("--current")) {
            HTTPRequestCurrent(API_KEY);
        }*/
        HTTPRequestCurrent(API_KEY, false);
    }

    public void analyzeWeather(JsonObject obj) {
        int id = obj.get("id").getAsInt();
        String[] drizzleActivities = {"Explore a cave", "Watch movies"};
        String[] thunderstormActivities = {"Watch movies"};
        String[] rainActivities = {"Explore a cave", "Watch movies", "Use a dehumidifier"};
        String clearActivities[] = {"Go to beach", "Hiking"};
        if (id < 233)  {
            System.out.println("Weather Condition: Thunderstorm. It advised to stay inside");
        } else if (id < 322) {
            System.out.println("Weather Condition: Drizzle. Use an umbrella when going out.");
        } else if (id < 532) {
            System.out.println("Weather Condition: Rain");
        } else {
            System.out.println("Weather Condition: Clear. Hello, World!");
        }
    }

    private static void currentWeatherData(JsonObject obj) {
        JsonObject weatherSub = obj.getAsJsonArray("weather").get(0).getAsJsonObject();
        JsonObject mainSub = obj.getAsJsonObject("main");
        JsonObject windSub = obj.getAsJsonObject("wind");

        String weatherStatus = weatherSub.get("main").getAsString();
        String description = weatherSub.get("description").getAsString();
        double temperature = mainSub.get("temp").getAsDouble();
        double humidity = mainSub.get("humidity").getAsDouble();
        double windSpeed = windSub.get("speed").getAsDouble();
        System.out.printf("Weather: %s\n", weatherStatus);
        System.out.printf("Description: %s\n", description);
        System.out.printf("Temperature: %2f\n", temperature);
        System.out.printf("Humidity: %2f\n", humidity);
        System.out.printf("Wind Speed: %2f\n", windSpeed);
    }

    private static void fiveDayForecast(JsonObject obj) {
        JsonArray list = obj.get("list").getAsJsonArray();
        JFrame frame = new JFrame("5-Day Weather Forecast");
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        panel.setLayout(new GridLayout(6, 6));
        frame.setSize(1300, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (JsonElement data: list) {
            String title = data.getAsJsonObject().get("dt_txt").getAsString();
            JComponent comp = new GUIComponent(data);
            comp.setBorder(BorderFactory.createTitledBorder(title));
            panel.add(comp);
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void HTTPRequestCurrent(String API_KEY, boolean getCurrent) throws Exception {

        //This is the URL to send the request to with the given API key.
        String url;
        if (getCurrent) url = "http://api.openweathermap.org/data/2.5/weather?lat=13&lon=144&units=imperial&APPID=" + API_KEY;
        else url = "http://api.openweathermap.org/data/2.5/forecast?lat=13&lon=144&units=imperial&APPID=" + API_KEY;
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //Set request type and headers
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        System.out.println("Sending request...");
        //Send request and read buffered data
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //Parse the returned JSON with GSON package
        JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
        if (getCurrent) currentWeatherData(jsonObject);
        else fiveDayForecast(jsonObject);
    }
}

