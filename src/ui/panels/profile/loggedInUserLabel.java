package ui.panels.profile;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dataObjects.Session;
import dataObjects.Subject;

public class loggedInUserLabel extends JLabel implements ChangeListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 7481116757299282524L;
	private Subject user = Session.getInstance().getUser();

	public loggedInUserLabel() {
		setAlignmentX(SwingConstants.RIGHT);
	}

	public void stateChanged(ChangeEvent e) {
		setText("Inloggad som " + user);
	}
}
