package View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dnd.Explorer;

public class test {
	public static void main(String[] args) {
		File file = new File("D:\\Users\\Capybara\\Pictures\\test.png");
        
        BufferedImage image = null;
         
        try
        {
            image = ImageIO.read(file);
             
            ImageIO.write(image, "jpg", new File("D:\\Users\\Capybara\\Pictures\\output.jpg"));
             
            ImageIO.write(image, "png", new File("D:\\Users\\Capybara\\Pictures\\output.png"));
             
            ImageIO.write(image, "gif", new File("D:\\Users\\Capybara\\Pictures\\output.gif"));
             
            ImageIO.write(image, "bmp", new File("D:\\Users\\Capybara\\Pictures\\output.bmp"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
         
        System.out.println("done");
	}
}
