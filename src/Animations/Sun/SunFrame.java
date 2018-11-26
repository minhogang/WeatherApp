package Sun;

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

public class SunFrame extends JFrame implements ActionListener
{
	// Setting the values for the initial dimensions of the window
	private int WIDTH = 750;
	private int HEIGHT = 750;
	
	private Timer sunTimer = new Timer(1000, this); // Used to refresh the animation

	public SunFrame() 
	{
		initializeFrame(); 
	}

	public void initializeFrame()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Sun");
		this.setBackground(Color.CYAN);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void start()
	{
		sunTimer.start();
	}

	public static void main(String[] args) throws Exception
	{
		SunFrame sunFrame = new SunFrame();
		URL url = new URL("https://www.shockwave-sound.com/sound-effects/windchimes-sounds/night.wav");
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		sunFrame.start();
	}

	public void paint(Graphics g)
	{
		SunPanel sunPanel = new SunPanel(this.getWidth(), this.getHeight());
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
		sunPanel.paint(g);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

}