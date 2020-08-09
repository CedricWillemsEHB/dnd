package model;



public class test {
	public static void main(String[] args) {
		Room room = new Room(5,7);
		String s = Serializator.serializeRoom(room);
		System.out.println(s);
		Room m = Serializator.deserializeRoom(s);

		System.out.println(m.toString());
		
	}
	
	
}
