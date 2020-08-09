package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explorer {
	private Map map;
	public void makeMap() {
		map = new Map(20,20);
		while(!map.findPath(5, 5, 20, 20, 15,3,3));
		map.makeRoomConnected();
		System.out.println(map.showMap());
		List<IEvent> events = new ArrayList<IEvent>();
		IEvent combat1 = new Combat();
		List<ICharacters> characters = new ArrayList<ICharacters>();
		characters.add(new Monster("Wolf1",50,2));
		combat1.setupEvent("Wolves", characters);
		events.add(combat1);
		IEvent combat2 = new Combat();
		characters = new ArrayList<ICharacters>();
		characters.add(new Monster("Heks1",25,8));
		combat2.setupEvent("Witches", characters);
		//IEvent marchant1 =  new Marchant();
		//marchant1.setupEvent("Potions seller", null);
		//events.add(marchant1);
		events.add(combat2);
		map.setEvent(events);
		System.out.println();
		System.out.println("----------------------------------------------------");
		System.out.println();
		
		System.out.println(map.showMapWithEvent());
	}
	
	public void letsExplore() throws IOException {
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
		boolean end = false;
		String answer = null;
		int x = map.getMainPath().get(0).getX();
		int y = map.getMainPath().get(0).getY();
		Room room = map.getMap()[y][x];
		int myHP = 100;
		int totalHP = 0;
		while(!end) {
			System.out.println(map.showMap(x,y));
			if(!room.setEvent(null)) {
				switch(room.getEvent().showEvent()) {
			        case "Combat":
			        	boolean stillFighting = true;
			        	List<ICharacters> monsters = new ArrayList<ICharacters>();
			        	monsters.clear();
			        	do {
			        		
			        		monsters = room.getEvent().getCharacters();
			        		for (int i=0; i<monsters.size(); i++) {
			        			if(monsters.get(i).getHP() > 0) {
			        				System.out.println(i +". " + monsters.get(i).toString());
			        			}			        		    
			        		}
			        		//TODO repeat till de user give a goed answer
			        		System.out.println("Welke wil je aanvallen?");
			        		answer = reader.readLine();
			        		String str = answer.replaceAll("\\D+","");
			        		if(!str.isEmpty() && Integer.parseInt(str)<monsters.size() ) {
			        			monsters.get(Integer.parseInt(str)).getAttacked(25);
			        			myHP -= monsters.get(Integer.parseInt(str)).attack();
			        		} else {
			        			for(int i = 0; i<monsters.size(); i++) {
			        				if(monsters.get(i).getHP() >0) {
			        					myHP -= monsters.get(i).attack();
			        					break;
			        				}
			        			}			        			
			        		}
			        		System.out.println("De monster val je aan?");
			        		
			        		System.out.println("HP : " + myHP);
			        		System.out.println(monsters.get(Integer.parseInt(answer)).toString());
			        		if(answer == "giveup") {
			        			stillFighting = false;
			        		}
			        		//System.out.println("HP : " + myHP);
			        		totalHP = 0;
			        		for(ICharacters monster : monsters) {
			        			totalHP += monster.getHP();
			        		}
			        		if(totalHP==0){
			        			stillFighting = false;
			        		}
			        	}while(stillFighting);
			        	break;
			        case "Market":
			        	
			        	break;
			        default:
			    }
			}		
			System.out.println(room.toString());
			System.out.println(room.showWays());
			//Player choose a direction
			answer = reader.readLine();
	        System.out.println(answer);
	        switch(answer) {
	        //Go to the  room left
	        case "left":  case "l":  case "oost": case "o":	        	
	        	if(room.goLeft) {
		        	x -= 1;
		        	room = map.getMap()[y][x];
				}
	        	break;
	        //Go to the room up
	        case "top" : case "t":  case "noord": case "n":	        	
	        	if(room.goUp) {
					y -= 1;
		        	room = map.getMap()[y][x];
				}
	        	break;
	        // Go to the room right
	        case "right": case "r": case "west": case "w":	        	
	        	if(room.goRight) {
					x += 1;
		        	room = map.getMap()[y][x];
				}
	        	break;
	        // Go to the room down
	        case "down": case "d": case "zuid": case "z":	        	
	        	if(room.goDown) {
					y += 1;
		        	room = map.getMap()[y][x];
				}
	        	break;
	        // End 
	        case "end": case "e": case "eind":	        	
	        	end = true;
	        	break;
	        default:
	        	System.out.println(room.toString());
				System.out.println(room.showWays());
	      }
		}
	}
	
}
