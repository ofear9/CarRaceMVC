import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * This class draw 3D Sport car race model using group of Polygons .
 * 
 * It creats all of the relevent polygon.
 * 
 * @author Ophir Karako and Ran Endelman
 *
 */
public class SportCar {

	public SportCar() {

	}

	public ArrayList<Node> getCar(double xCoor, double yCoor, int r, Color color) {

		ArrayList<Node> car = new ArrayList<Node>();

		Polygon polygon = new Polygon(xCoor, yCoor - r, xCoor, yCoor - 4 * r, xCoor + 2 * r, yCoor - 4 * r,
				xCoor + 4 * r, yCoor - 6 * r, xCoor + 6 * r, yCoor - 6 * r, xCoor + 8 * r, yCoor - 4 * r,
				xCoor + 10 * r, yCoor - 4 * r, xCoor + 10 * r, yCoor - r);

		polygon.setStroke(Color.BLACK);
		polygon.setFill(color);

		xCoor += 7;
		yCoor -= 7;

		Polygon poly2 = new Polygon(xCoor, yCoor - r, xCoor, yCoor - 4 * r, xCoor + 2 * r, yCoor - 4 * r, xCoor + 4 * r,
				yCoor - 6 * r, xCoor + 6 * r, yCoor - 6 * r, xCoor + 8 * r, yCoor - 4 * r, xCoor + 10 * r,
				yCoor - 4 * r, xCoor + 10 * r, yCoor - r);

		poly2.setFill(Color.TRANSPARENT);
		poly2.setStroke(Color.BLACK);

		xCoor -= 7;
		yCoor += 7;

		Polygon pol1 = new Polygon(xCoor, yCoor - 4 * r, xCoor + 2 * r, yCoor - 4 * r, xCoor + 7 + 2 * r,
				yCoor - 7 - 4 * r, xCoor + 7, yCoor - 7 - 4 * r);
		pol1.setStroke(Color.BLACK);
		pol1.setFill(Color.GREEN);

		Polygon rearWindow = new Polygon(xCoor + 2 * r, yCoor - 4 * r, xCoor + 4 * r, yCoor - 6 * r, xCoor + 7 + 4 * r,
				yCoor - 7 - 6 * r, xCoor + 7 + 2 * r, yCoor - 7 - 4 * r);
		rearWindow.setFill(Color.AQUA);
		rearWindow.setStroke(Color.BLACK);

		Polygon roof = new Polygon(xCoor + 4 * r, yCoor - 6 * r, xCoor + 6 * r, yCoor - 6 * r, xCoor + 7 + 6 * r,
				yCoor - 7 - 6 * r, xCoor + 7 + 4 * r, yCoor - 7 - 6 * r);

		roof.setFill(Color.GREEN);
		roof.setStroke(Color.BLACK);

		Polygon hood = new Polygon(xCoor + 8 * r, yCoor - 4 * r, xCoor + 10 * r, yCoor - 4 * r, xCoor + 7 + 10 * r,
				yCoor - 7 - 4 * r, xCoor + 7 + 8 * r, yCoor - 7 - 4 * r);
		hood.setStroke(Color.BLACK);
		hood.setFill(Color.CHOCOLATE);

		Polygon frontWin = new Polygon(xCoor + 6 * r, yCoor - 6 * r, xCoor + 8 * r, yCoor - 4 * r, xCoor + 7 + 8 * r,
				yCoor - 7 - 4 * r, xCoor + 7 + 6 * r, yCoor - 7 - 6 * r);

		frontWin.setFill(Color.AQUA);
		frontWin.setStroke(Color.BLACK);

		Polygon front = new Polygon(xCoor + 10 * r, yCoor - 4 * r, xCoor + 10 * r, yCoor - r, xCoor + 7 + 10 * r,
				yCoor - 7 - r, xCoor + 7 + 10 * r, yCoor - 7 - 4 * r);

		front.setFill(Color.BROWN);
		front.setStroke(Color.BLACK);

		Polygon window = new Polygon(xCoor + 3 * r, yCoor - 4 * r, xCoor + 7 * r, yCoor - 4 * r, xCoor + 6 * r,
				yCoor - 5 * r, xCoor + 4 * r, yCoor - 5 * r);
		window.setFill(Color.AQUA);
		window.setStroke(Color.BLACK);

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
		car.add(flag);
		car.add(polygon);
		car.add(window);
		car.add(frontWin);
		car.add(front);
		car.add(pol1);
		car.add(rearWindow);
		car.add(roof);
		car.add(hood);
		car.add(wheel1);
		car.add(wheel2);
		car.add(wheel5);
		car.add(wheel6);

		return car;

	}
}
