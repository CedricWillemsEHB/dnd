import model.Room;

public class Progam {
    public static void main(String[] args) {
        Room[][] map = new Room[5][5];
        Room room;
        for (int y = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                room = new Room(x,y);
                map[x][y] = room;
            }
        }
        System.out.println(map.toString());
        System.console();
    }
}
