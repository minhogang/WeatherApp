import com.google.gson.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GUIComponent extends JComponent {
    private JsonElement data;
    GUIComponent(JsonElement data){
        this.data = data;
    }

    //Fetch image based on the weather code
    private BufferedImage loadImage(int code){
        URL imagePath;
        if (code == 800) imagePath = getClass().getResource("assets/sunny.png");
        else if (code > 800) imagePath = getClass().getResource("assets/partlySunny.png");
        else if (code >= 500 && code <= 531) imagePath = getClass().getResource("assets/rain.png");
        else if (code >= 300 && code <= 321) imagePath = getClass().getResource("assets/drizzle.png");
        else if (code <= 232 && code >= 200) imagePath = getClass().getResource("assets/thunderstorm.png");
        else imagePath = getClass().getResource("assets/cloud.png");
        BufferedImage result = null;
        try {
            result = ImageIO.read(imagePath);
        } catch (IOException e) {
            System.out.println("Image url not found");
        }
        return result;
    }

    @Override
    public void paintComponent(Graphics g) {
        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("TimesRoman", Font.BOLD, 12));

            int weatherCode = this.data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsInt();
            BufferedImage background = loadImage(weatherCode);
            g2.drawImage(background, 0, 0, 200, 150, null);

            String temperature = this.data.getAsJsonObject().getAsJsonObject("main").get("temp").getAsString();
            String weather = this.data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
            String description = this.data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            String windSpeed = this.data.getAsJsonObject().getAsJsonObject("wind").get("speed").getAsString();
            String humidity = this.data.getAsJsonObject().getAsJsonObject("main").get("humidity").getAsString();
            //Draw the data for each card
            g2.drawString("Temperature: " + temperature, 10, 30);
            g2.drawString("Weather: " + weather, 10, 45);
            g2.drawString("Description: " + description, 10, 60);
            g2.drawString("Wind Speed: " + windSpeed, 10, 75);
            g2.drawString("Humidity: " + humidity, 10, 90);
        }
    }
}