import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * This is the controller class of the game - every race have is own controller.
 * This class also handlle the speed chegens and the winner check
 * 
 * @author Dr. Aviv
 *
 */

public class Controller implements CarEvents {
	private final int CAR1_ID = 0;
	private final int CAR2_ID = 1;
	private final int CAR3_ID = 2;
	private final int CAR4_ID = 3;
	private final int CAR5_ID = 4;
	public Timer timer;
	public Timer timer2;
	private Stage stg;
	private Model model;
	private View view;
	private MediaPlayer mediaPlayer1, mediaPlayer2, mediaPlayer3;
	private ArrayList<Car> cars;
	boolean startFlag = false;
    String winCar ="";
	int maxLaps = 0;
	int i;

	public Controller(Model model, View view, ArrayList<CarsForPane> carsForPane)
			throws IOException, InterruptedException {
		this.model = model;
		this.view = view;
		MusicPlayer mp = new MusicPlayer();
		mediaPlayer1 = mp.getStartSound();
		mediaPlayer2 = mp.getSong();
		mediaPlayer3 = mp.getClaps();
		TimerTask start = new TimerTask() {
			@Override
			public void run() {
				if (model.getCanWeStart()) {
					try {
						mediaPlayer1.play();
						Thread.sleep(4750);
						speedHandlers();
						mediaPlayer1.stop();
						mediaPlayer2.play();
						timer2.cancel();
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		timer2 = new Timer();
		timer2.schedule(start, 10, 1000);

	}
/**
 * This method send a random speed to the model for each car - n 
 * 
 * @param n - The number of the car on the model
 * @throws IOException
 * @throws InterruptedException
 */
	public void setSpeedModelView(int n) throws IOException, InterruptedException {

		Random randomGenerator = new Random();
		Double speed = randomGenerator.nextDouble() + randomGenerator.nextInt(60);
		try {
			model.changeSpeed(n, speed);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method change the speed every 3000ms
	 * It work with TimeTask that call setSeedModelView every 3000ms 
	 * This method also check if there is a winner .
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void speedHandlers() throws IOException, InterruptedException {
		/*
		 * mediaPlayer1.play(); Thread.sleep(4750);
		 */
		cars = new ArrayList<>();
		for (int i = 0; i < 5; i++)
			cars.add(model.getCarById(i));

		TimerTask speedChanges = new TimerTask() {
			@Override
			public void run() {
				try {
					setSpeedModelView(CAR1_ID);
					setSpeedModelView(CAR2_ID);
					setSpeedModelView(CAR3_ID);
					setSpeedModelView(CAR4_ID);
					setSpeedModelView(CAR5_ID);
					
					if (model.isWeHaveWinner()) {
						timer.cancel();
						Platform.runLater(() -> {
							view.setFinishRaceScreen("" + model.getWinNum());
						});
							System.out.println(model.getCarById(0).getLapsCount());
							System.out.println(model.getCarById(1).getLapsCount());
							System.out.println(model.getCarById(2).getLapsCount());
							System.out.println(model.getCarById(3).getLapsCount());
							System.out.println(model.getCarById(4).getLapsCount());
							for(int i=0;i<5;i++){
								if (model.getCarById(i).getLapsCount()>maxLaps){
									model.setWinNum(model.getCarById(i).getCarNum());
								}
							}
							System.out.println(model.getWinNum());
				
					
        
						endRace(model.getWinNum());
						System.out.println("The winner is - " + winCar);
					
					}

				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		timer = new Timer();
		timer.schedule(speedChanges, 10, 3000);

	}

	/**
	 * This method end the race and send the relevance parameters to the DB
	 * functions
	 * 
	 * @param winnigCarId
	 *            - the ID of the winning car
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void endRace(int winnigCarId) throws IOException, InterruptedException {
		for (int i = 0; i < 5; i++) {
			cars.get(i).setSpeed(0);
		}
		mediaPlayer2.stop();
		mediaPlayer3.play();
		Thread.sleep(5000);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					ArrayList<Car> cars = model.setCarsType();
					model.insertRaceToDB(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),
							cars.get(0).getType() + ", " + cars.get(1).getType() + ", " + cars.get(2).getType() + ", "
									+ cars.get(3).getType() + ", " + cars.get(4).getType(),
							view.getGamblers().toString(), "" + model.getCarById(winnigCarId).getType(),
							"" + view.getTotalBets());
					System.out.println(winnigCarId);
					System.out.println(model.getCarById(winnigCarId).getType());
					splitProfitsAndInsertToDB(model.getCarById(winnigCarId).getType(), view.getTotalBets());
					model.newRace(view.getCars());
					stg.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void setOwnerStage(Stage stg) {
		this.stg = stg;
	}

	public void errorAlert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(stg);
		alert.setTitle("Error");
		alert.setContentText(msg);
		alert.show();
	}

	/**
	 * This method split the profit of the bets according to the game rules,
	 * 
	 * @param winnigCar
	 *            - the winner car
	 * @param totalBets
	 *            - how much money the gamblers put on this car
	 * @throws SQLException
	 */
	public void splitProfitsAndInsertToDB(String winnigCar, int totalBets) throws SQLException {
		int profit = 0, afterTax, winnersCounter = 0, winnersTotalBets = 0;
		double pivot;
		ArrayList<Bet> bets = view.getBets();
		for (Bet bet : bets) {
			if (bet.getCarGambeldOn().contains(winnigCar)) {
				winnersTotalBets += bet.getAmount();
				winnersCounter++;
			}
		}
		afterTax = (int) (totalBets * 0.95);
		for (Bet bet : bets)
			if (bet.getCarGambeldOn().contains(winnigCar)) {
				if (winnersCounter == 1)
					profit = afterTax - bet.getAmount();
				else if (winnersCounter > 1) {
					pivot = (double) bet.getAmount() / (double) winnersTotalBets;
					profit = ((int) (afterTax * pivot) - bet.getAmount());
				}
				bet.setProfit(profit);
				model.updateProfitsForUser(bet.getGamblerName(), profit);
			} else {
				model.updateProfitsForUser(bet.getGamblerName(), -bet.getAmount());
				bet.setProfit(-bet.getAmount());
			}
		model.insertBetToDB(bets);
	}
}