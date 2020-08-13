package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class MainFrame extends JFrame {
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
	
	
	public MainFrame() {
		super("Hello world");
		
		setLayout(new BorderLayout());
		
		imagePanel = new ImagePanel();
		toolbar = new Toolbar();
		
		toolbar.setStringListener(new StringListener() {
			@Override
			public void makeMap() throws IOException {
				if (map == null) {
					generateMap();	
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
		public MainFrame(Player player) {
			super("Hello world");
			
			setLayout(new BorderLayout());
			
			imagePanel = new ImagePanel();
			toolbar = new Toolbar();
			this.player = player;
			if (!player.isDM()) {
				toolbar.toggleButtons();
			}
			toolbar.setStringListener(new StringListener() {
			@Override
			public void makeMap() throws IOException {
				if (map == null) {
					generateMap();	
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
	public void generateMap() {
		//Make a map
		map = new Map(20,20);
		//Find A random path
		while(!map.findPath(10, 10, 10, 10, 20,3,5));
		//Connect the rooms of the map
		map.makeRoomConnected();
		currentX = map.getMainPath().get(0).getX();
		currentY = map.getMainPath().get(0).getY();
		currentRoom = map.getMap()[currentY][currentX];
	}
	public void showMapImage() throws IOException{
		BufferedImage image = ImageIO.read(new File("D:\\\\Users\\\\Capybara\\\\Pictures\\\\dnd\\\\finalImg.png"));
        imagePanel.setImage(image);  
	}
	public void generateMapImage() throws IOException {
		// Setup members to make a image of the map
				int rows = map.getY();   //we assume the no. of rows and cols are known and each chunk has equal width and height
		        int cols = map.getX();
		        int chunks = 17;

		        int chunkWidth, chunkHeight;
		        int type;
		        //fetching image files
		        File[] imgFiles = new File[chunks];
		        for (int i = 0; i < chunks; i++) {
		        	imgFiles[i] = new File("D:\\Users\\Capybara\\Pictures\\dnd\\" + i + ".png");
		        }

		       //creating a bufferd image array from image files
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
		        ImageIO.write(finalImg, "png", new File("D:\\Users\\Capybara\\Pictures\\dnd\\finalImg.png"));
	}
	public void goNorth() {
		if(currentRoom.isGoUp()) {
			currentY -= 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	public void goSouth() {
		if(currentRoom.isGoDown()) {
			currentY += 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	public void goEast() {
		if(currentRoom.isGoRight()) {
			currentX += 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
	public void goWest() {
		if(currentRoom.isGoLeft()) {
			currentX -= 1;
			currentRoom = map.getMap()[currentY][currentX];
		}
	}
}
