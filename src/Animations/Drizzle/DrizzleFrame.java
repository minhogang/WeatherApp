package Drizzle;

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

public class DrizzleFrame extends JFrame implements ActionListener
{
	// Setting the values for the dimensions of the window
	private int WIDTH = 750;
	private int HEIGHT = 750;
	
	private Timer drizzleTimer = new Timer(5, this); // Used to refresh the animation

	public DrizzleFrame() 
	{
		initializeFrame(); 
	}

	public void initializeFrame()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Drizzle");
		this.setBackground(Color.BLACK);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void start()
	{
		drizzleTimer.start();
	}

	public static void main(String[] args) throws Exception
	{	
		URL url = new URL("https://www.shockwave-sound.com/sound-effects/rain-sounds/rain.wav");
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		DrizzleFrame drizzleFrame = new DrizzleFrame();
		drizzleFrame.start();
	}

	public void paint(Graphics g)
	{
		DrizzlePanel drizzlePanel = new DrizzlePanel(this.getWidth(),this.getHeight());
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		drizzlePanel.paint(g);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

}