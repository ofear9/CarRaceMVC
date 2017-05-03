import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * This is the model class - part of the MVC.
 *
 * @author Dr. Aviv 
 *
 */
class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	private Car c1, c2, c3, c4, c5;
	private Client myClient;
	private int raceCounter = 0;
	private int winNum = -1;
	String colorNames[] = { "RED", "AQUA", "BLUE", "GREEN", "YELLOW", "ORANGE", "PINK", "VIOLET", "WHITE",
			"TRANSPARENT" };
	private boolean canWeStart = false;
	private boolean weHaveWinner = false;
	private ArrayList<Car> cars = new ArrayList<>();
	public boolean getCanWeStart() {
		return canWeStart;
	}

	public void setCanWeStart(boolean canWeStart) {
		this.canWeStart = canWeStart;
	}

	public Model(int carNums) {
		this.raceCounter++;
		c1 = new Car(carNums++, raceCounter, this);
		c2 = new Car(carNums++, raceCounter, this);
		c3 = new Car(carNums++, raceCounter, this);
		c4 = new Car(carNums++, raceCounter, this);
		c5 = new Car(carNums++, raceCounter, this);
	}

	public void changeColor(int id, Color color) throws IOException {
		getCarById(id).setColor(color);
	}

	public void changeRadius(int id, int radius) throws IOException {
		getCarById(id).setRadius(radius);
	}

	public void changeSpeed(int id, double speed) throws IOException {
		getCarById(id).setSpeed(speed);
	}

	public Car getCarById(int id) {

		switch (id) {
		case 1:
			return c1;
		case 2:
			return c2;
		case 3:
			return c3;
		case 4:
			return c4;
		default:
			return c5;
		}

	}

	public int getRaceCounter() {
		return raceCounter;
	}

	public Client getMyClient() {
		return myClient;
	}

	public void setMyClient(Client myClient) {
		this.myClient = myClient;
	}

	public void sendMessageToServer(String msg) throws IOException {
		getMyClient().sendMessageToServer(msg);
	}

	public boolean isWeHaveWinner() {
		return weHaveWinner;
	}

	public synchronized void setWeHaveWinner(boolean weHaveWinner, int num) {
		if (this.weHaveWinner == false ){
		this.weHaveWinner = weHaveWinner;
		this.winNum = num;
		}
		
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public int getWinNum() {
		return winNum;
	}
	/**
	 * This method set for each car in the race - the type (Sport,Truck,Drug) , the color and the number of the car.
	 * @return An arrayList of cars
	 */
	public ArrayList<Car> setCarsType() {
		c1.setType(getMyClient().getCarsForPane().get(0).getColors() + " " + getMyClient().getCarsForPane().get(0).getName());
		c1.setCarNum(getMyClient().getCarsForPane().get(0).getNum());
		
		c2.setType(getMyClient().getCarsForPane().get(1).getColors() + " " + getMyClient().getCarsForPane().get(1).getName());
		c2.setCarNum(getMyClient().getCarsForPane().get(1).getNum());
		
		c3.setType(getMyClient().getCarsForPane().get(2).getColors() + " " + getMyClient().getCarsForPane().get(2).getName());
		c3.setCarNum(getMyClient().getCarsForPane().get(2).getNum());
		
		c4.setType(getMyClient().getCarsForPane().get(3).getColors() + " " + getMyClient().getCarsForPane().get(3).getName());
		c4.setCarNum(getMyClient().getCarsForPane().get(3).getNum());
		
		c5.setType(getMyClient().getCarsForPane().get(4).getColors() + " " + getMyClient().getCarsForPane().get(4).getName());
		c5.setCarNum(getMyClient().getCarsForPane().get(4).getNum());
		
		cars.add(c1);
		cars.add(c2);
		cars.add(c3);
		cars.add(c4);
		cars.add(c5);
		return cars;
	}
	
	public void newRace(ArrayList<CarsForPane> carsForPane) throws ClassNotFoundException, IOException {
		getMyClient().newRace(carsForPane);
	}
/**
 * This method send to DB class the details about the current race. 
 * The information will be saved under Races History table.
 * 
 * @param dateAndTime - The time of the race
 * @param cars - the cars
 * @param players - The players the bet on this game 
 * @param winner - the winning car 
 * @param totalBets - how many bets 
 * @throws SQLException
 */
	public void insertRaceToDB(String dateAndTime, String cars, String players, String winner, String totalBets) throws SQLException {
		getMyClient().insertRaceToDB(dateAndTime, cars, players, winner, totalBets);	
	}
	
	public int getRaceID() throws SQLException {
		return getMyClient().getRaceID();
	}

	public ArrayList<Car> getCars() {
		return cars;
	}
	/**
	 * This method send all of the bets to the DB class .
	 * @param bets - Array list of bets .
	 * @throws SQLException
	 */
	public void insertBetToDB(ArrayList<Bet> bets) throws SQLException {
		getMyClient().insertBetToDB(bets);
	}

	/**
	 * 
	 * @param gamblerName - The name of the player.
	 * @param profitToAdd - The amount of money that the player win - add to the total profit of this player.
	 * @throws SQLException
	 */
	public void updateProfitsForUser(String gamblerName, int profitToAdd) throws SQLException {
		getMyClient().updateProfitsForUser(gamblerName, profitToAdd);
	}

}