import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.event.Event;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
/**
 * 
 * @author Dr.Aviv
 *
 */

public class Car implements CarEvents, Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int model_id;
	private double speed;
	private Color color;
	private Model model;
	private int wheelRadius;
	private Timeline tl;
	private int lapsCount;
	private String type;
	private int carNum;
	
	
	
	public Timeline getTl() {
		return tl;
	}

	public int getCarNum() {
		return carNum;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public void setTl(Timeline tl) {
		this.tl = tl;
	}

	private Map<eventType, ArrayList<EventHandler<Event>>> carHashMap;

	public Car(int id, int model_id, Model model) {
		this.id = id;
		this.model = model;
		setModel_id(model_id);
		this.speed = 0;
		//this.color = Color.RED;
		this.wheelRadius = 5;
		carHashMap = new HashMap<eventType, ArrayList<EventHandler<Event>>>();
		for (eventType et : eventType.values())
			carHashMap.put(et, new ArrayList<EventHandler<Event>>());
		this.lapsCount = 0;
	}

	public int getId() {
		return id;
	}
	
	public int getModelId() {
		return model_id;
	}

	public Color getColor() {
		return color;
	}

	public int getRadius() {
		return wheelRadius;
	}

	public double getSpeed() {
		return speed;
	}

	public void setColor(Color color) throws IOException {
		this.color = color;
		processEvent(eventType.COLOR, new ActionEvent());
	}

	public void setSpeed(double speed) throws IOException {
		this.speed = speed;
		processEvent(eventType.SPEED, new ActionEvent());
	}

	public void setRadius(int wheelRadius) throws IOException {
		this.wheelRadius = wheelRadius;
		processEvent(eventType.RADIUS, new ActionEvent());
	}

	public synchronized void addEventHandler(EventHandler<Event> l, eventType et) {
		ArrayList<EventHandler<Event>> al;
		al = carHashMap.get(et);
		if (al == null)
			al = new ArrayList<EventHandler<Event>>();
		al.add(l);
		carHashMap.put(et, al);
	}

	public synchronized void removeEventHandler(EventHandler<Event> l, eventType et) {
		ArrayList<EventHandler<Event>> al;
		al = carHashMap.get(et);
		if (al != null && al.contains(l))
			al.remove(l);
		carHashMap.put(et, al);
	}

	private void processEvent(eventType et, Event e) throws IOException {
		String msg;
		ArrayList<EventHandler<Event>> al;
		synchronized (this) {
			al = carHashMap.get(et);
			if (al == null)
				return;
		}
		msg = "\nCarRaceView" + (model.getMyClient().getRaceNum()) + " | car number: " + (getId() + 1) + " | actionCommand: "
				+ et.toString() + " | array size is: " + al.size();
		model.sendMessageToServer(msg);
		for (int i = 0; i < al.size(); i++) {
			EventHandler<Event> handler = (EventHandler<Event>) al.get(i);
			handler.handle(e);
		}
	}

	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}

	public int getLapsCount() {
		return lapsCount;
	}

	public void setLapsCount(int lapsCount) {
		this.lapsCount = lapsCount;
		if (this.lapsCount>=15)
			model.setWeHaveWinner(true,this.id);
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}