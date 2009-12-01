package ui.panels.profile;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.components.RangeSlider;
import ui.components.RangeSlider.Slider;
import dataObjects.Subject;

public class NutritionPanel extends JPanel implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7612894160053221649L;
	private Subject user;

	public NutritionPanel() {
		user = new Subject("John Doe");
		init();
	}

	public NutritionPanel(Subject user) {
		this.user = user;
		init();
	}

	private void init() {
		
		JPanel sliderHolder = new JPanel();
		sliderHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		sliderHolder.setPreferredSize(new Dimension(750, 1000));
		
		RangeSlider s;
		Slider slider;
		s = new RangeSlider("Kalcium");
		slider = s.getSlider();
		Dimension d = new Dimension(500, 50);
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Kolhydrater");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Kolesterol");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Energi");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Fett");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Protein");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		s = new RangeSlider("Salt");
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(10);
		slider.setUpperValue(90);
		slider.addChangeListener(this);
		sliderHolder.add(s);
		
		
		
		JScrollPane scrollPane = new JScrollPane(sliderHolder);
		scrollPane.setPreferredSize(new Dimension(800, 600));
		
		add(scrollPane);
	}

	public void stateChanged(ChangeEvent arg0) {
		RangeSlider.Slider s = (RangeSlider.Slider)arg0.getSource();
		if(!s.getValueIsAdjusting()) {
			int[] values = s.getCombinedValues();
			System.out.println(values[0] + " - " + values[1]);
		}
	
		
	}
	

}
