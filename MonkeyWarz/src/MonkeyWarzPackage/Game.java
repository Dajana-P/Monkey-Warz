package MonkeyWarzPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.Toolkit;

public class Game extends JFrame {
	private	JLabel backgroundLabel;
	private static Game frame;
	public MainMenu menu;
	List<EntityThread> entities;

	public int BananaCount;
	public int MetalCount;
	public int MonkeyCount;
	public int KnightCount;
	public int MinerCount;
	public int ThreadCounter;
	public int SekundeOdPocetka;
	
	public int baseHealth;
	boolean KnightWeaponsPurchased;
	boolean FarmerEfficiencyPurchased;
	boolean MinerEfficiencyPurchased;
	
	
	public int MaxKnights;
	public int MaxFarmers;
	public int MaxMiners;
	public Spot[] MonkeySpots;
	public Spot[] KnightSpots;
	public Spot[] MinerSpots;
	public boolean IsCaveTaken;
	private JButton btnBuyKnight;
	private JButton btnBuyMiner;
	private JLabel lbMonkeys;
	private JLabel lbKnights;
	private JLabel lbMiners;
	private JLabel lbMetalPile;
	private JLabel lbBananaPile;
	private JButton btnUpgradeFarmerCap1;
	private JLabel lbUnits;
	private JLabel lbUpgrades;
	private JButton btnUpgradeFarmerCap2;
	private JButton btnUpgradeKnightCap1;
	private JButton btnUpgradeKnightCap2;
	private JButton btnPause;
	
	private boolean Paused;
	private JButton btnKnightWeapons;
	private JButton btnFarmerEfficiency;
	private JButton btnMinerCapacity;
	private JButton btnMinerEfficiency;
	private JLabel lbBananas;
	private JLabel lbMetal;
	private JLabel lbHealth;
	
	JSlider slider;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			//		frame = new Game(1,2,);
				//	frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Timer timerVreme=new Timer();
	TimerTask vreme=new TimerTask() {
		public void run() {
			if(Paused==false) {
			    int minute=SekundeOdPocetka/60;
			    String sMinute=String.valueOf(minute);
			    if(minute<10)
			    {
			    	sMinute="0"+sMinute;
			    }
			    int seconds=SekundeOdPocetka%60;
			    String sSeconds=String.valueOf(seconds);
			    if(seconds<10)
			    {
			    	sSeconds="0"+sSeconds;
			    }
			    
			    Random rand=new Random();
			    int summonRate=30;
			    if(SekundeOdPocetka>120)
			    	summonRate-=5;
			    if(SekundeOdPocetka>150)
			    	summonRate-=5;
			    if(SekundeOdPocetka>180)
			    	summonRate-=5;
			    if(SekundeOdPocetka%summonRate==0 && SekundeOdPocetka>=60)
			   {
			    	 
			    	 for(int i=0;i<SekundeOdPocetka/210+1;i++)
			    	 {
			    		 int ySpawnCoordinate=rand.nextInt(630);
			    		 while(ySpawnCoordinate >225 && ySpawnCoordinate<375)
			    			 ySpawnCoordinate=rand.nextInt(630);
			    		 CreateThread("Enemy","Enemy","Enemy","",930,ySpawnCoordinate);
			    	 }
			    }
			   
			    
			    lbTime.setText(sMinute+":"+sSeconds);
			    SekundeOdPocetka++; 
				
				
			}
				if(baseHealth<=0 && Paused==false)
				{
					Paused=true;
					ToggleButtons();
					timerVreme.purge();
					GameOverScreen gameOver=new GameOverScreen(menu);
					gameOver.show();
					frame.dispose();
					//System.exit(DISPOSE_ON_CLOSE);
				}
		}
	};
	private JLabel lbTime;
	private JButton btnBuyFarmer;

