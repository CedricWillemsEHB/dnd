package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.Serializator;

public class GameServer {
	private ServerSocket ss;
	private List<ServerSideConnection> players;
	private int numPlayers= 0;
	private int numDungeonmaster;
	private boolean partyReady;
	
	public GameServer() {
		players = new ArrayList<ServerSideConnection>();
		try {
			ss = new ServerSocket(51734);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while(true) {
				if(ss != null) {
					Socket s = ss.accept();
					
					System.out.println("Player #" + (numPlayers + 1) + " has connected.");
					ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
					players.add(ssc);
					Thread t = new Thread(ssc);
					t.start();
					numPlayers++;
				}
				
				if(numPlayers == 3) {
					break;
				}
			}
			
			System.out.println("We have enough players. No longer accpting connections.");			
		}catch(IOException ex) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
	private class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut; 
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch (IOException ex) {
				System.out.println("IOException from run() SSC");
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String command = "";
			String tmp = "";
			int receiverID= 0;
			boolean firstTime = true;
			try {
				//dataOut.writeInt(playerID);
				
				List<String> listStrings = new ArrayList<String>();
				List<Room> listRooms = new ArrayList<Room>();
				while(true) {
					//TODO
						int condition=dataIn.readInt();
						switch(condition) {
						case 1:
							if(firstTime) {
								numDungeonmaster = playerID;
								firstTime = false;
							}
							int maxRoom = dataIn.readInt();
							for(int i = 0 ; i < maxRoom; i++) {
								int length = dataIn.readInt();
								byte[] data=new byte[length];
								dataIn.readFully(data);
								String str=new String(data,"UTF-8");
								listStrings.add(str);
								Room r = Serializator.deserializeRoom(str);
								listRooms.add(r);
								System.out.println(r.toString());
							}
							receiverID=0;
							for(ServerSideConnection player : players) {
								if(receiverID!=playerID) {
									player.dataOut.writeInt(1);
									player.dataOut.writeInt(maxRoom);
									for(Room str : listRooms) {										
										player.sendRoom(str);
									}
								}
								receiverID++;
							}
							break;
						case 2:
							receiverID=0;
							for(ServerSideConnection player : players) {
								if(receiverID!=playerID) {
									player.dataOut.writeInt(2);
									dataOut.flush();
								}
								receiverID++;
							}
							break;
						case 3:
							receiverID=0;
							for(ServerSideConnection player : players) {
								if(receiverID!=playerID) {
									player.dataOut.writeInt(3);
									dataOut.flush();
								}
								receiverID++;
							}
							break;
						case 4:
							receiverID=0;
							for(ServerSideConnection player : players) {
								if(receiverID!=playerID) {
									player.dataOut.writeInt(4);
									dataOut.flush();
								}
								receiverID++;
							}
							break;
						case 5:
							receiverID=0;
							for(ServerSideConnection player : players) {
								if(receiverID!=playerID) {
									player.dataOut.writeInt(5);
									dataOut.flush();
								}
								receiverID++;
							}
							break;
						}
				} 
			}catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		public void sendRoom(Room r) {
			String str;
			try {
				str = Serializator.serializeRoom(r);
				byte[] data=str.getBytes("UTF-8");
				dataOut.writeInt(data.length);
				dataOut.write(data);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IOException from sendButtonNum() SSC");
			}
		}
		
		public void sendButtonNum(int n) {
			try {
				dataOut.writeInt(n);
				dataOut.flush();
			} catch (IOException ex) {
				System.out.println("IOException from sendButtonNum() SSC");
			}
		}
		
		public void closeConnection() {
			try {
				dataOut.close();
				System.out.println("Connection closed");
			} catch (IOException ex) {
				System.out.println("IOException from closeConnection() SSC");
			}
		}
	}
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}
}
