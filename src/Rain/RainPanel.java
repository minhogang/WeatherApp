// Purpose: Serves as the panel in the frame that contains the graphics

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class RainPanel extends JPanel
{
	// Settings the values for the dimensions of the panel
	private int WIDTH = 750;
	private int HEIGHT = 750;
	
	private ArrayList<RainDrop> rainDrops = new ArrayList<RainDrop>(); // An arraylist to hold the multiple x and y values as well as the velocities of the raindrops
	
	public RainPanel()
	{
		createPanel();
		createDrops();
	}
	
	public void createPanel()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.BLACK);
	}
	
	private void createDrops()
	{
		for (int i = 0; i < (this.getWidth() + this.getHeight()) / 2; i++) // Sets the upper limit of the number of raindrops to the average of the width and height so it will only show an appropriate amount
		{
			int randomX = (int) (Math.random() * (this.getWidth())); // Random x position anywhere along the current width
			int randomY = (int) (Math.random() * (this.getHeight())); // Random y position anywhere along the current height
			int randomV = (int) (Math.random() * 11); // Sets the velocity to any number from 0 - 10 
			rainDrops.add(new RainDrop(randomX, randomY, randomV)); // Creates a raindrop with these values and adds it into the arraylist
		}
	}
	
	private void changeDrops(int i) // For replacing a raindrop when it reaches the bottom
	{
		int randomX = (int) (Math.random() * (this.getWidth()));
		int randomY = (int) (Math.random() * 200);
		int randomV = (int) (Math.random() * 10);
		rainDrops.set(i, new RainDrop(randomX, randomY, randomV));
	}
	
	public void paint(Graphics g)
	{
		if (rainDrops.size() < (this.getHeight() + this.getWidth()) / 2) // If the number of raindrops is less than the average of the width and height, it will call the createDrops method
		{
			createDrops();
		}
		else if (rainDrops.size() > (this.getHeight() + this.getWidth()) / 2) // Resets if the number of raindrops is greater than the average
		{
			rainDrops.clear();
			createDrops();
		}
		
		for (int i = 0; i < rainDrops.size(); i++)
		{
			rainDrops.get(i).paint(g);
			
			if (rainDrops.get(i).y >= this.getHeight() - 15) // If the drop is near the bottom
			{
				RainSplash left = new RainSplash(rainDrops.get(i).x, rainDrops.get(i).y, false);
				RainSplash right = new RainSplash(rainDrops.get(i).x, rainDrops.get(i).y, true);
				left.paint(g);
				right.paint(g);				
			}
			
			if (rainDrops.get(i).y >= this.getHeight()) // Replaces the drops when it reaches the bottom
			{
				changeDrops(i);
			}
		}
	}
}
