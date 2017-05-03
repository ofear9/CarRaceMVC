import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
/**
 *  class HistoryAndStats it used to show all Statistic and History reports of the game.
 *  The class open a new stage with table views and show all the statics of the game . 
 *  
 * @author Ophir Karako and Ran Endelman.
 *
 */

public class HistoryAndStats implements Serializable {
	private static final long serialVersionUID = -1580589583154641702L;
	private ComboBox<String> cboTableName = new ComboBox<>();
	private TableView<?> tableView = new TableView<Object>();
	private Button btShowContents = new Button("Show Contents");
	private Label lblStatus = new Label();
	private DataBase db;

	public HistoryAndStats() {
		Stage primaryStage = new Stage();
		HBox hBox = new HBox(5);
		cboTableName.getItems().add("RacesHistory");
		cboTableName.getItems().add("Gambles");
		cboTableName.getItems().add("Accounts");
		hBox.getChildren().addAll(new Label("Table Name"), cboTableName, btShowContents);
		hBox.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(tableView);
		pane.setTop(hBox);
		pane.setBottom(lblStatus);
		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 420, 180);
		primaryStage.setTitle("History & Statistic"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show(); // Display the stage
		initializeDB();
		btShowContents.setOnAction(e -> showContents());
	}

	private void initializeDB() {
		db = new DataBase();
	}
/**
 * This method get the all information for the table view. 
 */
	private void showContents() {
		String queryString;
		String tableName = cboTableName.getValue();
		try {
			if (tableName.equals("Accounts"))
				queryString = "select `UserName`, `Profits` from " + tableName + " ORDER BY `" + tableName + "`.`Profits` ASC";
			else if (tableName.equals("RacesHistory"))
				queryString = "select * from " + tableName + " ORDER BY `" + tableName + "`.`systemProfits` ASC";
			else if (tableName.equals("Gambles"))
				queryString = "select * from " + tableName + " ORDER BY `" + tableName + "`.`gamblerName` ASC";
			else
				queryString = "select * from " + tableName;
			ResultSet resultSet = db.getTable(queryString);
			populateTableView(resultSet, tableView);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
/**
 * This method take the information from the DB and put everything on TableView.
 * 
 * @param rs - The information that got from the DB.
 * @param tableView - TableView
 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTableView(ResultSet rs, TableView tableView) { 
		tableView.getColumns().clear();
		tableView.setItems(FXCollections.observableArrayList());
		try { 
			int numOfColums = rs.getMetaData().getColumnCount();
			for(int i = 1 ; i <= numOfColums ; i++)
			{
				final int columNum = i - 1;
				TableColumn<String[],String> column = new TableColumn<>(rs.getMetaData().getColumnLabel(i));
				column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()[columNum]));
				tableView.getColumns().add(column);
			}
			while(rs.next())
			{
				String[] cells = new String[numOfColums];
				for(int i = 1 ; i <= numOfColums ; i++)
					cells[i - 1] = rs.getString(i);
				tableView.getItems().add(cells);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
