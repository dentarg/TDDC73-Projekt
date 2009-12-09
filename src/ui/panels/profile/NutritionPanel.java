package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.components.RangeSlider;
import ui.components.StatusPanel;
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
	
	private final String info = 
			"<html>Här ställer du in näringsvärden du " +
			"önskar i dina recept.<br />Dessa " +
			"inställningar kommer att användas när " +
			"du söker efter recept.</html>";
	
	public NutritionPanel() {
		user = Session.getInstance().getUser();
		init();
	}

	public NutritionPanel(Subject user) {
		this.user = user;
		init();
	}

	private void init() {
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setLayout(new BorderLayout());
        
        
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        
        p.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        		"Näringsvärden",
        		TitledBorder.LEFT,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        ));
        
        p.setSize(new Dimension(500, 50));
        
        JLabel text = new JLabel(info);
        text.setVerticalAlignment(JLabel.TOP);
        text.setHorizontalAlignment(JLabel.LEFT);
        
        p.add(text, BorderLayout.PAGE_START);
        p.add(createSliders(), BorderLayout.LINE_START);
        
        add(p, BorderLayout.LINE_START);
	}
	
	private JPanel createSliders() {
		JPanel sliderHolder = new JPanel();
		sliderHolder.setLayout(new BoxLayout(sliderHolder, BoxLayout.Y_AXIS));
		
		
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
					StatusPanel.getInstance().flash("Önskade kalciumvärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade kolhydratervärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade kolesterolvärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade energivärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade fettvärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade proteinvärden sparat för din profil", StatusPanel.INFO);
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
					StatusPanel.getInstance().flash("Önskade saltvärden sparat för din profil", StatusPanel.INFO);
				}
			}
		});
		sliderHolder.add(s);
		return sliderHolder;
	}

	

}
