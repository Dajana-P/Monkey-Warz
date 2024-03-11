package MonkeyWarzPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	public Clip clip;
	public int vol;
	private JPanel contentPane;
	public MainMenu frame;
	public JSlider slider;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    MainMenu frame = new MainMenu();
				    frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		frame=this;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/Slike/MonkeyIcon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 759);
		setTitle("MonkeyWarz");
		contentPane = new JPanel();
		setContentPane(contentPane);
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/Slike/MainMenu.png")));
		contentPane.setLayout(null);
		JLabel bg = new JLabel();
		bg.setBounds(0, 0, 1280, 720);
		bg.setIcon(icon);
		contentPane.add(bg);
		ImageIcon title = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/Slike/Title.png")));
		JLabel lbTitle = new JLabel(title);
		lbTitle.setBounds(912, 145, 255, 70);
		bg.add(lbTitle);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game=new Game(1,2,frame);
				frame.ToggleMute(slider);
				frame.setVisible(false);
				game.show();
			}
		});
		btnStart.setLocation(new Point(2, 0));
		btnStart.setForeground(new Color(0, 0, 0));
		btnStart.setBackground(new Color(212, 217, 219));
		btnStart.setIcon(new ImageIcon(MainMenu.class.getResource("/Slike/MonkeyKnight.png")));
		btnStart.setBounds(939, 280, 201, 50);
		bg.add(btnStart);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setForeground(new Color(0, 0, 0));
		btnExit.setBackground(new Color(212, 217, 219));
		btnExit.setIcon(new ImageIcon(MainMenu.class.getResource("/Slike/Enemy.png")));
		btnExit.setBounds(939, 540, 201, 50);
		bg.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
        	}
        });
		
		JButton btnBoost = new JButton("BOOST");
		btnBoost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game=new Game(30,30,frame);
				frame.ToggleMute(slider);
				frame.setVisible(false);
				game.show();
			}
		});
		
		bg.add(btnBoost);
		btnBoost.setForeground(new Color(0, 0, 0));
		btnBoost.setBackground(new Color(212, 217, 219));
		btnBoost.setIcon(new ImageIcon(MainMenu.class.getResource("/Slike/MonkeyWithBanana.png")));
		btnBoost.setBounds(939, 360, 201, 50);
		
		slider = new JSlider(-30, 0, 0);
		bg.add(slider);
		slider.setBounds(939, 450, 201, 30);
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				vol=(slider.getValue());
				if(vol==-30) vol=-80;
				ChangeVolume();
			}
		});
		slider.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		slider.setForeground(Color.BLACK);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		try {
			Music();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void Music() throws Exception {
		URL url=getClass().getResource("/Muzika/indian-music-with-sitar-tanpura-and-sarangi-74577.wav");
        AudioInputStream muzika = AudioSystem.getAudioInputStream(url);
        clip=AudioSystem.getClip();
        clip.open(muzika);
        clip.setFramePosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void ChangeVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(vol);
	}
	
	public void ToggleMute(JSlider slider) {
        clip.setFramePosition(0);
        slider.setValue(vol);
	}
}
