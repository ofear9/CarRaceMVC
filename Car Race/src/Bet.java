/**
 * This class representing a bet on a car on currnet race
 * @author Ophir karako,Ran Endlman
 * @param gamblerName - the gambler name
 * @param raceID - the race id 
 * @param carGambeldOn - the id of the car that the gamble gammbeld on 
 * @param amount - how much the gamble is 
 * @param profit - how much the user get from his bet
 * @ all the functions on this class is get and set
 */

public class Bet {
	private String gamblerName;
	private String raceID;
	private String carGambeldOn;
	private int amount;
	private int profit;
	public Bet(String gamblerName, String raceID, String carGambeldOn, int amount, int profit) {
		super();
		this.gamblerName = gamblerName;
		this.raceID = raceID;
		this.carGambeldOn = carGambeldOn;
		this.amount = amount;
		this.profit = profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}
	public String getGamblerName() {
		return gamblerName;
	}
	public void setGamblerName(String gamblerName) {
		this.gamblerName = gamblerName;
	}
	public String getRaceID() {
		return raceID;
	}
	public void setRaceID(String raceID) {
		this.raceID = raceID;
	}
	public String getCarGambeldOn() {
		return carGambeldOn;
	}
	public void setCarGambeldOn(String carGambeldOn) {
		this.carGambeldOn = carGambeldOn;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getProfit() {
		return profit;
	}
}
