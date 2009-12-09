package ui.panels.profile;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ui.components.StatusPanel;

import mappings.CostMap;
import mappings.DifficultyMap;
import mappings.TimeMap;

import dataObjects.PlanVariables;
import dataObjects.Session;
import dataObjects.Subject;

public class PreferencesPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = -9011866376505097065L;

    private static final int RADIO_PANEL_SPACING = 10;

    private static final int DIET_PANEL_PREFERRED_WIDTH = 300;

    private Subject user;
    private PlanVariables pv;

    public PreferencesPanel() {
        this.user   = Session.getInstance().getUser();
        this.pv     = new PlanVariables();
        
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;
        add(createPrefPanel(), c);
        c.gridx = 1;
        add(createDietPanel(), c);
    }
    
    public JPanel createPrefPanel() {
        JPanel prefPanel = new JPanel();
        //prefPanel.setPreferredSize(new Dimension(300, 600));      
        prefPanel.setLayout(new BoxLayout(prefPanel, BoxLayout.PAGE_AXIS));
        prefPanel.add(createButtonGroup("Kostnad"));
        prefPanel.add(createButtonGroup("Svårighetsgrad"));
        prefPanel.add(createButtonGroup("Tillagningstid"));
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Preferenser",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        prefPanel.setBorder(tb);
        return prefPanel;
    }
    
    public JPanel createDietPanel() {
        JPanel dietPanel = new JPanel();
        dietPanel.setLayout(new BoxLayout(dietPanel, BoxLayout.PAGE_AXIS));

        JLabel dietLabel = new JLabel("<html>När du väljer specialkost så " +
                "utesluts recept som innehåller ingredienser\nfrån " +
                "ett fördefinierat antal kategorier från " +
                "receptförslagen.</html>");

        dietLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dietPanel.add(dietLabel);

        DietPanel mainPanel = new DietPanel();
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dietPanel.add(mainPanel);

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Specialkost",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        dietPanel.setBorder(tb);

        Dimension preferredSize = dietPanel.getPreferredSize();
        preferredSize.width = DIET_PANEL_PREFERRED_WIDTH;
        dietPanel.setPreferredSize(preferredSize);

        return dietPanel;
    }    

    public String translate(String str) {
        if(str.equals("low"))
            return new String("låg");
        if(str.equals("medium"))
            return new String("mellan");
        if(str.equals("high"))
            return new String("hög");
        if(str.equals("easy"))
            return new String("lätt");
        if(str.equals("hard"))
            return new String("svår");
        if(str.equals("short"))
            return new String("kort");      
        
        System.out.println("u: " + str);
        return new String("unknown word");
    }
    
    public JPanel createButtonGroup(String title) {
        // a column in a panel
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));

        ButtonGroup group = new ButtonGroup();
        
        // value is always in the range 1 to 3
        for(int i = 1; i < 4; i++) {
            JRadioButton button = new JRadioButton();

            // use mappings to get button labels from value
            if(title.equals("Kostnad")) {
                CostMap map = pv.getCostMap();
                button.setText(translate(map.getValue(i)));
                button.setActionCommand("cost" + i);
            }
            else if(title.equals("Svårighetsgrad")) {
                DifficultyMap map = pv.getDifficultyMap();
                button.setText(translate(map.getValue(i)));
                button.setActionCommand("difficulty" + i);
            }
            else if(title.equals("Tillagningstid")) {
                TimeMap map = pv.getTimeMap();
                button.setText(translate(map.getValue(i)));
                button.setActionCommand("time" + i);
            }
            group.add(button);
            button.addActionListener(this);
            radioPanel.add(button);
        }

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP
                //new Font("Arial", Font.BOLD, 13)
        );

        radioPanel.setBorder(tb);
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return radioPanel;
    }

    public void flashMsg(String msg) {
        StatusPanel.getInstance().flash(msg, StatusPanel.INFO);
    }

    public void flashErrorMsg(String msg) {
        StatusPanel.getInstance().flash(msg, StatusPanel.ERROR);
    }   
    
    // update the user model with chosen value
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s.startsWith("cost")) {
            int desiredCost = Integer.parseInt(s.substring(s.length()-1));
            user.setDesiredCost(desiredCost);
            CostMap map = pv.getCostMap();
            String value = translate(map.getValue(desiredCost));
            flashMsg("Kostnad ändrad till " + value);
        }
        else if(s.startsWith("difficulty")){
            int desiredDifficulty = Integer.parseInt(s.substring(s.length()-1));
            user.setDesiredDifficulty(desiredDifficulty);
            DifficultyMap map = pv.getDifficultyMap();
            String value = translate(map.getValue(desiredDifficulty));
            flashMsg("Svårighetsgrad ändrad till " + value);
        }
        else if(s.startsWith("time")){
            int desiredTime = Integer.parseInt(s.substring(s.length()-1));
            user.setDesiredTime(desiredTime);
            TimeMap map = pv.getTimeMap();
            String value = translate(map.getValue(desiredTime));
            flashMsg("Tillagningstid ändrad till " + value);
        }
    }
}
