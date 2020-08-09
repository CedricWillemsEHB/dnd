package model;



public class test {
	public static void main(String[] args) {
		Room room = new Room(9,7);
		String s = Serializator.serializeRoom(room);
		System.out.println(s);
		Room r = Serializator.deserializeRoom(s);
		System.out.println(r.toString());
		
	}
	
	
}
