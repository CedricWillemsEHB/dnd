package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Serializator {
 public static String serializeMap(List<Room> map) {
	 String serializedObject = "";

	 try {
	     ByteArrayOutputStream bo = new ByteArrayOutputStream();
	     ObjectOutputStream so = new ObjectOutputStream(bo);
	     so.writeObject(map);
	     so.flush();
	     serializedObject = bo.toString();
	 } catch (Exception e) {
	     System.out.println(e);
	 }
	 return serializedObject;
 }
 public static String serializeRoom(Room room) {
	 String serializedObject = "";

	 try {
	     ByteArrayOutputStream bo = new ByteArrayOutputStream();
	     ObjectOutputStream so = new ObjectOutputStream(bo);
	     so.writeObject(room);
	     so.flush();
	     serializedObject = bo.toString();
	 } catch (Exception e) {
	     System.out.println(e);
	 }
	 return serializedObject;
 }
 @SuppressWarnings("unchecked")
public static List<Room> deserializeMap(String map) {
	 List<Room> obj = null;
	 try {
	     byte b[] = map.getBytes(); 
	     ByteArrayInputStream bi = new ByteArrayInputStream(b);
	     ObjectInputStream si = new ObjectInputStream(bi);
	     obj = (List<Room>) si.readObject();
	 } catch (Exception e) {
	     System.out.println(e);
	 }
	 return obj;
 }
 public static Room deserializeRoom(String room) {
	 Room obj = null;
	 try {
	     byte b[] = room.getBytes(); 
	     ByteArrayInputStream bi = new ByteArrayInputStream(b);
	     ObjectInputStream si = new ObjectInputStream(bi);
	     obj = (Room) si.readObject();
	 } catch (Exception e) {
	     System.out.println(e);
	 }
	 return obj;
 }
}
