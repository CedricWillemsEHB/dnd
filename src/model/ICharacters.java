package model;

import java.util.List;

public interface ICharacters {
	boolean setupCharater();
	List<String> showActoins();
	int getHP();
	void getAttacked(int dommage);
	int attack();
}
