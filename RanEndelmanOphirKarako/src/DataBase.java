import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class represents all of the DB functions for this game. this is the only class that actually contact to DB
 * @author Ophir Karako and Ran Endlman 
 *
 */


public class DataBase {

	private static String user = "scott";
	private static String pass = "tiger";
	private Statement statement;
	private Connection connection;

	public DataBase() {
		try { // Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded");
			// Establish a connection
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost/carrace?useSSL=false", user,
					pass);
			// jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false
			System.out.println("Database connected");
			// Create a statement
			this.statement = connection.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This class take the  relevance car for each race.
	 * There is 15 cars in the DB - 0 - 4 is race 1 , 5-9 are race 2 ...
	 * Every time that clinet is on  it ask for the relevance car .
	 * @param low - the low car id in the current race 
	 * @param hige - the high car id in the current race 
	 * @param raceNum - the number of the race 
	 * @return cars - an array list of CarsForPane (this is how we save the cars on the DB ) 
	 * @throws SQLException
	 */
	public synchronized ArrayList<CarsForPane> getCarsFromDB(int low, int hige, int raceNum) throws SQLException {
		ArrayList<CarsForPane> cars = new ArrayList<>();
		ResultSet rs;
		String generalQuery = "SELECT * FROM `cars` WHERE Num<" + (hige) + " AND Num>= " + (low);
		rs = this.statement.executeQuery(generalQuery);
		while (rs.next()) {
			cars.add(new CarsForPane(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		}

		saveCurrRaceToDb(cars, raceNum);
		// connection.close();
		return cars;
	}
/**
 * Save the current race to DB
 * 
 * @param cars - which cars are in the current race 
 * @param raceNum - the race number 
 * @throws SQLException
 */
	public synchronized void  saveCurrRaceToDb(ArrayList<CarsForPane> cars, int raceNum) throws SQLException {
		String generalQuery;
		for (CarsForPane car : cars) {
			String st = "'" + raceNum + "','" + car.getName() + "','" + car.getColors() + "','" + car.getNum()
					+ "','0','0'";
			generalQuery = "INSERT INTO `races`(`RaceNum`, `Car`, `Color`, `CarNum`,`NumBets`, `TotalBets`) VALUES ("
					+ st + ")";
			statement.executeUpdate(generalQuery);
		}
	}
/**
 * Return an array of  the relevance cars to show on the race "choose car to bet:"  comboBox 
 * 
 * @param raceNum  - The race number 
 * @return - An array of cars in String type. 
 * @throws SQLException
 */
	
	public synchronized ArrayList<String> getCarsForBets(int raceNum) throws SQLException {
		ArrayList<String> cars = new ArrayList<>();
		ResultSet rs;
		String generalQuery = "SELECT * FROM `races` WHERE `RaceNum`=" + raceNum + " Limit 5";
		rs = this.statement.executeQuery(generalQuery);
		while (rs.next()) {
			cars.add(rs.getString(3) + " " + rs.getString(2) + " " + rs.getString(4));
		}
		return cars;
	}

	/**
	 * Updates the status of the bets in the current race 
	 * 
	 * @param raceNum - the race number 
	 * @param carNum - the car number 
	 * @param amount - the amount of bets 
	 * @throws SQLException
	 */
	public synchronized void updateBets(int raceNum, int carNum, int amount) throws SQLException {
		String generalQuery;
		generalQuery = "UPDATE `races` SET `NumBets`= NumBets + 1,`TotalBets`= TotalBets + " + amount
				+ " WHERE `CarNum` = " + carNum;
		statement.executeUpdate(generalQuery);

	}

	/**
	 * Trunk the current race table every time the game is close.
	 * @throws SQLException
	 */
	
	public  synchronized void trunk() throws SQLException {
		String s = "TRUNCATE TABLE `races`";
		statement.executeUpdate(s);
	}

/**
 * Verify the user and password
 * 
 * @param name
 * @param pass
 * @return
 * @throws SQLException
 */
	public synchronized boolean verifyUser(String name, String pass) throws SQLException {
		ResultSet rs;
		String generalQuery = "SELECT * FROM `accounts` WHERE `UserName`= '" + name + "' AND `Password`= '" + pass + "'";
		rs = statement.executeQuery(generalQuery);

		if (rs.isBeforeFirst())
			return true;
		return false;
	}
/**
 * Save a summary of every race to the DB.
 * 
 * @param ID - the Id of the race
 * @param dateAndTime - the date and time of the race 
 * @param cars - which cars was participated on this race 
 * @param players  - which gamblers gamble on this race 
 * @param winner - who is the winner of the game 
 * @param totalBets  
 * @throws SQLException
 */
	
	
	public synchronized void insertRaceToDB(String ID, String dateAndTime, String cars, String players, String winner,
			String totalBets) throws SQLException {
		int systemProfit = (int) (Integer.parseInt(totalBets) * 0.05);
		String st = "'" + ID + "','" + dateAndTime + "','" + cars + "','" + players + "','" + winner + "','" + totalBets
				+ "','" + systemProfit + "'";
		String generalQuery = "INSERT INTO `raceshistory`(`ID`, `DateAndTime`, `Cars`, `Players`,`Winner`, `TotalBets`, `systemProfits`) VALUES ("
				+ st + ")";
		statement.executeUpdate(generalQuery);
	}

	/**
	 * Get any table from the DB.
	 * 
	 * @param queryString
	 * @return - A result set of the query.
	 * @throws SQLException
	 */
	public synchronized ResultSet getTable(String queryString) throws SQLException {
		return statement.executeQuery(queryString);
	}

	/**
	 * Return the last ID of last race. 
	 * 
	 * @return id - the highest ID of the race /The last ID race 
	 * @throws SQLException
	 */
	
	public synchronized int getHighestIDFromRaces() throws SQLException {
		int id = Integer.MIN_VALUE;
		String queryString = "SELECT `ID` FROM `raceshistory` ORDER BY `ID` DESC LIMIT 1";
		ResultSet resultSet = statement.executeQuery(queryString);
		if (resultSet.next())
			id = resultSet.getInt("id");
		return id;
	}
/**
 * Insert a new user to the system .
 * 
 * @param userName
 * @param pass
 * @throws SQLException
 */
	
	public synchronized void insertNewUser(String userName, String pass) throws SQLException {
		String st = "'" + userName + "','" + pass + "','" + 0 + "'";
		String generalQuery = "INSERT INTO `accounts`(`UserName`, `Password`, `Profits`) VALUES (" + st
				+ ")";
		statement.executeUpdate(generalQuery);
	}

	/**
	 * Check if the user exist in the DB.
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public synchronized boolean isUserNameExist(String name) throws SQLException {
		String queryString = "SELECT * FROM `accounts` WHERE `UserName`= '" + name + "'";
		ResultSet resultSet = statement.executeQuery(queryString);
		if (resultSet.next())
			return true;
		return false;
	}
	
	/**
	 * Update the profit for specific user.
	 * @param userName
	 * @param profitToAdd - how much profit we need to add the user. 
	 * @throws SQLException
	 */
	public synchronized void updateProfitsForUser(String userName, int profitToAdd) throws SQLException {
		int currentProfits;
		int updatedProfits;
		String queryString = "SELECT `Profits` FROM `accounts` WHERE `UserName`= '" + userName + "'";
		ResultSet resultSet = statement.executeQuery(queryString);
		if (resultSet.next()) {
			currentProfits = resultSet.getInt("Profits");
			updatedProfits = currentProfits + profitToAdd;
			queryString = "UPDATE `accounts` SET `Profits`=" + updatedProfits + " WHERE `UserName`= '" + userName + "'";
			statement.executeUpdate(queryString);
		}
	}
	
	/**
	 * Insert a gamblers action the to the relevance table . this table allow us to save all of the details about the gamblers .
	 * 
	 * @param gamblerName
	 * @param raceID
	 * @param carGambeldOn
	 * @param amount
	 * @param profit
	 * @throws SQLException
	 */
	public synchronized void insertNewGamblerRow(String gamblerName, String raceID, String carGambeldOn, int amount, int profit) throws SQLException {
		String st = "'" + gamblerName + "','" + raceID + "','" + carGambeldOn + "','" + amount + "','" + profit + "'";
		String generalQuery = "INSERT INTO `gambles`(`gamblerName`, `raceID`, `carGambeldOn`, `amount`, `profit`) VALUES"
				+ " (" + st + ")";
		statement.executeUpdate(generalQuery);	
	}
}
