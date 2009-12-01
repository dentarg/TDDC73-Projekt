package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.TitledBorder;

public class EditWindow extends JWindow {

	private AddRemoveComponent subjectList;
	
	public EditWindow() {
		getContentPane().add(createSubjectPanel());
		
		pack();
	}
	
	public JPanel createSubjectPanel() {
		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BorderLayout());
		
		subjectList = new AddRemoveComponent(new JFrame());
		subjectPanel.add(subjectList, BorderLayout.NORTH);
		
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				"Members", 
				TitledBorder.LEFT, 
				TitledBorder.CENTER, 
				new Font("Arial", Font.BOLD, 15));
		subjectPanel.setBorder(tb);
		
		return subjectPanel;
	}
}
