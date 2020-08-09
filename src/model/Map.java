package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
	Room[][] map;
	List<Room> mainPath = new ArrayList<Room>();
	List<Room> subPaths = new ArrayList<Room>();
	int x;
	int y;
	
	
	
	public Map(int x, int y) {
		
		super();
		this.map = new Room[y][x];
		this.x = x;
		this.y = y;
		for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
                Room room = new Room(w,h);
                map[h][w] = room;
            }
        }
		// TODO Auto-generated constructor stub
	}
	

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public Room[][] getMap() {
		return map;
	}


	public void setMap(Room[][] map) {
		this.map = map;
	}
	
	public void setEvent(List<IEvent> events) {
		int randomNum;
		int x;
		int y;
		Random rand;
		for(int i = 0; i < events.size(); i++) {		
			rand = new Random();
			do {
				randomNum =  rand.nextInt((getMainPath().size() -1) ) + 1;
				x = getMainPath().get(randomNum).getX();
				y = getMainPath().get(randomNum).getY();
			}while(!getMap()[y][x].setEvent(events.get(i)));
		}
	}


	public List<Room> getMainPath() {
		return mainPath;
	}


	public void setMainPath(List<Room> path) {
		this.mainPath = path;
	}
	
	public List<Room> getSubPaths() {
		return subPaths;
	}


	public void setSubPaths(List<Room> subPaths) {
		this.subPaths = subPaths;
	}
	public void setMapWithPaths() {
		for(Room r : mainPath ) {
			this.map[r.getY()][r.getX()].setAccessible(true);
		}
	}
	//Connect every room to make the map playable
	public void makeRoomConnected() {
		//Initialize the dimensions of the map
		int minx = x;
		int miny = y;
		int maxx = 0;
		int maxy = 0;
		for (int y2 = 0; y2 < y; y2++){
        	for(int x2 = 0; x2 < x; x2++) {
        		//Look for each room if it is accessible
        		if(this.map[y2][x2].getAccessible()) {
        			//Set direction true if the room in the direction is accessible
        			if(y2>0)
        				if(this.map[y2-1][x2].getAccessible())
        					this.map[y2][x2].setGoUp(true);
        			if(y2<y)
        				if(this.map[y2+1][x2].getAccessible())
        					this.map[y2][x2].setGoDown(true);
        			if(x2<x)
        				if(this.map[y2][x2+1].getAccessible())
        					this.map[y2][x2].setGoRight(true);
        			if(x2>0)
        				if(this.map[y2][x2-1].getAccessible())
        					this.map[y2][x2].setGoLeft(true);
        			//Set the dimensions 
        			if(minx>x2)
        				minx = x2;
        			if(miny>y2)
        				miny = y2;
        			if(maxx<x2)
        				maxx = x2;
        			if(miny<y2)
        				maxy = y2;
        		}        		
            }            
        }
		//TODO: Remade the dimension of the map
		this.x = maxx +1;
		this.y = maxy +1;
		
	}
	public void makeRoomConnectedWithPath() {
		int maxx = 0;
		int maxy = 0;
		for(int i = 0; i < this.mainPath.size(); i++) {
        	//System.out.println(path.get(i).toString());
        	int x = this.mainPath.get(i).getX();
        	int y = this.mainPath.get(i).getY();
        	if(i > 0) {
        		if(x < this.mainPath.get(i-1).getX()) 
        			this.map[y][x].setGoRight(true);
            	
            	if(x > this.mainPath.get(i-1).getX())
            		this.map[y][x].setGoLeft(true);
            	
            	if(y > this.mainPath.get(i-1).getY()) 
            		this.map[y][x].setGoUp(true);
            	
            	if(y < this.mainPath.get(i-1).getY())
            		this.map[y][x].setGoDown(true);
        	} 
        	if(i < mainPath.size()-1) {
        		if(x < mainPath.get(i+1).getX()) 
        			map[y][x].setGoRight(true);
        		
	        	if(x > mainPath.get(i+1).getX())
	        		map[y][x].setGoLeft(true);
	        	
	        	if(y > mainPath.get(i+1).getY()) 
	        		map[y][x].setGoUp(true);
	        	
	        	if(y < mainPath.get(i+1).getY())
	        		map[y][x].setGoDown(true);
        	}
        	if(maxx < this.mainPath.get(i).getX()) {
        		maxx = this.mainPath.get(i).getX();
        	}
        	if(maxy < mainPath.get(i).getY()) {
        		maxy = mainPath.get(i).getY();
        	}
        }
		for(int i = 0; i < this.subPaths.size(); i++) {
        	//System.out.println(path.get(i).toString());
        	int x = this.subPaths.get(i).getX();
        	int y = this.subPaths.get(i).getY();
        	if(i > 0) {
        		if(x < this.subPaths.get(i-1).getX()) 
        			this.map[y][x].setGoRight(true);
            	
            	if(x > this.subPaths.get(i-1).getX())
            		this.map[y][x].setGoLeft(true);
            	
            	if(y > this.subPaths.get(i-1).getY()) 
            		this.map[y][x].setGoUp(true);
            	
            	if(y < this.subPaths.get(i-1).getY())
            		this.map[y][x].setGoDown(true);
        	} 
        	if(i < subPaths.size()-1) {
        		if(x < subPaths.get(i+1).getX()) 
        			map[y][x].setGoRight(true);
        		
	        	if(x > subPaths.get(i+1).getX())
	        		map[y][x].setGoLeft(true);
	        	
	        	if(y > subPaths.get(i+1).getY()) 
	        		map[y][x].setGoUp(true);
	        	
	        	if(y < subPaths.get(i+1).getY())
	        		map[y][x].setGoDown(true);
        	}
        	if(maxx < this.subPaths.get(i).getX()) {
        		maxx = this.subPaths.get(i).getX();
        	}
        	if(maxy < subPaths.get(i).getY()) {
        		maxy = subPaths.get(i).getY();
        	}
        }
		this.x = maxx +1;
		this.y = maxy +1;
		
	}
	public String showMapWithEvent() {
		String str = "";
		for (int i = 0; i < this.x; i++) {
			str += "___";
		}
		str += "\n";
        for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
            	if(map[h][w].isGoLeft()) {
            		str += " ";
            	} else {
            		str += "|";
            	}
            	if(map[h][w].setEvent(null)) {
            		if(map[h][w].isGoDown()) {
                		str += " ";
                	} else {
                		str += "_";
                	}
            	} else {
            		str += "*";
            	}
            	
            	if(map[h][w].isGoRight()) {
            		str += " ";
            	} else {
            		str += "|";
            	}
            }
            str += "\n";
        }
        
        return str; 
    }
	
	public String showMap() {
		String str = "";
		for (int i = 0; i < this.x; i++) {
			str += " _ ";
		}
		str += "\n";
        for (int h = 0; h < y; h++){
            for(int w = 0; w < x; w++){
            	if(map[h][w].isGoLeft()) {
            		str += " ";
            	} else {
            		str += "|";
            	}
            	if(map[h][w].isGoDown()) {
            		str += " ";
            	} else {
            		str += "_";
            	}
            	if(map[h][w].isGoRight()) {
            		str += " ";
            	} else {
            		str += "|";
            	}
            }
            str += "\n";
        }
        
        return str; 
    }
	public String showMap(int findx, int findy) {
		String str = "";
        for (int h = 0; h < y; h++){
        	for(int t = 0; t <3; t++) {
        		for(int w = 0; w < x; w++){
        			if(t==0) {
        				str += "|";
            			if(map[h][w].isGoUp()) {
            				str += " ";                		
	                	} else {
	                		str += "_";
	                	}
            			str += "|";
            		}
            		if(t==1) {
            			if(map[h][w].isGoLeft()) {
            				str += " ";                		
	                	} else {
	                		str += "|";
	                	}
            			if(map[h][w].equals(map[findy][findx])) {
	                		str += "*";
	                	} else {
	                		str += " ";
	                	}
	                	if(map[h][w].isGoRight()) {
	                		str += " ";
	                	} else {
	                		str += "|";
	                	}
            		} 
            		if(t==2) {
            			str += "|";
            			if(map[h][w].isGoDown()) {
                    		str += " ";
                    	} else {
                    		str += "_";
                    	}
            			str += "|";
            		}
            		
            	}
        		str += "\n";
            }
            
        }
        
        return str; 
    }
	
	public boolean findPath(int x, int y, int xmax, int ymax, int minDistance, int maxBranch, int lengthBranch) {
		while(!findMainPath(x, y, xmax, ymax, minDistance));
		int i = 0;
		while( i <= maxBranch) {
			Random rand = new Random();
		    int randomNum =  rand.nextInt((mainPath.size())-3);
		    Room r = mainPath.get(randomNum);
			if(findSubPath(r.getX(), r.getY(), xmax, ymax, lengthBranch, true)) {
				i++;
			}
		}
		
		return true;
	}
	
	public boolean findSubPath(int x, int y, int xmax, int ymax, int minDistance, boolean fristLoop) {
		if(fristLoop)
			minDistance++;
		//Room goal = new Room(3,3);
		Room r =new Room(x,y);
		
		if(minDistance == 0) return true;
		if(!fristLoop)
			if(map[y][x].getAccessible()) return false;		
		map[y][x].setAccessible(true);
		Random rand = new Random();
	    int randomNum =  rand.nextInt((4) + 1) + 1;
	    switch(randomNum) {
	    	case 1: 
	    		if(y>0) {
	    			if(findSubPath(x, y - 1, xmax, ymax, minDistance -1, false)) {
	    				if(!fristLoop)
	    					subPaths.add(r);
	    				return true;
	    			}
	    				
	    			break;
	    		}
	    	case 2: 
	    		if(x>0) {
	    			if(findSubPath(x - 1, y, xmax, ymax, minDistance -1, false)){
	    				if(!fristLoop)
	    					subPaths.add(r);
	    				return true;
	    			}
	    			break;
	    		}
	    	case 3: 
	    		if(y<ymax-1) {
	    			if(findSubPath(x, y + 1, xmax, ymax, minDistance -1, false)) {
	    				if(!fristLoop)
	    					subPaths.add(r);
	    				return true;
	    			}
	    			break;
	    		}
	    	case 4: 
	    		if(x<xmax-1) {
	    			if(findSubPath(x + 1, y, xmax, ymax, minDistance -1, false)){
	    				if(!fristLoop)
	    					subPaths.add(r);
	    				return true;
	    			}
	    			break;
	    		}
		    }
	    if(!fristLoop)
	    	subPaths.remove(r);
	    if(!fristLoop)
	    	map[y][x].setAccessible(false);
		return false;
	}
	
	public boolean findMainPath(int x, int y, int xmax, int ymax, int minDistance) {

		//Room goal = new Room(3,3);
		Room r =new Room(x,y);
		
		if(minDistance == 0) return true;
		if(map[y][x].getAccessible()) return false;		
		map[y][x].setAccessible(true);
		Random rand = new Random();
	    int randomNum =  rand.nextInt((4) + 1) + 1;
	    switch(randomNum) {
	    	case 1: 
	    		if(y>0) {
	    			if(findMainPath(x, y - 1, xmax, ymax, minDistance -1)) {
	    				mainPath.add(r);
	    				return true;
	    			}
	    				
	    			break;
	    		}
	    	case 2: 
	    		if(x>0) {
	    			if(findMainPath(x - 1, y, xmax, ymax, minDistance -1)){
	    				mainPath.add(r);
	    				return true;
	    			}
	    			break;
	    		}
	    	case 3: 
	    		if(y<ymax-1) {
	    			if(findMainPath(x, y + 1, xmax, ymax, minDistance -1)) {
	    				mainPath.add(r);
	    				return true;
	    			}
	    			break;
	    		}
	    	case 4: 
	    		if(x<xmax-1) {
	    			if(findMainPath(x + 1, y, xmax, ymax, minDistance -1)){
	    				mainPath.add(r);
	    				return true;
	    			}
	    			break;
	    		}
		    }
		mainPath.remove(r);
		map[y][x].setAccessible(false);
	return false;
	}
}
