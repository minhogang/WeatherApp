import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

public class Main {

    public static void main(String[] args) throws Exception{
        HTTPRequest();
    }

    public static void HTTPRequest() throws Exception {
        String API_KEY = "de2fd82e32608db37b4964f69900f105";
        //This is the URL to send the request to with the given API key.
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=13&lon=144&units=imperial&APPID=" + API_KEY;
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
        JsonObject weatherSub = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        JsonObject mainSub = jsonObject.getAsJsonObject("main");
        JsonObject windSub = jsonObject.getAsJsonObject("wind");

        String weatherStatus = weatherSub.get("main").getAsString();
        String description = weatherSub.get("description").getAsString();
        double temperature = mainSub.get("temp").getAsDouble();
        double humidity = mainSub.get("humidity").getAsDouble();
        double windSpeed = windSub.get("speed").getAsDouble();
    }
}

