package model;

public class Room {
    int x;
    int y;
    boolean accessible;
    public Room(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean getAccessible(){
        return accessible;
    }
    public void setAccessible( boolean accessible){
        this.accessible = accessible;
    }
}
