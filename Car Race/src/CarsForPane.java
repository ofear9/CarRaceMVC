/**
 * @ This class represents how car woulb be save on the DB and shows on the pane
 * 
 * @author Ophir karako and Ran endelman 
 * @param name - the name of the car , the manufartor
 * @param type - the type of the car- sport, truck or drug
 * @color color - the color of the car
 * @param int - the number of the car
 * 
 * All of the functions are get or set
 */


class CarsForPane {
 private String name;
 private String type;
 private String color;
 private int num;
 
 
public CarsForPane(String name, String type, String color,int num) {
	this.name = name;
	this.type = type;
	this.color = color;
	this.num = num;
}

public int getNum() {
	return num;
}

public void setNum(int num) {
	this.num = num;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getColors() {
	return color;
}

public void setColors(String colors) {
	this.color = colors;
}
 
 
 
}