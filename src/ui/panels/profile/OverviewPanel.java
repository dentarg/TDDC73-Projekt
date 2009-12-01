package ui.panels.profile;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import dataObjects.Session;
import dataObjects.Subject;

public class OverviewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840460382044622882L;
	private Subject user;
	
	public OverviewPanel()
	{
		this.user = Session.getInstance().getUser();
		init();
	}
	
	public OverviewPanel(Subject user) {
		this.user = user;
		init();
	}
	
	private void init()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		
		
	}
}
