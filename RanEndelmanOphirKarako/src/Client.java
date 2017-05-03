import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * This class represents client - rvery race in the game is client 
 * @author Ophir Karako and Ran endlman 
 * 
 * @param raceNum - the race number
 * @param low,high - the limits of the selcted cars (0-4 ,5-9,10-14) this is the cars numbers. 
 *@param carsForPane - an array of carsForPane. 
 */



public class Client implements Serializable { 
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Socket socket;
	private int raceNum;
	Controller controller = null;
	private int low, high;
	private DataBase db;
	private ArrayList<CarsForPane> carsForPane;
	private View view = null;
	
	public Client(ArrayList<CarsForPane> previousCarsForPane) throws IOException, ClassNotFoundException {
		new Thread(() -> {
			Model model = null;
			try { // Create a socket to connect to the server
				socket = new Socket("localhost", 8000);
				toServer = new ObjectOutputStream(socket.getOutputStream());
				fromServer = new ObjectInputStream(socket.getInputStream());
				db = new DataBase();

			} catch (IOException e1) {
				try {
					socket.close();
				} catch (IOException e2) {
				}
			}
			try {
				model = (Model) fromServer.readObject();
				model.setMyClient(this);
				raceNum = (int) fromServer.readObject();
				setRaceNum(raceNum);
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				high = (int) fromServer.readObject();
				low = high - 5;
				if (previousCarsForPane == null)
					carsForPane = db.getCarsFromDB(low, high, raceNum);
				else
					this.carsForPane = previousCarsForPane;
			} catch (SQLException | ClassNotFoundException | IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			try {
				view = new View(carsForPane, db, raceNum);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			try {
				controller = new Controller(model, view, carsForPane);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			view.setModel(model);
			// modelList.add(model);
			// viewList.add(view);
			// controllerList.add(controller);
			Platform.runLater(() -> {
				Stage stg = new Stage();
				Scene scene = new Scene(view.getBorderPane(), 950, 700);

				controller.setOwnerStage(stg);
				try {
					view.createAllTimelines();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				stg.setScene(scene);
				stg.setTitle("CarRaceView" + raceNum);
				stg.setAlwaysOnTop(true);
				stg.show();
				scene.widthProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) { // TODO // stub
						view.setCarPanesMaxWidth(newValue.doubleValue());
					}
				});

				stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent event) {
						try {
							newRace(carsForPane);
						} catch (IOException e) {
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			});

		}).start();
	}
/**
 * This method send messages to the server 
 * @param msg - the message to the server
 * @throws IOException
 */
	public void sendMessageToServer(String msg) throws IOException {
		toServer.writeObject(msg);
	}

	public int getRaceNum() {
		return raceNum;
	}

	public void setRaceNum(int raceNum) {
		this.raceNum = raceNum;
	}
	
	/**
	 * This method send to the server his number and after that the server open a new clinet.This happened after the race is over.
	 * @param carsForPane  
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void newRace(ArrayList<CarsForPane> carsForPane) throws IOException, ClassNotFoundException {
		toServer.writeObject(raceNum);
		socket.close();
		new Client(carsForPane);
	}
	
	
	/**
	 * This method send to the DB the details of the current race
	 * @param dateAndTime - the race time
	 * @param cars - the current cars
	 * @param players - the gamblers
	 * @param winner - who is the winner
	 * @param totalBets - the amount of all bets 
	 * @throws SQLException
	 */
	public void insertRaceToDB(String dateAndTime, String cars, String players, String winner, String totalBets) throws SQLException {
		String id = "" + (db.getHighestIDFromRaces() + 1);
		db.insertRaceToDB(id, dateAndTime, cars, players, winner, totalBets);
	}

	public ArrayList<CarsForPane> getCarsForPane() {
		return carsForPane;
	}

	public int getRaceID() throws SQLException {
		return db.getHighestIDFromRaces();
		
	}

	/**
	 * This method save all of the gambles bets during race
	 * @param bets 
	 * @throws SQLException
	 */
	public void insertBetToDB(ArrayList<Bet> bets) throws SQLException {
		for (Bet bet : bets)
			db.insertNewGamblerRow(bet.getGamblerName(), bet.getRaceID(), 
					bet.getCarGambeldOn(), bet.getAmount(), bet.getProfit());
	}

/**
 * This method update the profit for the user if he win 
 * @param gamblerName - the name of the gambler
 * @param profitToAdd - how much profit to add 
 * @throws SQLException
 */
	public void updateProfitsForUser(String gamblerName, int profitToAdd) throws SQLException {
		db.updateProfitsForUser(gamblerName, profitToAdd);
	}

}
