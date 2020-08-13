package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import dnd.Combat;
import dnd.IEvent;
import dnd.Map;
import dnd.Market;
import dnd.Player;
import dnd.Room;
import dnd.Serializator;
import dnd.Player.ClientSideConnection;

public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Toolbar toolbar;
	private ImagePanel imagePanel;
	private Map map;
	private boolean endGame = false;
	private int currentX;
	private int currentY;
	private Room currentRoom;
	private int myHP = 100;
	private Player player;
	private String imageUrl = "image\\default\\";
	
	private ClientSideConnection csc;
	
		public GameFrame() {
			super("Hello world");
			
			setLayout(new BorderLayout());
			
			imagePanel = new ImagePanel();
			toolbar = new Toolbar();
			
			toolbar.setStringListener(new StringListener() {
				@Override
				public void makeMap() throws IOException {
					if (map == null) {
						generatePath();	
					}				
					generateMapImage();
					showMapImage();
					player.getCsc().sendMakeMap(map, currentX, currentY);
				}
	
				@Override
				public void clickNorth() throws IOException {
					// TODO Auto-generated method stub
					goNorth();
					generateMapImage();
					showMapImage();
				}
	
				@Override
				public void clickSouth() throws IOException {
					// TODO Auto-generated method stub
					goSouth();
					generateMapImage();
					showMapImage();
				}
	
				@Override
				public void clickEast() throws IOException {
					// TODO Auto-generated method stub
					goEast();
					generateMapImage();
					showMapImage();
				}
	
				@Override
				public void clickWest() throws IOException {
					// TODO Auto-generated method stub
					goWest();
					generateMapImage();
					showMapImage();
				}			
			});
			add(imagePanel, BorderLayout.CENTER);
			add(toolbar, BorderLayout.SOUTH);
			
			setSize(600,500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 400);
			setVisible(true);
		}
		public GameFrame(Player player) {
			super("Hello world");
			connectToServer();
			setLayout(new BorderLayout());
			
			imagePanel = new ImagePanel();
			toolbar = new Toolbar();
			this.player = player;
			
			toolbar.setStringListener(new StringListener() {
				@Override
				public void makeMap() throws IOException {
					if (map == null) {
						generatePath();	
						csc.sendMakeMap();
					}				
					generateMapImage();
					showMapImage();
					
				}
	
				@Override
				public void clickNorth() throws IOException {
					// TODO Auto-generated method stub
					goNorth();
					generateMapImage();
					showMapImage();
					csc.sendNorth();
				}
	
				@Override
				public void clickSouth() throws IOException {
					// TODO Auto-generated method stub
					goSouth();
					generateMapImage();
					showMapImage();
					csc.sendSouth();
				}
	
				@Override
				public void clickEast() throws IOException {
					// TODO Auto-generated method stub
					goEast();
					generateMapImage();
					showMapImage();
					csc.sendEast();
				}
	
				@Override
				public void clickWest() throws IOException {
					// TODO Auto-generated method stub
					goWest();
					generateMapImage();
					showMapImage();
					csc.sendWest();
				}			
			});
			add(imagePanel, BorderLayout.CENTER);
			add(toolbar, BorderLayout.SOUTH);
			
			setSize(700,700);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true); 
			//If player isn't a dungeon master
			if (!player.isDM()) {
				try {
					//Show start page for players
					BufferedImage image = ImageIO.read(new File(imageUrl + "startPlayer.png"));
					imagePanel.setImage(image);  
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}						        
				toolbar.toggleButtons();
				nextAction();
			} else {
				try {
					//Show start page for dungeon master
					BufferedImage image = ImageIO.read(new File(imageUrl + "startDM.png"));
					imagePanel.setImage(image);  
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}	
			}
		}
		public void nextAction() {
			Thread t = new Thread(new Runnable() {
				public void run() {
					System.out.println("----Turn----");
					updateTurn();					
				}
			});
			t.start();
		}

		public void updateTurn() {
			int n = csc.receiveButtonNum();
			System.out.println("Command :" + n);
			switch(n) {
			case 1:				
				initializeMap();
				map.setMainPath(csc.receiveRooms());
				startMapPlayer();
				
				try {
					generateMapImage();
					showRoomImage();
				} catch(Exception e) {
					System.out.println(e);
				}				
				break;
			case 2:
				goNorth();
				try {
					generateMapImage();
					showRoomImage();} catch(Exception e) {
						System.out.println(e);
					}
				break;
			case 3:
				goSouth();
				try {
					generateMapImage();
					showRoomImage();} catch(Exception e) {
						System.out.println(e);
					}
				break;
			case 4:
				goEast();
				try {
					generateMapImage();
					showRoomImage();} catch(Exception e) {
						System.out.println(e);
					}
				break;
			case 5:
				goWest();
				try {
					generateMapImage();
					showRoomImage();
					} catch(Exception e) {
						System.out.println(e);
					}
				break;
			}
			System.out.print("x: " + currentX + ", y: " + currentY);
			nextAction();
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
			public void sendNorth() {
					try {
						dataOut.writeInt(2);
						dataOut.flush();
					} catch (IOException ex) {
						System.out.println("IO Exception from sendButtonNum() CSC");
					}
			}
			public void sendSouth() {
				try {
					dataOut.writeInt(3);
					dataOut.flush();
				} catch (IOException ex) {
					System.out.println("IO Exception from sendButtonNum() CSC");
				}
			}
			public void sendEast() {
				try {
					dataOut.writeInt(4);
					dataOut.flush();
				} catch (IOException ex) {
					System.out.println("IO Exception from sendButtonNum() CSC");
				}
			}
			public void sendWest() {
				try {
					dataOut.writeInt(5);
					dataOut.flush();
				} catch (IOException ex) {
					System.out.println("IO Exception from sendButtonNum() CSC");
				}
			}
			public void sendMakeMap() {
				try {
					String str = "";
					//byte[] data=str.getBytes("UTF-8");
					//dataOut.writeInt(data.length);
					//dataOut.write(data);
					dataOut.writeInt(1);
					dataOut.writeInt(map.getMainPath().size() + map.getSubPaths().size());
					for( Room r : map.getMainPath()) {
						r = map.getMap()[r.getY()][r.getX()];
						str = Serializator.serializeRoom(r);
						byte[] data=str.getBytes("UTF-8");
						dataOut.writeInt(data.length);
						dataOut.write(data);
					}
					for( Room r : map.getSubPaths()) {
						r = map.getMap()[r.getY()][r.getX()];
						str = Serializator.serializeRoom(r);
						byte[] data=str.getBytes("UTF-8");
						dataOut.writeInt(data.length);
						dataOut.write(data);
					}
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
				} catch(IOException ex) {
					System.out.println("IO Exception from receiveButtonNum() CSC");
				}
				return n;
			}
			
			public List<Room> receiveRooms() {
				List<Room> listRooms =  new ArrayList<Room>();
				try {
					int maxRoom = dataIn.readInt();
					System.out.println("max Room: " + maxRoom);
					for(int i = 0 ; i < maxRoom; i++) {
						int length = dataIn.readInt();
						//System.out.println("lenght: " + length);
						byte[] data=new byte[length];
						dataIn.readFully(data);
						String str = new String(data,"UTF-8");
						//System.out.println(str);
						Room r = Serializator.deserializeRoom(str);
						listRooms.add(r);
						System.out.println(r.toString());
						//System.out.println(i);
					}
				} catch(IOException ex) {
					System.out.println("IO Exception from receiveButtonNum() CSC");
				}
				
				return listRooms;
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
			
	public void initializeMap() {
		//Make a map
				map = new Map(20,20);
	}
	
	public void generatePath() {
		initializeMap();
		//Find A random path
		while(!map.findPath(10, 10, 10, 10, 20,3,5));
		//Connect the rooms of the map
		startMap();
	}
	//Start the map
	public void startMap() {
		map.makeRoomConnected();
		currentX = map.getMainPath().get(0).getX();
		currentY = map.getMainPath().get(0).getY();
		currentRoom = map.getMap()[currentY][currentX];
	}
	//Start the map for the players	
	public void startMapPlayer() {
		map.setMapWithPaths();
		startMap();
	}
	//Show the map on GUI
	public void showMapImage() throws IOException{
		String imageMapUrl = imageUrl + "map\\";
		BufferedImage image = ImageIO.read(new File(imageMapUrl + "finalImg.png"));
        imagePanel.setImage(image);  
	}
	//Show the room where the player is at
	public void showRoomImage() throws IOException {
		//Set path for room image
		String imageRoomUrl = imageUrl + "room\\";
		int i = 0;
		if(!map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
			i = 0;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 1;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 2;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 3;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 4;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 5;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 6;
    	} else if(!map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 7;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 8;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 9;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 10;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && !map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 11;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 12;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& !map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 13;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && !map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 14;
    	} else if(map.getMap()[currentY][currentX].isGoUp() && map.getMap()[currentY][currentX].isGoRight() 
    			&& map.getMap()[currentY][currentX].isGoDown() && map.getMap()[currentY][currentX].isGoLeft()) {
    		i = 15;
    	}
		//Set room as buffered image
		BufferedImage image = ImageIO.read(new File(imageRoomUrl + i + ".png"));
		//Set image in panel
		imagePanel.setImage(image);  
	}
	//Made an image of the map
	public void generateMapImage() throws IOException {
		String imageMapUrl = imageUrl + "map\\";
		// Setup members to make a image of the map
				int rows = map.getY();   //we assume the no. of rows and cols are known and each chunk has equal width and height
		        int cols = map.getX();
		        int chunks = 17;

		        int chunkWidth, chunkHeight;
		        int type;
		        //fetching image files
		        File[] imgFiles = new File[chunks];
		        for (int i = 0; i < chunks; i++) {
		        	imgFiles[i] = new File(imageMapUrl + i + ".png");
		        }

		       //creating a buffered image array from image files
		        BufferedImage[] buffImages = new BufferedImage[chunks];
		        for (int i = 0; i < chunks; i++) {
		            buffImages[i] = ImageIO.read(imgFiles[i]);
		        }
		        type = buffImages[0].getType();
		        chunkWidth = buffImages[0].getWidth();
		        chunkHeight = buffImages[0].getHeight();

		        //Initializing the final image
		        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);
		        for (int i = 0; i < rows; i++) {
		            for (int j = 0; j < cols; j++) {
		            	if(i == currentY && j == currentX) {
		            		finalImg.createGraphics().drawImage(buffImages[16], chunkWidth * j, chunkHeight * i, null);
		            	} else {
		            		if(!map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[0], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[1], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[2], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[3], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[4], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[5], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[6], chunkWidth * j, chunkHeight * i, null);
			            	} else if(!map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[7], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[8], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[9], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[10], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && !map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[11], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[12], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& !map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[13], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && !map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[14], chunkWidth * j, chunkHeight * i, null);
			            	} else if(map.getMap()[i][j].isGoUp() && map.getMap()[i][j].isGoRight() 
			            			&& map.getMap()[i][j].isGoDown() && map.getMap()[i][j].isGoLeft()) {
			            		finalImg.createGraphics().drawImage(buffImages[15], chunkWidth * j, chunkHeight * i, null);
			            	}
		            	}
		            	
		            }
		        }
		        ImageIO.write(finalImg, "png", new File( imageMapUrl +"finalImg.png"));
		        
	}
	//player go up in map
	public void goNorth() {
		if(currentRoom.isGoUp()) {
			currentY -= 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	//player go down in map
	public void goSouth() {
		if(currentRoom.isGoDown()) {
			currentY += 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	//player go right in map
	public void goEast() {
		if(currentRoom.isGoRight()) {
			currentX += 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	//player go left in map
	public void goWest() {
		if(currentRoom.isGoLeft()) {
			currentX -= 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
}
