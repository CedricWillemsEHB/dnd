package View;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private JScrollPane jscrollpane;
	private JTextArea textArea;
	private Label label;
	
	public ImagePanel() {
		//textArea = new JTextArea();
		//setLayout(new BorderLayout());
		//jscrollpane = new JScrollPane(textArea);
		//add(jscrollpane, BorderLayout.CENTER);
		
	}
	void setImage(BufferedImage image)
    {
        this.image = image;
        repaint();
    }
	public void appendText(String string) {
		// TODO Auto-generated method stub
		textArea.append(string);
	}
	public void showText() {
	}
	public void setLabel() {
		//remove(jscrollpane);
		setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("D:\\Users\\Capybara\\Pictures\\dnd\\finalImg.png");
		JLabel label = new JLabel(icon);
		add(new JScrollPane(label), BorderLayout.CENTER,0);
		this.setVisible(true);
	}

	@Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }
    }
}
