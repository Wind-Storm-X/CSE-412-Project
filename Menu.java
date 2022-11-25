package application;

import java.util.ArrayList;

public class Menu {
	private String FoodType;
	private ArrayList<String> FoodList;
	
	public Menu(String foodType) {
		FoodType = foodType;
		FoodList = new ArrayList<String>();
	}
}
