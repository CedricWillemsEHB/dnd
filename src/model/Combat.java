package model;

import java.util.List;

public class Combat implements IEvent {
	private String name;
	List<ICharacters> monsters;
	@Override
	public boolean setupEvent(String name, List<ICharacters> characters) {
		if(!name.isEmpty()) {
			this.name = name;
			if(!characters.isEmpty()) {
				this.monsters = characters;
			}
			return true;
		}
		return false;
	}
	@Override
	public String showEvent() {
		// TODO Auto-generated method stub
		
		return this.getClass().getSimpleName();
	}
	@Override
	public List<ICharacters> getCharacters() {
		// TODO Auto-generated method stub
		return monsters;
	}

}
