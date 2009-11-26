package ui.panels.profile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.components.RangeSlider;
import ui.components.RangeSlider.Slider;

import dataObjects.Subject;

public class PreferencesPanel extends JPanel {

	private static final long serialVersionUID = -9011866376505097065L;
	private Subject user;

	public PreferencesPanel() {
		user = new Subject("John Doe");
		init();
	}

	public PreferencesPanel(Subject user) {
		this.user = user;
		init();
	}

	private void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
		
		RangeSlider s = new RangeSlider();
		Slider slider = s.getSlider();
		
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setName("Kalorier (kJ)");
		slider.setFocusable(false);
		Dimension d = new Dimension(500, 75);
		s.setPreferredSize(d);
		add(s);
		
	}

}
