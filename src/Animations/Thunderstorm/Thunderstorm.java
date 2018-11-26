package Thunderstorm;

//Purpose: Creates the thunderstorm

import java.awt.Color;
import java.awt.Graphics;

public class Thunderstorm
{
	private int velocity; // How fast the lightning strikes
	private int x, y; // The (x, y) positions of the lightning

	public Thunderstorm(int x, int v)
	{
		this.x = x;
		this.y = 0;
		this.velocity = v;
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.YELLOW);
		for (int i = 0; i < 30; i++)
		{
			int randomX = (int) (Math.random() * 31);
			int randomY = (int) (Math.random() * 51);
			g.drawLine(x, y, x - randomX, y + randomY);
			g.fillRect(x, y, randomX, randomY);
			x -= randomX + velocity;
			y += randomY + velocity;
			g.drawLine(x, y, x + randomX, y);
			x += 10 + velocity;	
		}
	}
}
