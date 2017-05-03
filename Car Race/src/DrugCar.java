import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * This class draw  3D Drug car race model using group of Polygons .
 * 
 * @author Ophir Karako and Ran Endelman 
 *
 */


public class DrugCar {

	public DrugCar() {
		
	}
public ArrayList<Node> getCar(double xCoor,double yCoor,int r ,Color color){
		
		ArrayList<Node> car = new ArrayList<Node>();
		
		Polygon polygon = new Polygon(xCoor, yCoor - r,
				xCoor, yCoor - 4 * r,
				xCoor + 4 * r , yCoor -2 *r,
				xCoor + 8 * r,yCoor -2 *r,
				xCoor +8  * r ,yCoor -r);
		
		
		polygon.setStroke(Color.BLACK);
		polygon.setFill(color);
		

		xCoor += 7;
		yCoor -= 7;

		Polygon poly2 = new Polygon(xCoor, yCoor - r,
				xCoor, yCoor - 4 * r,
				xCoor + 4 * r , yCoor -2 *r,
				xCoor + 8 * r,yCoor -2 *r,
				xCoor +8  * r ,yCoor -r);	
		
		poly2.setFill(Color.TRANSPARENT);
		poly2.setStroke(Color.BLACK);
		
		xCoor -= 7;
		yCoor += 7;
		
	
		
		Polygon window = new Polygon( xCoor, yCoor - 4 * r,
				xCoor + 4 * r , yCoor -2 *r,
				xCoor+7 + 4 * r , yCoor-7 -2 *r,
				xCoor+7, yCoor-7 - 4 * r);
	     window.setStroke(Color.BLACK);
	     window.setFill(color);
	     
        Polygon hood = new Polygon(xCoor + 4 * r , yCoor -2 *r,
        		xCoor + 8 * r,yCoor -2 *r,	
        		xCoor+7 + 8 * r,yCoor-7 -2 *r,	
        		xCoor+7 + 4 * r , yCoor-7 -2 *r);
        
        hood.setStroke(Color.BLACK);
        hood.setFill(color);
    
	  Polygon flagLeft = new Polygon(xCoor + 8 * r,yCoor -2 *r,
			         xCoor+8 *r ,yCoor -4 *r,
			         xCoor+6 * r , yCoor -3 *r,
			         xCoor+8 *r , yCoor -3 *r,
			         xCoor + 8 * r,yCoor -2 *r);
	  flagLeft.setStroke(Color.BLACK);
	  flagLeft.setFill(Color.TOMATO);

		xCoor += 7;
		yCoor -= 7;
		 
		Polygon flagRight = new Polygon(xCoor + 8 * r,yCoor -2 *r,
				         xCoor+8 *r ,yCoor -4 *r,
				         xCoor+6 * r , yCoor -3 *r,
				         xCoor+8 *r , yCoor -3 *r,
				         xCoor + 8 * r,yCoor -2 *r);
		flagRight.setStroke(Color.BLACK);
		flagRight.setFill(Color.TOMATO);
		
		xCoor -= 7;
		yCoor += 7;
		
	    Polygon front = new Polygon(xCoor + 8 * r,yCoor -2 *r,
				xCoor +8  * r ,yCoor -r,
				xCoor +7 +8  * r ,yCoor-7 -r,  
				xCoor+7 + 8 * r,yCoor-7 -2 *r);
	    front.setStroke(Color.BLACK);
	    front.setFill(Color.GRAY);
	    
		

	
		
		
		// Draw the wheels
		Circle wheel1 = new Circle(xCoor + r * 2, yCoor - r, r, Color.BLACK);
		Circle wheel2 = new Circle(xCoor + r * 5, yCoor - r, r, Color.BLACK);
		Circle wheel3 = new Circle(xCoor-7 + r *3, yCoor-1 - r, r, Color.BLACK);
		Circle wheel4 = new Circle(xCoor-7 + r *7, yCoor-1 - r, r, Color.BLACK);
		
		Circle wheel5 = new Circle(xCoor + r * 2, yCoor - r, r-5, Color.GRAY);
		Circle wheel6 = new Circle(xCoor + r * 5, yCoor - r, r-5, Color.GRAY);
		
		
     
	   car.add(poly2);
	   car.add(wheel3);
	   car.add(wheel4);
	   car.add(polygon);
	   car.add(window);
	   car.add(hood);
	   car.add(flagRight);
	   car.add(flagLeft);
	   car.add(front);
	   car.add(wheel1);
	   car.add(wheel2);
	   car.add(wheel5);
	   car.add(wheel6);
	   
	  
		return car;
		
	}
}
