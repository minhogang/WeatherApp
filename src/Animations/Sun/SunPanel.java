package Sun;

//Purpose: Serves as the panel in the frame that contains the graphics

import java.awt.Graphics;

import javax.swing.JPanel;

public class SunPanel extends JPanel
{
	public SunPanel(int width, int height)
	{
		createPanel(width, height);
	}

	public void createPanel(int width, int height)
	{
		this.setSize(width, height);
	}

	public void paint(Graphics g)
	{
		Sun sun = new Sun(this.getWidth(), this.getHeight());
		sun.paint(g);
	}
}
