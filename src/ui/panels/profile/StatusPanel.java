package ui.panels.profile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {

	private JLabel label;

	private static StatusPanel instance;

	public static StatusPanel getInstance() {
		if(instance == null) {
			instance = new StatusPanel();
		}
		return instance;
	}

	private StatusPanel() {
		super();
		label = new JLabel();
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setForeground(Color.GREEN);
		add(label);
	}

	public void flash(String message) {
		show(message);
		Thread 	t = new Thread() {
			public void run() {
				if(!getMessage().isEmpty() && this.isAlive()) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
					}
					hide();
				}
			};
		};
		t.start();

	}
	
	public void hide() {
		label.setText("");
	}
	
	public void show(String message) {
		FontMetrics fm = label.getFontMetrics(label.getFont());
		label.setSize(new Dimension(fm.stringWidth(message), fm.getHeight()));
		label.setText(message);
	}

	public String getMessage() {
		return label.getText();
	}

}
