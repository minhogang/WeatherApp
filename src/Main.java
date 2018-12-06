import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import au.com.bytecode.opencsv.CSVWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Drizzle.DrizzleFrame;
import Rain.RainFrame;
import Sun.SunFrame;
import Thunderstorm.ThunderstormFrame;

public class Main extends JFrame {
	public static void main(String[] args) throws EmptyOrInvalidArguments, Exception {
		String API_KEY = "de2fd82e32608db37b4964f69900f105";
		//Use the --export argument to export a 5-day weather forecast to a CSV file.
		if (args.length >= 1) {
			if (!args[0].equals("--export")) {
				throw new EmptyOrInvalidArguments("You have entered invalid arguments: Valid arguments are: --export");
			} else if (args[0].equals("--export")) {
				exportToCSV(API_KEY);
			}
		}
		HTTPRequest(API_KEY, false);
		System.out.println("Would you like to know the weather condition right now?");
		Scanner input = new Scanner(System.in);
		if (input.next().equals("yes")) {HTTPRequest(API_KEY, true);}
		input.close();
	}

	private static void currentWeatherData(JsonObject obj) throws Exception
	{
		JsonObject weatherSub = obj.getAsJsonArray("weather").get(0).getAsJsonObject();
		JsonObject mainSub = obj.getAsJsonObject("main");
		JsonObject windSub = obj.getAsJsonObject("wind");

		String weatherStatus = weatherSub.get("main").getAsString();
		String description = weatherSub.get("description").getAsString();
		double temperature = mainSub.get("temp").getAsDouble();
		double humidity = mainSub.get("humidity").getAsDouble();
		double windSpeed = windSub.get("speed").getAsDouble();

		if (description.contains("clouds") || description.contains("clear"))
		{
			SunFrame sunFrame = new SunFrame();
			URL url = new URL("https://www.shockwave-sound.com/sound-effects/windchimes-sounds/night.wav");
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			sunFrame.start();
		}
		else if (description.contains("light rain") || description.contains("drizzle"))
		{
			URL url = new URL("https://www.shockwave-sound.com/sound-effects/rain-sounds/rain.wav");
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			DrizzleFrame drizzleFrame = new DrizzleFrame();
			drizzleFrame.start();
		}
		else if (description.contains("rain"))
		{
			URL url = new URL("https://www.shockwave-sound.com/sound-effects/rain-sounds/heavyrainloop.wav");
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			RainFrame rainFrame = new RainFrame();
			rainFrame.start();
		}
		else if (description.contains("thunderstorm"))
		{
			URL url = new URL("https://www.shockwave-sound.com/sound-effects/thunder-sounds/thunderstorm2.wav");
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.setLoopPoints(0, 80000);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			ThunderstormFrame thunderstormFrame = new ThunderstormFrame();
			thunderstormFrame.start();
		}
		System.out.printf("Weather: %s\n", weatherStatus);
		System.out.printf("Description: %s\n", description);
		System.out.printf("Temperature: %.2f\n", temperature);
		System.out.printf("Humidity: %.2f\n", humidity);
		System.out.printf("Wind Speed: %.2f\n", windSpeed);
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
			String title = data.getAsJsonObject().get("dt_txt").getAsString(); //date and time of forecast
			JComponent comp = new GUIComponent(data);
			comp.setBorder(BorderFactory.createTitledBorder(title));
			panel.add(comp);
		}
		frame.add(panel);
		frame.setVisible(true);
	}

	private static void HTTPRequest(String API_KEY, boolean getCurrent) throws Exception {

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
	private static void exportToCSV(String API_KEY) throws Exception {
		String url = "http://api.openweathermap.org/data/2.5/forecast?lat=13&lon=144&units=imperial&APPID=" + API_KEY;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		System.out.println("Exporting to CSV...");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
		JsonArray list = jsonObject.get("list").getAsJsonArray();
		String fileName = "Weather_Forecast_Logs.csv";
		//Write to CSV
		File file = new File(fileName);
		FileWriter outputFile = new FileWriter(file, true); //append: true; to append text at the end of the file
		CSVWriter writer = new CSVWriter(outputFile);
		String[] header = {"Date", "Time", "Temperature", "Weather", "Description", "Wind Speed", "Humidity"};
		writer.writeNext(header);
		for (JsonElement data: list) {
			String title = data.getAsJsonObject().get("dt_txt").getAsString();
			String[] datetime = title.split(" ");
			String date = datetime[0];
			String time = datetime[1];
			String temperature = data.getAsJsonObject().getAsJsonObject("main").get("temp").getAsString();
			String weather = data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
			String description = data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
			String windSpeed = data.getAsJsonObject().getAsJsonObject("wind").get("speed").getAsString();
			String humidity = data.getAsJsonObject().getAsJsonObject("main").get("humidity").getAsString();
			writer.writeNext(new String[]{date, time, temperature, weather, description, windSpeed, humidity}); //write a line to the CSV file
		}
		writer.close();
	}
}