package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JWindow;

import ui.MainFrame;

public class StatusPanel extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2356950946463783554L;
	public static final int INFO = 0;
	public static final int ERROR = 1;

	private static final Color INFOCOLOR = Color.BLUE;
	private static final Color ERRORCOLOR = Color.RED;
	private static final int TIMEOUTE = 2000;

	private JLabel label;
	private static StatusPanel instance;

	public static StatusPanel getInstance() {
		if(instance == null) {
			instance = new StatusPanel();
		}
		return instance;
	}

	private StatusPanel() {
		super(MainFrame.mainFrame);
		label = new JLabel();
		label.setFont(new Font("Arial", Font.BOLD, 15));
		getContentPane().add(label);
		setMinimumSize(new Dimension(100, 30));
	}

	private MessageThread t = null;
	public void flash(String message, int status) {
		show(message, status);
		if(t != null) {
			t.interrupt();
			t = null;
		}
		t = new MessageThread();
		t.start();
	}

	public void show(String message, int status) {
		Point p = MainFrame.mainFrame.getLocationOnScreen();
		Dimension d = MainFrame.mainFrame.getSize();
		p.x += d.width / 2 - getWidth()/2;
		p.y += 23;
		setLocation(p);

		if(status == INFO) {
			label.setForeground(INFOCOLOR);
		} else {
			label.setForeground(ERRORCOLOR);
		}
		label.setText(message);

		FontMetrics fm = label.getFontMetrics(label.getFont());
		label.setSize(new Dimension(fm.stringWidth(message), fm.getHeight()));
		setSize(label.getSize());
		toFront();requestFocusInWindow();
		setVisible(true);
	}

	public String getMessage() {
		return label.getText();
	}


	private class MessageThread extends Thread {

		public MessageThread() {
			super.setDaemon(true);
		}

		public void run() {
			try {
				Thread.sleep(TIMEOUTE);
				setVisible(false);
			} catch (InterruptedException e) {
			}
		}
	}
}
