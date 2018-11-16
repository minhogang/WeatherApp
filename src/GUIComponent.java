import com.google.gson.*;
import javax.swing.*;
import java.awt.*;

public class GUIComponent extends JComponent {
    private JsonElement data;
    GUIComponent(JsonElement data){
        this.data = data;
    }
    @Override
    public void paintComponent(Graphics g) {
        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            String temperature = this.data.getAsJsonObject().getAsJsonObject("main").get("temp").getAsString();
            String weather = this.data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
            String description = this.data.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            String windSpeed = this.data.getAsJsonObject().getAsJsonObject("wind").get("speed").getAsString();
            String humidity = this.data.getAsJsonObject().getAsJsonObject("main").get("humidity").getAsString();
            g2.drawString("Temperature: " + temperature, 10, 90);
            g2.drawString("Weather: " + weather, 10, 105);
            g2.drawString("Description: " + description, 10, 120);
            g2.drawString("Wind Speed: " + windSpeed, 10, 135);
            g2.drawString("Humidity: " + humidity, 10, 150);
        }
    }
}
