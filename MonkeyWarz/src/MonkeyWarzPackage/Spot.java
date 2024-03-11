package MonkeyWarzPackage;

import java.awt.Point;

public class Spot {
	public Point coordinates;
	boolean IsTaken;
	
	public Spot(int xCoordinate,int yCoordinate)
	{
		coordinates=new Point(xCoordinate,yCoordinate);
		IsTaken=false;
	}
	
	public Spot(Point point)
	{
		coordinates=point;
		IsTaken=false;
	}
}
