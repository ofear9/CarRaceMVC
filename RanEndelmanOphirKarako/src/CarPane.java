import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CarPane extends Pane implements CarEvents {
	class ColorEvent implements EventHandler<Event> {
		@Override
		public void handle(Event event) {
			setColor(car.getColor());
		}
	}

	class RadiusEvent implements EventHandler<Event> {
		@Override
		public void handle(Event event) {
			setRadius(car.getRadius());
		}
	}

	class SpeedEvent implements EventHandler<Event> {
		@Override
		public void handle(Event event) {
			setSpeed(car.getSpeed());
		}
	}

	final int MOVE = 1;
	final int STOP = 0;
	private double xCoor;
	private double yCoor;
	private Timeline tl = new Timeline(); // speed=setRate()
	private Color color;
	private String type;
	private int r;// radius
	Random randomGenerator = new Random();
	boolean flag = true;
	int sel = randomGenerator.nextInt((2 + 0) + 1);
	private Car car;
	final Rotate rx = new Rotate(0, Rotate.X_AXIS);
	final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
	private Object selectedCar;
	int timeLine = 1;


	public CarPane(String type ,String Scolor) {
		xCoor = 0;
		r = randomGenerator.nextInt((15 - 5) + 1) + 5;
		this.color = getColorFromString(Scolor);
		this.type = type;
	
		
	
	
	}
	
	public Timeline getTl() {
		return tl;
	}


	public void setTl(int indefinite) {
		this.tl.setCycleCount(indefinite);
	}

	
	public void setCarModel(Car myCar) {
		car = myCar;
		if (car != null) {
			car.addEventHandler(new ColorEvent(), eventType.COLOR);
			car.addEventHandler(new RadiusEvent(), eventType.RADIUS);
			car.addEventHandler(new SpeedEvent(), eventType.SPEED);
		}
	}

	public Car getCarModel() {
		return car;
	}

	public void moveCar(int n) {
		yCoor = getHeight();
		setMinSize(10 * r, 6 * r);
		if (xCoor > getWidth()-130) {
			xCoor = -10 * r;
			this.car.setLapsCount(this.car.getLapsCount()+1);
			
			
		} else {
			xCoor += n;
		}
		// Draw the car

		ArrayList<Node> car = null;

		if (this.type.equals("Sport")) {
			selectedCar = new SportCar();
			selectedCar = (SportCar) selectedCar;
			car = ((SportCar) selectedCar).getCar(xCoor, yCoor, r,color);

		}

		else if (this.type.equals("Truck")) {
			selectedCar = new TruckCar();
			selectedCar = (TruckCar) selectedCar;
			car = ((TruckCar) selectedCar).getCar(xCoor, yCoor, r,color);
		}

		else {
			selectedCar = new DrugCar();
			selectedCar = (DrugCar) selectedCar;
			car = ((DrugCar) selectedCar).getCar(xCoor, yCoor, r,color);

		}
		// ArrayList<Node> car = selectedCar.getCar(xCoor, yCoor, r);
		flag = false;
		
		Group cubeGroup	= new Group();
		
		cubeGroup.getChildren().addAll(car);
		getChildren().clear();
		getChildren().addAll(cubeGroup);

	}

	public void createTimeline() throws InterruptedException {
		EventHandler<ActionEvent> eventHandler = e -> {
			moveCar(MOVE); // move car pane according to limits
		};
		tl = new Timeline();
		
		KeyFrame kf = new KeyFrame(Duration.millis(50), eventHandler);
		tl.setCycleCount(1);
		tl.getKeyFrames().add(kf);
		tl.play();

	}

	public Timeline getTimeline() {
		return tl;
	}

	public void setColor(Color color) {
		this.color = color;
		if (car.getSpeed() == STOP)
			moveCar(STOP);
	}

	public void setRadius(int r) {
		this.r = r;
		if (car.getSpeed() == STOP)
			moveCar(STOP);
	}

	public void setSpeed(double speed) {
		if (speed == STOP) {
			tl.stop();
		} else {
			tl.setRate(speed);
			tl.play();
		}
	}

	public Color getColorFromString(String Scolor) {

		Color colors[] = { Color.RED, Color.AQUA, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK,
				Color.VIOLET, Color.WHITE, Color.TRANSPARENT };

		String colorNames[] = { "RED", "AQUA", "BLUE", "GREEN", "YELLOW", "ORANGE", "PINK", "VIOLET", "WHITE",
				"TRANSPARENT" };

		for (int i = 0; i < colorNames.length; i++) {
			if (Scolor.toUpperCase().equals(colorNames[i]))
				return colors[i];
		}
		return Color.TRANSPARENT;
	}

	public Object getTypByString(String type) {
		switch (type) {

		case "Sport":
			return new SportCar();

		case "Truck":
			return new TruckCar();

		case "Drug":
			return new DrugCar();
		}
		return null;

	}

	public double getX() {
		return xCoor;
	}

	public double getY() {
		return yCoor;
	}
}
