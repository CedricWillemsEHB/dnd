package model;

public class Event {
	enum TypeEvent {
	    Combat,
	    MARCHANT,
	    OTHER
	  }
	private TypeEvent type;
	private String name;
	public Event(String name) {
		super();
		this.name = name;
	}
	public boolean setUpEvent() {
		if(this.type == TypeEvent.Combat) {
			//TODO
			return true;
		}
		return false;
		
	}
}
