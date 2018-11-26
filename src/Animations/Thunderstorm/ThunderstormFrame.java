package Thunderstorm;

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

public class ThunderstormFrame extends JFrame implements ActionListener
{
	// Setting the values for the dimensions of the window
	private int WIDTH = 750;
	private int HEIGHT = 750;

	private Timer rainTimer = new Timer(10, this); // Used to refresh the animation

	public ThunderstormFrame() 
	{
		initializeFrame(); 
	}

	public void initializeFrame()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Thunderstorm");
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
		URL url = new URL("https://www.shockwave-sound.com/sound-effects/thunder-sounds/thunderstorm2.wav");
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.setLoopPoints(0, 80000);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		ThunderstormFrame thunderstormFrame = new ThunderstormFrame();
		thunderstormFrame.start();
	}

	public void paint(Graphics g)
	{
		ThunderstormPanel thunderstormPanel = new ThunderstormPanel(this.getWidth(),this.getHeight());
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		thunderstormPanel.paint(g);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

}