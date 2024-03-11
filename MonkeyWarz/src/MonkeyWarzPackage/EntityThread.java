
package MonkeyWarzPackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EntityThread extends Thread {
	public double Efficiency;
	public String name;
	public String type;
	public String imageName;
	public int Width;
	public int Heigth;
	public int xCoordinate;
	public int yCoordinate;
	public int ThreadID;
	public Game game;
	public BufferedImage imageG;
	public BufferedImage secondaryImage;
	Spot post;
	public JLabel label;
	public boolean alive;
	public boolean facingRight;
	public JTextField textField;
	public List<EntityThread> entities;
	int speed;
	int damage;
	int health;
	int attackSpeed;
	int currentAttackSpeed;
	
	boolean Slept;
	boolean IsOutside;
	boolean JobComplete;
	boolean IsFighting;
	boolean IsFarming;
	boolean JustSpawned;
	Point Destination;
	
	boolean KnightsWeaponsPurchased;
	boolean FarmerEfficiencyPurchased;
	boolean MinerEfficiencyPurchased;
	
	public EntityThread(int ThreadID,String name,String type,String Image,String secondaryImage,int xCoordinate,int yCoordinate,JLabel contentPane,JLabel monkeLabel,List<EntityThread> entities,boolean KnightsWeapons,boolean FarmerEfficiency,boolean MinerEfficiency,Game game)
	{
		super();
		this.game=game;
		speed=1;
		attackSpeed=500;
		currentAttackSpeed=0;
		FarmerEfficiencyPurchased=FarmerEfficiency;
		MinerEfficiencyPurchased=MinerEfficiency;
		KnightsWeaponsPurchased=KnightsWeapons;
		Efficiency=0;
		health=5;
		damage=1;
		if(type=="Enemy")health=3;
		this.ThreadID=ThreadID;
		this.name=name;
		this.type=type;
		this.imageName=Image;
		this.xCoordinate=xCoordinate;
		this.yCoordinate=yCoordinate;
		this.entities=entities;
		alive=true;
		IsOutside=false;
		IsFighting=false;
		JobComplete=false;
		Slept=false;
		JustSpawned=true;
		IsFarming=false;
		facingRight=true;
		Spot post=null;
		
		JLabel newLabel=new JLabel();
		newLabel.setBounds(xCoordinate,yCoordinate,64,64);
		//newLabel.setIcon(icon);
		contentPane.add(newLabel);
		label=monkeLabel;
		
		try {
			imageG=ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/"+imageName+".png"));
			if(secondaryImage!="")
			{
				this.secondaryImage=ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/"+secondaryImage+".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setIcon(new ImageIcon(imageG));
	
	}
	
	@Override
	public void run()
	{
		while(alive) {
		if(type=="Monkey")
		{
			if(!JobComplete)
			{
				if(IsFarming==false)
				{
					if(xCoordinate==MapSpots.TentFarmer.coordinates.x && yCoordinate==MapSpots.TentFarmer.coordinates.y)
					{
						Destination=MapSpots.FarmPath1.coordinates;
					}
					else if(xCoordinate==MapSpots.FarmPath1.coordinates.x && yCoordinate==MapSpots.FarmPath1.coordinates.y)
					{
						Destination=MapSpots.FarmPath2.coordinates;
					}
					else if(xCoordinate==MapSpots.FarmPath2.coordinates.x && yCoordinate==MapSpots.FarmPath2.coordinates.y)
					{
						if(post==null)
						{
							for(Spot spot : MapSpots.BananaPlantations)
							{
								if(spot.IsTaken==false)
								{
									spot.IsTaken=true;
									post=spot;
									
									Destination=post.coordinates;
									break;
								}
							}
						}
					}
					else if(post!=null)
					{
						if(xCoordinate==post.coordinates.x && yCoordinate==post.coordinates.y)
						{
							JobComplete=true;
							JustSpawned=false;
							try {
									if(FarmerEfficiencyPurchased)
										Efficiency=35;
									Thread.sleep(Math.round(3500 * (1 - Efficiency/100)));
								
									
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			else
			{
				
				if(post!=null)
				if(xCoordinate==post.coordinates.x && yCoordinate==post.coordinates.y)
				{
					post.IsTaken=false;
					post=null;
					ImageIcon img=new ImageIcon(secondaryImage);
					label.setIcon(img);
					Destination=MapSpots.FarmPath2.coordinates;
				}
				if(xCoordinate==MapSpots.FarmPath2.coordinates.x && yCoordinate==MapSpots.FarmPath2.coordinates.y)
				{
					Destination=MapSpots.FarmPath1.coordinates;
				}
				else if(xCoordinate==MapSpots.FarmPath1.coordinates.x && yCoordinate==MapSpots.FarmPath1.coordinates.y)
				{
					Destination=MapSpots.TentFarmer.coordinates;
				}
				else if(xCoordinate==MapSpots.TentFarmer.coordinates.x && yCoordinate==MapSpots.TentFarmer.coordinates.y)
				{
					JobComplete=false;
					if(JustSpawned==false)
					{
						game.BananaCount++;
						game.UpdateLabels();
						ImageIcon img=new ImageIcon(imageG);
						label.setIcon(img);
					}
					else {
						JustSpawned=false;
					}
				}
			}
			moveTowards(Destination);
		}
		if(type=="Miner")
		{
			if(JobComplete)
			{
				
				if(xCoordinate==MapSpots.MineEnterance.coordinates.x && yCoordinate==MapSpots.MineEnterance.coordinates.y)
				{
					label.setVisible(true);
					game.IsCaveTaken=false;
				}
				moveTowards(MapSpots.TentMiner.coordinates);
			}
			if(xCoordinate==MapSpots.TentMiner.coordinates.x && yCoordinate==MapSpots.TentMiner.coordinates.y && JustSpawned==false)
			{
				JobComplete=false;
				game.MetalCount++;
				game.UpdateLabels();
			}
			if(game.IsCaveTaken==false && JobComplete==false)
			{
				moveTowards(MapSpots.MineEnterance.coordinates);
			}
			else if(game.IsCaveTaken==true && JobComplete==false)
			{
			
				moveTowards(MapSpots.Mine.coordinates);
			}
			else if(game.IsCaveTaken==true && JobComplete==false)
			{
				moveTowards(MapSpots.Mine.coordinates);
			}
			if(xCoordinate==MapSpots.MineEnterance.coordinates.x && yCoordinate==MapSpots.MineEnterance.coordinates.y)
			{
				label.setVisible(false);
				game.IsCaveTaken=true;
				JobComplete=true;
				JustSpawned=false;
				try {
					if(MinerEfficiencyPurchased)
						Efficiency=35;
					Thread.sleep(Math.round(6000 * (1 - Efficiency/100)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(type=="Enemy")
		{
			boolean AmIFighting=false;
			moveTowards(new Point(MapSpots.GateEnterance.coordinates.x-30,yCoordinate));
			double tempDistance=Double.MAX_VALUE;
			for(EntityThread entity : entities) 
			{
				EntityThread enemy=null;
				double distance=getDistance(new Point(entity.xCoordinate,entity.yCoordinate));
				if(distance<tempDistance && distance<10 && entity.type=="MonkeyKnight" && entity.alive)
				{
					tempDistance=distance;
					enemy=entity;
				}
				if(enemy!=null)
				{
					Attack(enemy);
				}
			}
			if(getDistance(new Point(MapSpots.GateEnterance.coordinates.x-30,yCoordinate))<10) {
				if(currentAttackSpeed==0)	
				{
					game.baseHealth--;
					game.UpdateLabels();
					currentAttackSpeed=attackSpeed;
				}
				else
				{
					currentAttackSpeed-=10;
				}
			}	
		}
		else if(type=="MonkeyKnight")
		{
			if(IsOutside==false)
			{
				if(xCoordinate==MapSpots.Barracks.coordinates.x && yCoordinate==MapSpots.Barracks.coordinates.y)
				Destination=new Point(MapSpots.GateExit.coordinates);
				
				if(xCoordinate==MapSpots.GateExit.coordinates.x && yCoordinate==MapSpots.GateExit.coordinates.y)
				{
					Destination=new Point(MapSpots.GateEnterance.coordinates.x,MapSpots.GateEnterance.coordinates.y);
				}
				if(xCoordinate==MapSpots.GateEnterance.coordinates.x && yCoordinate==MapSpots.GateEnterance.coordinates.y)
				{
					IsOutside=true;
				}
				
				if(Destination!=null)
				moveTowards(Destination);
			}
			else
			{
				boolean ShouldIChase=false;
				for(EntityThread entity : entities) {
					if(entity.type=="Enemy" && entity.alive)
					{
					     
						 ShouldIChase=true;
					}
				}
				if(ShouldIChase)
				{
					if(post!=null)
					{
						post.IsTaken=false;
						post=null;
					}
					moveTowardsEnemy();
				}
				else
				{
					if(post==null)
					{
						for(Spot spot : MapSpots.GuardPosts)
						{
							if(spot.IsTaken==false)
							{
								spot.IsTaken=true;
								post=spot;
								
								moveTowards(post.coordinates);
								break;
							}
						}
					}
					else
					{
						moveTowards(post.coordinates);
					}
				}
			}
			
		}
		if(Slept==false)
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	textField.setText("X of "+name+" coordinate is : "+ xCoordinate);
		}
	}
	
	public Point getCoordinates()
	{
		return new Point(xCoordinate,yCoordinate);
	}
	
	public double getDistance(Point locationEnd)
	{
		double distance;
		distance=Math.sqrt(Math.pow(xCoordinate-locationEnd.x,2)+Math.pow(yCoordinate-locationEnd.y,2));
		return distance;
	}
	
	public void Attack(EntityThread enemy)
	{   
		if(currentAttackSpeed==0) {
		int fullDamage=damage;
		if(KnightsWeaponsPurchased && type=="MonkeyKnight")
			fullDamage+=2;
		
		enemy.health-=fullDamage;
		if(enemy.health<=0)
		{
			enemy.alive=false;
			
			try {
				enemy.label.setIcon(new ImageIcon(ImageIO.read(EntityThread.class.getResourceAsStream("/Slike/Grave.png"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(enemy.type=="MonkeyKnight")
			{
				game.KnightCount--;
				game.UpdateLabels();
				
			}
			
			//entities.remove(closestEnemy);
			enemy.stop();
		}
		currentAttackSpeed=attackSpeed;
		if(type=="MonkeyKnight")
			label.setIcon(new ImageIcon(imageG));
		}
		else
		{
			if(type=="MonkeyKnight")
			label.setIcon(new ImageIcon(secondaryImage));
			currentAttackSpeed-=10;
		}
	}
	
	public void moveTowardsEnemy()
	{
		EntityThread closestEnemy=null;
		double distance=Double.MAX_VALUE;
		for(EntityThread entity : entities)
		{
			if(entity.type=="Enemy" && entity.alive) 
			{
				double tempDistance=getDistance(entity.getCoordinates());
				if(distance>tempDistance)
				{
					distance=tempDistance;
					closestEnemy=entity;
				}
			}
			
		}
		if(closestEnemy!=null)
		{
			moveTowards(closestEnemy.getCoordinates());
			if(getDistance(closestEnemy.getCoordinates())<10)
			{
				Attack(closestEnemy);
			}
		}
	}
	
    public static BufferedImage flipImage(final BufferedImage image, final boolean horizontal,
            final boolean vertical) {
        int x = 0;
        int y = 0;
        int w = image.getWidth();
        int h = image.getHeight();

        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = out.createGraphics();

        if (horizontal) {
            x = w;
            w *= -1;
        }

        if (vertical) {
            y = h;
            h *= -1;
        }

        g2d.drawImage(image, x, y, w, h, null);
        g2d.dispose();

        return out;
    }
	
	public void moveTowards(Point locationEnd)
	{
		if(xCoordinate>locationEnd.x)
		{
			if(facingRight==true)
			{
				ImageIcon img=new ImageIcon(flipImage(imageG,true,false));
				facingRight=false;
				if(name=="Monkey" || name=="Miner") {
					if(JobComplete)
					{
						img=new ImageIcon(flipImage(secondaryImage,true,false));
					}
				}
				
				label.setIcon(img);
			}
			xCoordinate-=speed;
		}
		else if(xCoordinate<locationEnd.x)
		{
			if(facingRight==false)
			{
				facingRight=true;
				ImageIcon img=new ImageIcon(imageG);
				if(name=="Monkey" || name=="Miner") {
					if(JobComplete)
					{
						img=new ImageIcon(secondaryImage);
					}
				}
				label.setIcon(img);
			}
			xCoordinate+=speed;
		}
		
		if(yCoordinate>locationEnd.y)
		{
			yCoordinate-=speed;
		}
		else if(yCoordinate<locationEnd.y)
		{
			yCoordinate+=speed;
		}
		label.setLocation(xCoordinate,yCoordinate );
	}
	
}
