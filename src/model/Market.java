package model;

import java.util.List;

public class Market implements IEvent {
	private String name;
	private ICharacters marchant;
	
	@Override
	public boolean setupEvent(String name, List<ICharacters> characters) {
		// TODO Auto-generated method stub
		if(!name.isEmpty()) {
			this.name = name;
			return true;
		}
		return false;
	}

	@Override
	public String showEvent() {
		// TODO Auto-generated method stub
		return "This event is: " + name;
	}

	@Override
	public List<ICharacters> getCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

}
