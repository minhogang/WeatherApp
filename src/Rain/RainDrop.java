// Purpose: Creates the raindrops

import java.awt.Graphics;

public class RainDrop
{
	protected int velocity; // How fast the raindrops fall
	protected int x, y; // The (x, y) positions of the raindrops
	protected int length = 10; // The size of the raindrops
	
	public RainDrop(int x, int y, int v)
	{
		this.velocity = v;
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g)
	{
		g.drawLine(x, y, x, y + length);
		y = y + length + velocity;
	}
}

