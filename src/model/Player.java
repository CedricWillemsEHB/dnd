package dnd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
	private String name;
	private int hp;
	private int attack;
	private boolean isDM;
	public ClientSideConnection csc;
	
	public Player(String name, int hp, int attack) {
		super();
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.isDM = false;
	}
	public Player(String name, int hp, int attack, boolean isDM) {
		super();
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.isDM = isDM;
	}
	public ClientSideConnection getCsc() {
		return csc;
	}
	public void setCsc(ClientSideConnection csc) {
		this.csc = csc;
	}
	public boolean isDM() {
		return isDM;
	}
	public void setDM(boolean isDM) {
		this.isDM = isDM;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void connectToServer() {
		csc = new ClientSideConnection();
	}
	public class ClientSideConnection{
		
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("----Client----");
			try {
				socket = new Socket("localhost", 51734);
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());			
			} catch (IOException ex) {
				System.out.println("IO Exception from CSC constructor");
			}
		}
		public void sendMakeMap(Map map, int x, int y) {
			try {				
				
				dataOut.writeInt(y);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IO Exception from sendButtonNum() CSC");
			}
		}
		public void sendButtonNum(int n) {
			try {
				dataOut.writeInt(n);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IO Exception from sendButtonNum() CSC");
			}
		}
		public int receiveButtonNum() {
			int n = -1;
			try {
				n = dataIn.readInt();
				System.out.println("Player # clicked button #" + n);
			} catch(IOException ex) {
				System.out.println("IO Exception from receiveButtonNum() CSC");
			}
			return n;
		}
		
		public void closeConnection() {
			try {
				socket.close();
				System.out.println("---CONNECTION CLOSED---");
			} catch(IOException ex) {
				System.out.println("IO Exception from closeConnestion() CSC");
			}
		}
	}

}
