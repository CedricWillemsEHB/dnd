package View;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton makeMapButton;
	private JButton northButton;
	private JButton southMapButton;
	private JButton eastMapButton;
	private JButton westMapButton;
	private StringListener textListener;
	
	public Toolbar() {
		makeMapButton = new JButton("Maak map");
		makeMapButton.addActionListener(this);
		northButton = new JButton("Boven");
		northButton.addActionListener(this);
		southMapButton = new JButton("Beneden");
		southMapButton.addActionListener(this);
		eastMapButton = new JButton("Rechts");
		eastMapButton.addActionListener(this);
		westMapButton = new JButton("Links");
		westMapButton.addActionListener(this);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(makeMapButton);
		add(northButton);
		add(southMapButton);
		add(eastMapButton);
		add(westMapButton);
	}

	public void setStringListener(StringListener listener) {
		this.textListener = listener;
	}
	public void toggleButtons() {
		makeMapButton.setEnabled(false);
		northButton.setEnabled(false);
		southMapButton.setEnabled(false);
		eastMapButton.setEnabled(false);
		westMapButton.setEnabled(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == makeMapButton) {
			if(textListener != null) {
				try {
					textListener.makeMap();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(clicked == northButton) {
			if(textListener != null) {				
				try {
					textListener.clickNorth();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(clicked == southMapButton) {
			if(textListener != null) {				
				try {
					textListener.clickSouth();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(clicked == eastMapButton) {
			if(textListener != null) {				
				try {
					textListener.clickEast();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(clicked == westMapButton) {
			if(textListener != null) {				
				try {
					textListener.clickWest();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	

}
