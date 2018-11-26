package Rain;

// Purpose: Serves as the window that will appear for the animation

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.Timer;

public class RainFrame extends JFrame implements ActionListener
{
	// Setting the values for the dimensions of the window
	private int WIDTH = 750;
	private int HEIGHT = 750;
	
	private Timer rainTimer = new Timer(5, this); // Used to refresh the animation

	public RainFrame() 
	{
		initializeFrame(); 
	}

	public void initializeFrame()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Rain");
		this.setBackground(Color.BLACK);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void start()
	{
		rainTimer.start();
	}

	public static void main(String[] args) throws Exception
	{	
		URL url = new URL("https://www.shockwave-sound.com/sound-effects/rain-sounds/heavyrainloop.wav");
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		RainFrame rainFrame = new RainFrame();
		rainFrame.start();
	}

	public void paint(Graphics g)
	{
		RainPanel rainPanel = new RainPanel(this.getWidth(),this.getHeight());
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		rainPanel.paint(g);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

}