	public void CreateThread(String Name,String Type, String Image,String SecondaryImage, int xCoordinate,int yCoordinate)
	{
		JLabel Label=new JLabel();
		ImageIcon icon=new ImageIcon(Image+".png");
		Label.setIcon(icon);
		Label.setBounds(xCoordinate, yCoordinate, 64, 64);
  		backgroundLabel.add(Label);
		EntityThread e=new EntityThread(ThreadCounter,Name,Type,Image,SecondaryImage,xCoordinate,yCoordinate,backgroundLabel,Label,entities,KnightWeaponsPurchased,FarmerEfficiencyPurchased,MinerEfficiencyPurchased,this);
		entities.add(e);
		ThreadCounter++;
		e.start();
	}
	
	public void UpdatePiles()
	{
			try {
				if(BananaCount==0)
					lbBananaPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/EmptyPile.png"))));
				else if(BananaCount>0 && BananaCount<5)
					lbBananaPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Bananas_1.png"))));
				else if(BananaCount>=5 && BananaCount<10)
					lbBananaPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Bananas_2.png"))));
				else if(BananaCount>10)
					lbBananaPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Bananas_3.png"))));
				
				if(MetalCount==0)
					lbMetalPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/EmptyPile.png"))));
				else if(MetalCount>0 && MetalCount<5)
					lbMetalPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Metal_1.png"))));
				else if(MetalCount>=5 && MetalCount<10)
					lbMetalPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Metal_2.png"))));
				else if(MetalCount>10)
					lbMetalPile.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Metal_3.png"))));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void UpdateLabels()
	{
		lbMiners.setText("Miners: "+MinerCount+"/"+MaxMiners);
		lbKnights.setText("Knights: "+KnightCount+"/"+MaxKnights);
		lbMonkeys.setText("Farmers: "+MonkeyCount+"/"+MaxFarmers);
		lbBananas.setText("Bananas: "+BananaCount);
		lbMetal.setText("Metal: "+MetalCount);
		lbHealth.setText("HP: "+baseHealth);
		UpdatePiles();
	}
	 public void ToggleButtons()
     {
     	btnBuyKnight.setEnabled(!btnBuyKnight.isEnabled());
     	btnBuyMiner.setEnabled(!btnBuyMiner.isEnabled());
     	btnBuyFarmer.setEnabled(!btnBuyFarmer.isEnabled());
     }
	
	public Game(int startingBananas, int startingMetal,MainMenu menu) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/Slike/MonkeyIcon.png"))); 
		frame=this;
		
		this.menu=menu;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1290, 755);
		JPanel contentPane = new JPanel();
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		ImageIcon background=new ImageIcon();
		try {
			background = new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Background.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        contentPane.setBackground(new Color(21, 21, 21));
        contentPane.setLayout(null);
        backgroundLabel=new JLabel();
        
       
        backgroundLabel.setBounds(0, -1, 1000, 720);
        backgroundLabel.setIcon(background);
        contentPane.add(backgroundLabel);
        
        btnBuyFarmer = new JButton("Buy Farmer (1 banana)");
        btnBuyFarmer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=1 && MonkeyCount<MaxFarmers)
        		{
        			CreateThread("Monkey","Monkey","Monkey","MonkeyWithBanana",MapSpots.TentFarmer.coordinates.x,MapSpots.TentFarmer.coordinates.y);
        			BananaCount-=1;
        			MonkeyCount++;
        			UpdateLabels();
        		}
        	
        	}
        });
        
        try {
			ImageIcon bananaCost = new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/BananaIcon.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        JLabel lbBananaCost = new JLabel();
        try {
			lbBananaCost.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/BananaIcon.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        lbBananaCost.setBounds(0,0,32,32);
        
        JLabel lbMetalCost = new JLabel();
        try {
			lbMetalCost.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/MetalIcon.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        btnBuyFarmer.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnBuyFarmer.setBounds(1010, 223, 254, 48);
        btnBuyFarmer.setForeground(new Color(0, 0, 0));
        btnBuyFarmer.setBackground(new Color(212, 217, 219));
        contentPane.add(btnBuyFarmer);
        
        btnBuyKnight = new JButton("Buy Knight (2 bananas + 2 metal)");
        btnBuyKnight.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=2 && MetalCount>=2 && KnightCount<MaxKnights)
        		{
        		CreateThread("MonkeyKnight","MonkeyKnight","MonkeyKnight","MonkeyKnightSwordDown",MapSpots.Barracks.coordinates.x,MapSpots.Barracks.coordinates.y);
        		KnightCount++;
        		BananaCount-=2;
        		MetalCount-=2;
        		UpdateLabels();
        		}
        	}
        });
        btnBuyKnight.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnBuyKnight.setBounds(1010, 273, 254, 48);
        btnBuyKnight.setForeground(new Color(0, 0, 0));
        btnBuyKnight.setBackground(new Color(212, 217, 219));
        contentPane.add(btnBuyKnight);
        
        btnBuyMiner = new JButton("Buy Miner (5 bananas)");
        btnBuyMiner.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=5 && MinerCount<MaxMiners)
        		{
        			CreateThread("Miner","Miner","MonkeyMiner","MonkeyMinerWithMetal",MapSpots.TentMiner.coordinates.x,MapSpots.TentMiner.coordinates.y);
        			MinerCount++;
        			BananaCount-=5;
        			UpdateLabels();
        		}
        		
        	}
        });
        btnBuyMiner.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnBuyMiner.setBounds(1010, 323, 254, 48);
        btnBuyMiner.setForeground(new Color(0, 0, 0));
        btnBuyMiner.setBackground(new Color(212, 217, 219));
        contentPane.add(btnBuyMiner);
        
        lbBananas = new JLabel("Bananas: ");
        lbBananas.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbBananas.setForeground(Color.WHITE);
        lbBananas.setBounds(1010, 50, 142, 14);
        contentPane.add(lbBananas);
        
        lbMetal = new JLabel("Metal:");
        lbMetal.setForeground(Color.WHITE);
        lbMetal.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbMetal.setBounds(1010, 75, 142, 14);
        contentPane.add(lbMetal);
        
        lbTime = new JLabel("Time:");
        lbTime.setHorizontalAlignment(SwingConstants.CENTER);
        lbTime.setForeground(Color.WHITE);
        lbTime.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbTime.setBounds(1066, 10, 142, 14);
        contentPane.add(lbTime);
        
        lbMonkeys = new JLabel("Farmers:");
        lbMonkeys.setForeground(Color.WHITE);
        lbMonkeys.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbMonkeys.setBounds(1010, 100, 142, 14);
        contentPane.add(lbMonkeys);
        
        lbKnights = new JLabel("Knights:");
        lbKnights.setForeground(Color.WHITE);
        lbKnights.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbKnights.setBounds(1010, 125, 142, 14);
        contentPane.add(lbKnights);
        
        lbMiners = new JLabel("Miners:");
        lbMiners.setForeground(Color.WHITE);
        lbMiners.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbMiners.setBounds(1010, 150, 142, 14);
        contentPane.add(lbMiners);
        
        btnUpgradeFarmerCap1 = new JButton("Farmer Capacity 1 (5 bananas)");
        btnUpgradeFarmerCap1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=5)
        		{
        			MaxFarmers++;
            	    btnUpgradeFarmerCap1.setEnabled(false);
            		BananaCount-=5;
            		UpdateLabels();
        		}
        		
        	}
        });
        btnUpgradeFarmerCap1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnUpgradeFarmerCap1.setBounds(1001, 433, 272, 20);
        btnUpgradeFarmerCap1.setForeground(new Color(0, 0, 0));
        btnUpgradeFarmerCap1.setBackground(new Color(212, 217, 219));
        contentPane.add(btnUpgradeFarmerCap1);
        
        lbUnits = new JLabel("Monkeys");
        lbUnits.setHorizontalAlignment(SwingConstants.CENTER);
        lbUnits.setForeground(Color.WHITE);
        lbUnits.setFont(new Font("Tahoma", Font.BOLD, 19));
        lbUnits.setBounds(1066, 183, 142, 23);
        contentPane.add(lbUnits);
        
        lbUpgrades = new JLabel("Upgrades");
        lbUpgrades.setHorizontalAlignment(SwingConstants.CENTER);
        lbUpgrades.setForeground(Color.WHITE);
        lbUpgrades.setFont(new Font("Tahoma", Font.BOLD, 19));
        lbUpgrades.setBounds(1066, 393, 142, 23);
        lbUpgrades.setBackground(new Color(212, 217, 219));
        contentPane.add(lbUpgrades);
        
        btnUpgradeFarmerCap2 = new JButton("Farmer Capacity 2 (10 bananas)");
        btnUpgradeFarmerCap2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=10)
        		{
        			MaxFarmers++;
            		btnUpgradeFarmerCap2.setEnabled(false);
            		BananaCount-=10;
            		UpdateLabels();
        		}
        	}
        });
        btnUpgradeFarmerCap2.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnUpgradeFarmerCap2.setBounds(1001, 458, 272, 20);
        btnUpgradeFarmerCap2.setForeground(new Color(0,0,0));
        btnUpgradeFarmerCap2.setBackground(new Color(212, 217, 219));
        contentPane.add(btnUpgradeFarmerCap2);
        
        btnUpgradeKnightCap1 = new JButton("Knight Capacity 1 (5 bananas + 3 metal)");
        btnUpgradeKnightCap1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=5 && MetalCount>=3)
        		{
        			MaxKnights++;
            	    btnUpgradeKnightCap1.setEnabled(false);
            		BananaCount-=5;
            		MetalCount-=3;
            		UpdateLabels();
        		}
        	}
        });
        btnUpgradeKnightCap1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnUpgradeKnightCap1.setBounds(1001, 508, 272, 20);
        btnUpgradeKnightCap1.setForeground(new Color(0, 0, 0));
        btnUpgradeKnightCap1.setBackground(new Color(212, 217, 219));
        contentPane.add(btnUpgradeKnightCap1);
        
        btnUpgradeKnightCap2 = new JButton("Knight Capacity 2 (10 bananas + 5 metal)");
        btnUpgradeKnightCap2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=10 && MetalCount>=5)
        		{
        			MaxKnights++;
            		btnUpgradeKnightCap2.setEnabled(false);
            		BananaCount-=10;
            		MetalCount-=5;
            		UpdateLabels();
        		}
        	}
        });
        btnUpgradeKnightCap2.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnUpgradeKnightCap2.setBounds(1001, 533, 272, 20);
        btnUpgradeKnightCap2.setForeground(new Color(0, 0, 0));
        btnUpgradeKnightCap2.setBackground(new Color(212, 217, 219));
        contentPane.add(btnUpgradeKnightCap2);
        
        btnPause = new JButton("Pause");
        btnPause.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(Paused)
        		{
        			ToggleButtons();
        			btnPause.setText("Pause");
        			Paused=false;
        			for(EntityThread entity : entities)
            		{
            			entity.resume();
            		}
        		}
        		else
        		{
        			ToggleButtons();
        			btnPause.setText("Resume");
        			Paused=true;
        			for(EntityThread entity : entities)
            		{
            			entity.suspend();
            		}	
        		}
        		
        	}
        });
        btnPause.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnPause.setBounds(1183, 50, 80, 50);
        btnPause.setForeground(new Color(0, 0, 0));
        btnPause.setBackground(new Color(212, 217, 219));
        contentPane.add(btnPause);
        
        btnKnightWeapons = new JButton("Knight Weapons (3 metal)");
        btnKnightWeapons.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(MetalCount>=3)
        		{
        			KnightWeaponsPurchased=true;
        			for(EntityThread entity : entities)
        			{
        				entity.KnightsWeaponsPurchased=true;
        			}
        			btnKnightWeapons.setEnabled(false);
        			MetalCount-=3;
        			UpdateLabels();
        		}
        		
        	}
        });
        
        btnKnightWeapons.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnKnightWeapons.setBounds(1001, 558, 272, 20);
        btnKnightWeapons.setForeground(new Color(0, 0, 0));
        btnKnightWeapons.setBackground(new Color(212, 217, 219));
        contentPane.add(btnKnightWeapons);
        
        btnFarmerEfficiency = new JButton("Farmer Efficiency (5 bananas + 2 metal)");
        btnFarmerEfficiency.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=5 && MetalCount>=2)
        		{
        			FarmerEfficiencyPurchased=true;
        			btnFarmerEfficiency.setEnabled(false);
        			for(EntityThread entity : entities)
        			{
        				entity.FarmerEfficiencyPurchased=true;
        			}
        			BananaCount-=5;
        			MetalCount-=2;
        			UpdateLabels();
        		}
        	}
        });
        
        
        btnFarmerEfficiency.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnFarmerEfficiency.setBounds(1001, 483, 272, 20);
        btnFarmerEfficiency.setForeground(new Color(0, 0, 0));
        btnFarmerEfficiency.setBackground(new Color(212, 217, 219));
        contentPane.add(btnFarmerEfficiency);
        
        btnMinerCapacity = new JButton("Miner Capacity (10 bananas + 3 metal)");
        btnMinerCapacity.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(BananaCount>=10 && MetalCount>=3)
        		{
        			MaxMiners++;
        			btnMinerCapacity.setEnabled(false);
        			BananaCount-=10;
        			MetalCount-=3;
        			UpdateLabels();
        		}
        	}
        });
        btnMinerCapacity.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnMinerCapacity.setBounds(1001, 583, 272, 20);
        btnMinerCapacity.setForeground(new Color(0, 0, 0));
        btnMinerCapacity.setBackground(new Color(212, 217, 219));
        contentPane.add(btnMinerCapacity);
        
        btnMinerEfficiency = new JButton("Miner Efficiency (7 metal)");
        btnMinerEfficiency.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(MetalCount>=7)
        		{
        			MinerEfficiencyPurchased=true;
        			btnMinerEfficiency.setEnabled(false);
        			for(EntityThread entity : entities)
        			{
        				entity.MinerEfficiencyPurchased=true;
        			}
        			MetalCount-=7;
        			UpdateLabels();
        		}
        	}
        });
        btnMinerEfficiency.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnMinerEfficiency.setBounds(1001, 608, 272, 20);
        btnMinerEfficiency.setForeground(new Color(0, 0, 0));
        btnMinerEfficiency.setBackground(new Color(212, 217, 219));
        contentPane.add(btnMinerEfficiency);
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBack.setForeground(new Color(0, 0, 0));
		btnBack.setBackground(new Color(212, 217, 219));
		btnBack.setBounds(1184, 655, 80, 50);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		menu.ToggleMute(menu.slider);
        		menu.setVisible(true);
				frame.dispose();
        	}
        });
        
        lbHealth = new JLabel("HP:");
        lbHealth.setForeground(Color.WHITE);
        lbHealth.setFont(new Font("Tahoma", Font.BOLD, 22));
        lbHealth.setBounds(1183, 125, 142, 20);
        contentPane.add(lbHealth);
        
        Paused=false;
		entities=new ArrayList<EntityThread>();
		SekundeOdPocetka=0;
		BananaCount=startingBananas;
		MetalCount=startingMetal;
		MonkeyCount=0;
		KnightCount=0;
		MinerCount=0;
		ThreadCounter=0;
		MaxFarmers=2;
		MaxKnights=2;
		MaxMiners=1;
		baseHealth=30;
		IsCaveTaken=false;
		KnightWeaponsPurchased=false;
		FarmerEfficiencyPurchased=false;
		MinerEfficiencyPurchased=false;
		lbMetalPile=new JLabel();
		lbMetalPile.setBounds(MapSpots.MetalPile.coordinates.x,MapSpots.MetalPile.coordinates.y,64,64);
		backgroundLabel.add(lbMetalPile);
		lbBananaPile=new JLabel();
		lbBananaPile.setBounds(MapSpots.BananaPile.coordinates.x,MapSpots.BananaPile.coordinates.y,64,64);
		backgroundLabel.add(lbBananaPile);
		int sliderPosition = menu.vol;
		if(sliderPosition == -80) sliderPosition = -30;
		slider = new JSlider(-30, 0, sliderPosition);
		slider.setBounds(1010, 665, 164, 30);
		slider.setFont(new Font("Tahoma", Font.PLAIN, 9));
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				menu.vol=(slider.getValue());
				if(menu.vol==-30) menu.vol=-80;
				menu.ChangeVolume();
			}
		});
		slider.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		slider.setForeground(Color.BLACK);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		
		contentPane.add(slider);
		UpdateLabels();
		timerVreme.schedule(vreme, 0, 1000);
	}
}