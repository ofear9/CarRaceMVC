import java.io.IOException;
import java.sql.SQLException;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is actually the main class of the game.
 * This class control the user access , open the server and start the 3 race screen . 
 * 
 * @author
 *
 */

public class CarRaceMVC extends Application {

	private Button btnNewServer = new Button("Start New Server");
	private Button btnNewClient = new Button("Start a Race Session !");
	private Button btnSignUp = new Button("Sign UP!");
	private DataBase db;
	private TextField user;
	private PasswordField password, verifyPassword;
	private Label failsOrSuc;
	

	@Override
	public void start(Stage primaryStage) throws IOException {
		// BorderPane pane = new BorderPane();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
       
		
		Text scenetitle = new Text("Welcome To Race Car ! ");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 1, 0, 4, 1);
        
		user = new TextField();
		password = new PasswordField();
		verifyPassword = new PasswordField();
		failsOrSuc = new Label();

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(btnNewServer, btnNewClient);
		HBox hbUserName = new HBox(11);
		HBox hbPassword = new HBox(12);
		HBox hbVerifyPassword = new HBox(5);
		hbUserName.getChildren().addAll(new Label("User name: "), user);
		hbPassword.getChildren().addAll(new Label("Password: "), password);
		hbVerifyPassword.getChildren().addAll(new Label("Verify Password: "), verifyPassword);
		grid.add(hbBtn, 1, 4);
		grid.add(new Label("Don't have account yet? Sign Up now!"), 1, 5);
		grid.add(hbUserName, 1, 6);
		grid.add(hbPassword, 1, 7);
		grid.add(hbVerifyPassword, 1, 8);
		grid.add(btnSignUp, 1, 9);
		grid.add(failsOrSuc, 1, 10);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		// grid.setStyle("-fx-background-color: orange");
	
	
		Scene scene = new Scene(grid, 400, 400);
	
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setTitle("CarRaceMVC"); // Set the stage title
		db = new DataBase();
	
		
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {

					db.trunk();
					Platform.exit();
					System.exit(0);

				} catch (Exception e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnNewServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				startServer();
				btnNewServer.setDisable(true);

			}
		});

		btnNewClient.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new HistoryAndStats();
					new Client(null);
					Thread.sleep(200);
					new Client(null);
					Thread.sleep(200);
					new Client(null);

				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		btnSignUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String userName = user.getText();
				String pass = password.getText();
				String verPass = verifyPassword.getText();
				if (pass.equals(verPass)) {
					try {
						if (!db.isUserNameExist(userName)) {
							db.insertNewUser(userName, pass);
							failsOrSuc.setText("New user created!!");
						} else
							failsOrSuc.setText("User name allready exist in the data base!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else
					failsOrSuc.setText("Passwords does not match!");
			}

		});

		primaryStage.show(); // Display the stage
		primaryStage.setAlwaysOnTop(true);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void startServer() {
		new Server();
	}

	public void createNewWindow() throws ClassNotFoundException, IOException {

	}
}