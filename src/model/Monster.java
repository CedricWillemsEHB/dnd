package model;

import java.util.List;

public class Monster implements ICharacters{
	private String name;
	private int hp;
	private int attack;
	public Monster(String name, int hp, int attack) {
		super();
		this.name = name;
		this.hp = hp;
		this.attack = attack;
	}
	@Override
	public String toString() {
		return "Monster ["+ name + ", hp=" + hp + ", attack="+attack+"]";
	}
	
	public void doSomething() {
		
	}
	@Override
	public boolean setupCharater() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<String> showActoins() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getHP() {
		// TODO Auto-generated method stub
		return hp;
	}
	@Override
	public void getAttacked(int dommage) {
		// TODO Auto-generated method stub
		this.hp -= dommage;
		if(this.hp < 0) {
			this.hp = 0;
		}
	}
	@Override
	public int attack() {
		// TODO Auto-generated method stub
		return attack;
	}
}
