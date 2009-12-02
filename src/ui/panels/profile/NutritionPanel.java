package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import ui.components.RangeSlider;
import ui.components.RangeSlider.Slider;
import dataObjects.Session;
import dataObjects.Subject;
import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;
import dataObjects.units.MilliGramMeasurement;

public class NutritionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7612894160053221649L;
	private Subject user;

	public NutritionPanel() {
		user = Session.getInstance().getUser();
		init();
	}

	public NutritionPanel(Subject user) {
		this.user = user;
		init();
	}

	private void init() {
		
		JPanel sliderHolder = new JPanel();
		sliderHolder.setLayout(new GridLayout(0, 1));
		
		RangeSlider s;
		Slider slider;
		s = new RangeSlider("Kalcium (mg)", 0, 1500);
		slider = s.getSlider();
		Dimension d = new Dimension(500, 50);
		slider.setPreferredSize(d);
		slider.setValue(user.getMinCalcium().getGrams()*1000);
		slider.setUpperValue(user.getMaxCalcium().getGrams()*1000);
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinCalcium(new MilliGramMeasurement(s.getValue()));
					user.setMaxCalcium(new MilliGramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Kolhydrater (g)", 0, 300);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinCarbohydrates().getGrams());
		slider.setUpperValue(user.getMaxCarbohydrates().getGrams());
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinCarbohydrates(new GramMeasurement(s.getValue()));
					user.setMaxCarbohydrates(new GramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Kolesterol (mg)", 0, 500);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinCholesterol().getGrams()*1000);
		slider.setUpperValue(user.getMaxCholesterol().getGrams()*1000);
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinCholesterol(new MilliGramMeasurement(s.getValue()));
					user.setMaxCholesterol(new MilliGramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Energi (kcal)", 0, 5000);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinEnergyContent().getQuantity());
		slider.setUpperValue(user.getMaxEnergyContent().getQuantity());
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinEnergyContent(Measurement.createMeasurement("kcal", new Float(s.getValue())));
					user.setMaxEnergyContent(Measurement.createMeasurement("kcal", new Float(s.getUpperValue())));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Fett (g)", 0, 50);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinFat().getGrams());
		slider.setUpperValue(user.getMaxFat().getGrams());
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinFat(new GramMeasurement(s.getValue()));
					user.setMaxFat(new GramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Protein (g)", 0, 100);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinProtein().getGrams());
		slider.setUpperValue(user.getMaxProtein().getGrams());
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinProtein(new GramMeasurement(s.getValue()));
					user.setMaxProtein(new GramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		s = new RangeSlider("Salt (mg)", 0, 200);
		slider = s.getSlider();
		slider.setPreferredSize(d);
		slider.setValue(user.getMinSodium().getGrams()*1000);
		slider.setUpperValue(user.getMaxSodium().getGrams()*1000);
		slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				RangeSlider.Slider s = (RangeSlider.Slider)e.getSource();
				if(!s.getValueIsAdjusting()) {
					user.setMinSodium(new MilliGramMeasurement(s.getValue()));
					user.setMaxSodium(new MilliGramMeasurement(s.getUpperValue()));
				}
			}
		});
		sliderHolder.add(s);
		add(sliderHolder);
	}

	

}
