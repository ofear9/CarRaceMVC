import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * This class represents how the Truck car should look .
 * 
 * It creats all of the relevent polygon.
 * 
 * 
 * @author Ophir Karako and Ran Endelman
 *
 *
 */
public class TruckCar {

	public TruckCar() {
	}

	public ArrayList<Node> getCar(double xCoor, double yCoor, int r, Color color) {

		ArrayList<Node> car = new ArrayList<Node>();

		Polygon polygon = new Polygon(xCoor, yCoor - r, xCoor, yCoor - 4 * r, xCoor + 6 * r, yCoor - 4 * r,
				xCoor + 6 * r, yCoor - 6 * r, xCoor + 4 * r, yCoor - 8 * r, xCoor + 6 * r, yCoor - 6 * r,
				xCoor + 10 * r, yCoor - 6 * r, xCoor + 10 * r, yCoor - r);

		polygon.setStroke(Color.BLACK);
		polygon.setFill(color);

		xCoor += 7;
		yCoor -= 7;

		Polygon poly2 = new Polygon(xCoor, yCoor - r, xCoor, yCoor - 4 * r, xCoor + 6 * r, yCoor - 4 * r, xCoor + 6 * r,
				yCoor - 6 * r, xCoor + 4 * r, yCoor - 8 * r, xCoor + 6 * r, yCoor - 6 * r, xCoor + 10 * r,
				yCoor - 6 * r, xCoor + 10 * r, yCoor - r);

		poly2.setFill(Color.TRANSPARENT);
		poly2.setStroke(Color.BLACK);

		xCoor -= 7;
		yCoor += 7;

		Polygon back = new Polygon(xCoor, yCoor - 4 * r, xCoor + 6 * r, yCoor - 4 * r, xCoor + 7 + 6 * r,
				yCoor - 7 - 4 * r, xCoor + 7, yCoor - 7 - 4 * r);

		back.setStroke(Color.BLACK);
		back.setFill(color);

		Polygon roof = new Polygon(xCoor + 6 * r, yCoor - 6 * r, xCoor + 10 * r, yCoor - 6 * r, xCoor + 7 + 10 * r,
				yCoor - 7 - 6 * r, xCoor + 7 + 6 * r, yCoor - 7 - 6 * r);

		roof.setStroke(Color.BLACK);
		roof.setFill(Color.GRAY);

		Polygon front = new Polygon(xCoor + 10 * r, yCoor - 6 * r, xCoor + 10 * r, yCoor - r, xCoor + 7 + 10 * r,
				yCoor - 7 - r, xCoor + 7 + 10 * r, yCoor - 7 - 6 * r);
		front.setStroke(Color.BLACK);
		front.setFill(Color.BLACK);

		Polygon frontWin = new Polygon(xCoor + 10 * r, yCoor - 6 * r, xCoor + 10 * r, yCoor - 4 * r, xCoor + 7 + 10 * r,
				yCoor - 7 - 4 * r, xCoor + 7 + 10 * r, yCoor - 7 - 6 * r);

		frontWin.setStroke(Color.BLACK);
		frontWin.setFill(Color.AQUA);

		Polygon flag = new Polygon(xCoor, yCoor - 4 * r, xCoor, yCoor - 8 * r, xCoor - 8, yCoor - 6 * r, xCoor,
				yCoor - 6 * r, xCoor, yCoor - 4 * r);
		flag.setStroke(Color.BLACK);
		flag.setFill(Color.TOMATO);
		// Draw the wheels
		Circle wheel1 = new Circle(xCoor + r * 3, yCoor - r, r, Color.BLACK);
		Circle wheel2 = new Circle(xCoor + r * 7, yCoor - r, r, Color.BLACK);
		Circle wheel3 = new Circle(xCoor + 7 + r * 3, yCoor + 1 - r, r, Color.BLACK);
		Circle wheel4 = new Circle(xCoor + 7 + r * 7, yCoor + 1 - r, r, Color.BLACK);

		Circle wheel5 = new Circle(xCoor + r * 3, yCoor - r, r - 5, Color.GRAY);
		Circle wheel6 = new Circle(xCoor + r * 7, yCoor - r, r - 5, Color.GRAY);

		car.add(poly2);
		car.add(wheel3);
		car.add(wheel4);
		car.add(back);
		car.add(polygon);
		car.add(flag);
		car.add(roof);
		car.add(front);
		car.add(frontWin);

		car.add(wheel1);
		car.add(wheel2);
		car.add(wheel5);
		car.add(wheel6);

		return car;

	}
}
