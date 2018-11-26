package Sun;

// Purpose: Creates the sun and cloud

import java.awt.Color;
import java.awt.Graphics;

public class Sun
{
	private int width, height; // The (x, y) positions of the lightning

	public Sun(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public void paint(Graphics g)
	{
		int randomX = (int) (Math.random() * this.width / 2) - 50;
		int randomY = (int) (Math.random() * (this.height / 3)) + 10;
		g.setColor(Color.YELLOW);
		g.fillOval(this.width - (this.width / 5), - this.height / 5, this.width / 2, this.height / 2);
		g.setColor(Color.WHITE);
		g.fillOval(randomX,  randomY, this.width / 5, this.height / 5);
		g.fillOval(randomX + 110, randomY, this.width / 5, this.height / 5);
		g.fillOval(randomX - 90,  randomY + 50, this.width / 5, this.height / 5);
		g.fillOval(randomX - 10,  randomY + 80, this.width / 5, this.height / 5);
		g.fillOval(randomX + 120,  randomY + 70, this.width / 5, this.height / 5);
		g.fillOval(randomX + 180,  randomY + 20, this.width / 5, this.height / 5);
	}
}
