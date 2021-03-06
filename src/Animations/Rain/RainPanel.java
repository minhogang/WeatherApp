package Rain;

// Purpose: Serves as the panel in the frame that contains the graphics

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class RainPanel extends JPanel
{
	private ArrayList<Rain> raindrops = new ArrayList<Rain>(); // An arraylist to hold the multiple x and y values as well as the velocities of the raindrops

	public RainPanel(int width, int height)
	{
		createPanel(width, height);
		createDrops();
	}

	public void createPanel(int width, int height)
	{
		this.setSize(width, height);
	}

	private void createDrops()
	{
		for (int i = 0; i < (this.getWidth() + this.getHeight()) / 2; i++) // Sets the upper limit of the number of raindrops to the average of the width and height so it will only show an appropriate amount
		{
			int randomX = (int) (Math.random() * (this.getWidth())); // Random x position anywhere along the current width
			int randomY = (int) (Math.random() * (this.getHeight())); // Random y position anywhere along the current height
			int randomV = (int) (Math.random() * 11); // Sets the velocity to any number from 0 - 10 
			raindrops.add(new Rain(randomX, randomY, randomV)); // Creates a raindrop with these values and adds it into the arraylist
		}
	}

	private void changeDrops(int i) // For replacing a raindrop when it reaches the bottom
	{
		int randomX = (int) (Math.random() * (this.getWidth()));
		int randomY = (int) (Math.random() * 100);
		int randomV = (int) (Math.random() * 11);
		raindrops.set(i, new Rain(randomX, randomY, randomV));
	}

	public void paint(Graphics g)
	{
		if (raindrops.size() < (this.getHeight() + this.getWidth()) / 2) // If the number of raindrops is less than the average of the width and height, it will call the createDrops method
		{
			createDrops();
		}
		else if (raindrops.size() > (this.getHeight() + this.getWidth()) / 2) // Resets if the number of raindrops is greater than the average
		{
			raindrops.clear();
			createDrops();
		}

		for (int i = 0; i < raindrops.size(); i++)
		{
			raindrops.get(i).paint(g);

			if (raindrops.get(i).y >= this.getHeight() - 15) // If the drop is near the bottom
			{
				RainSplash left = new RainSplash(raindrops.get(i).x, raindrops.get(i).y, false);
				RainSplash right = new RainSplash(raindrops.get(i).x, raindrops.get(i).y, true);
				left.paint(g);
				right.paint(g);				
			}

			if (raindrops.get(i).y > this.getHeight()) // Replaces the drops when it reaches the bottom
			{
				changeDrops(i);
			}
		}
	}
}
