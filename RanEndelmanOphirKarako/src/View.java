import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 * This is the View class of the game - Part of the MVC.
 * This class show all the scenes of the game . 
 * 
 * @author Dr. Aviv
 *
 */
public class View {
	private Model model;
	private BorderPane border_pane;
	private GridPane details_grid, cars_grid;
	private CarPane car_pane1, car_pane2, car_pane3, car_pane4, car_pane5;
	private Slider slRadius;
	private TextField spd_txt1, spd_txt2, spd_txt3, spd_txt4, spd_txt5;
	private ComboBox<String> colorComBox, carIdComBox;
	private ObservableList<String> items_color, items_car;
	private Button btn;
	private ArrayList<CarsForPane> cars;
	private TextField user, amountToBet;
	private PasswordField password;
	private Label betMadeLabel, remainsBetReq, betsStatus;
	private VBox vBox = new VBox(6);
	private Button makeBetButton = new Button("Bet!");
	private Button startRace = new Button("Start Race!");
	private ComboBox<String> carsToBet = new ComboBox<>();
	private int raceNum;
	private DataBase db;
	private int betsCounter;
	private ArrayList<String> gamblers;
	private ArrayList<Bet> bets;
	private int totalBets;

	public View(ArrayList<CarsForPane> cars, DataBase db2, int raceNum) throws SQLException {
		this.cars = cars;
		this.db = db2;
		this.gamblers = new ArrayList<>();
		this.bets = new ArrayList<>();
		this.totalBets = 0;
		this.raceNum = raceNum;
		border_pane = new BorderPane();
		border_pane.setTop(details_grid);
		createCarsGrid();
		border_pane.setCenter(cars_grid);
		border_pane.setRight(betsWindow());
		this.betsCounter = 0;
	}
/**
 * This method create the Vbox for the bets options for each race . 
 * @return Vbox of the betting option for in the right side of the race screen
 * @throws SQLException
 */
	public VBox betsWindow() throws SQLException {
		user = new TextField();
		password = new PasswordField();
		amountToBet = new TextField();
		betMadeLabel = new Label("");
		betsStatus = new Label("");
		remainsBetReq = new Label(3 - getBetsCounter() + " More Bets Requires in order to start the Race.");
		carsToBet.getItems().addAll(db.getCarsForBets(raceNum));
		vBox.getChildren().addAll(new Label("User name: "), user, new Label("Password"), password,
				new Label("Choose car: "), carsToBet, new Label("Choose amount to bet"), amountToBet, makeBetButton,
				betMadeLabel, startRace, remainsBetReq, betsStatus);
		startRace.setVisible(false);
		makeBetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String userName = user.getText();
				String pass = password.getText();
				try {
					if (verifyUser(userName, pass)) {
						try {
							int choosenCar = Integer
									.parseInt((carsToBet.getSelectionModel().getSelectedItem()).split(" ")[2]);
							int betAmmount = Integer.parseInt(amountToBet.getText());
							System.out.println("BET MADE");
							betMadeLabel.setText("Bet Made! \nYou can continue betting.");
							totalBets += Integer.parseInt(amountToBet.getText());
							gamblers.add(userName);
							bets.add(new Bet(userName, "" + (model.getRaceID() + 1), 
									carsToBet.getValue(), Integer.parseInt(amountToBet.getText()), 0));
							user.clear();
							password.clear();
							db.updateBets(raceNum, choosenCar, betAmmount);
							betsCounter++;
							betsStatus.setText(betsStatus.getText() + "\n" + userName + " Bet " + amountToBet.getText() + " on " + carsToBet.getValue());
							amountToBet.clear();
							if (getBetsCounter() == 1)
								remainsBetReq.setText(3 - getBetsCounter() + " More Bets Requires in order to start the Race.");
							else if (getBetsCounter() == 2)
								remainsBetReq.setText(3 - getBetsCounter() + " More Bet Require in order to start the Race.");
							else 
								remainsBetReq.setText("You can start the Race.");
							if (betsCounter == 3) {
								startRace.setVisible(true);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
						betMadeLabel.setText("Wrong User Name or Password! \nTry Again.");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		startRace.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				vBox.setVisible(false);
				border_pane.setRight(null);
				setTlToInf();
				model.setCanWeStart(true);
			}
		});

		return vBox;

	}

	public void setModel(Model myModel) {
		model = myModel;
		if (model != null) {
			car_pane1.setCarModel(model.getCarById(0));
			car_pane2.setCarModel(model.getCarById(1));
			car_pane3.setCarModel(model.getCarById(2));
			car_pane4.setCarModel(model.getCarById(3));
			car_pane5.setCarModel(model.getCarById(4));
		}
	}

	public void createCarsGrid() {
		cars_grid = new GridPane();
		car_pane1 = new CarPane(cars.get(0).getType(), cars.get(0).getColors());
		car_pane2 = new CarPane(cars.get(1).getType(), cars.get(1).getColors());
		car_pane3 = new CarPane(cars.get(2).getType(), cars.get(2).getColors());
		car_pane4 = new CarPane(cars.get(3).getType(), cars.get(3).getColors());
		car_pane5 = new CarPane(cars.get(4).getType(), cars.get(4).getColors());
		cars_grid.add(car_pane1, 0, 0);
		cars_grid.add(car_pane2, 0, 1);
		cars_grid.add(car_pane3, 0, 2);
		cars_grid.add(car_pane4, 0, 3);
		cars_grid.add(car_pane5, 0, 4);

		cars_grid.setStyle("-fx-background-color: beige");
		cars_grid.setGridLinesVisible(true);
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		cars_grid.getColumnConstraints().add(column);
		RowConstraints row = new RowConstraints();
		row.setPercentHeight(33);
		cars_grid.getRowConstraints().add(row);
		cars_grid.getRowConstraints().add(row);
		cars_grid.getRowConstraints().add(row);
		cars_grid.getRowConstraints().add(row);
		cars_grid.getRowConstraints().add(row);
	}

	public void setTlToInf() {
		car_pane1.setTl(Timeline.INDEFINITE);
		car_pane2.setTl(Timeline.INDEFINITE);
		car_pane3.setTl(Timeline.INDEFINITE);
		car_pane4.setTl(Timeline.INDEFINITE);
		car_pane5.setTl(Timeline.INDEFINITE);

	}

	public void createAllTimelines() throws InterruptedException {

		car_pane1.createTimeline();
		car_pane2.createTimeline();
		car_pane3.createTimeline();
		car_pane4.createTimeline();
		car_pane5.createTimeline();
	}

	public BorderPane getBorderPane() {
		return border_pane;
	}

	public GridPane getDetailsGrid() {
		return details_grid;
	}

	public GridPane getCarsGrid() {
		return cars_grid;
	}

	public void setCarPanesMaxWidth(double newWidth) {
		car_pane1.setMaxWidth(newWidth);
		car_pane2.setMaxWidth(newWidth);
		car_pane3.setMaxWidth(newWidth);
		car_pane4.setMaxWidth(newWidth);
		car_pane5.setMaxWidth(newWidth);
	}

	public Pane getCarPane1() {
		return car_pane1;
	}

	public Pane getCarPane2() {
		return car_pane2;
	}

	public Pane getCarPane3() {
		return car_pane3;
	}

	public Pane getCarPane4() {
		return car_pane4;
	}

	public Pane getCarPane5() {
		return car_pane5;
	}

	public TextField getSpeedTxt1() {
		return spd_txt1;
	}

	public TextField getSpeedTxt2() {
		return spd_txt2;
	}

	public TextField getSpeedTxt3() {
		return spd_txt3;
	}

	public TextField getSpeedTxt4() {
		return spd_txt4;
	}

	public TextField getSpeedTxt5() {
		return spd_txt5;
	}

	public void setSpeedField(int id, Double speed) {
		switch (id) {
		case 1:
			spd_txt1.setText(new DecimalFormat("##").format(speed));
			break;
		case 2:
			spd_txt2.setText(new DecimalFormat("##").format(speed));
			break;
		case 3:
			spd_txt3.setText(new DecimalFormat("##").format(speed));
			break;
		case 4:
			spd_txt4.setText(new DecimalFormat("##").format(speed));
			break;
		default:
			spd_txt5.setText(new DecimalFormat("##").format(speed));
			break;
		}
	}

	public ObservableList<String> getItemsCar() {
		return items_car;
	}

	public ObservableList<String> getItemsColor() {
		return items_color;
	}

	public ComboBox<String> getColorComBox() {
		return colorComBox;
	}

	public ComboBox<String> getCarIdComBox() {
		return carIdComBox;
	}

	public Button getColorButton() {
		return btn;
	}

	public Slider getRadSlider() {
		return slRadius;
	}

	private boolean verifyUser(String userName, String password) throws SQLException {
		return db.verifyUser(userName, password);
	}

	public ArrayList<CarsForPane> getCars() {
		return cars;
	}

	public ArrayList<String> getGamblers() {
		return gamblers;
	}

	public int getTotalBets() {
		return totalBets;
	}

	public ArrayList<Bet> getBets() {
		return bets;
	}

	public int getBetsCounter() {
		return betsCounter;
	}

	public void setBetsCounter(int betsCounter) {
		this.betsCounter = betsCounter;
	}
	
	public void setFinishRaceScreen(String winningCar) {
		border_pane.setCenter(new Label("Car number " + winningCar + " Won the race!\n" + betsStatus.getText()));	
	}
}
