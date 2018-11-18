// Purpose: Creates the splash animation

import java.awt.Graphics;

public class RainSplash
{
	protected int x, y; // (x, y) positions
	protected Boolean Right; // Direction of the splash
	
	public RainSplash(int x, int y, boolean Right)
	{
		this.x = x;
		this.y = y;
		this.Right = Right;
	}
	
	public void paint(Graphics g)
	{
		if (Right == true)
		{
			g.drawLine(x, y, x + 5, y - 5);
			x = x + 10;
			y = y - 10;
		}
		else
		{
			g.drawLine(x, y, x - 5, y - 5);
			x = x - 10;
			y = y - 10;
		}
	}
}
