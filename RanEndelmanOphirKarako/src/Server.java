import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is the server class of the game . 
 * Before the race start the user must load the server.
 * The server send a model for each client.
 * The server log contain all the logs of the game. 
 * 
 * @author Ophir Karako and Ran Endelman
 *
 */
public class Server implements Serializable {
	private static final long serialVersionUID = -3815780478499501933L;
	private TextArea ta = new TextArea();
	private ServerSocket serverSocket;
	private Socket socket;
	private int threadNum = 0;
	private int clientCounter = 1;
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	private ArrayList<Model> users = new ArrayList<Model>();
	private ObjectOutputStream outputToClient;
	private ObjectInputStream inputFromClient;
	private int carNums = 0;
	private int carLim = 5;
	// private Model model = null;

	public Server() { // Create a scene and place it in
		Stage primaryStage = new Stage(); // the stage
		ta.setEditable(false);
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Car Race Server"); // Set the stage
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Displsay the stage
		primaryStage.setAlwaysOnTop(true);
		// model = new Model();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.exit();
				System.exit(0);
			}
		});
		new Thread(() -> {
			try { // Create a server socket
				serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> {

					ta.appendText("Server started \n\n");
				});

				while (true) { // Listen for a new connection request
					socket = serverSocket.accept();

					// Increment clientNo
					threadNum++;
					Platform.runLater(() -> { // Display the client number
						ta.appendText("\nStarting thread " + threadNum);
						// Find the client's IP address
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("\nNew client connected to the game. Member IP " + inetAddress.getHostAddress());
					});
					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket)).start();
					sockets.add(socket);
				}
			} catch (IOException ex) {
			}
		}).start();
	}

	// Define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		private Model model;

		/** Construct a thread */
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}

		/** Run a thread */
		public void run() {
			try {
				model = new Model(carNums);
				carNums += 5;
				/*
				 * if (carNums >= 15) carNums = 0;
				 */
				outputToClient = new ObjectOutputStream(socket.getOutputStream());
				inputFromClient = new ObjectInputStream(socket.getInputStream());
				outputToClient.writeObject(model);
				outputToClient.writeObject(clientCounter);
				outputToClient.writeObject(carLim);
				carLim += 5;
				/* if (carLim >= 15) */
				users.add(model);
				clientCounter++;
				if (clientCounter > 3)
					clientCounter = 1;
				while (true) {
					try {
						Object input = inputFromClient.readObject();

						if (input instanceof Integer) {
							int LastRaceNum = (int) input;
							carNums = LastRaceNum;
							if (carNums == 1) {
								carLim = 5;
								clientCounter = 1;
							} else if (carNums == 2) {
								carLim = 10;
								clientCounter = 2;
							} else if (carNums == 3) {
								carLim = 15;
								clientCounter = 3;
							}

						}

						if (input instanceof String) {
							String msg = (String) input;
							Platform.runLater(() -> {
								ta.appendText(msg);
							});
						}

					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} catch (SocketException ex) {
				try {
					serverSocket.close();
					socket.close();
				} catch (IOException e) {
				}
			} catch (IOException ex) {
			}
		}
	}

	// public ArrayList<String> getOnlineUsers() {
	// ArrayList<String> onlineUsers = new ArrayList<String>();
	// for (User user : users)
	// if (user.isOnline)
	// onlineUsers.add(user.getNickName());
	// return onlineUsers;
	// }
	//
	// public void logOffUser(String nickName) throws IOException {
	// ArrayList<User> usersToRemove = new ArrayList<User>();
	// for (User user : users)
	// if (user.getNickName().equals(nickName)) {
	// usersToRemove.add(user);
	// clients.remove(user.getNickName());
	// }
	// users.removeAll(usersToRemove);
	// // sendOnlineClientsToAllClients();
	// }

}
